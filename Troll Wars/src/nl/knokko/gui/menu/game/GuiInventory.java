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
import nl.knokko.gui.component.GuiComponent;
import nl.knokko.gui.component.WrapperComponent;
import nl.knokko.gui.component.inventory.ComponentInventory;
import nl.knokko.gui.component.menu.GuiMenu;
import nl.knokko.gui.component.simple.SimpleColorComponent;
import nl.knokko.gui.component.text.ActivatableTextButton;
import nl.knokko.gui.component.text.CondivatableTextButton;
import nl.knokko.gui.util.TextBuilder.Properties;
import nl.knokko.inventory.InventoryType;
import nl.knokko.inventory.InventoryTypeBase;
import nl.knokko.main.Game;

public class GuiInventory extends GuiMenu {
	
	private static final Properties BUTTON_PROPS = Properties.createButton(new Color(100, 150, 0), new Color(40, 50, 0));
	private static final Properties HOVER_PROPS = Properties.createButton(new Color(125, 200, 0), new Color(50, 65, 0));
	private static final Properties ACTIVE_PROPS = Properties.createButton(new Color(150, 250, 0), new Color(65, 80, 0));
	
	private static final GuiColor BACKGROUND_COLOR = new SimpleGuiColor(150, 100, 0);
	
	private final StateGameMenu state;
    
    private InventoryType selectedType;
    private InventoryTypeBase selectedBaseType;

	public GuiInventory(StateGameMenu menu) {
		state = menu;
	}
	
	@Override
	protected void addComponents(){
		addComponent(new ButtonLink("Back to menu", state, state.getGameMenu(), BUTTON_PROPS, HOVER_PROPS), 0.025f, 0.7f, 0.25f, 0.8f);
		addComponent(new ButtonCloseMenu(BUTTON_PROPS, HOVER_PROPS), 0.025f, 0.5f, 0.25f, 0.6f);
		
		addComponent(new SimpleColorComponent(new SimpleGuiColor(0, 150, 250)), 0.275f, 0f, 1f, 1f);
		
		// Base type buttons
		addBaseTypeButton("All items", null, 0.7f);
		addBaseTypeButton(InventoryTypeBase.MATERIAL, 0.55f);
		addBaseTypeButton(InventoryTypeBase.EQUIPMENT, 0.4f);
		addBaseTypeButton(InventoryTypeBase.CONSUMABLE, 0.25f);
		
		// Materials
		addTypeButton(InventoryType.MONEY, 0.7f);
		addTypeButton(InventoryType.METAL, 0.575f);
		addTypeButton(InventoryType.GEM, 0.45f);
		addTypeButton(InventoryType.ORGANIC, 0.325f);
		
		// Equipment
		addTypeButton(InventoryType.WEAPON, 0.85f);
		addTypeButton(InventoryType.HELMET, 0.725f);
		addTypeButton(InventoryType.CHESTPLATE, 0.6f);
		addTypeButton(InventoryType.PANTS, 0.475f);
		addTypeButton(InventoryType.BOOTS, 0.35f);
		addTypeButton(InventoryType.GLOBE, 0.225f);
		
		// Consumables
		addTypeButton(InventoryType.DRINK, 0.7f);
		addTypeButton(InventoryType.FOOD, 0.5f);
		
		// Inventory wrapper components
		addWrapper(new InventoryBaseTypeWrapper(null));
		InventoryTypeBase[] baseValues = InventoryTypeBase.values();
		for(InventoryTypeBase base : baseValues)
			addWrapper(new InventoryBaseTypeWrapper(base));
		
		InventoryType[] values = InventoryType.values();
		for(InventoryType type : values)
			addWrapper(new InventoryTypeWrapper(type));
	}
	
	private void addWrapper(GuiComponent component) {
		addComponent(component, 0.74f, 0.05f, 0.985f, 0.95f);
	}
	
	private void addTypeButton(InventoryType type, float minY) {
		addComponent(new CondivatableTextButton(type.toString(), BUTTON_PROPS, HOVER_PROPS, ACTIVE_PROPS, () -> {
			selectedType = type;
		}, () -> {
			return selectedBaseType == type.getParent();
		}, () -> {
			return selectedType == type;
		}), 0.525f, minY, 0.725f, minY + 0.1f);
	}
	
	private void addBaseTypeButton(String text, InventoryTypeBase type, float minY) {
		addComponent(new ActivatableTextButton(text, BUTTON_PROPS, HOVER_PROPS, ACTIVE_PROPS, () -> {
			selectedBaseType = type;
			selectedType = null;
		}, () -> {
			return selectedBaseType == type;
		}), 0.3f, minY, 0.5f, minY + 0.1f);
	}
	
	private void addBaseTypeButton(InventoryTypeBase type, float minY) {
		addBaseTypeButton(type.toString(), type, minY);
	}
	
	@Override
	public GuiColor getBackgroundColor(){
		return BACKGROUND_COLOR;
	}
    
    private class InventoryTypeWrapper extends WrapperComponent<ComponentInventory> {
        
        private final InventoryType type;
        
        public InventoryTypeWrapper(InventoryType type) {
            super(new ComponentInventory(Game.getPlayerInventory(), Game.getPlayerInventory().getItems(type), null));
            this.type = type;
        }
        
        @Override
        public boolean isActive(){
            return selectedType == type;
        }
    }
    
    private class InventoryBaseTypeWrapper extends WrapperComponent<ComponentInventory> {
        
        private final InventoryTypeBase type;
        
        public InventoryBaseTypeWrapper(InventoryTypeBase type) {
            super(new ComponentInventory(Game.getPlayerInventory(), type != null ? Game.getPlayerInventory().getItems(type) : Game.getPlayerInventory().getItems(), null));
            this.type = type;
        }
        
        @Override
        public boolean isActive(){
            return selectedBaseType == type;
        }
    }
}