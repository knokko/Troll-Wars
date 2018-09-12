package nl.knokko.texture.equipment;

import nl.knokko.texture.pattern.equipment.*;

public interface ArmorTexture {
	
	float getShineDamper();
	
	float getReflectivity();
	
	PatternHelmet getHelmet();
	
	PatternChestplateTop getChestplateTop();
	
	PatternChestplate getChestplate();
	
	PatternUpperArm getUpperArm();
	
	PatternElbow getElbow();
	
	PatternUnderArm getUnderArm();
	
	PatternGlobe getGlobe();
	
	PatternUpperLeg getUpperLeg();
	
	PatternKnee getKnee();
	
	PatternUnderLeg getUnderLeg();
	
	PatternShoe getShoe();
}