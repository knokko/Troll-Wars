package nl.knokko.model.type;

import nl.knokko.shaders.ShaderType;

public class DefaultTileModel extends TileModel {

	public DefaultTileModel(int vaoID, int vertexCount) {
		super(vaoID, vertexCount);
	}

	@Override
	public ShaderType getShaderType() {
		return ShaderType.NORMAL;
	}
}