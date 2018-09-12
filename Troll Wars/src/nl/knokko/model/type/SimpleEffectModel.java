package nl.knokko.model.type;

public class SimpleEffectModel {
	
	private int vaoID;
	private int vertexCount;
	
	public SimpleEffectModel(int vaoID, int vertexCount) {
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
	}
	
	public int getVaoID(){
		return vaoID;
	}
	
	public int getVertexCount(){
		return vertexCount;
	}
}