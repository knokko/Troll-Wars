package nl.knokko.battle.decoration;

import nl.knokko.util.color.Color;
import nl.knokko.view.light.Light;

public abstract class BattleDecoration {
	
	public abstract Color getBackgroundColor();
	
	public abstract Light getLight();
	
	public abstract byte getID();
}