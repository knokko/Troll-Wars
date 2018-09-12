package nl.knokko.battle.element;

public class ArrayElementStats implements ElementalStatistics {
	
	private final float[] resistances;
	private final float[] powers;

	public ArrayElementStats() {
		resistances = new float[BattleElement.count()];
		powers = new float[BattleElement.count()];
	}
	
	public ArrayElementStats setPower(BattleElement element, float power){
		powers[element.ordinal()] = power;
		return this;
	}
	
	public ArrayElementStats setResistance(BattleElement element, float resistance){
		resistances[element.ordinal()] = resistance;
		return this;
	}
	
	public ArrayElementStats setElementValues(BattleElement element, float resistance, float power){
		resistances[element.ordinal()] = resistance;
		powers[element.ordinal()] = power;
		return this;
	}

	@Override
	public float getResistance(BattleElement element) {
		return resistances[element.ordinal()];
	}

	@Override
	public float getPower(BattleElement element) {
		return powers[element.ordinal()];
	}

}
