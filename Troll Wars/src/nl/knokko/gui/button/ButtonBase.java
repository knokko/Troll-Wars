package nl.knokko.gui.button;

import nl.knokko.gui.Gui;
import nl.knokko.texture.Texture;
import nl.knokko.util.color.Color;
import nl.knokko.util.resources.Resources;

import org.lwjgl.util.vector.Vector2f;

public abstract class ButtonBase implements IButton {
	
	protected Vector2f centre;
	protected Vector2f size;
	
	protected Texture texture;
	protected Texture[] textures;

	public ButtonBase(Vector2f centre, Vector2f size, Color buttonColor, Color borderColor) {
		this.centre = centre;
		this.size = size;
		this.texture = Resources.createButtonTexture(size, buttonColor, borderColor);
		this.textures = new Texture[]{texture};
	}

	@Override
	public Vector2f getCentre() {
		return centre;
	}

	@Override
	public Vector2f getSize() {
		return size;
	}

	@Override
	public boolean isHit(float x, float y) {
		return x >= getCentre().x - size.x && x <= getCentre().x + size.x && y >= getCentre().y - size.y && y <= getCentre().y + size.y;
	}
	
	@Override
	public Texture[] getTextures(){
		return textures;
	}
	
	@Override
	public void update(Gui gui){}
}
