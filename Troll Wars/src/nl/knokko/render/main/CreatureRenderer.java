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