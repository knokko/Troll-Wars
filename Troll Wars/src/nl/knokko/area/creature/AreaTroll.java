package nl.knokko.area.creature;

import nl.knokko.animation.body.AnimatorHumanoid;
import nl.knokko.animation.body.BodyAnimator;
import nl.knokko.model.ModelPart;
import nl.knokko.model.body.BodyTroll;
import nl.knokko.texture.painter.TrollPainter;
import nl.knokko.util.position.SpawnPosition;
import nl.knokko.util.resources.Resources;

public abstract class AreaTroll extends AreaCreature {
	
	public AreaTroll(SpawnPosition spawn) {
		super(spawn);
	}

	@Override
	protected void updateCreature() {}

	@Override
	protected void createBody() {
		addModel(Resources.createModelTroll(getBody(), getTexture()));
	}

	@Override
	protected BodyAnimator createAnimator() {
		ModelPart[] childs = models.get(0).getChildren();
		return new AnimatorHumanoid(childs[1], childs[2], childs[3], childs[4], childs[3].getChildren()[0], childs[4].getChildren()[0]);
	}
	
	public BodyTroll getBody(){
		return BodyTroll.Models.AREA;
	}
	
	public TrollPainter getTexture(){
		return BodyTroll.Textures.createRargia();
	}
}
