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
package nl.knokko.model.body;

import nl.knokko.model.ModelPart;
import nl.knokko.model.body.belly.HumanoidBelly;
import nl.knokko.model.body.claw.BirdClaw;
import nl.knokko.model.body.head.BirdHead;
import nl.knokko.model.body.wing.BirdWing;
import nl.knokko.texture.painter.BirdPainter;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;
import nl.knokko.util.color.Color;
import static nl.knokko.util.color.ColorHelper.r;

public interface BodyBird extends Body, HumanoidBelly, BirdClaw, BirdWing, BirdHead {
	
	void save(BitOutput buffer);
	
	public static class Textures {
		
		public static BirdPainter createSorg(){
			return new BirdPainter(r(new Color(0, 40, 140), 0.15f), r(new Color(0, 65, 200), 0.15f), r(new Color(200, 100, 0), 0.15f), r(new Color(200, 100, 0), 0.15f));
		}
	}
	
	public static class Models {
		
		public static BodyBird loadInstance(BitInput data){
			float[] wingPartLenghts = new float[data.readByte()];
			for(byte b = 0; b < wingPartLenghts.length; b++)
				wingPartLenghts[b] = data.readFloat();
			return new Instance(data.readFloat(), data.readFloat(), data.readFloat(), data.readFloat(), data.readFloat(), data.readFloat(),
					data.readInt(), data.readFloat(), data.readFloat(), data.readFloat(), data.readFloat(), data.readFloat(), wingPartLenghts,
					data.readFloat(), data.readFloat(), data.readFloat(), data.readFloat(), data.readFloat());
		}
		
		public static BodyBird createInstance(float scale, float bellyHeight, float bellyWidth, float bellyDepth,
				float legLength, float legRadius, float nailSpreadAngle, int nailAmount, float nailLength, float nailRadius,
				float wingPartMaxLength, float wingPartHeight, float wingPartDepth, float[] wingPartLengths,
				float snailLength, float snailRadius, float headHeight, float headWidth, float headDepth){
			return new Instance(bellyHeight * scale, bellyWidth * scale, bellyDepth * scale,
					legLength * scale, legRadius * scale, nailSpreadAngle, nailAmount, nailLength * scale, nailRadius * scale,
					wingPartMaxLength * scale, wingPartHeight * scale, wingPartDepth * scale, wingPartLengths,
					snailLength * scale, snailRadius * scale, headHeight * scale, headWidth * scale, headDepth * scale);
		}
		
		public static BodyBird createSimpleInstance(float scale){
			return createInstance(scale, 0.2f, 0.1f, 0.1f,   0.15f, 0.015f, 70, 3, 0.15f, 0.02f,   0.2f, 0.03f, 0.01f, new float[]{0.5f, 0.7f, 0.8f, 0.9f, 0.7f},
					0.1f, 0.04f, 0.13f, 0.08f, 0.08f);
		}
		
		private static class Instance implements BodyBird {
			
			private final float bellyHeight;
			private final float bellyWidth;
			private final float bellyDepth;
			
			private final float legLength;
			private final float legRadius;
			private final float nailSpreadAngle;
			private final int nailAmount;
			private final float nailLength;
			private final float nailRadius;
			
			private final float wingPartMaxLength;
			private final float wingPartHeight;
			private final float wingPartDepth;
			private final float[] wingPartLengths;
			
			private final float snailLength;
			private final float snailRadius;
			private final float headHeight;
			private final float headWidth;
			private final float headDepth;
			
