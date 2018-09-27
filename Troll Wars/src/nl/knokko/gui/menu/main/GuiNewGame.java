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
package nl.knokko.gui.menu.main;

import java.awt.Color;
import java.awt.Font;

import nl.knokko.gamestate.StateMainMenu;
import nl.knokko.gui.button.ButtonLink;
import nl.knokko.gui.component.menu.GuiMenu;
import nl.knokko.gui.component.text.TextButton;
import nl.knokko.gui.component.text.TextComponent;
import nl.knokko.gui.component.text.TextEditField;
import nl.knokko.gui.util.TextBuilder.HorAlignment;
import nl.knokko.gui.util.TextBuilder.Properties;
import nl.knokko.gui.util.TextBuilder.VerAlignment;
import nl.knokko.main.Game;
import nl.knokko.util.resources.Saver;

public class GuiNewGame extends GuiMenu {
	
	public static final Properties BUTTON_PROPERTIES = new Properties(GuiMainMenu.FONT, Color.BLACK, new Color(150, 0, 150), new Color(50, 0, 50), HorAlignment.MIDDLE, VerAlignment.MIDDLE, 0.05f, 0.1f, 0.05f, 0.1f);
	public static final Properties HOVER_BUTTON_PROPERTIES = new Properties(GuiMainMenu.FONT, new Color(50, 50, 50), new Color(250, 0, 250), new Color(80, 0, 80), HorAlignment.MIDDLE, VerAlignment.MIDDLE, 0.05f, 0.1f, 0.05f, 0.1f);
	
	public static final Properties EDIT_PROPERTIES = new Properties(GuiMainMenu.FONT, Color.BLACK, new Color(200, 200, 200), new Color(50, 50, 50), HorAlignment.LEFT, VerAlignment.MIDDLE, 0.025f, 0.05f, 0.025f, 0.05f);
	public static final Properties ACTIVE_EDIT_PROPERTIES = new Properties(GuiMainMenu.FONT, Color.BLACK, Color.WHITE, new Color(0, 200, 200), HorAlignment.LEFT, VerAlignment.MIDDLE, 0.025f, 0.05f, 0.025f, 0.05f);
	
	public static final Properties ERROR_PROPERTIES = new Properties(new Font("", 1, 30), Color.RED, new Color(0, 0, 0, 0), new Color(0, 0, 0, 0), HorAlignment.MIDDLE, VerAlignment.MIDDLE, 0, 0, 0, 0);
	
	private final StateMainMenu menu;
	private TextEditField nameButton;
	private TextComponent errorText;
	
	public GuiNewGame(StateMainMenu menu){
		this.menu = menu;
	}
	
	@Override
	protected void addComponents(){
		addComponent(new ButtonLink("back", menu, menu.getGuiMainMenu(), BUTTON_PROPERTIES, HOVER_BUTTON_PROPERTIES), 0.35f, 0.65f, 0.65f, 0.75f);
		nameButton = new TextEditField("", EDIT_PROPERTIES, ACTIVE_EDIT_PROPERTIES);
		addComponent(nameButton, 0.1f, 0.4f, 0.9f, 0.6f);
		addComponent(new TextButton("start the game", BUTTON_PROPERTIES, HOVER_BUTTON_PROPERTIES, new Runnable(){

			@Override
			public void run() {
				String result = Saver.validateSaveName(nameButton.getText());
				if(result == null)
					Game.startNewGame(nameButton.getText());
				else {
					errorText.setText(result);
				}
			}
			
		}), 0.3f, 0.225f, 0.7f, 0.375f);
		errorText = new TextComponent("", ERROR_PROPERTIES);
		addComponent(errorText, 0.025f, 0.025f, 0.975f, 0.175f);
	}
}
