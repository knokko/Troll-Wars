package nl.knokko.battle.element;

public interface ElementalStatistics {
	
	/**
	 * This method returns the resistance against the specified element.
	 * A resistance of 1 means complete immunity.
	 * A resistance of 0 means that the damage is not reduced or increased.
	 * A resistance of -1 means that the damage will be multiplied by 2.
	 * @param element The element to check the resistance for
	 * @return The resistance against the specified element
	 */
	float getResistance(BattleElement element);
	
	/**
	 * This method returns the power this creature will have when using the specified element.
	 * A power of 0 means that the attack will deal no damage at all.
	 * A power of 1 means that the attack will deal normal damage.
	 * A power of 2 means that the damage will be multiplied with 2.
	 * @param element The element to check the power for
	 * @return The power this creature will have when using the specified element
	 */
	float getPower(BattleElement element);
}