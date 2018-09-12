package nl.knokko.texture.equipment;

import nl.knokko.model.equipment.weapon.ModelSpear;
import nl.knokko.texture.painter.SpearPainter;

public interface SpearTexture {
	
	float getShineDamper();
	
	float getReflectivity();
	
	ModelSpear getSpearModel();
	
	SpearPainter getSpearPainter();
}