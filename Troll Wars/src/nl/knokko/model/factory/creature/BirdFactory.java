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

import nl.knokko.model.ModelPart;
import nl.knokko.model.ModelPartParent;
import nl.knokko.model.body.BodyBird;
import nl.knokko.model.body.belly.HumanoidBelly;
import nl.knokko.model.body.claw.BirdClaw;
import nl.knokko.model.body.head.BirdHead;
import nl.knokko.model.body.wing.BirdWing;
import nl.knokko.model.factory.ModelBuilder;
import nl.knokko.model.type.AbstractModel;
import nl.knokko.texture.ModelTexture;
import nl.knokko.texture.area.TextureArea;
import nl.knokko.texture.factory.CreatureTextureFactory;
import nl.knokko.texture.marker.TextureMarkerBird;
import nl.knokko.texture.painter.BirdPainter;
import nl.knokko.util.Maths;

public class BirdFactory {
	
	public static ModelPart createModelBird(BodyBird body, BirdPainter painter){
		TextureMarkerBird tb = new TextureMarkerBird(body);
		int tw = tb.getWidth();
		int th = tb.getHeight();
		ModelTexture texture = new ModelTexture(CreatureTextureFactory.createBirdTexture(tb, painter), 1.0f, 0.0f);
		ModelPart leftWing = new ModelPart(createBirdWing(body, body, tb.getLeftWing(), tw, th), texture, new Vector3f(-body.bellyWidth() / 3f, body.bellyHeight() / 2, 0), 90, 180, 0);
		ModelPart rightWing = new ModelPart(createBirdWing(body, body, tb.getRightWing(), tw, th), texture, new Vector3f(body.bellyWidth() / 3f, body.bellyHeight() / 2, 0), 90, 0, 0);
		ModelPart leftLeg = new ModelPart(createBirdLeg(body, tb.getLeftLeg(), tw, th), texture, new Vector3f(-body.bellyWidth() / 3f, -body.bellyHeight() / 3f, 0f), 0, 0, 330);
		ModelPart rightLeg = new ModelPart(createBirdLeg(body, tb.getRightLeg(), tw, th), texture, new Vector3f(body.bellyWidth() / 3f, -body.bellyHeight() / 3f, 0f), 0, 0, 30);
		ModelPart head = new ModelPart(createBirdHead(body, tb.getHead(), tb.getSnail(), tw, th), texture, new Vector3f(0, body.bellyHeight() / 2, 0), 30, 0, 0);
		ModelPartParent model = new ModelPartParent(CreatureFactory.createCreatureSphere(body, tb.getBelly(), tw, th), texture, new Vector3f(0, body.legLength() + body.bellyHeight(), 0), 310, 0, 0, head, leftWing, rightWing, leftLeg, rightLeg);
		return model;
	}
	
	public static AbstractModel createBirdHead(BirdHead head, TextureArea areaHead, TextureArea areaSnail, int textureWidth, int textureHeight){
		ModelBuilder builder = new ModelBuilder();
		builder.addSphere(20, 0, head.headHeight() / 2, 0, head.headWidth() / 2, head.headHeight() / 2, head.headDepth() / 2, areaHead.getMinU(textureWidth), areaHead.getMinV(textureHeight), areaHead.getMaxU(textureWidth), areaHead.getMaxV(textureHeight));
		builder.addHorizontalCilinder(10, 0, head.headHeight() / 2, head.snailRadius(), head.snailRadius(), 0, 0, 0, -head.snailLength(), areaSnail.getMinU(textureWidth), areaSnail.getMinV(textureHeight), areaSnail.getMaxU(textureWidth), areaSnail.getMaxV(textureHeight));
		return builder.load();
	}
	
	public static AbstractModel createBirdWing(HumanoidBelly belly, BirdWing wing, TextureArea area, int textureWidth, int textureHeight){
		ModelBuilder builder = new ModelBuilder();
		float mv = area.getMinV(textureHeight);
		float dv = area.getMaxV(textureHeight) - mv;
		int i = 0;
		float[] parts = wing.wingPartLengths();
		for(float length : parts){
			builder.addBox(0, 0, i * wing.wingPartHeight(), length * wing.wingPartMaxLength(), wing.wingPartDepth(), (i + 1) * wing.wingPartHeight(), area.getMinU(textureWidth), mv + i * dv / parts.length, area.getMaxU(textureWidth), mv + (i + 1) * dv / parts.length);
			i++;
		}
		return builder.load();
	}
	
	public static AbstractModel createBirdLeg(BirdClaw leg, TextureArea area, int textureWidth, int textureHeight){
		ModelBuilder builder = new ModelBuilder();
		float minU = area.getMinU(textureWidth);
		float minV = area.getMinV(textureHeight);
		float maxU = area.getMaxU(textureWidth);
		float maxV = area.getMaxV(textureHeight);
		builder.addVerticalCilinder(10, 0f, 0f, leg.legRadius(), leg.legRadius(), leg.legRadius(), leg.legRadius(), 0, -leg.legLength(), minU, minV, maxU, maxV);
		int[][] indices = new int[leg.nailAmount()][12];
		float deltaAngle = leg.nailSpreadAngle() / leg.nailAmount();
		for(int i = 0; i < leg.nailAmount(); i++){
			float angleXZ = -leg.nailSpreadAngle() / 2 + i * deltaAngle;
			for(int j = 0; j <= 10; j++){
				float partY = (float)j / 10;
				float angle2 = partY * 360;
				indices[i][j] = builder.addVertex(leg.nailRadius() * Maths.cos(angle2) * Maths.cos(angleXZ), -leg.legLength() + Maths.sin(angle2) * leg.nailRadius(), leg.nailRadius() * Maths.cos(angle2) * Maths.sin(angleXZ), Maths.cos(angle2) * Maths.cos(angleXZ), Maths.sin(angle2), Maths.cos(angle2) * Maths.sin(angleXZ), minU, minV);//TODO fix the uv coords
			}
			indices[i][11] = builder.addVertex((leg.legRadius() + leg.nailLength()) * Maths.sin(angleXZ), -leg.legLength(), -(leg.legRadius() + leg.nailLength()) * Maths.cos(angleXZ), Maths.sin(angleXZ), 0, -Maths.cos(angleXZ), maxU, maxV);
		}
		for(int i = 0; i < leg.nailAmount(); i++)
			for(int j = 0; j < 10; j++)
				builder.bindTriangle(indices[i][j], indices[i][j + 1], indices[i][11]);
		return builder.load();
	}
}