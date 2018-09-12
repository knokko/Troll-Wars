package nl.knokko.designer.dialogue;

import nl.knokko.story.dialogue.PortraitMap;
import nl.knokko.story.dialogue.Portraits;
import nl.knokko.texture.ImageTexture;
import nl.knokko.util.bits.BitOutput;

public abstract class PartBuilder {
	
	public int index;
	
	public int designerX;
	public int designerY;
	
	protected ImageTexture portrait;

	public PartBuilder(int index, int designerX, int designerY) {
		this.index = index;
		this.designerX = designerX;
		this.designerY = designerY;
		this.portrait = Portraits.EMPTY;
	}
	
	public void setPortrait(ImageTexture portrait){
		this.portrait = portrait;
	}
	
	public abstract void save(BitOutput output, PortraitMap portraits);
}