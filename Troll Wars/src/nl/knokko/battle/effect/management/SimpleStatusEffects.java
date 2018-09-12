package nl.knokko.battle.effect.management;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import nl.knokko.battle.Battle;
import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.battle.damage.DamageCause;
import nl.knokko.battle.effect.StatusEffect;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;

import org.lwjgl.util.vector.Vector4f;

public class SimpleStatusEffects implements StatusEffects {
	
	protected final Collection<StatusEffect> effects;

	public SimpleStatusEffects() {
		effects = new ArrayList<StatusEffect>();
	}
	
	public SimpleStatusEffects(BitInput input){
		int size = input.readInt();
		effects = new ArrayList<StatusEffect>(size);
		for(int i = 0; i < size; i++){
			effects.add(StatusEffect.Registry.load(input));
		}
	}
	
	@Override
	public void save(Battle battle, BitOutput output){
		output.addInt(effects.size());
		for(StatusEffect effect : effects){
			output.addShort(effect.getID());
			effect.save(battle, output);
		}
	}

	@Override
	public Collection<StatusEffect> getEffects() {
		return effects;
	}

	@Override
	public Vector4f getEffectColors() {
		Vector4f color = new Vector4f();
		for(StatusEffect effect : effects)
			Vector4f.add(color, effect.getEffectColor(), color);
		return color;
	}

	@Override
	public long beforeAttack(BattleCreature host, BattleCreature target, DamageCause cause, long damage) {
		return StatusEffect.AttackPriority.beforeAttack(host, target, cause, damage, effects);
	}

	@Override
	public void beginTurn(BattleCreature host) {
		StatusEffect.UpdatePriority.beginTurn(host, effects);
	}

	@Override
	public void add(StatusEffect effect) {
		for(StatusEffect current : effects){
			if(current.getID() == effect.getID()){
				current.combine(effect);
				return;
			}
		}
		effects.add(effect);
	}

	@Override
	public void clear() {
		effects.clear();
	}

	@Override
	public void remove(short ID) {
		Iterator<StatusEffect> iterator = effects.iterator();
		while(iterator.hasNext()){
			if(iterator.next().getID() == ID){
				iterator.remove();
				return;
			}
		}
	}

	@Override
	public void remove(Selector selector) {
		Iterator<StatusEffect> iterator = effects.iterator();
		while(iterator.hasNext()){
			if(selector.shouldRemove(iterator.next())){
				iterator.remove();
			}
		}
	}
}