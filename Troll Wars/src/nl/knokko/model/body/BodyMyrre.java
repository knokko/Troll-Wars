/*******************************************************************************
 * The MIT License
 *
 * Copyright (c) 2018 knokko
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

import org.lwjgl.util.vector.Vector3f;

import nl.knokko.model.ModelOwner;
import nl.knokko.model.ModelPart;
import nl.knokko.model.body.arm.MyrreArm;
import nl.knokko.model.body.belly.MyrreBelly;
import nl.knokko.model.body.claw.MyrreFootClaw;
import nl.knokko.model.body.claw.MyrreHandClaw;
import nl.knokko.model.body.leg.MyrreLeg;
import nl.knokko.model.type.AbstractModel;
import nl.knokko.texture.ModelTexture;
import nl.knokko.texture.painter.MyrrePainter;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;
import nl.knokko.util.color.ColorAlpha;

public interface BodyMyrre extends MyrreArm, MyrreFootClaw, MyrreHandClaw, MyrreLeg, MyrreBelly {
	
	void save(BitOutput output);
	
	public static class Rotation {
		
		public static final float ARM_ROLL = 20;
		public static final float ARM_PITCH = 80;
	}
	
	public static class Models {
		
		public static final BodyMyrre MYRMORA = createInstance(1f, 30f, 15f, 15f, 25f, 5f, 10, 20f, 35f, 7f, 10, 25f);
		
		public static BodyMyrre loadInstance(BitInput input){
			return createInstance(input.readFloat(), input.readFloat(), input.readFloat(), input.readFloat(),
					input.readFloat(), input.readFloat(), input.readInt(), input.readFloat(), input.readFloat(),
					input.readFloat(), input.readInt(), input.readFloat());
		}
		
		public static BodyMyrre createInstance(final float scale,
				final float bellyHeight, final float bellyWidth, final float bellyDepth,
				final float armLength, final float armRadius, final int armParts, final float armClawLength,
				final float legLength, final float legRadius, final int legParts, final float legClawLength){
			return new BodyMyrre(){

				@Override
				public float armLength() {
					return armLength * scale;
				}

				@Override
				public float armRadius() {
					return armRadius * scale;
				}

				@Override
				public int armParts() {
					return armParts;
				}

				@Override
				public float nailFootLength() {
					return legClawLength;
				}

				@Override
				public float nailHandLength() {
					return armClawLength;
				}

				@Override
				public float legLength() {
					return legLength;
				}

				@Override
				public float legRadius() {
					return legRadius;
				}

				@Override
				public int legParts() {
					return legParts;
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
				public void save(BitOutput output) {
					output.addFloat(scale);
					output.addFloat(bellyHeight);
					output.addFloat(bellyWidth);
					output.addFloat(bellyDepth);
					output.addFloat(armLength);
					output.addFloat(armRadius);
					output.addInt(armParts);
					output.addFloat(armClawLength);
					output.addFloat(legLength);
					output.addFloat(legRadius);
					output.addInt(legParts);
					output.addFloat(legClawLength);
				}
			};
		}
	}
	
	public static class Textures {
		
		public static final MyrrePainter MYRMORA = new MyrrePainter(new ColorAlpha(87, 0, 127, 120), new ColorAlpha(200, 0, 0, 200), new ColorAlpha(0, 0, 0, 170));
	}
	
	public static class Helper {
		
		private static ModelPart getLeftArm(ModelPart belly){
			return belly.getChildren()[0];
		}
		
		private static ModelPart getRightArm(ModelPart belly){
			return belly.getChildren()[1];
		}
		
		private static ModelPart getLeftLeg(ModelPart belly){
			return belly.getChildren()[2];
		}
		
		private static ModelPart getRightLeg(ModelPart belly){
			return belly.getChildren()[3];
		}
		
		private static ModelPart[] getLegParts(ModelPart leg){
			int length = 1;
			ModelPart part = leg;
			while(part.getChildren().length > 0){
				part = part.getChildren()[0];
				length++;
			}
			ModelPart[] parts = new ModelPart[length];
			part = leg;
			for(int index = 0; index < length; index++){
				parts[index] = part;
				part = part.getChildren()[0];
			}
			return parts;
		}
		
		private static MyrreHandModelPart getLeftHand(ModelPart belly){
			return getHand(getLeftArm(belly));
		}
		
		private static MyrreHandModelPart getRightHand(ModelPart belly){
			return getHand(getRightArm(belly));
		}
		
		private static ModelPart getLeftHandClaw(ModelPart belly){
			return getClaw(getHand(getLeftArm(belly)));
		}
		
		private static ModelPart getRightHandClaw(ModelPart belly){
			return getClaw(getHand(getRightArm(belly)));
		}
		
		private static void showLeftClaw(ModelPart belly){
			getLeftHand(belly).weaponType = MyrreHandModelPart.WEAPON_CLAW;
		}
		
		private static void showRightClaw(ModelPart belly){
			getRightHand(belly).weaponType = MyrreHandModelPart.WEAPON_CLAW;
		}
		
		private static void hideLeftClaw(ModelPart belly){
			getLeftHand(belly).weaponType = MyrreHandModelPart.WEAPON_NONE;
		}
		
		private static void hideRightClaw(ModelPart belly){
			getRightHand(belly).weaponType = MyrreHandModelPart.WEAPON_NONE;
		}
		
		private static void setLeftWeapon(ModelPart belly, ModelPart weapon){
			getLeftHand(belly).setWeapon(weapon);
		}
		
		private static void setRightWeapon(ModelPart belly, ModelPart weapon){
			getRightHand(belly).setWeapon(weapon);
		}
		
		private static void clearLeftWeapon(ModelPart belly){
			getLeftHand(belly).setWeapon(null);
		}
		
		private static void clearRightWeapon(ModelPart belly){
			getRightHand(belly).setWeapon(null);
		}
		
		private static MyrreHandModelPart getHand(ModelPart arm){
			if(arm instanceof MyrreHandModelPart)
				return (MyrreHandModelPart) arm;
			return getHand(arm.getChildren()[0]);
		}
		
		private static ModelPart[] getArmParts(ModelPart arm){
			int length = 1;
			ModelPart part = arm;
			while(!(part instanceof MyrreHandModelPart)){
				part = arm.getChildren()[0];
				length++;
			}
			ModelPart[] parts = new ModelPart[length];
			part = arm;
			for(int index = 0; index < length; index++){
				parts[index] = part;
				part = arm.getChildren()[0];
			}
			return parts;
		}
		
		private static ModelPart getClaw(MyrreHandModelPart hand){
			return hand.claw[0];
		}
		
		public static ModelPart getBelly(ModelOwner myrre){
			return myrre.getModels().get(0);
		}
		
		public static ModelPart getLeftArm(ModelOwner myrre){
			return getLeftArm(getBelly(myrre));
		}
		
		public static ModelPart getRightArm(ModelOwner myrre){
			return getRightArm(getBelly(myrre));
		}
		
		public static ModelPart getLeftLeg(ModelOwner myrre){
			return getLeftLeg(getBelly(myrre));
		}
		
		public static ModelPart getRightLeg(ModelOwner myrre){
			return getRightLeg(getBelly(myrre));
		}
		
		public static ModelPart[] getLeftArmParts(ModelOwner myrre){
			return getArmParts(getLeftArm(getBelly(myrre)));
		}
		
		public static ModelPart[] getRightArmParts(ModelOwner myrre){
			return getArmParts(getRightArm(getBelly(myrre)));
		}
		
		public static ModelPart[] getLeftLegParts(ModelOwner myrre){
			return getLegParts(getLeftLeg(getBelly(myrre)));
		}
		
		public static ModelPart[] getRightLegParts(ModelOwner myrre){
			return getLegParts(getRightLeg(getBelly(myrre)));
		}
		
		public static ModelPart getLeftHand(ModelOwner myrre){
			return getLeftHand(getBelly(myrre));
		}
		
		public static ModelPart getRightHand(ModelOwner myrre){
			return getRightHand(getBelly(myrre));
		}
		
		public static ModelPart getLeftHandClaw(ModelOwner myrre){
			return getLeftHandClaw(getBelly(myrre));
		}
		
		public static ModelPart getRightHandClaw(ModelOwner myrre){
			return getRightHandClaw(getBelly(myrre));
		}
		
		public static void showLeftClaw(ModelOwner myrre){
			showLeftClaw(getBelly(myrre));
		}
		
		public static void showRightClaw(ModelOwner myrre){
			showRightClaw(getBelly(myrre));
		}
		
		public static void hideLeftClaw(ModelOwner myrre){
			hideLeftClaw(getBelly(myrre));
		}
		
		public static void hideRightClaw(ModelOwner myrre){
			hideRightClaw(getBelly(myrre));
		}
		
		public static void setLeftWeapon(ModelOwner myrre, ModelPart weapon){
			setLeftWeapon(getBelly(myrre), weapon);
		}
		
		public static void setRightWeapon(ModelOwner myrre, ModelPart weapon){
			setRightWeapon(getBelly(myrre), weapon);
		}
		
		public static void clearLeftWeapon(ModelOwner myrre){
			clearLeftWeapon(getBelly(myrre));
		}
		
		public static void clearRightWeapon(ModelOwner myrre){
			clearRightWeapon(getBelly(myrre));
		}
	}
	
	public static class MyrreHandModelPart extends ModelPart {
		
		private static final byte WEAPON_NONE = 0;
		private static final byte WEAPON_CLAW = 1;
		private static final byte WEAPON_REAL = 2;
		
		private ModelPart[] claw;
		private ModelPart[] weapon;
		
		private byte weaponType;

		public MyrreHandModelPart(AbstractModel model, ModelTexture texture, Vector3f relativePosition, ModelPart claw) {
			super(model, texture, relativePosition);
			this.claw = new ModelPart[]{claw};
		}
		
		@Override
		public ModelPart[] getChildren(){
			if(weaponType == WEAPON_NONE)
				return super.getChildren();
			if(weaponType == WEAPON_CLAW)
				return claw;
			return weapon;
		}
		
		private void setWeapon(ModelPart weapon){
			if(weapon == null && weaponType == WEAPON_REAL){
				this.weapon = new ModelPart[1];
				weaponType = WEAPON_NONE;
			}
			if(weapon != null){
				this.weapon = new ModelPart[]{weapon};
				weaponType = WEAPON_REAL;
			}
		}
	}
}