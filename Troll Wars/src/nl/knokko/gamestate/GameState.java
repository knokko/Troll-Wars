package nl.knokko.gamestate;

import nl.knokko.gui.Gui;

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
	void setCurrentGui(Gui gui);
}
