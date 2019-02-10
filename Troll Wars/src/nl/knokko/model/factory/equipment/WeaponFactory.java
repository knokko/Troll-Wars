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

import java.util.Random;

import org.lwjgl.util.vector.Vector3f;

import nl.knokko.main.Game;
import nl.knokko.model.ModelPart;
import nl.knokko.model.body.hand.HumanoidHandProperties;
import nl.knokko.model.equipment.weapon.ModelBone;
import nl.knokko.model.equipment.weapon.ModelSpear;
import nl.knokko.model.equipment.weapon.ModelSword;
import nl.knokko.model.factory.ModelBuilder;
import nl.knokko.texture.ModelTexture;
import nl.knokko.texture.area.TextureArea;
import nl.knokko.texture.equipment.SpearTexture;
import nl.knokko.texture.equipment.SwordTexture;
import nl.knokko.texture.marker.TextureMarkerBone;
import nl.knokko.texture.marker.TextureMarkerSpear;
import nl.knokko.texture.marker.TextureMarkerSword;
import nl.knokko.texture.painter.BonePainter;
import nl.knokko.util.Maths;

import static nl.knokko.texture.factory.WeaponTextureFactory.*;

public class WeaponFactory {
	
	public static ModelPart createModelBone(ModelBone bone, BonePainter painter, HumanoidHandProperties hand, boolean left){
		ModelBuilder builder = new ModelBuilder();
		float scale = hand.handCoreLength() / Math.max(bone.boneRadiusX(), bone.boneRadiusZ()) * 0.5f;
		TextureMarkerBone marker = new TextureMarkerBone(bone, scale, Game.getOptions().pixelsPerMeter);
		int tw = marker.getWidth();
		int th = marker.getHeight();
		Random r = new Random(bone.seed());
		float z = -hand.handCoreLength() / 2 - hand.fingerLength() * 0.4f + bone.maxRandomFactor() * bone.boneRadiusZ() * scale;
		float x = -bone.boneRadiusX() * scale - hand.handWidth() * 0.55f + bone.maxRandomFactor() * bone.boneRadiusX() * scale;
		int partsR = 10;
		int partsY = 10;
		TextureArea c = marker.getCilinder();
		for(int i = 0; i <= partsY; i++){
			float y = (float)i / partsY * bone.boneLength() * scale - hand.handHeight();
			float v = c.getMinV(th) + (c.getMaxV(th) - c.getMinV(th)) * ((float) i / partsY);
			for(int j = 0; j < partsR; j++){
				float angle = (float) ((float)j / partsR * 2 * Math.PI);
				builder.addVertex(x + (Maths.cosRad(angle) * bone.boneRadiusX() * scale) * (1 - bone.maxRandomFactor() + 2 * r.nextFloat() * bone.maxRandomFactor()), y, z + (Maths.sinRad(angle) * bone.boneRadiusZ() * scale) * (1 - bone.maxRandomFactor() + 2 * r.nextFloat() * bone.maxRandomFactor()), Maths.cosRad(angle), 0, Maths.sinRad(angle), c.getMinU(tw) + ((float)j / partsR) * (c.getMaxU(tw) - c.getMinU(tw)), v);
			}
			builder.addVertex(builder.vertices.get(i * 3 * (partsR + 1)), builder.vertices.get(i * 3 * (partsR + 1) + 1), builder.vertices.get(i * 3 * (partsR + 1) + 2), 1, 0, 0, c.getMaxU(tw), v);
		}
		if(!left)
			builder.mirrorX();
		for(int i = 0; i < partsY; i++)
			for(int j = 0; j < partsR; j++)
				builder.bindFourangle(i * (partsR + 1) + j, i * (partsR + 1) + j + 1, (i + 1) * (partsR + 1) + j + 1, (i + 1) * (partsR + 1) + j);
		TextureArea s1 = marker.getSphere1();
		TextureArea s2 = marker.getSphere2();
		TextureArea s3 = marker.getSphere3();
		TextureArea s4 = marker.getSphere4();
		builder.addBoneSphere(bone, r, scale, bone.boneRadiusX() * scale + x, (bone.boneLength()) * scale - hand.handHeight(), z, s1.getMinU(tw), s1.getMinV(th), s1.getMaxU(tw), s1.getMaxV(th), partsY, partsR);
		builder.addBoneSphere(bone, r, scale, -bone.boneRadiusX() * scale + x, (bone.boneLength()) * scale - hand.handHeight(), z, s2.getMinU(tw), s2.getMinV(th), s2.getMaxU(tw), s2.getMaxV(th), partsY, partsR);
		builder.addBoneSphere(bone, r, scale, bone.boneRadiusX() * scale + x, -hand.handHeight(), z, s3.getMinU(tw), s3.getMinV(th), s3.getMaxU(tw), s3.getMaxV(th), partsY, partsR);
		builder.addBoneSphere(bone, r, scale, -bone.boneRadiusX() * scale + x, -hand.handHeight(), z, s4.getMinU(tw), s4.getMinV(th), s4.getMaxU(tw), s4.getMaxV(th), partsY, partsR);
		ModelPart model = new ModelPart(builder.load(), new ModelTexture(createBoneTexture(marker, painter), 1f, 0f), new Vector3f());
		return model;
	}
	
