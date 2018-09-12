package nl.knokko.items;

import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.battle.element.BattleElement;
import nl.knokko.model.ModelPart;
import nl.knokko.model.body.hand.HumanoidHandProperties;
import nl.knokko.model.equipment.weapon.ModelBone;
import nl.knokko.texture.painter.BonePainter;
import nl.knokko.util.color.Color;
import nl.knokko.util.resources.Resources;

public class ItemBone extends ItemWeapon {
	
	protected final ModelBone model;
	protected final Color color;
	
	protected final int stabDamage;
	protected final int swingDamage;
	protected final int slashDamage;
	protected final int smashDamage;
	protected final int punchDamage;
	protected final int spellDamage;

	public ItemBone(String name, ModelBone model, Color color, int stabDamage, int swingDamage, int slashDamage, int smashDamage, int punchDamage, int spellDamage) {
		super(name);
		this.model = model;
		this.color = color;
		this.stabDamage = stabDamage;
		this.swingDamage = swingDamage;
		this.slashDamage = slashDamage;
		this.smashDamage = smashDamage;
		this.punchDamage = punchDamage;
		this.spellDamage = spellDamage;
	}
	
	@Override
	public ModelPart createModel(HumanoidHandProperties hand, boolean left){
		return Resources.createModelBone(model, new BonePainter(color), hand, left);
	}

	@Override
	public float getRange(BattleCreature user, HumanoidHandProperties hand) {//TODO test this one and that of ItemSpear
		return (hand.handCoreLength() / Math.max(model.boneRadiusX(), model.boneRadiusZ()) * 0.5f) * (model.boneLength() + model.boneRadiusTopY());
	}

	@Override
	public int getShootDamage() {
		return 0;
	}

	@Override
	public int getStabDamage() {
		return stabDamage;
	}

	@Override
	public int getPrickDamage() {
		return 0;
	}

	@Override
	public int getSwingDamage() {
		return swingDamage;
	}

	@Override
	public int getSlashDamage() {
		return slashDamage;
	}

	@Override
	public int getSmashDamage() {
		return smashDamage;
	}

	@Override
	public int getPunchDamage() {
		return punchDamage;
	}

	@Override
	public int getSpellDamage() {
		return spellDamage;
	}

	@Override
	public BattleElement getElement() {
		return BattleElement.PHYSICAL;
	}
}
