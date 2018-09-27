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
package nl.knokko.battle.creature.player;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import nl.knokko.animation.body.BattleAnimationHelper;
import nl.knokko.battle.Battle;
import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.battle.damage.DamageCalculator;
import nl.knokko.battle.damage.DamageCause;
import nl.knokko.battle.effect.management.SimpleStatusEffects;
import nl.knokko.battle.effect.management.StatusEffects;
import nl.knokko.battle.element.ElementStatsSum;
import nl.knokko.battle.element.ElementalStatistics;
import nl.knokko.battle.move.BattleMove;
import nl.knokko.battle.move.FightMoveOption;
import nl.knokko.battle.move.ItemMoveOption;
import nl.knokko.battle.render.properties.BattleRenderProperties;
import nl.knokko.equipment.Equipment;
import nl.knokko.inventory.Inventory;
import nl.knokko.main.Game;
import nl.knokko.model.ModelOwner;
import nl.knokko.model.ModelPart;
import nl.knokko.players.Player;
import nl.knokko.shaders.ShaderType;
import nl.knokko.util.Maths;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;

public abstract class BattlePlayer implements BattleCreature, ModelOwner {
	
	protected final Player player;
	protected final List<ModelPart> models;
	protected final ElementalStatistics eStats;
	protected final BattleAnimationHelper animator;
	protected final StatusEffects statusEffects;
	protected final BattleRenderProperties renderProperties;
	
	protected int x;
	protected int y;
	protected int z;
	
	protected float yaw;
	
	public static BattlePlayer getInstance(Player player){
		if(player == Game.getPlayers().gothrok)
			return new BattleGothrok();
		throw new IllegalArgumentException("Unknown player: " + player);
	}
	
	public BattlePlayer(BitInput buffer, Player player){
		this.player = player;
		this.eStats = new ElementStatsSum(player.getNaturalElementStats(), player.getEquipment().getElementStats());
		this.models = new ArrayList<ModelPart>(1);
		this.addBody();
		this.animator = createAnimator();
		x = buffer.readInt();
		y = buffer.readInt();
		z = buffer.readInt();
		yaw = buffer.readFloat();
		this.statusEffects = new SimpleStatusEffects(buffer);
		player.refreshEquipmentModels(this);
		this.renderProperties = createRenderProperties();
	}

	BattlePlayer(Player player) {
		this.player = player;
		this.models = new ArrayList<ModelPart>(1);
		this.statusEffects = new SimpleStatusEffects();
		this.addBody();
		this.player.refreshEquipmentModels(this);
		this.animator = createAnimator();
		this.eStats = new ElementStatsSum(player.getNaturalElementStats(), player.getEquipment().getElementStats());
		this.renderProperties = createRenderProperties();
	}

	@Override
	public void saveInBattle(Battle battle, BitOutput buffer) {
		buffer.addInt(x);
		buffer.addInt(y);
		buffer.addInt(z);
		buffer.addFloat(yaw);
		statusEffects.save(battle, buffer);
	}

	@Override
	public boolean isFainted() {
		return getHealth() <= 0;
	}

	@Override
	public long getHealth() {
		return player.getHealth();
	}

	@Override
	public long getMaxHealth() {
		return player.getMaxHealth();
	}

	@Override
	public long getMana() {
		return player.getMana();
	}

	@Override
	public long getMaxMana() {
		return player.getMaxMana();
	}

	@Override
	public int getFocus() {
		return player.getFocus();
	}

	@Override
	public int getMaxFocus() {
		return player.getMaxFocus();
	}

	@Override
	public int getEnergy() {
		return player.getEnergy();
	}

	@Override
	public int getMaxEnergy() {
		return player.getMaxEnergy();
	}

	@Override
	public int getRage() {
		return player.getRage();
	}

	@Override
	public int getMaxRage() {
		return player.getMaxRage();
	}
	
	@Override
	public int getStrength(){
		return player.getStrength();
	}
	
