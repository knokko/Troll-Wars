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
package nl.knokko.render.main;

import nl.knokko.shaders.LiquidShader;
import nl.knokko.shaders.ShaderType;
import nl.knokko.shaders.SpiritShader;
import nl.knokko.shaders.WorldShader;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

public class CreatureRenderer extends WorldRenderer {

	public CreatureRenderer() {}
	
	public void renderInstance(Matrix4f matrix, ShaderType shader, int vertexCount){
		if(shader == ShaderType.NORMAL)
			WorldShader.WORLD_SHADER.loadTransformationMatrix(matrix);
		else if(shader == ShaderType.LIQUID)
			LiquidShader.LIQUID_SHADER.loadTransformationMatrix(matrix);
		else
			SpiritShader.SPIRIT_SHADER.loadTransformationMatrix(matrix);
		GL11.glDrawElements(GL11.GL_TRIANGLES, vertexCount, GL11.GL_UNSIGNED_INT, 0);
	}
}