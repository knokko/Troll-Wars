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
import nl.knokko.gui.menu.main.*;
import nl.knokko.main.Game;

public class StateMainMenu implements GameState {
	
	private final GuiComponent[] guis;
	
	private GuiComponent currentGui;

	public StateMainMenu() {
		guis = new GuiComponent[]{new GuiMainMenu(this), new GuiNewGame(this), new GuiLoadGame(this), new GuiMainOptions(this)};
		for(GuiComponent gui : guis){
			gui.setState(Game.getGuiState());
			gui.init();
		}
		currentGui = getGuiMainMenu();
	}

	@Override
	public void update() {
		
	}

	@Override
	public void render() {
		
	}

	@Override
	public void open() {}

	@Override
	public void close() {}

	@Override
	public void enable() {}

	@Override
	public void disable() {}

	@Override
	public boolean renderTransparent() {
		return false;
	}

	@Override
	public boolean updateTransparent() {
		return false;
	}
	
	@Override
	public void setCurrentGui(GuiComponent gui){
		currentGui = gui;
		Game.getWindow().markChange();
	}
	
	@Override
	public GuiComponent getCurrentGui(){
		return currentGui;
	}
	
	public GuiComponent getGuiMainMenu(){
		return guis[0];
	}
	
	public GuiComponent getGuiNewGame(){
		return guis[1];
	}
	
	public GuiComponent getGuiLoadGame(){
		return guis[2];
	}
	
	public GuiComponent getGuiOptions(){
		return guis[3];
	}

	@Override
	public void save() {}

	@Override
	public void keyPressed(int keyCode) {}
}
