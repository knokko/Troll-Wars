package nl.knokko.model.type;

import nl.knokko.shaders.ShaderType;

public class LiquidModel extends AbstractModel {

	public LiquidModel(int vaoID, int vertexCount) {
		super(vaoID, vertexCount);
	}

	@Override
	public ShaderType getShaderType() {
		return ShaderType.LIQUID;
	}
}