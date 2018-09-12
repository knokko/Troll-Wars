package nl.knokko.gui.button;

import org.lwjgl.util.vector.Vector2f;

import nl.knokko.gui.Gui;
import nl.knokko.texture.Texture;

public interface IButton {
	
	Vector2f getCentre();
	Vector2f getSize();
	
	Texture[] getTextures();
	
	boolean isHit(float x, float y);
	
	void leftClick(float x, float y);
	
	void update(Gui gui);
}
