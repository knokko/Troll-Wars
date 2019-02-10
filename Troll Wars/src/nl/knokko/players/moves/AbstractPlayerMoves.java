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
package nl.knokko.players.moves;

import nl.knokko.battle.Battle;
import nl.knokko.battle.creature.ArmCreature;
import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.battle.creature.MovingBattleCreature;
import nl.knokko.battle.move.BattleMove;
import nl.knokko.battle.move.FightMoveOption;
import nl.knokko.battle.move.physical.*;
import nl.knokko.items.ItemWeapon;
import nl.knokko.model.body.arm.HumanoidArm;
import nl.knokko.model.body.hand.HumanoidHandProperties;
import nl.knokko.texture.Texture;
import nl.knokko.texture.factory.MyTextureLoader;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;

public abstract class AbstractPlayerMoves implements PlayerMoves {
	
	public static Texture[] loadGuiMoveTexture(String moveName){
		Texture[] textures = new Texture[3];
		String pre = "gui/moves/";
		textures[0] = new Texture(MyTextureLoader.loadTexture(pre + "learned/" + moveName));
		textures[1] = new Texture(MyTextureLoader.loadTexture(pre + "notlearned/" + moveName));
		textures[2] = new Texture(MyTextureLoader.loadTexture(pre + "cantlearn/" + moveName));
		return textures;
	}
	
	protected FightMoveOption[] learnedMoves;
	protected PlayerMoveOption[] learnableMoves;
	
	protected boolean[] learned;

	public AbstractPlayerMoves() {
		learnedMoves = getDefaultMoves();
		learnableMoves = getLearnableMoves();
		learned = new boolean[learnableMoves.length];
	}

	@Override
	public FightMoveOption[] getLearnedMoves() {
		return learnedMoves;
	}
	
	public void save(BitOutput buffer){
		buffer.addInt(learned.length);
		for(boolean bool : learned)
			buffer.addBoolean(bool);
	}
	
	public void load(BitInput buffer){
		int length = buffer.readInt();
		if(length != learned.length)
			System.out.println("The loaded save is deprecated because it had less moves than the game has now.");
		for(int i = 0; i < length; i++)
			learned[i] = buffer.readBoolean();
	}
	
	protected abstract FightMoveOption[] getDefaultMoves();
	
	protected abstract PlayerMoveOption[] getLearnableMoves();
	
	public static abstract class PlayerMoveOption {
		
		protected final Texture[] textures;
		protected final String name;
		protected final FightMoveOption move;
		
		protected final float x;
		protected final float y;
		
		protected PlayerMoveOption(String name, float x, float y){
			this.x = x;
			this.y = y;
			this.name = name;
			this.textures = loadGuiMoveTexture(name);
			this.move = createMoveOption();
		}
		
		protected abstract FightMoveOption createMoveOption();
		
		public FightMoveOption getMove(){
			return move;
		}
		
		public String getName(){
			return name;
		}
		
		public float getMenuX(){
			return x;
		}
		
		public float getMenuY(){
			return y;
		}
		
		public Texture getLearnedTexture(){
			return textures[0];
		}
		
		public Texture getLearnableTexture(){
			return textures[1];
		}
		
		public Texture getNotLearnableTexture(){
			return textures[2];
		}
	}
	
	public static abstract class BasicMoveOption implements FightMoveOption {
		
		protected final HumanoidArm arm;
		protected final HumanoidHandProperties hand;
		
		private final String name;

		protected BasicMoveOption(String name, HumanoidArm arm, HumanoidHandProperties hand) {
			this.name = name;
			this.arm = arm;
			this.hand = hand;
		}
		
		protected abstract BattleMove createMove(MovingBattleCreature caster, ArmCreature arm, BattleCreature target);
		
		protected abstract boolean canUseWeapon(ItemWeapon weapon);

		@Override
		public boolean canCast(BattleCreature caster, Battle battle) {
			return caster instanceof MovingBattleCreature && caster instanceof ArmCreature && canUseWeapon(((ArmCreature) caster).getWeapon());
		}

