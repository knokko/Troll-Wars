package nl.knokko.gamestate;

import nl.knokko.gui.Gui;
import nl.knokko.main.Game;

public class StateInteracting implements GameState {
	
	private Gui gui;

	public StateInteracting() {}

	@Override
	public void update() {
		gui.update();
	}

	@Override
	public void render() {
		gui.render(Game.getGuiRenderer());
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
	public void setCurrentGui(Gui gui) {
		this.gui = gui;
		gui.addButtons();
	}

	@Override
	public void save() {}
}
