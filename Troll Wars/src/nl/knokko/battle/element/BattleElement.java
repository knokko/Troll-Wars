package nl.knokko.battle.element;

import nl.knokko.util.color.Color;
import nl.knokko.util.color.ColorAlpha;

public enum BattleElement {
	
	PHYSICAL(1f, new Color(120, 120, 120)),
	ROCK(1f, new Color(75, 30, 0)),
	METAL(1f, new Color(160, 160, 160)),
	WIND(0.9f, new Color(0, 150, 150)),
	WATER(0.5f, new Color(0, 50, 200)),
	LIGHT(0.3f, new Color(255, 255, 255)),
	FIRE(0f, new Color(200, 0, 0)),
	POISON(0f, new Color(0, 200, 50)),
	ELECTRIC(0f, new Color(250, 200, 0)),
	SPIRITUAL(0f, new ColorAlpha(0, 150, 250, 100)),
	PSYCHIC(0f, new Color(250, 0, 200));
	
	public static final byte ID_BITCOUNT = 4;
	
	public static BattleElement fromID(byte id){
		return values()[id];
	}
	
	public static int count(){
		return values().length;
	}
	
	private final float phys;
	
	private final Color color;
	
	BattleElement(float phys, Color color){
		this.phys = phys;
		this.color = color;
	}
	
	public String getName(){
		String name = name();
		return name.charAt(0) + name.substring(1).toLowerCase();
	}
	
	public float getPhysicalProtection(){
		return phys;
	}
	
	public float getMagicProtection(){
		return 1 - phys;
	}
	
	public byte getID(){
		return (byte) ordinal();
	}
	
	public Color getColor(){
		return color;
	}
}
