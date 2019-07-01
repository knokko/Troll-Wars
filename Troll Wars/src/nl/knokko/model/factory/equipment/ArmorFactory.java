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
package nl.knokko.model.factory.equipment;

import static java.lang.Math.PI;

import org.lwjgl.util.vector.Vector3f;

import nl.knokko.main.Game;
import nl.knokko.model.ModelPart;
import nl.knokko.model.body.arm.HumanoidArm;
import nl.knokko.model.body.belly.HumanoidBelly;
import nl.knokko.model.body.foot.HumanoidFootProperties;
import nl.knokko.model.body.hand.HumanoidHandProperties;
import nl.knokko.model.body.head.HeadProperties;
import nl.knokko.model.body.leg.HumanoidLeg;
import nl.knokko.model.equipment.boots.ModelArmorFoot;
import nl.knokko.model.equipment.chestplate.ModelArmorBelly;
import nl.knokko.model.equipment.chestplate.ModelArmorUnderArm;
import nl.knokko.model.equipment.chestplate.ModelArmorUpperArm;
import nl.knokko.model.equipment.hands.ModelArmorGlobe;
import nl.knokko.model.equipment.helmet.ModelArmorHead;
import nl.knokko.model.equipment.pants.ModelArmorUnderLeg;
import nl.knokko.model.equipment.pants.ModelArmorUpperLeg;
import nl.knokko.model.factory.ModelBuilder;
import nl.knokko.texture.ModelTexture;
import nl.knokko.texture.Texture;
import nl.knokko.texture.area.TextureArea;
import nl.knokko.texture.builder.TextureBuilder;
import nl.knokko.texture.equipment.ArmorTexture;
import nl.knokko.texture.factory.MyTextureLoader;
import nl.knokko.texture.marker.TextureMarker;
import nl.knokko.util.Maths;

public class ArmorFactory {
	
	public static ModelPart createModelArmorHelmet(ModelArmorHead armor, HeadProperties head, ArmorTexture at){
		float f = armor.distanceToHeadFactor();
		TextureMarker marker = new TextureMarker(TextureMarker.createSphere(head.headHeight() * f, head.headWidth() * f, head.headDepth() * f, Game.getOptions().pixelsPerMeter));
		TextureArea area = marker.getArea(0);
		ModelBuilder builder = new ModelBuilder();
		int tw = marker.getWidth();
		int th = marker.getHeight();
		TextureBuilder textureBuilder = MyTextureLoader.createTextureBuilder(tw, th, true);
		at.getHelmet().paint(textureBuilder, area);
		float radiusX = head.headWidth() * armor.distanceToHeadFactor() / 2;
		float radiusY = head.headHeight() * armor.distanceToHeadFactor() / 2;
		float radiusZ = head.headDepth() * armor.distanceToHeadFactor() / 2;
		builder.addSphere(20, 0, head.headHeight() / 2, 0, radiusX, radiusY, radiusZ, area.getMinU(tw), area.getMinV(th), area.getMaxU(tw), area.getMaxV(th));
		return new ModelPart(builder.load(), new ModelTexture(new Texture(MyTextureLoader.loadTexture(textureBuilder)), at.getShineDamper(), at.getReflectivity()), new Vector3f());
	}
	
