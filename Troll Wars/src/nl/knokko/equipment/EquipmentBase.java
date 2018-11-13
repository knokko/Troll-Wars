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

import nl.knokko.battle.element.ElementStatsMultiSum;
import nl.knokko.battle.element.ElementalStatistics;
import nl.knokko.items.Item;
import nl.knokko.items.ItemArmor;
import nl.knokko.items.ItemShoe;
import nl.knokko.items.ItemChestplate;
import nl.knokko.items.ItemGlobe;
import nl.knokko.items.ItemHelmet;
import nl.knokko.items.ItemPants;
import nl.knokko.items.ItemWeapon;
import nl.knokko.items.Items;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;

public abstract class EquipmentBase implements Equipment {
	
	protected ItemHelmet helmet;
	protected ItemChestplate chestplate;
	protected ItemGlobe leftGlobe;
	protected ItemGlobe rightGlobe;
	protected ItemWeapon leftWeapon;
	protected ItemWeapon rightWeapon;
	protected ItemPants pants;
	protected ItemShoe leftShoe;
	protected ItemShoe rightShoe;
	
	protected ElementStatsMultiSum eStats;

	public EquipmentBase() {
		eStats = new ElementStatsMultiSum(null, null, null, null, null, null, null, null, null);
	}
	
	public EquipmentBase(BitInput buffer){
		this();
		load(buffer);
	}
	
	private int getArmor(ItemArmor armor){
		if(armor != null)
			return armor.getArmor();
		return 0;
	}
	
	private int getResistance(ItemArmor armor){
		if(armor != null)
			return armor.getResistance();
		return 0;
	}
	
	@Override
	public int getArmor(){
		int armor = 0;
		armor += getArmor(helmet);
		armor += getArmor(chestplate);
		armor += getArmor(leftGlobe);
		armor += getArmor(rightGlobe);
		armor += getArmor(pants);
		armor += getArmor(leftShoe);
		armor += getArmor(rightShoe);
		return armor;
	}
	
	@Override
	public int getResistance(){
		int resist = 0;
		resist += getResistance(helmet);
		resist += getResistance(chestplate);
		resist += getResistance(leftGlobe);
		resist += getResistance(rightGlobe);
		resist += getResistance(pants);
		resist += getResistance(leftShoe);
		resist += getResistance(rightShoe);
		return resist;
	}
	
	@Override
	public BitOutput save(BitOutput buffer){
		buffer.addShort(Items.getID(helmet));
		buffer.addShort(Items.getID(chestplate));
		buffer.addShort(Items.getID(leftGlobe));
		buffer.addShort(Items.getID(rightGlobe));
		buffer.addShort(Items.getID(leftWeapon));
		buffer.addShort(Items.getID(rightWeapon));
		buffer.addShort(Items.getID(pants));
		buffer.addShort(Items.getID(leftShoe));
		buffer.addShort(Items.getID(rightShoe));
		return buffer;
	}
	
	@Override
	public void load(BitInput buffer){
		equipHelmet(Items.fromID(buffer.readShort()));
		equipChestplate(Items.fromID(buffer.readShort()));
		equipLeftGlobe(Items.fromID(buffer.readShort()));
		equipRightGlobe(Items.fromID(buffer.readShort()));
		equipLeftWeapon(Items.fromID(buffer.readShort()));
		equipRightWeapon(Items.fromID(buffer.readShort()));
		equipPants(Items.fromID(buffer.readShort()));
		equipLeftShoe(Items.fromID(buffer.readShort()));
		equipRightShoe(Items.fromID(buffer.readShort()));
	}
	
	@Override
	public void equipLeftShoe(Item shoe){
		if(!canEquipLeftShoe() || !canEquipLeftShoe(shoe)) throw new IllegalArgumentException("Can't equip " + shoe + " on " + this);
		this.leftShoe = (ItemShoe) shoe;
		if(shoe != null)
			eStats.set(this.leftShoe.getElementStats(), 7);
		else
			eStats.set(null, 7);
	}
	
	@Override
	public void equipRightShoe(Item shoe){
		if(!canEquipRightShoe() || !canEquipRightShoe(shoe)) throw new IllegalArgumentException("Can't equip " + shoe + " on " + this);
		this.rightShoe = (ItemShoe) shoe;
		if(shoe != null)
			eStats.set(this.rightShoe.getElementStats(), 8);
		else
			eStats.set(null, 7);
	}
	
