package nl.knokko.gui.menu.main;

import org.lwjgl.util.vector.Vector2f;

import nl.knokko.gamestate.StateMainMenu;
import nl.knokko.gui.GuiBase;
import nl.knokko.gui.button.ButtonLink;
import nl.knokko.gui.button.ButtonText;
import nl.knokko.gui.button.ButtonTyping;
import nl.knokko.gui.texture.GuiTexture;
import nl.knokko.main.Game;
import nl.knokko.util.color.Color;
import nl.knokko.util.resources.Resources;
import nl.knokko.util.resources.Saver;

public class GuiNewGame extends GuiBase {
	
	public static final Color BUTTON_COLOR = new Color(0, 0, 200);
	public static final Color BORDER_COLOR = new Color(0, 0, 100);
	
	private final StateMainMenu menu;
	private ButtonTyping nameButton;
	private GuiTexture errorText;
	
	public GuiNewGame(StateMainMenu menu){
		this.menu = menu;
	}
	
	@Override
	public void addButtons(){
		addButton(new ButtonLink(new Vector2f(0f, 0.4f), new Vector2f(0.3f, 0.1f), BUTTON_COLOR, BORDER_COLOR, Color.BLACK, "back", menu.getGuiMainMenu(), menu));
		nameButton = new ButtonTyping(new Vector2f(0f, 0f), new Vector2f(0.8f, 0.2f), new Color(200, 200, 200), Color.BLACK, 30);
		addButton(nameButton);
		addButton(new ButtonText(new Vector2f(0f, -0.4f), new Vector2f(0.4f, 0.15f), BUTTON_COLOR, BORDER_COLOR, Color.BLACK, "start the game"){

			@Override
			public void leftClick(float x, float y) {
				String result = Saver.validateSaveName(nameButton.getText());
				if(result == null)
					Game.startNewGame(nameButton.getText());
				else {
					errorText = new GuiTexture(new Vector2f(0f, -0.8f), new Vector2f(0.95f, 0.15f), Resources.getTextTexture(result, Color.BLACK));
					GuiNewGame.this.textures.clear();
					GuiNewGame.this.textures.add(errorText);
				}
			}
		});
	}
}
