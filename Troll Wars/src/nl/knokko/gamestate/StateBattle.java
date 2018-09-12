package nl.knokko.gamestate;

import nl.knokko.battle.Battle;
import nl.knokko.battle.BattleDefault;
import nl.knokko.gui.Gui;
import nl.knokko.gui.battle.GuiBattle;
import nl.knokko.main.Game;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;
import nl.knokko.util.resources.Saver;

public class StateBattle implements GameState {
	
	private static final Class<?>[] CLASS_MAP = {BattleDefault.class};
	
	private Gui gui;
	private Battle battle;

	public StateBattle() {}

	@Override
	public void update() {
		if(battle != null){
			battle.update();
			gui.update();
		}
		else
			Game.removeState();
	}

	@Override
	public void render() {
		if(battle != null){
			battle.render();
			gui.render(Game.getGuiRenderer());
		}
	}

	@Override
	public void open() {
		if(battle == null)
			throw new IllegalStateException("There is no battle!");
	}

	@Override
	public void close() {}

	@Override
	public void enable() {}

	@Override
	public void disable() {}

	@Override
	public boolean renderTransparent() {
		return false;
	}

	@Override
	public boolean updateTransparent() {
		return false;
	}

	@Override
	public void setCurrentGui(Gui gui) {
		this.gui = gui;
		gui.addButtons();
	}
	
	public void save(){
		if(battle != null){
			BitOutput buffer = Saver.save("battle.data", 200);
			buffer.addByte(battle.getClassID());
			battle.save(buffer);
			//Saver.save(buffer, "battle.data");
		}
	}
	
	public void load(BitInput buffer){
		try {
			battle = (Battle) CLASS_MAP[buffer.readByte() + 128].newInstance();
			battle.load(buffer);
			gui = new GuiBattle(battle);
			gui.addButtons();
		} catch(Exception ex){
			throw new IllegalArgumentException("Couldn't load battle:", ex);
		}
	}
	
	public void setBattle(Battle battle){
		this.battle = battle;
		this.gui = new GuiBattle(battle);
		this.gui.addButtons();
	}
	
	public Battle getBattle(){
		return battle;
	}
	
	public void run(){
		if(battle.canRun()){
			battle = null;
			gui = null;
		}
	}
}
