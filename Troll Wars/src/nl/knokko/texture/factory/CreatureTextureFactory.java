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
import nl.knokko.texture.marker.TextureMarkerBird;
import nl.knokko.texture.marker.TextureMarkerHuman;
import nl.knokko.texture.marker.TextureMarkerMyrre;
import nl.knokko.texture.marker.TextureMarkerTroll;
import nl.knokko.texture.painter.BirdPainter;
import nl.knokko.texture.painter.HumanPainter;
import nl.knokko.texture.painter.MyrrePainter;
import nl.knokko.texture.painter.TrollPainter;

public class CreatureTextureFactory {
	
	public static Texture createMyrreTexture(TextureMarkerMyrre tm, MyrrePainter p){
		TextureBuilder builder = MyTextureLoader.createTextureBuilder(tm.getWidth(), tm.getHeight(), true);
		p.getBelly().paint(builder, tm.getBelly());
		p.getLeftArm().paint(builder, tm.getLeftArm());
		p.getRightArm().paint(builder, tm.getRightArm());
		p.getLeftHandClaw().paint(builder, tm.getLeftHandClaws());
		p.getRightHandClaw().paint(builder, tm.getRightHandClaws());
		p.getLeftLeg().paint(builder, tm.getLeftLeg());
		p.getRightLeg().paint(builder, tm.getRightLeg());
		p.getLeftFootClaw().paint(builder, tm.getLeftFootClaws());
		p.getRightFootClaw().paint(builder, tm.getRightFootClaws());
		return new Texture(MyTextureLoader.loadTexture(builder));
	}
	
	public static Texture createTrollTexture(TextureMarkerTroll tt, TrollPainter painter){
		TextureBuilder builder = MyTextureLoader.createTextureBuilder(tt.getWidth(), tt.getHeight(), false);
		painter.getHead().paint(builder, tt.getHead());
		painter.getBelly().paint(builder, tt.getBelly());
		painter.getLeftUpperArm().paint(builder, tt.getLeftUpperArm());
		painter.getRightUpperArm().paint(builder, tt.getRightUpperArm());
		painter.getLeftUnderArm().paint(builder, tt.getLeftUnderArm());
		painter.getRightUnderArm().paint(builder, tt.getRightUnderArm());
		painter.getLeftUpperLeg().paint(builder, tt.getLeftUpperLeg());
		painter.getRightUpperLeg().paint(builder, tt.getRightUpperLeg());
		painter.getLeftKnee().paint(builder, tt.getLeftKnee());
		painter.getRightKnee().paint(builder, tt.getRightKnee());
		painter.getLeftUnderLeg().paint(builder, tt.getLeftUnderLeg());
		painter.getRightUnderLeg().paint(builder, tt.getRightUnderLeg());
		painter.getLeftFoot().paint(builder, tt.getLeftFoot());
		painter.getRightFoot().paint(builder, tt.getRightFoot());
		painter.getLeftShoulder().paint(builder, tt.getLeftShoulder());
		painter.getRightShoulder().paint(builder, tt.getRightShoulder());
		painter.getBellyTop().paint(builder, tt.getBellyTop());
		painter.getNose().paint(builder, tt.getNose());
		painter.getLeftHand().paint(builder, tt.getLeftHand());
		painter.getRightHand().paint(builder, tt.getRightHand());
		return new Texture(MyTextureLoader.loadTexture(builder));
	}
	
	public static Texture createHumanTexture(TextureMarkerHuman t, HumanPainter p){
		TextureBuilder b = MyTextureLoader.createTextureBuilder(t.getWidth(), t.getHeight(), false);
		p.getHead().paint(b, t.getHead());
		p.getBelly().paint(b, t.getBelly());
		p.getLeftUpperArm().paint(b, t.getLeftUpperArm());
		p.getRightUpperArm().paint(b, t.getRightUpperArm());
		p.getLeftUnderArm().paint(b, t.getLeftUnderArm());
		p.getRightUnderArm().paint(b, t.getRightUnderArm());
		p.getLeftUpperLeg().paint(b, t.getLeftUpperLeg());
		p.getRightUpperLeg().paint(b, t.getRightUpperLeg());
		p.getLeftKnee().paint(b, t.getLeftKnee());
		p.getRightKnee().paint(b, t.getRightKnee());
		p.getLeftUnderLeg().paint(b, t.getLeftUnderLeg());
		p.getRightUnderLeg().paint(b, t.getRightUnderLeg());
		p.getLeftFoot().paint(b, t.getLeftFoot());
		p.getRightFoot().paint(b, t.getRightFoot());
		p.getLeftShoulder().paint(b, t.getLeftShoulder());
		p.getRightShoulder().paint(b, t.getRightShoulder());
		p.getBellyTop().paint(b, t.getBellyTop());
		p.getLeftHand().paint(b, t.getLeftHand());
		p.getRightHand().paint(b, t.getRightHand());
		return new Texture(MyTextureLoader.loadTexture(b));
	}
	
	public static Texture createBirdTexture(TextureMarkerBird tb, BirdPainter painter){
		TextureBuilder builder = MyTextureLoader.createTextureBuilder(tb.getWidth(), tb.getHeight(), true);
		painter.getHead().paint(builder, tb.getHead());
		painter.getSnail().paint(builder, tb.getSnail());
		painter.getBelly().paint(builder, tb.getBelly());
		painter.getLeftWing().paint(builder, tb.getLeftWing());
		painter.getRightWing().paint(builder, tb.getRightWing());
		painter.getLeftLeg().paint(builder, tb.getLeftLeg());
		painter.getRightLeg().paint(builder, tb.getRightLeg());
		painter.getLeftClaw().paint(builder, tb.getLeftClaw());
		painter.getRightClaw().paint(builder, tb.getRightClaw());
		return new Texture(MyTextureLoader.loadTexture(builder));
	}
}