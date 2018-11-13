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
package nl.knokko.area;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import nl.knokko.area.AreaDoor.Location;
import nl.knokko.area.creature.AreaCreature;
import nl.knokko.area.creature.AreaPlayer;
import nl.knokko.areas.AreaRargia;
import nl.knokko.areas.AreaSorgCave;
import nl.knokko.areas.AreaSorgMountains;
import nl.knokko.main.Game;
import nl.knokko.render.main.CreatureRenderer;
import nl.knokko.render.tile.TileRenderer;
import nl.knokko.util.position.SpawnPosition;
import nl.knokko.view.camera.Camera;
import nl.knokko.view.light.Light;

public class Area {
	
	private static final Class<?>[] ID_MAP = {AreaRargia.class, AreaRargia.BoneSmith.class, AreaRargia.Farmer.class, AreaRargia.BlackSmith.class, AreaRargia.Farmacy.class, AreaSorgMountains.class, AreaSorgCave.A1.class};
	
	public static Class<?> classFromID(short id){
		return ID_MAP[id - Short.MIN_VALUE];
	}
	
	public static Area fromID(short id){
		try {
			return ((Area) classFromID(id).getMethod("getInstance").invoke(null));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static int amount(){
		return ID_MAP.length;
	}
	
	private static final SpawnPosition SPAWN = new SpawnPosition(0, 5, 0);
	
	private TileMap tiles;
	private final CreatureList creatures;
	private final Map<Location, AreaDoor> doors;

	public Area(int width, int depth) {
		tiles = new TileMap(width, depth, true);
		creatures = new CreatureList();
		doors = null;
	}
	
	public Area(TileMap tiles){
		this.tiles = tiles;
		creatures = new CreatureList();
		doors = new HashMap<Location, AreaDoor>();
		buildUp();
	}
	
	public Area(){
		creatures = new CreatureList();
		doors = new HashMap<Location,AreaDoor>();
	}
	
	public TileMap getTiles(){
		return tiles;
	}
	
	public CreatureList getCreatures(){
		return creatures;
	}
	
	public void render(CreatureRenderer creatureRenderer, TileRenderer tileRenderer, Camera camera, Light light){
		tileRenderer.prepare(true);
		tileRenderer.renderTiles(tiles, camera, light);
		creatures.render(creatureRenderer, camera, light);
	}
	
	public void update(){
		creatures.update();
	}
	
	public void startRandomBattle(){}
	
	public boolean hasRandomBattles(){
		return false;
	}
	
	protected void buildUp(){}
	
	public void spawnCreature(AreaCreature creature){
		creatures.spawnCreature(creature);
		creature.setArea(this);
	}
	
	protected void addDoor(AreaDoor door){
		doors.put(door.getLocation(), door);
	}
	
	public SpawnPosition getPlayerSpawn(){
		return SPAWN;
	}
	
	public boolean hasSpawnPoint(){
		return false;
	}
	
	public AreaPlayer getPlayer(boolean raiseException){
		return creatures.getPlayer(raiseException);
	}
	
	public AreaDoor getDoorAt(Location location){
		AreaDoor door = doors.get(location);
		if(door == null){
			Set<Entry<Location,AreaDoor>> entrySet = doors.entrySet();
			System.out.println("Available doors are:");
			for(Entry<Location,AreaDoor> entry : entrySet)
				System.out.println("(" + entry.getKey() + "," + entry.getValue() + ")");
			throw new IllegalArgumentException("There is no door at " + location + " in " + getClass().getSimpleName());
		}
		return door;
	}
	
	public short getID(){
		throw new IllegalStateException("The default Area class does not have an ID!");
	}
	
	public void save(){}
	
	public void load(){}
	
	public final void enable(){
		System.out.println("Enabling area " + getClass().getName().substring(20));
		tiles = loadTileMap();
		if(hasSpawnPoint()){
			spawnCreature(new AreaPlayer(getPlayerSpawn()));
			Game.getPlayers().gothrok.refreshEquipmentModels(creatures.getPlayer(true));
			Game.getNPCManager().processArea(this);
		}
		else if(getClass() != Area.class){
			spawnCreature(new AreaPlayer(new SpawnPosition(-1, 0, 0)));
			Game.getPlayers().gothrok.refreshEquipmentModels(creatures.getPlayer(true));
			Game.getNPCManager().processArea(this);
		}
		buildUp();
	}
	
	public final void disable(){
		System.out.println("Disabling area " + getClass().getName().substring(20));
		System.out.println("Current memory is " + Runtime.getRuntime().totalMemory());
		tiles = null;
		creatures.getList().clear();
		doors.clear();
	}
	
	protected TileMap loadTileMap(){
		throw new UnsupportedOperationException("Subclasses of Area need to override this method.");
	}
}
