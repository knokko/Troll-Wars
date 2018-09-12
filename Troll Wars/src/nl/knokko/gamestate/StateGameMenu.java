package nl.knokko.gamestate;

import nl.knokko.gui.component.GuiComponent;
import nl.knokko.gui.keycode.KeyCode;
import nl.knokko.gui.menu.game.*;
import nl.knokko.main.Game;

public class StateGameMenu implements GameState {
	
	private final GuiComponent[] guis;
	
	private GuiComponent currentGui;

	public StateGameMenu() {
		guis = new GuiComponent[]{new GuiGameMenu(this), new GuiInventory(this), new GuiPlayerMenu(this), new GuiPlayerEquipment(this)};
		for(GuiComponent gui : guis){
			gui.setState(Game.getGuiState());
			gui.init();
		}
		setCurrentGui(getGameMenu());
	}

	@Override
	public void update() {
		//currentGui.update();
		if(Game.getWindow().getInput().isKeyDown(KeyCode.KEY_ESCAPE))
			Game.removeState();
	}

	@Override
	public void render() {
		//currentGui.render(Game.getGuiRenderer());
	}

	@Override
	public void open() {
		setCurrentGui(getGameMenu());
	}

	@Override
	public void close() {}

	@Override
	public void enable() {}

	@Override
	public void disable() {}

	@Override
	public boolean renderTransparent() {
		return true;
	}

	@Override
	public boolean updateTransparent() {
		return true;
	}

	@Override
	public void setCurrentGui(GuiComponent gui) {
		currentGui = gui;
	}
	
	@Override
	public GuiComponent getCurrentGui(){
		return currentGui;
	}
	
	public GuiComponent getGameMenu(){
		return guis[0];
	}
	
	public GuiComponent getInventory(){
		return guis[1];
	}
	
	public GuiPlayerMenu getPlayersMenu(){
		return (GuiPlayerMenu) guis[2];
	}
	
	public GuiComponent getPlayerEquipment(){
		return guis[3];
	}

	@Override
	public void save() {}
}