	public static ModelPart createModelSpear(SpearTexture texture, HumanoidHandProperties hand){
		ModelBuilder builder = new ModelBuilder();
		ModelSpear spear = texture.getSpearModel();
		float scale = hand.handCoreLength() / spear.stickRadius() / 2;
		TextureMarkerSpear marker = new TextureMarkerSpear(spear, scale, Game.getOptions().pixelsPerMeter);
		int tw = marker.getWidth();
		int th = marker.getHeight();
		float x = -spear.stickRadius() * scale - hand.handWidth() / 2;
		float y = spear.stickLength() * scale - hand.handHeight();
		float z = -spear.stickRadius() * scale - hand.fingerLength() / 2;
		TextureArea st = marker.getStick();
		builder.addVerticalCilinder(10, x, z, spear.stickRadius() * scale, spear.stickRadius() * scale, -hand.handHeight(), y, st.getMinU(tw), st.getMinV(th), st.getMaxU(tw), st.getMaxV(th));
		float r = spear.pointMaxRadius() * scale;
		float l = spear.pointLength() * scale;
		TextureArea pb = marker.getPointBottom();
		builder.addFourangle(x, y, z - r, 0, -1, 0, pb.getMinU(tw), pb.getMinV(th), x + r, y, z, 1, 0, 0, pb.getMaxU(tw), pb.getMinV(th), x, y, z + r, 0, 0, 1, pb.getMaxU(tw), pb.getMaxV(th), x - r, y, z, -1, 0, 0, pb.getMinU(tw), pb.getMaxV(th));
		TextureArea sb = marker.getStickBottom();
		float sr = spear.stickRadius() * scale;
		float minY = -hand.handHeight();
		builder.addFourangle(x - sr, minY, z - sr, 0, -1, 0, sb.getMinU(tw), sb.getMinV(th), x + sr, minY, z - sr, 0, -1, 0, sb.getMaxU(tw), sb.getMinV(th), x + sr, minY, z + sr, 0, -1, 0, sb.getMaxU(tw), sb.getMaxV(th), x - sr, minY, z + sr, 0, -1, 0, sb.getMinU(tw), sb.getMaxV(th));
		Vector3f ne = new Vector3f(r, l, -r);
		Vector3f es = new Vector3f(r, l, r);
		Vector3f sw = new Vector3f(-r, l, r);
		Vector3f wn = new Vector3f(-r, l, -r);
		ne.normalise();
		es.normalise();
		sw.normalise();
		wn.normalise();
		TextureArea nes = marker.getPointNES();
		builder.addFourangle(x, y + l, z, 1, 0, 0, nes.getMaxU(tw), nes.getMaxV(th), x, y, z - r, ne.x, ne.y, ne.z, nes.getMinU(tw), nes.getMinV(th), x + r, y, z, ne.x, ne.y, ne.z, nes.getMaxU(tw), nes.getMinV(th), x, y, z + r, es.x, es.y, es.z, nes.getMinU(tw), nes.getMaxV(th));
		TextureArea swn = marker.getPointSWN();
		builder.addFourangle(x, y + l, z, -1, 0, 0, swn.getMaxU(tw), swn.getMaxV(th), x, y, z + r, sw.x, sw.y, sw.z, swn.getMinU(tw), swn.getMinV(th), x - r, y, z, sw.x, sw.y, sw.z, swn.getMaxU(tw), swn.getMinV(th), x, y, z - r, wn.x, wn.y, wn.z, swn.getMinU(tw), swn.getMaxV(th));
		return new ModelPart(builder.load(), new ModelTexture(createSpearTexture(marker, texture.getSpearPainter()), texture.getShineDamper(), texture.getReflectivity()), new Vector3f());
	}
	
