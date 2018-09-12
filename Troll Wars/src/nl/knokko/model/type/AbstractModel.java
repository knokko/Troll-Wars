package nl.knokko.model.type;

import nl.knokko.shaders.ShaderType;

public abstract class AbstractModel {
	
	private final int vaoID;
	private final int vertexCount;
	
	public AbstractModel(int vaoID, int vertexCount) {
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
	}
	
	public int getVaoID(){
		return vaoID;
	}
	
	public int getVertexCount(){
		return vertexCount;
	}
	
	public abstract ShaderType getShaderType();
}