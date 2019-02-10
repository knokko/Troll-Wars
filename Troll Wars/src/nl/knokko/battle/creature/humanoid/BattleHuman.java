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
package nl.knokko.battle.creature.humanoid;

import nl.knokko.animation.body.AnimatorHumanoid;
import nl.knokko.animation.body.BattleAnimationHelper;
import nl.knokko.battle.Battle;
import nl.knokko.battle.creature.ArmCreature;
import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.battle.creature.MovingBattleCreature;
import nl.knokko.battle.creature.ResourceBattleCreature;
import nl.knokko.battle.element.ElementalStatistics;
import nl.knokko.battle.element.SimpleElementStats;
import nl.knokko.battle.move.BattleMove;
import nl.knokko.battle.move.FightMoveOption;
import nl.knokko.battle.move.ItemMoveOption;
import nl.knokko.battle.move.ItemMoveOption.Category;
import nl.knokko.battle.render.properties.BattleRenderProperties;
import nl.knokko.battle.render.properties.HumanRenderProperties;
import nl.knokko.equipment.EquipmentFull;
import nl.knokko.inventory.Inventory;
import nl.knokko.items.ItemWeapon;
import nl.knokko.model.ModelPart;
import nl.knokko.model.body.BodyHuman;
import nl.knokko.model.body.hand.HumanoidHandProperties;
import nl.knokko.model.factory.creature.HumanFactory;
import nl.knokko.story.npc.IntroHumanNPC;
import nl.knokko.texture.painter.HumanPainter;
import nl.knokko.texture.painter.ModelPainter;
import nl.knokko.util.Maths;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;
import static nl.knokko.model.body.BodyHuman.Helper.*;

public abstract class BattleHuman extends ResourceBattleCreature implements MovingBattleCreature, ArmCreature {
	
	protected BodyHuman body;
	protected HumanPainter painter;
	
	protected float radiusXZ;
	protected float height;
	
	protected boolean leftArm;

	public BattleHuman(String name, int level, EquipmentFull equipment, BodyHuman body, HumanPainter painter) {
		super(name, 400 + 170 * level, 200 + 150 * level, 100 + 30 * level, 100 + 30 * level, 100 + 30 * level, 23 + 9 * level, 32 + 13 * level, 12 + level, body, painter);
		this.equipment = equipment;
	}

	public BattleHuman(BitInput buffer) {
		super(buffer);
		leftArm = buffer.readBoolean();
	}
	
	@Override
	public void saveInBattle(Battle battle, BitOutput output){
		super.saveInBattle(battle, output);
		output.addBoolean(leftArm);
	}

	@Override
	public ElementalStatistics getElementStats() {
		return SimpleElementStats.HUMAN;
	}

	@Override
	public int getArmor() {
		return equipment.getArmor();
	}

	@Override
	public int getResistance() {
		return equipment.getResistance();
	}

	@Override
	public boolean isPlayerControlled() {
		return false;
	}

	@Override
	public ItemMoveOption[] getItems() {
		return null;
	}

	@Override
	public ItemMoveOption[] getItems(Category category) {
		return null;
	}

	@Override
	public FightMoveOption[] getMoves() {
		return null;
	}

	@Override
	public FightMoveOption[] getMoves(nl.knokko.battle.move.FightMoveOption.Category category) {
		return null;
	}

	@Override
	public Inventory getInventory() {
		return null;
	}

	@Override
	public String getActiveArm() {
		return leftArm ? "Left Arm" : "Right Arm";
	}

	@Override
	public void swapArm() {
		leftArm = !leftArm;
	}

	@Override
	public ModelPart getUpperArm() {
		return leftArm ? models.get(0).getChildren()[1] : models.get(0).getChildren()[2];
	}

	@Override
	public ModelPart getUnderArm() {
		return getUpperArm().getChildren()[0];
	}

	@Override
	public float getUpperArmLength() {
		return body.upperArmLength();
	}

	@Override
	public float getUnderArmLength() {
		return body.underArmLength();
	}

	@Override
	public ItemWeapon getWeapon() {
		return leftArm ? equipment.getLeftWeapon() : equipment.getRightWeapon();
	}

	@Override
	public HumanoidHandProperties getHand() {
		return body;
	}

	@Override
	public float getSpeed() {
		return getStepSize() / BattleCreature.StepDuration.HUMAN;
	}

	@Override
	protected BattleAnimationHelper createAnimator() {
		ModelPart m = models.get(0);
		return new BattleAnimationHelper(getStepSize(), new AnimatorHumanoid(getLeftArm(m), getRightArm(m), getLeftLeg(m), getRightLeg(m), getLeftUnderLeg(m), getRightUnderLeg(m)));
	}

	@Override
	protected void addBody(Object body, ModelPainter painter) {
		this.body = (BodyHuman) body;
		this.painter = (HumanPainter) painter;
		models.add(HumanFactory.createModelHuman(this.body, this.painter));
		prepareHitBox();
	}

	@Override
	protected void addBody(BitInput bits) {
		body = BodyHuman.Models.loadInstance(bits);
		painter = new HumanPainter(bits);
		models.add(HumanFactory.createModelHuman(body, painter));
		prepareHitBox();
	}

	@Override
	protected void saveBody(BitOutput bits) {
		body.save(bits);
		painter.save(bits);
	}

	@Override
	protected int getYOffset() {
		return (int) (height / 2);
	}
	
	@Override
	protected BattleRenderProperties createRenderProperties(){
		return new HumanRenderProperties(this, body);
	}
	
	protected float getStepSize(){
		return Maths.sin(AnimatorHumanoid.LEG_ANGLE) * (body.upperLegLength() + body.underLegLength()) *  4;
	}
	
	private void prepareHitBox(){
		radiusXZ = Math.max(body.bellyWidth(), body.bellyDepth()) / 2 + (body.upperArmLength() + body.underArmLength()) * Maths.sin(15) + body.shoulderRadius();
		height = body.footMidHeight() + body.underLegLength() + body.upperLegLength() + body.bellyHeight() + body.headHeight();
	}
	
	public static class IntroWarrior extends BattleHuman {

		public IntroWarrior(IntroHumanNPC npc) {
			super("Human Warrior", npc.getLevel(), EquipmentFull.createFullIron(npc.getWeapon()), npc.getBody(), npc.getPainter());
		}
		
		public IntroWarrior(BitInput input){
			super(input);
		}

		@Override
		public short getID() {
			return BattleCreature.Registry.ID_HUMAN_INTRO_WARRIOR;
		}
		
		@Override
		public EquipmentFull createEquipment(){
			return new EquipmentFull();
		}

		@Override
		public BattleMove chooseMove(Battle battle, BattleCreature[] ownTeam, BattleCreature[] opposingTeam) {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	public static class IntroPaladin extends BattleHuman {

		public IntroPaladin(IntroHumanNPC npc) {
			super("Human Paladin", npc.getLevel(), EquipmentFull.createFullBlessedIron(npc.getWeapon()), npc.getBody(), npc.getPainter());
		}

		@Override
		public short getID() {
			return BattleCreature.Registry.ID_HUMAN_INTRO_PALADIN;
		}

		@Override
		public BattleMove chooseMove(Battle battle, BattleCreature[] ownTeam, BattleCreature[] opposingTeam) {
			// TODO Auto-generated method stub
			return null;
		}
	}
}