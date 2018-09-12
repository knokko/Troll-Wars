package nl.knokko.items;

import nl.knokko.battle.element.ElementalStatistics;
import nl.knokko.inventory.InventoryType;
import nl.knokko.model.ModelPart;
import nl.knokko.model.body.foot.HumanoidFootProperties;
import nl.knokko.model.body.leg.HumanoidLeg;
import nl.knokko.model.equipment.boots.ModelArmorFoot;
import nl.knokko.texture.equipment.ArmorTexture;
import nl.knokko.util.resources.Resources;

public class ItemShoe extends ItemArmor {
	
	protected ArmorTexture armorTexture;

	public ItemShoe(String name, ElementalStatistics elementStats, int armor, int resistance, ArmorTexture texture) {
		super(name, elementStats, armor, resistance);
		armorTexture = texture;
	}

	public ItemShoe(String name, int armor, int resistance, ArmorTexture texture) {
		super(name, armor, resistance);
		armorTexture = texture;
	}

	@Override
	public final InventoryType getType() {
		return InventoryType.BOOTS;
	}
	
	public ModelPart createModelLeft(HumanoidFootProperties foot, HumanoidLeg leg){
		return Resources.createModelArmorFoot(ModelArmorFoot.Factory.createInstance(1.1f), foot, leg, armorTexture, false);
	}
	
	public ModelPart createModelRight(HumanoidFootProperties foot, HumanoidLeg leg){
		return Resources.createModelArmorFoot(ModelArmorFoot.Factory.createInstance(1.1f), foot, leg, armorTexture, true);
	}
}
