package nl.knokko.battle.creature;

import nl.knokko.items.ItemWeapon;
import nl.knokko.model.ModelPart;
import nl.knokko.model.body.hand.HumanoidHandProperties;

public interface ArmCreature {
	
	ModelPart getUpperArm();
	
	ModelPart getUnderArm();
	
	float getUpperArmLength();
	
	float getUnderArmLength();
	
	ItemWeapon getWeapon();
	
	HumanoidHandProperties getHand();
}
