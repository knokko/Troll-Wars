package nl.knokko.model.type;

import nl.knokko.shaders.ShaderType;

public class BigTileModel extends TileModel {

	public BigTileModel(int vaoID, int vertexCount) {
		super(vaoID, vertexCount);
	}

	@Override
	public ShaderType getShaderType() {
		return ShaderType.BIG_NORMAL;
	}
}