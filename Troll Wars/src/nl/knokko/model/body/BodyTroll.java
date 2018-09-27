/* 
 * The MIT License
 *
 * Copyright 2018 knokko.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
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
 */
package nl.knokko.model.body;

import java.util.Random;

import nl.knokko.model.body.head.TrollHeadProperties;
import nl.knokko.texture.painter.TrollPainter;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;
import nl.knokko.util.color.Color;
import static nl.knokko.util.color.ColorHelper.r;

public interface BodyTroll extends TrollHeadProperties, BodyHumanoid {
	
	void save(BitOutput buffer);
	
	public static class Rotation {
		
		public static final float ARM_PITCH = 270;
		public static final float ARM_ROLL = 15;
	}
	
	public static class Instance extends BodyHumanoid.Instance implements BodyTroll {
		
		protected final float noseLengthFront;
		protected final float noseLengthBack;
		protected final float noseWidth;
		protected final float noseHeight;
		
		public Instance(BitInput input){
			super(input);
			noseLengthFront = input.readFloat();
			noseLengthBack = input.readFloat();
			noseWidth = input.readFloat();
			noseHeight = input.readFloat();
		}

		public Instance(
				float scale, float headWidth, float headHeight, float headDepth, 
				float bellyHeight, float bellyWidth, float bellyDepth, float elbowRadius, float wristRadius,
				float upperArmLength, float underArmLength, float upperLegLength, float underLegLength,
				float footFrontLength, float footMidHeight, float footOutHeight, float footWidth, 
				float handCoreLength, float handHeight, float handWidth, float thumbLength, float fingerLength,
				float noseLengthFront, float noseLengthBack, float noseWidth, float noseHeight) {
			super(
					scale, headWidth, headHeight, headDepth, 
					bellyHeight, bellyWidth, bellyDepth, elbowRadius, wristRadius, 
					upperArmLength, underArmLength, upperLegLength, underLegLength, 
					footFrontLength, footMidHeight, footOutHeight, footWidth, 
					handCoreLength, handHeight, handWidth, thumbLength, fingerLength);
			this.noseLengthFront = noseLengthFront;
			this.noseLengthBack = noseLengthBack;
			this.noseWidth = noseWidth;
			this.noseHeight = noseHeight;
		}
		
		@Override
		public void save(BitOutput output){
			super.save(output);
			output.addFloat(noseLengthFront);
			output.addFloat(noseLengthBack);
			output.addFloat(noseWidth);
			output.addFloat(noseHeight);
		}

		@Override
		public float noseLengthFront() {
			return noseLengthFront * scale;
		}

		@Override
		public float noseLengthBack() {
			return noseLengthBack * scale;
		}

		@Override
		public float noseWidth() {
			return noseWidth * scale;
		}

		@Override
		public float noseHeight() {
			return noseHeight * scale;
		}
	}
	
	public static class Models {
		
		public static final BodyTroll AREA = createInstance(100f, 0.15f, 0.2f, 0.15f, 0.25f, 0.20f, 0.1f, 0.055f, 0.035f, 0.15f, 0.15f, 0.2f, 0.2f, 0.1f, 0.02f, 0.01f, 0.05f, 0.07f, 0.06f, 0.02f, 0.04f, 0.07f, 0.05f, 0.05f, 0.03f, 0.02f);
		public static final BodyTroll AREA_GOTHROK = AREA;
		
		public static final BodyTroll BATTLE_BODY = createInstance(100f, 0.15f, 0.2f, 0.15f, 0.25f, 0.20f, 0.1f, 0.055f, 0.035f, 0.15f, 0.15f, 0.2f, 0.2f, 0.1f, 0.02f, 0.01f, 0.05f, 0.07f, 0.06f, 0.02f, 0.04f, 0.07f, 0.05f, 0.05f, 0.03f, 0.02f);
		public static final BodyTroll BATTLE_GOTHROK = BATTLE_BODY;
		
		public static BodyTroll createRargiaTroll(Random r, float md){
			return createInstance(100f, f(0.15f, r, md), f(0.2f, r, md), f(0.15f, r, md), f(0.25f, r, md), f(0.20f, r, md),
					f(0.1f, r, md), f(0.055f, r, md), f(0.035f, r, md), f(0.15f, r, md), f(0.15f, r, md), f(0.2f, r, md), f(0.2f, r, md),
					f(0.1f, r, md), f(0.02f, r, md), f(0.01f, r, md), f(0.05f, r, md), f(0.07f, r, md), f(0.06f, r, md), f(0.02f, r, md),
					f(0.04f, r, md), f(0.07f, r, md), f(0.05f, r, md), f(0.05f, r, md), f(0.03f, r, md), f(0.02f, r, md));
		}
		
