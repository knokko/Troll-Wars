package nl.knokko.input;

import java.util.ArrayList;
import nl.knokko.gui.keycode.KeyCode;

public final class KeyInput {
	
	private static ArrayList<KeyPressedEvent> presses = new ArrayList<KeyPressedEvent>();
	private static ArrayList<KeyPressedCharEvent> charPresses = new ArrayList<KeyPressedCharEvent>();
	private static ArrayList<KeyReleasedEvent> releases = new ArrayList<KeyReleasedEvent>();
        
        private static boolean[] pressedKeys = new boolean[KeyCode.AMOUNT];
	
	public static void update(){
		presses.clear();
		charPresses.clear();
		releases.clear();
	}
	
	public static void addPress(int keyCode){
		presses.add(new KeyPressedEvent(keyCode));
                pressedKeys[keyCode] = true;
	}
	
	public static void addPress(char character){
		charPresses.add(new KeyPressedCharEvent(character));
	}
	
	public static void addRelease(int keyCode){
		releases.add(new KeyReleasedEvent(keyCode));
                pressedKeys[keyCode] = false;
	}
	
	/**
	 * @return an ArrayList that contains all keys that were pressed during the current tick
	 */
	public static ArrayList<KeyPressedEvent> getCurrentPresses(){
		return presses;
	}
	
	public static ArrayList<KeyPressedCharEvent> getCurrentCharPresses(){
		return charPresses;
	}
	
	/**
	 * @return an ArrayList that contains all keys that were released during the current tick
	 */
	public static ArrayList<KeyReleasedEvent> getCurrentReleases(){
		return releases;
	}
	
	/**
	 * Checks if the specified key has been pressed during this tick.
	 * @param key The key code to check for
	 * @return True if the key was pressed during this tick, false otherwise
	 * @see nl.knokko.gui.keycode.KeyCode
	 */
	public static boolean wasKeyPressed(int key){
		for(KeyPressedEvent event : presses)
			if(event.getKey() == key)
				return true;
		return false;
	}
        
        public static boolean isKeyDown(int key){
            return pressedKeys[key];
        }
}
