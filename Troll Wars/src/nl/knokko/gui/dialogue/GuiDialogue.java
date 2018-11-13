package nl.knokko.gui.dialogue;

import nl.knokko.gui.component.menu.GuiMenu;
import nl.knokko.story.dialogue.Dialogue;
import nl.knokko.story.dialogue.DialoguePart;
import nl.knokko.util.resources.Resources;

public class GuiDialogue extends GuiMenu {
	
	private final Dialogue dialogue;

	public GuiDialogue(Dialogue dialogue) {
		this.dialogue = dialogue;
	}

	@Override
	protected void addComponents() {
		// TODO Auto-generated method stub

	}
	
	protected void changePart() {
		DialoguePart current = dialogue.current();
		Resources.createDialogueImage(current, null);
	}
}