package nl.knokko.gui.menu.game;

import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import nl.knokko.gamestate.StateGameMenu;
import nl.knokko.gui.button.ButtonCloseMenu;
import nl.knokko.gui.button.ButtonLink;
import nl.knokko.gui.component.AbstractGuiComponent;
import nl.knokko.gui.component.menu.GuiMenu;
import nl.knokko.gui.mousecode.MouseCode;
import nl.knokko.gui.render.GuiRenderer;
import nl.knokko.gui.texture.GuiTexture;
import nl.knokko.inventory.InventoryType;
import nl.knokko.items.Item;
import nl.knokko.main.Game;
import nl.knokko.main.GameScreen;
import nl.knokko.players.Player;
import nl.knokko.texture.SizedTexture;
import nl.knokko.texture.Texture;
import nl.knokko.util.color.Color;
import nl.knokko.util.color.ColorAlpha;
import nl.knokko.util.resources.Resources;

public class GuiPlayerEquipment extends GuiMenu {
	
	private static final Vector2f EQUIPMENT_SIZE = new Vector2f(0.04f, 0.04f);
	private static final Color COVER = new ColorAlpha(0, 0, 0, 100);
	
	private static final Color BUTTON_COLOR = new Color(200, 100, 0);
	private static final Color BORDER_COLOR = new Color(100, 50, 0);
	
	private final StateGameMenu state;
	
	private EquipmentButton selectedButton;
	private List<Item> selectableItems;
	private SizedTexture selectedInventoryTexture;

	public GuiPlayerEquipment(StateGameMenu state) {
		this.state = state;
	}
	
	@Override
	protected void addComponents(){
		addButton(new GuiPlayerMenu.ButtonSwapPlayer(new Vector2f(-0.65f, 0f), new Vector2f(0.3f, 0.1f), BUTTON_COLOR, BORDER_COLOR, state.getPlayersMenu()));
		addButton(new ButtonLink(new Vector2f(-0.65f, -0.6f), new Vector2f(0.3f, 0.1f), BUTTON_COLOR, BORDER_COLOR, Color.BLACK, "Back to characters", state.getPlayersMenu(), state));
		addButton(new ButtonCloseMenu(new Vector2f(-0.65f, -0.85f), new Vector2f(0.3f, 0.1f), BUTTON_COLOR, BORDER_COLOR));
		addButton(new EquipmentButton(new Vector2f(0.3f, 0.8f), EQUIPMENT_SIZE, "Helmet", InventoryType.HELMET));
		addButton(new EquipmentButton(new Vector2f(0.0f, 0.5f), EQUIPMENT_SIZE, "LeftWeapon", InventoryType.WEAPON));
		addButton(new EquipmentButton(new Vector2f(0.6f, 0.5f), EQUIPMENT_SIZE, "RightWeapon", InventoryType.WEAPON));
		addButton(new EquipmentButton(new Vector2f(0.0f, 0.2f), EQUIPMENT_SIZE, "LeftGlobe", InventoryType.GLOBE));
		addButton(new EquipmentButton(new Vector2f(0.6f, 0.2f), EQUIPMENT_SIZE, "RightGlobe", InventoryType.GLOBE));
		addButton(new EquipmentButton(new Vector2f(0.3f, -0.1f), EQUIPMENT_SIZE, "Chestplate", InventoryType.CHESTPLATE));
		addButton(new EquipmentButton(new Vector2f(0.3f, -0.4f), EQUIPMENT_SIZE, "Pants", InventoryType.PANTS));
		addButton(new EquipmentButton(new Vector2f(0.0f, -0.7f), EQUIPMENT_SIZE, "LeftShoe", InventoryType.BOOTS));
		addButton(new EquipmentButton(new Vector2f(0.6f, -0.7f), EQUIPMENT_SIZE, "RightShoe", InventoryType.BOOTS));
	}
	
