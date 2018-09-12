package nl.knokko.gamestate;

import nl.knokko.gui.Gui;
import nl.knokko.gui.menu.main.*;
import nl.knokko.main.Game;

public class StateMainMenu implements GameState {
	
	private final Gui[] guis;
	
	private Gui currentGui;

	public StateMainMenu() {
		guis = new Gui[]{new GuiMainMenu(this), new GuiNewGame(this), new GuiLoadGame(this), new GuiMainOptions(this), new GuiMainHelp(this)};
		for(Gui gui : guis)
			gui.addButtons();
		currentGui = getGuiMainMenu();
	}

	@Override
	public void update() {
		currentGui.update();
	}

	@Override
	public void render() {
		currentGui.render(Game.getGuiRenderer());
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
	public void setCurrentGui(Gui gui){
		currentGui = gui;
	}
	
	public Gui getGuiMainMenu(){
		return guis[0];
	}
	
	public Gui getGuiNewGame(){
		return guis[1];
	}
	
	public Gui getGuiLoadGame(){
		return guis[2];
	}
	
	public Gui getGuiOptions(){
		return guis[3];
	}
	
	public Gui getGuiHelpMenu(){
		return guis[4];
	}

	@Override
	public void save() {}
}
