package nl.knokko.items;

import nl.knokko.battle.element.ElementalStatistics;
import nl.knokko.inventory.InventoryType;
import nl.knokko.model.ModelPart;
import nl.knokko.model.body.head.HeadProperties;
import nl.knokko.model.equipment.helmet.ModelArmorHead;
import nl.knokko.texture.equipment.ArmorTexture;
import nl.knokko.util.resources.Resources;

public class ItemHelmet extends ItemArmor {
	
	protected ArmorTexture armorTexture;
	
	public ItemHelmet(String name, ElementalStatistics elementStats, int armor, int resistance, ArmorTexture texture) {
		super(name, elementStats, armor, resistance);
		armorTexture = texture;
	}

	public ItemHelmet(String name, int armor, int resistance, ArmorTexture texture) {
		super(name, armor, resistance);
		armorTexture = texture;
	}

	@Override
	public final InventoryType getType() {
		return InventoryType.HELMET;
	}
	
	public ModelPart createModel(HeadProperties head){
		return Resources.createModelArmorHelmet(ModelArmorHead.Factory.createInstance(1.1f), head, armorTexture);
	}
}