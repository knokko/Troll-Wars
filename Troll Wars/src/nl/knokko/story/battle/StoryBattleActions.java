package nl.knokko.story.battle;

import nl.knokko.main.Game;
import nl.knokko.story.event.IntroHumanEvent;
import static nl.knokko.story.event.IntroHumanEvent.*;

public class StoryBattleActions {
	
	public static class Defeat {
		
		public static Runnable DEFAULT = new Runnable(){

			@Override
			public void run() {
				Game.gameOver();
			}
		};
	}
	
	public static class Victory {
		
		public static Runnable INTRO_HUMAN = new Runnable(){

			@Override
			public void run() {
				IntroHumanEvent e = Game.getEventManager().introHuman();
				byte s = e.getState();
				if(s == STATE_FOUGHT_ALONE){
					//Not going to happen legitimately, but theoretically possible.
					//Anyway, nothing special should happen in this scenario.
				}
				else if(s == STATE_FOUGHT_WITH_TROLLS){
					//TODO Start dialogue with the trolls.
				}
				else if(s == STATE_FOUGHT_WITH_DEMON){
					//TODO Short dialogue with the demon
				}
				//TODO update quest log (after I created quest overview...)
				else
					throw new IllegalStateException("The state of the intro human event is " + s + ", this should not be possible.");
			}
		};
	}
}