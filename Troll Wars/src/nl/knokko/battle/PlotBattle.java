package nl.knokko.battle;

import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.main.Game;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;

public class PlotBattle extends BattleDefault {
	
	public static final byte CLASS_ID = -127;
	
	protected int plotID;

	public PlotBattle(BattleCreature[] players, BattleCreature[] opponents, int plotID) {
		super(players, opponents);
		this.plotID = plotID;
	}
	
	@Override
	public byte getClassID(){
		return CLASS_ID;
	}
	
	@Override
	public boolean canRun(){
		return false;
	}
	
	@Override
	public void save(BitOutput output){
		super.save(output);
		output.addInt(plotID);
	}
	
	@Override
	public void load(BitInput input){
		super.load(input);
		plotID = input.readInt();
	}
	
	@Override
	protected void onVictory(){
		Game.getEventHandler().onPlotBattleVictory(plotID);
	}
	
	@Override
	protected void onDefeat(){
		Game.getEventHandler().onPlotBattleDefeat(plotID);
	}
}
