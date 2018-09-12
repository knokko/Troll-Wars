package nl.knokko.story.dialogue;

import nl.knokko.texture.ImageTexture;
import nl.knokko.util.color.Color;

public class SimpleDialoguePart extends DialoguePart {
	
	public static final Color DEFAULT_BACKGROUND_COLOR = new Color(62, 0, 89);
	
	protected final Color backGround;
	
	protected final ImageTexture portrait;
	
	protected final DialogueText[] text;
	
	protected final SimpleDialogueText title;
	
	protected Runnable next;

	public SimpleDialoguePart(Dialogue dialogue, Color backGround, ImageTexture portrait, SimpleDialogueText title, DialogueText text) {
		super(dialogue);
		this.backGround = backGround;
		this.portrait = portrait;
		this.text = new DialogueText[]{text};
		this.title = title;
	}
	
	/*
	public SimpleDialoguePart(Dialogue dialogue, Color backGround, ImageTexture portrait, SimpleDialogueText title, String text, Color textColor){
		this(dialogue, backGround, portrait, title, new SimpleDialogueText(text, textColor));
	}
	
	public SimpleDialoguePart(Dialogue dialogue, ImageTexture portrait, SimpleDialogueText title, DialogueText text){
		this(dialogue, DEFAULT_BACKGROUND_COLOR, portrait, title, text);
	}
	
	public SimpleDialoguePart(Dialogue dialogue, ImageTexture portrait, SimpleDialogueText title, String text, Color textColor){
		this(dialogue, DEFAULT_BACKGROUND_COLOR, portrait, text, textColor);
	}
	
	public SimpleDialoguePart(Dialogue dialogue, ImageTexture portrait, String text){
		this(dialogue, portrait, new SimpleDialogueText(text));
	}
	*/

	@Override
	public Color getBackGround() {
		return backGround;
	}
	
	@Override
	public ImageTexture getPortrait(){
		return portrait;
	}

	@Override
	public DialogueText[] getText() {
		return text;
	}

	@Override
	public void next() {
		next.run();
	}
	
	public void setNext(Runnable next){
		this.next = next;
	}

	@Override
	public SimpleDialogueText getTitle() {
		return title;
	}
}
