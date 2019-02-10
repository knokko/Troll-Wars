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

import nl.knokko.equipment.Equipment;
import nl.knokko.items.ItemChestplate;
import nl.knokko.items.ItemGlobe;
import nl.knokko.items.ItemHelmet;
import nl.knokko.items.ItemPants;
import nl.knokko.items.ItemShoe;
import nl.knokko.items.ItemWeapon;
import nl.knokko.model.ModelOwner;
import nl.knokko.model.ModelPart;
import nl.knokko.model.body.arm.HumanoidArm;
import nl.knokko.model.body.belly.HumanoidBelly;
import nl.knokko.model.body.foot.HumanoidFootProperties;
import nl.knokko.model.body.hand.HumanoidHandProperties;
import nl.knokko.model.body.head.HeadProperties;
import nl.knokko.model.body.leg.HumanoidLeg;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;

public interface BodyHumanoid extends HeadProperties, HumanoidBelly, HumanoidArm, HumanoidLeg, HumanoidFootProperties, HumanoidHandProperties, Body {
	
	void save(BitOutput output);
	
	public static class Instance implements BodyHumanoid {
		
		protected final float scale;
		
		protected final float headWidth;
		protected final float headHeight;
		protected final float headDepth;
		
		protected final float bellyHeight;
		protected final float bellyWidth;
		protected final float bellyDepth;
		
		protected final float elbowRadius;
		protected final float wristRadius;
		protected final float upperArmLength;
		protected final float underArmLength;
		
		protected final float upperLegLength;
		protected final float underLegLength;
		
		protected final float footFrontLength;
		protected final float footMidHeight;
		protected final float footOutHeight;
		protected final float footWidth;
		
		protected final float handCoreLength;
		protected final float handHeight;
		protected final float handWidth;
		protected final float thumbLength;
		protected final float fingerLength;
		
		public Instance(BitInput input){
			this(
					input.readFloat(), input.readFloat(), input.readFloat(), input.readFloat(),
					input.readFloat(), input.readFloat(), input.readFloat(), input.readFloat(), input.readFloat(),
					input.readFloat(), input.readFloat(), input.readFloat(), input.readFloat(),
					input.readFloat(), input.readFloat(), input.readFloat(), input.readFloat(),
					input.readFloat(), input.readFloat(), input.readFloat(), input.readFloat(), input.readFloat()
			);
		}
		
