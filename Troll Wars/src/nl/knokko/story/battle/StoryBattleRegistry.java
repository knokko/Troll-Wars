package nl.knokko.story.battle;

import static nl.knokko.story.battle.StoryBattleActions.*;

public class StoryBattleRegistry {
	
	public static final int STORY_BATTLE_COUNT = 1;
	
	public static final int ID_INTRO_HUMAN = 0;
	
	public static final Runnable[] VICTORY_EVENTS;
	
	public static final Runnable[] DEFEAT_EVENTS;
	
	static {
		VICTORY_EVENTS = new Runnable[STORY_BATTLE_COUNT];
		putVictory(ID_INTRO_HUMAN, Victory.INTRO_HUMAN);
		
		DEFEAT_EVENTS = new Runnable[STORY_BATTLE_COUNT];
		for(int i = 0; i < STORY_BATTLE_COUNT; i++)
			DEFEAT_EVENTS[i] = Defeat.DEFAULT;
	}
	
	private static void putVictory(int id, Runnable action){
		VICTORY_EVENTS[id] = action;
	}
}