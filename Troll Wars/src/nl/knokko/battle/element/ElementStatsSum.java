package nl.knokko.battle.element;

public class ElementStatsSum implements ElementalStatistics {
	
	private final ElementalStatistics e1;
	private final ElementalStatistics e2;

	public ElementStatsSum(ElementalStatistics stats1, ElementalStatistics stats2) {
		e1 = stats1;
		e2 = stats2;
		if(e1 == null)
			throw new NullPointerException();
		if(e2 == null)
			throw new NullPointerException();
	}

	@Override
	public float getResistance(BattleElement element) {
		return e1.getResistance(element) + e2.getResistance(element);
	}

	@Override
	public float getPower(BattleElement element) {
		return e1.getPower(element) * e2.getPower(element);
	}
}
