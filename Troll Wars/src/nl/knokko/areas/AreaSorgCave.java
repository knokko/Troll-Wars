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

import java.util.Random;

import nl.knokko.area.Area;
import nl.knokko.area.AreaDoor;
import nl.knokko.area.AreaDoor.Location;
import nl.knokko.area.TileMap;
import nl.knokko.battle.decoration.BattleDecoration;
import nl.knokko.battle.decoration.BattleDecorations;
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
	
	@Override
	public BattleDecoration getBattleDecoration() {
		return BattleDecorations.SORG_CAVE;
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
