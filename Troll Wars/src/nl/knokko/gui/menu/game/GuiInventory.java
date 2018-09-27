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

import org.lwjgl.util.vector.Vector2f;

import nl.knokko.gamestate.StateGameMenu;
import nl.knokko.gui.button.ButtonCloseMenu;
import nl.knokko.gui.button.ButtonLink;
import nl.knokko.gui.component.menu.GuiMenu;
import nl.knokko.gui.render.GuiRenderer;
import nl.knokko.inventory.InventoryTypeBase;
import nl.knokko.main.Game;
import nl.knokko.texture.SizedTexture;
import nl.knokko.util.color.Color;
import nl.knokko.util.resources.Resources;

public class GuiInventory extends GuiMenu {
	
	private static final Color BUTTON_COLOR = new Color(100, 150, 0);
	private static final Color BORDER_COLOR = new Color(40, 50, 0);
	private static final Color BACKGROUND_COLOR = new Color(150, 100, 0);
	
	private final StateGameMenu state;
	
	private SizedTexture materialsTexture;
	private SizedTexture equipmentTexture;
	private SizedTexture consumablesTexture;
	
	private boolean hasTextures;

	public GuiInventory(StateGameMenu menu) {
		state = menu;
	}
	
	@Override
	protected void addComponents(){
		/*
		addButton(new InventoryTypeButton(new Vector2f(-0.65f, 0.7f), null));
		addButton(new InventoryTypeButton(new Vector2f(-0.65f, 0.45f), InventoryTypeBase.MATERIAL));
		addButton(new InventoryTypeButton(new Vector2f(-0.65f, 0.2f), InventoryTypeBase.EQUIPMENT));
		addButton(new InventoryTypeButton(new Vector2f(-0.65f, -0.05f), InventoryTypeBase.CONSUMABLE));
		*/
		addButton(new ButtonLink(new Vector2f(-0.65f, -0.6f), new Vector2f(0.3f, 0.1f), BUTTON_COLOR, BORDER_COLOR, Color.BLACK, "Back to menu", state.getGameMenu(), state));
		addButton(new ButtonCloseMenu(new Vector2f(-0.65f, -0.85f), new Vector2f(0.3f, 0.1f), BUTTON_COLOR, BORDER_COLOR));
	}
	
	@Override
	public void render(GuiRenderer renderer){
		renderer.render(textures, buttons, this, false);
		if(!hasTextures){
			hasTextures = true;
			materialsTexture = Resources.createInventoryTexture(Game.getPlayerInventory(), Game.getPlayerInventory().getItems(InventoryTypeBase.MATERIAL));
			equipmentTexture = Resources.createInventoryTexture(Game.getPlayerInventory(), Game.getPlayerInventory().getItems(InventoryTypeBase.EQUIPMENT));
			consumablesTexture = Resources.createInventoryTexture(Game.getPlayerInventory(), Game.getPlayerInventory().getItems(InventoryTypeBase.CONSUMABLE));
		}
		if(materialsTexture != null)
			renderer.renderTextures(new Vector2f(-0.1f, 1 - materialsTexture.getRelativeHeight()), new Vector2f(0.2f, materialsTexture.getRelativeHeight()), materialsTexture);
		if(equipmentTexture != null)
			renderer.renderTextures(new Vector2f(0.35f, 1 - equipmentTexture.getRelativeHeight()), new Vector2f(0.2f, equipmentTexture.getRelativeHeight()), equipmentTexture);
		if(consumablesTexture != null)
			renderer.renderTextures(new Vector2f(0.8f, 1 - consumablesTexture.getRelativeHeight()), new Vector2f(0.2f, consumablesTexture.getRelativeHeight()), consumablesTexture);
	}
	
	@Override
	public Color getBackgroundColor(){
		return BACKGROUND_COLOR;
	}
	
	/*
	private class InventoryTypeButton extends ButtonText {
		
		private SizedTexture texture;
		private InventoryTypeBase type;

		public InventoryTypeButton(Vector2f centre, InventoryTypeBase type) {
			super(centre, new Vector2f(0.3f, 0.1f), BUTTON_COLOR, BORDER_COLOR, Color.BLACK, type != null ? type.name().toLowerCase() : "all items");
			this.type = type;
		}

		@Override
		public void leftClick(float x, float y) {
			if(texture == null)
				texture = Resources.createInventoryTexture(Game.getPlayerInventory(), type != null ? Game.getPlayerInventory().getItems(type) : Game.getPlayerInventory().getItems());
			currentTexture = texture;
		}
		
	}
	*/
}
