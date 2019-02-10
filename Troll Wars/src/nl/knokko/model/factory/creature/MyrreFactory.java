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
package nl.knokko.model.factory.creature;

import org.lwjgl.util.vector.Vector3f;

import nl.knokko.main.Game;
import nl.knokko.model.ModelPart;
import nl.knokko.model.ModelPartParent;
import nl.knokko.model.body.BodyMyrre;
import nl.knokko.model.body.arm.MyrreArm;
import nl.knokko.model.body.belly.MyrreBelly;
import nl.knokko.model.body.claw.MyrreFootClaw;
import nl.knokko.model.body.claw.MyrreHandClaw;
import nl.knokko.model.body.leg.MyrreLeg;
import nl.knokko.model.factory.ModelBuilder;
import nl.knokko.model.type.AbstractModel;
import nl.knokko.texture.ModelTexture;
import nl.knokko.texture.area.TextureArea;
import nl.knokko.texture.factory.CreatureTextureFactory;
import nl.knokko.texture.marker.TextureMarkerMyrre;
import nl.knokko.texture.painter.MyrrePainter;

public class MyrreFactory {
	
	public static ModelPart createModelMyrre(BodyMyrre body, MyrrePainter colors){
		TextureMarkerMyrre tm = new TextureMarkerMyrre(body, Game.getOptions().pixelsPerMeter);
		int tw = tm.getWidth();
		int th = tm.getHeight();
		ModelTexture texture = new ModelTexture(CreatureTextureFactory.createMyrreTexture(tm, colors), 1f, 0f);
		ModelPart leftFoot = new ModelPart(createMyrreFootClaw(body, body, tm.getLeftFootClaws(), tw, th), texture, new Vector3f(0, 0, 0));
		ModelPart rightFoot = new ModelPart(createMyrreFootClaw(body, body, tm.getRightFootClaws(), tw, th), texture, new Vector3f(0, 0, 0));
		ModelPart leftLeg = createMyrreLeg(body, texture, tm.getLeftLeg(), new Vector3f(-body.bellyWidth() / 2, -body.bellyHeight() / 2, 0), leftFoot);
		ModelPart rightLeg = createMyrreLeg(body, texture, tm.getRightLeg(), new Vector3f(body.bellyWidth() / 2, -body.bellyHeight() / 2, 0), rightFoot);
		ModelPart leftHandClaw = new ModelPart(createMyrreHandClaw(body, body, tm.getLeftHandClaws(), tw, th, true), texture, new Vector3f());
		ModelPart rightHandClaw = new ModelPart(createMyrreHandClaw(body, body, tm.getRightHandClaws(), tw, th, false), texture, new Vector3f());
		ModelPart leftArm = createMyrreArm(body, texture, tm.getLeftArm(), new Vector3f(-body.bellyWidth() / 3, body.bellyHeight() / 4, 0), true, leftHandClaw);
		ModelPart rightArm = createMyrreArm(body, texture, tm.getRightArm(), new Vector3f(body.bellyWidth() / 3, body.bellyHeight() / 4, 0), false, rightHandClaw);
		return new ModelPartParent(createMyrreBelly(body, tm.getBelly(), tw, th), texture, new Vector3f(), leftArm, rightArm, leftLeg, rightLeg);
	}
	
	private static ModelPart createMyrreLeg(MyrreLeg leg, ModelTexture texture, TextureArea ta, Vector3f vector, ModelPart foot){
		int tw = ta.getWidth();
		int th = ta.getHeight();
		ModelPart lowPart = createMyrreLegPart(leg, texture, ta, tw, th, 0, foot);
		for(int i = 1; i < leg.legParts(); i++)
			lowPart = createMyrreLegPart(leg, texture, ta, tw, th, i, lowPart);
		lowPart.getRelativePosition().x = vector.x;
		lowPart.getRelativePosition().y = vector.y;
		lowPart.getRelativePosition().z = vector.z;
		return lowPart;
	}
	
	private static ModelPart createMyrreArm(MyrreArm arm, ModelTexture texture, TextureArea ta, Vector3f vector, boolean left, ModelPart hand){
		int tw = ta.getWidth();
		int th = ta.getHeight();
		ModelPart part = createMyrreHandPart(arm, texture, ta, tw, th, hand);
		for(int i = 1; i < arm.armParts(); i++)
			part = createMyrreArmPart(arm, texture, ta, tw, th, 1, part);
		part.getRelativePosition().x = vector.x;
		part.getRelativePosition().y = vector.y;
		part.getRelativePosition().z = vector.z;
		part.setPitch(BodyMyrre.Rotation.ARM_PITCH);
		part.setRoll(left ? -BodyMyrre.Rotation.ARM_ROLL : BodyMyrre.Rotation.ARM_ROLL);
		return part;
	}
	
