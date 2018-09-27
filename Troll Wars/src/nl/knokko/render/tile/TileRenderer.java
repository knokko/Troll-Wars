/* 
 * The MIT License
 *
 * Copyright 2018 knokko.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
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
 */
package nl.knokko.render.tile;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import nl.knokko.area.TileMap;
import nl.knokko.main.Game;
import nl.knokko.model.type.DefaultModel;
import nl.knokko.model.type.LiquidModel;
import nl.knokko.model.type.SpiritModel;
import nl.knokko.model.type.TileModel;
import nl.knokko.render.main.WorldRenderer;
import nl.knokko.shaders.ShaderType;
import nl.knokko.texture.ModelTexture;
import nl.knokko.tiles.Tile;
import nl.knokko.tiles.Tiles;
import nl.knokko.tiles.Tiles.RenderForm;
import nl.knokko.util.FrustumHelper;
import nl.knokko.util.Maths;
import nl.knokko.util.color.Color;
import nl.knokko.util.resources.Resources;
import nl.knokko.view.camera.Camera;
import nl.knokko.view.light.Light;
import static nl.knokko.shaders.WorldShader.WORLD_SHADER;
import static nl.knokko.shaders.LiquidShader.LIQUID_SHADER;
import static nl.knokko.shaders.SpiritShader.SPIRIT_SHADER;
import static nl.knokko.shaders.tile.DefaultTileShader.DEFAULT_TILE_SHADER;
import static nl.knokko.shaders.tile.LiquidTileShader.LIQUID_TILE_SHADER;
import static nl.knokko.shaders.tile.SpiritTileShader.SPIRIT_TILE_SHADER;

public class TileRenderer extends WorldRenderer {
	
	private static long totalTime = 0;
	private static long times = 0;
	
	public static final ModelTexture RED = new ModelTexture(Resources.createFilledTexture(Color.RED), 0.1f, 0.1f);
	public static final ModelTexture GREEN = new ModelTexture(Resources.createFilledTexture(Color.GREEN), 0.1f, 0.1f);
	public static final DefaultModel QUAD = Resources.loadDefaultModel(new float[]{-1,0,-1, 1,0,-1, 1,0,1, -1,0,1}, new float[]{0,0, 1,0, 1,1, 0,1}, new float[]{0,1,0, 0,1,0, 0,1,0, 0,1,0}, new int[]{0,1,2, 0,3,2});
	public static final LiquidModel LIQUID_QUAD = Resources.loadLiquidModel(new float[]{-1,0,-1, 1,0,-1, 1,0,1, -1,0,1}, new float[]{0,0, 1,0, 1,1, 0,1}, new int[]{0,1,2, 0,3,2});
	public static final SpiritModel SPIRIT_QUAD = Resources.loadSpiritModel(new float[]{-1,0,-1, 1,0,-1, 1,0,1, -1,0,1}, new float[]{0,0, 1,0, 1,1, 0,1}, new int[]{0,1,2, 0,3,2});
	public static final DefaultModel TRIANGLE = Resources.loadDefaultModel(new float[]{-1,0,-1, 1,0,-1, 1,0,1}, new float[]{0,0, 1,0, 1,1}, new float[]{0,1,0, 0,1,0, 0,1,0}, new int[]{0,1,2});
	public static final LiquidModel LIQUID_TRIANGLE = Resources.loadLiquidModel(new float[]{-1,0,-1, 1,0,-1, 1,0,1}, new float[]{0,0, 1,0, 1,1}, new int[]{0,1,2});
	public static final SpiritModel SPIRIT_TRIANGLE = Resources.loadSpiritModel(new float[]{-1,0,-1, 1,0,-1, 1,0,1}, new float[]{0,0, 1,0, 1,1}, new int[]{0,1,2});
	
	public void renderTiles(TileMap tiles, Camera camera, Light light){
		long startTime = System.nanoTime();
		
		//renderOld(tiles, camera, light);
		renderNew(tiles, camera);
		
		times++;
		totalTime += (System.nanoTime() - startTime) / 1000;
	}
	
	void renderNew(TileMap tiles, Camera camera){
		FrustumHelper.setProjViewMatrix(Matrix4f.mul(Game.getProjectionMatrix(), Maths.createViewMatrix(camera), null));
		TileRenderMap renderMap = tiles.getNewRenderMap();
		prepareDefaultTileShader(camera);
		renderTileMapNew(camera, renderMap, ShaderType.NORMAL);
		stopDefaultTileShader();
		prepareLiquidTileShader(camera);
		renderTileMapNew(camera, renderMap, ShaderType.LIQUID);
		stopLiquidTileShader();
		prepareSpiritTileShader(camera);
		renderTileMapNew(camera, renderMap, ShaderType.SPIRIT);
		stopSpiritTileShader();
	}
	
