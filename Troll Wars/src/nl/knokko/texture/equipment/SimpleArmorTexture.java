package nl.knokko.texture.equipment;

import nl.knokko.texture.pattern.TexturePattern;
import nl.knokko.texture.pattern.equipment.*;

public class SimpleArmorTexture implements ArmorTexture {
	
	protected final float shineDamper;
	protected final float reflectivity;
	
	protected final PatternHelmet helmet;
	protected final PatternChestplateTop chestplateTop;
	protected final PatternChestplate chestplate;
	protected final PatternUpperArm upperArm;
	protected final PatternElbow elbow;
	protected final PatternUnderArm underArm;
	protected final PatternGlobe globe;
	protected final PatternUpperLeg upperLeg;
	protected final PatternKnee knee;
	protected final PatternUnderLeg underLeg;
	protected final PatternShoe shoe;

	public SimpleArmorTexture(TexturePattern basePattern, float shineDamper, float reflectivity) {
		this.shineDamper = shineDamper;
		this.reflectivity = reflectivity;
		helmet = new PatternHelmet(basePattern);
		chestplateTop = new PatternChestplateTop(basePattern);
		chestplate = new PatternChestplate(basePattern);
		upperArm = new PatternUpperArm(basePattern);
		elbow = new PatternElbow(basePattern);
		underArm = new PatternUnderArm(basePattern);
		globe = new PatternGlobe(basePattern);
		upperLeg = new PatternUpperLeg(basePattern);
		knee = new PatternKnee(basePattern);
		underLeg = new PatternUnderLeg(basePattern);
		shoe = new PatternShoe(basePattern);
	}
	
	public float getShineDamper(){
		return shineDamper;
	}
	
	public float getReflectivity(){
		return reflectivity;
	}
	
	public PatternHelmet getHelmet(){
		return helmet;
	}
	
	public PatternChestplateTop getChestplateTop(){
		return chestplateTop;
	}
	
	public PatternChestplate getChestplate(){
		return chestplate;
	}
	
	public PatternUpperArm getUpperArm(){
		return upperArm;
	}
	
	public PatternElbow getElbow(){
		return elbow;
	}
	
	public PatternUnderArm getUnderArm(){
		return underArm;
	}
	
	public PatternGlobe getGlobe(){
		return globe;
	}
	
	public PatternUpperLeg getUpperLeg(){
		return upperLeg;
	}
	
	public PatternKnee getKnee(){
		return knee;
	}
	
	public PatternUnderLeg getUnderLeg(){
		return underLeg;
	}
	
	public PatternShoe getShoe(){
		return shoe;
	}
}