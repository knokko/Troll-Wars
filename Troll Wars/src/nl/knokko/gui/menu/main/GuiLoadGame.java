package nl.knokko.gui.menu.main;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;

import nl.knokko.gamestate.StateMainMenu;
import nl.knokko.gui.GuiBase;
import nl.knokko.gui.button.ButtonLink;
import nl.knokko.gui.button.ButtonText;
import nl.knokko.gui.button.IButton;
import nl.knokko.input.MouseInput;
import nl.knokko.input.MouseScrollEvent;
import nl.knokko.main.Game;
import nl.knokko.render.main.GuiRenderer;
import nl.knokko.texture.Texture;
import nl.knokko.util.color.Color;
import nl.knokko.util.resources.Resources;
import nl.knokko.util.resources.Saver;
import nl.knokko.util.resources.Saver.SaveTime;

public class GuiLoadGame extends GuiBase {
	
	public static final Color BUTTON_COLOR = new Color(150, 0, 150);
	public static final Color BORDER_COLOR = new Color(50, 0, 50);
	
	private final StateMainMenu state;
	
	private ArrayList<ButtonSaveFile> savesButtons;
	private ArrayList<ButtonSaveTime> timesButtons;
	private ArrayList<IButton> saveOptionButtons;
	private ArrayList<IButton> timeOptionButtons;
	
	private ButtonSaveFile selectedSave;
	private ButtonSaveTime selectedTime;
	
	private Texture selectTexture = Resources.createBorderTexture(new Color(0, 0, 200), 0.3f, 0.1f, 5);
	
	private float saveScroll = 0f;
	private float timeScroll = 0f;

	public GuiLoadGame(StateMainMenu menu) {
		state = menu;
	}
	
	@Override
	public void addButtons(){
		saveOptionButtons = new ArrayList<IButton>();
		timeOptionButtons = new ArrayList<IButton>();
		savesButtons = new ArrayList<ButtonSaveFile>();
		timesButtons = new ArrayList<ButtonSaveTime>();
		addButton(new ButtonLink(new Vector2f(-0.65f ,0.85f), new Vector2f(0.3f, 0.1f), BUTTON_COLOR, BORDER_COLOR, Color.BLACK, "Back", state.getGuiMainMenu(), state));
		addSavesButtons();
	}
	
	@Override
	public void render(GuiRenderer renderer){
		renderer.start(this);
		renderer.render(textures, buttons, this, false);
		for(ButtonSaveFile but : savesButtons){
			float y = but.getCentre().y + saveScroll;
			if(y < 0.6f && y > -1.1f){
				renderer.renderTextures(new Vector2f(-0.6f, y), but.getSize(), but.getTextures());
				if(but == selectedSave)
					renderer.renderTextures(new Vector2f(-0.6f, y), but.getSize(), selectTexture);
			}
		}
		for(ButtonSaveTime but : timesButtons){
			float y = but.getCentre().y + timeScroll;
			if(y < 0.6f && y > -1.1f){
				renderer.renderTextures(new Vector2f(0f, y), but.getSize(), but.getTextures());
				if(but == selectedTime)
					renderer.renderTextures(new Vector2f(0f, y), but.getSize(), selectTexture);
			}
		}
		for(IButton button : saveOptionButtons)
			renderer.renderButtonTexture(button, button.getTextures(), this);
		for(IButton button : timeOptionButtons)
			renderer.renderButtonTexture(button, button.getTextures(), this);
		renderer.stop();
	}
	
	@Override
	public void update(){
		super.update();
		if(MouseInput.getY() < 0.7f){
			float x = MouseInput.getX();
			if(x <= 0.3f){
				ArrayList<MouseScrollEvent> scrolls = MouseInput.getMouseScrolls();
				if(x <= -0.3f){
					for(MouseScrollEvent scroll : scrolls)
						saveScroll += scroll.getDeltaScroll() * 0.001f;
					if(saveScroll < 0)
						saveScroll = 0;
				}
				else {
					for(MouseScrollEvent scroll : scrolls)
						timeScroll += scroll.getDeltaScroll() * 0.001f;
					if(timeScroll < 0)
						timeScroll = 0;
				}
			}
		}
	}
	
	@Override
	protected void leftClick(float x, float y){
		if(x <= -0.3f && y < 0.7f){
			for(ButtonSaveFile button : savesButtons)
				if(button.isHit(x, y))
					button.leftClick(x, y);
		}
		else if(x <= 0.3f && y < 0.7f){
			for(ButtonSaveTime button : timesButtons)
				if(button.isHit(x, y))
					button.leftClick(x, y);
		}
		else {
			super.leftClick(x, y);
			for(int i = 0; i < saveOptionButtons.size(); i++)
				if(saveOptionButtons.get(i).isHit(x, y))
					saveOptionButtons.get(i).leftClick(x, y);
			for(int i = 0; i < timeOptionButtons.size(); i++)
				if(timeOptionButtons.get(i).isHit(x, y))
					timeOptionButtons.get(i).leftClick(x, y);
		}
	}
	
