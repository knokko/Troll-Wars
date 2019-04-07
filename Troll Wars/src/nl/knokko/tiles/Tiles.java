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
package nl.knokko.tiles;

import java.util.ArrayList;
import java.util.Random;

import nl.knokko.main.Options;
import nl.knokko.texture.ModelTexture;
import nl.knokko.texture.Texture;
import nl.knokko.texture.factory.TileTextureFactory;
import nl.knokko.util.Facing;
import nl.knokko.util.color.Color;
import nl.knokko.util.resources.Resources;

public class Tiles {
	
	private static final Texture[] GRASS_TEXTURES = TileTextureFactory.createGrassTextures(Options.TEXTURES_PER_TILE);
	private static final Texture BIG_GRASS_TEXTURE = TileTextureFactory.createBigGrassTexture(new Color(0,100,40), new Color(200, 255, 150), new Color(30,13,0));
	private static final Texture[] BLUE_GRASS_TEXTURES = TileTextureFactory.createGrassTextures(Options.TEXTURES_PER_TILE, new Color(0, 100, 200));
	private static final Texture BIG_BLUE_GRASS_TEXTURE = TileTextureFactory.createBigGrassTexture(new Color(10, 50, 100), new Color(40, 120, 230), new Color(0,0,0));
	private static final Texture[] SORG_ROCK_TEXTURES = TileTextureFactory.createRockTextures(Options.TEXTURES_PER_TILE, Color.SORG_BASE, Color.SORG_TINTS, new int[]{4, 4});
	private static final Texture[] SORG_ROCK_HOLE_TEXTURES = TileTextureFactory.createHoleTextures(Options.TEXTURES_PER_TILE, Color.SORG_BASE, Color.SORG_TINTS, new int[]{4, 4});
	private static final Texture[] SORG_BRICKS_TEXTURES = TileTextureFactory.createBrickTextures(Options.TEXTURES_PER_TILE, Color.SORG_BASE, Color.SORG_BRICK_EDGE, 8, 4);
	private static final Texture[] SORG_BRICKS_TL_TEXTURES = TileTextureFactory.createBrickTriLeftTextures(Options.TEXTURES_PER_TILE, Color.SORG_BASE, Color.SORG_BRICK_EDGE, 8, 4);
	private static final Texture[] SORG_BRICKS_TR_TEXTURES = TileTextureFactory.createBrickTriRightTextures(Options.TEXTURES_PER_TILE, Color.SORG_BASE, Color.SORG_BRICK_EDGE, 8, 4);
	private static final Texture[] AYUE_PLANKS_TEXTURES = TileTextureFactory.createPlanksTextures(Options.TEXTURES_PER_TILE, Color.AYUE_PLANK, Color.AYUE_EDGE, 16, 4, 4);
	private static final Texture[] AYUE_FENCE_TEXTURES = TileTextureFactory.createFenceTextures(Options.TEXTURES_PER_TILE, Color.AYUE_PLANK, Color.AYUE_EDGE, 16, 4, 4);
	private static final Texture[] AYUE_DOOR_TEXTURES = TileTextureFactory.createDoorTextures(Options.TEXTURES_PER_TILE, Color.AYUE_PLANK, Color.AYUE_EDGE, new Color(10, 10, 10), 16, 4, 4);
	private static final Texture[] AYUE_LADDER_TEXTURES = TileTextureFactory.createLadderTextures(Options.TEXTURES_PER_TILE, Color.AYUE_PLANK, new Color(30, 25, 50));
	private static final Texture[] GIO_PATH_TEXTURES = Resources.createThinPathTextures(Options.TEXTURES_PER_TILE, new Color(30, 60, 170), 0.2f);
	private static final Texture WATER_TEXTURE = Resources.createLiquidTexture(new Color(0, 0, 110));
	
	static final ArrayList<Tile> tiles = new ArrayList<Tile>();
	static final Random random = new Random(30497620367L);
	private static Tile[][] ID_MAP;
	
	public static final Tile[] GREEN_GRASS = createTiles("Grass");
	public static final Tile[] BLUE_GRASS = createTiles("BlueGrass");
	
	public static final Tile[] BLUE_GRASS_SN1 = createTiles("BlueGrassSlopeNorth1");
	public static final Tile[] BLUE_GRASS_SE1 = createTiles("BlueGrassSlopeEast1");
	public static final Tile[] BLUE_GRASS_SS1 = createTiles("BlueGrassSlopeSouth1");
	public static final Tile[] BLUE_GRASS_SW1 = createTiles("BlueGrassSlopeWest1");
	
	public static final Tile[] BLUE_GRASS_SH_NFD1 = createTiles("BlueGrassSlopeHalfNorthFalseDown1");
	public static final Tile[] BLUE_GRASS_SH_EFD1 = createTiles("BlueGrassSlopeHalfEastFalseDown1");
	public static final Tile[] BLUE_GRASS_SH_SFD1 = createTiles("BlueGrassSlopeHalfSouthFalseDown1");
	public static final Tile[] BLUE_GRASS_SH_WFD1 = createTiles("BlueGrassSlopeHalfWestFalseDown1");
	public static final Tile[] BLUE_GRASS_SH_NTD1 = createTiles("BlueGrassSlopeHalfNorthTrueDown1");
	public static final Tile[] BLUE_GRASS_SH_ETD1 = createTiles("BlueGrassSlopeHalfEastTrueDown1");
	public static final Tile[] BLUE_GRASS_SH_STD1 = createTiles("BlueGrassSlopeHalfSouthTrueDown1");
	public static final Tile[] BLUE_GRASS_SH_WTD1 = createTiles("BlueGrassSlopeHalfWestTrueDown1");
	public static final Tile[] BLUE_GRASS_SH_NFU1 = createTiles("BlueGrassSlopeHalfNorthFalseUp1");
	public static final Tile[] BLUE_GRASS_SH_EFU1 = createTiles("BlueGrassSlopeHalfEastFalseUp1");
	public static final Tile[] BLUE_GRASS_SH_SFU1 = createTiles("BlueGrassSlopeHalfSouthFalseUp1");
	public static final Tile[] BLUE_GRASS_SH_WFU1 = createTiles("BlueGrassSlopeHalfWestFalseUp1");
	public static final Tile[] BLUE_GRASS_SH_NTU1 = createTiles("BlueGrassSlopeHalfNorthTrueUp1");
	public static final Tile[] BLUE_GRASS_SH_ETU1 = createTiles("BlueGrassSlopeHalfEastTrueUp1");
	public static final Tile[] BLUE_GRASS_SH_STU1 = createTiles("BlueGrassSlopeHalfSouthTrueUp1");
	public static final Tile[] BLUE_GRASS_SH_WTU1 = createTiles("BlueGrassSlopeHalfWestTrueUp1");
	
