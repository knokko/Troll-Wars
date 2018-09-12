package nl.knokko.story.dialogues;

import nl.knokko.story.dialogue.Dialogue;
import nl.knokko.util.resources.Resources;

public final class Dialogues {
	
	public static final Dialogue INTRO_HUMANS = Resources.loadDialogue("intro humans", 0);
	public static final Dialogue INTRO_HUMANS_WITH_TROLLS = Resources.loadDialogue("intro humans with trolls", 1);
	public static final Dialogue INTRO_HUMANS_WITHOUT_TROLLS = Resources.loadDialogue("intro humans without trolls", 2);
	
	private static final Dialogue[] DIALOGUES = {INTRO_HUMANS, INTRO_HUMANS_WITH_TROLLS, INTRO_HUMANS_WITHOUT_TROLLS};
	
	public static Dialogue fromID(int id){
		return DIALOGUES[id];
	}
}