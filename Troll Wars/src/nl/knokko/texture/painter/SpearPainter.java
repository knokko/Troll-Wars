package nl.knokko.texture.painter;

import nl.knokko.texture.pattern.PatternCircle;
import nl.knokko.texture.pattern.TexturePattern;
import nl.knokko.util.bits.BitOutput;
import nl.knokko.util.color.Color;

public class SpearPainter extends ModelPainter {

	public SpearPainter(TexturePattern stickPattern, TexturePattern pointPattern) {
		super(stickPattern, new PatternCircle(stickPattern, Color.TRANSPARENT), pointPattern);
	}

	@Override
	public void save(BitOutput buffer) {
		throw new UnsupportedOperationException("A spear painter can't save itself!");
	}
	
	public TexturePattern getStick(){
		return patterns[0];
	}
	
	public TexturePattern getStickBottom(){
		return patterns[1];
	}
	
	public TexturePattern getPoint(){
		return patterns[2];
	}
}