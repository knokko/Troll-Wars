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
package nl.knokko.texture.factory;

import nl.knokko.texture.Texture;
import nl.knokko.texture.builder.TextureBuilder;
import nl.knokko.texture.marker.TextureMarkerBone;
import nl.knokko.texture.marker.TextureMarkerSpear;
import nl.knokko.texture.marker.TextureMarkerSword;
import nl.knokko.texture.painter.BonePainter;
import nl.knokko.texture.painter.SpearPainter;
import nl.knokko.texture.painter.SwordPainter;

public class WeaponTextureFactory {
	
	public static Texture createBoneTexture(TextureMarkerBone marker, BonePainter painter){
		TextureBuilder builder = MyTextureLoader.createTextureBuilder(marker.getWidth(), marker.getHeight(), false);
		painter.getPattern().paint(builder, marker.getCilinder());
		painter.getPattern().paint(builder, marker.getSphere1());
		painter.getPattern().paint(builder, marker.getSphere2());
		painter.getPattern().paint(builder, marker.getSphere3());
		painter.getPattern().paint(builder, marker.getSphere4());
		return new Texture(MyTextureLoader.loadTexture(builder));
	}
	
	public static Texture createSpearTexture(TextureMarkerSpear marker, SpearPainter painter){
		TextureBuilder builder = MyTextureLoader.createTextureBuilder(marker.getWidth(), marker.getHeight(), true);
		painter.getStick().paint(builder, marker.getStick());
		painter.getStickBottom().paint(builder, marker.getStickBottom());
		painter.getPoint().paint(builder, marker.getPointBottom());
		painter.getPoint().paint(builder, marker.getPointNES());
		painter.getPoint().paint(builder, marker.getPointSWN());
		return new Texture(MyTextureLoader.loadTexture(builder));
	}
	
	public static Texture createSwordTexture(TextureMarkerSword marker, SwordPainter painter){
		TextureBuilder builder = MyTextureLoader.createTextureBuilder(marker.getWidth(), marker.getHeight(), true);
		painter.getHandleBottom().paint(builder, marker.getHandleBottom());
		painter.getHandle().paint(builder, marker.getHandle());
		painter.getMiddle().paint(builder, marker.getMiddleFront());
		painter.getMiddle().paint(builder, marker.getMiddleBack());
		painter.getMiddle().paint(builder, marker.getMiddleTop());
		painter.getMiddle().paint(builder, marker.getMiddleBottom());
		painter.getMiddle().paint(builder, marker.getMiddleLeft());
		painter.getMiddle().paint(builder, marker.getMiddleRight());
		painter.getBlade().paint(builder, marker.getBladeFront());
		painter.getBlade().paint(builder, marker.getBladeBack());
		return new Texture(MyTextureLoader.loadTexture(builder));
	}
}