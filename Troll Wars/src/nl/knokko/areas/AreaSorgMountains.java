package nl.knokko.areas;

import java.util.Random;

import nl.knokko.area.Area;
import nl.knokko.area.AreaDoor;
import nl.knokko.area.AreaDoor.Location;
import nl.knokko.area.TileMap;
import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.battle.creature.bird.BattleMountainBird;
import nl.knokko.battle.creature.humanoid.BattleTroll;
import nl.knokko.equipment.EquipmentFull;
import nl.knokko.main.Game;
import nl.knokko.model.body.BodyBird;
import nl.knokko.model.body.BodyTroll;
import nl.knokko.util.position.SpawnPosition;
import nl.knokko.util.resources.Resources;

public class AreaSorgMountains extends Area {
	
	public static final short ID = -32763;
	public static final SpawnPosition SPAWN = new SpawnPosition(98, 100, 100);
	
	private static AreaSorgMountains instance;
	
	public static AreaSorgMountains getInstance(){
		if(instance == null)
			instance = new AreaSorgMountains();
		return instance;
	}

	private AreaSorgMountains() {
		super();
	}
	
	@Override
	protected TileMap loadTileMap(){
		return Resources.loadTileMap("Sorg Mountains", false);
	}
	
	@Override
	public SpawnPosition getPlayerSpawn(){
		return SPAWN;
	}
		
	@Override
	public short getID(){
		return ID;
	}
		
	@Override
	public boolean hasSpawnPoint(){
		return true;
	}
	
	@Override
	protected void buildUp(){
		super.buildUp();
		addDoor(new AreaDoor(new Location(97, 100, 100), new Location(50, 20, 0), 180, AreaRargia.ID));
		addDoor(new AreaDoor(new Location(75, 84, 31), new Location(50, 85, 40), 180, AreaSorgCave.A1.ID));
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
			Game.startRandomBattle(createSorgBird(1));
		if(rand == 3)
			Game.startRandomBattle(createSorgBird(2));
	}
	
	@Override
	public boolean hasRandomBattles(){
		return true;
	}
	
	public static BattleCreature createSorgTroll(Random random, int level){
		return new BattleTroll("Sorg Troll", level, new EquipmentFull(), BodyTroll.Models.createSorgTroll(random, 0.1f), BodyTroll.Textures.createSorg());
	}
	
	public static BattleCreature createSorgBird(int level){
		return new BattleMountainBird(level, BodyBird.Models.createSimpleInstance(100f), BodyBird.Textures.createSorg());
	}
}
