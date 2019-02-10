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
import nl.knokko.model.body.BodyTroll;
import nl.knokko.model.body.head.TrollHeadProperties;
import nl.knokko.model.factory.ModelBuilder;
import nl.knokko.model.type.AbstractModel;
import nl.knokko.texture.ModelTexture;
import nl.knokko.texture.area.TextureArea;
import nl.knokko.texture.factory.CreatureTextureFactory;
import nl.knokko.texture.marker.TextureMarkerTroll;
import nl.knokko.texture.painter.TrollPainter;

import static nl.knokko.model.factory.creature.HumanoidFactory.*;
import static nl.knokko.model.factory.creature.CreatureFactory.*;

public class TrollFactory {
	
	public static ModelPart createModelTroll(BodyTroll body, TrollPainter colors){
		TextureMarkerTroll tt = new TextureMarkerTroll(body);
		int tw = tt.getWidth();
		int th = tt.getHeight();
		ModelTexture texture = new ModelTexture(CreatureTextureFactory.createTrollTexture(tt, colors), 1.0f, 0.0f);
		float s = 1f;
		ModelPart leftFoot = new ModelPartParent(createHumanoidFoot(body, body, tt.getLeftFoot(), tw, th), texture, new Vector3f(0, -s * (body.upperLegLength() + body.footMidHeight()), 0), 0, 180, 0);
		ModelPart rightFoot = new ModelPartParent(createHumanoidFoot(body, body, tt.getRightFoot(), tw, th), texture, new Vector3f(0, -s * (body.upperLegLength() + body.footMidHeight()), 0), 0, 180, 0);
		ModelPartParent leftUnderLeg = new ModelPartParent(createCreatureUnderLeg(body, tt.getLeftUnderLeg(), tt.getLeftKnee(), tw, th), texture, new Vector3f(0f, -s * body.upperLegLength(), 0f), leftFoot);
		ModelPartParent rightUnderLeg = new ModelPartParent(createCreatureUnderLeg(body, tt.getRightUnderLeg(), tt.getRightKnee(), tw, th), texture, new Vector3f(0f, -s * body.upperLegLength(), 0f), rightFoot);
		ModelPartParent leftLeg = new ModelPartParent(createCreatureUpperLeg(body, tt.getLeftUpperLeg(), tw, th), texture, new Vector3f(s * -body.bellyWidth() / 2 + s * body.legUpperRadius(), -s * body.bellyHeight() / 2, 0f), leftUnderLeg);
		ModelPartParent rightLeg = new ModelPartParent(createCreatureUpperLeg(body, tt.getRightUpperLeg(), tw, th), texture, new Vector3f(s * body.bellyWidth() / 2 - s * body.legUpperRadius(), -s * body.bellyHeight() / 2, 0f), rightUnderLeg);
		ModelPart leftHand = new ModelPartParent(createHumanoidHand(body, tt.getLeftHand(), tw, th, true), texture, new Vector3f(0, 0, -s * body.underArmLength()));
		ModelPartParent leftUnderArm = new ModelPartParent(createCreatureUnderArm(body, tt.getLeftUnderArm(), tw, th), texture, new Vector3f(0, 0, -s * body.upperArmLength()), 0, 0, 0, leftHand);
		ModelPartParent leftArm = new ModelPartParent(createCreatureUpperArm(body, tt.getLeftUpperArm(), tt.getLeftShoulder(), tw, th), texture, new Vector3f(s * -body.bellyWidth() / 2, s * body.bellyHeight() / 2, 0f), BodyTroll.Rotation.ARM_PITCH, 0, -BodyTroll.Rotation.ARM_ROLL, leftUnderArm);
		ModelPart rightHand = new ModelPartParent(createHumanoidHand(body, tt.getRightHand(), tw, th, false), texture, new Vector3f(0, 0, -s * body.underArmLength()));
		ModelPartParent rightUnderArm = new ModelPartParent(createCreatureUnderArm(body, tt.getRightUnderArm(), tw, th), texture, new Vector3f(0, 0, -s * body.upperArmLength()), 0, 0, 0, rightHand);
		ModelPartParent rightArm = new ModelPartParent(createCreatureUpperArm(body, tt.getRightUpperArm(), tt.getRightShoulder(), tw, th), texture, new Vector3f(s * body.bellyWidth() / 2, s * body.bellyHeight() / 2, 0f), BodyTroll.Rotation.ARM_PITCH, 0, BodyTroll.Rotation.ARM_ROLL, rightUnderArm);
		ModelPart head = new ModelPartParent(createTrollHead(body, tt.getHead(), tt.getNose(), tw, th), texture, new Vector3f(0, s * body.bellyHeight() / 2 + s * body.bellyDepth() / 4, 0));
		ModelPartParent belly = new ModelPartParent(createCreatureBelly(body, tt.getBelly(), tt.getBellyTop(), tw, th), texture, new Vector3f(0f, s * (body.footMidHeight() + body.underLegLength() + body.upperLegLength() + body.bellyHeight() / 2), 0f), 0, 0, 0, head, leftArm, rightArm, leftLeg, rightLeg);
		return belly;
	}
	
	private static AbstractModel createTrollHead(TrollHeadProperties head, TextureArea area, TextureArea areaNose, int textureWidth, int textureHeight){
		ModelBuilder builder = new ModelBuilder();
		builder.addSphere(20, 0, head.headHeight() / 2, 0, head.headWidth() / 2, head.headHeight() / 2, head.headDepth() / 2, area.getMinU(textureWidth), area.getMinV(textureHeight), area.getMaxU(textureWidth), area.getMaxV(textureHeight));
		int indexBottomNose = builder.vertexCount();
		float midU = (areaNose.getMinU(textureWidth) + areaNose.getMaxU(textureWidth)) / 2;
		float lowV = (areaNose.getMinV(textureHeight) + areaNose.getMaxV(textureHeight)) / 2;
		builder.addVertex(-head.noseWidth() / 2, head.headHeight() / 3, -head.headDepth() / 2, 0, 0, 1, areaNose.getMinU(textureWidth), lowV);
		builder.addVertex(0, head.headHeight() / 3, -head.headDepth() / 2 - head.noseLengthFront() - head.noseLengthBack(), 0, 0, 1, midU, areaNose.getMinV(textureHeight));
		builder.addVertex(head.noseWidth() / 2, head.headHeight() / 3, -head.headDepth() / 2, 0, 0, 1, areaNose.getMaxU(textureWidth), lowV);
		int indexTopNose = builder.vertexCount();
		Vector3f v = new Vector3f(0, 0.3f, 0.6f);
		v.normalise();
		builder.addVertex(0, head.headHeight() / 3 + head.noseHeight(), -head.headDepth() / 2 - head.noseLengthBack(), v.x, v.y, v.z, midU, lowV);
		builder.addVertex(0, head.headHeight() / 3 + head.noseHeight(), -head.headDepth() / 2, 0, 0, 1, midU, areaNose.getMaxV(textureHeight));
		builder.bindTriangle(indexBottomNose, indexBottomNose + 1, indexTopNose);
		builder.bindTriangle(indexBottomNose + 1, indexBottomNose + 2, indexTopNose);
		builder.bindTriangle(indexBottomNose, indexTopNose, indexTopNose + 1);
		builder.bindTriangle(indexBottomNose + 2, indexTopNose, indexTopNose + 1);
		builder.bindFourangle(indexBottomNose, indexBottomNose + 1, indexBottomNose + 2, indexTopNose + 1);
		return builder.load();
	}
}