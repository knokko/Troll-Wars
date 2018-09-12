package nl.knokko.model.type;

import nl.knokko.shaders.ShaderType;

public class SpiritModel extends AbstractModel {

	public SpiritModel(int vaoID, int vertexCount) {
		super(vaoID, vertexCount);
	}

	@Override
	public ShaderType getShaderType() {
		return ShaderType.SPIRIT;
	}
}