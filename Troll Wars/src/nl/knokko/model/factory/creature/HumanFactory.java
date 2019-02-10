package nl.knokko.model.factory.creature;

import org.lwjgl.util.vector.Vector3f;

import nl.knokko.model.ModelPart;
import nl.knokko.model.ModelPartParent;
import nl.knokko.model.body.BodyHuman;
import nl.knokko.texture.ModelTexture;
import nl.knokko.texture.factory.CreatureTextureFactory;
import nl.knokko.texture.marker.TextureMarkerHuman;
import nl.knokko.texture.painter.HumanPainter;

import static nl.knokko.model.factory.creature.HumanoidFactory.*;
import static nl.knokko.model.factory.creature.CreatureFactory.*;

public class HumanFactory {
	
	public static ModelPart createModelHuman(BodyHuman body, HumanPainter colors){
		TextureMarkerHuman tt = new TextureMarkerHuman(body);
		int tw = tt.getWidth();
		int th = tt.getHeight();
		ModelTexture texture = new ModelTexture(CreatureTextureFactory.createHumanTexture(tt, colors), 1.0f, 0.0f);
		float s = 1f;
		ModelPart leftFoot = new ModelPartParent(createHumanoidFoot(body, body, tt.getLeftFoot(), tw, th), texture, new Vector3f(0, -s * (body.upperLegLength() + body.footMidHeight()), 0), 0, 180, 0);
		ModelPart rightFoot = new ModelPartParent(createHumanoidFoot(body, body, tt.getRightFoot(), tw, th), texture, new Vector3f(0, -s * (body.upperLegLength() + body.footMidHeight()), 0), 0, 180, 0);
		ModelPartParent leftUnderLeg = new ModelPartParent(createCreatureUnderLeg(body, tt.getLeftUnderLeg(), tt.getLeftKnee(), tw, th), texture, new Vector3f(0f, -s * body.upperLegLength(), 0f), leftFoot);
		ModelPartParent rightUnderLeg = new ModelPartParent(createCreatureUnderLeg(body, tt.getRightUnderLeg(), tt.getRightKnee(), tw, th), texture, new Vector3f(0f, -s * body.upperLegLength(), 0f), rightFoot);
		ModelPartParent leftLeg = new ModelPartParent(createCreatureUpperLeg(body, tt.getLeftUpperLeg(), tw, th), texture, new Vector3f(s * -body.bellyWidth() / 2 + s * body.legUpperRadius(), -s * body.bellyHeight() / 2, 0f), leftUnderLeg);
		ModelPartParent rightLeg = new ModelPartParent(createCreatureUpperLeg(body, tt.getRightUpperLeg(), tw, th), texture, new Vector3f(s * body.bellyWidth() / 2 - s * body.legUpperRadius(), -s * body.bellyHeight() / 2, 0f), rightUnderLeg);
		ModelPart leftHand = new ModelPartParent(createHumanoidHand(body, tt.getLeftHand(), tw, th, true), texture, new Vector3f(0, 0, -s * body.underArmLength()));
		ModelPartParent leftUnderArm = new ModelPartParent(createCreatureUnderArm(body, tt.getLeftUnderArm(), tw, th), texture, new Vector3f(0, 0, -s * body.upperArmLength()), 0, 0, 0, leftHand);
		ModelPartParent leftArm = new ModelPartParent(createCreatureUpperArm(body, tt.getLeftUpperArm(), tt.getLeftShoulder(), tw, th), texture, new Vector3f(s * -body.bellyWidth() / 2, s * body.bellyHeight() / 2, 0f), BodyHuman.Rotation.ARM_PITCH, 0, -BodyHuman.Rotation.ARM_ROLL, leftUnderArm);
		ModelPart rightHand = new ModelPartParent(createHumanoidHand(body, tt.getRightHand(), tw, th, false), texture, new Vector3f(0, 0, -s * body.underArmLength()));
		ModelPartParent rightUnderArm = new ModelPartParent(createCreatureUnderArm(body, tt.getRightUnderArm(), tw, th), texture, new Vector3f(0, 0, -s * body.upperArmLength()), 0, 0, 0, rightHand);
		ModelPartParent rightArm = new ModelPartParent(createCreatureUpperArm(body, tt.getRightUpperArm(), tt.getRightShoulder(), tw, th), texture, new Vector3f(s * body.bellyWidth() / 2, s * body.bellyHeight() / 2, 0f), BodyHuman.Rotation.ARM_PITCH, 0, BodyHuman.Rotation.ARM_ROLL, rightUnderArm);
		ModelPart head = new ModelPartParent(createCreatureHead(body, tt.getHead(), tw, th), texture, new Vector3f(0, s * body.bellyHeight() / 2 + s * body.bellyDepth() / 4, 0));
		ModelPartParent belly = new ModelPartParent(createCreatureBelly(body, tt.getBelly(), tt.getBellyTop(), tw, th), texture, new Vector3f(0f, s * (body.footMidHeight() + body.underLegLength() + body.upperLegLength() + body.bellyHeight() / 2), 0f), 0, 0, 0, head, leftArm, rightArm, leftLeg, rightLeg);
		return belly;
	}
}