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

public interface ElementalStatistics {
	
	/**
	 * This method returns the resistance against the specified element.
	 * A resistance of 1 means complete immunity.
	 * A resistance of 0 means that the damage is not reduced or increased.
	 * A resistance of -1 means that the damage will be multiplied by 2.
	 * @param element The element to check the resistance for
	 * @return The resistance against the specified element
	 */
	float getResistance(BattleElement element);
	
	/**
	 * This method returns the power this creature will have when using the specified element.
	 * A power of 0 means that the attack will deal no damage at all.
	 * A power of 1 means that the attack will deal normal damage.
	 * A power of 2 means that the damage will be multiplied with 2.
	 * @param element The element to check the power for
	 * @return The power this creature will have when using the specified element
	 */
	float getPower(BattleElement element);
}