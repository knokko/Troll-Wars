package nl.knokko.story.dialogue;

import nl.knokko.texture.ImageTexture;
import nl.knokko.util.color.Color;

public abstract class DialoguePart {
	
	protected final Dialogue dialogue;

	public DialoguePart(Dialogue dialogue) {
		this.dialogue = dialogue;
	}
	
	public abstract Color getBackGround();
	
	public abstract ImageTexture getPortrait();
	
	public abstract DialogueText[] getText();
	
	public abstract SimpleDialogueText getTitle();
	
	public abstract void next();
}