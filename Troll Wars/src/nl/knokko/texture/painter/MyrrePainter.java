package nl.knokko.texture.painter;

import nl.knokko.texture.pattern.PatternFill;
import nl.knokko.texture.pattern.PatternSpirit;
import nl.knokko.texture.pattern.TexturePattern;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;
import nl.knokko.util.color.Color;

public class MyrrePainter extends ModelPainter {
	
	public static final float MAX_DIF = 0.1f;
	
	protected final Color skinColor;
	protected final Color handClawColor;
	protected final Color footClawColor;

	public MyrrePainter(Color skinColor, Color handClawColor, Color footClawColor) {
		super(new PatternSpirit(skinColor, MAX_DIF, -683), 
				new PatternSpirit(skinColor, MAX_DIF, 8361), new PatternSpirit(skinColor, MAX_DIF, 14827540),
				new PatternFill(handClawColor), new PatternFill(handClawColor),
				new PatternSpirit(skinColor, MAX_DIF, -97869), new PatternSpirit(skinColor, MAX_DIF, 54968720),
				new PatternFill(footClawColor), new PatternFill(footClawColor)
		);
		this.skinColor = skinColor;
		this.handClawColor = handClawColor;
		this.footClawColor = footClawColor;
	}
	
	public MyrrePainter(BitInput input){
		this(Color.fromSimpleBits(input), Color.fromSimpleBits(input), Color.fromSimpleBits(input));
		//this(input.readSimpleColor(), input.readSimpleColor(), input.readSimpleColor());
	}

	@Override
	public void save(BitOutput output) {
		//output.addSimpleColor(skinColor);
		//output.addSimpleColor(handClawColor);
		//output.addSimpleColor(footClawColor);
		skinColor.toSimpleBits(output);
		handClawColor.toSimpleBits(output);
		footClawColor.toSimpleBits(output);
	}
	
	public TexturePattern getBelly(){
		return patterns[0];
	}
	
	public TexturePattern getLeftArm(){
		return patterns[1];
	}
	
	public TexturePattern getRightArm(){
		return patterns[2];
	}
	
	public TexturePattern getLeftHandClaw(){
		return patterns[3];
	}
	
	public TexturePattern getRightHandClaw(){
		return patterns[4];
	}
	
	public TexturePattern getLeftLeg(){
		return patterns[5];
	}
	
	public TexturePattern getRightLeg(){
		return patterns[6];
	}
	
	public TexturePattern getLeftFootClaw(){
		return patterns[7];
	}
	
	public TexturePattern getRightFootClaw(){
		return patterns[8];
	}
}