	public static final Tile[] SORG_ROCK = createTiles("SorgRock");
	
	public static final Tile[] SORG_ROCK_NORTH = createTiles("SorgRockNorth");
	public static final Tile[] SORG_ROCK_EAST = createTiles("SorgRockEast");
	public static final Tile[] SORG_ROCK_SOUTH = createTiles("SorgRockSouth");
	public static final Tile[] SORG_ROCK_WEST = createTiles("SorgRockWest");
	
	public static final Tile[] SORG_ROCK_NORTH2 = createTiles("SorgRockNorth2");
	public static final Tile[] SORG_ROCK_EAST2 = createTiles("SorgRockEast2");
	public static final Tile[] SORG_ROCK_SOUTH2 = createTiles("SorgRockSouth2");
	public static final Tile[] SORG_ROCK_WEST2 = createTiles("SorgRockWest2");
	
	public static final Tile[] ROCK_SLOPE_N1 = createTiles("SorgRockSlopeNorth1");
	public static final Tile[] ROCK_SLOPE_E1 = createTiles("SorgRockSlopeEast1");
	public static final Tile[] ROCK_SLOPE_S1 = createTiles("SorgRockSlopeSouth1");
	public static final Tile[] ROCK_SLOPE_W1 = createTiles("SorgRockSlopeWest1");
	public static final Tile[] ROCK_SLOPE_N2 = createTiles("SorgRockSlopeNorth2");
	public static final Tile[] ROCK_SLOPE_E2 = createTiles("SorgRockSlopeEast2");
	public static final Tile[] ROCK_SLOPE_S2 = createTiles("SorgRockSlopeSouth2");
	public static final Tile[] ROCK_SLOPE_W2 = createTiles("SorgRockSlopeWest2");
	public static final Tile[] ROCK_SLOPE_N3 = createTiles("SorgRockSlopeNorth3");
	public static final Tile[] ROCK_SLOPE_E3 = createTiles("SorgRockSlopeEast3");
	public static final Tile[] ROCK_SLOPE_S3 = createTiles("SorgRockSlopeSouth3");
	public static final Tile[] ROCK_SLOPE_W3 = createTiles("SorgRockSlopeWest3");
	public static final Tile[] ROCK_SLOPE_N4 = createTiles("SorgRockSlopeNorth4");
	public static final Tile[] ROCK_SLOPE_E4 = createTiles("SorgRockSlopeEast4");
	public static final Tile[] ROCK_SLOPE_S4 = createTiles("SorgRockSlopeSouth4");
	public static final Tile[] ROCK_SLOPE_W4 = createTiles("SorgRockSlopeWest4");
	
	public static final Tile[] ROCK_SLOPEH_NFD4 = createTiles("SorgRockSlopeHalfNorthFalseDown4");
	public static final Tile[] ROCK_SLOPEH_EFD4 = createTiles("SorgRockSlopeHalfEastFalseDown4");
	public static final Tile[] ROCK_SLOPEH_SFD4 = createTiles("SorgRockSlopeHalfSouthFalseDown4");
	public static final Tile[] ROCK_SLOPEH_WFD4 = createTiles("SorgRockSlopeHalfWestFalseDown4");
	public static final Tile[] ROCK_SLOPEH_NTD4 = createTiles("SorgRockSlopeHalfNorthTrueDown4");
	public static final Tile[] ROCK_SLOPEH_ETD4 = createTiles("SorgRockSlopeHalfEastTrueDown4");
	public static final Tile[] ROCK_SLOPEH_STD4 = createTiles("SorgRockSlopeHalfSouthTrueDown4");
	public static final Tile[] ROCK_SLOPEH_WTD4 = createTiles("SorgRockSlopeHalfWestTrueDown4");
	public static final Tile[] ROCK_SLOPEH_NFU4 = createTiles("SorgRockSlopeHalfNorthFalseUp4");
	public static final Tile[] ROCK_SLOPEH_EFU4 = createTiles("SorgRockSlopeHalfEastFalseUp4");
	public static final Tile[] ROCK_SLOPEH_SFU4 = createTiles("SorgRockSlopeHalfSouthFalseUp4");
	public static final Tile[] ROCK_SLOPEH_WFU4 = createTiles("SorgRockSlopeHalfWestFalseUp4");
	public static final Tile[] ROCK_SLOPEH_NTU4 = createTiles("SorgRockSlopeHalfNorthTrueUp4");
	public static final Tile[] ROCK_SLOPEH_ETU4 = createTiles("SorgRockSlopeHalfEastTrueUp4");
	public static final Tile[] ROCK_SLOPEH_STU4 = createTiles("SorgRockSlopeHalfSouthTrueUp4");
	public static final Tile[] ROCK_SLOPEH_WTU4 = createTiles("SorgRockSlopeHalfWestTrueUp4");
	
	public static final Tile[] SORG_BRICKS = createTiles("SorgBricks");
	public static final Tile[] SORG_BRICKS_NORTH = createTiles("SorgBricksNorth");
	public static final Tile[] SORG_BRICKS_EAST = createTiles("SorgBricksEast");
	public static final Tile[] SORG_BRICKS_SOUTH = createTiles("SorgBricksSouth");
	public static final Tile[] SORG_BRICKS_WEST = createTiles("SorgBricksWest");
	
	public static final Tile[] SORG_BRICKS_NORTH_H = createTiles("SorgBricksNorthHalf");
	public static final Tile[] SORG_BRICKS_EAST_H = createTiles("SorgBricksEastHalf");
	public static final Tile[] SORG_BRICKS_SOUTH_H = createTiles("SorgBricksSouthHalf");
	public static final Tile[] SORG_BRICKS_WEST_H = createTiles("SorgBricksWestHalf");
	