	private void addSavesButtons(){
		timesButtons.clear();
		selectedSave = null;
		saveOptionButtons.clear();
		timeOptionButtons.clear();
		savesButtons.clear();
		String[] saves = Saver.getSaveFiles();
		for(int i = 0; i < saves.length; i++)
			savesButtons.add(new ButtonSaveFile(new Vector2f(-0.6f, 0.55f - i * 0.25f), saves[i]));
	}
	
	private void setSaveTimes(SaveTime[] times){
		timesButtons.clear();
		for(int i = 0; i < times.length; i++)
			timesButtons.add(new ButtonSaveTime(new Vector2f(0f, 0.55f - i * 0.25f), times[i].getText(), times[i].getMilliTime()));
	}
	
	private static String recoverName(String save){
		String or = "";
		if(save.length() == 1)
			return "";
		String[] split = save.substring(1).split("k");
		try {
			for(String s : split)
				or += (char) Integer.parseInt(replace(s));
			return or;
		} catch(Exception ex){
			System.out.println("Could not recover string '" + save + "':");
			ex.printStackTrace();
			return "Failed";
		}
	}
	
	public static String replace(String s){
		s = s.replace('a', '0');
		s = s.replace('b', '1');
		s = s.replace('c', '2');
		s = s.replace('d', '3');
		s = s.replace('e', '4');
		s = s.replace('f', '5');
		s = s.replace('g', '6');
		s = s.replace('h', '7');
		s = s.replace('i', '8');
		s = s.replace('j', '9');
		return s;
	}
	
	private class ButtonSaveFile extends ButtonText {
		
		private final String save;

		public ButtonSaveFile(Vector2f centre, String save) {
			super(centre, new Vector2f(0.3f, 0.1f), BUTTON_COLOR, BORDER_COLOR, Color.BLACK, recoverName(save));
			this.save = save;
		}

		@Override
		public void leftClick(float x, float y) {
			selectedSave = this;
			saveOptionButtons.clear();
			saveOptionButtons.add(new ButtonText(new Vector2f(0f, 0.85f), new Vector2f(0.2f, 0.1f), BUTTON_COLOR, BORDER_COLOR, Color.BLACK, "open"){

				@Override
				public void leftClick(float x, float y) {
					setSaveTimes(Saver.getSaveTimes(save));
				}
			});
			saveOptionButtons.add(new ButtonText(new Vector2f(0.65f, 0.85f), new Vector2f(0.3f, 0.1f), BUTTON_COLOR, BORDER_COLOR, Color.RED, "delete"){

				@Override
				public void leftClick(float x, float y) {
					Saver.deleteSaveFile(save);
					addSavesButtons();
				}
			});
		}
		
		@Override
		public boolean isHit(float x, float y){
			return super.isHit(x, y - saveScroll);
		}
		
	}
	
	private class ButtonSaveTime extends ButtonText {
		
		private final long time;

		public ButtonSaveTime(Vector2f centre, String text, long time) {
			super(centre, new Vector2f(0.3f, 0.1f), BUTTON_COLOR, BORDER_COLOR, Color.BLACK, text);
			this.time = time;
		}

		@Override
		public void leftClick(float x, float y) {
			selectedTime = this;
			timeOptionButtons.clear();
			timeOptionButtons.add(new ButtonText(new Vector2f(0.65f, 0.55f), new Vector2f(0.3f, 0.1f), BUTTON_COLOR, BORDER_COLOR, Color.BLACK, "load"){

				@Override
				public void leftClick(float x, float y) {
					Game.loadGame(recoverName(selectedSave.save), time);
				}
			});
			timeOptionButtons.add(new ButtonText(new Vector2f(0.65f, 0.3f), new Vector2f(0.3f, 0.1f), BUTTON_COLOR, BORDER_COLOR, Color.RED, "delete"){

				@Override
				public void leftClick(float x, float y) {
					Saver.deleteSaveTime(selectedSave.save, time);
					setSaveTimes(Saver.getSaveTimes(selectedSave.save));
					timeOptionButtons.clear();
				}
			});
			timeOptionButtons.add(new ButtonText(new Vector2f(0.65f, 0.05f), new Vector2f(0.3f, 0.1f), BUTTON_COLOR, BORDER_COLOR, Color.BLACK, "delete older"){

				@Override
				public void leftClick(float x, float y) {
					for(ButtonSaveTime but : timesButtons){
						if(but.time < time)
							Saver.deleteSaveTime(selectedSave.save, but.time);
					}
					setSaveTimes(Saver.getSaveTimes(selectedSave.save));
					timeOptionButtons.clear();
				}
			});
		}
		
		@Override
		public boolean isHit(float x, float y){
			return super.isHit(x, y - timeScroll);
		}
		
	}
}
