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
package nl.knokko.texture.painter;

import nl.knokko.texture.pattern.*;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;
import nl.knokko.util.color.Color;

public class TrollPainter extends ModelPainter {
	
	private final Color skinColor;
	private final Color hairColor;

	public TrollPainter(Color skinColor, Color hairColor) {
		super(
				new PatternFace(skinColor, hairColor, 0.2f, 349394), new PatternAverage(skinColor, 0.2f, 2938923),
				new PatternAverage(skinColor, 0.2f, 23782), new PatternAverage(skinColor, 0.2f, 239287),
				new PatternAverage(skinColor, 0.2f, 92378), new PatternAverage(skinColor, 0.2f, 237347),
				new PatternAverage(skinColor, 0.2f, 8347824), new PatternAverage(skinColor, 0.2f, 623),
				new PatternAverage(skinColor, 0.2f, 53023), new PatternAverage(skinColor, 0.2f, 1),
				new PatternAverage(skinColor, 0.2f, 81632), new PatternAverage(skinColor, 0.2f, 293195),
				new PatternAverage(skinColor, 0.2f, 6295), new PatternAverage(skinColor, 0.2f, 1839),
				new PatternAverage(skinColor, 0.2f, 649278), new PatternAverage(skinColor, 0.3f, 9571264),
				new PatternAverage(skinColor, 0.2f, 72323), new PatternAverage(skinColor, 0.3f, 838891),
				new PatternAverage(skinColor, 0.2f, -2748), new PatternAverage(skinColor, 0.2f, 931267237239L)
				);
		this.skinColor = skinColor;
		this.hairColor = hairColor;
	}
	
	public TrollPainter(BitInput bits){
		//this(bits.readSimpleColor(), bits.readSimpleColor());
		this(Color.fromSimpleBits(bits), Color.fromSimpleBits(bits));
	}
	
	public TexturePattern getHead(){
		return patterns[0];
	}
	
	public TexturePattern getBelly(){
		return patterns[1];
	}
	
	public TexturePattern getLeftUpperArm(){
		return patterns[2];
	}
	
	public TexturePattern getRightUpperArm(){
		return patterns[3];
	}
	
	public TexturePattern getLeftUnderArm(){
		return patterns[4];
	}
	
	public TexturePattern getRightUnderArm(){
		return patterns[5];
	}
	
	public TexturePattern getLeftUpperLeg(){
		return patterns[6];
	}
	
	public TexturePattern getRightUpperLeg(){
		return patterns[7];
	}
	
	public TexturePattern getLeftUnderLeg(){
		return patterns[8];
	}
	
	public TexturePattern getRightUnderLeg(){
		return patterns[9];
	}
	
	public TexturePattern getLeftFoot(){
		return patterns[10];
	}
	
	public TexturePattern getRightFoot(){
		return patterns[11];
	}
	
	public TexturePattern getLeftShoulder(){
		return patterns[12];
	}
	
	public TexturePattern getRightShoulder(){
		return patterns[13];
	}
	
	public TexturePattern getBellyTop(){
		return patterns[14];
	}
	
	public TexturePattern getNose(){
		return patterns[15];
	}
	
	public TexturePattern getLeftHand(){
		return patterns[16];
	}
	
	public TexturePattern getRightHand(){
		return patterns[17];
	}
	
	public TexturePattern getLeftKnee(){
		return patterns[18];
	}
	
	public TexturePattern getRightKnee(){
		return patterns[19];
	}
	
	@Override
	public void save(BitOutput bits){
		//bits.addSimpleColor(skinColor);
		//bits.addSimpleColor(hairColor);
		skinColor.toSimpleBits(bits);
		hairColor.toSimpleBits(bits);
	}
}