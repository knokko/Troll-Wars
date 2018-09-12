package nl.knokko.model.type;

import nl.knokko.shaders.ShaderType;

public class DefaultModel extends AbstractModel {

	public DefaultModel(int vaoID, int vertexCount) {
		super(vaoID, vertexCount);
	}

	@Override
	public ShaderType getShaderType() {
		return ShaderType.NORMAL;
	}
}