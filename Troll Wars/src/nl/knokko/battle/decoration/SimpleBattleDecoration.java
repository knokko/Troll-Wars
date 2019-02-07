package nl.knokko.battle.decoration;

import nl.knokko.util.color.Color;
import nl.knokko.view.light.Light;

public class SimpleBattleDecoration extends BattleDecoration {
	
	private final byte id;
	
	private final Color backgroundColor;
	private final Light light;
	
	public SimpleBattleDecoration(byte id, Color backgroundColor, Light light) {
		this.id = id;
		this.backgroundColor = backgroundColor;
		this.light = light;
	}

	@Override
	public Color getBackgroundColor() {
		return backgroundColor;
	}
	
	@Override
	public Light getLight() {
		return light;
	}
	
	@Override
	public byte getID() {
		return id;
	}
}