package nl.knokko.story.battle;

import nl.knokko.main.Game;

public class StoryBattleManager {

	public StoryBattleManager() {}
	
	public void onStoryBattleVictory(int plotID){
		Game.finishBattle();
		StoryBattleRegistry.VICTORY_EVENTS[plotID].run();
	}
	
	public void onStoryBattleDefeat(int plotID){
		StoryBattleRegistry.DEFEAT_EVENTS[plotID].run();
	}
}