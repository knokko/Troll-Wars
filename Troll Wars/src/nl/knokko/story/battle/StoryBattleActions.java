/*******************************************************************************
 * The MIT License
 *
 * Copyright (c) 2019 knokko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *  
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *******************************************************************************/
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