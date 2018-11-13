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
package nl.knokko.gui.menu.game;

import java.awt.Color;

import nl.knokko.gamestate.StateGameMenu;
import nl.knokko.gui.button.ButtonCloseMenu;
import nl.knokko.gui.button.ButtonLink;
import nl.knokko.gui.color.GuiColor;
import nl.knokko.gui.color.SimpleGuiColor;
import nl.knokko.gui.component.AbstractGuiComponent;
import nl.knokko.gui.component.WrapperComponent;
import nl.knokko.gui.component.inventory.ComponentInventory;
import nl.knokko.gui.component.menu.GuiMenu;
import nl.knokko.gui.component.text.ConditionalTextButton;
import nl.knokko.gui.mousecode.MouseCode;
import nl.knokko.gui.render.GuiRenderer;
import nl.knokko.gui.texture.GuiTexture;
import nl.knokko.gui.util.TextBuilder.Properties;
import nl.knokko.inventory.InventoryType;
import nl.knokko.items.Item;
import nl.knokko.main.Game;
import nl.knokko.players.Player;

public class GuiPlayerEquipment extends GuiMenu {
	
	private static final Properties BUTTON_PROPS = Properties.createButton(new Color(200, 100, 0), new Color(100, 50, 0));
	private static final Properties HOVER_PROPS = Properties.createButton(new Color(255, 125, 0), new Color(125, 65, 0));
	
	private final StateGameMenu state;
	
	private WrapperComponent<ComponentInventory> equipmentSelect;
	private EquipmentEmptyButton equipmentEmpty;

	public GuiPlayerEquipment(StateGameMenu state) {
		this.state = state;
	}
	
	@Override
	protected void addComponents(){
		equipmentSelect = new WrapperComponent<ComponentInventory>(null);
		equipmentEmpty = new EquipmentEmptyButton();
		addComponent(equipmentSelect, 0.7f, 0.15f, 0.95f, 0.95f);
		addComponent(equipmentEmpty, 0.7f, 0.025f, 0.9f, 0.125f);
		addComponent(new ButtonCloseMenu(BUTTON_PROPS, HOVER_PROPS), 0.05f, 0.15f, 0.25f, 0.25f);
		addComponent(new ButtonLink("Back to characters", state, state.getPlayersMenu(), BUTTON_PROPS, HOVER_PROPS), 0.05f, 0.3f, 0.25f, 0.4f);
		addComponent(new GuiPlayerMenu.ButtonSwapPlayer(BUTTON_PROPS, HOVER_PROPS, state.getPlayersMenu()), 0.05f, 0.5f, 0.25f, 0.6f);
		
		addComponent(new EquipmentButton("Helmet", InventoryType.HELMET), 0.45f, 0.6f, 0.55f, 0.7f);
		addComponent(new EquipmentButton("Chestplate", InventoryType.CHESTPLATE), 0.45f, 0.5f, 0.55f, 0.6f);
		addComponent(new EquipmentButton("LeftGlobe", InventoryType.GLOBE), 0.35f, 0.5f, 0.45f, 0.6f);
		addComponent(new EquipmentButton("RightGlobe", InventoryType.GLOBE), 0.55f, 0.5f, 0.65f, 0.6f);
		addComponent(new EquipmentButton("LeftWeapon", InventoryType.WEAPON), 0.35f, 0.4f, 0.45f, 0.5f);
		addComponent(new EquipmentButton("RightWeapon", InventoryType.WEAPON), 0.55f, 0.4f, 0.65f, 0.5f);
		addComponent(new EquipmentButton("Pants", InventoryType.PANTS), 0.45f, 0.3f, 0.55f, 0.4f);
		addComponent(new EquipmentButton("LeftShoe", InventoryType.BOOTS), 0.35f, 0.2f, 0.45f, 0.3f);
		addComponent(new EquipmentButton("RightShoe", InventoryType.BOOTS), 0.55f, 0.2f, 0.65f, 0.3f);
	}
	
	private Player getPlayer(){
		return Game.getGameMenu().getPlayersMenu().player;
	}
	
	private void openItemSelection(EquipmentButton button){
		equipmentSelect.setComponent(new ComponentInventory(Game.getPlayerInventory(), Game.getPlayerInventory().getItems((Item item) -> {
			return button.canHaveItem(item);
		}), (Item item) -> {
			Item old = button.getItem();
			if (old != null)
				Game.getPlayerInventory().addItem(old);
			button.setItem(item);
			Game.getPlayerInventory().removeItem(item);
			closeItemSelection();
		}));
		equipmentEmpty.activeEquipment = button;
	}
	
	private void closeItemSelection(){
		equipmentEmpty.activeEquipment = null;
		equipmentSelect.setComponent(null);
	}
	
	private static final GuiColor EMPTY_COLOR = new SimpleGuiColor(200, 100, 0);
	
	private class EquipmentButton extends AbstractGuiComponent {
		
		private GuiTexture texture;
		private boolean canHaveItem;
		
		private final InventoryType inventoryType;
		private final String slot;
		
		private EquipmentButton(String slot, InventoryType type){
			this.slot = slot;
			inventoryType = type;
		}
		
		@Override
		public void init(){
			updateItem();
		}

		@Override
		public void click(float x, float y, int button) {
			if(canHaveItem && button == MouseCode.BUTTON_LEFT)
				openItemSelection(this);
		}
		
		@Override
		public void render(GuiRenderer renderer){
			if(canHaveItem) {
				if(texture != null)
					renderer.renderTexture(texture, 0, 0, 1, 1);
				else
					renderer.fill(EMPTY_COLOR, 0, 0, 1, 1);
			}
		}
		
		private Item getItem(){
			try {
				return (Item) getPlayer().getEquipment().getClass().getMethod("get" + slot).invoke(getPlayer().getEquipment());
			} catch(Exception ex){
				throw new RuntimeException("Can't get item of slot " + slot, ex);
			}
		}
		
		private boolean canHaveItem() {
			try {
				return (Boolean) getPlayer().getEquipment().getClass().getMethod("canEquip" + slot).invoke(getPlayer().getEquipment());
			} catch(Exception ex){
				throw new RuntimeException("Can't check if slot " + slot + " accepts items", ex);
			}
		}
		
		private boolean canHaveItem(Item item) {
			if(item.getType() == inventoryType) {
				try {
					Class<?> eqClass = getPlayer().getEquipment().getClass();
					return (Boolean) eqClass.getMethod("canEquip" + slot).invoke(getPlayer().getEquipment()) && (Boolean) eqClass.getMethod("canEquip" + slot, Item.class).invoke(getPlayer().getEquipment(), item);
				} catch(Exception ex){
					throw new RuntimeException("Can't check if slot " + slot + " accepts item " + item, ex);
				}
				}
			return false;
		}
		
		private void updateItem(){
			canHaveItem = canHaveItem();
			if(canHaveItem)
				updateItem(getItem());
			else
				texture = null;
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
				closeItemSelection();
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
	
	private class EquipmentEmptyButton extends ConditionalTextButton {
		
		private EquipmentButton activeEquipment;

		public EquipmentEmptyButton() {
			super("Empty slot", BUTTON_PROPS, HOVER_PROPS, null, null);
			clickAction = () -> {
				activeEquipment.setItem(null);
			};
			condition = () -> {
				return activeEquipment != null;
			};
		}
	}
}