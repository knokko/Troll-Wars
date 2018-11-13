/*******************************************************************************
 * The MIT License
 *
 * Copyright (c) 2018 knokko
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

import java.awt.Color;

import nl.knokko.gamestate.StateMainMenu;
import nl.knokko.gui.button.ButtonLink;
import nl.knokko.gui.component.menu.GuiMenu;
import nl.knokko.gui.component.text.TextButton;
import nl.knokko.gui.component.text.TextComponent;
import nl.knokko.gui.component.text.TextEditField;
import nl.knokko.gui.util.TextBuilder.Properties;
import nl.knokko.main.Game;
import nl.knokko.util.resources.Saver;

public class GuiNewGame extends GuiMenu {
	
	private static final Properties BUTTON_PROPS = Properties.createButton(new Color(150, 0, 150), new Color(50, 0, 50));
	private static final Properties HOVER_PROPS = Properties.createButton(new Color(250, 0, 250), new Color(80, 0, 80), new Color(50, 0, 50));
	
	private static final Properties EDIT_PROPS = Properties.createEdit();
	private static final Properties ACTIVE_PROPS = Properties.createEdit(Color.YELLOW);
	
	private static final Properties ERROR_PROPS = Properties.createLabel(Color.RED);
	
	private final StateMainMenu menu;
	private TextEditField nameButton;
	private TextComponent errorText;
	
	public GuiNewGame(StateMainMenu menu){
		this.menu = menu;
	}
	
	@Override
	protected void addComponents(){
		addComponent(new ButtonLink("back", menu, menu.getGuiMainMenu(), BUTTON_PROPS, HOVER_PROPS), 0.35f, 0.65f, 0.65f, 0.75f);
		nameButton = new TextEditField("", EDIT_PROPS, ACTIVE_PROPS);
		addComponent(nameButton, 0.1f, 0.4f, 0.9f, 0.6f);
		addComponent(new TextButton("start the game", BUTTON_PROPS, HOVER_PROPS, new Runnable(){

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
		errorText = new TextComponent("", ERROR_PROPS);
		addComponent(errorText, 0.025f, 0.025f, 0.975f, 0.175f);
	}
}
