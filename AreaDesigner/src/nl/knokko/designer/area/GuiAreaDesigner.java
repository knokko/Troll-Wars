package nl.knokko.designer.area;

import org.lwjgl.util.vector.Vector2f;

import nl.knokko.gui.GuiBase;
import nl.knokko.gui.texture.GuiTexture;
import nl.knokko.texture.Texture;
import nl.knokko.util.color.Color;
import nl.knokko.util.resources.Resources;

public class GuiAreaDesigner extends GuiBase {

	public GuiAreaDesigner() {
		addTexture(new GuiTexture(new Vector2f(0f, 0.9f), new Vector2f(1f, 0.1f), Resources.createFilledTexture(Color.BLUE_PURPLE)));
		addTexture(new GuiTexture(new Vector2f(0.5f, 0.9f), new Vector2f(0.2f, 0.08f), null){
			
			@Override
			public Texture[] getTextures(){
				return new Texture[]{AreaDesigner.getTileNameTexture()};
			}
		});
		addTexture(new GuiTexture(new Vector2f(-0.4f, 0.9f), new Vector2f(0.35f, 0.08f), null){
			
			@Override
			public Texture[] getTextures(){
				return new Texture[]{Resources.getTextTexture("position:[" + AreaDesigner.getTargetX() + "," + AreaDesigner.getTargetY() + "," + AreaDesigner.getTargetZ() + "]", Color.BLACK)};
			}
		});
	}
	
	@Override
	public Color getBackGroundColor(){
		return null;
	}
}
