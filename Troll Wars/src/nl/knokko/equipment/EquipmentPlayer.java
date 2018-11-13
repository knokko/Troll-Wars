/*******************************************************************************
 * The MIT License
 *
 * Copyright (c) 2018 knokko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *  
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *******************************************************************************/
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

	@Override
	public boolean canEquipLeftShoe() {
		return player.canEquip(InventoryType.BOOTS);
	}

	@Override
	public boolean canEquipRightShoe() {
		return player.canEquip(InventoryType.BOOTS);
	}

	@Override
	public boolean canEquipPants() {
		return player.canEquip(InventoryType.PANTS);
	}

	@Override
	public boolean canEquipChestplate() {
		return player.canEquip(InventoryType.CHESTPLATE);
	}

	@Override
	public boolean canEquipLeftGlobe() {
		return player.canEquip(InventoryType.GLOBE);
	}

	@Override
	public boolean canEquipRightGlobe() {
		return player.canEquip(InventoryType.GLOBE);
	}

	@Override
	public boolean canEquipLeftWeapon() {
		return player.canEquip(InventoryType.WEAPON);
	}

	@Override
	public boolean canEquipRightWeapon() {
		return player.canEquip(InventoryType.WEAPON);
	}

	@Override
	public boolean canEquipHelmet() {
		return player.canEquip(InventoryType.HELMET);
	}
}