		public static BodyTroll createSorgTroll(Random r, float md){
			return createInstance(100f, f(0.16f,r,md), f(0.22f,r,md), f(0.16f,r,md), f(0.25f,r,md), f(0.22f,r,md), f(0.12f,r,md),
					f(0.065f,r,md), f(0.04f,r,md), f(0.15f,r,md), f(0.15f,r,md), f(0.2f,r,md), f(0.2f,r,md), f(0.1f,r,md), f(0.02f,r,md), f(0.01f,r,md), f(0.055f,r,md),
					f(0.07f,r,md), f(0.06f,r,md), f(0.022f,r,md), f(0.04f,r,md), f(0.07f,r,md), f(0.05f,r,md), f(0.05f,r,md), f(0.03f,r,md), f(0.02f,r,md));
		}
		
		private static float f(float original, Random random, float maxDif){
			return original - maxDif * original + 2 * maxDif * original * random.nextFloat();
		}
		
		public static BodyTroll createInstance(final float scale, final float headWidth, final float headHeight, final float headDepth, 
				final float bellyHeight, final float bellyWidth, final float bellyDepth, final float elbowRadius, final float wristRadius, 
				final float upperArmLength, final float underArmLength, final float upperLegLength, final float underLegLength, 
				final float footFrontLength, final float footMidHeight, final float footOutHeight, final float footWidth,
				final float handCoreLength, final float handHeight, final float handWidth, final float thumbLength, final float fingerLength,
				final float noseLengthFront, final float noseLengthBack, final float noseWidth, final float noseHeight){
			return new Instance(scale, headWidth, headHeight, headDepth, 
				bellyHeight, bellyWidth, bellyDepth, elbowRadius, wristRadius, 
				upperArmLength, underArmLength, upperLegLength, underLegLength, 
				footFrontLength, footMidHeight, footOutHeight, footWidth,
				handCoreLength, handHeight, handWidth, thumbLength, fingerLength,
				noseLengthFront, noseLengthBack, noseWidth, noseHeight);
			/*return new BodyTroll(){
				
				@Override
				public float headWidth(){
					return headWidth * scale;
				}
				
				@Override
				public float headHeight(){
					return headHeight * scale;
				}
				
				@Override
				public float headDepth(){
					return headDepth * scale;
				}

				@Override
				public float bellyHeight() {
					return bellyHeight * scale;
				}

				@Override
				public float bellyWidth() {
					return bellyWidth * scale;
				}

				@Override
				public float bellyDepth() {
					return bellyDepth * scale;
				}

				@Override
				public float shoulderRadius() {
					return bellyDepth() / 2;
				}

				@Override
				public float elbowRadius() {
					return elbowRadius * scale;
				}
				
				@Override
				public float wristRadius(){
					return wristRadius * scale;
				}

				@Override
				public float upperArmLength() {
					return upperArmLength * scale;
				}
				
				@Override
				public float underArmLength(){
					return underArmLength * scale;
				}

				@Override
				public float legUpperRadius() {
					return bellyDepth() / 2;
				}

				@Override
				public float kneeRadius() {
					return legUpperRadius() * 0.8f;
				}

				@Override
				public float legUnderRadius() {
					return legUpperRadius() * 0.7f;
				}

				@Override
				public float upperLegLength() {
					return upperLegLength * scale;
				}

				@Override
				public float underLegLength() {
					return underLegLength * scale;
				}

				@Override
				public float footFrontLength() {
					return footFrontLength * scale;
				}

				@Override
				public float footMidHeight() {
					return footMidHeight * scale;
				}

				@Override
				public float footOutHeight() {
					return footOutHeight * scale;
				}

				@Override
				public float footWidth() {
					return footWidth * scale;
				}

				@Override
				public float noseLengthFront() {
					return noseLengthFront * scale;
				}

				@Override
				public float noseLengthBack() {
					return noseLengthBack * scale;
				}

				@Override
				public float noseWidth() {
					return noseWidth * scale;
				}

				@Override
				public float noseHeight() {
					return noseHeight * scale;
				}

				@Override
				public float handCoreLength() {
					return handCoreLength * scale;
				}

				@Override
				public float handHeight() {
					return handHeight * scale;
				}

				@Override
				public float handWidth() {
					return handWidth * scale;
				}

				@Override
				public float thumbLength() {
					return thumbLength * scale;
				}

				@Override
				public float fingerLength() {
					return fingerLength * scale;
				}

				@Override
				public void save(BitOutput buffer) {
					buffer.addFloat(scale);
					buffer.addFloat(headWidth);
					buffer.addFloat(headHeight);
					buffer.addFloat(headDepth);
					buffer.addFloat(bellyHeight);
					buffer.addFloat(bellyWidth);
					buffer.addFloat(bellyDepth);
					buffer.addFloat(elbowRadius);
					buffer.addFloat(wristRadius);
					buffer.addFloat(upperArmLength);
					buffer.addFloat(underArmLength);
					buffer.addFloat(upperLegLength);
					buffer.addFloat(underLegLength);
					buffer.addFloat(footFrontLength);
					buffer.addFloat(footMidHeight);
					buffer.addFloat(footOutHeight);
					buffer.addFloat(footWidth);
					buffer.addFloat(handCoreLength);
					buffer.addFloat(handHeight);
					buffer.addFloat(handWidth);
					buffer.addFloat(thumbLength);
					buffer.addFloat(fingerLength);
					buffer.addFloat(noseLengthFront);
					buffer.addFloat(noseLengthBack);
					buffer.addFloat(noseWidth);
					buffer.addFloat(noseHeight);
				}
			};*/
		}
		
