package nl.knokko.gui.menu.game;

import org.lwjgl.util.vector.Vector2f;

import nl.knokko.gamestate.StateGameMenu;
import nl.knokko.gui.button.ButtonCloseMenu;
import nl.knokko.gui.button.ButtonLink;
import nl.knokko.gui.component.GuiComponent;
import nl.knokko.gui.component.menu.GuiMenu;
import nl.knokko.gui.component.text.TextButton;
import nl.knokko.gui.util.TextBuilder.Properties;
import nl.knokko.main.Game;
import nl.knokko.players.Player;
import nl.knokko.util.color.Color;

public class GuiPlayerMenu extends GuiMenu {
	
	private static final Color BUTTON_COLOR = new Color(0, 150, 100);
	private static final Color BORDER_COLOR = new Color(0, 50, 40);
	
	private final StateGameMenu state;
	
	Player player;

	public GuiPlayerMenu(StateGameMenu state) {
		this.state = state;
		this.player = Game.getPlayers().gothrok;
	}
	
	@Override
	protected void addComponents(){
		addButton(new ButtonLink(new Vector2f(-0.65f, 0.85f), new Vector2f(0.3f, 0.1f), BUTTON_COLOR, BORDER_COLOR, Color.BLACK, "Equipment", state.getPlayerEquipment(), state));
		addButton(new ButtonSwapPlayer(new Vector2f(-0.65f, 0f), new Vector2f(0.3f, 0.1f), BUTTON_COLOR, BORDER_COLOR, this));
		addButton(new ButtonLink(new Vector2f(-0.65f, -0.6f), new Vector2f(0.3f, 0.1f), BUTTON_COLOR, BORDER_COLOR, Color.BLACK, "Back to menu", state.getGameMenu(), state));
		addButton(new ButtonCloseMenu(new Vector2f(-0.65f, -0.85f), new Vector2f(0.3f, 0.1f), BUTTON_COLOR, BORDER_COLOR));
	}
	
	void setPlayer(Player newPlayer){
		player = newPlayer;
	}
	
	void swapPlayer(){
		Player p = Game.getPlayers().nextPlayer(player);
		if(p != player)
			setPlayer(p);
	}
	
	public static class ButtonSwapPlayer extends TextButton {
		
		public ButtonSwapPlayer(Properties properties, Properties hoverProperties, GuiComponent playersMenu){
			super("Next Character", properties, hoverProperties, new ClickAction((GuiPlayerMenu) playersMenu));
		}
		
		private static class ClickAction implements Runnable {
			
			private final GuiPlayerMenu gui;
			
			private ClickAction(GuiPlayerMenu playersMenu){
				gui = playersMenu;
			}

			@Override
			public void run() {
				gui.swapPlayer();
			}
		}
	}
}
