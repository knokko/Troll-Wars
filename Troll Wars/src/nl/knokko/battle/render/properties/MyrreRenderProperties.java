package nl.knokko.battle.render.properties;

import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.model.ModelPart;
import nl.knokko.model.body.BodyMyrre;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class MyrreRenderProperties extends SimpleRenderProperties {
	
	protected final ModelPart leftHand;
	protected final ModelPart rightHand;

	public MyrreRenderProperties(BattleCreature creature, BodyMyrre body) {
		super(creature, 0, 0, 0, Math.max(body.bellyWidth(), body.bellyDepth()), body.legLength() + body.bellyHeight());
		leftHand = BodyMyrre.Helper.getLeftHand(creature);
		rightHand = BodyMyrre.Helper.getRightHand(creature);
	}

	@Override
	public Vector3f[] getCastHands() {
		Matrix4f matrix = creature.getMatrix();
		Matrix4f left = leftHand.getMatrix(matrix);
		Matrix4f right = rightHand.getMatrix(matrix);
		return new Vector3f[]{new Vector3f(left.m20, left.m21, -left.m22), new Vector3f(right.m20, right.m21, -right.m22)};
	}
}