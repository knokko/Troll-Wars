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