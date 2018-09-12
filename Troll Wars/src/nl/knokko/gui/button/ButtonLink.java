package nl.knokko.gui.button;

import nl.knokko.gamestate.GameState;
import nl.knokko.gui.Gui;
import nl.knokko.util.color.Color;

import org.lwjgl.util.vector.Vector2f;

public class ButtonLink extends ButtonText {
	
	protected Gui link;
	private GameState state;

	public ButtonLink(Vector2f centre, Vector2f size, Color buttonColor, Color borderColor, Color textColor, String text, Gui link, GameState state) {
		super(centre, size, buttonColor, borderColor, textColor, text);
		this.link = link;
		this.state = state;
	}

	@Override
	public void leftClick(float x, float y) {
		state.setCurrentGui(link);
	}
}
