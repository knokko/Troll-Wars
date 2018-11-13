/*******************************************************************************
 * The MIT License
 *
 * Copyright (c) 2018 knokko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *  
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *******************************************************************************/
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
