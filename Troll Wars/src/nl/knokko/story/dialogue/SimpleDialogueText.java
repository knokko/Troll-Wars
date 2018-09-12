package nl.knokko.story.dialogue;

import java.awt.Font;

import nl.knokko.util.color.Color;

public class SimpleDialogueText implements DialogueText {
	
	public static final Font DEFAULT_FONT = new Font("TimesRoman", 0, 30);
	public static final Color DEFAULT_COLOR = Color.BLACK;
	
	protected final String text;
	protected final Color color;
	protected final Font font;

	public SimpleDialogueText(String text, Color color, Font font) {
		this.text = text;
		this.color = color;
		this.font = font;
	}
	
	public SimpleDialogueText(String text, Font font){
		this(text, DEFAULT_COLOR, font);
	}
	
	public SimpleDialogueText(String text, Color color){
		this(text, color, DEFAULT_FONT);
	}
	
	public SimpleDialogueText(String text){
		this(text, DEFAULT_COLOR, DEFAULT_FONT);
	}

	@Override
	public Color getColor() {
		return color;
	}
	
	@Override
	public Font getFont(){
		return font;
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public void onClick() {}
}
