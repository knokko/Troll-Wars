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

import nl.knokko.model.body.arm.HumanoidArm;
import nl.knokko.model.body.belly.HumanoidBelly;
import nl.knokko.model.body.head.HeadProperties;
import nl.knokko.model.body.leg.HumanoidLeg;
import nl.knokko.model.factory.ModelBuilder;
import nl.knokko.model.type.AbstractModel;
import nl.knokko.texture.area.TextureArea;

public class CreatureFactory {
	
	public static AbstractModel createCreatureHead(HeadProperties head, TextureArea area, int textureWidth, int textureHeight){
		ModelBuilder builder = new ModelBuilder();
		builder.addSphere(20, 0, head.headHeight() / 2, 0, head.headWidth() / 2, head.headHeight() / 2, head.headDepth() / 2, area.getMinU(textureWidth), area.getMinV(textureHeight), area.getMaxU(textureWidth), area.getMaxV(textureHeight));
		return builder.load();
	}
	
	public static AbstractModel createCreatureBelly(HumanoidBelly belly, TextureArea areaBelly, TextureArea areaTop, int textureWidth, int textureHeight){
		ModelBuilder builder = new ModelBuilder();
		//addSmoothTopsCilinder(builder, 10, 0, 0, belly.bellyWidth() / 2, belly.bellyDepth() / 2, -belly.bellyHeight() * 0.5f, -belly.bellyHeight() * 0.5f + belly.shoulderRadius(), belly.bellyHeight() * 0.5f, belly.bellyHeight() * 0.5f - belly.shoulderRadius(), 0, 0, 1, 1);
		builder.addSphereTop(10, 0, belly.bellyHeight() / 2, 0, belly.bellyWidth() / 2, belly.bellyDepth() / 2, belly.bellyDepth() / 2, areaTop.getMinU(textureWidth), areaTop.getMinV(textureHeight), areaTop.getMaxU(textureWidth), areaTop.getMaxV(textureHeight));
		builder.addVerticalCilinder(10, 0, 0, belly.bellyWidth() / 2, belly.bellyDepth() / 2, -belly.bellyHeight() * 0.5f, belly.bellyHeight() * 0.5f, areaBelly.getMinU(textureWidth), areaBelly.getMinV(textureHeight), areaBelly.getMaxU(textureWidth), areaBelly.getMaxV(textureHeight));
		return builder.load();
	}
	
	public static AbstractModel createCreatureSphere(HumanoidBelly belly, TextureArea areaBelly, int textureWidth, int textureHeight){
		ModelBuilder builder = new ModelBuilder();
		builder.addSphere(10, 0, 0, 0, belly.bellyWidth() / 2, belly.bellyHeight() / 2, belly.bellyDepth() / 2, areaBelly.getMinU(textureWidth), areaBelly.getMinV(textureHeight), areaBelly.getMaxU(textureWidth), areaBelly.getMaxV(textureHeight));
		return builder.load();
	}
	
	public static AbstractModel createCreatureUpperArm(HumanoidArm arm, TextureArea areaArm, TextureArea areaShoulder, int textureWidth, int textureHeight){
		ModelBuilder builder = new ModelBuilder();
		builder.addSphereSouth(10, 0, 0, 0, arm.shoulderRadius(), arm.shoulderRadius(), arm.shoulderRadius(), areaShoulder.getMinU(textureWidth), areaShoulder.getMinV(textureHeight), areaShoulder.getMaxU(textureWidth), areaShoulder.getMaxV(textureHeight));
		builder.addHorizontalCilinder(10, 0f, 0f, arm.shoulderRadius(), arm.shoulderRadius(), arm.elbowRadius(), arm.elbowRadius(), 0, -arm.upperArmLength(), areaArm.getMinU(textureWidth), areaArm.getMinV(textureHeight), areaArm.getMaxU(textureWidth), areaArm.getMaxV(textureHeight));
		return builder.load();
	}
	
	public static AbstractModel createCreatureUnderArm(HumanoidArm arm, TextureArea area, int textureWidth, int textureHeight){
		ModelBuilder builder = new ModelBuilder();
		builder.addHorizontalCilinder(10, 0f, 0f, arm.elbowRadius(), arm.elbowRadius(), arm.wristRadius(), arm.wristRadius(), 0, -arm.underArmLength(), area.getMinU(textureWidth), area.getMinV(textureHeight), area.getMaxU(textureWidth), area.getMaxV(textureHeight));
		return builder.load();
	}
	
	public static AbstractModel createCreatureUpperLeg(HumanoidLeg leg, TextureArea area, int textureWidth, int textureHeight){
		ModelBuilder builder = new ModelBuilder();
		builder.addVerticalCilinder(10, 0f, 0f, leg.legUpperRadius(), leg.legUpperRadius(), leg.kneeRadius(), leg.kneeRadius(), 0, -leg.upperLegLength(), area.getMinU(textureWidth), area.getMinV(textureHeight), area.getMaxU(textureWidth), area.getMaxV(textureHeight));
		return builder.load();
	}
	
	public static AbstractModel createCreatureUnderLeg(HumanoidLeg leg, TextureArea area, TextureArea areaKnee, int textureWidth, int textureHeight){
		ModelBuilder builder = new ModelBuilder();
		builder.addSphere(10, 0f, 0f, 0f, leg.kneeRadius(), leg.kneeRadius(), leg.kneeRadius(), areaKnee.getMinU(textureWidth), areaKnee.getMinV(textureHeight), areaKnee.getMaxU(textureWidth), areaKnee.getMaxV(textureHeight));
		builder.addVerticalCilinder(10, 0f, 0f, leg.kneeRadius(), leg.kneeRadius(), leg.legUnderRadius(), leg.legUnderRadius(), 0, -leg.underLegLength(), area.getMinU(textureWidth), area.getMinV(textureHeight), area.getMaxU(textureWidth), area.getMaxV(textureHeight));
		return builder.load();
	}
}