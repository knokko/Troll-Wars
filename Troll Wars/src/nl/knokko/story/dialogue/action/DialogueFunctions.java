/*******************************************************************************
 * The MIT License
 *
 * Copyright (c) 2018 knokko
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
package nl.knokko.story.dialogue.action;

import nl.knokko.battle.PlotBattle;
import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.battle.creature.player.BattlePlayer;
import nl.knokko.battle.decoration.BattleDecorations;
import nl.knokko.battle.creature.demon.BattleMyrmora;
import nl.knokko.battle.creature.humanoid.BattleHuman;
import nl.knokko.main.Game;
import nl.knokko.story.battle.StoryBattleRegistry;
import nl.knokko.story.event.IntroHumanEvent;
import nl.knokko.story.npc.NPCManager;

public final class DialogueFunctions {
	
	public static void leave(){
		Game.closeDialogue();
	}

	public static void introHumanAppearDemon(){
		Game.getEventManager().introHuman().setState(IntroHumanEvent.STATE_SHOW_MYRMORA);
		Game.getNPCManager().refresh();
	}
	
	public static void introHumanLeaveTheresa(){
		Game.getNPCManager().getIntroTheresa().startLeaving();
	}
	
	public static void introHumanBattleWithDemon(){
		Game.getEventManager().introHuman().setState(IntroHumanEvent.STATE_FOUGHT_WITH_DEMON);
		BattleCreature[] allies = new BattleCreature[2];
		allies[0] = BattlePlayer.getInstance(Game.getPlayers().gothrok);
		Game.getPlayers().gothrok.enterBattle();
		allies[1] = new BattleMyrmora.Intro();
		Game.closeDialogue();
		Game.startBattle(new PlotBattle(BattleDecorations.SORG_CAVE, allies, getIntroHumans(), StoryBattleRegistry.ID_INTRO_HUMAN));
	}
	
	public static void introHumanBattleAlone(){
		Game.getEventManager().introHuman().setState(IntroHumanEvent.STATE_FOUGHT_ALONE);
		Game.closeDialogue();
		Game.startBattle(new PlotBattle(BattleDecorations.SORG_CAVE, Game.getPlayers().getBattlePlayers(), getIntroHumans(), StoryBattleRegistry.ID_INTRO_HUMAN));
	}
	
	public static void introHumanLeave(){
		Game.getEventManager().introHuman().setState(IntroHumanEvent.STATE_LEFT);
		Game.closeDialogue();
	}
	
	private static BattleCreature[] getIntroHumans(){
		BattleCreature[] humans = new BattleCreature[6];
		NPCManager n = Game.getNPCManager();
		//humans[0] = new BattleHuman.IntroWarrior(20, Items.IRON_SWORD, BodyHuman.Models.createSimpleInstance(random, 0.05f), BodyHuman.Textures.createBlanc(new Color(30, 20, 10)));
		//humans[1] = new BattleHuman.IntroWarrior(20, Items.IRON_SPEAR, BodyHuman.Models.createSimpleInstance(random, 0.05f), BodyHuman.Textures.createBrown(new Color(10, 20, 10)));
		//humans[2] = new BattleHuman.IntroWarrior(20, Items.IRON_SWORD, BodyHuman.Models.createSimpleInstance(random, 0.05f), BodyHuman.Textures.createBlanc(new Color(250, 250, 75)));
		//humans[3] = new BattleHuman.IntroPaladin(25, Items.BLESSED_IRON_SWORD, BodyHuman.Models.createSimpleInstance(random, 0f), BodyHuman.Textures.createBlanc(new Color(230, 250, 220)));
		//humans[4] = new BattleHuman.IntroWarrior(20, Items.IRON_SWORD, BodyHuman.Models.createSimpleInstance(random, 0.05f), BodyHuman.Textures.createBrown(new Color(100, 70, 0)));
		//humans[5] = new BattleHuman.IntroWarrior(20, Items.IRON_SPEAR, BodyHuman.Models.createSimpleInstance(random, 0.05f), BodyHuman.Textures.createBlanc(new Color(250, 250, 75)));
		//humans[6] = new BattleHuman.IntroWarrior(20, Items.IRON_SWORD, BodyHuman.Models.createSimpleInstance(random, 0.05f), BodyHuman.Textures.createBlanc(new Color(30, 20, 10)));
		humans[0] = new BattleHuman.IntroWarrior(n.getIntroWarrior1());
		humans[1] = new BattleHuman.IntroWarrior(n.getIntroWarrior2());
		humans[2] = new BattleHuman.IntroWarrior(n.getIntroWarrior3());
		humans[3] = new BattleHuman.IntroPaladin(n.getIntroPaladin());
		humans[4] = new BattleHuman.IntroWarrior(n.getIntroWarrior4());
		//humans[5] = new BattleHuman.IntroWarrior(n.getIntroWarrior5());
		//Intro warrior 5 is Theresa, who will leave to report to the wizard
		humans[5] = new BattleHuman.IntroWarrior(n.getIntroWarrior6());
		return humans;
	}
}