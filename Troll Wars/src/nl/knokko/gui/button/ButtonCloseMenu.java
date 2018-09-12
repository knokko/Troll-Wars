package nl.knokko.gui.button;

import nl.knokko.gui.component.text.TextButton;
import nl.knokko.gui.util.TextBuilder.Properties;
import nl.knokko.main.Game;

public class ButtonCloseMenu extends TextButton {
	
	private static final Runnable CLICK_ACTION = new Runnable(){

		@Override
		public void run() {
			Game.removeState();
		}
	};
	
	public ButtonCloseMenu(Properties properties, Properties hoverProperties){
		super("Back to game", properties, hoverProperties, CLICK_ACTION);
	}
}