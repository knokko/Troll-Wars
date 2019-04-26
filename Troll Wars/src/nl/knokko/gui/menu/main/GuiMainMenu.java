/*******************************************************************************
 * The MIT License
 *
 * Copyright (c) 2019 knokko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
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
 *******************************************************************************/
package nl.knokko.gui.menu.main;

import nl.knokko.gamestate.StateMainMenu;
import nl.knokko.gui.button.ButtonLink;
import nl.knokko.gui.button.ButtonProps;
import nl.knokko.gui.color.GuiColor;
import nl.knokko.gui.color.SimpleGuiColor;
import nl.knokko.gui.component.menu.GuiMenu;
import nl.knokko.gui.component.text.TextButton;
import nl.knokko.gui.component.text.TextComponent;
import nl.knokko.gui.util.TextBuilder.Properties;
import nl.knokko.main.Game;

public class GuiMainMenu extends GuiMenu {
	
	//private static final Properties BUTTON_PROPERTIES = Properties.createButton(new Color(150, 0, 150), new Color(50, 0, 50));
	//private static final Properties HOVER_PROPERTIES = Properties.createButton(new Color(250, 0, 250), new Color(80, 0, 80), new Color(50, 0, 50));
	private static final Properties BUTTON_PROPERTIES = ButtonProps.MAIN_MENU;
	private static final Properties HOVER_PROPERTIES = ButtonProps.MAIN_MENU_HOVER;
	
	public static final GuiColor BACKGROUND = new SimpleGuiColor(0, 150, 200);
	
	private final StateMainMenu menu;

	public GuiMainMenu(StateMainMenu menu) {
		this.menu = menu;
	}
	
	@Override
	public GuiColor getBackgroundColor() {
		return BACKGROUND;
	}
	
	private static final float OFFSET_Y = 0.10f;
	
	@Override
	protected void addComponents(){
		addComponent(new TextComponent("Troll Wars", ButtonProps.MAIN_MENU_TITLE), 0.3f, 0.75f, 0.7f, 0.95f);
		addComponent(new ButtonLink("new game", menu, menu.getGuiNewGame(), BUTTON_PROPERTIES, HOVER_PROPERTIES), 0.35f, OFFSET_Y + 0.5f, 0.65f, OFFSET_Y + 0.6f);
		addComponent(new ButtonLink("load game", menu, menu.getGuiLoadGame(), BUTTON_PROPERTIES, HOVER_PROPERTIES){
			
			@Override
			public void click(float x, float y, int button){
				super.click(x, y, button);
				((GuiLoadGame) menu.getGuiLoadGame()).refresh();
			}
		}, 0.35f, OFFSET_Y + 0.375f, 0.65f, OFFSET_Y + 0.475f);
		addComponent(new ButtonLink("options", menu, menu.getGuiOptions(), BUTTON_PROPERTIES, HOVER_PROPERTIES), 0.35f, OFFSET_Y + 0.2f, 0.65f, OFFSET_Y + 0.3f);
		addComponent(new TextButton("quit game", BUTTON_PROPERTIES, HOVER_PROPERTIES, new Runnable(){

			@Override
			public void run() {
				Game.stop(false);
			}
		}), 0.35f, OFFSET_Y, 0.65f, OFFSET_Y + 0.1f);
	}
}
