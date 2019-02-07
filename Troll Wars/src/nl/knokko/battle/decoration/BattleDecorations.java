package nl.knokko.battle.decoration;

import nl.knokko.util.color.Color;
import nl.knokko.view.light.DefaultLight;
import nl.knokko.view.light.Light;

public class BattleDecorations {
	
	public static final Color BACKGROUND_OUTSIDE = new Color(100, 100, 255);
	public static final Color BACKGROUND_INSIDE = new Color(50, 50, 70);
	
	public static final Light LIGHT_OUTSIDE = new DefaultLight();
	
	public static final BattleDecoration SORG_MOUNTAINS = new SimpleBattleDecoration((byte) 0, BACKGROUND_OUTSIDE, LIGHT_OUTSIDE);
	public static final BattleDecoration SORG_CAVE = new SimpleBattleDecoration((byte) 1, BACKGROUND_INSIDE, LIGHT_OUTSIDE);
	
	private static final BattleDecoration[] DECORATIONS = {
			SORG_MOUNTAINS, SORG_CAVE
	};
	
	public static BattleDecoration fromID(byte id) {
		return DECORATIONS[id & 0xFF];
	}
}