		public static BodyTroll load(BitInput bits){
			return createInstance(
					bits.readFloat(), bits.readFloat(), bits.readFloat(), bits.readFloat(), bits.readFloat(), bits.readFloat(),
					bits.readFloat(), bits.readFloat(), bits.readFloat(), bits.readFloat(), bits.readFloat(), bits.readFloat(),
					bits.readFloat(), bits.readFloat(), bits.readFloat(), bits.readFloat(), bits.readFloat(), bits.readFloat(),
					bits.readFloat(), bits.readFloat(), bits.readFloat(), bits.readFloat(), bits.readFloat(), bits.readFloat(),
					bits.readFloat(), bits.readFloat());
		}
	}
	
	public static class Textures {
		
		public static TrollPainter createRargia(){
			return new TrollPainter(r(new Color(20, 150, 200), 0.15f), r(new Color(100, 70, 0), 0.15f));
		}
		
		public static TrollPainter createSorg(){
			return new TrollPainter(r(new Color(20, 180, 120), 0.15f), r(new Color(60, 40, 0), 0.15f));
		}
	}
	
	/*public static class Helper {
		
		public static ModelPart getTrollBody(ModelOwner troll){
			return troll.getModels().get(0);
		}
		
		public static ModelPart getHead(ModelPart trollBody){
			return trollBody.getChildren()[0];
		}
		
		public static ModelPart getBelly(ModelPart trollBody){
			return trollBody;
		}
		
		public static ModelPart getLeftArm(ModelPart trollBody){
			return trollBody.getChildren()[1];
		}
		
		public static ModelPart getRightArm(ModelPart trollBody){
			return trollBody.getChildren()[2];
		}
		
		public static ModelPart getLeftUnderArm(ModelPart trollBody){
			return getLeftArm(trollBody).getChildren()[0];
		}
		
		public static ModelPart getRightUnderArm(ModelPart trollBody){
			return getRightArm(trollBody).getChildren()[0];
		}
		
		public static ModelPart getLeftHand(ModelPart trollBody){
			return getLeftUnderArm(trollBody).getChildren()[0];
		}
		
		public static ModelPart getRightHand(ModelPart trollBody){
			return getRightUnderArm(trollBody).getChildren()[0];
		}
		
		public static ModelPart getLeftLeg(ModelPart trollBody){
			return trollBody.getChildren()[3];
		}
		
		public static ModelPart getRightLeg(ModelPart trollBody){
			return trollBody.getChildren()[4];
		}
		
		public static ModelPart getLeftUnderLeg(ModelPart trollBody){
			return getLeftLeg(trollBody).getChildren()[0];
		}
		
		public static ModelPart getRightUnderLeg(ModelPart trollBody){
			return getRightLeg(trollBody).getChildren()[0];
		}
		
		public static ModelPart getLeftFoot(ModelPart trollBody){
			return getLeftUnderLeg(trollBody).getChildren()[0];
		}
		
		public static ModelPart getRightFoot(ModelPart trollBody){
			return getRightUnderLeg(trollBody).getChildren()[0];
		}
		
		private static void setEquipment(ModelPart part, ModelPart equipment, int index){
			if(equipment != null)
				equipment.setParent(part);
			if(part.getChildren().length > index)
				part.getChildren()[index] = equipment;
			else if(equipment != null)
				part.addChild(equipment);
		}
		
		public static void setHelmet(ModelPart trollBody, ModelPart helmet){
			setEquipment(getHead(trollBody), helmet, 0);
		}
		
		public static void setChestplate(ModelPart trollBody, ModelPart chestplate, ModelPart upperArm, ModelPart underArm){
			setEquipment(getBelly(trollBody), chestplate, 5);
			setEquipment(getLeftArm(trollBody), upperArm != null ? upperArm.clone() : upperArm, 1);
			setEquipment(getLeftUnderArm(trollBody), underArm != null ? underArm.clone() : null, 1);
			setEquipment(getRightArm(trollBody), upperArm != null ? upperArm.clone() : null, 1);
			setEquipment(getRightUnderArm(trollBody), underArm != null ? underArm.clone() : null, 1);
		}
		
		public static void setLeftWeapon(ModelPart trollBody, ModelPart weapon){
			setEquipment(getLeftHand(trollBody), weapon, 0);
		}
		
		public static void setRightWeapon(ModelPart trollBody, ModelPart weapon){
			setEquipment(getRightHand(trollBody), weapon, 0);
		}
		
		public static void setLeftGlobe(ModelPart trollBody, ModelPart globe){
			setEquipment(getLeftHand(trollBody), globe, 1);
		}
		
		public static void setRightGlobe(ModelPart trollBody, ModelPart globe){
			setEquipment(getRightHand(trollBody), globe, 1);
		}
		
		public static void setPants(ModelPart trollBody, ModelPart upperLeg, ModelPart underLeg){
			setEquipment(getLeftLeg(trollBody), upperLeg != null ? upperLeg.clone() : null, 1);
			setEquipment(getRightLeg(trollBody), upperLeg != null ? upperLeg.clone() : null, 1);
			setEquipment(getLeftUnderLeg(trollBody), underLeg != null ? underLeg.clone() : null, 1);
			setEquipment(getRightUnderLeg(trollBody), underLeg != null ? underLeg.clone() : null, 1);
		}
		
		public static void setLeftShoe(ModelPart trollBody, ModelPart shoe){
			setEquipment(getLeftFoot(trollBody), shoe != null ? shoe.clone() : null, 0);
		}
		
		public static void setRightShoe(ModelPart trollBody, ModelPart shoe){
			setEquipment(getRightFoot(trollBody), shoe != null ? shoe.clone() : null, 0);
		}
		
		public static void setHelmet(ModelOwner troll, ModelPart helmet){
			setHelmet(getTrollBody(troll), helmet);
		}
		
		public static void setLeftWeapon(ModelOwner troll, ModelPart weapon){
			setLeftWeapon(getTrollBody(troll), weapon);
		}
		
		public static void setRightWeapon(ModelOwner troll, ModelPart weapon){
			setRightWeapon(getTrollBody(troll), weapon);
		}
		
		public static void setLeftGlobe(ModelOwner troll, ModelPart globe){
			setLeftGlobe(getTrollBody(troll), globe);
		}
		
		public static void setRightGlobe(ModelOwner troll, ModelPart globe){
			setRightGlobe(getTrollBody(troll), globe);
		}
		
		public static void setChestplate(ModelOwner troll, ModelPart belly, ModelPart upperLeg, ModelPart underLeg){
			setChestplate(getTrollBody(troll), belly, upperLeg, underLeg);
		}
		
		public static void setPants(ModelOwner troll, ModelPart upperLeg, ModelPart underLeg){
			setPants(getTrollBody(troll), upperLeg, underLeg);
		}
		
		public static void setLeftShoe(ModelOwner troll, ModelPart shoe){
			setLeftShoe(getTrollBody(troll), shoe);
		}
		
		public static void setRightShoe(ModelOwner troll, ModelPart shoe){
			setRightShoe(getTrollBody(troll), shoe);
		}
	}*/
	
	public static class Helper extends BodyHumanoid.Helper {}//at the moment, BodyHumanoid.Helper has all necessary methods
}
