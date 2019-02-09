package nl.knokko.battle.decoration;

import nl.knokko.util.color.Color;
import nl.knokko.view.light.DefaultLight;
import nl.knokko.view.light.Light;

public class BattleDecorations {
	
	public static final Color BACKGROUND_OUTSIDE = new Color(100, 100, 255);
	public static final Color BACKGROUND_INSIDE = new Color(50, 50, 70);
	
	public static final Light LIGHT_OUTSIDE = new DefaultLight();
	
	public static final byte ID_SORG_MOUNTAINS = 0;
	public static final byte ID_SORG_CAVE = 1;
	
	public static final BattleDecoration SORG_MOUNTAINS = new DecorationSorgMountains();
	public static final BattleDecoration SORG_CAVE = new DecorationSorgCave();
	
	private static final BattleDecoration[] DECORATIONS = {
			SORG_MOUNTAINS, SORG_CAVE
	};
	
	public static BattleDecoration fromID(byte id) {
		return DECORATIONS[id & 0xFF];
	}
}