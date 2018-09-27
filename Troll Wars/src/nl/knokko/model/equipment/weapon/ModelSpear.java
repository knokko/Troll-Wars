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
package nl.knokko.model.equipment.weapon;

public interface ModelSpear {
	
	float stickRadius();
	
	float stickLength();
	
	float pointMaxRadius();
	
	float pointLength();
	
	public static class Factory {
		
		public static ModelSpear createSimple(){
			return createInstance(0.1f, 1.5f, 0.2f, 1.0f);
		}
		
		public static ModelSpear createInstance(final float stickRadius, final float stickLength, final float pointMaxRadius, final float pointLength){
			return new ModelSpear(){

				@Override
				public float stickRadius() {
					return stickRadius;
				}

				@Override
				public float stickLength() {
					return stickLength;
				}

				@Override
				public float pointMaxRadius() {
					return pointMaxRadius;
				}

				@Override
				public float pointLength() {
					return pointLength;
				}
			};
		}
	}
}
