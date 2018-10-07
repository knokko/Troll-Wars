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
import nl.knokko.gui.component.GuiComponent;
import nl.knokko.gui.component.menu.GuiMenu;
import nl.knokko.gui.component.text.TextButton;
import nl.knokko.gui.util.TextBuilder.Properties;
import nl.knokko.main.Game;
import nl.knokko.players.Player;

public class GuiPlayerMenu extends GuiMenu {
	
	private static final Properties BUTTON_PROPS = Properties.createButton(new Color(0, 150, 100), new Color(0, 50, 40));
	private static final Properties HOVER_PROPS = Properties.createButton(new Color(0, 200, 120), new Color(0, 65, 50));
	
	private final StateGameMenu state;
	
	Player player;

	public GuiPlayerMenu(StateGameMenu state) {
		this.state = state;
		this.player = Game.getPlayers().gothrok;
	}
	
	@Override
	protected void addComponents(){
		addComponent(new ButtonLink("Equipment", state, state.getPlayerEquipment(), BUTTON_PROPS, HOVER_PROPS), 0.05f, 0.85f, 0.25f, 0.95f);
		addComponent(new ButtonSwapPlayer(BUTTON_PROPS, HOVER_PROPS, this), 0.05f, 0.45f, 0.25f, 0.55f);
		addComponent(new ButtonLink("Back to menu", state, state.getGameMenu(), BUTTON_PROPS, HOVER_PROPS), 0.05f, 0.3f, 0.25f, 0.4f);
		addComponent(new ButtonCloseMenu(BUTTON_PROPS, HOVER_PROPS), 0.05f, 0.1f, 0.25f, 0.2f);
	}
	
	void setPlayer(Player newPlayer){
		player = newPlayer;
	}
	
	void swapPlayer(){
		Player p = Game.getPlayers().nextPlayer(player);
		if(p != player)
			setPlayer(p);
	}
	
	public static class ButtonSwapPlayer extends TextButton {
		
		public ButtonSwapPlayer(Properties properties, Properties hoverProperties, GuiComponent playersMenu){
			super("Next Character", properties, hoverProperties, new ClickAction((GuiPlayerMenu) playersMenu));
		}
		
		private static class ClickAction implements Runnable {
			
			private final GuiPlayerMenu gui;
			
			private ClickAction(GuiPlayerMenu playersMenu){
				gui = playersMenu;
			}

			@Override
			public void run() {
				gui.swapPlayer();
			}
		}
	}
}
