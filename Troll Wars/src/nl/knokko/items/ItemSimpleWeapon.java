package nl.knokko.items;

import nl.knokko.battle.element.BattleElement;
import nl.knokko.battle.element.ElementalStatistics;

public abstract class ItemSimpleWeapon extends ItemWeapon {
	
	protected BattleElement element;
	
	protected int shootDamage;
	protected int stabDamage;
	protected int prickDamage;
	protected int swingDamage;
	protected int slashDamage;
	protected int smashDamage;
	protected int punchDamage;
	protected int spellDamage;

	public ItemSimpleWeapon(String name, BattleElement element, int shootDamage, int stabDamage, int prickDamage,
			int swingDamage, int slashDamage, int smashDamage, int punchDamage, int spellDamage) {
		super(name);
		this.element = element;
		this.shootDamage = shootDamage;
		this.stabDamage = stabDamage;
		this.prickDamage = prickDamage;
		this.swingDamage = swingDamage;
		this.slashDamage = slashDamage;
		this.smashDamage = smashDamage;
		this.punchDamage = punchDamage;
		this.spellDamage = spellDamage;
	}

	public ItemSimpleWeapon(String name, ElementalStatistics elementStats, BattleElement element, int shootDamage,
			int stabDamage, int prickDamage, int swingDamage, int slashDamage, int smashDamage, int punchDamage, int spellDamage) {
		super(name, elementStats);
		this.element = element;
		this.shootDamage = shootDamage;
		this.stabDamage = stabDamage;
		this.prickDamage = prickDamage;
		this.swingDamage = swingDamage;
		this.slashDamage = slashDamage;
		this.smashDamage = smashDamage;
		this.punchDamage = punchDamage;
		this.spellDamage = spellDamage;
	}

	@Override
	public BattleElement getElement() {
		return element;
	}

	@Override
	public int getShootDamage() {
		return shootDamage;
	}

	@Override
	public int getStabDamage() {
		return stabDamage;
	}

	@Override
	public int getPrickDamage() {
		return prickDamage;
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
}