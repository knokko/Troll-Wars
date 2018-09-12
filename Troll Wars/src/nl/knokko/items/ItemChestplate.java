package nl.knokko.items;

import nl.knokko.battle.element.ElementalStatistics;
import nl.knokko.inventory.InventoryType;
import nl.knokko.model.ModelPart;
import nl.knokko.model.body.arm.HumanoidArm;
import nl.knokko.model.body.belly.HumanoidBelly;
import nl.knokko.model.equipment.chestplate.ModelArmorArm;
import nl.knokko.model.equipment.chestplate.ModelArmorBelly;
import nl.knokko.texture.equipment.ArmorTexture;
import nl.knokko.util.resources.Resources;

public class ItemChestplate extends ItemArmor {
	
	protected ArmorTexture armorTexture;
	
	public ItemChestplate(String name, ElementalStatistics elementStats, int armor, int resistance, ArmorTexture armorTexture) {
		super(name, elementStats, armor, resistance);
		this.armorTexture = armorTexture;
	}

	public ItemChestplate(String name, int armor, int resistance, ArmorTexture armorTexture) {
		super(name, armor, resistance);
		this.armorTexture = armorTexture;
	}

	@Override
	public final InventoryType getType() {
		return InventoryType.CHESTPLATE;
	}
	
	public ModelPart createModelBelly(HumanoidBelly belly){
		return Resources.createModelArmorBelly(ModelArmorBelly.Factory.createInstance(1.15f), belly, armorTexture);
	}
	
	public ModelPart createModelUpperArm(HumanoidArm arm){
		return Resources.createModelArmorUpperArm(ModelArmorArm.Factory.createInstance(1.1f, 1.3f), arm, armorTexture);
	}
	
	public ModelPart createModelUnderArm(HumanoidArm arm){
		return Resources.createModelArmorUnderArm(ModelArmorArm.Factory.createInstance(1.1f, 1.3f), arm, armorTexture);
	}
}