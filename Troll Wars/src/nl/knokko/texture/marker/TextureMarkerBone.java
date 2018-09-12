package nl.knokko.texture.marker;

import nl.knokko.model.equipment.weapon.ModelBone;
import nl.knokko.texture.area.TextureArea;

public class TextureMarkerBone extends TextureMarker {

	public TextureMarkerBone(ModelBone bone, float scale, int ppm) {
		super(
				createSphere(bone.boneRadiusTopY() * scale * 2, bone.boneRadiusTopX() * scale * 2, bone.boneRadiusTopZ() * scale * 2, ppm),
				createSphere(bone.boneRadiusTopY() * scale * 2, bone.boneRadiusTopX() * scale * 2, bone.boneRadiusTopZ() * scale * 2, ppm),
				createSphere(bone.boneRadiusTopY() * scale * 2, bone.boneRadiusTopX() * scale * 2, bone.boneRadiusTopZ() * scale * 2, ppm),
				createSphere(bone.boneRadiusTopY() * scale * 2, bone.boneRadiusTopX() * scale * 2, bone.boneRadiusTopZ() * scale * 2, ppm),
				createCilinder(bone.boneLength() * scale * 2, bone.boneRadiusX() * scale * 2, bone.boneRadiusZ() * scale * 2, ppm)
				);
	}
	
	public TextureArea getSphere1(){
		return areas[0];
	}
	
	public TextureArea getSphere2(){
		return areas[1];
	}
	
	public TextureArea getSphere3(){
		return areas[2];
	}
	
	public TextureArea getSphere4(){
		return areas[3];
	}
	
	public TextureArea getCilinder(){
		return areas[4];
	}
}
