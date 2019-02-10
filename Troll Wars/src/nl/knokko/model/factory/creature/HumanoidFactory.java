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

import nl.knokko.model.body.foot.HumanoidFootProperties;
import nl.knokko.model.body.hand.HumanoidHandProperties;
import nl.knokko.model.body.leg.HumanoidLeg;
import nl.knokko.model.factory.ModelBuilder;
import nl.knokko.model.type.AbstractModel;
import nl.knokko.texture.area.TextureArea;
import nl.knokko.util.Maths;

public class HumanoidFactory {
	
	public static AbstractModel createHumanoidHand(HumanoidHandProperties hand, TextureArea area, int textureWidth, int textureHeight, boolean left){
		ModelBuilder builder = new ModelBuilder();
		float minU = area.getMinU(textureWidth);
		float minV = area.getMinV(textureHeight);
		float maxU = area.getMaxU(textureWidth);
		float maxV = area.getMaxV(textureHeight);
		float deltaU = maxU - minU;
		float deltaV = maxV - minV;
		float vEdge = minV + deltaV * (hand.handCoreLength() / (hand.handCoreLength() + hand.fingerLength()));
		float uLeftTop = minU;
		float handCircle = hand.handWidth() * 2 + hand.handHeight() * 2;
		float uRightTop = minU + deltaU * (hand.handWidth() / handCircle);
		float uRightBottom = minU + deltaU * 0.5f;
		float uLeftBottom = minU + deltaU * ((2 * hand.handWidth() + hand.handHeight()) / handCircle);
		Vector3f lu = (Vector3f) new Vector3f(-1, 1, 0).normalise();
		float hw = hand.handWidth() / 2;
		float hh = hand.handHeight() / 2;
		float hcl = hand.handCoreLength();
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
		float fl = hand.fingerLength() / 3;
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
		builder.addVertex( hw - sfl - sfl2 - sfl3, hh, -hcl - cfl - cfl2 - cfl3, -lu.x, lu.y, lu.z, uRightTop, vf3);//right top finger 3
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
		return builder.load();
	}
	
	public static AbstractModel createHumanoidFoot(HumanoidFootProperties foot, HumanoidLeg leg, TextureArea area, int textureWidth, int textureHeight){
		ModelBuilder builder = new ModelBuilder();
		float minV = area.getMinV(textureHeight);
		float minU = area.getMinU(textureWidth);
		float maxV = area.getMaxV(textureHeight);
		float maxU = area.getMaxU(textureWidth);
		float deltaU = maxU - minU;
		float deltaV = maxV - minV;
		float lowV = minV + 0.2f * deltaV;
		float lowVFront = minV + 0.4f * deltaV;
		float highVFront = minV + 0.6f * deltaV;
		int midParts = 5;
		int indexUpperCircle = builder.vertexCount();
		for(int i = 0; i <= midParts; i++){
			float angle = i / (float) midParts * 180f;
			builder.addVertex(Maths.cos(angle) * leg.legUnderRadius(), foot.footMidHeight(), -Maths.sin(angle) * leg.legUnderRadius(), Maths.cos(angle), 0, Maths.sin(angle), minU + (i / (float) midParts) * deltaU, minV);
		}
		int indexLowerCircle = builder.vertexCount();
		for(int i = 0; i <= midParts; i++){
			float angle = i / (float) midParts * 180f;
			builder.addVertex(Maths.cos(angle) * leg.legUnderRadius(), 0, -Maths.sin(angle) * leg.legUnderRadius(), Maths.cos(angle), 0, Maths.sin(angle), minU + (i / (float) midParts) * deltaU, lowV);
		}
		int indexLowFront = builder.vertexCount();
		builder.addVertex(-foot.footWidth() / 2, 0, 0, -1, 0, 0, minU, lowVFront);
		builder.addVertex(-foot.footWidth() / 2, 0, foot.footFrontLength(), -1, 0, 0, minU + 0.35f * deltaU, lowVFront);
		builder.addVertex(foot.footWidth() / 2, 0, foot.footFrontLength(), 1, 0, 0, minU + 0.65f * deltaU, lowVFront);
		builder.addVertex(foot.footWidth() / 2, 0, 0, 1, 0, 0, maxU, lowVFront);
		int indexHighFront = builder.vertexCount();
		builder.addVertex(-foot.footWidth() / 2, foot.footMidHeight(), 0, 0, 1, 0, maxU, highVFront);
		builder.addVertex(-foot.footWidth() / 2, foot.footOutHeight(), foot.footFrontLength(), 0, 1, 0, minU + 0.5f * deltaU, highVFront);
		builder.addVertex(foot.footWidth() / 2, foot.footOutHeight(), foot.footFrontLength(), 0, 1, 0, minU + 0.5f * deltaU, maxV);
		builder.addVertex(foot.footWidth() / 2, foot.footMidHeight(), 0, 0, 1, 0, maxU, maxV);
		builder.bindFourangle2(indexLowFront, indexLowFront + 1, indexHighFront, indexHighFront + 1);
		builder.bindFourangle2(indexLowFront + 1, indexLowFront + 2, indexHighFront + 1, indexHighFront + 2);
		builder.bindFourangle2(indexLowFront + 2, indexLowFront + 3, indexHighFront + 2, indexHighFront + 3);
		builder.bindFourangle(indexHighFront, indexHighFront + 1, indexHighFront + 2, indexHighFront + 3);
		builder.bindFourangle(indexLowFront, indexLowFront + 1, indexLowFront + 2, indexLowFront + 3);
		for(int i = 0; i < midParts; i++)
			builder.bindFourangle(indexLowerCircle + i, indexLowerCircle + i + 1, indexUpperCircle + i + 1, indexUpperCircle + i);
		return builder.load();
	}
}