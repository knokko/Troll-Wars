package nl.knokko.gamestate;

import nl.knokko.gui.component.GuiComponent;
import nl.knokko.main.Game;

public class StateInteracting implements GameState {
	
	private GuiComponent gui;

	public StateInteracting() {}

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
		return true;
	}

	@Override
	public boolean updateTransparent() {
		return false;
	}

	@Override
	public void setCurrentGui(GuiComponent gui) {
		this.gui = gui;
		gui.setState(Game.getGuiState());
		gui.init();
	}
	
	@Override
	public GuiComponent getCurrentGui(){
		return gui;
	}

	@Override
	public void save() {}
}