	public static ModelPart createModelArmorGlobe(ModelArmorGlobe armor, HumanoidHandProperties hand, ArmorTexture at, boolean left){
		float factor = armor.distanceToHandFactor();
		TextureMarker marker = new TextureMarker(new TextureArea(2 * factor * (hand.handHeight() + hand.handWidth()) * Game.getOptions().pixelsPerMeter, (hand.handCoreLength() + hand.fingerLength()) * factor * Game.getOptions().pixelsPerMeter));
		TextureArea ta = marker.getArea(0);
		ModelBuilder builder = new ModelBuilder();
		int tw = marker.getWidth();
		int th = marker.getHeight();
		TextureBuilder textureBuilder = MyTextureLoader.createTextureBuilder(tw, th, true);
		at.getGlobe().paint(textureBuilder, ta);
		float minU = ta.getMinU(tw);
		float minV = ta.getMinV(th);
		float maxU = ta.getMaxU(tw);
		float maxV = ta.getMaxV(th);
		float deltaU = maxU - minU;
		float deltaV = maxV - minV;
		float vEdge = minV + deltaV * (hand.handCoreLength() / (hand.handCoreLength() + hand.fingerLength()));
		float uLeftTop = minU;
		float handCircle = hand.handWidth() * 2 + hand.handHeight() * 2;
		float uRightTop = minU + deltaU * (hand.handWidth() / handCircle);
		float uRightBottom = minU + deltaU * 0.5f;
		float uLeftBottom = minU + deltaU * ((2 * hand.handWidth() + hand.handHeight()) / handCircle);
		Vector3f lu = (Vector3f) new Vector3f(-1, 1, 0).normalise();
		float hw = factor * hand.handWidth() / 2;
		float hh = factor * hand.handHeight() / 2;
		float hcl = factor * hand.handCoreLength();
		int indexBack = 0;
		builder.addVertex(-hw, hh, 0, lu.x, lu.y, lu.z, uLeftTop, minV);//left top
		builder.addVertex(hw, hh, 0, -lu.x, lu.y, lu.z, uRightTop, minV);//right top
		builder.addVertex(hw, -hh, 0, -lu.x, -lu.y, lu.z, uRightBottom, minV);//right bottom
		builder.addVertex(-hw, -hh, 0, lu.x, -lu.y, lu.z, uLeftBottom, minV);//left bottom
		builder.addVertex(-hw, hh, 0, lu.x, lu.y, lu.z, maxU, minV);//left top 2
		int indexEdge = builder.vertexCount();
		builder.addVertex(-hw, hh, -hcl, lu.x, lu.y, lu.z, uLeftTop, vEdge);//left top
		builder.addVertex(hw, hh, -hcl, -lu.x, lu.y, lu.z, uRightTop, vEdge);//right top
		builder.addVertex(hw, -hh, -hcl, -lu.x, -lu.y, lu.z, uRightBottom, vEdge);//right bottom
		builder.addVertex(-hw, -hh, -hcl, lu.x, -lu.y, lu.z, uLeftBottom, vEdge);//left bottom
		builder.addVertex(-hw, hh, -hcl, lu.x, lu.y, lu.z, maxU, vEdge);//left top 2
		float angle = 30f;
		float fl = factor * hand.fingerLength() / 3;
		float sfl = Maths.sin(angle) * fl;
		float cfl = Maths.cos(angle) * fl;
		float sfl2 = Maths.sin(angle + angle) * fl;
		float cfl2 = Maths.cos(angle + angle) * fl;
		float sfl3 = Maths.sin(angle + angle + angle) * fl;
		float cfl3 = Maths.cos(angle + angle + angle) * fl;
		float vf1 = minV + deltaV * ((hcl + fl) / (hcl + hand.fingerLength()));
		float vf2 = minV + deltaV * ((hcl + fl + fl) / (hcl + hand.fingerLength()));
		float vf3 = maxV;
		int indexFinger1 = builder.vertexCount();
		builder.addVertex(-hw - sfl, hh, -hcl - cfl, lu.x, lu.y, lu.z, uLeftTop, vf1);//left top finger 1
		builder.addVertex(hw - sfl, hh, -hcl - cfl, -lu.x, lu.y, lu.z, uRightTop, vf1);//right top finger 1
		builder.addVertex(hw - sfl, -hh, -hcl - cfl, -lu.x, -lu.y, lu.z, uRightBottom, vf1);//right bottom finger 1
		builder.addVertex(-hw - sfl, -hh, -hcl - cfl, lu.x, -lu.y, lu.z, uLeftBottom, vf1);//left bottom finger 1
		builder.addVertex(-hw - sfl, hh, -hcl - cfl, lu.x, lu.y, lu.z, maxU, vf1);//left top finger 1 (2)
		int indexFinger2 = builder.vertexCount();
		builder.addVertex(-hw - sfl - sfl2, hh, -hcl - cfl - cfl2, lu.x, lu.y, lu.z, uLeftTop, vf2);//left top finger 2
		builder.addVertex(hw - sfl - sfl2, hh, -hcl - cfl - cfl2, -lu.x, lu.y, lu.z, uRightTop, vf2);//right top finger 2
		builder.addVertex(hw - sfl - sfl2, -hh, -hcl - cfl - cfl2, -lu.x, -lu.y, lu.z, uRightBottom, vf2);//right bottom finger 2
		builder.addVertex(-hw - sfl - sfl2, -hh, -hcl - cfl - cfl2, lu.x, -lu.y, lu.z, uLeftBottom, vf2);//left bottom finger 2
		builder.addVertex(-hw - sfl - sfl2, hh, -hcl - cfl - cfl2, lu.x, lu.y, lu.z, maxU, vf2);//left top finger 2 (2)
		int indexFinger3 = builder.vertexCount();
		builder.addVertex(-hw - sfl - sfl2 - sfl3, hh, -hcl - cfl - cfl2 - cfl3, lu.x, lu.y, lu.z, uLeftTop, vf3);//left top finger 3
		builder.addVertex(hw - sfl - sfl2 - sfl3, hh, -hcl - cfl - cfl2 - cfl3, -lu.x, lu.y, lu.z, uRightTop, vf3);//right top finger 3
		builder.addVertex(hw - sfl - sfl2 - sfl3, -hh, -hcl - cfl - cfl2 - cfl3, -lu.x, -lu.y, lu.z, uRightBottom, vf3);//right bottom finger 3
		builder.addVertex(-hw - sfl - sfl2 - sfl3, -hh, -hcl - cfl - cfl2 - cfl3, lu.x, -lu.y, lu.z, uLeftBottom, vf3);//left bottom finger 3
		builder.addVertex(-hw - sfl - sfl2 - sfl3, hh, -hcl - cfl - cfl2 - cfl3, lu.x, lu.y, lu.z, maxU, vf3);//left top finger 3 (2)
		if(left)
			builder.mirrorX();
		builder.bindFourangle(indexBack, indexBack + 1, indexEdge + 1, indexEdge);
		builder.bindFourangle(indexBack + 1, indexBack + 2, indexEdge + 2, indexEdge + 1);
		builder.bindFourangle(indexBack + 2, indexBack + 3, indexEdge + 3, indexEdge + 2);
		builder.bindFourangle(indexBack + 3, indexBack + 4, indexEdge + 4, indexEdge + 3);
		builder.bindFourangle(indexEdge, indexEdge + 1, indexFinger1 + 1, indexFinger1);
		builder.bindFourangle(indexEdge + 1, indexEdge + 2, indexFinger1 + 2, indexFinger1 + 1);
		builder.bindFourangle(indexEdge + 2, indexEdge + 3, indexFinger1 + 3, indexFinger1 + 2);
		builder.bindFourangle(indexEdge + 3, indexEdge + 4, indexFinger1 + 4, indexFinger1 + 3);
		builder.bindFourangle(indexFinger1, indexFinger1 + 1, indexFinger2 + 1, indexFinger2);
		builder.bindFourangle(indexFinger1 + 1, indexFinger1 + 2, indexFinger2 + 2, indexFinger2 + 1);
		builder.bindFourangle(indexFinger1 + 2, indexFinger1 + 3, indexFinger2 + 3, indexFinger2 + 2);
		builder.bindFourangle(indexFinger1 + 3, indexFinger1 + 4, indexFinger2 + 4, indexFinger2 + 3);
		builder.bindFourangle(indexFinger2, indexFinger2 + 1, indexFinger3 + 1, indexFinger3);
		builder.bindFourangle(indexFinger2 + 1, indexFinger2 + 2, indexFinger3 + 2, indexFinger3 + 1);
		builder.bindFourangle(indexFinger2 + 2, indexFinger2 + 3, indexFinger3 + 3, indexFinger3 + 2);
		builder.bindFourangle(indexFinger2 + 3, indexFinger2 + 4, indexFinger3 + 4, indexFinger3 + 3);
		builder.bindFourangle(indexFinger3, indexFinger3 + 1, indexFinger3 + 2, indexFinger3 + 3);
		return new ModelPart(builder.load(), new ModelTexture(new Texture(MyTextureLoader.loadTexture(textureBuilder)), at.getShineDamper(), at.getReflectivity()), new Vector3f());
	}
	
