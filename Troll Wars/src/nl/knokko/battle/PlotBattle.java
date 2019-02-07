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
package nl.knokko.battle;

import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.battle.decoration.BattleDecoration;
import nl.knokko.main.Game;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;

public class PlotBattle extends BattleDefault {
	
	public static final byte CLASS_ID = -127;
	
	protected int plotID;

	public PlotBattle(BattleDecoration decoration, BattleCreature[] players, BattleCreature[] opponents, int plotID) {
		super(decoration, players, opponents);
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
