package nl.knokko.texture.equipment;

import nl.knokko.model.equipment.weapon.ModelSword;
import nl.knokko.texture.painter.SwordPainter;

public interface SwordTexture {
	
	float getShineDamper();
	
	float getReflectivity();
	
	ModelSword getSwordModel();
	
	SwordPainter getSwordPainter();
}