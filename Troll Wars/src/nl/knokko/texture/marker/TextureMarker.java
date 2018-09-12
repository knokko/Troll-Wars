package nl.knokko.texture.marker;

import static java.lang.Math.PI;

import java.awt.Dimension;

import nl.knokko.texture.area.TextureArea;
import nl.knokko.util.Maths;

public class TextureMarker {
	
	public static Dimension setTextureAreas(TextureArea[] areas){
		int x = 0;
		int y = 0;
		int maxX = 0;
		int maxY = 0;
		for(TextureArea area : areas){
			area.setMinX(x);
			area.setMinY(y);
			maxX = area.getMaxX();
			if(area.getMaxY() > maxY)
				maxY = area.getMaxY();
			x = maxX + 1;
		}
		return new Dimension(Maths.next2Power(maxX), Maths.next2Power(maxY));
	}
	
	public static TextureArea createSphere(float height, float width, float depth, int ppm){
		return new TextureArea((width + depth) * 0.5 * PI * ppm, height * 0.5 * PI * ppm);
	}
	
	public static TextureArea createHalfSphere(float halfHeight, float width, float depth, int ppm){
		return new TextureArea((width + depth) * 0.5 * PI * ppm, halfHeight * 0.5 * PI * ppm);
	}
	
	public static TextureArea createCilinder(float height, float width, float depth, int ppm){
		return new TextureArea((width + depth) * 0.5 * PI * ppm, height * ppm);
	}
	
	public static TextureArea createApproachingCilinder(float radius1, float radius2, float length, int ppm){
		return new TextureArea((radius1 + radius2) * PI * ppm, length * ppm);
	}
	
	public static TextureArea createRectangle(float width, float height, int ppm){
		return new TextureArea(width * ppm, height * ppm);
	}
	
	public static TextureArea createCone(float radiusX, float radiusZ, float length, int ppm){
		float radius = (float) Math.sqrt(length * length + (radiusX + radiusZ) * (radiusX + radiusZ) / 4);
		return new TextureArea(2 * radius * ppm, 2 * radius * ppm);
	}
	
	protected TextureArea[] areas;
	
	protected int width;
	protected int height;

	public TextureMarker(TextureArea...areas) {
		this.areas = areas;
		Dimension dim = setTextureAreas(areas);
		this.width = dim.width;
		this.height = dim.height;
	}
	
	public TextureArea getArea(int index){
		return areas[index];
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getAmount(){
		return areas.length;
	}
}