	public static ModelPart createModelArmorBelly(ModelArmorBelly armor, HumanoidBelly belly, ArmorTexture texture){
		float f = armor.distanceToBellyFactor();
		TextureMarker marker = new TextureMarker(TextureMarker.createCilinder(belly.bellyHeight() * f, belly.bellyWidth() * f, belly.bellyWidth() * f, Game.getOptions().pixelsPerMeter), TextureMarker.createHalfSphere(belly.bellyDepth() * f, belly.bellyWidth() * f, belly.bellyDepth() * f, Game.getOptions().pixelsPerMeter));
		TextureArea areaCil = marker.getArea(0);
		TextureArea areaTop = marker.getArea(1);
		TextureBuilder textureBuilder = MyTextureLoader.createTextureBuilder(marker.getWidth(), marker.getHeight(), true);
		texture.getChestplate().paint(textureBuilder, areaCil);
		texture.getChestplateTop().paint(textureBuilder, areaTop);
		ModelBuilder builder = new ModelBuilder();
		int tw = marker.getWidth();
		int th = marker.getHeight();
		builder.addSphereTop(10, 0, belly.bellyHeight() / 2, 0, belly.bellyWidth() / 2 * f, belly.bellyDepth() / 2 * f, belly.bellyDepth() / 2 * f, areaTop.getMinU(tw), areaTop.getMinV(th), areaTop.getMaxU(tw), areaTop.getMaxV(th));
		builder.addVerticalCilinder(10, 0, 0, belly.bellyWidth() / 2 * f, belly.bellyDepth() / 2 * f, -belly.bellyHeight() * 0.5f * f, belly.bellyHeight() * 0.5f * f, areaCil.getMinU(tw), areaCil.getMinV(th), areaCil.getMaxU(tw), areaCil.getMaxV(th));
		return new ModelPart(builder.load(), new ModelTexture(new Texture(MyTextureLoader.loadTexture(textureBuilder)), texture.getShineDamper(), texture.getReflectivity()), new Vector3f());
	}
	
