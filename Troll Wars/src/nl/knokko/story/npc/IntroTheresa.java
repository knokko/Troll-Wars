/*******************************************************************************
 * The MIT License
 *
 * Copyright (c) 2019 knokko
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
import nl.knokko.area.creature.AreaIntroTheresa;
import nl.knokko.equipment.EquipmentFull;
import nl.knokko.items.Items;
import nl.knokko.main.Game;
import nl.knokko.model.body.BodyHuman;
import nl.knokko.story.event.IntroHumanEvent;
import nl.knokko.texture.painter.HumanPainter;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;

public class IntroTheresa implements NPC {
	
	public static final short MAX_LEAVE_STATUS = 32 * (AreaIntroTheresa.START_X - AreaIntroTheresa.END_X + AreaIntroTheresa.START_Z - AreaIntroTheresa.END_Z);
	public static final short TURN_STATUS = 32 * (AreaIntroTheresa.START_Z - AreaIntroTheresa.END_Z);
	
	private short leaveStatus;
	
	private final BodyHuman body;
	private final HumanPainter painter;
	
	public IntroTheresa(BodyHuman body, HumanPainter painter){
		this.body = body;
		this.painter = painter;
	}

	@Override
	public Class<?>[] getPossibleAreas() {
		return IntroHumanNPC.AREA;
	}

	@Override
	public AreaCreature createRepresentation(Area area) {
		byte state = Game.getEventManager().introHuman().getState();
		if(leaveStatus <= MAX_LEAVE_STATUS && state == IntroHumanEvent.STATE_LEFT || state == IntroHumanEvent.STATE_NOT_MET || state == IntroHumanEvent.STATE_SHOW_MYRMORA)
			return new AreaIntroTheresa(EquipmentFull.createFullIron(Items.IRON_SPEAR), body, painter, 180);
		return null;
	}

	@Override
	public void save(BitOutput bits) {
		bits.addShort(leaveStatus);
	}

	@Override
	public void load(BitInput bits) {
		leaveStatus = bits.readShort();
	}

	@Override
	public void initFirstGame() {
		leaveStatus = -1;
	}
	
	public short getLeaveStatus(){
		return leaveStatus;
	}
	
	public void startLeaving(){
		leaveStatus = 0;
	}
	
	public void updateLeaveStatus(){
		if(leaveStatus >= 0 && leaveStatus <= MAX_LEAVE_STATUS)
			leaveStatus++;
	}
}