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
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import nl.knokko.gui.button.ButtonCloseMenu;
import nl.knokko.gui.component.WrapperComponent;
import nl.knokko.gui.component.image.ConditionalImageButton;
import nl.knokko.gui.component.image.SimpleImageComponent;
import nl.knokko.gui.component.inventory.ComponentInventory;
import nl.knokko.gui.component.menu.GuiMenu;
import nl.knokko.gui.component.text.TextButton;
import nl.knokko.gui.component.text.TextComponent;
import nl.knokko.gui.keycode.KeyCode;
import nl.knokko.gui.render.GuiRenderer;
import nl.knokko.gui.texture.loader.GuiTextureLoader;
import nl.knokko.gui.util.TextBuilder.Properties;
import nl.knokko.input.KeyInput;
import nl.knokko.inventory.trading.TradeOffer;
import nl.knokko.inventory.trading.TradeOffers;
import nl.knokko.main.Game;
import nl.knokko.main.GameScreen;
import nl.knokko.texture.SizedTexture;
import nl.knokko.util.resources.Resources;

public class GuiTrading extends GuiMenu {
	
	//private static final Vector2f BUY_MIN = new Vector2f(-200f / GameScreen.getWidth() * 2, 175f / GameScreen.getHeight() * -2);//[-200,-50] and [100,175]
	//private static final Vector2f BUY_MAX = new Vector2f(-50f / GameScreen.getWidth() * 2, 100f / GameScreen.getHeight() * -2);
	//private static final Vector2f CANCEL_MIN = new Vector2f(50f / GameScreen.getWidth() * 2, 175f / GameScreen.getHeight() * -2);//[50,200] and [100,175]
	//private static final Vector2f CANCEL_MAX = new Vector2f(200f / GameScreen.getWidth() * 2, 100f / GameScreen.getHeight() * -2);
	
	protected TradeOffers offers;
	
	protected List<TradeOffer> list;
	
	protected ComponentInventory inventoryComponent;
	protected SelectedOfferWrapper offerWrapper;
	
	protected SizedTexture offersTexture;
	protected SizedTexture selectedOfferTexture;
	
	protected TradeOffer selectedOffer;

	public GuiTrading(TradeOffers offers) {
		this.offers = offers;
		inventoryComponent = new ComponentInventory(null, null);
	}
	
	@Override
	protected void addComponents(){
		offerWrapper = new SelectedOfferWrapper();
		addComponent(offerWrapper, 0.55f, 0.5f, 0.95f, 0.9f);
		addComponent(inventoryComponent, 0, 0, 0.2f, 1);
		refreshCurrentTexture();
		addButton(new ButtonCloseMenu(new Vector2f(-0.65f, -0.85f), new Vector2f(0.3f, 0.1f), new Color(0, 150, 150), new Color(0, 50, 50)));
	}
	
	@Override
	public void update(){
		super.update();
		if(KeyInput.wasKeyPressed(KeyCode.KEY_ESCAPE)){
			if(selectedOfferTexture == null)
				Game.removeState();
			else {
				selectedOfferTexture = null;
				selectedOffer = null;
				offerWrapper.getComponent().updateOffer();
			}
		}
	}
	
	@Override
	public void leftClick(float x, float y){
		if(selectedOfferTexture != null && x >= -selectedOfferTexture.getRelativeWidth() && x <= selectedOfferTexture.getRelativeWidth() && y >= -selectedOfferTexture.getRelativeHeight() && y <= selectedOfferTexture.getRelativeHeight()){
			if(x >= BUY_MIN.x && x <= BUY_MAX.x && y >= BUY_MIN.y && y <= BUY_MAX.y){
				selectedOffer.trade(Game.getPlayerInventory());
				refreshCurrentTexture();
				return;
			}
			if(x >= CANCEL_MIN.x && x <= CANCEL_MAX.x && y >= CANCEL_MIN.y && y <= CANCEL_MAX.y){
				selectedOfferTexture = null;
				selectedOffer = null;
				offerWrapper.getComponent().updateOffer();
			}
			return;
		}
		if(x >= -0.5f){
			int index = (int) ((((1 - y) / 2) * GameScreen.getHeight()) / 64);
			if(index < list.size()){
				selectedOfferTexture = Resources.createTradeOfferTexture(list.get(index));
				selectedOffer = list.get(index);
			}
		}
	}
	
	public void refreshCurrentTexture(){
		inventoryComponent.refresh(offers.getUsefulItems(Game.getPlayerInventory().getItems()));
		list = offers.getAvailableOffers(Game.getPlayerInventory());
		offersTexture = Resources.creatureTradeOffersTexture(list.toArray(new TradeOffer[list.size()]));
		if(selectedOffer != null && !selectedOffer.canPay(Game.getPlayerInventory())){
			selectedOffer = null;
			selectedOfferTexture = null;
		}
	}
	
	private static final Properties LABEL_PROPS = Properties.createLabel();
	private static final Properties TRADE_PROPS = Properties.createButton(new Color(0, 200, 0), new Color(0, 50, 0));
	private static final Properties TRADE_HOVER_PROPS = Properties.createButton(Color.GREEN, new Color(0, 65, 0));
	
	private class SelectedOfferWrapper extends WrapperComponent<SelectedOfferComponent> {

		public SelectedOfferWrapper() {
			super(new SelectedOfferComponent());
		}
		
		@Override
		public boolean isActive() {
			return selectedOffer != null;
		}
	}
	
	private class SelectedOfferComponent extends GuiMenu {
		
		private TextComponent amountComponent;
		private int amount;
		
		private void updateOffer() {
			if(selectedOffer != null) {
				if(amount != 1) {
					amount = 1;
					amountComponent.setText("1");
				}
			}
		}

		@Override
		protected void addComponents() {
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
				updateOffer();
			}), 0.65f, 0.1f, 0.85f, 0.2f);
			addComponent(new SimpleImageComponent(tl.loadTexture("textures/portraits/gothrok.png")), 0.1f, 0.85f, 0.2f, 0.95f);
		}
	}
}