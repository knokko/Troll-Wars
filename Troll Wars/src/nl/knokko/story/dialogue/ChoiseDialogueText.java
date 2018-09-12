package nl.knokko.story.dialogue;

import java.awt.Font;

import nl.knokko.util.color.Color;

public class ChoiseDialogueText extends SimpleDialogueText {
	
	public static final Color DEFAULT_HOLD_COLOR = Color.YELLOW;
	
	protected final Color holdColor;
	protected Runnable action;

	public ChoiseDialogueText(String text, Color color, Color holdColor, Font font) {
		super(text, color, font);
		this.holdColor = holdColor;
	}

	public ChoiseDialogueText(String text, Font font) {
		this(text, DEFAULT_COLOR, DEFAULT_HOLD_COLOR, font);
	}

	public ChoiseDialogueText(String text, Color color) {
		this(text, color, DEFAULT_HOLD_COLOR, DEFAULT_FONT);
	}

	public ChoiseDialogueText(String text) {
		this(text, DEFAULT_COLOR, DEFAULT_HOLD_COLOR, DEFAULT_FONT);
	}
	
	@Override
	public void onClick(){
		if(action == null)
			throw new IllegalStateException("No action has been defined for the text: " + text);
		action.run();
	}
	
	public void setAction(Runnable action){
		this.action = action;
	}
}
