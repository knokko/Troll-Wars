package nl.knokko.texture.marker;

import nl.knokko.main.Game;
import nl.knokko.model.body.BodyTroll;
import nl.knokko.texture.area.TextureArea;
import static java.lang.Math.PI;

public class TextureMarkerTroll extends TextureMarker {
	
	/**
	 * @param model The troll body that will be used to create the model for this texture.
	 * @param ppm Pixels Per Meter (the amount of pixels for every meter)
	 */
	private TextureMarkerTroll(BodyTroll model, int ppm) {
		super(
				createSphere(model.bellyHeight(), model.bellyWidth(), model.bellyDepth(), ppm),
				createCilinder(model.bellyHeight(), model.bellyWidth(), model.bellyDepth(), ppm),
				createApproachingCilinder(model.shoulderRadius(), model.elbowRadius(), model.upperArmLength(), ppm),
				createApproachingCilinder(model.shoulderRadius(), model.elbowRadius(), model.upperArmLength(), ppm),
				createApproachingCilinder(model.wristRadius(), model.elbowRadius(), model.underArmLength(), ppm),
				createApproachingCilinder(model.wristRadius(), model.elbowRadius(), model.underArmLength(), ppm),
				createApproachingCilinder(model.legUpperRadius(), model.kneeRadius(), model.upperLegLength(), ppm),
				createApproachingCilinder(model.legUpperRadius(), model.kneeRadius(), model.upperLegLength(), ppm),
				createApproachingCilinder(model.legUnderRadius(), model.kneeRadius(), model.underLegLength(), ppm),
				createApproachingCilinder(model.legUnderRadius(), model.kneeRadius(), model.underLegLength(), ppm),
				new TextureArea(2 * model.footFrontLength() * ppm + model.footWidth() * PI * ppm, model.footWidth() * 2 * ppm),
				new TextureArea(2 * model.footFrontLength() * ppm + model.footWidth() * PI * ppm, model.footWidth() * 2 * ppm),
				createSphere(model.shoulderRadius(), model.shoulderRadius(), model.shoulderRadius(), ppm),
				createSphere(model.shoulderRadius(), model.shoulderRadius(), model.shoulderRadius(), ppm),
				new TextureArea((model.bellyWidth() + model.bellyDepth()) * 0.5 * PI * ppm, 0.25 * model.bellyDepth() * PI * ppm),
				new TextureArea(2 * Math.hypot(model.noseWidth() / 2, model.noseHeight()) * ppm, (model.noseLengthBack()  * 2 + model.noseLengthFront() + Math.hypot(model.noseLengthFront(), model.noseHeight())) * ppm),
				new TextureArea(2 * (model.handHeight() + model.handWidth()) * ppm, (model.handCoreLength() + model.fingerLength()) * ppm),
				new TextureArea(2 * (model.handHeight() + model.handWidth()) * ppm, (model.handCoreLength() + model.fingerLength()) * ppm),
				createSphere(model.kneeRadius(), model.kneeRadius(), model.kneeRadius(), ppm),
				createSphere(model.kneeRadius(), model.kneeRadius(), model.kneeRadius(), ppm)
				);
	}
	
	public TextureMarkerTroll(BodyTroll model){
		this(model, Game.getOptions().pixelsPerMeter);
	}
	
	public TextureArea getHead(){
		return areas[0];
	}
	
	public TextureArea getBelly(){
		return areas[1];
	}
	
	public TextureArea getLeftUpperArm(){
		return areas[2];
	}
	
	public TextureArea getRightUpperArm(){
		return areas[3];
	}
	
	public TextureArea getLeftUnderArm(){
		return areas[4];
	}
	
	public TextureArea getRightUnderArm(){
		return areas[5];
	}
	
	public TextureArea getLeftUpperLeg(){
		return areas[6];
	}
	
	public TextureArea getRightUpperLeg(){
		return areas[7];
	}
	
	public TextureArea getLeftUnderLeg(){
		return areas[8];
	}
	
	public TextureArea getRightUnderLeg(){
		return areas[9];
	}
	
	public TextureArea getLeftFoot(){
		return areas[10];
	}
	
	public TextureArea getRightFoot(){
		return areas[11];
	}
	
	public TextureArea getLeftShoulder(){
		return areas[12];
	}
	
	public TextureArea getRightShoulder(){
		return areas[13];
	}
	
	public TextureArea getBellyTop(){
		return areas[14];
	}
	
	public TextureArea getNose(){
		return areas[15];
	}
	
	public TextureArea getLeftHand(){
		return areas[16];
	}
	
	public TextureArea getRightHand(){
		return areas[17];
	}
	
	public TextureArea getLeftKnee(){
		return areas[18];
	}
	
	public TextureArea getRightKnee(){
		return areas[19];
	}
}
