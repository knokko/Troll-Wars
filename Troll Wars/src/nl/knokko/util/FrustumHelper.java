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
package nl.knokko.util;

import org.lwjgl.util.vector.Matrix4f;

public class FrustumHelper {
	
	private static final int NUM_PLANES = 6;
	
	private static final Vector4[] FRUSTUM_PLANES = new Vector4[NUM_PLANES];
	
	static {
		for(int index = 0; index < NUM_PLANES; index++)
			FRUSTUM_PLANES[index] = new Vector4();
	}
	
	private static Matrix4 projView;
	
	public static void setProjViewMatrix(Matrix4f matrix){
		projView = new Matrix4(matrix);
		for(int index = 0; index < NUM_PLANES; index++)
			projView.frustumPlane(index, FRUSTUM_PLANES[index]);
	}
	
	public static boolean insideFrustum(float x0, float y0, float z0, float boundingRadius) {
	    boolean result = true;
	    for(int i = 0; i < NUM_PLANES; i++) {
	        Vector4 plane = FRUSTUM_PLANES[i];
	        if(plane.x * x0 + plane.y * y0 + plane.z * z0 + plane.w <= -boundingRadius)
	            return false;
	    }
	    return result;
	}
	
	private static class Matrix4 {
		
		private static final int PLANE_NX = 0;
		private static final int PLANE_PX = 1;
		private static final int PLANE_NY = 2;
		private static final int PLANE_PY = 3;
		private static final int PLANE_NZ = 4;
		private static final int PLANE_PZ = 5;
		
		float m00, m01, m02, m03;
		float m10, m11, m12, m13;
		float m20, m21, m22, m23;
		float m30, m31, m32, m33;
		
		public Matrix4(Matrix4f source){
			m00 = source.m00;
			m01 = source.m01;
			m02 = source.m02;
			m03 = source.m03;
			m10 = source.m10;
			m11 = source.m11;
			m12 = source.m12;
			m13 = source.m13;
			m20 = source.m20;
			m21 = source.m21;
			m22 = source.m22;
			m23 = source.m23;
			m30 = source.m30;
			m31 = source.m31;
			m32 = source.m32;
			m33 = source.m33;
		}

		public Vector4 frustumPlane(int plane, Vector4 planeEquation) {
			switch (plane) {
			case PLANE_NX:
				planeEquation.set(m03 + m00, m13 + m10, m23 + m20, m33 + m30).normalize3(planeEquation);
				break;
			case PLANE_PX:
				planeEquation.set(m03 - m00, m13 - m10, m23 - m20, m33 - m30).normalize3(planeEquation);
				break;
			case PLANE_NY:
				planeEquation.set(m03 + m01, m13 + m11, m23 + m21, m33 + m31).normalize3(planeEquation);
				break;
			case PLANE_PY:
				planeEquation.set(m03 - m01, m13 - m11, m23 - m21, m33 - m31).normalize3(planeEquation);
				break;
			case PLANE_NZ:
				planeEquation.set(m03 + m02, m13 + m12, m23 + m22, m33 + m32).normalize3(planeEquation);
				break;
			case PLANE_PZ:
				planeEquation.set(m03 - m02, m13 - m12, m23 - m22, m33 - m32).normalize3(planeEquation);
				break;
			default:
				throw new IllegalArgumentException("plane"); //$NON-NLS-1$
			}
			return planeEquation;
		}
	}
	
	private static class Vector4 {
		
		public float x;
		public float y;
		public float z;
		public float w;

		public Vector4() {}
		
		public Vector4 set(float x, float y, float z, float w){
			this.x = x;
			this.y = y;
			this.z = z;
			this.w = w;
			return this;
		}
		
		public Vector4 normalize3(Vector4 dest) {
	        float invLength = 1.0f / (float) Math.sqrt(x * x + y * y + z * z);
	        dest.x = x * invLength;
	        dest.y = y * invLength;
	        dest.z = z * invLength;
	        dest.w = w * invLength;
	        return dest;
	    }
	}
}