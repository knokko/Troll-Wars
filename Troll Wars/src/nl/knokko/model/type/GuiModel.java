package nl.knokko.model.type;

import nl.knokko.shaders.ShaderType;

public class GuiModel extends AbstractModel {

	public GuiModel(int vaoID, int vertexCount) {
		super(vaoID, vertexCount);
	}

	@Override
	public ShaderType getShaderType() {
		return null;
	}
}