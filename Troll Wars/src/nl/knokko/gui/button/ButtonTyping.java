package nl.knokko.gui.button;

import java.util.ArrayList;

import nl.knokko.gui.Gui;
import nl.knokko.input.KeyInput;
import nl.knokko.input.KeyPressedEvent;
import nl.knokko.texture.Texture;
import nl.knokko.util.color.Color;
import nl.knokko.util.resources.Resources;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

public class ButtonTyping extends ButtonBase {
	
	private String text;
	
	private int maxLength;
	
	private boolean enabled;

	public ButtonTyping(Vector2f centre, Vector2f size, Color buttonColor, Color borderColor, int maxLength) {
		super(centre, size, buttonColor, borderColor);
		text = "";
		this.maxLength = maxLength;
	}

	@Override
	public void leftClick(float x, float y) {
		enable();
	}
	
	@Override
	public void update(Gui gui){
		String prev = text;
		if(isEnabled()){
			ArrayList<KeyPressedEvent> presses = KeyInput.getCurrentPresses();
			for(KeyPressedEvent event : presses){
				if(event.getKey() == Keyboard.KEY_CLEAR){
					text = "";
				}
				else if(event.getKey() == Keyboard.KEY_BACK){
					if(text.length() > 0)
						text = text.substring(0, text.length() - 1);
				}
				else if(text.length() < maxLength){
					char c = event.getCharacter();
					text += c;
				}
			}
		}
		if(text != prev)
			textures = new Texture[]{texture, Resources.getTextTexture(text, Color.BLACK)};
	}
	
	public void enable(){
		enabled = true;
	}
	
	public void disable(){
		enabled = false;
	}
	
	public boolean isEnabled(){
		return enabled;
	}
	
	public String getText(){
		return text;
	}
	
	public void setText(String newText){
		text = newText;
		textures = new Texture[]{texture, Resources.getTextTexture(text, Color.BLACK)};
	}
}
