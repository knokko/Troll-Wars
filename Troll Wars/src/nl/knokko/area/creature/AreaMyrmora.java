package nl.knokko.area.creature;

import nl.knokko.animation.body.AnimatorMyrre;
import nl.knokko.animation.body.BodyAnimator;
import nl.knokko.model.body.BodyMyrre;
import nl.knokko.util.position.SpawnPosition;
import nl.knokko.util.resources.Resources;

public class AreaMyrmora {

	public static class Intro extends AreaCreature {

		public Intro(SpawnPosition spawn) {
			super(spawn);
		}

		@Override
		protected void updateCreature() {}//just needs to stand, nothing more

		@Override
		protected void createBody() {
			addModel(Resources.createModelMyrre(BodyMyrre.Models.MYRMORA, BodyMyrre.Textures.MYRMORA));
		}

		@Override
		protected BodyAnimator createAnimator() {
			return new AnimatorMyrre(this);
		}

		@Override
		public void interact() {}//the player can't interact
	}
}