	public static ModelPart createModelArmorUpperArm(ModelArmorUpperArm armor, HumanoidArm arm, ArmorTexture at){
		float af = armor.distanceToArmFactor();
		float sf = armor.distanceToShoulderFactor();
		TextureMarker marker = new TextureMarker(TextureMarker.createHalfSphere(arm.shoulderRadius() * sf, arm.shoulderRadius() * sf * 2, arm.shoulderRadius() * sf * 2, Game.getOptions().pixelsPerMeter), TextureMarker.createCilinder(arm.underArmLength(), arm.shoulderRadius() * 2 * af, arm.shoulderRadius() * 2 * af, Game.getOptions().pixelsPerMeter));
		int tw = marker.getWidth();
		int th = marker.getHeight();
		TextureArea elbow = marker.getArea(0);
		TextureArea upperArm = marker.getArea(1);
		TextureBuilder textureBuilder = MyTextureLoader.createTextureBuilder(tw, th, true);
		at.getElbow().paint(textureBuilder, elbow);
		at.getUpperArm().paint(textureBuilder, upperArm);
		ModelBuilder builder = new ModelBuilder();
		builder.addSphereSouth(10, 0, 0, 0, arm.shoulderRadius() * sf, arm.shoulderRadius() * sf, arm.shoulderRadius() * sf, elbow.getMinU(tw), elbow.getMinV(th), elbow.getMaxU(tw), elbow.getMaxV(th));
		builder.addHorizontalCilinder(10, 0f, 0f, arm.shoulderRadius() * af, arm.shoulderRadius() * af, arm.elbowRadius() * af, arm.elbowRadius() * af, 0, -arm.upperArmLength(), upperArm.getMinU(tw), upperArm.getMinV(th), upperArm.getMaxU(tw), upperArm.getMaxV(th));
		return new ModelPart(builder.load(), new ModelTexture(new Texture(MyTextureLoader.loadTexture(textureBuilder)), at.getShineDamper(), at.getReflectivity()), new Vector3f());
	}
	
