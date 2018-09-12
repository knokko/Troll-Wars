package nl.knokko.items;

import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.battle.element.BattleElement;
import nl.knokko.battle.element.ElementalStatistics;
import nl.knokko.battle.element.SimpleElementStats;
import nl.knokko.battle.move.physical.MovePunch;
import nl.knokko.model.ModelPart;
import nl.knokko.model.body.hand.HumanoidHandProperties;
import nl.knokko.texture.equipment.SpearTexture;
import nl.knokko.util.resources.Resources;

public class ItemSpear extends ItemSimpleWeapon {
	
	protected SpearTexture texture;

	public ItemSpear(String name, SpearTexture texture, BattleElement element, int prickDamage, int swingDamage, int slashDamage, int smashDamage, int spellDamage) {
		this(name, texture, BattleElement.PHYSICAL, SimpleElementStats.NEUTRAL, prickDamage, swingDamage, slashDamage, smashDamage, spellDamage);
	}
	
	public ItemSpear(String name, SpearTexture texture, BattleElement element, int prickDamage, int spellDamage){
		this(name, texture, element, SimpleElementStats.NEUTRAL, prickDamage, prickDamage / 3, prickDamage / 3, prickDamage / 3, spellDamage);
	}

	public ItemSpear(String name, SpearTexture texture, BattleElement element, ElementalStatistics elementStats, int prickDamage, int swingDamage, int slashDamage, int smashDamage, int spellDamage) {
		super(name, elementStats, element, 0, 0, prickDamage, swingDamage, slashDamage, smashDamage, MovePunch.BASE_DAMAGE, spellDamage);
		this.texture = texture;
	}

	@Override
	public ModelPart createModel(HumanoidHandProperties hand, boolean left) {
		return Resources.createModelSpear(texture, hand);
	}

	@Override
	public float getRange(BattleCreature user, HumanoidHandProperties hand) {
		return (hand.handCoreLength() / texture.getSpearModel().stickRadius() / 2) * (texture.getSpearModel().stickLength() + texture.getSpearModel().pointLength()) - hand.handHeight();
	}
}