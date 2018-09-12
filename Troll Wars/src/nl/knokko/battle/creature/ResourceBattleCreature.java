package nl.knokko.battle.creature;

import nl.knokko.battle.Battle;
import nl.knokko.battle.damage.DamageCause;
import nl.knokko.main.Game;
import nl.knokko.texture.painter.ModelPainter;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;

public abstract class ResourceBattleCreature extends SimpleBattleCreature {
	
	protected int maxEnergy;
	protected int energy;
	
	protected int maxRage;
	protected int rage;
	
	protected int maxFocus;
	protected int focus;

	public ResourceBattleCreature(String name, long maxHealth, long maxMana, int maxEnergy, int maxRage, int maxFocus, int strength, int spirit, int turnSpeed, Object body, ModelPainter painter) {
		super(name, maxHealth, maxMana, strength, spirit, turnSpeed, body, painter);
		this.maxEnergy = Game.getOptions().getDifficulty().determineEnergy(maxEnergy);
		this.energy = maxEnergy;
		this.maxRage = Game.getOptions().getDifficulty().determineRage(maxRage);
		this.rage = 0;
		this.maxFocus = Game.getOptions().getDifficulty().determineFocus(maxFocus);
		this.focus = 0;
	}

	public ResourceBattleCreature(BitInput input) {
		super(input);
		maxEnergy = input.readInt();
		energy = input.readInt();
		maxRage = input.readInt();
		rage = input.readInt();
		maxFocus = input.readInt();
		focus = input.readInt();
	}
	
	@Override
	public void saveInBattle(Battle battle, BitOutput output){
		super.saveInBattle(battle, output);
		output.addInt(maxEnergy);
		output.addInt(energy);
		output.addInt(maxRage);
		output.addInt(rage);
		output.addInt(maxFocus);
		output.addInt(focus);
	}
	
	@Override
	public int getMaxEnergy(){
		return maxEnergy;
	}
	
	@Override
	public int getEnergy(){
		return energy;
	}
	
	@Override
	public int getMaxRage(){
		return maxRage;
	}
	
	@Override
	public int getRage(){
		return rage;
	}
	
	@Override
	public int getMaxFocus(){
		return maxFocus;
	}
	
	@Override
	public int getFocus(){
		return focus;
	}
	
	@Override
	public void attack(DamageCause cause, long damage){
		long previousHealth = health;
		super.attack(cause, damage);
		long actualDamage = previousHealth - health;
		if(actualDamage > 0){
			double part = actualDamage / maxHealth;
			part *= 3;
			rage += part * maxRage;
			focus -= part * maxFocus;
			if(rage > maxRage)
				rage = maxRage;
			if(focus < 0)
				focus = 0;
		}
	}
	
	@Override
	public void restoreFocus(int amount){
		focus += amount;
		if(focus > maxFocus)
			focus = maxFocus;
	}
}