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
import nl.knokko.gui.component.menu.GuiMenu;
import nl.knokko.gui.component.text.TextButton;
import nl.knokko.gui.util.TextBuilder.Properties;
import nl.knokko.main.Game;


public class GuiGameMenu extends GuiMenu {
    
    private static final Properties BUTTON_PROPERTIES = Properties.createButton(new Color(0, 50, 200), new Color(0, 20, 50));
    private static final Properties HOVER_PROPERTIES = Properties.createButton(new Color(0, 60, 250), new Color(0, 30, 70));
	
	private final StateGameMenu state;

	public GuiGameMenu(StateGameMenu menu) {
		state = menu;
	}
	
	@Override
	protected void addComponents(){
        addComponent(new ButtonCloseMenu(BUTTON_PROPERTIES, HOVER_PROPERTIES), 0.675f, 0.15f, 0.975f, 0.25f);
        addComponent(new TextButton("Save and stop", BUTTON_PROPERTIES, HOVER_PROPERTIES, () -> Game.stop(true)), 0.675f, 0.025f, 0.975f, 0.125f);
        addComponent(new ButtonLink("Inventory", state, state.getInventory(), BUTTON_PROPERTIES, HOVER_PROPERTIES), 0.025f, 0.875f, 0.325f, 0.975f);
        addComponent(new ButtonLink("Characters", state, state.getPlayersMenu(), BUTTON_PROPERTIES, HOVER_PROPERTIES), 0.025f, 0.75f, 0.325f, 0.85f);
	}
	
	@Override
	public GuiColor getBackgroundColor(){
		return null;
	}
}