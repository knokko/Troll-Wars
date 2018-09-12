package nl.knokko.gui.button;

import nl.knokko.gamestate.GameState;
import nl.knokko.gui.component.GuiComponent;
import nl.knokko.gui.component.text.TextButton;
import nl.knokko.gui.util.TextBuilder.Properties;

public class ButtonLink extends TextButton {
	
	public ButtonLink(String text, GameState state, GuiComponent link, Properties properties, Properties hoverProperties){
		super(text, properties, hoverProperties, new ClickAction(link, state));
	}
	
	private static class ClickAction implements Runnable {
		
		private final GuiComponent link;
		private final GameState state;
		
		private ClickAction(GuiComponent link, GameState state){
			this.link = link;
			this.state = state;
		}

		@Override
		public void run() {
			state.setCurrentGui(link);
		}
	}
}