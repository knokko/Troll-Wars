package nl.knokko.model.type;

import nl.knokko.shaders.ShaderType;

public class LiquidTileModel extends TileModel {

	public LiquidTileModel(int vaoID, int vertexCount) {
		super(vaoID, vertexCount);
	}

	@Override
	public ShaderType getShaderType() {
		return ShaderType.LIQUID;
	}
}