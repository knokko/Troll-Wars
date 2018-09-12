package nl.knokko.texture.painter;

import nl.knokko.texture.pattern.PatternCircle;
import nl.knokko.texture.pattern.TexturePattern;
import nl.knokko.util.bits.BitOutput;
import nl.knokko.util.color.Color;

public class SwordPainter extends ModelPainter {
	
	public SwordPainter(TexturePattern handlePattern, TexturePattern bladePattern){
		this(handlePattern, handlePattern, bladePattern);
	}

	public SwordPainter(TexturePattern handlePattern, TexturePattern middlePattern, TexturePattern bladePattern) {
		super(new PatternCircle(handlePattern, Color.TRANSPARENT), handlePattern, middlePattern, bladePattern);
	}

	@Override
	public void save(BitOutput buffer) {
		throw new UnsupportedOperationException("A sword can't save itself!");
	}
	
	public TexturePattern getHandleBottom(){
		return patterns[0];
	}
	
	public TexturePattern getHandle(){
		return patterns[1];
	}
	
	public TexturePattern getMiddle(){
		return patterns[2];
	}
	
	public TexturePattern getBlade(){
		return patterns[3];
	}
}