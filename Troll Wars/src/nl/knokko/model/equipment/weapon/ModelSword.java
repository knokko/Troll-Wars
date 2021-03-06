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
package nl.knokko.model.equipment.weapon;

public interface ModelSword {
	
	float handleRadius();
	
	float handleLength();
	
	float middleLength();
	
	float middleWidth();
	
	float bladeLength();
	
	float bladeWidth();
	
	float bladeDepth();
	
	public static class Factory {
		
		public static ModelSword createSimple(){
			return createInstance(1.5f, 4.9f, 8f, 2.5f, 29.25f, 4f, 2f);
		}
		
		public static ModelSword createInstance(final float handleRadius, final float handleLength,
				final float middleLength, final float middleWidth, final float bladeLength,
				final float bladeWidth, final float bladeDepth){
			return new ModelSword(){

				@Override
				public float handleRadius() {
					return handleRadius;
				}

				@Override
				public float handleLength() {
					return handleLength;
				}

				@Override
				public float middleLength() {
					return middleLength;
				}

				@Override
				public float middleWidth() {
					return middleWidth;
				}

				@Override
				public float bladeLength() {
					return bladeLength;
				}

				@Override
				public float bladeWidth() {
					return bladeWidth;
				}

				@Override
				public float bladeDepth() {
					return bladeDepth;
				}
			};
		}
	}
}