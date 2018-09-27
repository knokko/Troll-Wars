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

public final class SimpleElementStats {
	
	public static final ElementalStatistics NEUTRAL = new ElementalStatistics(){

		@Override
		public float getResistance(BattleElement element) {
			return 0;
		}

		@Override
		public float getPower(BattleElement element) {
			return 1;
		}
		
	};
	
	public static final ElementalStatistics TROLL = createInstance(0.1f, 0.1f, 0, 0, 0.2f, -0.1f, -0.3f, 0.1f, -0.1f, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
	public static final ElementalStatistics BIRD = createInstance(0, -0.5f, -0.3f, -0.5f, 0.3f, 0.5f, -0.5f, -0.3f, 1f, 0, 0, 1, 1, 1, 1.5f, 1, 1, 1, 1, 1, 1, 1);
	public static final ElementalStatistics HUMAN = createInstance(0f, -0.2f, -0.25f, 0.1f, 0.3f, 0.6f, -0.7f, -0.5f, -0.7f, 0.3f, 0f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f);
	public static final ElementalStatistics MYRRE = createInstance(1f, 0.9f, 0.9f, -0.3f, -0.3f, -0.8f, -0.4f, 1f, -0.8f, -1f, -1f, 0.5f, 0.5f, 0.5f, 1f, 1f, 0.5f, 1.5f, 1.5f, 1f, 2f, 2f);

	public static ElementalStatistics createInstance(
			final float physicalProtection, final float rockProtection, final float metalProtection, final float windProtection, 
			final float waterProtection, final float lightProtection, final float fireProtection, final float poisonProtection, 
			final float electricProtection, final float spiritProtection, final float psychicProtection, final float physicalPower,
			final float rockPower, final float metalPower, final float windPower, final float waterPower, final float lightPower,
			final float firePower, final float poisonPower, final float electricPower, final float spiritPower, final float psychicPower){
		return new ElementalStatistics(){

			@Override
			public float getResistance(BattleElement element) {
				switch(element){
				case ELECTRIC:
					return electricProtection;
				case FIRE:
					return fireProtection;
				case LIGHT:
					return lightProtection;
				case METAL:
					return metalProtection;
				case PHYSICAL:
					return physicalProtection;
				case POISON:
					return poisonProtection;
				case PSYCHIC:
					return psychicProtection;
				case ROCK:
					return rockProtection;
				case SPIRITUAL:
					return spiritProtection;
				case WATER:
					return waterProtection;
				case WIND:
					return windProtection;
				default:
					throw new IllegalArgumentException("Unknown element: " + element);
				}
			}

			@Override
			public float getPower(BattleElement element) {
				switch(element){
				case ELECTRIC:
					return electricPower;
				case FIRE:
					return firePower;
				case LIGHT:
					return lightPower;
				case METAL:
					return metalPower;
				case PHYSICAL:
					return physicalPower;
				case POISON:
					return poisonPower;
				case PSYCHIC:
					return psychicPower;
				case ROCK:
					return rockPower;
				case SPIRITUAL:
					return spiritPower;
				case WATER:
					return waterPower;
				case WIND:
					return windPower;
				default:
					throw new IllegalArgumentException("Unknown element: " + element);
				}
			}
			
		};
	}
}
