package nl.knokko.areas;

import java.util.Random;

import nl.knokko.area.Area;
import nl.knokko.area.AreaDoor;
import nl.knokko.area.AreaDoor.Location;
import nl.knokko.area.TileMap;
import nl.knokko.main.Game;
import nl.knokko.util.resources.Resources;

import static nl.knokko.areas.AreaSorgMountains.createSorgTroll;

public abstract class AreaSorgCave extends Area {

	public AreaSorgCave() {
		super();
	}
	
	@Override
	public boolean hasRandomBattles(){
		return true;
	}
	
	public static class A1 extends AreaSorgCave {
		
		public static final short ID = -32762;
		
		private static A1 instance;
		
		public static A1 getInstance(){
			if(instance == null)
				instance = new A1();
			return instance;
		}

		public A1() {
			super();
		}
		
		@Override
		protected TileMap loadTileMap(){
			return Resources.loadTileMap("Sorg Cave A1", false);
		}
		
		@Override
		public void buildUp(){
			super.buildUp();
			addDoor(new AreaDoor(new Location(50, 85, 40), new Location(75, 84, 30), 0, AreaSorgMountains.ID));
		}
		
		@Override
		public short getID(){
			return ID;
		}
		
		@Override
		public void startRandomBattle(){
			Random random = new Random();
			int rand = random.nextInt(4);
			if(rand == 0)
				Game.startRandomBattle(createSorgTroll(random, 1));
			if(rand == 1)
				Game.startRandomBattle(createSorgTroll(random, 2));
			if(rand == 2)
				Game.startRandomBattle(createSorgTroll(random, 1), createSorgTroll(random, 1));
			if(rand == 3)
				Game.startRandomBattle(createSorgTroll(random, 1), createSorgTroll(random, 2));
		}
	}
}
