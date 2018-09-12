package nl.knokko.render.main;

import java.util.ArrayList;

import nl.knokko.gui.Gui;
import nl.knokko.gui.ScrollGui;
import nl.knokko.gui.button.IButton;
import nl.knokko.gui.texture.GuiTexture;
import nl.knokko.model.type.AbstractModel;
import nl.knokko.texture.Texture;
import nl.knokko.util.color.Color;
import nl.knokko.util.resources.Resources;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;

import static nl.knokko.shaders.GuiShader.GUI_SHADER;

public class GuiRenderer {
	
	private static final float[] QUADS = {-1,1, -1,-1, 1,1, 1,-1};
	
	public static final AbstractModel QUAD = Resources.loadGuiModel(QUADS);
	
	public void start(Gui gui){
		GUI_SHADER.start();
		GL30.glBindVertexArray(QUAD.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		Color color = gui.getBackGroundColor();
		if(color != null){
			GL11.glClearColor(color.getRedF(), color.getGreenF(), color.getBlueF(), color.getAlphaF());
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		}
	}
	
	public void stop(){
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_BLEND);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		GUI_SHADER.stop();
	}
	
	private long totalRenderTime;
	private int frames;
	
	public void render(ArrayList<GuiTexture> guis, ArrayList<IButton> buttons, Gui igui, boolean startAndStop){
		long startTime = System.nanoTime();
		if(startAndStop)
			start(igui);
		for(GuiTexture gui : guis)
			renderGuiTexture(gui, igui);
		for(IButton button : buttons)
			renderButtonTexture(button, button.getTextures(), igui);
		if(startAndStop)
			stop();
		long endTime = System.nanoTime();
		totalRenderTime += endTime - startTime;
		frames++;
	}
	
	public void renderGuiTexture(GuiTexture gui, Gui igui){
		Vector2f translation = new Vector2f(gui.getPosition());
		if(igui instanceof ScrollGui)
			translation.y += ((ScrollGui) igui).getCurrentScroll() * 2;
		renderTextures(translation, gui.getScale(), gui.getTextures());
	}
	
	public void renderButtonTexture(IButton button, Texture[] textures, Gui igui){
		Vector2f translation = new Vector2f(button.getCentre());
		if(igui instanceof ScrollGui)
			translation.y -= ((ScrollGui) igui).getCurrentScroll() * 2;
		renderTextures(translation, button.getSize(), textures);
	}
	
	public void renderTextures(Vector2f translation, Vector2f scale, Texture... textures){
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		//Matrix4f matrix = Maths.createTransformationMatrix(translation, scale);
		//GUI_SHADER.loadTransformation(matrix);
		GUI_SHADER.loadCentrePosition(translation);
		GUI_SHADER.loadScale(scale);
		for(Texture texture : textures){
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getID());
			GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, QUAD.getVertexCount());
		}
	}
	
	public void cleanUp(){
		GUI_SHADER.cleanUp();
		if(frames > 0)
			System.out.println("Average gui render time was " + (totalRenderTime) / frames / 1000 + " microseconds.");
	}
}
