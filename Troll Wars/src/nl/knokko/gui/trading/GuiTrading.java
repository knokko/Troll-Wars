package nl.knokko.gui.trading;

import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import nl.knokko.gui.button.ButtonCloseMenu;
import nl.knokko.gui.component.inventory.ComponentInventory;
import nl.knokko.gui.component.menu.GuiMenu;
import nl.knokko.gui.render.GuiRenderer;
import nl.knokko.input.KeyInput;
import nl.knokko.inventory.trading.TradeOffer;
import nl.knokko.inventory.trading.TradeOffers;
import nl.knokko.main.Game;
import nl.knokko.main.GameScreen;
import nl.knokko.texture.SizedTexture;
import nl.knokko.util.color.Color;
import nl.knokko.util.resources.Resources;

public class GuiTrading extends GuiMenu {
	
	//private static final Vector2f BUY_MIN = new Vector2f(-200f / GameScreen.getWidth() * 2, 175f / GameScreen.getHeight() * -2);//[-200,-50] and [100,175]
	//private static final Vector2f BUY_MAX = new Vector2f(-50f / GameScreen.getWidth() * 2, 100f / GameScreen.getHeight() * -2);
	//private static final Vector2f CANCEL_MIN = new Vector2f(50f / GameScreen.getWidth() * 2, 175f / GameScreen.getHeight() * -2);//[50,200] and [100,175]
	//private static final Vector2f CANCEL_MAX = new Vector2f(200f / GameScreen.getWidth() * 2, 100f / GameScreen.getHeight() * -2);
	
	protected TradeOffers offers;
	
	protected List<TradeOffer> list;
	
	protected ComponentInventory inventoryComponent;
	
	protected SizedTexture offersTexture;
	protected SizedTexture selectedOfferTexture;
	
	protected TradeOffer selectedOffer;

	public GuiTrading(TradeOffers offers) {
		this.offers = offers;
		inventoryComponent = new ComponentInventory(null, null);
	}
	
	@Override
	protected void addComponents(){
		addComponent(inventoryComponent, 0, 0, 0.2f, 1);
		refreshCurrentTexture();
		addButton(new ButtonCloseMenu(new Vector2f(-0.65f, -0.85f), new Vector2f(0.3f, 0.1f), new Color(0, 150, 150), new Color(0, 50, 50)));
	}
	
	@Override
	public void update(){
		super.update();
		if(KeyInput.wasKeyPressed(Keyboard.KEY_ESCAPE)){
			if(selectedOfferTexture == null)
				Game.removeState();
			else {
				selectedOfferTexture = null;
				selectedOffer = null;
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
			}
			return;
		}
		for(IButton button : buttons)
			if(button.isHit(x, y)){
				button.leftClick(x, y);
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
	
	@Override
	public void render(GuiRenderer renderer){
		renderer.renderTextures(new Vector2f(0.25f, 1 - offersTexture.getRelativeHeight()), new Vector2f(0.75f, offersTexture.getRelativeHeight()), offersTexture);
		if(selectedOfferTexture != null)
			renderer.renderTextures(new Vector2f(), new Vector2f(selectedOfferTexture.getRelativeWidth(), selectedOfferTexture.getRelativeHeight()), selectedOfferTexture);
		renderer.render(textures, buttons, this, false);
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
	
	private class SelectedOfferComponent {
		
	}
}