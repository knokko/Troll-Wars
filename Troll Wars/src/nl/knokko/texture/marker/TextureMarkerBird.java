package nl.knokko.texture.marker;

import nl.knokko.main.Game;
import nl.knokko.model.body.BodyBird;
import nl.knokko.texture.area.TextureArea;

public class TextureMarkerBird extends TextureMarker {

	private TextureMarkerBird(BodyBird model, int ppm) {
		super(
				createSphere(model.headHeight(), model.headWidth(), model.headDepth(), ppm),
				createApproachingCilinder(model.snailRadius(), 0, model.snailLength(), ppm),
				createSphere(model.bellyHeight(), model.bellyWidth(), model.bellyDepth(), ppm),
				createRectangle(model.wingPartMaxLength(), model.wingPartHeight() * model.wingPartLengths().length, ppm),
				createRectangle(model.wingPartMaxLength(), model.wingPartHeight() * model.wingPartLengths().length, ppm),
				createCilinder(model.legLength(), model.legRadius(), model.legRadius(), ppm),
				createCilinder(model.legLength(), model.legRadius(), model.legRadius(), ppm),
				createCilinder(model.nailLength(), model.nailAmount() * model.nailRadius(), model.nailAmount() * model.nailRadius(), ppm),
				createCilinder(model.nailLength(), model.nailAmount() * model.nailRadius(), model.nailAmount() * model.nailRadius(), ppm)
				);
	}
	
	public TextureMarkerBird(BodyBird model){
		this(model, Game.getOptions().pixelsPerMeter);
	}
	
	public TextureArea getHead(){
		return areas[0];
	}
	
	public TextureArea getSnail(){
		return areas[1];
	}
	
	public TextureArea getBelly(){
		return areas[2];
	}
	
	public TextureArea getLeftWing(){
		return areas[3];
	}
	
	public TextureArea getRightWing(){
		return areas[4];
	}
	
	public TextureArea getLeftLeg(){
		return areas[5];
	}
	
	public TextureArea getRightLeg(){
		return areas[6];
	}
	
	public TextureArea getLeftClaw(){
		return areas[7];
	}
	
	public TextureArea getRightClaw(){
		return areas[8];
	}
}
