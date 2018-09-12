package nl.knokko.gui.menu.main;

import org.lwjgl.util.vector.Vector2f;

import nl.knokko.gamestate.StateMainMenu;
import nl.knokko.gui.GuiBase;
import nl.knokko.gui.button.ButtonLink;
import nl.knokko.gui.button.ButtonText;
import nl.knokko.main.Game;
import nl.knokko.util.color.Color;

public class GuiMainMenu extends GuiBase {
	
	public static final Color BUTTON_COLOR = new Color(150, 0, 150);
	public static final Color BORDER_COLOR = new Color(50, 0, 50);
	
	private final StateMainMenu menu;

	public GuiMainMenu(StateMainMenu menu) {
		this.menu = menu;
	}
	
	@Override
	public void addButtons(){
		addButton(new ButtonLink(new Vector2f(0f, 0.8f), new Vector2f(0.4f, 0.15f), BUTTON_COLOR, BORDER_COLOR, Color.BLACK, "new game", menu.getGuiNewGame(), menu));
		addButton(new ButtonLink(new Vector2f(0f, 0.4f), new Vector2f(0.4f, 0.15f), BUTTON_COLOR, BORDER_COLOR, Color.BLACK, "load game", menu.getGuiLoadGame(), menu){
			
			@Override
			public void leftClick(float x, float y){
				super.leftClick(x, y);
				link.addButtons();
			}
		});
		addButton(new ButtonLink(new Vector2f(0f, 0f), new Vector2f(0.4f, 0.15f), BUTTON_COLOR, BORDER_COLOR, Color.BLACK, "options", menu.getGuiOptions(), menu));
		addButton(new ButtonLink(new Vector2f(0f, -0.4f), new Vector2f(0.4f, 0.15f), BUTTON_COLOR, BORDER_COLOR, Color.BLACK, "help menu", menu.getGuiHelpMenu(), menu));
		addButton(new ButtonText(new Vector2f(0f, -0.8f), new Vector2f(0.4f, 0.15f), BUTTON_COLOR, BORDER_COLOR, Color.BLACK, "quit game"){

			@Override
			public void leftClick(float x, float y){
				Game.stop(false);
			}
		});
	}
}