	private static ModelPart createMyrreHandPart(MyrreArm arm, ModelTexture texture, TextureArea ta, int tw, int th, ModelPart hand){
		AbstractModel model = createMyrreArmPart(arm, ta, tw, th, 0);
		return new BodyMyrre.MyrreHandModelPart(model, texture, new Vector3f(), hand);
	}
	
	private static ModelPart createMyrreArmPart(MyrreArm arm, ModelTexture texture, TextureArea ta, int tw, int th, int index, ModelPart next){
		return new ModelPartParent(createMyrreArmPart(arm, ta, tw, th, index), texture, new Vector3f(0, -arm.armLength() * index / arm.armParts(), 0), next);
	}
	
	private static AbstractModel createMyrreBelly(MyrreBelly belly, TextureArea ta, int tw, int th){
		ModelBuilder builder = new ModelBuilder();
		builder.addSphere(10, 0, 0, 0, belly.bellyWidth() / 2, belly.bellyHeight() / 2, belly.bellyDepth() / 2, ta.getMinU(tw), ta.getMinV(th), ta.getMaxU(tw), ta.getMaxV(th));
		return builder.load();
	}
	
	private static AbstractModel createMyrreArmPart(MyrreArm arm, TextureArea ta, int tw, int th, int index){
		ModelBuilder builder = new ModelBuilder();
		float minU = ta.getMinU(tw);
		float deltaU = ta.getMaxU(tw) - minU;
		builder.addHorizontalCilinder(10, 0f, 0f, arm.armRadius(), arm.armRadius(), arm.armRadius(), arm.armRadius(), 0, -arm.armLength() / arm.armParts(), minU + deltaU * index / arm.armParts(), ta.getMinV(th), minU + (index + 1) * deltaU, ta.getMaxV(th));
		return builder.load();
	}
	
	private static AbstractModel createMyrreHandClaw(MyrreHandClaw claw, MyrreArm arm, TextureArea area, int tw, int th, boolean left){
		ModelBuilder builder = new ModelBuilder();
		float minV = area.getMinV(th);
		float maxV = area.getMaxV(th);
		float midV = (maxV + minV) / 2;
		float minU = area.getMinU(tw);
		float maxU = area.getMaxU(tw);
		builder.addPlaneTriangle(1, 0, 0, arm.armRadius(), -arm.armRadius(), 0, minU, minV, arm.armRadius(), -arm.armRadius() / 3, 0, minU, maxV, arm.armRadius(), -arm.armRadius() * 2 / 3, -claw.nailHandLength(), maxU, midV);
		builder.addPlaneTriangle(1, 0, 0, arm.armRadius(), arm.armRadius(), 0, minU, minV, arm.armRadius(), arm.armRadius() / 3, 0, minU, maxV, arm.armRadius(), arm.armRadius() * 2 / 3, -claw.nailHandLength(), maxU, midV);
		if(left)
			builder.mirrorX();
		return builder.load();
	}
	
	private static ModelPart createMyrreLegPart(MyrreLeg leg, ModelTexture texture, TextureArea area, int tw, int th, int index, ModelPart next){
		ModelBuilder builder = new ModelBuilder();
		float minU = area.getMinU(tw);
		float deltaU = area.getMaxU(tw) - minU;
		builder.addVerticalCilinder(10, 0, 0, leg.legRadius(), leg.legRadius(), -leg.legLength() / leg.legParts(), 0, minU + index * deltaU / leg.legParts(), area.getMinV(th), minU + (index + 1) * deltaU / leg.legParts(), area.getMaxV(th));
		return new ModelPartParent(builder.load(), texture, new Vector3f(0, -leg.legLength() / leg.legParts(), 0), next);
	}
	
	private static AbstractModel createMyrreFootClaw(MyrreFootClaw claw, MyrreLeg leg, TextureArea ta, int tw, int th){
		ModelBuilder builder = new ModelBuilder();
		float minU = ta.getMinU(tw);
		float minV = ta.getMinV(th);
		float maxU = ta.getMaxU(tw);
		float maxV = ta.getMaxV(th);
		float midV = (minV + maxV) / 2;
		builder.addPlaneTriangle(0, 1, 0, -leg.legRadius(), 0, -leg.legRadius(), minU, minV, -leg.legRadius() / 3, 0, -leg.legRadius(), minU, maxV, -leg.legRadius() * 2 / 3, 0, -leg.legRadius() - claw.nailFootLength(), maxU, midV);
		builder.addPlaneTriangle(0, 1, 0, -leg.legRadius() / 3, 0, -leg.legRadius(), minU, minV, leg.legRadius() / 3, 0, -leg.legRadius(), minU, maxV, 0, 0, -leg.legRadius() - claw.nailFootLength(), maxU, midV);
		builder.addPlaneTriangle(0, 1, 0, leg.legRadius() / 3, 0, -leg.legRadius(), minU, minV, leg.legRadius(), 0, -leg.legRadius(), minU, maxV, leg.legRadius() * 2 / 3, 0, -leg.legRadius() - claw.nailFootLength(), maxU, midV);
		return builder.load();
	}
}