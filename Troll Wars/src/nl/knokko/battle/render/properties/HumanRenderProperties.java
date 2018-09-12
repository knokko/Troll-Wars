package nl.knokko.battle.render.properties;

import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.model.ModelPart;
import nl.knokko.model.body.BodyHuman;
import nl.knokko.util.Maths;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class HumanRenderProperties extends SimpleRenderProperties {
	
	protected final ModelPart leftHand;
	protected final ModelPart rightHand;

	public HumanRenderProperties(BattleCreature creature, BodyHuman body) {
		super(creature, 0, 0, 0, 
				Math.max(body.bellyWidth(), body.bellyDepth()) / 2 + (body.upperArmLength() + body.underArmLength()) * Maths.sin(BodyHuman.Rotation.ARM_ROLL) + body.shoulderRadius(), 
				body.footMidHeight() + body.underLegLength() + body.upperLegLength() + body.bellyHeight() + body.headHeight());
		ModelPart belly = creature.getModels().get(0);
		leftHand = BodyHuman.Helper.getLeftHand(belly);
		rightHand = BodyHuman.Helper.getRightHand(belly);
	}

	@Override
	public Vector3f[] getCastHands() {
		Matrix4f matrix = creature.getMatrix();
		Matrix4f left = leftHand.getMatrix(matrix);
		Matrix4f right = rightHand.getMatrix(matrix);
		return new Vector3f[]{new Vector3f(left.m20, left.m21, -left.m22), new Vector3f(right.m20, right.m21, -right.m22)};
	}
}