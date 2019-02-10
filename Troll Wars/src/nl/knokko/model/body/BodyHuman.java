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

import java.util.Random;

import nl.knokko.texture.painter.HumanPainter;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;
import nl.knokko.util.color.Color;
import static nl.knokko.util.color.ColorHelper.r;

public interface BodyHuman extends BodyHumanoid {
	
	void save(BitOutput bits);
	
	public static class Rotation {
		
		public static final float ARM_PITCH = 270;
		public static final float ARM_ROLL = 15;
		
	}
	
	public static class Instance extends BodyHumanoid.Instance implements BodyHuman {

		public Instance(
				float scale, float headWidth, float headHeight, float headDepth, 
				float bellyHeight, float bellyWidth, float bellyDepth, float elbowRadius, float wristRadius,
				float upperArmLength, float underArmLength, float upperLegLength, float underLegLength,
				float footFrontLength, float footMidHeight, float footOutHeight, float footWidth, 
				float handCoreLength, float handHeight, float handWidth, float thumbLength, float fingerLength) {
			super(
					scale, headWidth, headHeight, headDepth, 
					bellyHeight, bellyWidth, bellyDepth, elbowRadius, wristRadius, 
					upperArmLength, underArmLength, upperLegLength, underLegLength, 
					footFrontLength, footMidHeight, footOutHeight, footWidth, 
					handCoreLength, handHeight, handWidth, thumbLength, fingerLength);
		}
	}
	
	public static class Models {
		
		public static BodyHuman createSimpleInstance(Random r, float md){
			return createInstance(100f, f(0.16f,r,md), f(0.22f,r,md), f(0.16f,r,md), f(0.25f,r,md), f(0.22f,r,md), f(0.12f,r,md),
					f(0.065f,r,md), f(0.04f,r,md), f(0.15f,r,md), f(0.15f,r,md), f(0.2f,r,md), f(0.2f,r,md), f(0.1f,r,md), f(0.02f,r,md), f(0.01f,r,md), f(0.055f,r,md),
					f(0.07f,r,md), f(0.06f,r,md), f(0.022f,r,md), f(0.04f,r,md), f(0.07f,r,md));
		}
		
		public static BodyHuman loadInstance(BitInput bits){
			return createInstance(1, bits.readFloat(), bits.readFloat(), bits.readFloat(), bits.readFloat(), bits.readFloat(),
					bits.readFloat(), bits.readFloat(), bits.readFloat(), bits.readFloat(), bits.readFloat(), bits.readFloat(),
					bits.readFloat(), bits.readFloat(), bits.readFloat(), bits.readFloat(), bits.readFloat(), bits.readFloat(),
					bits.readFloat(), bits.readFloat(), bits.readFloat(), bits.readFloat());
		}
		
		public static BodyHuman createInstance(float scale, float headHeight, float headWidth, float headDepth,
				float bellyHeight, float bellyWidth, float bellyDepth,
				float elbowRadius, float wristRadius, float upperArmLength, float underArmLength,
				float upperLegLength, float underLegLength,
				float footFrontLength, float footMidHeight, float footOutHeight, float footWidth,
				float handCoreLength, float handHeight, float handWidth, float thumbLength, float fingerLength){
			return new BodyHuman.Instance(scale, headHeight, headWidth, headDepth, bellyHeight, bellyWidth, bellyDepth,
					elbowRadius, wristRadius, upperArmLength, underArmLength,
					upperLegLength, underLegLength,
					footFrontLength, footMidHeight, footOutHeight, footWidth,
					handCoreLength, handHeight, handWidth, thumbLength, fingerLength);
		}
		
		private static float f(float original, Random random, float maxDif){
			return original - maxDif * original + 2 * maxDif * original * random.nextFloat();
		}
		
