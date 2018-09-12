package nl.knokko.texture.area;

public class TextureArea {
	
	private int minX;
	private int minY;
	
	private final int width;
	private final int height;

	public TextureArea(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public TextureArea(float width, float height){
		this((int) width, (int) height);
	}
	
	public TextureArea(double width, double height){
		this((int) width, (int) height);
	}
	
	public int getMinX(){
		return minX;
	}
	
	public int getMinY(){
		return minY;
	}
	
	public int getMaxX(){
		return minX + width;
	}
	
	public int getMaxY(){
		return minY + height;
	}
	
	public float getMinU(int textureWidth){
		return (float) minX / textureWidth;
	}
	
	public float getMinV(int textureHeight){
		return (float) minY / textureHeight;
	}
	
	public float getMaxU(int textureWidth){
		return (float) getMaxX() / textureWidth;
	}
	
	public float getMaxV(int textureHeight){
		return (float) getMaxY() / textureHeight;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public void setMinX(int x){
		minX = x;
	}
	
	public void setMinY(int y){
		minY = y;
	}
}
