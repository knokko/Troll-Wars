package nl.knokko.gamestate;

import org.lwjgl.input.Keyboard;

import nl.knokko.gui.Gui;
import nl.knokko.gui.menu.game.*;
import nl.knokko.input.KeyInput;
import nl.knokko.main.Game;

public class StateGameMenu implements GameState {
	
	private final Gui[] guis;
	
	private Gui currentGui;

	public StateGameMenu() {
		guis = new Gui[]{new GuiGameMenu(this), new GuiInventory(this), new GuiPlayerMenu(this), new GuiPlayerEquipment(this)};
		for(Gui gui : guis)
			gui.addButtons();
		setCurrentGui(getGameMenu());
	}

	@Override
	public void update() {
		currentGui.update();
		if(KeyInput.wasKeyPressed(Keyboard.KEY_ESCAPE))
			Game.removeState();
	}

	@Override
	public void render() {
		currentGui.render(Game.getGuiRenderer());
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
	public void setCurrentGui(Gui gui) {
		currentGui = gui;
		if(currentGui != null)
			currentGui.open();
	}
	
	public Gui getGameMenu(){
		return guis[0];
	}
	
	public Gui getInventory(){
		return guis[1];
	}
	
	public GuiPlayerMenu getPlayersMenu(){
		return (GuiPlayerMenu) guis[2];
	}
	
	public Gui getPlayerEquipment(){
		return guis[3];
	}

	@Override
	public void save() {}
}
