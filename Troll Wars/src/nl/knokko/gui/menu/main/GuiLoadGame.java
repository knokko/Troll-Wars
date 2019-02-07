/*******************************************************************************
 * The MIT License
 *
 * Copyright (c) 2018 knokko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *  
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *******************************************************************************/
package nl.knokko.gui.menu.main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import nl.knokko.gamestate.StateMainMenu;
import nl.knokko.gui.button.ButtonLink;
import nl.knokko.gui.color.GuiColor;
import nl.knokko.gui.color.SimpleGuiColor;
import nl.knokko.gui.component.menu.GuiMenu;
import nl.knokko.gui.component.text.ActivatableTextButton;
import nl.knokko.gui.component.text.TextButton;
import nl.knokko.gui.render.GuiRenderer;
import nl.knokko.gui.util.Condition;
import nl.knokko.gui.util.TextBuilder.Properties;
import nl.knokko.input.MouseInput;
import nl.knokko.input.MouseScrollEvent;
import nl.knokko.main.Game;
import nl.knokko.util.resources.Saver;
import nl.knokko.util.resources.Saver.SaveTime;

public class GuiLoadGame extends GuiMenu {
	
	private static final Properties BUTTON_PROPS = Properties.createButton(new Color(150, 0, 150), new Color(50, 0, 50));
	private static final Properties HOVER_PROPS = Properties.createButton(new Color(250, 0, 250), new Color(80, 0, 80), new Color(50, 50, 50));
	private static final Properties SELECTED_PROPS = Properties.createButton(new Color(250, 0, 250), new Color(255, 100, 255), new Color(0, 50, 50));
	
	private static final GuiColor BACKGROUND = new SimpleGuiColor(0, 0, 150);
	
	private final StateMainMenu state;
	
	private SavesButtons savesButtons;
	private TimesButtons timesButtons;
	
	private class SavesButtons extends GuiMenu {

		@Override
		protected void addComponents() {
			String[] saves = Saver.getSaveFiles();
			for(int index = 0; index < saves.length; index++)
				addComponent(new ButtonSaveFile(saves[index]), 0f, 0.725f - index * 0.125f, 1f, 0.825f - index * 0.125f);
		}
		
		@Override
		public GuiColor getBackgroundColor() {
			return BACKGROUND;
		}
		
		protected void refresh(){
			clearComponents();
			addComponents();
		}
	}
	
	private class TimesButtons extends GuiMenu {

		@Override
		protected void addComponents() {}
		
		@Override
		public GuiColor getBackgroundColor() {
			return BACKGROUND;
		}
		
		protected void refresh(){
			clearComponents();
			if(selectedSave != null){
				SaveTime[] saves = Saver.getSaveTimes(selectedSave);
				for(int index = 0; index < saves.length; index++)
					addComponent(new ButtonSaveTime(saves[index].getText(), saves[index].getMilliTime()), 0f, 0.725f - index * 0.125f, 1f, 0.825f - index * 0.125f);
			}
		}
	}
	
	private String selectedSave;
	private long selectedTime;
	
	private float saveScroll = 0f;
	private float timeScroll = 0f;

	public GuiLoadGame(StateMainMenu menu) {
		state = menu;
	}
	
	@Override
	protected void addComponents(){
		savesButtons = new SavesButtons();
		timesButtons = new TimesButtons();
		addComponent(timesButtons, 0.35f, 0f, 0.65f, 0.8f);
		addComponent(savesButtons, 0.025f, 0f, 0.325f, 0.8f);
		addComponent(new ButtonSaveFileOption("delete", new DeleteSaveFileAction()), 0.675f, 0.875f, 0.975f, 0.975f);
		addComponent(new ButtonSaveTimeOption("load", new LoadSaveTimeAction(), BUTTON_PROPS, HOVER_PROPS), 0.675f, 0.725f, 0.975f, 0.825f);
		addComponent(new ButtonSaveTimeOption("delete", new DeleteSaveTimeAction(), BUTTON_PROPS, HOVER_PROPS), 0.675f, 0.6f, 0.975f, 0.7f);
		addComponent(new ButtonSaveTimeOption("delete older", new DeleteOlderTimeAction(), BUTTON_PROPS, HOVER_PROPS), 0.675f, 0.475f, 0.975f, 0.575f);
		addComponent(new ButtonLink("back", state, state.getGuiMainMenu(), BUTTON_PROPS, HOVER_PROPS), 0.025f, 0.875f, 0.325f, 0.975f);
		addSavesButtons();
	}
	
