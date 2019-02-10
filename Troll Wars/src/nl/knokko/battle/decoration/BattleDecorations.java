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