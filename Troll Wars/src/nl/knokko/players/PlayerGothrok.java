/* 
 * The MIT License
 *
 * Copyright 2018 knokko.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
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
 */
package nl.knokko.players;

import nl.knokko.area.creature.AreaCreature;
import nl.knokko.area.creature.AreaPlayer;
import nl.knokko.battle.element.ElementalStatistics;
import nl.knokko.battle.element.SimpleElementStats;
import nl.knokko.inventory.InventoryType;
import nl.knokko.items.Item;
import nl.knokko.items.Items;
import nl.knokko.main.Game;
import nl.knokko.model.ModelOwner;
import nl.knokko.model.ModelPart;
import nl.knokko.model.body.BodyTroll;
import nl.knokko.players.moves.PlayerMovesGothrok;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;

public class PlayerGothrok extends Player {
	
	private boolean leftHand;

	public PlayerGothrok() {}

	@Override
	public boolean alwaysAvailable() {
		return true;
	}

	@Override
	public boolean canEquip(InventoryType type) {
		return true;
	}

	@Override
	public boolean canEquip(Item item) {
		return true;
	}

	@Override
	public void initialiseExtraFirstGame() {
		equipment.equipRightWeapon(Items.POG_BONE);
		Game.getPlayerInventory().addItem(Items.SORG_SPEAR);
		refreshRightWeapon(getAreaPlayer());
		turnSpeed = 10;
		maxHealth = 1000;
		maxMana = 500;
		maxFocus = 300;
		maxEnergy = 500;
		maxRage = 500;
		strength = 100;
		spirit = 100;
	}
	
	@Override
	public AreaPlayer getAreaPlayer(){
		return Game.getArea().getArea().getPlayer(true);
	}
	
	@Override
	public void refreshHelmet(ModelOwner ap){
		if(equipment.getHelmet() != null)
			BodyTroll.Helper.setHelmet(ap, equipment.getHelmet().createModel(b(ap)));
		else
			BodyTroll.Helper.setHelmet(ap, null);
	}
	
	@Override
	public void refreshLeftWeapon(ModelOwner ap){
		if(equipment.getLeftWeapon() != null)
			BodyTroll.Helper.setLeftWeapon(ap, equipment.getLeftWeapon().createModel(b(ap), true));
		else
			BodyTroll.Helper.setLeftWeapon(ap, null);
	}
	
	@Override
	public void refreshRightWeapon(ModelOwner ap){
		if(equipment.getRightWeapon() != null)
			BodyTroll.Helper.setRightWeapon(ap, equipment.getRightWeapon().createModel(b(ap), true));
		else
			BodyTroll.Helper.setRightWeapon(ap, null);
	}
	
	@Override
	public void refreshLeftGlobe(ModelOwner ap){
		if(equipment.getLeftGlobe() != null)
			BodyTroll.Helper.setLeftGlobe(ap, equipment.getLeftGlobe().createModelLeft(b(ap)));
		else
			BodyTroll.Helper.setLeftGlobe(ap, null);
	}
	
	@Override
	public void refreshRightGlobe(ModelOwner ap){
		if(equipment.getRightGlobe() != null)
			BodyTroll.Helper.setRightGlobe(ap, equipment.getRightGlobe().createModelRight(b(ap)));
		else
			BodyTroll.Helper.setRightGlobe(ap, null);
	}
	
	@Override
	public void refreshChestplate(ModelOwner ap){
		if(equipment.getChestplate() != null)
			BodyTroll.Helper.setChestplate(ap, equipment.getChestplate().createModelBelly(b(ap)), equipment.getChestplate().createModelUpperArm(b(ap)), equipment.getChestplate().createModelUnderArm(b(ap)));
		else
			BodyTroll.Helper.setChestplate(ap, (ModelPart) null, null, null);
	}
	
	@Override
	public void refreshPants(ModelOwner ap){
		if(equipment.getPants() != null)
			BodyTroll.Helper.setPants(ap, equipment.getPants().createModelUpperLeg(b(ap)), equipment.getPants().createModelUnderLeg(b(ap)));
		else
			BodyTroll.Helper.setPants(ap, (ModelPart) null, null);
	}
	
	@Override
	public void refreshLeftShoe(ModelOwner ap){
		if(equipment.getLeftShoe() != null)
			BodyTroll.Helper.setLeftShoe(ap, equipment.getLeftShoe().createModelLeft(b(ap), b(ap)));
		else
			BodyTroll.Helper.setLeftShoe(ap, null);
	}
	
	@Override
	public void refreshRightShoe(ModelOwner ap){
		if(equipment.getRightShoe() != null)
			BodyTroll.Helper.setRightShoe(ap, equipment.getRightShoe().createModelRight(b(ap), b(ap)));
		else
			BodyTroll.Helper.setRightShoe(ap, null);
	}

	@Override
	public ElementalStatistics getNaturalElementStats() {
		return SimpleElementStats.TROLL;
	}
	
	@Override
	public BitOutput save(BitOutput buffer){
		buffer = super.save(buffer);
		buffer.addBoolean(leftHand);
		return buffer;
	}
	
	@Override
	public void load(BitInput buffer){
		super.load(buffer);
		leftHand = buffer.readBoolean();
	}

	@Override
	protected PlayerMovesGothrok createMoves() {
		return new PlayerMovesGothrok();
	}
	
	public void swapHands(){
		leftHand = !leftHand;
	}
	
	public boolean useLeftHand(){
		return leftHand;
	}
	
	public boolean useRightHand(){
		return !leftHand;
	}
	
	private static BodyTroll b(ModelOwner owner){
		return owner instanceof AreaCreature ? BodyTroll.Models.AREA_GOTHROK : BodyTroll.Models.BATTLE_GOTHROK;
	}
}
