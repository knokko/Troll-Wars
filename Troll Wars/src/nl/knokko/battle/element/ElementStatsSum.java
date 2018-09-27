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

public class ElementStatsSum implements ElementalStatistics {
	
	private final ElementalStatistics e1;
	private final ElementalStatistics e2;

	public ElementStatsSum(ElementalStatistics stats1, ElementalStatistics stats2) {
		e1 = stats1;
		e2 = stats2;
		if(e1 == null)
			throw new NullPointerException();
		if(e2 == null)
			throw new NullPointerException();
	}

	@Override
	public float getResistance(BattleElement element) {
		return e1.getResistance(element) + e2.getResistance(element);
	}

	@Override
	public float getPower(BattleElement element) {
		return e1.getPower(element) * e2.getPower(element);
	}
}
