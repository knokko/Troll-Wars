package nl.knokko.gamestate;

import nl.knokko.gui.component.GuiComponent;
import nl.knokko.gui.menu.main.*;
import nl.knokko.main.Game;

public class StateMainMenu implements GameState {
	
	private final GuiComponent[] guis;
	
	private GuiComponent currentGui;

	public StateMainMenu() {
		guis = new GuiComponent[]{new GuiMainMenu(this), new GuiNewGame(this), new GuiLoadGame(this), new GuiMainOptions(this), new GuiMainHelp(this)};
		for(GuiComponent gui : guis){
			gui.setState(Game.getGuiState());
			gui.init();
		}
		currentGui = getGuiMainMenu();
	}

	@Override
	public void update() {
		
	}

	@Override
	public void render() {
		
	}

	@Override
	public void open() {}

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
	public void setCurrentGui(GuiComponent gui){
		currentGui = gui;
	}
	
	@Override
	public GuiComponent getCurrentGui(){
		return currentGui;
	}
	
	public GuiComponent getGuiMainMenu(){
		return guis[0];
	}
	
	public GuiComponent getGuiNewGame(){
		return guis[1];
	}
	
	public GuiComponent getGuiLoadGame(){
		return guis[2];
	}
	
	public GuiComponent getGuiOptions(){
		return guis[3];
	}
	
	public GuiComponent getGuiHelpMenu(){
		return guis[4];
	}

	@Override
	public void save() {}
}
