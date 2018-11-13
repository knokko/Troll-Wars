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
package nl.knokko.input;

import java.util.ArrayList;
import nl.knokko.gui.keycode.KeyCode;

public final class KeyInput {

	private static ArrayList<KeyPressedEvent> presses = new ArrayList<KeyPressedEvent>();
	private static ArrayList<KeyPressedCharEvent> charPresses = new ArrayList<KeyPressedCharEvent>();
	private static ArrayList<KeyReleasedEvent> releases = new ArrayList<KeyReleasedEvent>();

	private static boolean[] pressedKeys = new boolean[KeyCode.AMOUNT];

	public static void update() {
		presses.clear();
		charPresses.clear();
		releases.clear();
	}

	public static void addPress(int keyCode) {
		presses.add(new KeyPressedEvent(keyCode));
		pressedKeys[keyCode] = true;
	}

	public static void addPress(char character) {
		charPresses.add(new KeyPressedCharEvent(character));
	}

	public static void addRelease(int keyCode) {
		releases.add(new KeyReleasedEvent(keyCode));
		pressedKeys[keyCode] = false;
	}

	/**
	 * @return an ArrayList that contains all keys that were pressed during the
	 *         current tick
	 */
	public static ArrayList<KeyPressedEvent> getCurrentPresses() {
		return presses;
	}

	public static ArrayList<KeyPressedCharEvent> getCurrentCharPresses() {
		return charPresses;
	}

	/**
	 * @return an ArrayList that contains all keys that were released during the
	 *         current tick
	 */
	public static ArrayList<KeyReleasedEvent> getCurrentReleases() {
		return releases;
	}

	/**
	 * Checks if the specified key has been pressed during this tick.
	 * 
	 * @param key The key code to check for
	 * @return True if the key was pressed during this tick, false otherwise
	 * @see nl.knokko.gui.keycode.KeyCode
	 */
	public static boolean wasKeyPressed(int key) {
		for (KeyPressedEvent event : presses)
			if (event.getKey() == key)
				return true;
		return false;
	}

	public static boolean isKeyDown(int key) {
		return pressedKeys[key];
	}
}
