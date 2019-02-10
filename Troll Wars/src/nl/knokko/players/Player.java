/*******************************************************************************
 * The MIT License
 *
 * Copyright (c) 2019 knokko
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
package nl.knokko.players;

import nl.knokko.area.creature.AreaPlayer;
import nl.knokko.battle.element.ElementalStatistics;
import nl.knokko.battle.move.FightMoveOption;
import nl.knokko.equipment.Equipment;
import nl.knokko.equipment.EquipmentPlayer;
import nl.knokko.inventory.InventoryType;
import nl.knokko.items.*;
import nl.knokko.model.ModelOwner;
import nl.knokko.players.moves.AbstractPlayerMoves;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;
import nl.knokko.util.resources.Saver;

public abstract class Player {
	
	private static final int BITS = 1 + 16 * 8 + 128 + 128 + 64 + 64 + 64 + 30;
	
	protected final AbstractPlayerMoves moves;
	protected final EquipmentPlayer equipment;
	
	protected long health;
	protected long maxHealth;
	
	protected long mana;
	protected long maxMana;
	
	protected int focus;
	protected int maxFocus;
	
	protected int energy;
	protected int maxEnergy;
	
	protected int rage;
	protected int maxRage;
	
	protected int turnSpeed;
	protected int strength;
	protected int spirit;
	
	protected boolean available;

	Player() {
		equipment = new EquipmentPlayer(this);
		moves = createMoves();
	}
	
	@Override
	public String toString(){
		return name();
	}
	
	@Override
	public int hashCode(){
		return name().hashCode();
	}
	
	public String name(){
		return getClass().getSimpleName().substring(6);
	}
	
	public Equipment getEquipment(){
		return equipment;
	}
	
	public FightMoveOption[] getMoves(){
		return moves.getLearnedMoves();
	}
	
	public boolean isAvailable(){
		return available || alwaysAvailable();
	}
	
	public void save() {
		save(Saver.save("players/" + name() + ".player", BITS));
		//Saver.save(save(new BitBuffer(BITS)), "players/" + name() + ".player");
	}
	
	public BitOutput save(BitOutput buffer){
		if(!alwaysAvailable())
			buffer.addBoolean(available);
		buffer.addLong(health);
		buffer.addLong(maxHealth);
		buffer.addLong(mana);
		buffer.addLong(maxMana);
		buffer.addInt(focus);
		buffer.addInt(maxFocus);
		buffer.addInt(energy);
		buffer.addInt(maxEnergy);
		buffer.addInt(rage);
		buffer.addInt(maxRage);
		buffer.addInt(turnSpeed);
		buffer.addInt(strength);
		buffer.addInt(spirit);
		equipment.save(buffer);
		moves.save(buffer);
		return buffer;
	}
	
	public void load() {
		load(Saver.load("players/" + name() + ".player"));
	}
	
	public void load(BitInput buffer){
		if(!alwaysAvailable())
			available = buffer.readBoolean();
		health = buffer.readLong();
		maxHealth = buffer.readLong();
		mana = buffer.readLong();
		maxMana = buffer.readLong();
		focus = buffer.readInt();
		maxFocus = buffer.readInt();
		energy = buffer.readInt();
		maxEnergy = buffer.readInt();
		rage = buffer.readInt();
		maxRage = buffer.readInt();
		turnSpeed = buffer.readInt();
		strength = buffer.readInt();
		spirit = buffer.readInt();
		equipment.load(buffer);
		refreshEquipmentModels();
		moves.load(buffer);
	}
	
	public void setAvailable(){
		available = true;
	}
	
	public void refreshEquipmentModels(ModelOwner ap){
		refreshHelmet(ap);
		refreshLeftWeapon(ap);
		refreshRightWeapon(ap);
		refreshLeftGlobe(ap);
		refreshRightGlobe(ap);
		refreshChestplate(ap);
		refreshPants(ap);
		refreshLeftShoe(ap);
		refreshRightShoe(ap);
	}
	
	public void refreshEquipmentModels(){
		AreaPlayer ap = getAreaPlayer();
		if(ap != null)
			refreshEquipmentModels(ap);
	}
	
	public void refreshLeftWeapon(){
		AreaPlayer ap = getAreaPlayer();
		if(ap != null)
			refreshLeftWeapon(ap);
	}
	
	public void refreshRightWeapon(){
		AreaPlayer ap = getAreaPlayer();
		if(ap != null)
			refreshLeftWeapon(ap);
	}
	
	public void refreshHelmet(){
		AreaPlayer ap = getAreaPlayer();
		if(ap != null)
			refreshHelmet(ap);
	}
	
	public void refreshLeftGlobe(){
		AreaPlayer ap = getAreaPlayer();
		if(ap != null)
			refreshLeftGlobe(ap);
	}
	
	public void refreshRightGlobe(){
		AreaPlayer ap = getAreaPlayer();
		if(ap != null)
			refreshRightGlobe(ap);
	}
	
	public void refreshChestplate(){
		AreaPlayer ap = getAreaPlayer();
		if(ap != null)
			refreshChestplate(ap);
	}
	
	public void refreshPants(){
		AreaPlayer ap = getAreaPlayer();
		if(ap != null)
			refreshPants(ap);
	}
	
	public void refreshLeftShoe(){
		AreaPlayer ap = getAreaPlayer();
		if(ap != null)
			refreshLeftShoe(ap);
	}
	
	public void refreshRightShoe(){
		AreaPlayer ap = getAreaPlayer();
		if(ap != null)
			refreshRightShoe(ap);
	}
	
	public long getHealth(){
		return health;
	}
	
	public long getMana(){
		return mana;
	}
	
	public int getFocus(){
		return focus;
	}
	
	public int getEnergy(){
		return energy;
	}
	
	public int getRage(){
		return rage;
	}
	
	public long getMaxHealth(){
		return maxHealth;
	}
	
	public long getMaxMana(){
		return maxMana;
	}
	
	public int getMaxFocus(){
		return maxFocus;
	}
	
	public int getMaxEnergy(){
		return maxEnergy;
	}
	
	public int getMaxRage(){
		return maxRage;
	}
	
	public int getTurnSpeed(){
		return turnSpeed;
	}
	
	public int getStrength(){
		return strength;
	}
	
	public int getSpirit(){
		return spirit;
	}
	
	public void enterBattle(){
		energy = maxEnergy;
		focus = 0;
		rage = 0;
	}
	
	public void restoreHealth(long amount){
		health += amount;
		if(health > maxHealth)
			health = maxHealth;
	}
	
	public void restoreMana(long amount){
		mana += amount;
		if(mana > maxMana)
			mana = maxMana;
	}
	
	public void restoreFocus(int amount){
		focus += amount;
		if(focus > maxFocus)
			focus = maxFocus;
	}
	
	public void removeHealth(long finalDamage){
		health -= finalDamage;
		if(health < 0)
			health = 0;
		if(health > maxHealth)
			health = maxHealth;
	}
	
	public final void initialiseFirstGame(){
		initialiseExtraFirstGame();
		health = maxHealth;
		mana = maxMana;
		focus = 0;
		energy = maxEnergy;
		rage = 0;
	}
	
	public abstract AreaPlayer getAreaPlayer();
	
	public abstract boolean alwaysAvailable();
	
	public abstract boolean canEquip(InventoryType type);
	
	public abstract boolean canEquip(Item item);
	
	public abstract void initialiseExtraFirstGame();
	
	public abstract void refreshLeftWeapon(ModelOwner ap);
	
	public abstract void refreshRightWeapon(ModelOwner ap);
	
	public abstract void refreshHelmet(ModelOwner ap);
	
	public abstract void refreshLeftGlobe(ModelOwner ap);
	
	public abstract void refreshRightGlobe(ModelOwner ap);
	
	public abstract void refreshChestplate(ModelOwner ap);
	
	public abstract void refreshPants(ModelOwner ap);
	
	public abstract void refreshLeftShoe(ModelOwner ap);
	
	public abstract void refreshRightShoe(ModelOwner ap);
	
	public abstract ElementalStatistics getNaturalElementStats();
	
	protected abstract AbstractPlayerMoves createMoves();
}