	public static final Tile[] SORG_BRICKS_NORTH_TL = createTiles("SorgBricksNorthTriLeft");
	public static final Tile[] SORG_BRICKS_EAST_TL = createTiles("SorgBricksEastTriLeft");
	public static final Tile[] SORG_BRICKS_SOUTH_TL = createTiles("SorgBricksSouthTriLeft");
	public static final Tile[] SORG_BRICKS_WEST_TL = createTiles("SorgBricksWestTriLeft");
	
	public static final Tile[] SORG_BRICKS_NORTH_TR = createTiles("SorgBricksNorthTriRight");
	public static final Tile[] SORG_BRICKS_EAST_TR = createTiles("SorgBricksEastTriRight");
	public static final Tile[] SORG_BRICKS_SOUTH_TR = createTiles("SorgBricksSouthTriRight");
	public static final Tile[] SORG_BRICKS_WEST_TR = createTiles("SorgBricksWestTriRight");
	
	public static final Tile[] SORG_ROCK_HOLE_N = createTiles("SorgRockHoleNorth");
	public static final Tile[] SORG_ROCK_HOLE_E = createTiles("SorgRockHoleEast");
	public static final Tile[] SORG_ROCK_HOLE_S = createTiles("SorgRockHoleSouth");
	public static final Tile[] SORG_ROCK_HOLE_W = createTiles("SorgRockHoleWest");
	
	public static final Tile[] AYUE_PLANKS = createTiles("AyuePlanks");
	public static final Tile[] AYUE_PLANKS_NORTH = createTiles("AyuePlanksNorth");
	public static final Tile[] AYUE_PLANKS_EAST = createTiles("AyuePlanksEast");
	public static final Tile[] AYUE_PLANKS_SOUTH = createTiles("AyuePlanksSouth");
	public static final Tile[] AYUE_PLANKS_WEST = createTiles("AyuePlanksWest");
	
	public static final Tile[] AYUE_FENCE_NORTH = createTiles("AyueFenceNorth");
	public static final Tile[] AYUE_FENCE_EAST = createTiles("AyueFenceEast");
	public static final Tile[] AYUE_FENCE_SOUTH = createTiles("AyueFenceSouth");
	public static final Tile[] AYUE_FENCE_WEST = createTiles("AyueFenceWest");
	
	public static final Tile[] AYUE_DOOR_NORTH = createTiles("AyueDoorNorth");
	public static final Tile[] AYUE_DOOR_EAST = createTiles("AyueDoorEast");
	public static final Tile[] AYUE_DOOR_SOUTH = createTiles("AyueDoorSouth");
	public static final Tile[] AYUE_DOOR_WEST = createTiles("AyueDoorWest");
	
	public static final Tile[] AYUE_SLOPE_NORTH = createTiles("AyueSlopeNorth");
	public static final Tile[] AYUE_SLOPE_EAST = createTiles("AyueSlopeEast");
	public static final Tile[] AYUE_SLOPE_SOUTH = createTiles("AyueSlopeSouth");
	public static final Tile[] AYUE_SLOPE_WEST = createTiles("AyueSlopeWest");
	
	public static final Tile[] AYUE_LADDER_NORTH = createTiles("AyueLadderNorth");
	public static final Tile[] AYUE_LADDER_EAST = createTiles("AyueLadderEast");
	public static final Tile[] AYUE_LADDER_SOUTH = createTiles("AyueLadderSouth");
	public static final Tile[] AYUE_LADDER_WEST = createTiles("AyueLadderWest");
	
	public static final Tile[] GIO_PATH = createTiles("GioPath");
	public static final Tile[] WATER = createTiles("Water");
	
