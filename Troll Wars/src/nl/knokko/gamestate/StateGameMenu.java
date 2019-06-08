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
package nl.knokko.gamestate;

import nl.knokko.gui.component.GuiComponent;
import nl.knokko.gui.keycode.KeyCode;
import nl.knokko.gui.menu.game.*;
import nl.knokko.main.Game;

public class StateGameMenu implements GameState {
	
	private final GuiComponent[] guis;
	
	private GuiComponent currentGui;

	public StateGameMenu() {
		guis = new GuiComponent[]{new GuiGameMenu(this), new GuiInventory(this), new GuiPlayerMenu(this), new GuiPlayerEquipment(this)};
	}

	@Override
	public void update() {}

	@Override
	public void render() {}

	@Override
	public void open() {
		setCurrentGui(getGameMenu());
	}

	@Override
	public void close() {}

	@Override
	public void enable() {}

	@Override
	public void disable() {}

	@Override
	public boolean renderTransparent() {
		return true;
	}

	@Override
	public boolean updateTransparent() {
		return true;
	}

	@Override
	public void setCurrentGui(GuiComponent gui) {
		currentGui = gui;
		gui.setState(Game.getGuiState());
		gui.init();
		Game.getWindow().markChange();
	}
	
	@Override
	public GuiComponent getCurrentGui(){
		return currentGui;
	}
	
	public GuiComponent getGameMenu(){
		return guis[0];
	}
	
	public GuiComponent getInventory(){
		return guis[1];
	}
	
	public GuiPlayerMenu getPlayersMenu(){
		return (GuiPlayerMenu) guis[2];
	}
	
	public GuiComponent getPlayerEquipment(){
		return guis[3];
	}

	@Override
	public void save() {}
	
	@Override
	public void keyPressed(int keyCode) {
		if(keyCode == KeyCode.KEY_ESCAPE)
			Game.removeState();
	}
}