		/*private static class Instance implements BodyHuman {
			
			private final float headHeight;
			private final float headWidth;
			private final float headDepth;
			
			private final float bellyHeight;
			private final float bellyWidth;
			private final float bellyDepth;
			
			private final float elbowRadius;
			private final float wristRadius;
			private final float upperArmLength;
			private final float underArmLength;
			
			private final float upperLegLength;
			private final float underLegLength;
			
			private final float footFrontLength;
			private final float footMidHeight;
			private final float footOutHeight;
			private final float footWidth;
			
			private final float handCoreLength;
			private final float handHeight;
			private final float handWidth;
			private final float thumbLength;
			private final float fingerLength;
			
			private Instance(float scale, float headHeight, float headWidth, float headDepth,
					float bellyHeight, float bellyWidth, float bellyDepth,
					float elbowRadius, float wristRadius, float upperArmLength, float underArmLength,
					float upperLegLength, float underLegLength,
					float footFrontLength, float footMidHeight, float footOutHeight, float footWidth,
					float handCoreLength, float handHeight, float handWidth, float thumbLength, float fingerLength){
				this.headHeight = headHeight * scale;
				this.headWidth = headWidth * scale;
				this.headDepth = headDepth * scale;
				
				this.bellyHeight = bellyHeight * scale;
				this.bellyWidth = bellyWidth * scale;
				this.bellyDepth = bellyDepth * scale;
				
				this.elbowRadius = elbowRadius * scale;
				this.wristRadius = wristRadius * scale;
				this.upperArmLength = upperArmLength * scale;
				this.underArmLength = underArmLength * scale;
				
				this.upperLegLength = upperLegLength * scale;
				this.underLegLength = underLegLength * scale;
				
				this.footFrontLength = footFrontLength * scale;
				this.footMidHeight = footMidHeight * scale;
				this.footOutHeight = footOutHeight * scale;
				this.footWidth = footWidth * scale;
				
				this.handCoreLength = handCoreLength * scale;
				this.handHeight = handHeight * scale;
				this.handWidth = handWidth * scale;
				this.thumbLength = thumbLength * scale;
				this.fingerLength = fingerLength * scale;
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
				return bellyDepth() / 2;
			}

			@Override
			public float elbowRadius() {
				return elbowRadius;
			}

			@Override
			public float wristRadius() {
				return wristRadius;
			}

			@Override
			public float upperArmLength() {
				return upperArmLength;
			}

			@Override
			public float underArmLength() {
				return underArmLength;
			}

			@Override
			public float legUpperRadius() {
				return bellyDepth / 2;
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
				return upperLegLength;
			}

			@Override
			public float underLegLength() {
				return underLegLength;
			}

			@Override
			public float footFrontLength() {
				return footFrontLength;
			}

			@Override
			public float footMidHeight() {
				return footMidHeight;
			}

			@Override
			public float footOutHeight() {
				return footOutHeight;
			}

			@Override
			public float footWidth() {
				return footWidth;
			}

			@Override
			public float handCoreLength() {
				return handCoreLength;
			}

			@Override
			public float handHeight() {
				return handHeight;
			}

			@Override
			public float handWidth() {
				return handWidth;
			}

			@Override
			public float thumbLength() {
				return thumbLength;
			}

			@Override
			public float fingerLength() {
				return fingerLength;
			}

			@Override
			public void save(BitOutput bits) {
				bits.addFloat(headHeight);
				bits.addFloat(headWidth);
				bits.addFloat(headDepth);
				bits.addFloat(bellyHeight);
				bits.addFloat(bellyWidth);
				bits.addFloat(bellyDepth);
				bits.addFloat(elbowRadius);
				bits.addFloat(wristRadius);
				bits.addFloat(upperArmLength);
				bits.addFloat(underArmLength);
				bits.addFloat(upperLegLength);
				bits.addFloat(underLegLength);
				bits.addFloat(footFrontLength);
				bits.addFloat(footMidHeight);
				bits.addFloat(footOutHeight);
				bits.addFloat(footWidth);
				bits.addFloat(handCoreLength);
				bits.addFloat(handHeight);
				bits.addFloat(handWidth);
				bits.addFloat(thumbLength);
				bits.addFloat(fingerLength);
			}
		}*/
	}
	
	public static class Textures {
		
		public static HumanPainter createBlanc(Color hair){
			return new HumanPainter(r(new Color(250, 180, 120), 0.15f), r(hair, 0.15f));
		}
		
		public static HumanPainter createBrown(Color hair){
			return new HumanPainter(r(new Color(90, 35, 0), 0.15f), r(hair, 0.15f));
		}
	}
	
