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

public class ArrayElementStats implements ElementalStatistics {
	
	private final float[] resistances;
	private final float[] powers;

	public ArrayElementStats() {
		resistances = new float[BattleElement.count()];
		powers = new float[BattleElement.count()];
	}
	
	public ArrayElementStats setPower(BattleElement element, float power){
		powers[element.ordinal()] = power;
		return this;
	}
	
	public ArrayElementStats setResistance(BattleElement element, float resistance){
		resistances[element.ordinal()] = resistance;
		return this;
	}
	
	public ArrayElementStats setElementValues(BattleElement element, float resistance, float power){
		resistances[element.ordinal()] = resistance;
		powers[element.ordinal()] = power;
		return this;
	}

	@Override
	public float getResistance(BattleElement element) {
		return resistances[element.ordinal()];
	}

	@Override
	public float getPower(BattleElement element) {
		return powers[element.ordinal()];
	}

}
