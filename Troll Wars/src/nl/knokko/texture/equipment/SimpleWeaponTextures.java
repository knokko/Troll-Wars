package nl.knokko.texture.equipment;

import nl.knokko.model.equipment.weapon.ModelSpear;
import nl.knokko.model.equipment.weapon.ModelSword;
import nl.knokko.texture.painter.SpearPainter;
import nl.knokko.texture.painter.SwordPainter;
import nl.knokko.texture.pattern.PatternAverage;
import nl.knokko.texture.pattern.PatternMetal;
import nl.knokko.texture.pattern.PatternWood;
import nl.knokko.util.color.Color;

public class SimpleWeaponTextures implements WeaponTextures {
	
	protected float shineDamper;
	protected float reflectivity;
	
	protected ModelSword swordModel;
	protected SwordPainter swordPainter;
	
	protected ModelSpear spearModel;
	protected SpearPainter spearPainter;
	
	public static SimpleWeaponTextures createRockSet(Color handleColor, Color rockColor){
		return new SimpleWeaponTextures(1f, 0.2f, 
				ModelSword.Factory.createSimple(), new SwordPainter(new PatternWood(handleColor, 0.1f, 23891436), new PatternAverage(rockColor, 0.2f, 17465)),
				ModelSpear.Factory.createSimple(), new SpearPainter(new PatternWood(handleColor, 0.1f, -5476328), new PatternAverage(rockColor, 0.2f, 1115429))
		);
	}
	
	public static SimpleWeaponTextures createMetalSet(Color handleColor, Color metalColor){
		return new SimpleWeaponTextures(1f, 0.2f, 
				ModelSword.Factory.createSimple(), new SwordPainter(new PatternWood(handleColor, 0.1f, 23891436), new PatternMetal(metalColor, 17465)),
				ModelSpear.Factory.createSimple(), new SpearPainter(new PatternWood(handleColor, 0.1f, -5476328), new PatternMetal(metalColor, 1115429))
		);
	}

	public SimpleWeaponTextures(float shineDamper, float reflectivity, ModelSword swordModel, SwordPainter swordPainter,
			ModelSpear spearModel, SpearPainter spearPainter) {
		this.shineDamper = shineDamper;
		this.reflectivity = reflectivity;
		this.swordModel = swordModel;
		this.swordPainter = swordPainter;
		this.spearModel = spearModel;
		this.spearPainter = spearPainter;
	}

	@Override
	public float getShineDamper() {
		return shineDamper;
	}

	@Override
	public float getReflectivity() {
		return reflectivity;
	}

	@Override
	public ModelSword getSwordModel() {
		return swordModel;
	}

	@Override
	public SwordPainter getSwordPainter() {
		return swordPainter;
	}

	@Override
	public ModelSpear getSpearModel() {
		return spearModel;
	}

	@Override
	public SpearPainter getSpearPainter() {
		return spearPainter;
	}
}