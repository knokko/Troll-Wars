package nl.knokko.gui;

import java.util.ArrayList;

import nl.knokko.gui.button.IButton;
import nl.knokko.gui.texture.GuiTexture;
import nl.knokko.input.MouseClickEvent;
import nl.knokko.input.MouseInput;
import nl.knokko.render.main.GuiRenderer;
import nl.knokko.util.color.Color;

public class GuiBase implements Gui {
	
	protected ArrayList<IButton> buttons = new ArrayList<IButton>();
	protected ArrayList<GuiTexture> textures = new ArrayList<GuiTexture>();

	public GuiBase(){}

	@Override
	public void render(GuiRenderer renderer){
		renderer.render(textures, buttons, this, true);
	}

	@Override
	public void update(){
		ArrayList<MouseClickEvent> clicks = MouseInput.getMouseClicks();
		for(MouseClickEvent event : clicks)
			if(event.getButton() == 0)
				leftClick(event.getX(), event.getY());
		for(IButton button : buttons)
			button.update(this);
	}
	
	@Override
	public Color getBackGroundColor(){
		return Color.RED;
	}
	
	@Override
	public void addButtons(){}
	
	@Override
	public void open(){}
	
	protected void leftClick(float x, float y){
		for(IButton button : buttons)
			if(button.isHit(x, y))
				button.leftClick(x, y);
	}
	
	protected void addButton(IButton button){
		buttons.add(button);
	}
	
	protected void addTexture(GuiTexture texture){
		textures.add(texture);
	}
	
	protected void clearButtons(){
		buttons.clear();
	}
}