	@Override
	public void equipPants(Item pants){
		if(!canEquipPants() || !canEquipPants(pants)) throw new IllegalArgumentException("Can't equip " + pants + " on " + this);
		this.pants = (ItemPants) pants;
		if(pants != null)
			eStats.set(this.pants.getElementStats(), 6);
		else
			eStats.set(null, 6);
	}
	
	@Override
	public void equipChestplate(Item plate){
		if(!canEquipChestplate() || !canEquipChestplate(plate)) throw new IllegalArgumentException("Can't equip " + plate + " on " + this);
		this.chestplate = (ItemChestplate) plate;
		if(plate != null)
			eStats.set(this.chestplate.getElementStats(), 1);
		else
			eStats.set(null, 1);
	}
	
	@Override
	public void equipLeftGlobe(Item globe){
		if(!canEquipLeftGlobe() || !canEquipLeftGlobe(globe)) throw new IllegalArgumentException("Can't equip " + globe + " on " + this);
		this.leftGlobe = (ItemGlobe) globe;
		if(globe != null)
			eStats.set(this.leftGlobe.getElementStats(), 2);
		else
			eStats.set(null, 2);
	}
	
	@Override
	public void equipRightGlobe(Item globe){
		if(!canEquipRightGlobe() || !canEquipRightGlobe(globe)) throw new IllegalArgumentException("Can't equip " + globe + " on " + this);
		this.rightGlobe = (ItemGlobe) globe;
		if(globe != null)
			eStats.set(this.rightGlobe.getElementStats(), 3);
		else
			eStats.set(null, 3);
	}
	
	@Override
	public void equipLeftWeapon(Item weapon){
		if(!canEquipLeftWeapon() || !canEquipLeftWeapon(weapon)) throw new IllegalArgumentException("Can't equip " + weapon + " on " + this);
		this.leftWeapon = (ItemWeapon) weapon;
		if(weapon != null)
			eStats.set(this.leftWeapon.getElementStats(), 4);
		else
			eStats.set(null, 4);
	}
	
	@Override
	public void equipRightWeapon(Item weapon){
		if(!canEquipRightWeapon() || !canEquipRightWeapon(weapon)) throw new IllegalArgumentException("Can't equip " + weapon + " on " + this);
		this.rightWeapon = (ItemWeapon) weapon;
		if(weapon != null)
			eStats.set(this.rightWeapon.getElementStats(), 5);
		else
			eStats.set(null, 5);
	}
	
	@Override
	public void equipHelmet(Item helmet){
		if(!canEquipHelmet() || !canEquipHelmet(helmet)) throw new IllegalArgumentException("Can't equip " + helmet + " on " + this);
		this.helmet = (ItemHelmet) helmet;
		if(helmet != null)
			eStats.set(this.helmet.getElementStats(), 0);
		else
			eStats.set(null, 0);
	}
	
	@Override
	public ItemShoe getLeftShoe(){
		return leftShoe;
	}
	
	@Override
	public ItemShoe getRightShoe(){
		return rightShoe;
	}
	
	@Override
	public ItemPants getPants(){
		return pants;
	}
	
	@Override
	public ItemChestplate getChestplate(){
		return chestplate;
	}
	
	@Override
	public ItemGlobe getLeftGlobe(){
		return leftGlobe;
	}
	
	@Override
	public ItemGlobe getRightGlobe(){
		return rightGlobe;
	}
	
	@Override
	public ItemWeapon getLeftWeapon(){
		return leftWeapon;
	}
	
	@Override
	public ItemWeapon getRightWeapon(){
		return rightWeapon;
	}
	
	@Override
	public ItemHelmet getHelmet(){
		return helmet;
	}
	
	@Override
	public boolean canEquipLeftShoe(Item shoe) {
		return true;
	}
	
	@Override
	public boolean canEquipRightShoe(Item shoe) {
		return true;
	}
	
	@Override
	public boolean canEquipPants(Item pants) {
		return true;
	}
	
	@Override
	public boolean canEquipChestplate(Item chestplate) {
		return true;
	}
	
	@Override
	public boolean canEquipLeftGlobe(Item globe) {
		return true;
	}
	
	@Override
	public boolean canEquipRightGlobe(Item globe) {
		return true;
	}
	
	@Override
	public boolean canEquipLeftWeapon(Item weapon) {
		return true;
	}
	
	@Override
	public boolean canEquipRightWeapon(Item weapon) {
		return true;
	}
	
	@Override
	public boolean canEquipHelmet(Item helmet) {
		return true;
	}
	
	@Override
	public ElementalStatistics getElementStats() {
		return eStats;
	}
}