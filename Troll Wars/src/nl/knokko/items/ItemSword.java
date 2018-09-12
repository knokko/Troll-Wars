package nl.knokko.items;

import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.battle.element.BattleElement;
import nl.knokko.battle.element.ElementalStatistics;
import nl.knokko.battle.move.physical.MovePunch;
import nl.knokko.model.ModelPart;
import nl.knokko.model.body.hand.HumanoidHandProperties;
import nl.knokko.texture.equipment.SwordTexture;
import nl.knokko.util.resources.Resources;

public class ItemSword extends ItemSimpleWeapon {
	
	protected SwordTexture texture;
	
	public ItemSword(String name, SwordTexture texture, BattleElement element, int slashDamage, int spellDamage) {
		super(name, element, 0, 0, slashDamage * 4 / 3, slashDamage, slashDamage, slashDamage, MovePunch.BASE_DAMAGE, spellDamage);
		this.texture = texture;
	}

	public ItemSword(String name, SwordTexture texture, ElementalStatistics elementStats,
			BattleElement element, int slashDamage, int spellDamage) {
		super(name, elementStats, element, 0, 0, slashDamage * 4 / 3, slashDamage, slashDamage, slashDamage, MovePunch.BASE_DAMAGE, spellDamage);
		this.texture = texture;
	}

	@Override
	public ModelPart createModel(HumanoidHandProperties hand, boolean left) {
		return Resources.createModelSword(texture, hand);
	}

	@Override
	public float getRange(BattleCreature user, HumanoidHandProperties hand) {
		return (hand.handCoreLength() / texture.getSwordModel().handleRadius() / 2) * (texture.getSwordModel().handleLength() + texture.getSwordModel().middleWidth() + texture.getSwordModel().bladeLength()) - hand.handHeight();
	}
}