	public static void init(){
		ID_MAP = new Tile[107][];
		registerTiles(GREEN_GRASS, (short) -32768);
		registerTiles(SORG_ROCK, (short) -32767);
		registerTiles(ROCK_SLOPE_N1, (short) -32766);
		registerTiles(ROCK_SLOPE_W1, (short) -32765);
		registerTiles(ROCK_SLOPE_S1, (short) -32764);
		registerTiles(ROCK_SLOPE_E1, (short) -32763);
		registerTiles(ROCK_SLOPE_N2, (short) -32762);
		registerTiles(ROCK_SLOPE_W2, (short) -32761);
		registerTiles(ROCK_SLOPE_S2, (short) -32760);
		registerTiles(ROCK_SLOPE_E2, (short) -32759);
		registerTiles(ROCK_SLOPE_N3, (short) -32758);
		registerTiles(ROCK_SLOPE_W3, (short) -32757);
		registerTiles(ROCK_SLOPE_S3, (short) -32756);
		registerTiles(ROCK_SLOPE_E3, (short) -32755);
		registerTiles(ROCK_SLOPE_N4, (short) -32754);
		registerTiles(ROCK_SLOPE_W4, (short) -32753);
		registerTiles(ROCK_SLOPE_S4, (short) -32752);
		registerTiles(ROCK_SLOPE_E4, (short) -32751);
		registerTiles(SORG_BRICKS_NORTH, (short) -32750);
		registerTiles(SORG_BRICKS_WEST, (short) -32747);
		registerTiles(SORG_BRICKS_SOUTH, (short) -32748);
		registerTiles(SORG_BRICKS_EAST, (short) -32749);
		registerTiles(SORG_ROCK_NORTH, (short) -32746);
		registerTiles(SORG_ROCK_WEST, (short) -32745);
		registerTiles(SORG_ROCK_SOUTH, (short) -32744);
		registerTiles(SORG_ROCK_EAST, (short) -32743);
		registerTiles(SORG_ROCK_NORTH2, (short) -32742);
		registerTiles(SORG_ROCK_WEST2, (short) -32741);
		registerTiles(SORG_ROCK_SOUTH2, (short) -32740);
		registerTiles(SORG_ROCK_EAST2, (short) -32739);
		registerTiles(AYUE_PLANKS, (short) -32738);
		registerTiles(AYUE_PLANKS_NORTH, (short) -32737);
		registerTiles(AYUE_PLANKS_WEST, (short) -32734);
		registerTiles(AYUE_PLANKS_SOUTH, (short) -32735);
		registerTiles(AYUE_PLANKS_EAST, (short) -32736);
		registerTiles(AYUE_DOOR_NORTH, (short) -32733);
		registerTiles(AYUE_DOOR_WEST, (short) -32730);
		registerTiles(AYUE_DOOR_SOUTH, (short) -32731);
		registerTiles(AYUE_DOOR_EAST, (short) -32732);
		registerTiles(AYUE_SLOPE_NORTH, (short) -32729);
		registerTiles(AYUE_SLOPE_WEST, (short) -32728);
		registerTiles(AYUE_SLOPE_SOUTH, (short) -32727);
		registerTiles(AYUE_SLOPE_EAST, (short) -32726);
		registerTiles(GIO_PATH, (short) -32725);
		registerTiles(WATER, (short) -32724);
		registerTiles(ROCK_SLOPEH_NFD4, (short) -32723);
		registerTiles(ROCK_SLOPEH_WFD4, (short) -32722);
		registerTiles(ROCK_SLOPEH_SFD4, (short) -32721);
		registerTiles(ROCK_SLOPEH_EFD4, (short) -32720);
		registerTiles(ROCK_SLOPEH_NTD4, (short) -32719);
		registerTiles(ROCK_SLOPEH_WTD4, (short) -32718);
		registerTiles(ROCK_SLOPEH_STD4, (short) -32717);
		registerTiles(ROCK_SLOPEH_ETD4, (short) -32716);
		registerTiles(ROCK_SLOPEH_NFU4, (short) -32715);
		registerTiles(ROCK_SLOPEH_WFU4, (short) -32714);
		registerTiles(ROCK_SLOPEH_SFU4, (short) -32713);
		registerTiles(ROCK_SLOPEH_EFU4, (short) -32712);
		registerTiles(ROCK_SLOPEH_NTU4, (short) -32711);
		registerTiles(ROCK_SLOPEH_WTU4, (short) -32710);
		registerTiles(ROCK_SLOPEH_STU4, (short) -32709);
		registerTiles(ROCK_SLOPEH_ETU4, (short) -32708);
		registerTiles(SORG_BRICKS, (short) -32707);
		registerTiles(AYUE_FENCE_NORTH, (short) -32706);
		registerTiles(AYUE_FENCE_EAST, (short) -32703);
		registerTiles(AYUE_FENCE_SOUTH, (short) -32704);
		registerTiles(AYUE_FENCE_WEST, (short) -32705);
		registerTiles(SORG_BRICKS_NORTH_TL, (short) -32702);
		registerTiles(SORG_BRICKS_EAST_TL, (short) -32697);
		registerTiles(SORG_BRICKS_SOUTH_TL, (short) -32700);
		registerTiles(SORG_BRICKS_WEST_TL, (short) -32695);
		registerTiles(SORG_BRICKS_NORTH_TR, (short) -32698);
		registerTiles(SORG_BRICKS_EAST_TR, (short) -32699);
		registerTiles(SORG_BRICKS_SOUTH_TR, (short) -32696);
		registerTiles(SORG_BRICKS_WEST_TR, (short) -32701);
		registerTiles(SORG_BRICKS_NORTH_H, (short) -32694);
		registerTiles(SORG_BRICKS_EAST_H, (short) -32693);
		registerTiles(SORG_BRICKS_SOUTH_H, (short) -32692);
		registerTiles(SORG_BRICKS_WEST_H, (short) -32691);
		registerTiles(BLUE_GRASS, (short) -32690);
		registerTiles(BLUE_GRASS_SN1, (short) -32689);
		registerTiles(BLUE_GRASS_SE1, (short) -32688);
		registerTiles(BLUE_GRASS_SS1, (short) -32687);
		registerTiles(BLUE_GRASS_SW1, (short) -32686);
		registerTiles(BLUE_GRASS_SH_NFD1, (short) -32685);
		registerTiles(BLUE_GRASS_SH_WFD1, (short) -32684);
		registerTiles(BLUE_GRASS_SH_SFD1, (short) -32683);
		registerTiles(BLUE_GRASS_SH_EFD1, (short) -32682);
		registerTiles(BLUE_GRASS_SH_NTD1, (short) -32681);
		registerTiles(BLUE_GRASS_SH_WTD1, (short) -32680);
		registerTiles(BLUE_GRASS_SH_STD1, (short) -32679);
		registerTiles(BLUE_GRASS_SH_ETD1, (short) -32678);
		registerTiles(BLUE_GRASS_SH_NFU1, (short) -32677);
		registerTiles(BLUE_GRASS_SH_WFU1, (short) -32676);
		registerTiles(BLUE_GRASS_SH_SFU1, (short) -32675);
		registerTiles(BLUE_GRASS_SH_EFU1, (short) -32674);
		registerTiles(BLUE_GRASS_SH_NTU1, (short) -32673);
		registerTiles(BLUE_GRASS_SH_WTU1, (short) -32672);
		registerTiles(BLUE_GRASS_SH_STU1, (short) -32671);
		registerTiles(BLUE_GRASS_SH_ETU1, (short) -32670);
		registerTiles(AYUE_LADDER_NORTH, (short) -32669);
		registerTiles(AYUE_LADDER_EAST, (short) -32668);
		registerTiles(AYUE_LADDER_SOUTH, (short) -32667);
		registerTiles(AYUE_LADDER_WEST, (short) -32666);
		registerTiles(SORG_ROCK_HOLE_N, (short) -32665);
		registerTiles(SORG_ROCK_HOLE_E, (short) -32664);
		registerTiles(SORG_ROCK_HOLE_S, (short) -32663);
		registerTiles(SORG_ROCK_HOLE_W, (short) -32662);
	}
	
	private static void registerTiles(Tile[] tiles, short id){
		for(Tile tile : tiles)
			tile.setAbsoluteID(id);
		ID_MAP[id - Short.MIN_VALUE] = tiles;
	}
	
	public static Tile fromRuntimeID(short id){
		return tiles.get(id - Short.MIN_VALUE);
	}
	
	public static Tile[] fromAbsoluteID(short id){
		return ID_MAP[id - Short.MIN_VALUE];
	}
	
	public static Tile randomFromAbsoluteID(short id){
		Tile[] tiles = fromAbsoluteID(id);
		return tiles[random.nextInt(tiles.length)];
	}
	
