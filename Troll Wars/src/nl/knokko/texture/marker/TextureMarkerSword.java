package nl.knokko.texture.marker;

import nl.knokko.model.equipment.weapon.ModelSword;
import nl.knokko.texture.area.TextureArea;

public class TextureMarkerSword extends TextureMarker {

	public TextureMarkerSword(ModelSword sword, float scale, int ppm) {
		super(
				createRectangle(sword.handleRadius() * 2 * scale, sword.handleRadius() * 2 * scale, ppm),
				createCilinder(sword.handleLength() * scale, sword.handleRadius() * scale, sword.handleRadius() * scale, ppm),
				createRectangle(sword.middleLength() * scale, sword.middleWidth() * scale, ppm),
				createRectangle(sword.middleLength() * scale, sword.middleWidth() * scale, ppm),
				createRectangle(sword.middleWidth() * scale, sword.middleLength() * scale, ppm),
				createRectangle(sword.middleWidth() * scale, sword.middleLength() * scale, ppm),
				createRectangle(sword.middleWidth() * scale, sword.middleWidth() * scale, ppm),
				createRectangle(sword.middleWidth() * scale, sword.middleWidth() * scale, ppm),
				createRectangle(sword.bladeLength() * scale, sword.bladeWidth() * scale, ppm),
				createRectangle(sword.bladeLength() * scale, sword.bladeWidth() * scale, ppm)
			);
	}
	
	public TextureArea getHandleBottom(){
		return areas[0];
	}
	
	public TextureArea getHandle(){
		return areas[1];
	}
	
	public TextureArea getMiddleFront(){
		return areas[2];
	}
	
	public TextureArea getMiddleBack(){
		return areas[3];
	}
	
	public TextureArea getMiddleTop(){
		return areas[4];
	}
	
	public TextureArea getMiddleBottom(){
		return areas[5];
	}
	
	public TextureArea getMiddleLeft(){
		return areas[6];
	}
	
	public TextureArea getMiddleRight(){
		return areas[7];
	}
	
	public TextureArea getBladeFront(){
		return areas[8];
	}
	
	public TextureArea getBladeBack(){
		return areas[9];
	}
}