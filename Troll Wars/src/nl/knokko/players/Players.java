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
package nl.knokko.players;

import java.util.ArrayList;

import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.battle.creature.player.BattlePlayer;

public final class Players {
	
	private final Player[] players;
	
	public final PlayerGothrok gothrok;
	
	public Players(){
		gothrok = new PlayerGothrok();
		players = new Player[]{gothrok};
	}
	
	public Player fromName(String name){
		for(Player player : players)
			if(player.name().equals(name))
				return player;
		throw new IllegalArgumentException("There is no player with name '" + name + "'!");
	}
	
	public Player nextPlayer(Player current){
		int i = 0;
		for(Player player : players){
			if(player == current)
				break;
			i++;
		}
		for(int j = i + 1; j < players.length; j++)
			if(players[j].isAvailable())
				return players[j];
		for(int j = 0; j <= i; j++)
			if(players[j].isAvailable())
				return players[j];
		throw new RuntimeException("Can't find next player! current player is " + current + " and players.length is " + players.length);
	}
	
	public BattleCreature[] getBattlePlayers(){
		ArrayList<BattleCreature> team = new ArrayList<BattleCreature>(players.length);
		for(Player player : players){
			if(player.isAvailable()){
				team.add(BattlePlayer.getInstance(player));
				player.enterBattle();
			}
		}
		return team.toArray(new BattleCreature[team.size()]);
	}
	
	public void save() {
		for(Player player : players)
			player.save();
	}
	
	public void load() {
		for(Player player : players)
			player.load();
	}
	
	public void initFirstGame(){
		for(Player player : players)
			player.initialiseFirstGame();
	}
}