	public static int getAmount(){
		return tiles.size();
	}
	
	private static Tile[] createTiles(String methodName){
		Tile[] tiles = new Tile[Options.TEXTURES_PER_TILE];
		for(int i = 0; i < tiles.length; i++){
			try {
				tiles[i] = (Tile) TileCreator.class.getMethod("create" + methodName, int.class).invoke(null, i);
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		}
		return tiles;
	}
	
	public static enum RenderForm {
		
		QUAD,
		TRIANGLE,
		CUSTOM;
	}
	
	static class TileCreator {
		
		public static Tile createGrass(int index){
			return new TileBottom(new ModelTexture(GRASS_TEXTURES[index], 1f, 0.1f), new ModelTexture(BIG_GRASS_TEXTURE, 1f, 0.1f));
		}
		
		public static Tile createBlueGrass(int index){
			return new TileBottom(new ModelTexture(BLUE_GRASS_TEXTURES[index], 1f, 0.1f), new ModelTexture(BIG_BLUE_GRASS_TEXTURE, 1f, 0.1f));
		}
		
		public static Tile createBlueGrassSlopeNorth1(int index){
			return new TileSlope(new ModelTexture(BLUE_GRASS_TEXTURES[index], 1f, 0.1f), Facing.NORTH, 1);
		}
		
		public static Tile createBlueGrassSlopeEast1(int index){
			return new TileSlope(new ModelTexture(BLUE_GRASS_TEXTURES[index], 1f, 0.1f), Facing.EAST, 1);
		}
		
		public static Tile createBlueGrassSlopeSouth1(int index){
			return new TileSlope(new ModelTexture(BLUE_GRASS_TEXTURES[index], 1f, 0.1f), Facing.SOUTH, 1);
		}
		
		public static Tile createBlueGrassSlopeWest1(int index){
			return new TileSlope(new ModelTexture(BLUE_GRASS_TEXTURES[index], 1f, 0.1f), Facing.WEST, 1);
		}
		
		public static Tile createSorgRock(int index){
			return new TileBottom(new ModelTexture(SORG_ROCK_TEXTURES[index], 1.3f, 0.2f));
		}
		
		public static Tile createSorgRockSlopeNorth1(int index){
			return new TileSlope(new ModelTexture(SORG_ROCK_TEXTURES[index], 1.3f, 0.2f), Facing.NORTH, 1);
		}
		
		public static Tile createSorgRockSlopeEast1(int index){
			return new TileSlope(new ModelTexture(SORG_ROCK_TEXTURES[index], 1.3f, 0.2f), Facing.EAST, 1);
		}
		
		public static Tile createSorgRockSlopeSouth1(int index){
			return new TileSlope(new ModelTexture(SORG_ROCK_TEXTURES[index], 1.3f, 0.2f), Facing.SOUTH, 1);
		}
		
		public static Tile createSorgRockSlopeWest1(int index){
			return new TileSlope(new ModelTexture(SORG_ROCK_TEXTURES[index], 1.3f, 0.2f), Facing.WEST, 1);
		}
		
		public static Tile createSorgRockSlopeNorth2(int index){
			return new TileSlope(new ModelTexture(SORG_ROCK_TEXTURES[index], 1.3f, 0.2f), Facing.NORTH, 2);
		}
		
		public static Tile createSorgRockSlopeEast2(int index){
			return new TileSlope(new ModelTexture(SORG_ROCK_TEXTURES[index], 1.3f, 0.2f), Facing.EAST, 2);
		}
		
		public static Tile createSorgRockSlopeSouth2(int index){
			return new TileSlope(new ModelTexture(SORG_ROCK_TEXTURES[index], 1.3f, 0.2f), Facing.SOUTH, 2);
		}
		
		public static Tile createSorgRockSlopeWest2(int index){
			return new TileSlope(new ModelTexture(SORG_ROCK_TEXTURES[index], 1.3f, 0.2f), Facing.WEST, 2);
		}
		
		public static Tile createSorgRockSlopeNorth3(int index){
			return new TileSlope(new ModelTexture(SORG_ROCK_TEXTURES[index], 1.3f, 0.2f), Facing.NORTH, 3);
		}
		
		public static Tile createSorgRockSlopeEast3(int index){
			return new TileSlope(new ModelTexture(SORG_ROCK_TEXTURES[index], 1.3f, 0.2f), Facing.EAST, 3);
		}
		
		public static Tile createSorgRockSlopeSouth3(int index){
			return new TileSlope(new ModelTexture(SORG_ROCK_TEXTURES[index], 1.3f, 0.2f), Facing.SOUTH, 3);
		}
		
		public static Tile createSorgRockSlopeWest3(int index){
			return new TileSlope(new ModelTexture(SORG_ROCK_TEXTURES[index], 1.3f, 0.2f), Facing.WEST, 3);
		}
		
		public static Tile createSorgRockSlopeNorth4(int index){
			return new TileSlope(new ModelTexture(SORG_ROCK_TEXTURES[index], 1.3f, 0.2f), Facing.NORTH, 4);
		}
		
		public static Tile createSorgRockSlopeEast4(int index){
			return new TileSlope(new ModelTexture(SORG_ROCK_TEXTURES[index], 1.3f, 0.2f), Facing.EAST, 4);
		}
		
		public static Tile createSorgRockSlopeSouth4(int index){
			return new TileSlope(new ModelTexture(SORG_ROCK_TEXTURES[index], 1.3f, 0.2f), Facing.SOUTH, 4);
		}
		
		public static Tile createSorgRockSlopeWest4(int index){
			return new TileSlope(new ModelTexture(SORG_ROCK_TEXTURES[index], 1.3f, 0.2f), Facing.WEST, 4);
		}
		
		public static Tile createSorgBricksNorth(int index){
			return new TileWall(new ModelTexture(SORG_BRICKS_TEXTURES[index], 1.3f, 0.2f), Facing.NORTH);
		}
		
		public static Tile createSorgBricksEast(int index){
			return new TileWall(new ModelTexture(SORG_BRICKS_TEXTURES[index], 1.3f, 0.2f), Facing.EAST);
		}
		
		public static Tile createSorgBricksSouth(int index){
			return new TileWall(new ModelTexture(SORG_BRICKS_TEXTURES[index], 1.3f, 0.2f), Facing.SOUTH);
		}
		
		public static Tile createSorgBricksWest(int index){
			return new TileWall(new ModelTexture(SORG_BRICKS_TEXTURES[index], 1.3f, 0.2f), Facing.WEST);
		}
		
		public static Tile createSorgBricksNorthHalf(int index){
			return new TileWallHalf(new ModelTexture(SORG_BRICKS_TEXTURES[index], 1.3f, 0.2f), Facing.NORTH);
		}
		
		public static Tile createSorgBricksEastHalf(int index){
			return new TileWallHalf(new ModelTexture(SORG_BRICKS_TEXTURES[index], 1.3f, 0.2f), Facing.EAST);
		}
		
		public static Tile createSorgBricksSouthHalf(int index){
			return new TileWallHalf(new ModelTexture(SORG_BRICKS_TEXTURES[index], 1.3f, 0.2f), Facing.SOUTH);
		}
		
		public static Tile createSorgBricksWestHalf(int index){
			return new TileWallHalf(new ModelTexture(SORG_BRICKS_TEXTURES[index], 1.3f, 0.2f), Facing.WEST);
		}
		
		public static Tile createSorgBricksNorthTriLeft(int index){
			return new TileWall(new ModelTexture(SORG_BRICKS_TL_TEXTURES[index], 1.3f, 0.2f), Facing.NORTH);
		}
		
		public static Tile createSorgBricksEastTriLeft(int index){
			return new TileWall(new ModelTexture(SORG_BRICKS_TL_TEXTURES[index], 1.3f, 0.2f), Facing.EAST);
		}
		
		public static Tile createSorgBricksSouthTriLeft(int index){
			return new TileWall(new ModelTexture(SORG_BRICKS_TL_TEXTURES[index], 1.3f, 0.2f), Facing.SOUTH);
		}
		
		public static Tile createSorgBricksWestTriLeft(int index){
			return new TileWall(new ModelTexture(SORG_BRICKS_TL_TEXTURES[index], 1.3f, 0.2f), Facing.WEST);
		}
		
		public static Tile createSorgBricksNorthTriRight(int index){
			return new TileWall(new ModelTexture(SORG_BRICKS_TR_TEXTURES[index], 1.3f, 0.2f), Facing.NORTH);
		}
		
		public static Tile createSorgBricksEastTriRight(int index){
			return new TileWall(new ModelTexture(SORG_BRICKS_TR_TEXTURES[index], 1.3f, 0.2f), Facing.EAST);
		}
		
		public static Tile createSorgBricksSouthTriRight(int index){
			return new TileWall(new ModelTexture(SORG_BRICKS_TR_TEXTURES[index], 1.3f, 0.2f), Facing.SOUTH);
		}
		
		public static Tile createSorgBricksWestTriRight(int index){
			return new TileWall(new ModelTexture(SORG_BRICKS_TR_TEXTURES[index], 1.3f, 0.2f), Facing.WEST);
		}
		
		public static Tile createSorgBricks(int index){
			return new TileBottom(new ModelTexture(SORG_BRICKS_TEXTURES[index], 1.3f, 0.2f));
		}
		
		public static Tile createSorgRockNorth(int index){
			return new TileWall(new ModelTexture(SORG_ROCK_TEXTURES[index], 1.3f, 0.2f), Facing.NORTH);
		}
		
		public static Tile createSorgRockEast(int index){
			return new TileWall(new ModelTexture(SORG_ROCK_TEXTURES[index], 1.3f, 0.2f), Facing.EAST);
		}
		
		public static Tile createSorgRockSouth(int index){
			return new TileWall(new ModelTexture(SORG_ROCK_TEXTURES[index], 1.3f, 0.2f), Facing.SOUTH);
		}
		
		public static Tile createSorgRockWest(int index){
			return new TileWall(new ModelTexture(SORG_ROCK_TEXTURES[index], 1.3f, 0.2f), Facing.WEST);
		}
		
		public static Tile createSorgRockHoleNorth(int index){
			return new TileLargeDoor(new ModelTexture(SORG_ROCK_HOLE_TEXTURES[index], 1.3f, 0.2f), Facing.NORTH);
		}
		
		public static Tile createSorgRockHoleEast(int index){
			return new TileLargeDoor(new ModelTexture(SORG_ROCK_HOLE_TEXTURES[index], 1.3f, 0.2f), Facing.EAST);
		}
		
		public static Tile createSorgRockHoleSouth(int index){
			return new TileLargeDoor(new ModelTexture(SORG_ROCK_HOLE_TEXTURES[index], 1.3f, 0.2f), Facing.SOUTH);
		}
		
		public static Tile createSorgRockHoleWest(int index){
			return new TileLargeDoor(new ModelTexture(SORG_ROCK_HOLE_TEXTURES[index], 1.3f, 0.2f), Facing.WEST);
		}
		
		public static Tile createSorgRockNorth2(int index){
			return new TileWallHalf(new ModelTexture(SORG_ROCK_TEXTURES[index], 1.3f, 0.2f), Facing.NORTH);
		}
		
		public static Tile createSorgRockEast2(int index){
			return new TileWallHalf(new ModelTexture(SORG_ROCK_TEXTURES[index], 1.3f, 0.2f), Facing.EAST);
		}
		
		public static Tile createSorgRockSouth2(int index){
			return new TileWallHalf(new ModelTexture(SORG_ROCK_TEXTURES[index], 1.3f, 0.2f), Facing.SOUTH);
		}
		
		public static Tile createSorgRockWest2(int index){
			return new TileWallHalf(new ModelTexture(SORG_ROCK_TEXTURES[index], 1.3f, 0.2f), Facing.WEST);
		}
		
		public static Tile createAyuePlanks(int index){
			return new TileBottom(new ModelTexture(AYUE_PLANKS_TEXTURES[index], 0.5f, 0.1f));
		}
		
		public static Tile createAyuePlanksNorth(int index){
			return new TileWall(new ModelTexture(AYUE_PLANKS_TEXTURES[index], 0.5f, 0.1f), Facing.NORTH);
		}
		
		public static Tile createAyuePlanksEast(int index){
			return new TileWall(new ModelTexture(AYUE_PLANKS_TEXTURES[index], 0.5f, 0.1f), Facing.EAST);
		}
		
		public static Tile createAyuePlanksSouth(int index){
			return new TileWall(new ModelTexture(AYUE_PLANKS_TEXTURES[index], 0.5f, 0.1f), Facing.SOUTH);
		}
		
		public static Tile createAyuePlanksWest(int index){
			return new TileWall(new ModelTexture(AYUE_PLANKS_TEXTURES[index], 0.5f, 0.1f), Facing.WEST);
		}
		
		public static Tile createAyueFenceNorth(int index){
			return new TileWall(new ModelTexture(AYUE_FENCE_TEXTURES[index], 0.5f, 0.1f), Facing.NORTH);
		}
		
		public static Tile createAyueFenceEast(int index){
			return new TileWall(new ModelTexture(AYUE_FENCE_TEXTURES[index], 0.5f, 0.1f), Facing.EAST);
		}
		
		public static Tile createAyueFenceSouth(int index){
			return new TileWall(new ModelTexture(AYUE_FENCE_TEXTURES[index], 0.5f, 0.1f), Facing.SOUTH);
		}
		
		public static Tile createAyueFenceWest(int index){
			return new TileWall(new ModelTexture(AYUE_FENCE_TEXTURES[index], 0.5f, 0.1f), Facing.WEST);
		}
		
		public static Tile createAyueDoorNorth(int index){
			return new TileDoor(new ModelTexture(AYUE_DOOR_TEXTURES[index], 0.5f, 0.1f), Facing.NORTH);
		}
		
		public static Tile createAyueDoorEast(int index){
			return new TileDoor(new ModelTexture(AYUE_DOOR_TEXTURES[index], 0.5f, 0.1f), Facing.EAST);
		}
		
		public static Tile createAyueDoorSouth(int index){
			return new TileDoor(new ModelTexture(AYUE_DOOR_TEXTURES[index], 0.5f, 0.1f), Facing.SOUTH);
		}
		
		public static Tile createAyueDoorWest(int index){
			return new TileDoor(new ModelTexture(AYUE_DOOR_TEXTURES[index], 0.5f, 0.1f), Facing.WEST);
		}
		
		public static Tile createAyueSlopeNorth(int index){
			return new TileSlope(new ModelTexture(AYUE_PLANKS_TEXTURES[index], 0.5f, 0.1f), Facing.NORTH, 2);
		}
		
		public static Tile createAyueSlopeEast(int index){
			return new TileSlope(new ModelTexture(AYUE_PLANKS_TEXTURES[index], 0.5f, 0.1f), Facing.EAST, 2);
		}
		
		public static Tile createAyueSlopeSouth(int index){
			return new TileSlope(new ModelTexture(AYUE_PLANKS_TEXTURES[index], 0.5f, 0.1f), Facing.SOUTH, 2);
		}
		
		public static Tile createAyueSlopeWest(int index){
			return new TileSlope(new ModelTexture(AYUE_PLANKS_TEXTURES[index], 0.5f, 0.1f), Facing.WEST, 2);
		}
		
		public static Tile createAyueLadderNorth(int index){
			return new TileLadder(new ModelTexture(AYUE_LADDER_TEXTURES[index], 0.5f, 0.1f), Facing.NORTH);
		}
		
		public static Tile createAyueLadderEast(int index){
			return new TileLadder(new ModelTexture(AYUE_LADDER_TEXTURES[index], 0.5f, 0.1f), Facing.EAST);
		}
		
		public static Tile createAyueLadderSouth(int index){
			return new TileLadder(new ModelTexture(AYUE_LADDER_TEXTURES[index], 0.5f, 0.1f), Facing.SOUTH);
		}
		
		public static Tile createAyueLadderWest(int index){
			return new TileLadder(new ModelTexture(AYUE_LADDER_TEXTURES[index], 0.5f, 0.1f), Facing.WEST);
		}
		
		public static Tile createGioPath(int index){
			return new TileBottomCover(new ModelTexture(GIO_PATH_TEXTURES[index], 1f, 0.3f));
		}
		
		public static Tile createWater(int index){
			return new TileLiquid(new ModelTexture(WATER_TEXTURE, 0f, 0f));
		}
		
		public static Tile createSorgRockSlopeHalfNorthFalseDown4(int index){
			return new TileHalfSlope(new ModelTexture(SORG_ROCK_TEXTURES[index], 1.3f, 0.2f), Facing.NORTH, 4, false, false);
		}
		
		public static Tile createSorgRockSlopeHalfEastFalseDown4(int index){
			return new TileHalfSlope(new ModelTexture(SORG_ROCK_TEXTURES[index], 1.3f, 0.2f), Facing.EAST, 4, false, false);
		}
		
		public static Tile createSorgRockSlopeHalfSouthFalseDown4(int index){
			return new TileHalfSlope(new ModelTexture(SORG_ROCK_TEXTURES[index], 1.3f, 0.2f), Facing.SOUTH, 4, false, false);
		}
		
		public static Tile createSorgRockSlopeHalfWestFalseDown4(int index){
			return new TileHalfSlope(new ModelTexture(SORG_ROCK_TEXTURES[index], 1.3f, 0.2f), Facing.WEST, 4, false, false);
		}
		
		public static Tile createSorgRockSlopeHalfNorthTrueDown4(int index){
			return new TileHalfSlope(new ModelTexture(SORG_ROCK_TEXTURES[index], 1.3f, 0.2f), Facing.NORTH, 4, true, false);
		}
		
		public static Tile createSorgRockSlopeHalfEastTrueDown4(int index){
			return new TileHalfSlope(new ModelTexture(SORG_ROCK_TEXTURES[index], 1.3f, 0.2f), Facing.EAST, 4, true, false);
		}
		
		public static Tile createSorgRockSlopeHalfSouthTrueDown4(int index){
			return new TileHalfSlope(new ModelTexture(SORG_ROCK_TEXTURES[index], 1.3f, 0.2f), Facing.SOUTH, 4, true, false);
		}
		
		public static Tile createSorgRockSlopeHalfWestTrueDown4(int index){
			return new TileHalfSlope(new ModelTexture(SORG_ROCK_TEXTURES[index], 1.3f, 0.2f), Facing.WEST, 4, true, false);
		}
		
		public static Tile createSorgRockSlopeHalfNorthFalseUp4(int index){
			return new TileHalfSlope(new ModelTexture(SORG_ROCK_TEXTURES[index], 1.3f, 0.2f), Facing.NORTH, 4, false, true);
		}
		
		public static Tile createSorgRockSlopeHalfEastFalseUp4(int index){
			return new TileHalfSlope(new ModelTexture(SORG_ROCK_TEXTURES[index], 1.3f, 0.2f), Facing.EAST, 4, false, true);
		}
		
		public static Tile createSorgRockSlopeHalfSouthFalseUp4(int index){
			return new TileHalfSlope(new ModelTexture(SORG_ROCK_TEXTURES[index], 1.3f, 0.2f), Facing.SOUTH, 4, false, true);
		}
		
		public static Tile createSorgRockSlopeHalfWestFalseUp4(int index){
			return new TileHalfSlope(new ModelTexture(SORG_ROCK_TEXTURES[index], 1.3f, 0.2f), Facing.WEST, 4, false, true);
		}
		
		public static Tile createSorgRockSlopeHalfNorthTrueUp4(int index){
			return new TileHalfSlope(new ModelTexture(SORG_ROCK_TEXTURES[index], 1.3f, 0.2f), Facing.NORTH, 4, true, true);
		}
		
		public static Tile createSorgRockSlopeHalfEastTrueUp4(int index){
			return new TileHalfSlope(new ModelTexture(SORG_ROCK_TEXTURES[index], 1.3f, 0.2f), Facing.EAST, 4, true, true);
		}
		
		public static Tile createSorgRockSlopeHalfSouthTrueUp4(int index){
			return new TileHalfSlope(new ModelTexture(SORG_ROCK_TEXTURES[index], 1.3f, 0.2f), Facing.SOUTH, 4, true, true);
		}
		
		public static Tile createSorgRockSlopeHalfWestTrueUp4(int index){
			return new TileHalfSlope(new ModelTexture(SORG_ROCK_TEXTURES[index], 1.3f, 0.2f), Facing.WEST, 4, true, true);
		}
		
		public static Tile createBlueGrassSlopeHalfNorthFalseDown1(int index){
			return new TileHalfSlope(new ModelTexture(BLUE_GRASS_TEXTURES[index], 1f, 0.1f), Facing.NORTH, 1, false, false);
		}
		
		public static Tile createBlueGrassSlopeHalfEastFalseDown1(int index){
			return new TileHalfSlope(new ModelTexture(BLUE_GRASS_TEXTURES[index], 1f, 0.1f), Facing.EAST, 1, false, false);
		}
		
		public static Tile createBlueGrassSlopeHalfSouthFalseDown1(int index){
			return new TileHalfSlope(new ModelTexture(BLUE_GRASS_TEXTURES[index], 1f, 0.1f), Facing.SOUTH, 1, false, false);
		}
		
		public static Tile createBlueGrassSlopeHalfWestFalseDown1(int index){
			return new TileHalfSlope(new ModelTexture(BLUE_GRASS_TEXTURES[index], 1f, 0.1f), Facing.WEST, 1, false, false);
		}
		
		public static Tile createBlueGrassSlopeHalfNorthTrueDown1(int index){
			return new TileHalfSlope(new ModelTexture(BLUE_GRASS_TEXTURES[index], 1f, 0.1f), Facing.NORTH, 1, true, false);
		}
		
		public static Tile createBlueGrassSlopeHalfEastTrueDown1(int index){
			return new TileHalfSlope(new ModelTexture(BLUE_GRASS_TEXTURES[index], 1f, 0.1f), Facing.EAST, 1, true, false);
		}
		
		public static Tile createBlueGrassSlopeHalfSouthTrueDown1(int index){
			return new TileHalfSlope(new ModelTexture(BLUE_GRASS_TEXTURES[index], 1f, 0.1f), Facing.SOUTH, 1, true, false);
		}
		
		public static Tile createBlueGrassSlopeHalfWestTrueDown1(int index){
			return new TileHalfSlope(new ModelTexture(BLUE_GRASS_TEXTURES[index], 1f, 0.1f), Facing.WEST, 1, true, false);
		}
		
		public static Tile createBlueGrassSlopeHalfNorthFalseUp1(int index){
			return new TileHalfSlope(new ModelTexture(BLUE_GRASS_TEXTURES[index], 1f, 0.1f), Facing.NORTH, 1, false, true);
		}
		
		public static Tile createBlueGrassSlopeHalfEastFalseUp1(int index){
			return new TileHalfSlope(new ModelTexture(BLUE_GRASS_TEXTURES[index], 1f, 0.1f), Facing.EAST, 1, false, true);
		}
		
		public static Tile createBlueGrassSlopeHalfSouthFalseUp1(int index){
			return new TileHalfSlope(new ModelTexture(BLUE_GRASS_TEXTURES[index], 1f, 0.1f), Facing.SOUTH, 1, false, true);
		}
		
		public static Tile createBlueGrassSlopeHalfWestFalseUp1(int index){
			return new TileHalfSlope(new ModelTexture(BLUE_GRASS_TEXTURES[index], 1f, 0.1f), Facing.WEST, 1, false, true);
		}
		
		public static Tile createBlueGrassSlopeHalfNorthTrueUp1(int index){
			return new TileHalfSlope(new ModelTexture(BLUE_GRASS_TEXTURES[index], 1f, 0.1f), Facing.NORTH, 1, true, true);
		}
		
		public static Tile createBlueGrassSlopeHalfEastTrueUp1(int index){
			return new TileHalfSlope(new ModelTexture(BLUE_GRASS_TEXTURES[index], 1f, 0.1f), Facing.EAST, 1, true, true);
		}
		
		public static Tile createBlueGrassSlopeHalfSouthTrueUp1(int index){
			return new TileHalfSlope(new ModelTexture(BLUE_GRASS_TEXTURES[index], 1f, 0.1f), Facing.SOUTH, 1, true, true);
		}
		
		public static Tile createBlueGrassSlopeHalfWestTrueUp1(int index){
			return new TileHalfSlope(new ModelTexture(BLUE_GRASS_TEXTURES[index], 1f, 0.1f), Facing.WEST, 1, true, true);
		}
	}
}
