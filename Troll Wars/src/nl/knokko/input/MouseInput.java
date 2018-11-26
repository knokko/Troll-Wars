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
package nl.knokko.input;

import java.util.ArrayList;

import nl.knokko.gui.component.state.GuiComponentState;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public final class MouseInput {

	// private static byte clickCooldown;

	private static final ArrayList<MouseMoveEvent> MOVES = new ArrayList<MouseMoveEvent>();
	private static final ArrayList<MouseClickEvent> CLICKS = new ArrayList<MouseClickEvent>();
	private static final ArrayList<MouseScrollEvent> SCROLLS = new ArrayList<MouseScrollEvent>();
	
	private static GuiComponentState guiState;
	
	public static void setGuiState(GuiComponentState state) {
		guiState = state;
	}

	public static void update() {
		MOVES.clear();
		CLICKS.clear();
		SCROLLS.clear();
		float dx = guiState.getMouseDX();
		float dy = guiState.getMouseDY();
		if (dx != 0 || dy != 0)
			MOVES.add(new MouseMoveEvent(guiState.getMouseX(), guiState.getMouseX(), dx, dy));
	}

	public static ArrayList<MouseMoveEvent> getMouseMoves() {
		return MOVES;
	}

	public static ArrayList<MouseClickEvent> getMouseClicks() {
		return CLICKS;
	}

	public static ArrayList<MouseScrollEvent> getMouseScrolls() {
		return SCROLLS;
	}

	public static void addScroll(float amount) {
		SCROLLS.add(new MouseScrollEvent(amount));
	}

	public void addClick(float x, float y, int button) {
		CLICKS.add(new MouseClickEvent(x, y, button));
	}

	public static int getCurrentX() {
		return Mouse.getX();
	}

	public static int getCurrentY() {
		return Mouse.getY();
	}

	public static float getRelativeX() {
		return getRelativeX(getCurrentX());
	}

	public static float getRelativeX(int screenX) {
		return (float) screenX * 2f / Display.getWidth() - 1f;
	}

	public static float getRelativeY() {
		return getRelativeY(getCurrentY());
	}

	public static float getRelativeY(int screenY) {
		return (float) screenY * 2f / Display.getHeight() - 1f;
	}

	public static float getX() {
		return getRelativeX(getCurrentX());
	}

	public static float getY() {
		return getRelativeY(getCurrentY());
	}
}