	/*
	void renderOld(TileMap tiles, Camera camera, Light light){
		Matrix4f[][] renderMap = tiles.getRenderMap();
		FrustumHelper.setProjViewMatrix(Matrix4f.mul(Game.getProjectionMatrix(), Maths.createViewMatrix(camera), null));
		prepareLiquidShader(camera, light);
		prepareLiquidModel(LIQUID_QUAD);
		renderTileMapOld(camera, renderMap, RenderForm.QUAD, ShaderType.LIQUID);
		unbindLiquidModel();
		prepareLiquidModel(LIQUID_TRIANGLE);
		renderTileMapOld(camera, renderMap, RenderForm.TRIANGLE, ShaderType.LIQUID);
		unbindLiquidModel();
		
		prepareSpiritShader(camera, light);
		prepareSpiritModel(SPIRIT_QUAD);
		renderTileMapOld(camera, renderMap, RenderForm.QUAD, ShaderType.SPIRIT);
		unbindSpiritModel();
		prepareSpiritModel(SPIRIT_TRIANGLE);
		renderTileMapOld(camera, renderMap, RenderForm.TRIANGLE, ShaderType.SPIRIT);
		unbindSpiritModel();
		
		prepareWorldShader(camera, light);
		prepareNormalModel(TRIANGLE);
		renderTileMapOld(camera, renderMap, RenderForm.TRIANGLE, ShaderType.NORMAL);
		unbindNormalModel();
		prepareNormalModel(QUAD);
		renderTileMapOld(camera, renderMap, RenderForm.QUAD, ShaderType.NORMAL);
		unbindNormalModel();
	}
	*/
	
	private void prepareDefaultTileShader(Camera camera){
		DEFAULT_TILE_SHADER.start();
		DEFAULT_TILE_SHADER.loadViewMatrix(camera);
	}
	
	private void prepareLiquidTileShader(Camera camera){
		LIQUID_TILE_SHADER.start();
		LIQUID_TILE_SHADER.loadViewMatrix(camera);
		LIQUID_TILE_SHADER.loadRandomizer();
	}
	
	private void prepareSpiritTileShader(Camera camera){
		SPIRIT_TILE_SHADER.start();
		SPIRIT_TILE_SHADER.loadViewMatrix(camera);
		SPIRIT_TILE_SHADER.loadRandomizer();
	}
	
	private void stopDefaultTileShader(){
		DEFAULT_TILE_SHADER.stop();
	}
	
	private void stopLiquidTileShader(){
		LIQUID_TILE_SHADER.stop();
	}
	
	private void stopSpiritTileShader(){
		SPIRIT_TILE_SHADER.stop();
	}
	
