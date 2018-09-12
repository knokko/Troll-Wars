package nl.knokko.equipment;

import nl.knokko.inventory.InventoryType;
import nl.knokko.items.Item;
import nl.knokko.players.Player;
import nl.knokko.util.bits.BitInput;

public class EquipmentPlayer extends EquipmentBase {
	
	private final Player player;

	public EquipmentPlayer(Player player) {
		this.player = player;
	}

	public EquipmentPlayer(Player player, BitInput buffer) {
		super(buffer);
		this.player = player;
	}

	@Override
	protected boolean canEquip(InventoryType type) {
		return player.canEquip(type);
	}

	@Override
	protected boolean canEquip(Item item) {
		return player.canEquip(item);
	}
	
	@Override
	public void equipLeftShoe(Item boots){
		super.equipLeftShoe(boots);
		player.refreshLeftShoe();
	}
	
	@Override
	public void equipRightShoe(Item shoe){
		super.equipRightShoe(shoe);
		player.refreshRightShoe();
	}
	
	@Override
	public void equipPants(Item pants){
		super.equipPants(pants);
		player.refreshPants();
	}
	
	@Override
	public void equipChestplate(Item plate){
		super.equipChestplate(plate);
		player.refreshChestplate();
	}
	
	@Override
	public void equipLeftGlobe(Item globe){
		super.equipLeftGlobe(globe);
		player.refreshLeftGlobe();
	}
	
	@Override
	public void equipRightGlobe(Item globe){
		super.equipRightGlobe(globe);
		player.refreshRightGlobe();
	}
	
	@Override
	public void equipLeftWeapon(Item weapon){
		super.equipLeftWeapon(weapon);
		player.refreshLeftWeapon();
	}
	
	@Override
	public void equipRightWeapon(Item weapon){
		super.equipRightWeapon(weapon);
		player.refreshRightWeapon();
	}
	
	@Override
	public void equipHelmet(Item helmet){
		super.equipHelmet(helmet);
		player.refreshHelmet();
	}
}
