package nl.knokko.battle;

public enum BattleState {
	
	WAITING_FOR_PLAYER,
	UPDATING_MOVE,
	STARTING,
	VICTORY,
	DEFEAT;
	
	public static final byte BIT_COUNT = 3;
	
	public static BattleState fromID(long id){
		return values()[(int)id];
	}
	
	public int getID(){
		return ordinal();
	}
}