	private void prepareDefaultTileModel(TileModel model){
		GL30.glBindVertexArray(model.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		GL20.glEnableVertexAttribArray(3);
	}
	
	private void prepareLiquidTileModel(TileModel model){
		GL30.glBindVertexArray(model.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
	}
	
	private void prepareSpiritTileModel(TileModel model){
		GL30.glBindVertexArray(model.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
	}
	
	private void renderTileMapNew(Camera camera, TileRenderMap tiles, ShaderType shader){
		for(TileModel model : TileModels.ALL){
			if(model.getShaderType() == shader && tiles.hasModel(model)){
				if(shader == ShaderType.NORMAL)
					prepareDefaultTileModel(model);
				else if(shader == ShaderType.LIQUID)
					prepareLiquidTileModel(model);
				else
					prepareSpiritTileModel(model);
				for(Tile tile : model.getTiles()){
					Vector3f[] positions = tiles.getPositions(tile);
					if(positions.length > 0){
						prepareTileTexture(tile.getTexture(), shader);
						for(Vector3f position : positions){
							if(FrustumHelper.insideFrustum(position.x, position.y, position.z, 64)){//64 seems safe
								if(shader == ShaderType.NORMAL)
									DEFAULT_TILE_SHADER.loadTilePosition(position);
								else if(shader == ShaderType.LIQUID)
									LIQUID_TILE_SHADER.loadTilePosition(position);
								else
									SPIRIT_TILE_SHADER.loadTilePosition(position);
								GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
							}
						}
					}
				}
				if(shader == ShaderType.NORMAL)
					unbindDefaultTileModel();
				else if(shader == ShaderType.LIQUID)
					unbindLiquidTileModel();
				else
					unbindSpiritTileModel();
			}
		}
	}
	
	private void prepareTileTexture(ModelTexture texture, ShaderType shader){
		if(shader == ShaderType.NORMAL)
			prepareNormalTileTexture(texture);
		else if(shader == ShaderType.LIQUID)
			prepareLiquidTileTexture(texture);
		else
			prepareSpiritTileTexture(texture);
	}
	
	private void prepareNormalTileTexture(ModelTexture texture){
		DEFAULT_TILE_SHADER.loadShine(texture.getShineDamper(), texture.getReflectivity());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
	}
	
	private void prepareLiquidTileTexture(ModelTexture texture){
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
	}
	
	private void prepareSpiritTileTexture(ModelTexture texture){
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
	}
	
	private void unbindDefaultTileModel(){
		GL30.glBindVertexArray(0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL20.glDisableVertexAttribArray(3);
	}
	
	private void unbindLiquidTileModel(){
		GL30.glBindVertexArray(0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
	}
	
	private void unbindSpiritTileModel(){
		GL30.glBindVertexArray(0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
	}
	
	void renderTileMapOld(Camera camera, Matrix4f[][] renderMap, RenderForm type, ShaderType st){
		//float minX = camera.getMinX() / 32;
		//float maxX = camera.getMaxX() / 32;
		//float minZ = camera.getMinZ() / 32;
		//float maxZ = camera.getMaxZ() / 32;
		for(int i = 0; i < renderMap.length; i++){
			if(renderMap[i] != null){
				Tile tile = Tiles.fromRuntimeID((short) (i + Short.MIN_VALUE));
				if(tile.getRenderForm() == type && tile.getShaderType() == st){
					prepareTexture(tile.getTexture(), st);
					for(Matrix4f matrix : renderMap[i]){
						if(FrustumHelper.insideFrustum(matrix.m30, matrix.m31, matrix.m32, 64)){//64 should be safe to use
						//if(TileMap.getTileX(matrix) + 1 >= minX && TileMap.getTileX(matrix) - 1 <= maxX && TileMap.getTileZ(matrix)  + 1 >= minZ && TileMap.getTileZ(matrix) - 1 <= maxZ){
							if(st == ShaderType.NORMAL)
								WORLD_SHADER.loadTransformationMatrix(matrix);
							if(st == ShaderType.LIQUID)
								LIQUID_SHADER.loadTransformationMatrix(matrix);
							if(st == ShaderType.SPIRIT)
								SPIRIT_SHADER.loadTransformationMatrix(matrix);
							GL11.glDrawElements(GL11.GL_TRIANGLES, QUAD.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
						}
					}
				}
			}
		}
	}
	
	public void renderWhiteTop(Camera camera, Tile tile, int tileX, int tileY, int tileZ){
		if(tile.getShaderType() == ShaderType.NORMAL){
			prepareDefaultTileShader(camera);
			prepareDefaultTileModel(tile.getModel());
			prepareNormalTileTexture(Resources.white());
			DEFAULT_TILE_SHADER.loadTilePosition(new Vector3f(tileX * 64, tileY * 16, tileZ * 64));
			GL11.glDrawElements(GL11.GL_TRIANGLES, tile.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			unbindDefaultTileModel();
			stopDefaultTileShader();
		}
		if(tile.getShaderType() == ShaderType.LIQUID){
			prepareLiquidTileShader(camera);
			prepareLiquidTileModel(tile.getModel());
			//TODO think a little about this...
		}
	}
	
	public void renderGreenTop(Camera camera, Tile tile, int tileX, int tileY, int tileZ){
		if(tile.getShaderType() == ShaderType.NORMAL){
			prepareDefaultTileShader(camera);
			prepareDefaultTileModel(tile.getModel());
			prepareNormalTileTexture(GREEN);
			DEFAULT_TILE_SHADER.loadTilePosition(new Vector3f(tileX * 64, tileY * 16, tileZ * 64));
			GL11.glDrawElements(GL11.GL_TRIANGLES, tile.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			unbindDefaultTileModel();
			stopDefaultTileShader();
		}
		if(tile.getShaderType() == ShaderType.LIQUID){
			prepareLiquidTileShader(camera);
			prepareLiquidTileModel(tile.getModel());
			//TODO think a little about this...
		}
	}
	
	public void renderEdge(int maxX, int maxY, int maxZ){
		prepareNormalTexture(RED);
		prepareNormalModel(QUAD);
		WORLD_SHADER.loadTransformationMatrix(Maths.createTransformationMatrix(new Vector3f(maxX * 32 - 32, -1f, maxZ * 32 - 32), 0, 0, 0, maxX * 32, 1, maxZ * 32));
		GL11.glDrawElements(GL11.GL_TRIANGLES, QUAD.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
	}
	
	public void unprepare(){
		unbindNormalModel();
		WORLD_SHADER.stop();
	}
	
	public static void onFinish(){
		if(times != 0)
			System.out.println("Average tile render time was " + (totalTime / times) + " microseconds.");
	}
}
