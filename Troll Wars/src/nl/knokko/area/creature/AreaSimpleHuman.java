package nl.knokko.area.creature;

import nl.knokko.animation.body.AnimatorHumanoid;
import nl.knokko.animation.body.BodyAnimator;
import nl.knokko.equipment.EquipmentFull;
import nl.knokko.model.ModelPart;
import nl.knokko.model.body.BodyHuman;
import nl.knokko.model.body.BodyHuman.Helper;
import nl.knokko.texture.painter.HumanPainter;
import nl.knokko.util.position.SpawnPosition;
import nl.knokko.util.resources.Resources;

public class AreaSimpleHuman extends AreaCreature {
	
	private final BodyHuman body;
	private final HumanPainter colors;
	private final EquipmentFull equipment;

	public AreaSimpleHuman(SpawnPosition spawn, EquipmentFull equipment, BodyHuman body, HumanPainter colors, float rotationY) {
		super(spawn);
		this.body = body;
		this.colors = colors;
		this.equipment = equipment;
		this.rotationY = rotationY;
	}

	@Override
	protected void updateCreature() {}//lazy

	@Override
	protected void createBody() {
		addModel(Resources.createModelHuman(body, colors));
		BodyHuman.Helper.setEquipment(this, equipment, body);
	}

	@Override
	protected BodyAnimator createAnimator() {
		ModelPart model = models.get(0);
		return new AnimatorHumanoid(Helper.getLeftArm(model), Helper.getRightArm(model), Helper.getLeftLeg(model), Helper.getRightLeg(model), Helper.getLeftUnderLeg(model), Helper.getRightUnderLeg(model));
	}

	@Override
	public void interact() {}//lazy
}