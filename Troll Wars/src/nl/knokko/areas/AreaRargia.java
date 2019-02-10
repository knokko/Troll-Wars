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
package nl.knokko.areas;

import nl.knokko.area.Area;
import nl.knokko.area.AreaDoor;
import nl.knokko.area.AreaDoor.Location;
import nl.knokko.area.TileMap;
import nl.knokko.util.position.SpawnPosition;
import nl.knokko.util.resources.Resources;

public class AreaRargia extends Area {
	
	public static final short ID = -32768;
	
	private static final SpawnPosition SPAWN = new SpawnPosition(50, 6, 26);
	
	private static AreaRargia instance;
	
	public static AreaRargia getInstance(){
		if(instance == null)
			instance = new AreaRargia();
		return instance;
	}

	private AreaRargia() {
		super();
	}
	
	@Override
	protected TileMap loadTileMap(){
		return Resources.loadTileMap("Rargia", false);
	}
	
	@Override
	protected void buildUp(){
		super.buildUp();
		addDoor(new AreaDoor(new Location(88, 7, 44), null, 180, BoneSmith.ID));
		addDoor(new AreaDoor(new Location(85, 7, 44), null, 180, Farmer.ID));
		addDoor(new AreaDoor(new Location(88, 7, 28), null, 180, BlackSmith.ID));
		addDoor(new AreaDoor(new Location(85, 7, 28), null, 180, Farmacy.ID));
		addDoor(new AreaDoor(new Location(50, 20, 0), null, 90, AreaSorgMountains.ID));
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
	
	public static class BoneSmith extends Area {
		
		public static final short ID = -32767;
		public static final SpawnPosition SPAWN = new SpawnPosition(5, 4, 0);
		
		private static BoneSmith instance;
		
		public static BoneSmith getInstance(){
			if(instance == null)
				instance = new BoneSmith();
			return instance;
		}

		private BoneSmith() {
			super();
		}
		
		@Override
		protected TileMap loadTileMap(){
			return Resources.loadTileMap("Rargia bonesmith", false);
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
			addDoor(new AreaDoor(new Location(5, 8, 0), new Location(87, 3, 44), 270, AreaRargia.ID));
		}
	}
	
	public static class Farmer extends Area {
		
		public static final short ID = -32766;
		public static final SpawnPosition SPAWN = new SpawnPosition(5, 4, 0);
		
		private static Farmer instance;
		
		public static Farmer getInstance(){
			if(instance == null)
				instance = new Farmer();
			return instance;
		}

		private Farmer() {
			super();
		}
		
		@Override
		protected TileMap loadTileMap(){
			return Resources.loadTileMap("Rargia farmer", false);
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
			addDoor(new AreaDoor(new Location(5, 8, 0), new Location(86, 3, 44), 90, AreaRargia.ID));
		}
	}
	
	public static class BlackSmith extends Area {
		
		public static final short ID = -32765;
		public static final SpawnPosition SPAWN = new SpawnPosition(6, 0, 0);
		
		private static BlackSmith instance;
		
		public static BlackSmith getInstance(){
			if(instance == null)
				instance = new BlackSmith();
			return instance;
		}

		private BlackSmith() {
			super();
		}
		
		@Override
		protected TileMap loadTileMap(){
			return Resources.loadTileMap("Rargia blacksmith", false);
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
			addDoor(new AreaDoor(new Location(6, 4, 0), new Location(87, 3, 28), 270, AreaRargia.ID));
		}
	}
		
	public static class Farmacy extends Area {
			
		public static final short ID = -32764;
		public static final SpawnPosition SPAWN = new SpawnPosition(6, 0, 0);
			
		private static Farmacy instance;
			
		public static Farmacy getInstance(){
			if(instance == null)
				instance = new Farmacy();
			return instance;
		}

		private Farmacy() {
			super();
		}
		
		@Override
		protected TileMap loadTileMap(){
			return Resources.loadTileMap("Rargia farmacy", false);
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
			addDoor(new AreaDoor(new Location(6, 4, 0), new Location(86, 3, 28), 90, AreaRargia.ID));
		}
	}
}
