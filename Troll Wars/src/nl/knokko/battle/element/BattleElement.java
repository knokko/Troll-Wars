/* 
 * The MIT License
 *
 * Copyright 2018 knokko.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
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
 */
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
