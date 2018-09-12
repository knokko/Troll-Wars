package nl.knokko.area.creature;

import nl.knokko.inventory.trading.TradeOffers;
import nl.knokko.main.Game;
import nl.knokko.util.position.SpawnPosition;

public class TrollTrader extends AreaTroll {
	
	private final TradeOffers offers;

	public TrollTrader(SpawnPosition spawn, TradeOffers offers) {
		super(spawn);
		this.offers = offers;
	}

	@Override
	public void interact() {
		Game.setTrading(offers);
	}

}
