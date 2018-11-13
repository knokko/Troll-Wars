/*******************************************************************************
 * The MIT License
 *
 * Copyright (c) 2018 knokko
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
package nl.knokko.battle.damage;

public enum AttackType {
	
	SLASH(2f, 0),
	STAB(2.4f, 0),
	SMASH(1.6f, 0),
	PUNCH(1.6f, 5),
	PRICK(2.2f, 0),
	SHOOT(2.5f, 0),
	SPELL(2f, 0),
	SWING(2.1f, 0);
	
	private final float armorBase;
	private final int baseDamage;
	
	private AttackType(float armorBase, int baseDamage){
		this.armorBase = armorBase;
		this.baseDamage = baseDamage;
	}
	
	public final float getArmorBase(){
		return armorBase;
	}
	
	public int getBaseDamage(){
		if(baseDamage > 0)
			return baseDamage;
		throw new UnsupportedOperationException("Attack type " + name() + " can't be used without weapon!");
	}
}
