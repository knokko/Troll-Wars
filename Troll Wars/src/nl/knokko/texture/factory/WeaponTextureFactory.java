package nl.knokko.texture.factory;

import nl.knokko.texture.Texture;
import nl.knokko.texture.marker.TextureMarkerBone;
import nl.knokko.texture.marker.TextureMarkerSpear;
import nl.knokko.texture.marker.TextureMarkerSword;
import nl.knokko.texture.painter.BonePainter;
import nl.knokko.texture.painter.SpearPainter;
import nl.knokko.texture.painter.SwordPainter;

public class WeaponTextureFactory {
	
	public static Texture createBoneTexture(TextureMarkerBone marker, BonePainter painter){
		TextureBuilder builder = new TextureBuilder(marker.getWidth(), marker.getHeight(), false);
		painter.getPattern().paint(builder, marker.getCilinder());
		painter.getPattern().paint(builder, marker.getSphere1());
		painter.getPattern().paint(builder, marker.getSphere2());
		painter.getPattern().paint(builder, marker.getSphere3());
		painter.getPattern().paint(builder, marker.getSphere4());
		return new Texture(builder.loadNormal());
	}
	
	public static Texture createSpearTexture(TextureMarkerSpear marker, SpearPainter painter){
		TextureBuilder builder = new TextureBuilder(marker.getWidth(), marker.getHeight(), true);
		painter.getStick().paint(builder, marker.getStick());
		painter.getStickBottom().paint(builder, marker.getStickBottom());
		painter.getPoint().paint(builder, marker.getPointBottom());
		painter.getPoint().paint(builder, marker.getPointNES());
		painter.getPoint().paint(builder, marker.getPointSWN());
		return new Texture(builder.loadNormal());
	}
	
	public static Texture createSwordTexture(TextureMarkerSword marker, SwordPainter painter){
		TextureBuilder builder = new TextureBuilder(marker.getWidth(), marker.getHeight(), true);
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
		return new Texture(builder.loadNormal());
	}
}