	public static ModelPart createModelSword(SwordTexture texture, HumanoidHandProperties hand){
		ModelSword sword = texture.getSwordModel();
		float scale = hand.handCoreLength() / sword.handleRadius() / 2;
		TextureMarkerSword marker = new TextureMarkerSword(sword, scale, Game.getOptions().pixelsPerMeter);
		int tw = marker.getWidth();
		int th = marker.getHeight();
		float x = -sword.handleRadius() * scale - hand.handWidth() / 2;
		float y = -hand.handHeight();
		float z = -sword.handleRadius() * scale - hand.fingerLength() / 2;
		TextureArea ah = marker.getHandle();
		ModelBuilder builder = new ModelBuilder();
		builder.addVerticalCilinder(10, x, z, sword.handleRadius() * scale, sword.handleRadius() * scale, y, y + sword.handleLength() * scale, ah, tw, th);
		float hr = sword.handleRadius() * scale;
		builder.addPlane(marker.getHandleBottom(), tw, th, 0, -1, 0, x - hr, y, z - hr, x + hr, y, z - hr, x + hr, y, z + hr, x - hr, y, z + hr);
		float mw = sword.middleWidth() * scale / 2;
		float ml = sword.middleLength() * scale / 2;
		float my = y + sword.handleLength() * scale + mw;
		builder.addPlane(marker.getMiddleFront(), tw, th, 0, 0, 1, x - ml, my - mw, z + mw, x + ml, my - mw, z + mw, x + ml, my + mw, z + mw, x - ml, my + mw, z + mw);
		builder.addPlane(marker.getMiddleBack(), tw, th, 0, 0, -1, x - ml, my - mw, z - mw, x + ml, my - mw, z - mw, x + ml, my + mw, z - mw, x - ml, my + mw, z - mw);
		builder.addPlane(marker.getMiddleTop(), tw, th, 0, 1, 0, x - ml, my + mw, z - mw, x + ml, my + mw, z - mw, x + ml, my + mw, z + mw, x - ml, my + mw, z + mw);
		builder.addPlane(marker.getMiddleBottom(), tw, th, 0, -1, 0, x - ml, my - mw, z - mw, x + ml, my - mw, z - mw, x + ml, my - mw, z + mw, x - ml, my - mw, z + mw);
		builder.addPlane(marker.getMiddleLeft(), tw, th, -1, 0, 0, x - ml, my - mw, z - mw, x - ml, my + mw, z - mw, x - ml, my + mw, z + mw, x - ml, my - mw, z + mw);
		builder.addPlane(marker.getMiddleRight(), tw, th, 1, 0, 0, x + ml, my - mw, z - mw, x + ml, my + mw, z - mw, x + ml, my + mw, z + mw, x + ml, my - mw, z + mw);
		//TODO the blade texture coords might behave a bit strangely...
		float bty = y + (sword.handleLength() + sword.middleWidth() + sword.bladeLength()) * scale;
		float by = y + (sword.handleLength() + sword.middleWidth()) * scale;
		float bw = sword.bladeWidth() * scale / 2;
		float bd = sword.bladeDepth() * scale / 2;
		builder.addFourangle(marker.getBladeFront(), tw, th, x - bw, by, z, -1, 0, 0, x, by, z + bd, 0, 0, 1, x + bw, by, z, 1, 0, 0, x, bty, z, 0, 1, 0);
		builder.addFourangle(marker.getBladeBack(), tw, th, x - bw, by, z, -1, 0, 0, x, by, z - bd, 0, 0, -1, x + bw, by, z, 1, 0, 0, x, bty, z, 0, 1, 0);
		return new ModelPart(builder.load(), new ModelTexture(createSwordTexture(marker, texture.getSwordPainter()), texture.getShineDamper(), texture.getReflectivity()), new Vector3f());
	}
}