	public static ModelPart createModelArmorUnderArm(ModelArmorUnderArm armor, HumanoidArm arm, ArmorTexture at){
		float f = armor.distanceToArmFactor();
		TextureMarker marker = new TextureMarker(TextureMarker.createCilinder(arm.underArmLength(), arm.elbowRadius() * 2 * f, arm.elbowRadius() * 2 * f, Game.getOptions().pixelsPerMeter));
		int tw = marker.getWidth();
		int th = marker.getHeight();
		TextureBuilder textureBuilder = MyTextureLoader.createTextureBuilder(tw, th, true);
		TextureArea ta = marker.getArea(0);
		at.getUnderArm().paint(textureBuilder, ta);
		ModelBuilder builder = new ModelBuilder();
		builder.addHorizontalCilinder(10, 0f, 0f, arm.elbowRadius() * f, arm.elbowRadius() * f, arm.wristRadius() * f, arm.wristRadius() * f, 0, -arm.underArmLength(), ta.getMinU(tw), ta.getMinV(th), ta.getMaxU(tw), ta.getMaxV(th));
		return new ModelPart(builder.load(), new ModelTexture(new Texture(MyTextureLoader.loadTexture(textureBuilder)), at.getShineDamper(), at.getReflectivity()), new Vector3f());
	}
	
	public static ModelPart createModelArmorUpperLeg(ModelArmorUpperLeg armor, HumanoidLeg leg, ArmorTexture at){
		ModelBuilder builder = new ModelBuilder();
		float f = armor.distanceToLegFactor();
		TextureMarker marker = new TextureMarker(TextureMarker.createCilinder(leg.upperLegLength(), leg.legUpperRadius() * 2 * f, leg.legUpperRadius() * 2 * f, Game.getOptions().pixelsPerMeter));
		int tw = marker.getWidth();
		int th = marker.getHeight();
		TextureArea ta = marker.getArea(0);
		TextureBuilder textureBuilder = MyTextureLoader.createTextureBuilder(tw, th, true);
		at.getUpperLeg().paint(textureBuilder, ta);
		builder.addVerticalCilinder(10, 0f, 0f, leg.legUpperRadius() * f, leg.legUpperRadius() * f, leg.kneeRadius() * f, leg.kneeRadius() * f, 0, -leg.upperLegLength(), ta.getMinU(tw), ta.getMinV(th), ta.getMaxU(tw), ta.getMaxV(th));
		return new ModelPart(builder.load(), new ModelTexture(new Texture(MyTextureLoader.loadTexture(textureBuilder)), at.getShineDamper(), at.getReflectivity()), new Vector3f());
	}
	
	public static ModelPart createModelArmorUnderLeg(ModelArmorUnderLeg armor, HumanoidLeg leg, ArmorTexture at){
		ModelBuilder builder = new ModelBuilder();
		float f = armor.distanceToLegFactor();
		TextureMarker marker = new TextureMarker(TextureMarker.createCilinder(leg.underLegLength(), leg.kneeRadius() * 2 * f, leg.kneeRadius() * 2 * f, Game.getOptions().pixelsPerMeter));
		int tw = marker.getWidth();
		int th = marker.getHeight();
		TextureBuilder textureBuilder = MyTextureLoader.createTextureBuilder(tw, th, true);
		TextureArea ta = marker.getArea(0);
		at.getUnderLeg().paint(textureBuilder, ta);
		builder.addVerticalCilinder(10, 0f, 0f, leg.kneeRadius() * f, leg.kneeRadius() * f, leg.legUnderRadius() * f, leg.legUnderRadius() * f, 0, -leg.underLegLength(), ta.getMinU(tw), ta.getMinV(th), ta.getMaxU(tw), ta.getMaxV(th));
		return new ModelPart(builder.load(), new ModelTexture(new Texture(MyTextureLoader.loadTexture(textureBuilder)), at.getShineDamper(), at.getReflectivity()), new Vector3f());
	}
	