	@Override
	public int getSpirit(){
		return player.getSpirit();
	}

	@Override
	public void restoreHealth(long amount) {
		player.restoreHealth(amount);
	}

	@Override
	public void restoreMana(long amount) {
		player.restoreMana(amount);
	}
	
	@Override
	public void restoreFocus(int amount){
		player.restoreFocus(amount);
	}

	@Override
	public ElementalStatistics getElementStats() {
		return new ElementStatsSum(player.getNaturalElementStats(), player.getEquipment().getElementStats());
	}
	
	@Override
	public StatusEffects getStatusEffects(){
		return statusEffects;
	}
	
	@Override
	public Equipment getEquipment(){
		return player.getEquipment();
	}

	@Override
	public void attack(DamageCause cause, long damage) {
		player.removeHealth(DamageCalculator.calculateFinalDamage(this, cause, damage));
	}
	
	@Override
	public void inflictTrueDamage(long amount){
		player.removeHealth(amount);
	}

	@Override
	public int getArmor() {
		return player.getEquipment().getArmor();
	}

	@Override
	public int getResistance() {
		return player.getEquipment().getResistance();
	}

	@Override
	public int getTurnSpeed() {
		return player.getTurnSpeed();
	}

	@Override
	public boolean isPlayerControlled() {
		return true;
	}

	@Override
	public BattleMove chooseMove(Battle battle, BattleCreature[] ownTeam, BattleCreature[] opposingTeam) {
		return null;
	}

	@Override
	public ItemMoveOption[] getItems() {
		return Inventory.Helper.getItemMoves(getInventory());
	}

	@Override
	public ItemMoveOption[] getItems(ItemMoveOption.Category category) {
		return Inventory.Helper.getItemMoves(getInventory(), category);
	}

	@Override
	public FightMoveOption[] getMoves() {
		return player.getMoves();
	}

	@Override
	public FightMoveOption[] getMoves(FightMoveOption.Category category) {
		FightMoveOption[] allMoves = player.getMoves();
		ArrayList<FightMoveOption> rightMoves = new ArrayList<FightMoveOption>(allMoves.length);
		for(FightMoveOption move : allMoves)
			if(move.getCategory() == category)
				rightMoves.add(move);
		return rightMoves.toArray(new FightMoveOption[rightMoves.size()]);
	}

	@Override
	public List<ModelPart> getModels() {
		return models;
	}
	
	@Override
	public ShaderType getShaderType(){
		return ShaderType.NORMAL;
	}

	@Override
	public Inventory getInventory() {
		return Game.getPlayerInventory();
	}

	@Override
	public Matrix4f getMatrix() {
		return Maths.createTransformationMatrix(new Vector3f(renderProperties.getCentreX(), renderProperties.getMinY(), renderProperties.getCentreZ()), 0, -yaw, 0, 1);
	}

	@Override
	public void move(float dx, float dy, float dz) {
		animator.walk(dx, dy, dz);
		x += Math.round(dx);
		y += Math.round(dy);
		z += Math.round(dz);
		if(dx != 0 || dz != 0)
			yaw = (float) -Maths.getYaw(new Vector3f(dx, dy, dz));
	}
	
	@Override
	public void setPosition(int x, int y, int z){
		this.x = x;
		this.y = y - getYOffset();
		this.z = z;
		animator.reset();
	}
	
	@Override
	public void setRotation(float pitch, float yaw, float roll){
		this.yaw = yaw;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY(){
		return y + getYOffset();
	}

	@Override
	public int getZ() {
		return z;
	}

	@Override
	public String getName() {
		return player.name();
	}
	
	@Override
	public BattleRenderProperties getRenderProperties(){
		return renderProperties;
	}
	
	protected abstract void addBody();
	
	protected abstract BattleAnimationHelper createAnimator();
	
	protected abstract int getYOffset();
	
	protected abstract BattleRenderProperties createRenderProperties();
}