		public Instance(
				float scale,  float headWidth,  float headHeight,  float headDepth, 
				float bellyHeight,  float bellyWidth,  float bellyDepth,  float elbowRadius,  float wristRadius, 
				float upperArmLength,  float underArmLength,  float upperLegLength,  float underLegLength, 
				float footFrontLength,  float footMidHeight,  float footOutHeight,  float footWidth,
				float handCoreLength,  float handHeight,  float handWidth,  float thumbLength,  float fingerLength
				){
			this.scale = scale;
			this.headWidth = headWidth;
			this.headHeight = headHeight;
			this.headDepth = headDepth;
			this.bellyHeight = bellyHeight;
			this.bellyWidth = bellyWidth;
			this.bellyDepth = bellyDepth;
			this.elbowRadius = elbowRadius;
			this.wristRadius = wristRadius;
			this.upperArmLength = upperArmLength;
			this.underArmLength = underArmLength;
			this.upperLegLength = upperLegLength;
			this.underLegLength = underLegLength;
			this.footFrontLength = footFrontLength;
			this.footMidHeight = footMidHeight;
			this.footOutHeight = footOutHeight;
			this.footWidth = footWidth;
			this.handCoreLength = handCoreLength;
			this.handHeight = handHeight;
			this.handWidth = handWidth;
			this.thumbLength = thumbLength;
			this.fingerLength = fingerLength;
		}
		
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
		}
	}
	
	public static class Helper {
		
		public static ModelPart getBelly(ModelOwner creature){
			return creature.getModels().get(0);
		}
		
		public static ModelPart getHead(ModelPart belly){
			return belly.getChildren()[0];
		}
		
		public static ModelPart getBelly(ModelPart belly){
			return belly;
		}
		
		public static ModelPart getLeftArm(ModelPart belly){
			return belly.getChildren()[1];
		}
		
		public static ModelPart getRightArm(ModelPart belly){
			return belly.getChildren()[2];
		}
		
		public static ModelPart getLeftUnderArm(ModelPart belly){
			return getLeftArm(belly).getChildren()[0];
		}
		
		public static ModelPart getRightUnderArm(ModelPart belly){
			return getRightArm(belly).getChildren()[0];
		}
		
		public static ModelPart getLeftHand(ModelPart belly){
			return getLeftUnderArm(belly).getChildren()[0];
		}
		
		public static ModelPart getRightHand(ModelPart belly){
			return getRightUnderArm(belly).getChildren()[0];
		}
		
		public static ModelPart getLeftLeg(ModelPart belly){
			return belly.getChildren()[3];
		}
		
		public static ModelPart getRightLeg(ModelPart belly){
			return belly.getChildren()[4];
		}
		
		public static ModelPart getLeftUnderLeg(ModelPart belly){
			return getLeftLeg(belly).getChildren()[0];
		}
		
		public static ModelPart getRightUnderLeg(ModelPart belly){
			return getRightLeg(belly).getChildren()[0];
		}
		
		public static ModelPart getLeftFoot(ModelPart belly){
			return getLeftUnderLeg(belly).getChildren()[0];
		}
		
		public static ModelPart getRightFoot(ModelPart belly){
			return getRightUnderLeg(belly).getChildren()[0];
		}
		
		private static void setEquipment(ModelPart part, ModelPart equipment, int index){
			if(equipment != null)
				equipment.setParent(part);
			if(part.getChildren().length > index)
				part.getChildren()[index] = equipment;
			else if(equipment != null)
				part.addChild(equipment);
		}
		
		public static void setEquipment(ModelOwner creature, Equipment equipment, BodyHumanoid body){
			setEquipment(getBelly(creature), equipment, body);
		}
		
		public static void setEquipment(ModelPart belly, Equipment equipment, BodyHumanoid body){
			setHelmet(belly, equipment.getHelmet(), body);
			setChestplate(belly, equipment.getChestplate(), body);
			setLeftWeapon(belly, equipment.getLeftWeapon(), body);
			setRightWeapon(belly, equipment.getRightWeapon(), body);
			setLeftGlobe(belly, equipment.getLeftGlobe(), body);
			setRightGlobe(belly, equipment.getRightGlobe(), body);
			setPants(belly, equipment.getPants(), body);
			setLeftShoe(belly, equipment.getLeftShoe(), body);
			setRightShoe(belly, equipment.getRightShoe(), body);
		}
		
		public static void setHelmet(ModelOwner creature, ItemHelmet helmet, HeadProperties head){
			setHelmet(creature, helmet != null ? helmet.createModel(head) : null);
		}
		
		public static void setHelmet(ModelOwner creature, ModelPart helmet){
			setHelmet(getBelly(creature), helmet);
		}
		
		public static void setHelmet(ModelPart belly, ItemHelmet helmet, HeadProperties head){
			setHelmet(belly, helmet.createModel(head));
		}
		
		public static void setHelmet(ModelPart belly, ModelPart helmet){
			setEquipment(getHead(belly), helmet, 0);
		}
		
		public static void setChestplate(ModelOwner creature, ItemChestplate plate, HumanoidBelly belly, HumanoidArm arm){
			if(plate != null)
				setChestplate(creature, plate.createModelBelly(belly), plate.createModelUpperArm(arm), plate.createModelUnderArm(arm));
			else
				setChestplate(creature, (ModelPart) null, null, null);
		}
		
		public static void setChestplate(ModelOwner creature, ItemChestplate plate, BodyHumanoid body){
			if(plate != null)
				setChestplate(creature, plate.createModelBelly(body), plate.createModelUpperArm(body), plate.createModelUnderArm(body));
			else
				setChestplate(creature, (ModelPart) null, null, null);
		}
		
		public static void setChestplate(ModelOwner creature, ModelPart belly, ModelPart upperArm, ModelPart underArm){
			setChestplate(getBelly(creature), belly, upperArm, underArm);
		}
		
		public static void setChestplate(ModelPart belly, ItemChestplate plate, HumanoidBelly Belly, HumanoidArm arm){
			if(plate != null)
				setChestplate(belly, plate.createModelBelly(Belly), plate.createModelUpperArm(arm), plate.createModelUnderArm(arm));
			else
				setChestplate(belly, (ModelPart) null, null, null);
		}
		
		public static void setChestplate(ModelPart belly, ItemChestplate plate, BodyHumanoid body){
			if(plate != null)
				setChestplate(belly, plate.createModelBelly(body), plate.createModelUpperArm(body), plate.createModelUnderArm(body));
			else
				setChestplate(belly, (ModelPart) null, null, null);
		}
		
		public static void setChestplate(ModelPart belly, ModelPart chestplate, ModelPart upperArm, ModelPart underArm){
			setEquipment(getBelly(belly), chestplate, 5);
			setEquipment(getLeftArm(belly), upperArm != null ? upperArm.clone() : upperArm, 1);
			setEquipment(getLeftUnderArm(belly), underArm != null ? underArm.clone() : null, 1);
			setEquipment(getRightArm(belly), upperArm != null ? upperArm.clone() : null, 1);
			setEquipment(getRightUnderArm(belly), underArm != null ? underArm.clone() : null, 1);
		}
		
		public static void setLeftWeapon(ModelOwner creature, ItemWeapon weapon, HumanoidHandProperties hand){
			setLeftWeapon(creature, weapon != null ? weapon.createModel(hand, true) : null);
		}
		
		public static void setLeftWeapon(ModelOwner creature, ModelPart weapon){
			setLeftWeapon(getBelly(creature), weapon);
		}
		
		public static void setLeftWeapon(ModelPart belly, ItemWeapon weapon, HumanoidHandProperties hand){
			setLeftWeapon(belly, weapon != null ? weapon.createModel(hand, true) : null);
		}
		
		public static void setLeftWeapon(ModelPart belly, ModelPart weapon){
			setEquipment(getLeftHand(belly), weapon, 0);
		}
		
		public static void setRightWeapon(ModelOwner creature, ItemWeapon weapon, HumanoidHandProperties hand){
			setRightWeapon(creature, weapon != null ? weapon.createModel(hand, false) : null);
		}
		
		public static void setRightWeapon(ModelOwner creature, ModelPart weapon){
			setRightWeapon(getBelly(creature), weapon);
		}
		
		public static void setRightWeapon(ModelPart belly, ItemWeapon weapon, HumanoidHandProperties hand){
			setRightWeapon(belly, weapon != null ? weapon.createModel(hand, false) : null);
		}
		
		public static void setRightWeapon(ModelPart belly, ModelPart weapon){
			setEquipment(getRightHand(belly), weapon, 0);
		}
		
		public static void setLeftGlobe(ModelOwner creature, ItemGlobe globe, HumanoidHandProperties hand){
			setLeftGlobe(creature, globe != null ? globe.createModelLeft(hand) : null);
		}
		
		public static void setLeftGlobe(ModelOwner creature, ModelPart globe){
			setLeftGlobe(getBelly(creature), globe);
		}
		
		public static void setLeftGlobe(ModelPart belly, ItemGlobe globe, HumanoidHandProperties hand){
			setLeftGlobe(belly, globe != null ? globe.createModelLeft(hand) : null);
		}
		
		public static void setLeftGlobe(ModelPart belly, ModelPart globe){
			setEquipment(getLeftHand(belly), globe, 1);
		}
		
		public static void setRightGlobe(ModelOwner creature, ItemGlobe globe, HumanoidHandProperties hand){
			setRightGlobe(creature, globe != null ? globe.createModelRight(hand) : null);
		}
		
		public static void setRightGlobe(ModelOwner creature, ModelPart globe){
			setRightGlobe(getBelly(creature), globe);
		}
		
		public static void setRightGlobe(ModelPart belly, ItemGlobe globe, HumanoidHandProperties hand){
			setRightGlobe(belly, globe != null ? globe.createModelRight(hand) : null);
		}
		
		public static void setRightGlobe(ModelPart belly, ModelPart globe){
			setEquipment(getRightHand(belly), globe, 1);
		}
		
		public static void setPants(ModelOwner creature, ItemPants pants, HumanoidLeg leg){
			if(pants != null)
				setPants(creature, pants.createModelUpperLeg(leg), pants.createModelUnderLeg(leg));
			else
				setPants(creature, (ModelPart) null, null);
		}
		
		public static void setPants(ModelOwner creature, ModelPart upperLeg, ModelPart underLeg){
			setPants(getBelly(creature), upperLeg, underLeg);
		}
		
		public static void setPants(ModelPart belly, ItemPants pants, HumanoidLeg leg){
			if(pants != null)
				setPants(belly, pants.createModelUpperLeg(leg), pants.createModelUnderLeg(leg));
			else
				setPants(belly, (ModelPart) null, null);
		}
		
		public static void setPants(ModelPart belly, ModelPart upperLeg, ModelPart underLeg){
			setEquipment(getLeftLeg(belly), upperLeg != null ? upperLeg.clone() : null, 1);
			setEquipment(getRightLeg(belly), upperLeg != null ? upperLeg.clone() : null, 1);
			setEquipment(getLeftUnderLeg(belly), underLeg != null ? underLeg.clone() : null, 1);
			setEquipment(getRightUnderLeg(belly), underLeg != null ? underLeg.clone() : null, 1);
		}
		
		public static void setLeftShoe(ModelOwner creature, ItemShoe shoe, BodyHumanoid body){
			setLeftShoe(creature, shoe != null ? shoe.createModelLeft(body, body) : null);
		}
		
		public static void setLeftShoe(ModelOwner creature, ItemShoe shoe, HumanoidLeg leg, HumanoidFootProperties foot){
			setLeftShoe(creature, shoe != null ? shoe.createModelLeft(foot, leg) : null);
		}
		
		public static void setLeftShoe(ModelOwner creature, ModelPart shoe){
			setLeftShoe(getBelly(creature), shoe);
		}
		
		public static void setLeftShoe(ModelPart belly, ItemShoe shoe, HumanoidLeg leg, HumanoidFootProperties foot){
			setLeftShoe(belly, shoe != null ? shoe.createModelLeft(foot, leg) : null);
		}
		
		public static void setLeftShoe(ModelPart belly, ItemShoe shoe, BodyHumanoid body){
			setLeftShoe(belly, shoe != null ? shoe.createModelLeft(body, body) : null);
		}
		
		public static void setLeftShoe(ModelPart belly, ModelPart shoe){
			setEquipment(getLeftFoot(belly), shoe != null ? shoe.clone() : null, 0);
		}
		
		public static void setRightShoe(ModelOwner creature, ItemShoe shoe, BodyHumanoid body){
			setRightShoe(creature, shoe != null ? shoe.createModelRight(body, body) : null);
		}
		
		public static void setRightShoe(ModelOwner creature, ItemShoe shoe, HumanoidLeg leg, HumanoidFootProperties foot){
			setRightShoe(creature, shoe != null ? shoe.createModelRight(foot, leg) : null);
		}
		
		public static void setRightShoe(ModelOwner creature, ModelPart shoe){
			setRightShoe(getBelly(creature), shoe);
		}
		
		public static void setRightShoe(ModelPart belly, ItemShoe shoe, HumanoidLeg leg, HumanoidFootProperties foot){
			setRightShoe(belly, shoe != null ? shoe.createModelRight(foot, leg) : null);
		}
		
		public static void setRightShoe(ModelPart belly, ItemShoe shoe, BodyHumanoid body){
			setRightShoe(belly, shoe != null ? shoe.createModelRight(body, body) : null);
		}
		
		public static void setRightShoe(ModelPart belly, ModelPart shoe){
			setEquipment(getRightFoot(belly), shoe != null ? shoe.clone() : null, 0);
		}
	}
}