	@Override
	public void render(GuiRenderer renderer){
		super.render(renderer);
		if(selectedInventoryTexture != null){
			//TODO create another GuiComponent for the item selection
			renderer.fill(COVER, 0, 0, 1, 1);
			renderer.renderTextures(new Vector2f(0f, 1f - selectedInventoryTexture.getRelativeHeight()), new Vector2f(selectedInventoryTexture.getRelativeWidth(), selectedInventoryTexture.getRelativeHeight()), selectedInventoryTexture);
		}
	}
	
	@Override
	public void click(float x, float y, int button){
		if(button == MouseCode.BUTTON_LEFT){
			if(selectedInventoryTexture != null){
				Item item = selectedButton.getItem();
				if(x >= -selectedInventoryTexture.getRelativeWidth() && x <= selectedInventoryTexture.getRelativeWidth()){
					int index = (int) ((((1 - y) / 2) * GameScreen.getHeight()) / 64);
					if(index < selectableItems.size()){
						selectedButton.setItem(selectableItems.get(index));
						if(item != null)
							Game.getPlayerInventory().addItem(item);
						Game.getPlayerInventory().removeItem(selectableItems.get(index));
					}
					else if(item != null){
						selectedButton.setItem(null);
						Game.getPlayerInventory().addItem(item);
					}
				}
				else if(item != null){
					selectedButton.setItem(null);
					Game.getPlayerInventory().addItem(item);
				}
				closeItemSelection();
			}
			else
				super.click(x, y, button);
		}
	}
	
	private Player getPlayer(){
		return Game.getGameMenu().getPlayersMenu().player;
	}
	
	private void openItemSelection(EquipmentButton button, InventoryType type){
		selectedButton = button;
		selectableItems = Game.getPlayerInventory().getItems(type);
		selectedInventoryTexture = Resources.createInventoryTexture(Game.getPlayerInventory(), selectableItems);
		if(selectedInventoryTexture == null){
			Game.getPlayerInventory().addItem(selectedButton.getItem());
			selectedButton.setItem(null);
			selectedButton = null;
			selectableItems = null;
		}
	}
	
	private void closeItemSelection(){
		selectedButton = null;
		selectableItems = null;
		selectedInventoryTexture = null;
	}
	
	private class EquipmentButton extends AbstractGuiComponent {
		
		private GuiTexture texture;
		private final String slot;
		private final InventoryType type;
		
		private EquipmentButton(String slot, InventoryType type){
			this.slot = slot;
			this.type = type;
		}
		
		@Override
		public void init(){
			updateItem();
		}

		@Override
		public void click(float x, float y, int button) {
			if(button == MouseCode.BUTTON_LEFT)
				openItemSelection(this, type);
		}
		
		@Override
		public void render(GuiRenderer renderer){
			//updateItem();
			if(texture != null)
				renderer.renderTexture(texture, 0, 0, 1, 1);
			else
				renderer.fill(BORDER_COLOR, 0, 0, 1, 1);
		}
		
		private Item getItem(){
			try {
				return (Item) getPlayer().getEquipment().getClass().getMethod("get" + slot).invoke(getPlayer().getEquipment());
			} catch(Exception ex){
				throw new RuntimeException("Can't get item of slot " + slot, ex);
			}
		}
		
		private void updateItem(){
			updateItem(getItem());
		}
		
		private void updateItem(Item item){
			if(item != null)
				texture = item.getTexture().getGuiTexture();
			else
				texture = null;
		}
		
		private void setItem(Item item){
			try {
				getPlayer().getEquipment().getClass().getMethod("equip" + slot, Item.class).invoke(getPlayer().getEquipment(), item);
				getPlayer().refreshEquipmentModels();
				updateItem(item);
			} catch(Exception ex){
				throw new RuntimeException("Can't set item in slot " + slot, ex);
			}
		}

		@Override
		public void update() {}

		@Override
		public void clickOut(int button) {}

		@Override
		public boolean scroll(float amount) {
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