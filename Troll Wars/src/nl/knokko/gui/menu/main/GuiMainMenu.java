package nl.knokko.gui.menu.main;

import java.awt.Color;
import java.awt.Font;

import nl.knokko.gamestate.StateMainMenu;
import nl.knokko.gui.button.ButtonLink;
import nl.knokko.gui.component.menu.GuiMenu;
import nl.knokko.gui.component.text.TextButton;
import nl.knokko.gui.util.TextBuilder.HorAlignment;
import nl.knokko.gui.util.TextBuilder.Properties;
import nl.knokko.gui.util.TextBuilder.VerAlignment;
import nl.knokko.main.Game;

public class GuiMainMenu extends GuiMenu {
	
	public static final Font FONT = new Font("TimesRoman", 0, 30);
	
	public static final Properties BUTTON_PROPERTIES = new Properties(FONT, Color.BLACK, new Color(150, 0, 150), new Color(50, 0, 50), HorAlignment.MIDDLE, VerAlignment.MIDDLE, 0.05f, 0.1f, 0.05f, 0.1f);
	public static final Properties HOVER_BUTTON_PROPERTIES = new Properties(FONT, new Color(50, 50, 50), new Color(250, 0, 250), new Color(80, 0, 80), HorAlignment.MIDDLE, VerAlignment.MIDDLE, 0.05f, 0.1f, 0.05f, 0.1f);
	
	private final StateMainMenu menu;

	public GuiMainMenu(StateMainMenu menu) {
		this.menu = menu;
	}
	
	@Override
	protected void addComponents(){
		addComponent(new ButtonLink("new game", menu, menu.getGuiNewGame(), BUTTON_PROPERTIES, HOVER_BUTTON_PROPERTIES), 0.3f, 0.825f, 0.7f, 0.975f);
		addComponent(new ButtonLink("load game", menu, menu.getGuiLoadGame(), BUTTON_PROPERTIES, HOVER_BUTTON_PROPERTIES){
			
			@Override
			public void click(float x, float y, int button){
				super.click(x, y, button);
				((GuiLoadGame) menu.getGuiLoadGame()).refresh();
			}
		}, 0.3f, 0.625f, 0.7f, 0.775f);
		addComponent(new ButtonLink("options", menu, menu.getGuiOptions(), BUTTON_PROPERTIES, HOVER_BUTTON_PROPERTIES), 0.3f, 0.425f, 0.7f, 0.575f);
		addComponent(new ButtonLink("help menu", menu, menu.getGuiHelpMenu(), BUTTON_PROPERTIES, HOVER_BUTTON_PROPERTIES), 0.3f, 0.225f, 0.7f, 0.375f);
		addComponent(new TextButton("quit game", BUTTON_PROPERTIES, HOVER_BUTTON_PROPERTIES, new Runnable(){

			@Override
			public void run() {
				Game.stop(false);
			}
		}), 0.3f, 0.025f, 0.7f, 0.175f);
	}
}
