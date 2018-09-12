package nl.knokko.story.npc;

import nl.knokko.area.Area;
import nl.knokko.area.creature.AreaCreature;
import nl.knokko.inventory.trading.TradeOffers;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;

/**
 * A trading NPC that will not move.
 */
public abstract class LazyTraderNPC implements NPC {
	
	protected TradeOffers tradeOffers;
	protected Class<? extends Area> area;
	
	protected int tileX;
	protected int tileY;
	protected int tileZ;

	public LazyTraderNPC(Class<? extends Area> area, int tileX, int tileY, int tileZ, TradeOffers offers) {
		tradeOffers = offers;
		this.area = area;
		this.tileX = tileX;
		this.tileY = tileY;
		this.tileZ = tileZ;
	}
	
	@Override
	public Class<?>[] getPossibleAreas() {
		return new Class<?>[]{area};
	}
	
	@Override
	public AreaCreature createRepresentation(Area area) {
		if(area.getClass() == this.area)
			return createTrader();
		return null;
	}

	@Override
	public void save(BitOutput bits) {}

	@Override
	public void load(BitInput bits) {}

	@Override
	public void initFirstGame() {}
	
	protected abstract AreaCreature createTrader();
}
