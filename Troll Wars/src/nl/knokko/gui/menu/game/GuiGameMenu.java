package nl.knokko.gui.menu.game;

import org.lwjgl.util.vector.Vector2f;

import nl.knokko.gamestate.StateGameMenu;
import nl.knokko.gui.GuiBase;
import nl.knokko.gui.button.ButtonCloseMenu;
import nl.knokko.gui.button.ButtonLink;
import nl.knokko.gui.button.ButtonText;
import nl.knokko.main.Game;
import nl.knokko.util.color.Color;

public class GuiGameMenu extends GuiBase {
	
	private static final Color BUTTON_COLOR = new Color(0, 50, 200);
	private static final Color BORDER_COLOR = new Color(0, 20, 50);
	
	private final StateGameMenu state;

	public GuiGameMenu(StateGameMenu menu) {
		state = menu;
	}
	
	@Override
	public void addButtons(){
		addButton(new ButtonCloseMenu(new Vector2f(0.65f, -0.6f), new Vector2f(0.3f, 0.1f), BUTTON_COLOR, BORDER_COLOR));
		addButton(new ButtonText(new Vector2f(0.65f, -0.85f), new Vector2f(0.3f, 0.1f), BUTTON_COLOR, BORDER_COLOR, Color.BLACK, "Save and stop"){

			@Override
			public void leftClick(float x, float y) {
				Game.stop(true);
			}
		});
		addButton(new ButtonLink(new Vector2f(-0.65f, 0.85f), new Vector2f(0.3f, 0.1f), BUTTON_COLOR, BORDER_COLOR, Color.BLACK, "Inventory", state.getInventory(), state));
		addButton(new ButtonLink(new Vector2f(-0.65f, 0.6f), new Vector2f(0.3f, 0.1f), BUTTON_COLOR, BORDER_COLOR, Color.BLACK, "Characters", state.getPlayersMenu(), state));
	}
	
	@Override
	public Color getBackGroundColor(){
		return null;
	}
}
