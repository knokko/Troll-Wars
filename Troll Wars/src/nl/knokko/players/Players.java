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
