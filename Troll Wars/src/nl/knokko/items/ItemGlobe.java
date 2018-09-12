package nl.knokko.items;

import nl.knokko.battle.element.ElementalStatistics;
import nl.knokko.inventory.InventoryType;
import nl.knokko.model.ModelPart;
import nl.knokko.model.body.hand.HumanoidHandProperties;
import nl.knokko.model.equipment.hands.ModelArmorGlobe;
import nl.knokko.texture.equipment.ArmorTexture;
import nl.knokko.util.resources.Resources;

public class ItemGlobe extends ItemArmor {
	
	protected ArmorTexture armorTexture;

	public ItemGlobe(String name, ElementalStatistics elementStats, int armor, int resistance, ArmorTexture texture) {
		super(name, elementStats, armor, resistance);
		armorTexture = texture;
	}

	public ItemGlobe(String name, int armor, int resistance, ArmorTexture texture) {
		super(name, armor, resistance);
		armorTexture = texture;
	}

	@Override
	public final InventoryType getType() {
		return InventoryType.GLOBE;
	}
	
	public ModelPart createModelLeft(HumanoidHandProperties hand){
		return Resources.createModelArmorGlobe(ModelArmorGlobe.Factory.createInstance(1.1f), hand, armorTexture, true);
	}
	
	public ModelPart createModelRight(HumanoidHandProperties hand){
		return Resources.createModelArmorGlobe(ModelArmorGlobe.Factory.createInstance(1.1f), hand, armorTexture, false);
	}
}