		@Override
		public boolean canCast(BattleCreature caster, BattleCreature target, Battle battle) {
			return !target.isFainted() && caster != target;
		}

		@Override
		public boolean isPositive() {
			return false;
		}

		@Override
		public BattleMove createMove(BattleCreature caster, BattleCreature target, Battle battle) {
			return BasicMoveOption.this.createMove((MovingBattleCreature) caster, (ArmCreature) caster, target);
		}

		@Override
		public Category getCategory() {
			return Category.BASIC;
		}

		@Override
		public String getName() {
			return name;
		}
		
		public static class PunchMoveOption extends BasicMoveOption {

			protected PunchMoveOption(HumanoidArm arm, HumanoidHandProperties hand) {
				super("Punch", arm, hand);
			}

			@Override
			protected BattleMove createMove(MovingBattleCreature caster, ArmCreature arm, BattleCreature target) {
				return new MovePunch(caster, arm, target);
			}

			@Override
			protected boolean canUseWeapon(ItemWeapon weapon) {
				return weapon == null || weapon.getPunchDamage() > 0;
			}
		}
		
		public static class StabMoveOption extends BasicMoveOption {

			protected StabMoveOption(HumanoidArm arm, HumanoidHandProperties hand) {
				super("Stab", arm, hand);
			}

			@Override
			protected BattleMove createMove(MovingBattleCreature caster, ArmCreature arm, BattleCreature target) {
				return new MoveStab(caster, arm, target);
			}

			@Override
			protected boolean canUseWeapon(ItemWeapon weapon) {
				return weapon != null && weapon.getStabDamage() > 0;
			}
		}
		
		public static class PrickMoveOption extends BasicMoveOption {
			
			protected PrickMoveOption(HumanoidArm arm, HumanoidHandProperties hand){
				super("Prick", arm, hand);
			}
			
			@Override
			protected BattleMove createMove(MovingBattleCreature caster, ArmCreature arm, BattleCreature target){
				return new MovePrick(caster, arm, target);
			}
			
			@Override
			protected boolean canUseWeapon(ItemWeapon weapon) {
				return weapon != null && weapon.getPrickDamage() > 0;
			}
		}
		
		public static class SmashMoveOption extends BasicMoveOption {
			
			protected SmashMoveOption(HumanoidArm arm, HumanoidHandProperties hand){
				super("Smash", arm, hand);
			}
			
			@Override
			protected BattleMove createMove(MovingBattleCreature caster, ArmCreature arm, BattleCreature target){
				return new MoveSmash(caster, arm, target);
			}
			
			@Override
			protected boolean canUseWeapon(ItemWeapon weapon) {
				return weapon != null && weapon.getSmashDamage() > 0;
			}
		}
		
		public static class SwingMoveOption extends BasicMoveOption {
			
			protected SwingMoveOption(HumanoidArm arm, HumanoidHandProperties hand){
				super("Swing", arm, hand);
			}
			
			@Override
			protected BattleMove createMove(MovingBattleCreature caster, ArmCreature arm, BattleCreature target){
				return new MoveSwing(caster, arm, target);
			}
			
			@Override
			protected boolean canUseWeapon(ItemWeapon weapon) {
				return weapon != null && weapon.getSwingDamage() > 0;
			}
		}
		
		public static class SlashMoveOption extends BasicMoveOption {
			
			protected SlashMoveOption(HumanoidArm arm, HumanoidHandProperties hand){
				super("Slash", arm, hand);
			}
			
			@Override
			protected BattleMove createMove(MovingBattleCreature caster, ArmCreature arm, BattleCreature target){
				return new MoveSlash(caster, arm, target);
			}
			
			@Override
			protected boolean canUseWeapon(ItemWeapon weapon) {
				return weapon != null && weapon.getSlashDamage() > 0;
			}
		}
	}
}
