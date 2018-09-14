package nl.knokko.render.battle;

import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.battle.render.properties.BattleRenderProperties;
import nl.knokko.model.type.AbstractModel;
import nl.knokko.model.type.GuiModel;
import nl.knokko.view.camera.Camera;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import static nl.knokko.shaders.battle.ResourceShader.RESOURCE_SHADER;
import nl.knokko.util.resources.Resources;

public class Battle2dRenderer {
    
        public static final GuiModel QUAD = Resources.loadGuiModel(new float[]{-1,1, -1,-1, 1,1, 1,-1});
	
	public void startResourceShader(Matrix4f viewMatrix){
		RESOURCE_SHADER.start();
		RESOURCE_SHADER.loadViewMatrix(viewMatrix);
		prepareModel(QUAD);
	}
	
	public void renderResources(Camera camera, BattleCreature creature){
		BattleRenderProperties props = creature.getRenderProperties();
		renderHealth(props, creature.getHealth(), creature.getMaxHealth());
		renderMana(props, creature.getMana(), creature.getMaxMana());
		renderFocus(props, creature.getFocus(), creature.getMaxFocus());
	}
	
	private void prepareModel(AbstractModel model){
		GL30.glBindVertexArray(model.getVaoID());
		GL20.glEnableVertexAttribArray(0);
	}
	
	private void unbindModel(){
		GL30.glBindVertexArray(0);
		GL20.glDisableVertexAttribArray(0);
	}
	
	private void renderHealth(BattleRenderProperties props, long health, long maxHealth){
		RESOURCE_SHADER.setHealthColor();
		renderResource(props.getHealthX(), props.getHealthY(), props.getHealthZ(), props.getHealthWidth() / 2, props.getHealthHeight() / 2, health, maxHealth);
	}
	
	private void renderMana(BattleRenderProperties props, long mana, long maxMana){
		if(maxMana > 0){
			RESOURCE_SHADER.setManaColor();
			renderResource(props.getManaX(), props.getManaY(), props.getManaZ(), props.getManaWidth() / 2, props.getManaHeight() / 2, mana, maxMana);
		}
	}
	
	private void renderFocus(BattleRenderProperties props, long focus, long maxFocus){
		if(maxFocus > 0){
			RESOURCE_SHADER.setFocusColor();
		}
	}
	
	private void renderResource(float midX, float midY, float midZ, float scaleX, float scaleY, long current, long max){
		RESOURCE_SHADER.loadProgress((float) current / max);
		RESOURCE_SHADER.loadWorldPosition(new Vector3f(midX, midY, midZ));
		RESOURCE_SHADER.loadScale(new Vector2f(scaleX, scaleY));
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, QUAD.getVertexCount());
	}
	
	public void stopResourceShader(){
		RESOURCE_SHADER.stop();
		unbindModel();
	}
}