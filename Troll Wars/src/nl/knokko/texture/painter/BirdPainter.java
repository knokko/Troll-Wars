package nl.knokko.texture.painter;

import nl.knokko.texture.pattern.PatternAverage;
import nl.knokko.texture.pattern.PatternBirdFace;
import nl.knokko.texture.pattern.TexturePattern;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;
import nl.knokko.util.color.Color;

public class BirdPainter extends ModelPainter {
	
	private Color bodyColor;
	private Color wingColor;
	private Color snailColor;
	private Color clawColor;

	public BirdPainter(Color bodyColor, Color wingColor, Color snailColor, Color clawColor) {
		super(
				new PatternBirdFace(bodyColor, 0.3f, -239283),
				new PatternAverage(snailColor, 0.1f, 334),
				new PatternAverage(bodyColor, 0.3f, 4238923923L),
				new PatternAverage(wingColor, 0.2f, 230),
				new PatternAverage(wingColor, 0.2f, -1398293),
				new PatternAverage(clawColor, 0.1f, -2383632236L),
				new PatternAverage(clawColor, 0.1f, 23983827),
				new PatternAverage(clawColor, 0.1f, 1637),
				new PatternAverage(clawColor, 0.1f, 8471)
				);
		this.bodyColor = bodyColor;
		this.wingColor = wingColor;
		this.snailColor = snailColor;
		this.clawColor = clawColor;
	}
	
	public BirdPainter(BitInput bits){
		this(Color.fromSimpleBits(bits), Color.fromSimpleBits(bits), Color.fromSimpleBits(bits), Color.fromSimpleBits(bits));
		//this(bits.readSimpleColor(), bits.readSimpleColor(), bits.readSimpleColor(), bits.readSimpleColor());
	}
	
	public TexturePattern getHead(){
		return patterns[0];
	}
	
	public TexturePattern getSnail(){
		return patterns[1];
	}
	
	public TexturePattern getBelly(){
		return patterns[2];
	}
	
	public TexturePattern getLeftWing(){
		return patterns[3];
	}
	
	public TexturePattern getRightWing(){
		return patterns[4];
	}
	
	public TexturePattern getLeftLeg(){
		return patterns[5];
	}
	
	public TexturePattern getRightLeg(){
		return patterns[6];
	}
	
	public TexturePattern getLeftClaw(){
		return patterns[7];
	}
	
	public TexturePattern getRightClaw(){
		return patterns[8];
	}

	@Override
	public void save(BitOutput buffer) {
		//buffer.addSimpleColor(bodyColor);
		//buffer.addSimpleColor(wingColor);
		//buffer.addSimpleColor(snailColor);
		//buffer.addSimpleColor(clawColor);
		bodyColor.toSimpleBits(buffer);
		wingColor.toSimpleBits(buffer);
		snailColor.toSimpleBits(buffer);
		clawColor.toSimpleBits(buffer);
	}
}
