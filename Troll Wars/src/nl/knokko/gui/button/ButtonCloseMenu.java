package nl.knokko.gui.button;

import nl.knokko.main.Game;
import nl.knokko.util.color.Color;

import org.lwjgl.util.vector.Vector2f;

public class ButtonCloseMenu extends ButtonText {

	public ButtonCloseMenu(Vector2f centre, Vector2f size, Color buttonColor, Color borderColor) {
		super(centre, size, buttonColor, borderColor, Color.BLACK, "Back to game");
	}

	@Override
	public void leftClick(float x, float y) {
		Game.removeState();
	}

}
