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
package nl.knokko.util.color;

import org.lwjgl.util.vector.Vector3f;

import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;
import nl.knokko.util.color.special.ColorMyrmora;

public class ColorSpecial extends Color {
	
	public static final ColorMyrmora MYRMORA = new ColorMyrmora();
	
	private static final Color[] COLORS = {
		MYRMORA
	};
	
	public static Color loadSpecialColor(BitInput bits){
		char index = bits.readChar();
		if(index >= COLORS.length)
			throw new IllegalArgumentException("Invalid special color: " + index);
		return COLORS[index];
	}
	
	public static Color get(int index){
		if(index >= COLORS.length || index < 0)
			throw new IllegalArgumentException("Invalid index (" + index + "), it must be between 0 and " + (COLORS.length - 1));
		return COLORS[index];
	}
	
	public static int amount(){
		return COLORS.length;
	}
	
	protected final char id;
	
	public ColorSpecial(char id){
		super(145, 92, 84);
		this.id = id;
	}
	
	@Override
	public void toBits(BitOutput output){
		output.addBoolean(true);
		output.addChar(id);
	}
	
	@Override
	public boolean isSpecial(){
		return true;
	}
	
	@Override
	public String toString(){
		return getClass().getSimpleName();
	}
	
	@Override
	public boolean equals(Object other){
		return other instanceof ColorSpecial && ((ColorSpecial)other).id == id;
	}
	
	@Override
	public int hashCode(){
		return id;
	}
	
	public Vector3f getAliasVector() {
		return new Vector3f((red & 0xFF) / 255f, (green & 0xFF) / 255f, (blue & 0xFF) / 255f);
	}
}