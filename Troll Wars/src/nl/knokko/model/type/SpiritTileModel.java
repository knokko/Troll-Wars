package nl.knokko.model.type;

import nl.knokko.shaders.ShaderType;

public class SpiritTileModel extends TileModel {

	public SpiritTileModel(int vaoID, int vertexCount) {
		super(vaoID, vertexCount);
	}

	@Override
	public ShaderType getShaderType() {
		return ShaderType.SPIRIT;
	}
}