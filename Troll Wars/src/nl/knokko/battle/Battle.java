/* 
 * The MIT License
 *
 * Copyright 2018 knokko.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
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
 */
package nl.knokko.battle;

import java.util.ArrayList;
import java.util.Random;

import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.battle.move.BattleMove;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;
import nl.knokko.view.camera.Camera;

public interface Battle {
	
	void save(BitOutput buffer);
	
	void load(BitInput buffer);
	
	byte getClassID();
	
	void update();
	
	void render();
	
	void selectPlayerMove(BattleMove move);
	
	BattleCreature getChoosingPlayer();
	
	Camera getCamera();
	
	boolean canRun();
	
	BattleCreature[] getPlayers();
	
	BattleCreature[] getOpponents();
	
	public static class Selector {
		
		private static final Random random = new Random();
		
		public static BattleCreature selectRandom(BattleCreature[] team){
			ArrayList<BattleCreature> list = new ArrayList<BattleCreature>(team.length);
			for(BattleCreature bc : team)
				if(!bc.isFainted())
					list.add(bc);
			return list.get(random.nextInt(list.size()));
		}
	}
	
	public static class Helper {
		
		public static int getIndexFor(Battle battle, BattleCreature creature){
			BattleCreature[] players = battle.getPlayers();
			for(int index = 0; index < players.length; index++)
				if(players[index] == creature)
					return index + 1;
			BattleCreature[] enemies = battle.getOpponents();
			for(int index = 0; index < enemies.length; index++)
				if(enemies[index] == creature)
					return -index - 1;
			return 0;
		}
		
		public static BattleCreature getByIndex(Battle battle, int index){
			if(index > 0)
				return battle.getPlayers()[index - 1];
			if(index < 0)
				return battle.getOpponents()[-index - 1];
			return null;
		}
	}
}