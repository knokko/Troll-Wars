package nl.knokko.battle.decoration;

import nl.knokko.model.ModelPart;
import nl.knokko.model.type.DefaultModel;
import nl.knokko.util.resources.Resources;

import static nl.knokko.battle.decoration.BattleDecorations.*;

import org.lwjgl.util.vector.Vector3f;

public class DecorationSorgCave extends SimpleBattleDecoration {

	public DecorationSorgCave() {
		super(ID_SORG_CAVE, BACKGROUND_INSIDE, LIGHT_OUTSIDE);
	}

	@Override
	protected ModelPart[] createModel() {
		ModelPart[] result = new ModelPart[1];
		DefaultModel model = Resources.loadDefaultModel(new float[] {0,0,0, 100,0,0, 100,100,0, 0,100,0}, new float[] {0,0, 1,0, 1,1, 0,1}, new float[] {0,0,1, 0,0,1, 0,0,1, 0,0,1}, new int[] {0,1,2, 2,3,0});
		result[0] = new ModelPart(model, Resources.white(), new Vector3f());
		return result;
	}
}