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