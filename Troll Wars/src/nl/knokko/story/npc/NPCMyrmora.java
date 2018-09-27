/* 
 * The MIT License
 *
 * Copyright 2018 knokko.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
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
 */
package nl.knokko.story.npc;

import nl.knokko.area.Area;
import nl.knokko.area.creature.AreaCreature;
import nl.knokko.area.creature.AreaMyrmora;
import nl.knokko.areas.AreaSorgCave;
import nl.knokko.main.Game;
import nl.knokko.story.event.IntroHumanEvent;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;
import nl.knokko.util.position.SpawnPosition;

public class NPCMyrmora {

	public static class Intro implements NPC {
		
		private static final Class<?>[] AREA = {AreaSorgCave.A1.class};
		private static final SpawnPosition SPAWN = new SpawnPosition(50, 13, 48);

		@Override
		public Class<?>[] getPossibleAreas() {
			return AREA;
		}

		@Override
		public AreaCreature createRepresentation(Area area) {
			if(Game.getEventManager().introHuman().getState() == IntroHumanEvent.STATE_SHOW_MYRMORA)
				return new AreaMyrmora.Intro(SPAWN);
			return null;
		}

		@Override
		public void save(BitOutput bits) {}

		@Override
		public void load(BitInput bits) {}

		@Override
		public void initFirstGame() {}
		
	}
}