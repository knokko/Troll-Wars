package nl.knokko.story.npc;

import nl.knokko.area.Area;
import nl.knokko.area.creature.AreaCreature;
import nl.knokko.area.creature.TrollTrader;
import nl.knokko.inventory.trading.TradeOffers;
import nl.knokko.util.position.SpawnPosition;

public class LazyTrollTraderNPC extends LazyTraderNPC {

	public LazyTrollTraderNPC(Class<? extends Area> area, int tileX, int tileY, int tileZ, TradeOffers offers) {
		super(area, tileX, tileY, tileZ, offers);
	}

	@Override
	protected AreaCreature createTrader() {
		return new TrollTrader(new SpawnPosition(tileX,tileY,tileZ), tradeOffers);
	}
}
