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
package nl.knokko.model.equipment.weapon;

public interface ModelBone {
	
	float boneLength();
	
	float boneRadiusX();
	
	float boneRadiusZ();
	
	float boneRadiusTopX();
	
	float boneRadiusTopY();
	
	float boneRadiusTopZ();
	
	float maxRandomFactor();
	
	long seed();
	
	public static class Factory {
		
		public static ModelBone createInstance(final float boneLength, final float boneRadiusX, final float boneRadiusZ, final float boneRadiusTopX, final float boneRadiusTopY, final float boneRadiusTopZ, final float maxRandomFactor, final long seed){
			return new ModelBone(){

				@Override
				public float boneLength() {
					return boneLength;
				}

				@Override
				public float boneRadiusX() {
					return boneRadiusX;
				}

				@Override
				public float boneRadiusZ() {
					return boneRadiusZ;
				}
				
				@Override
				public float boneRadiusTopX(){
					return boneRadiusTopX;
				}
				
				@Override
				public float boneRadiusTopY(){
					return boneRadiusTopY;
				}
				
				@Override
				public float boneRadiusTopZ(){
					return boneRadiusTopZ;
				}

				@Override
				public float maxRandomFactor() {
					return maxRandomFactor;
				}

				@Override
				public long seed() {
					return seed;
				}
				
			};
		}
	}
}
