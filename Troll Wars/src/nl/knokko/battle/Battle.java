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