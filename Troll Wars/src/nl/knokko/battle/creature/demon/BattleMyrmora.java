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
package nl.knokko.battle.creature.demon;

import java.util.List;

import nl.knokko.animation.body.AnimatorMyrre;
import nl.knokko.animation.body.BattleAnimationHelper;
import nl.knokko.battle.Battle;
import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.battle.creature.ResourceBattleCreature;
import nl.knokko.battle.element.ElementalStatistics;
import nl.knokko.battle.element.SimpleElementStats;
import nl.knokko.battle.move.BattleMove;
import nl.knokko.battle.move.FightMoveOption;
import nl.knokko.battle.move.ItemMoveOption;
import nl.knokko.battle.move.ItemMoveOption.Category;
import nl.knokko.battle.render.properties.BattleRenderProperties;
import nl.knokko.battle.render.properties.MyrreRenderProperties;
import nl.knokko.inventory.Inventory;
import nl.knokko.model.ModelOwner;
import nl.knokko.model.ModelPart;
import nl.knokko.model.body.BodyMyrre;
import nl.knokko.texture.painter.ModelPainter;
import nl.knokko.util.Maths;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;
import nl.knokko.util.resources.Resources;

public class BattleMyrmora {
	
	public static class Intro extends ResourceBattleCreature implements ModelOwner {
		
		public Intro(){
			super("Myrmora", 10000, 30000, 500, 1000, 1000, 0, 500, 0, BodyMyrre.Models.MYRMORA, BodyMyrre.Textures.MYRMORA);
		}
		
		private boolean leftArm;
		
		private int height;
		
		@Override
		public short getID() {
			return BattleCreature.Registry.ID_MYRMORA_INTRO;
		}
		
		@Override
		public ElementalStatistics getElementStats() {
			return SimpleElementStats.MYRRE;
		}
		
		@Override
		public int getArmor() {
			return 500;
		}
		
		@Override
		public int getResistance() {
			return 500;
		}
		
		@Override
		public boolean isPlayerControlled() {
			return false;
		}
		
		@Override
		public BattleMove chooseMove(Battle battle, BattleCreature[] ownTeam, BattleCreature[] opposingTeam) {
			// TODO This can become a little complicated...
			return null;
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
			return leftArm ? "Left Claw" : "Right Claw";
		}
		
		@Override
		public void swapArm() {
			leftArm = !leftArm;
		}
		
		@Override
		public List<ModelPart> getModels(){
			return models;
		}
		
		@Override
		protected BattleAnimationHelper createAnimator() {
			return new BattleAnimationHelper(getStepSize(), new AnimatorMyrre(this));
		}
		
		@Override
		protected void addBody(Object body, ModelPainter painter) {
			addBody();
		}
		
		@Override
		protected void addBody(BitInput bits) {
			addBody();
		}
		
		private void addBody(){
			models.add(Resources.createModelMyrre(BodyMyrre.Models.MYRMORA, BodyMyrre.Textures.MYRMORA));
			height = (int) (BodyMyrre.Models.MYRMORA.legLength() + BodyMyrre.Models.MYRMORA.bellyHeight());
		}
		
		@Override
		protected void saveBody(BitOutput bits) {}
		
		@Override
		protected int getYOffset() {
			return height * 3 / 4;
		}
		
		@Override
		protected BattleRenderProperties createRenderProperties(){
			return new MyrreRenderProperties(this, BodyMyrre.Models.MYRMORA);
		}
		
		protected float getStepSize(){
			return 4 * Maths.sin(AnimatorMyrre.LEG_ANGLE) * BodyMyrre.Models.MYRMORA.legLength();
		}
	}
}