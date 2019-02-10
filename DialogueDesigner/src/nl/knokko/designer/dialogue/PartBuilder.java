/*******************************************************************************
 * The MIT License
 *
 * Copyright (c) 2019 knokko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *  
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *******************************************************************************/
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