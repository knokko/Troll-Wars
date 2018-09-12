package nl.knokko.battle.damage;

public enum AttackType {
	
	SLASH(2f, 0),
	STAB(2.4f, 0),
	SMASH(1.6f, 0),
	PUNCH(1.6f, 5),
	PRICK(2.2f, 0),
	SHOOT(2.5f, 0),
	SPELL(2f, 0),
	SWING(2.1f, 0);
	
	private final float armorBase;
	private final int baseDamage;
	
	private AttackType(float armorBase, int baseDamage){
		this.armorBase = armorBase;
		this.baseDamage = baseDamage;
	}
	
	public final float getArmorBase(){
		return armorBase;
	}
	
	public int getBaseDamage(){
		if(baseDamage > 0)
			return baseDamage;
		throw new UnsupportedOperationException("Attack type " + name() + " can't be used without weapon!");
	}
}
