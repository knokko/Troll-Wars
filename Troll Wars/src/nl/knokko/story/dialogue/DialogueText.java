package nl.knokko.story.dialogue;

import java.awt.Font;

import nl.knokko.util.color.Color;

public interface DialogueText {
	
	Color getColor();
	
	Font getFont();
	
	/**
	 * This method should always return the same text; changing it won't have any effect.
	 * @return The text
	 */
	String getText();
	
	void onClick();
}
