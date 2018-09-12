package nl.knokko.inventory.trading;

import java.util.ArrayList;
import java.util.List;

import nl.knokko.inventory.Inventory;
import nl.knokko.items.Item;

public class TradeOffers {
	
	private TradeOffer[] offers;

	public TradeOffers(TradeOffer...offers) {
		this.offers = offers;
	}
	
	public List<TradeOffer> getAvailableOffers(Inventory inventory){
		List<TradeOffer> list = new ArrayList<TradeOffer>();
		for(TradeOffer offer : offers)
			if(offer.canPay(inventory))
				list.add(offer);
		return list;
	}
	
	public List<Item> getUsefulItems(List<Item> availableItems){
		List<Item> items = new ArrayList<Item>();
		for(Item item : availableItems){
			for(TradeOffer offer : offers){
				if(offer.needItem(item)){
					items.add(item);
					break;
				}
			}
		}
		return items;
	}
	
	public TradeOffer[] getAllOffers(){
		return offers;
	}
}