	@Override
	public GuiColor getBackgroundColor() {
		return BACKGROUND;
	}
	
	public void refresh(){
		addSavesButtons();
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
	
	private void addSavesButtons(){
		selectedSave = null;
		savesButtons.refresh();
		timesButtons.refresh();
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
	
	private class ButtonSaveFile extends ActivatableTextButton {

		public ButtonSaveFile(final String save) {
			super(recoverName(save), BUTTON_PROPS, HOVER_PROPS, SELECTED_PROPS, new Runnable(){

				@Override
				public void run() {
					selectedSave = save;
					timesButtons.refresh();
				}
			}, new Condition() {

				@Override
				public boolean isTrue() {
					return save.equals(selectedSave);
				}
			});
		}
	}
	
	private class ButtonSaveFileOption extends TextButton {

		public ButtonSaveFileOption(String text, Runnable action) {
			super(text, BUTTON_PROPS, HOVER_PROPS, new SaveFileAction(action));
		}
		
		@Override
		public void render(GuiRenderer renderer){
			if(selectedSave != null)
				super.render(renderer);
		}
	}
	
	private class SaveFileAction implements Runnable {
		
		private final Runnable action;
		
		private SaveFileAction(Runnable action){
			this.action = action;
		}

		@Override
		public void run() {
			if(selectedSave != null)
				action.run();
		}
	}
	
	private class DeleteSaveFileAction implements Runnable {

		@Override
		public void run() {
			Saver.deleteSaveFile(selectedSave);
			addSavesButtons();
		}
	}
	
	private class ButtonSaveTime extends ActivatableTextButton {
		
		private final long time;

		public ButtonSaveTime(String text, final long time) {
			super(text, BUTTON_PROPS, HOVER_PROPS, SELECTED_PROPS, new Runnable(){

				@Override
				public void run() {
					selectedTime = time;
				}
			}, new Condition() {

				@Override
				public boolean isTrue() {
					return selectedTime == time;
				}
			});
			this.time = time;
		}
	}
	
	private class ButtonSaveTimeOption extends TextButton {
		
		public ButtonSaveTimeOption(String text, Runnable action, Properties properties, Properties hoverProperties) {
			super(text, properties, hoverProperties, new SaveTimeAction(action));
		}
		
		@Override
		public void render(GuiRenderer renderer){
			if(selectedTime != 0)
				super.render(renderer);
		}
	}
	
	private class SaveTimeAction implements Runnable {
		
		private final Runnable action;
		
		private SaveTimeAction(Runnable action){
			this.action = action;
		}

		@Override
		public void run() {
			if(selectedTime != 0)
				action.run();
		}
		
	}
	
	private class LoadSaveTimeAction implements Runnable {

		@Override
		public void run() {
			Game.loadGame(recoverName(selectedSave), selectedTime);
		}
	}
	
	private class DeleteSaveTimeAction implements Runnable {

		@Override
		public void run() {
			Saver.deleteSaveTime(selectedSave, selectedTime);
			selectedTime = 0;
			timesButtons.refresh();
		}
	}
	
	private class DeleteOlderTimeAction implements Runnable {

		@Override
		public void run() {
			List<SubComponent> timeComponents = timesButtons.getComponents();
			for(SubComponent component : timeComponents){
				long time = ((ButtonSaveTime)component.getComponent()).time;
				if(time < selectedTime)
					Saver.deleteSaveTime(selectedSave, time);
			}
			timesButtons.refresh();
		}
	}
}