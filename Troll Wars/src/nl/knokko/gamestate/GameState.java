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
package nl.knokko.gamestate;

import nl.knokko.gui.component.GuiComponent;

public interface GameState {
	
	/**
	 * Update this GameState
	 */
	void update();
	
	/**
	 * Render this GameState
	 */
	void render();
	
	/**
	 * Open this GameState after it has not been on the currentStates
	 */
	void open();
	
	/**
	 * Close this GameState and remove it from the currentStates
	 */
	void close();
	
	/**
	 * Indicate that this GameState has become the top state
	 * Do not call this method if this GameState has just been opened.
	 */
	void enable();
	
	/**
	 * Indicate that this GameState is no longer the top state
	 */
	void disable();
	
	void save();
	
	/**
	 * @return true if the underlying states should be rendererd as well, false if not
	 */
	boolean renderTransparent();
	
	/**
	 * @return true if the underlying states should update as well, false if not
	 */
	boolean updateTransparent();
	
	/**
	 * Set the current gui for the state (Main) Menu.
	 * Other states should ignore this method.
	 * @param gui
	 */
	void setCurrentGui(GuiComponent gui);
	
	GuiComponent getCurrentGui();
	
	void keyPressed(int keyCode);
}