	/*public static class Helper {
		
		public static ModelPart getHumanBody(ModelOwner troll){
			return troll.getModels().get(0);
		}
		
		public static ModelPart getHead(ModelPart body){
			return body.getChildren()[0];
		}
		
		public static ModelPart getBelly(ModelPart body){
			return body;
		}
		
		public static ModelPart getLeftArm(ModelPart body){
			return body.getChildren()[1];
		}
		
		public static ModelPart getRightArm(ModelPart body){
			return body.getChildren()[2];
		}
		
		public static ModelPart getLeftUnderArm(ModelPart body){
			return getLeftArm(body).getChildren()[0];
		}
		
		public static ModelPart getRightUnderArm(ModelPart body){
			return getRightArm(body).getChildren()[0];
		}
		
		public static ModelPart getLeftHand(ModelPart body){
			return getLeftUnderArm(body).getChildren()[0];
		}
		
		public static ModelPart getRightHand(ModelPart body){
			return getRightUnderArm(body).getChildren()[0];
		}
		
		public static ModelPart getLeftLeg(ModelPart body){
			return body.getChildren()[3];
		}
		
		public static ModelPart getRightLeg(ModelPart body){
			return body.getChildren()[4];
		}
		
		public static ModelPart getLeftUnderLeg(ModelPart body){
			return getLeftLeg(body).getChildren()[0];
		}
		
		public static ModelPart getRightUnderLeg(ModelPart body){
			return getRightLeg(body).getChildren()[0];
		}
		
		private static void setEquipment(ModelPart part, ModelPart equipment, int index){
			if(equipment != null)
				equipment.setParent(part);
			if(part.getChildren().length > index)
				part.getChildren()[index] = equipment;
			else if(equipment != null)
				part.addChild(equipment);
		}
		
		public static void setHelmet(ModelPart belly, ItemHelmet helmet, HeadProperties head){
			setHelmet(belly, helmet.createModel(head));
		}
		
		public static void setHelmet(ModelPart body, ModelPart helmet){
			setEquipment(getHead(body), helmet, 0);
		}
		
		public static void setChestplate(ModelPart belly, ItemChestplate chestplate, BodyHuman body){
			setChestplate(belly, chestplate.createModelBelly(body), chestplate.createModelUpperArm(body), chestplate.createModelUnderArm(body));
		}
		
		public static void setChestplate(ModelPart body, ModelPart chestplate, ModelPart upperArm, ModelPart underArm){
			setEquipment(getBelly(body), chestplate, 5);
			setEquipment(getLeftArm(body), upperArm != null ? upperArm.clone() : upperArm, 1);
			setEquipment(getLeftUnderArm(body), underArm != null ? underArm.clone() : null, 1);
			setEquipment(getRightArm(body), upperArm != null ? upperArm.clone() : null, 1);
			setEquipment(getRightUnderArm(body), underArm != null ? underArm.clone() : null, 1);
		}
		
		public static void setLeftWeapon(ModelPart belly, ItemWeapon weapon, HumanoidHandProperties hand){
			setLeftWeapon(belly, weapon.createModel(hand, true));
		}
		
		public static void setLeftWeapon(ModelPart body, ModelPart weapon){
			setEquipment(getLeftHand(body), weapon, 0);
		}
		
		public static void setRightWeapon(ModelPart belly, ItemWeapon weapon, HumanoidHandProperties hand){
			setRightWeapon(belly, weapon.createModel(hand, false));
		}
		
		public static void setRightWeapon(ModelPart body, ModelPart weapon){
			setEquipment(getRightHand(body), weapon, 0);
		}
		
		public static void setPants(ModelPart belly, ItemPants pants, HumanoidLeg leg){
			setPants(belly, pants.createModelUpperLeg(leg), pants.createModelUnderLeg(leg));
		}
		
		public static void setPants(ModelPart body, ModelPart upperLeg, ModelPart underLeg){
			setEquipment(getLeftLeg(body), upperLeg != null ? upperLeg.clone() : null, 1);
			setEquipment(getRightLeg(body), upperLeg != null ? upperLeg.clone() : null, 1);
			setEquipment(getLeftUnderLeg(body), underLeg != null ? underLeg.clone() : null, 1);
			setEquipment(getRightUnderLeg(body), underLeg != null ? underLeg.clone() : null, 1);
		}
		
		public static void setLeftWeapon(ModelOwner body, ModelPart weapon){
			setLeftWeapon(getHumanBody(body), weapon);
		}
		
		public static void setRightWeapon(ModelOwner body, ModelPart weapon){
			setRightWeapon(getHumanBody(body), weapon);
		}
		
		public static void setChestplate(ModelOwner body, ModelPart belly, ModelPart upperArm, ModelPart underArm){
			setChestplate(getHumanBody(body), belly, upperArm, underArm);
		}
		
		public static void setPants(ModelOwner body, ModelPart upperLeg, ModelPart underLeg){
			setPants(getHumanBody(body), upperLeg, underLeg);
		}
	}*/
	
	public static class Helper extends BodyHumanoid.Helper {}
}