	public static ModelPart createModelArmorFoot(ModelArmorFoot armor, HumanoidFootProperties foot, HumanoidLeg leg, ArmorTexture at, boolean right){
		ModelBuilder builder = new ModelBuilder();
		float f = armor.distanceToFootFactor();
		int ppm = Game.getOptions().pixelsPerMeter;
		TextureMarker marker = new TextureMarker(new TextureArea(2 * foot.footFrontLength() * f * ppm + foot.footWidth() * PI * f * ppm, foot.footWidth() * 2 * f * ppm));
		int tw = marker.getWidth();
		int th = marker.getHeight();
		TextureArea ta = marker.getArea(0);
		TextureBuilder textureBuilder = MyTextureLoader.createTextureBuilder(tw, th, true);
		at.getShoe().paint(textureBuilder, ta);
		float minV = ta.getMinV(th);
		float minU = ta.getMinU(tw);
		float maxU = ta.getMaxU(tw);
		float maxV = ta.getMaxV(th);
		float deltaU = maxU - minU;
		float deltaV = maxV - minV;
		float lowV = minV + 0.2f * deltaV;
		float lowVFront = minV + 0.4f * deltaV;
		float highVFront = minV + 0.6f * deltaV;
		int midParts = 5;
		int indexUpperCircle = builder.vertexCount();
		for(int i = 0; i <= midParts; i++){
			float angle = i / (float) midParts * 180f;
			builder.addVertex(Maths.cos(angle) * leg.legUnderRadius() * f, foot.footMidHeight() * f, -Maths.sin(angle) * leg.legUnderRadius() * f, Maths.cos(angle), 0, Maths.sin(angle), minU + (i / (float) midParts) * deltaU, minV);
		}
		int indexLowerCircle = builder.vertexCount();
		for(int i = 0; i <= midParts; i++){
			float angle = i / (float) midParts * 180f;
			builder.addVertex(Maths.cos(angle) * leg.legUnderRadius() * f, 0, -Maths.sin(angle) * leg.legUnderRadius() * f, Maths.cos(angle), 0, Maths.sin(angle), minU + (i / (float) midParts) * deltaU, lowV);
		}
		int indexLowFront = builder.vertexCount();
		builder.addVertex(-foot.footWidth()  * f / 2, 0, 0, -1, 0, 0, minU, lowVFront);
		builder.addVertex(-foot.footWidth()  * f / 2, 0, foot.footFrontLength() * f, -1, 0, 0, minU + 0.35f * deltaU, lowVFront);
		builder.addVertex(foot.footWidth() * f / 2, 0, foot.footFrontLength() * f, 1, 0, 0, minU + 0.65f * deltaU, lowVFront);
		builder.addVertex(foot.footWidth() * f / 2, 0, 0, 1, 0, 0, maxU, lowVFront);
		int indexHighFront = builder.vertexCount();
		builder.addVertex(-foot.footWidth() * f / 2, foot.footMidHeight() * f, 0, 0, 1, 0, maxU, highVFront);
		builder.addVertex(-foot.footWidth() * f / 2, foot.footOutHeight() * f, foot.footFrontLength() * f, 0, 1, 0, minU + 0.5f * deltaU, highVFront);
		builder.addVertex(foot.footWidth() * f / 2, foot.footOutHeight() * f, foot.footFrontLength() * f, 0, 1, 0, minU + 0.5f * deltaU, maxV);
		builder.addVertex(foot.footWidth() * f / 2, foot.footMidHeight() * f, 0, 0, 1, 0, maxU, maxV);
		builder.bindFourangle2(indexLowFront, indexLowFront + 1, indexHighFront, indexHighFront + 1);
		builder.bindFourangle2(indexLowFront + 1, indexLowFront + 2, indexHighFront + 1, indexHighFront + 2);
		builder.bindFourangle2(indexLowFront + 2, indexLowFront + 3, indexHighFront + 2, indexHighFront + 3);
		builder.bindFourangle(indexHighFront, indexHighFront + 1, indexHighFront + 2, indexHighFront + 3);
		builder.bindFourangle(indexLowFront, indexLowFront + 1, indexLowFront + 2, indexLowFront + 3);
		for(int i = 0; i < midParts; i++)
			builder.bindFourangle(indexLowerCircle + i, indexLowerCircle + i + 1, indexUpperCircle + i + 1, indexUpperCircle + i);
		return new ModelPart(builder.load(), new ModelTexture(new Texture(MyTextureLoader.loadTexture(textureBuilder)), at.getShineDamper(), at.getReflectivity()), new Vector3f());
	}
}