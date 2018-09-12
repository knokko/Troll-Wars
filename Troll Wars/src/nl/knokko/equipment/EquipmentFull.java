package nl.knokko.equipment;

import nl.knokko.inventory.InventoryType;
import nl.knokko.items.Item;
import nl.knokko.items.Items;
import nl.knokko.util.bits.BitInput;

public class EquipmentFull extends EquipmentBase {
	
	public static EquipmentFull createFullIron(Item weapon){
		EquipmentFull equipment = new EquipmentFull();
		equipment.equipHelmet(Items.IRON_HELMET);
		equipment.equipChestplate(Items.IRON_CHESTPLATE);
		equipment.equipPants(Items.IRON_LEGGINGS);
		equipment.equipLeftShoe(Items.IRON_SHOE);
		equipment.equipRightShoe(Items.IRON_SHOE);
		equipment.equipRightWeapon(weapon);
		return equipment;
	}
	
	public static EquipmentFull createFullBlessedIron(Item weapon){
		EquipmentFull equipment = new EquipmentFull();
		equipment.equipHelmet(Items.BLESSED_IRON_HELMET);
		equipment.equipChestplate(Items.BLESSED_IRON_CHESTPLATE);
		equipment.equipPants(Items.BLESSED_IRON_LEGGINGS);
		equipment.equipLeftShoe(Items.BLESSED_IRON_SHOE);
		equipment.equipRightShoe(Items.BLESSED_IRON_SHOE);
		equipment.equipRightWeapon(weapon);
		return equipment;
	}

	public EquipmentFull() {}

	public EquipmentFull(BitInput buffer) {
		super(buffer);
	}

	@Override
	protected boolean canEquip(InventoryType type) {
		return true;
	}

	@Override
	protected boolean canEquip(Item item) {
		return true;
	}
}
