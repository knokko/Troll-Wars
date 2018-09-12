package nl.knokko.texture;

public class ModelTexture {
	
	private Texture texture;
	
	private float shineDamper;
	private float reflectivity;

	public ModelTexture(Texture texture, float shineDamper, float reflectivity) {
		this.texture = texture;
		this.shineDamper = shineDamper;
		this.reflectivity = reflectivity;
	}
	
	public int getTextureID(){
		return texture.getID();
	}
	
	public float getShineDamper(){
		return shineDamper;
	}
	
	public float getReflectivity(){
		return reflectivity;
	}
}
