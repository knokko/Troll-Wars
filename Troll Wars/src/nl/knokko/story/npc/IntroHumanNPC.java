package nl.knokko.story.npc;

import nl.knokko.area.Area;
import nl.knokko.area.creature.AreaCreature;
import nl.knokko.area.creature.AreaSimpleHuman;
import nl.knokko.areas.AreaSorgCave;
import nl.knokko.equipment.EquipmentFull;
import nl.knokko.items.ItemWeapon;
import nl.knokko.items.Items;
import nl.knokko.main.Game;
import nl.knokko.model.body.BodyHuman;
import nl.knokko.texture.painter.HumanPainter;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;
import nl.knokko.util.position.SpawnPosition;

public class IntroHumanNPC implements NPC {
	
	static final Class<?>[] AREA = {AreaSorgCave.A1.class};
	
	private final SpawnPosition position;
	private final BodyHuman body;
	private final HumanPainter painter;
	private final ItemWeapon weapon;

	public IntroHumanNPC(SpawnPosition position, BodyHuman body, HumanPainter painter, ItemWeapon weapon) {
		this.position = position;
		this.body = body;
		this.painter = painter;
		this.weapon = weapon;
	}

	@Override
	public Class<?>[] getPossibleAreas() {
		return AREA;
	}

	@Override
	public AreaCreature createRepresentation(Area area) {
		if(!Game.getEventManager().introHuman().isOver()){
			if(weapon == Items.BLESSED_IRON_SWORD)
				return new AreaSimpleHuman(position, EquipmentFull.createFullBlessedIron(weapon), body, painter, 180);
			return new AreaSimpleHuman(position, EquipmentFull.createFullIron(weapon), body, painter, 180);
		}
		return null;
	}

	@Override
	public void save(BitOutput bits) {}//uses event data rather than own data

	@Override
	public void load(BitInput bits) {}

	@Override
	public void initFirstGame() {}
	
	public BodyHuman getBody(){
		return body;
	}
	
	public HumanPainter getPainter(){
		return painter;
	}
	
	public ItemWeapon getWeapon(){
		return weapon;
	}
	
	public int getLevel(){
		return weapon == Items.BLESSED_IRON_SWORD ? 25 : 20;
	}
}