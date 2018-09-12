package nl.knokko.texture.painter;

import nl.knokko.texture.pattern.TexturePattern;
import nl.knokko.util.bits.BitOutput;

public abstract class ModelPainter {
	
	protected TexturePattern[] patterns;

	public ModelPainter(TexturePattern...patterns) {
		this.patterns = patterns;
	}
	
	public TexturePattern getPattern(int index){
		return patterns[index];
	}
	
	public int getAmount(){
		return patterns.length;
	}
	
	public abstract void save(BitOutput buffer);
}
