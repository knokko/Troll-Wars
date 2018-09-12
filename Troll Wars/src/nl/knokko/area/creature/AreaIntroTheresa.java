package nl.knokko.area.creature;

import nl.knokko.equipment.EquipmentFull;
import nl.knokko.main.Game;
import nl.knokko.model.body.BodyHuman;
import nl.knokko.story.npc.IntroTheresa;
import nl.knokko.texture.painter.HumanPainter;
import nl.knokko.util.position.SpawnPosition;

public class AreaIntroTheresa extends AreaSimpleHuman {
	
	private static int getX(){
		return getX(Game.getNPCManager().getIntroTheresa().getLeaveStatus());
	}
	
	private static int getX(short status){
		if(status == -1)
			return START_X;
		if(status <= IntroTheresa.TURN_STATUS)
			return START_X;
		status -= IntroTheresa.TURN_STATUS;
		status /= 32;
		return START_X - status;
	}
	
	private static int getZ(){
		return getZ(Game.getNPCManager().getIntroTheresa().getLeaveStatus());
	}
	
	private static int getZ(short status){
		if(status == -1)
			return START_Z;
		if(status >= IntroTheresa.TURN_STATUS)
			return END_Z;
		status /= 32;
		return START_Z - status;
	}
	
	public static final int START_X = 51;
	public static final int END_X = 37;
	public static final int Y = 13;
	public static final int START_Z = 46;
	public static final int END_Z = 35;

	public AreaIntroTheresa(EquipmentFull equipment, BodyHuman body, HumanPainter colors, float rotationY) {
		super(new SpawnPosition(getX(), Y, getZ()), equipment, body, colors, rotationY);
	}
	
	@Override
	protected void updateCreature(){
		Game.getNPCManager().getIntroTheresa().updateLeaveStatus();
		short status = Game.getNPCManager().getIntroTheresa().getLeaveStatus();
		if(status <= IntroTheresa.MAX_LEAVE_STATUS){
			getPosition().setPosition(getX(status), Y, getZ(status));
			animator.update((status - status / 32 * 32) / 32f, true, true);
		}
		else {
			Game.getNPCManager().refresh();
		}
		//this.animator.update(moveProgress, updateArms, updateLegs);
	}//TODO test this?
}