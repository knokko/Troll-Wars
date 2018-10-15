/* 
 * The MIT License
 *
 * Copyright 2018 knokko.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package nl.knokko.gui.trading;

import java.awt.Color;

import nl.knokko.gui.button.ButtonCloseMenu;
import nl.knokko.gui.color.GuiColor;
import nl.knokko.gui.component.GuiComponent;
import nl.knokko.gui.component.WrapperComponent;
import nl.knokko.gui.component.image.ConditionalImageButton;
import nl.knokko.gui.component.image.SimpleImageComponent;
import nl.knokko.gui.component.inventory.ComponentInventory;
import nl.knokko.gui.component.menu.GuiMenu;
import nl.knokko.gui.component.state.GuiComponentState;
import nl.knokko.gui.component.state.RelativeComponentState;
import nl.knokko.gui.component.text.TextButton;
import nl.knokko.gui.component.text.TextComponent;
import nl.knokko.gui.keycode.KeyCode;
import nl.knokko.gui.render.GuiRenderer;
import nl.knokko.gui.texture.loader.GuiTextureLoader;
import nl.knokko.gui.util.TextBuilder.HorAlignment;
import nl.knokko.gui.util.TextBuilder.Properties;
import nl.knokko.gui.util.TextBuilder.VerAlignment;
import nl.knokko.inventory.trading.ItemStack;
import nl.knokko.inventory.trading.TradeOffer;
import nl.knokko.inventory.trading.TradeOffers;
import nl.knokko.items.Item;
import nl.knokko.main.Game;

public class GuiTrading extends GuiMenu {
	
	protected TradeOffers offers;
	
	protected ComponentInventory inventoryComponent;
	protected OffersComponent offersComponent;
	protected SelectedOfferWrapper selectedOfferWrapper;
	
	protected TradeOffer selectedOffer;

	public GuiTrading(TradeOffers offers) {
		this.offers = offers;
	}
	
	@Override
	protected void addComponents(){
		inventoryComponent = new ComponentInventory(offers.getUsefulItems(Game.getPlayerInventory().getItems()), null);
		offersComponent = new OffersComponent();
		selectedOfferWrapper = new SelectedOfferWrapper();
		addComponent(selectedOfferWrapper, 0.55f, 0.5f, 0.95f, 0.9f);
		System.out.println("add selectedOfferWrapper " + selectedOfferWrapper.getComponent().amountComponent);
		addComponent(offersComponent, 0.25f, 0f, 0.5f, 1f);
		addComponent(inventoryComponent, 0, 0, 0.2f, 0.8f);
		addComponent(new ButtonCloseMenu(Properties.createButton(new Color(0, 150, 150), new Color(0, 50, 50)), Properties.createButton(new Color(0, 180, 180), new Color(0, 65, 65))), 0.05f, 0.85f, 0.25f, 0.95f);
	}
	
	@Override
	public void keyPressed(int key) {
		if(key == KeyCode.KEY_ESCAPE) {
			if(selectedOffer == null)
				Game.removeState();
			else {
				selectedOffer = null;
				selectedOfferWrapper.getComponent().updateOffer();
			}
		}
	}
	
	private static final Properties LABEL_PROPS = Properties.createLabel();
	private static final Properties TRADE_PROPS = Properties.createButton(new Color(0, 200, 0), new Color(0, 50, 0));
	private static final Properties TRADE_HOVER_PROPS = Properties.createButton(Color.GREEN, new Color(0, 65, 0));
	
	private class OffersComponent extends GuiMenu {

		@Override
		protected void addComponents() {
			clearComponents();
			TradeOffer[] available = offers.getAllOffers();
			int index = 0;
			for (TradeOffer offer : available) {
				addComponent(new TradeOfferComponent(offer), 0.05f, 0.8f - index * 0.2f, 0.95f, 1f - index * 0.2f);
				index++;
			}
		}
		
		private class TradeOfferComponent implements GuiComponent {
			
			private final ItemStacksComponent give;
			private final ItemStacksComponent get;
			
			private final TradeOffer offer;
			
			private GuiComponentState state;
			
			private TradeOfferComponent(TradeOffer offer) {
				give = new ItemStacksComponent(0.4f);
				get = new ItemStacksComponent(0.4f);
				this.offer = offer;
			}
			
			@Override
			public void setState(GuiComponentState state) {
				this.state = state;
				give.setState(new RelativeComponentState.Static(state, 0.05f, 0.05f, 0.45f, 0.75f));
				get.setState(new RelativeComponentState.Static(state, 0.55f, 0.05f, 0.95f, 0.75f));
			}
			
			@Override
			public GuiComponentState getState() {
				return state;
			}

			@Override
			public void init() {
				give.setItems(offer.getItemsToGive());
				get.setItems(offer.getItemsToGet());
			}

			@Override
			public void update() {}

			@Override
			public void render(GuiRenderer renderer) {
				give.render(renderer.getArea(0.05f, 0.05f, 0.45f, 0.75f));
				get.render(renderer.getArea(0.55f, 0.05f, 0.95f, 0.75f));
			}

			@Override
			public void click(float x, float y, int button) {
				selectedOffer = offer;
				selectedOfferWrapper.getComponent().updateOffer();
			}

			@Override
			public void clickOut(int button) {}

			@Override
			public boolean scroll(float amount) {
				if(state.getMouseY() <= 0.75f) {
					float x = state.getMouseX();
					if(x >= 0.05f && x <= 0.45f)
						return give.scroll(amount);
					if(x >= 0.55f && x <= 0.95f)
						return get.scroll(amount);
				}
				return false;
			}

			@Override
			public void keyPressed(int keyCode) {}

			@Override
			public void keyPressed(char character) {}

			@Override
			public void keyReleased(int keyCode) {}
		}
	}
	
	private class SelectedOfferWrapper extends WrapperComponent<SelectedOfferComponent> {

		public SelectedOfferWrapper() {
			super(new SelectedOfferComponent());
		}
		
		@Override
		public boolean isActive() {
			return selectedOffer != null;
		}
	}
	
	private static final GuiColor SELECTED_OFFER_BACKGROUND = nl.knokko.util.color.Color.YELLOW;
	
	private class SelectedOfferComponent extends GuiMenu {
		
		private TextComponent amountComponent;
		private int amount;
		
		private ItemStacksComponent give;
		private ItemStacksComponent get;
		
		private void updateOffer() {
			if(selectedOffer != null) {
				if(amount != 1) {
					amount = 1;
					amountComponent.setText("1");
				}
				give.setItems(selectedOffer.getItemsToGive());
				get.setItems(selectedOffer.getItemsToGet());
			}
		}
		
		@Override
		public GuiColor getBackgroundColor() {
			return SELECTED_OFFER_BACKGROUND;
		}

		@Override
		protected void addComponents() {
			System.out.println("Create amountComponent");
			amountComponent = new TextComponent("1", LABEL_PROPS);
			amount = 1;
			addComponent(amountComponent, 0.4f, 0.1f, 0.5f, 0.2f);
			addComponent(new TextComponent("x", LABEL_PROPS), 0.35f, 0.1f, 0.38f, 0.2f);
			GuiTextureLoader tl = state.getWindow().getTextureLoader();
			addComponent(new ConditionalImageButton(tl.loadTexture("textures/gui/trading/increase.png"), tl.loadTexture("textures/gui/trading/increase_hover.png"), () -> {
				amount++;
				amountComponent.setText(amount + "");
			}, () -> {
				return selectedOffer.canPay(Game.getPlayerInventory(), amount + 1);
			}), 0.225f, 0.155f, 0.325f, 0.205f);
			addComponent(new ConditionalImageButton(tl.loadTexture("textures/gui/trading/decrease.png"), tl.loadTexture("textures/gui/trading/decrease_hover.png"), () -> {
				amount--;
				amountComponent.setText(amount + "");
			}, () -> {
				return amount > 1;
			}), 0.225f, 0.095f, 0.325f, 0.145f);
			addComponent(new TextButton("Trade", TRADE_PROPS, TRADE_HOVER_PROPS, () -> {
				selectedOffer.trade(Game.getPlayerInventory(), amount);
				selectedOffer = null;
			}), 0.65f, 0.1f, 0.85f, 0.2f);
			addComponent(new SimpleImageComponent(tl.loadTexture("textures/portraits/gothrok.png")), 0.1f, 0.85f, 0.2f, 0.95f);
			give = new ItemStacksComponent(0.2f);
			get = new ItemStacksComponent(0.2f);
			addComponent(give, 0.25f, 0.4f, 0.45f, 0.8f);
			addComponent(get, 0.55f, 0.4f, 0.75f, 0.8f);
		}
	}
	
	private class ItemStacksComponent extends GuiMenu {
		
		private final float stackHeight;
		
		private ItemStacksComponent(float stackHeight) {
			this.stackHeight = stackHeight;
		}

		@Override
		protected void addComponents() {}
		
		private void setItems(ItemStack[] items) {
			clearComponents();
			if(items != null) {
				for (int index = 0; index < items.length; index++) {
					Item item = items[index].getItem();
					addComponent(new SimpleImageComponent(item.getTexture().getGuiTexture()), 0f, 1f - stackHeight - index * stackHeight * 1.2f, 0.2f, 1f - index * stackHeight * 1.2f);
					addComponent(new TextComponent(item.getName() + " x " + items[index].getAmount(), ITEM_PROPERTIES), 0.2f, 1f - stackHeight - index * stackHeight * 1.2f, 1f, 1f - index * stackHeight * 1.2f);
				}
			}
		}
	}
	
	private static final Properties ITEM_PROPERTIES = new Properties(
			Properties.DEFAULT_BUTTON_FONT, Color.BLACK, new Color(180, 70, 0), new Color(50, 20, 0), 
			HorAlignment.LEFT, VerAlignment.MIDDLE, 0.025f, 0.05f, 0.05f, 0.1f, -1, -1);
}