package nl.knokko.texture.marker;

import nl.knokko.model.body.BodyMyrre;
import nl.knokko.texture.area.TextureArea;

public class TextureMarkerMyrre extends TextureMarker {

	public TextureMarkerMyrre(BodyMyrre body, int ppm) {
		super(
				createSphere(body.bellyHeight(), body.bellyWidth(), body.bellyDepth(), ppm),
				createCilinder(body.armLength(), body.armRadius() * 2, body.armRadius() * 2, ppm),
				createCilinder(body.armLength(), body.armRadius() * 2, body.armRadius() * 2, ppm),
				createRectangle(body.nailHandLength(), body.armRadius() * 2 / 3, ppm),
				createRectangle(body.nailHandLength(), body.armRadius() * 2 / 3, ppm),
				createCilinder(body.legLength(), body.legRadius() * 2, body.legRadius() * 2, ppm),
				createCilinder(body.legLength(), body.legRadius() * 2, body.legRadius() * 2, ppm),
				createRectangle(body.nailFootLength(), body.legRadius() * 2 / 3, ppm),
				createRectangle(body.nailFootLength(), body.legRadius() * 2 / 3, ppm)
		);
	}
	
	public TextureArea getBelly(){
		return areas[0];
	}
	
	public TextureArea getLeftArm(){
		return areas[1];
	}
	
	public TextureArea getRightArm(){
		return areas[2];
	}
	
	public TextureArea getLeftHandClaws(){
		return areas[3];
	}
	
	public TextureArea getRightHandClaws(){
		return areas[4];
	}
	
	public TextureArea getLeftLeg(){
		return areas[5];
	}
	
	public TextureArea getRightLeg(){
		return areas[6];
	}
	
	public TextureArea getLeftFootClaws(){
		return areas[7];
	}
	
	public TextureArea getRightFootClaws(){
		return areas[8];
	}
}