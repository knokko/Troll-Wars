package nl.knokko.battle.element;

public class ElementStatsMultiSum implements ElementalStatistics {
	
	private final ElementalStatistics[] stats;

	public ElementStatsMultiSum(ElementalStatistics... stats) {
		this.stats = stats;
	}

	@Override
	public float getResistance(BattleElement element) {
		float resistance = 0;
		for(ElementalStatistics es : stats)
			if(es != null)
				resistance += es.getResistance(element);
		return resistance;
	}

	@Override
	public float getPower(BattleElement element) {
		float power = 1;
		for(ElementalStatistics es : stats)
			if(es != null)
				power *= es.getPower(element);
		return power;
	}
	
	public void set(ElementalStatistics eStats, int index){
		stats[index] = eStats;
	}
}
