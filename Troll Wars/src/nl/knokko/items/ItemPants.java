package nl.knokko.items;

import nl.knokko.battle.element.ElementalStatistics;
import nl.knokko.inventory.InventoryType;
import nl.knokko.model.ModelPart;
import nl.knokko.model.body.leg.HumanoidLeg;
import nl.knokko.model.equipment.pants.ModelArmorLeg;
import nl.knokko.texture.equipment.ArmorTexture;
import nl.knokko.util.resources.Resources;

public class ItemPants extends ItemArmor {
	
	protected ArmorTexture armorTexture;
	
	public ItemPants(String name, ElementalStatistics elementStats, int armor, int resistance, ArmorTexture texture) {
		super(name, elementStats, armor, resistance);
		armorTexture = texture;
	}

	public ItemPants(String name, int armor, int resistance, ArmorTexture texture) {
		super(name, armor, resistance);
		armorTexture = texture;
	}

	@Override
	public final InventoryType getType() {
		return InventoryType.PANTS;
	}
	
	public ModelPart createModelUpperLeg(HumanoidLeg leg){
		return Resources.createModelArmorUpperLeg(ModelArmorLeg.Factory.createInstance(1.1f), leg, armorTexture);
	}
	
	public ModelPart createModelUnderLeg(HumanoidLeg leg){
		return Resources.createModelArmorUnderLeg(ModelArmorLeg.Factory.createInstance(1.1f), leg, armorTexture);
	}
}