			private Instance(float bellyHeight, float bellyWidth, float bellyDepth, float legLength, float legRadius,
					float nailSpreadAngle, int nailAmount, float nailLength, float nailRadius, 
					float wingPartMaxLength, float wingPartHeight, float wingPartDepth, float[] wingPartLengths, float snailLength, 
					float snailRadius, float headHeight, float headWidth, float headDepth){
				if(wingPartLengths.length > Byte.MAX_VALUE)
					throw new IllegalArgumentException("Max wing part count is 127, but this one's is " + wingPartLengths.length);
				this.bellyHeight = bellyHeight;
				this.bellyWidth = bellyWidth;
				this.bellyDepth = bellyDepth;
				this.legLength = legLength;
				this.legRadius = legRadius;
				this.nailSpreadAngle = nailSpreadAngle;
				this.nailAmount = nailAmount;
				this.nailLength = nailLength;
				this.nailRadius = nailRadius;
				this.wingPartMaxLength = wingPartMaxLength;
				this.wingPartHeight = wingPartHeight;
				this.wingPartDepth = wingPartDepth;
				this.wingPartLengths = wingPartLengths;
				this.snailLength = snailLength;
				this.snailRadius = snailRadius;
				this.headHeight = headHeight;
				this.headWidth = headWidth;
				this.headDepth = headDepth;
			}

			@Override
			public float bellyHeight() {
				return bellyHeight;
			}

			@Override
			public float bellyWidth() {
				return bellyWidth;
			}

			@Override
			public float bellyDepth() {
				return bellyDepth;
			}

			@Override
			public float shoulderRadius() {
				return 0;
			}

			@Override
			public float legLength() {
				return legLength;
			}
			
			@Override
			public float legRadius(){
				return legRadius;
			}

			@Override
			public float nailSpreadAngle() {
				return nailSpreadAngle;
			}

			@Override
			public int nailAmount() {
				return nailAmount;
			}

			@Override
			public float nailLength() {
				return nailLength;
			}

			@Override
			public float nailRadius() {
				return nailRadius;
			}

			@Override
			public float wingPartMaxLength() {
				return wingPartMaxLength;
			}

			@Override
			public float wingPartHeight() {
				return wingPartHeight;
			}
			
			@Override
			public float wingPartDepth(){
				return wingPartDepth;
			}

			@Override
			public float[] wingPartLengths() {
				return wingPartLengths;
			}

			@Override
			public float snailLength() {
				return snailLength;
			}

			@Override
			public float snailRadius() {
				return snailRadius;
			}

			@Override
			public float headHeight() {
				return headHeight;
			}

			@Override
			public float headWidth() {
				return headWidth;
			}

			@Override
			public float headDepth() {
				return headDepth;
			}
			
			@Override
			public void save(BitOutput buffer){
				buffer.addByte((byte) wingPartLengths.length);
				for(float wpl : wingPartLengths)
					buffer.addFloat(wpl);
				buffer.addFloat(bellyHeight);
				buffer.addFloat(bellyWidth);
				buffer.addFloat(bellyDepth);
				buffer.addFloat(legLength);
				buffer.addFloat(legRadius);
				buffer.addFloat(nailSpreadAngle);
				buffer.addInt(nailAmount);
				buffer.addFloat(nailLength);
				buffer.addFloat(nailRadius);
				buffer.addFloat(wingPartMaxLength);
				buffer.addFloat(wingPartHeight);
				buffer.addFloat(wingPartDepth);
				buffer.addFloat(snailLength);
				buffer.addFloat(snailRadius);
				buffer.addFloat(headHeight);
				buffer.addFloat(headWidth);
				buffer.addFloat(headDepth);
			}
		}
	}
	
	public static class Helper {
		
		public static ModelPart getHead(ModelPart belly){
			return belly.getChildren()[0];
		}
		
		public static ModelPart getLeftWing(ModelPart belly){
			return belly.getChildren()[1];
		}
		
		public static ModelPart getRightWing(ModelPart belly){
			return belly.getChildren()[2];
		}
		
		public static ModelPart getLeftLeg(ModelPart belly){
			return belly.getChildren()[3];
		}
		
		public static ModelPart getRightLeg(ModelPart belly){
			return belly.getChildren()[4];
		}
	}
}