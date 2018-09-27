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
import nl.knokko.main.Game;
import nl.knokko.util.color.Color;

public class GuiGameMenu extends GuiMenu {
	
	private static final Color BUTTON_COLOR = new Color(0, 50, 200);
	private static final Color BORDER_COLOR = new Color(0, 20, 50);
	
	private final StateGameMenu state;

	public GuiGameMenu(StateGameMenu menu) {
		state = menu;
	}
	
	@Override
	protected void addComponents(){
		addButton(new ButtonCloseMenu(new Vector2f(0.65f, -0.6f), new Vector2f(0.3f, 0.1f), BUTTON_COLOR, BORDER_COLOR));
		addButton(new ButtonText(new Vector2f(0.65f, -0.85f), new Vector2f(0.3f, 0.1f), BUTTON_COLOR, BORDER_COLOR, Color.BLACK, "Save and stop"){

			@Override
			public void leftClick(float x, float y) {
				Game.stop(true);
			}
		});
		addButton(new ButtonLink(new Vector2f(-0.65f, 0.85f), new Vector2f(0.3f, 0.1f), BUTTON_COLOR, BORDER_COLOR, Color.BLACK, "Inventory", state.getInventory(), state));
		addButton(new ButtonLink(new Vector2f(-0.65f, 0.6f), new Vector2f(0.3f, 0.1f), BUTTON_COLOR, BORDER_COLOR, Color.BLACK, "Characters", state.getPlayersMenu(), state));
	}
	
	@Override
	public Color getBackgroundColor(){
		return null;
	}
}
