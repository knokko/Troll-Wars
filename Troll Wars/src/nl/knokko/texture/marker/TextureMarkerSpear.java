package nl.knokko.texture.marker;

import nl.knokko.model.equipment.weapon.ModelSpear;
import nl.knokko.texture.area.TextureArea;

public class TextureMarkerSpear extends TextureMarker {

	public TextureMarkerSpear(ModelSpear spear, float scale, int ppm) {
		super(
				createCilinder(spear.stickLength() * scale, spear.stickRadius() * scale * 2, spear.stickRadius() * scale * 2, ppm),
				createRectangle(spear.stickRadius() * scale * 2, spear.stickRadius() * scale * 2, ppm),
				createRectangle((float) (Math.sqrt(2 * spear.pointMaxRadius() * spear.pointMaxRadius()) * scale), (float) (Math.sqrt(2 * spear.pointMaxRadius() * spear.pointMaxRadius()) * scale), ppm),
				createRectangle((float) (Math.sqrt(2 * spear.pointMaxRadius() * spear.pointMaxRadius()) * scale), spear.pointLength() * scale, ppm),
				createRectangle((float) (Math.sqrt(2 * spear.pointMaxRadius() * spear.pointMaxRadius()) * scale), spear.pointLength() * scale, ppm)
				);
	}
	
	public TextureArea getStick(){
		return areas[0];
	}
	
	public TextureArea getStickBottom(){
		return areas[1];
	}
	
	public TextureArea getPointBottom(){
		return areas[2];
	}
	
	public TextureArea getPointNES(){
		return areas[3];
	}
	
	public TextureArea getPointSWN(){
		return areas[4];
	}
}
