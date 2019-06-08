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
package nl.knokko.battle.creature;

import java.util.List;

import org.lwjgl.util.vector.Matrix4f;

import nl.knokko.battle.Battle;
import nl.knokko.battle.creature.bird.BattleMountainBird;
import nl.knokko.battle.creature.demon.BattleMyrmora;
import nl.knokko.battle.creature.humanoid.BattleHuman;
import nl.knokko.battle.creature.humanoid.BattleTroll;
import nl.knokko.battle.creature.player.BattleGothrok;
import nl.knokko.battle.damage.DamageCause;
import nl.knokko.battle.effect.management.StatusEffects;
import nl.knokko.battle.element.BattleElement;
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
import nl.knokko.shaders.ShaderType;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;
import nl.knokko.view.camera.Camera;

public interface BattleCreature extends ModelOwner {
	
	/**
	 * This method must store the same amount of bits as it loads!
	 * @param buffer
	 */
	void saveInBattle(Battle battle, BitOutput buffer);
	
	/**
	 * @return A type-unique identifier of this creature that can be used to recreate this creature.
	 */
	short getID();
	
	boolean isFainted();
	
	long getHealth();
	
	long getMaxHealth();
	
	long getMana();
	
	long getMaxMana();
	
	int getFocus();
	
	int getMaxFocus();
	
	int getEnergy();
	
	int getMaxEnergy();
	
	int getRage();
	
	int getMaxRage();
	
	int getStrength();
	
	int getSpirit();
	
	void restoreHealth(long amount);
	
	void restoreMana(long amount);
	
	void restoreFocus(int amount);
	
	Equipment getEquipment();
	
	ElementalStatistics getElementStats();
	
	BattleElement getAttackElement();
	
	void attack(DamageCause cause, long damage);
	
	void inflictTrueDamage(long amount);
	
	/**
	 * The armor is used to reduce damage from physical elements. However, this method
	 * only determines what number should be displayed.
	 * Actual damage reduction should be applied in attack(DamageCause,long).
	 * @return The current armor of this creature.
	 */
	int getArmor();
	
	/**
	 * The resistance is used to reduce damage from magical elements. However, this method
	 * only determines what number should be displayed.
	 * Actual damage reduction should be applied in attack(DamageCause,long).
	 * @return
	 */
	int getResistance();
	
	/**
	 * The turn speed is the value that determines when and how many times a BattleCreature is on turn during a battle.
	 * The greater this value, the more turns this BattleCreature has.
	 * @return The turn speed of this BattleCreature
	 */
	int getTurnSpeed();
	
	boolean isPlayerControlled();
	
	BattleMove chooseMove(Battle battle, BattleCreature[] ownTeam, BattleCreature[] opposingTeam);
	
	ItemMoveOption[] getItems();
	
	ItemMoveOption[] getItems(ItemMoveOption.Category category);
	
	FightMoveOption[] getMoves();
	
	FightMoveOption[] getMoves(FightMoveOption.Category category);
	
	List<ModelPart> getModels();
	
	StatusEffects getStatusEffects();
	
	ShaderType getShaderType();
	
	/**
	 * @return The inventory of this battle creature or null if it has no inventory.
	 */
	Inventory getInventory();
	
	Matrix4f getMatrix(Camera camera);
	
	/**
	 * Values might be cast to int!
	 * @param dx
	 * @param dy
	 * @param dz
	 */
	void move(float dx, float dy, float dz);
	
	void setPosition(int x, int y, int z);
	
	void setRotation(float pitch, float yaw, float roll);
	
	int getX();
	
	int getY();
	
	int getZ();
	
	BattleRenderProperties getRenderProperties();
	
	String getName();
	
	String getActiveArm();
	
	void swapArm();
	
	public static final class Registry {
		
		private Registry(){}
		
		private static final Class<?>[] ID_MAP = {
			BattleTroll.class, BattleGothrok.class, BattleMountainBird.class,
			BattleHuman.IntroWarrior.class, BattleHuman.IntroPaladin.class, BattleMyrmora.Intro.class
		};
		
		public static final short ID_TROLL = -32768;
		public static final short ID_PLAYER_GOTHROK = -32767;
		public static final short ID_BIRD = -32766;
		
		public static final short ID_HUMAN_INTRO_WARRIOR = -32765;
		public static final short ID_HUMAN_INTRO_PALADIN = -32764;
		public static final short ID_MYRMORA_INTRO = -32763;
		
		public static void saveCreature(Battle battle, BattleCreature creature, BitOutput bits){
			bits.addShort(creature.getID());
			creature.saveInBattle(battle, bits);
		}
		
		public static BattleCreature loadCreature(BitInput bits){
			try {
				return (BattleCreature) ID_MAP[bits.readShort() - Short.MIN_VALUE].getConstructor(BitInput.class).newInstance(bits);
			} catch(Exception ex){
				throw new IllegalArgumentException(ex);
			}
		}
	}
	
	public static final class StepDuration {
		
		public static final int TROLL = Game.fps() * 1;
		public static final int HUMAN = Game.fps() * 7 / 9;
	}
	
	public static final class MoveSpeed {
		
		public static final float BIRD = 120f / Game.fps();
	}
}