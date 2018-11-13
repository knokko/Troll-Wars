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
package nl.knokko.designer.area;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.filechooser.FileSystemView;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import nl.knokko.area.Area;
import nl.knokko.area.TileMap;
import nl.knokko.gui.component.state.RelativeComponentState;
import nl.knokko.gui.keycode.KeyCode;
import nl.knokko.gui.texture.GuiTexture;
import nl.knokko.gui.window.GLGuiWindow;
import nl.knokko.gui.window.WindowListener;
import nl.knokko.input.KeyInput;
import nl.knokko.input.MouseInput;
import nl.knokko.input.MouseScrollEvent;
import nl.knokko.main.Game;
import nl.knokko.main.GameScreen;
import nl.knokko.render.tile.TileModels;
import nl.knokko.render.tile.TileRenderer;
import nl.knokko.shaders.WorldShader;
import nl.knokko.tiles.Tile;
import nl.knokko.tiles.Tiles;
import nl.knokko.util.Facing;
import nl.knokko.util.resources.Compressor;
import nl.knokko.util.resources.Natives;
import nl.knokko.util.resources.Resources;
import nl.knokko.view.camera.Camera;
import nl.knokko.view.camera.CameraFlying;
import nl.knokko.view.light.DefaultLight;

public class AreaDesigner {
	
	public static final DefaultLight LIGHT = new DefaultLight();
	
	private static final int NEW_WIDTH = 200;
	private static final int NEW_DEPTH = 200;
	
	private static GLGuiWindow window;
	private static Area area;
	private static Camera camera;
	private static GuiAreaDesigner gui;
	private static Random random;
	
	private static TileRenderer tileRenderer;
	
	private static int targetX = 0;
	private static int targetY = 0;
	private static int targetZ = 0;
	
	private static int markedX = -1;
	private static int markedY = -1;
	private static int markedZ = -1;
	
	private static byte moveBufferX = 0;
	private static byte moveBufferY = 0;
	private static byte moveBufferZ = 0;
	
	private static byte stackAmount;
	
	private static int tileIndex = 0;
	
	private static TileHolder[] tiles;
	private static GuiTexture[] tileNameTextures;
	
	private static long prevTime;
	
	private static long totalRenderTime;
	private static int renderCount;
	
	private static boolean moveTileState;

	public static void main(String[] args) {
		prepare();
		init();
		open();
		postInit();
		while(shouldContinue()){
			update();
			render();
		}
		finish();
		close();
	}
	
	private static void prepare(){
		Natives.prepare(new File(FileSystemView.getFileSystemView().getDefaultDirectory() + File.separator + "TrollWars"));
	}
	
	private static void open(){
		
		window.open("Troll Wars Area Designer", true);
		//GameScreen.openScreen();
	}
	
	private static void postInit() {
		Tiles.init();
		TileModels.init();
		Field[] fields = Tiles.class.getFields();
		ArrayList<TileHolder> tileList = new ArrayList<TileHolder>();
		for(int i = 0; i < fields.length; i++){
			try {
				Object value = fields[i].get(null);
				if(value instanceof Tile)
					tileList.add(new SingleTileHolder((Tile) value, fields[i].getName()));
				else if(value instanceof Tile[])
					tileList.add(new ArrayTileHolder((Tile[]) value, fields[i].getName()));
			} catch (Exception ex) {
				ex.printStackTrace();
			} 
		}
		tiles = tileList.toArray(new TileHolder[tileList.size()]);
		Mouse.setGrabbed(true);
		tileRenderer = new TileRenderer();
		window.setMainComponent(gui);
		MouseInput.setGuiState(new RelativeComponentState.Static(gui.getState(), 0, 0, 1, 1));
		load();
	}
	
	private static void init(){
		Game.createProjectionMatrix();
		//camera = new CameraFlying(new Vector3f(WIDTH * 32, 80, DEPTH * 32), 90, 0);
		random = new Random();
		
		window = new GLGuiWindow();
		window.setWindowListener(new AreaWindowListener());
		gui = new GuiAreaDesigner();
		//window.setMainComponent(gui);
	}
	
	private static void load(){
		try {
			FileInputStream input = new FileInputStream("wip.area");
			byte[] bytes = Resources.readByteArray(input);
			bytes = Compressor.decompress(bytes);
			ArrayList<Byte> list = new ArrayList<Byte>(bytes.length);
			for(byte b : bytes)
				list.add(b);
			TileMap tileMap = TileMap.load(list, 0, true);
			area = new Area(tileMap);
			camera = new CameraFlying(new Vector3f(tileMap.getWidth() * 32, 80, tileMap.getDepth() * 32), 90, 0);
			targetX = tileMap.getWidth() / 2;
			targetZ = tileMap.getDepth() / 2;
		} catch(Exception ex){
			ex.printStackTrace();
			area = new Area(NEW_WIDTH, NEW_DEPTH);
			targetX = NEW_WIDTH / 2;
			targetZ = NEW_DEPTH / 2;
		}
	}
	
	private static boolean shouldContinue(){
		return !Display.isCloseRequested() && !KeyInput.isKeyDown(KeyCode.KEY_ESCAPE);
	}
	
	private static void update(){
		window.update();
		area.update();
		camera.update();
		gui.update();
		if(!moveTileState){
			Facing facing = Facing.fromYaw(camera.getDegYaw());
			if(KeyInput.isKeyDown(KeyCode.KEY_UP)){
				if(Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
					moveTileUp();
				else
					moveTile(facing);
				gui.updateSelectedPosition();
			}
			if(KeyInput.isKeyDown(KeyCode.KEY_DOWN)){
				if(Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
					moveTileDown();
				else
					moveTile(Facing.fromYaw(facing.getDegreeYaw() + 180));
				gui.updateSelectedPosition();
			}
			if(KeyInput.isKeyDown(KeyCode.KEY_RIGHT)) {
				moveTile(Facing.fromYaw(facing.getDegreeYaw() + 90));
				gui.updateSelectedPosition();
			}
			if(KeyInput.isKeyDown(KeyCode.KEY_LEFT)) {
				moveTile(Facing.fromYaw(facing.getDegreeYaw() + 270));
				gui.updateSelectedPosition();
			}
			if(moveBufferX > 0)
				--moveBufferX;
			if(moveBufferY > 0)
				--moveBufferY;
			if(moveBufferZ > 0)
				--moveBufferZ;
		}
		else {
			if(camera.getDegPitch() > 0){
				float distanceXZ = (float) (Math.tan(0.5 * Math.PI - camera.getRadPitch()) * ((camera.getPosition().y / 32) - targetY));
				targetX = (int) (camera.getPosition().x / 32 - area.getTiles().getWidth() / 2 + Math.sin(camera.getRadYaw()) * distanceXZ);
				targetZ = (int) (camera.getPosition().z / 32 - area.getTiles().getDepth() / 2 - Math.cos(camera.getRadYaw()) * distanceXZ);  
			}
		}
		ArrayList<MouseScrollEvent> scrolls = MouseInput.getMouseScrolls();
		for(MouseScrollEvent event : scrolls){
			tileIndex += event.getDeltaScroll() / 120;
			if(tileIndex < 0)
				tileIndex = 0;
			if(tileIndex >= tiles.length)
				tileIndex = tiles.length - 1;
			gui.updateSelectedTile();
		}
		if(KeyInput.isKeyDown(KeyCode.KEY_H)){
			targetX = (int) (camera.getPosition().x / 64);
			targetY = (int) (camera.getPosition().y / 16);
			targetZ = (int) (camera.getPosition().z / 64);
			gui.updateSelectedPosition();
		}
		if(KeyInput.isKeyDown(KeyCode.KEY_1))
			stackAmount = 1;
		if(KeyInput.isKeyDown(KeyCode.KEY_2))
			stackAmount = 2;
		if(KeyInput.isKeyDown(KeyCode.KEY_3))
			stackAmount = 3;
		if(KeyInput.isKeyDown(KeyCode.KEY_4))
			stackAmount = 4;
		if(KeyInput.isKeyDown(KeyCode.KEY_5))
			stackAmount = 5;
		if(KeyInput.isKeyDown(KeyCode.KEY_6))
			stackAmount = 6;
		if(KeyInput.isKeyDown(KeyCode.KEY_7))
			stackAmount = 7;
		if(KeyInput.isKeyDown(KeyCode.KEY_8))
			stackAmount = 8;
		if(KeyInput.isKeyDown(KeyCode.KEY_9))
			stackAmount = 9;
		if(KeyInput.isKeyDown(KeyCode.KEY_C))
			area.getTiles().clearDuplicates();
		boolean pressed = false;
		int button = -1;
		while (Mouse.next()) {
			if (Mouse.getEventButtonState()) {
				pressed = true;
				button = Mouse.getEventButton();
			}
		}
		/*
		ArrayList<MouseClickEvent> clicks = MouseInput.getMouseClicks();
		for(MouseClickEvent click : clicks){
			if(click.wasPressed()){
				pressed = true;
				button = click.getButton();
			}
		}*/
		if(pressed && targetX < area.getTiles().getWidth() && targetX >= 0 && targetY >= 0 && targetZ < area.getTiles().getDepth() && targetZ >= 0){
			if(KeyInput.isKeyDown(KeyCode.KEY_F)){
				markedX = targetX;
				markedY = targetY;
				markedZ = targetZ;
			}
			else if(KeyInput.isKeyDown(KeyCode.KEY_Q)){
				moveTileState = !moveTileState;
			}
			else {
				if(button == 0){
					if(markedY == -1){
						area.getTiles().placeTile(tiles[tileIndex].getTile(random), targetX, targetY, targetZ);
						for(int i = 1; i < stackAmount; i++)
							area.getTiles().placeTile(tiles[tileIndex].getTile(random), targetX, targetY + 4 * i, targetZ);
					}
					else {
						area.getTiles().fillTiles(tiles[tileIndex].getTile(random), targetX, targetY, targetZ, markedX, markedY, markedZ);
						markedY = -1;
					}
				}
				if(button == 1)
					area.getTiles().removeTiles(targetX, targetY, targetZ);
			}
		}
		MouseInput.update();
		KeyInput.update();
	}
	
	private static void render(){
		setTime();
		tileRenderer.prepare(true);
		tileRenderer.renderTiles(area.getTiles(), camera, LIGHT);
		measureTime("main rendering");
		WorldShader.WORLD_SHADER.start();
		WorldShader.WORLD_SHADER.loadViewMatrix(camera);
		tileRenderer.renderEdge(area.getTiles().getWidth(), 255, area.getTiles().getDepth());
		tileRenderer.renderWhiteTop(camera, tiles[tileIndex].getTile(random), targetX, targetY, targetZ);
		if(markedY != -1)
			tileRenderer.renderGreenTop(camera, tiles[tileIndex].getTile(random), markedX, markedY, markedZ);
		tileRenderer.unprepare();
		window.render();
		Display.sync(GameScreen.fps());
	}
	
	private static void finish(){
		save();
		Mouse.setGrabbed(false);
	}
	
	private static void close(){
		window.close();
	}
	
	private static void save(){
		ArrayList<Byte> data = new ArrayList<Byte>();
		area.getTiles().save(data);
		byte[] array = new byte[data.size()];
		for(int i = 0; i < data.size(); i++)
			array[i] = data.get(i);
		array = Compressor.compress(array);
		try {
			new File("back-ups").mkdir();
			save(array, "wip.area");
			save(array, "back-ups/" + System.currentTimeMillis() + ".area");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if(renderCount > 0)
			System.out.println("The average render time was " + totalRenderTime / renderCount + " microseconds.");
	}
	
	private static void save(byte[] data, String fileName){
		try {
			FileOutputStream output = new FileOutputStream(fileName);
			output.write(data);
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void moveTile(Facing facing){
		if(facing == Facing.NORTH)
			moveTileNorth();
		if(facing == Facing.EAST)
			moveTileEast();
		if(facing == Facing.SOUTH)
			moveTileSouth();
		if(facing == Facing.WEST)
			moveTileWest();
	}
	
	private static void moveTileUp(){
		if(moveBufferY == 0){
			if(targetY < 255)
				targetY++;
			moveBufferY = getMoveBuffer();
		}
	}
	
	private static void moveTileDown(){
		if(moveBufferY == 0){
			if(targetY > 0)
				targetY--;
			moveBufferY = getMoveBuffer();
		}
	}
	
	private static void moveTileNorth(){
		if(moveBufferZ == 0){
			if(targetZ > 0)
				targetZ--;
			moveBufferZ = getMoveBuffer();
		}
	}
	
	private static void moveTileSouth(){
		if(moveBufferZ == 0){
			if(targetZ < area.getTiles().getDepth() - 1)
				targetZ++;
			moveBufferZ = getMoveBuffer();
		}
	}
	
	private static void moveTileEast(){
		if(moveBufferX == 0){
			if(targetX < area.getTiles().getWidth() - 1)
				targetX++;
			moveBufferX = getMoveBuffer();
		}
	}
	
	private static void moveTileWest(){
		if(moveBufferX == 0){
			if(targetX > 0)
				targetX--;
			moveBufferX = getMoveBuffer();
		}
	}
	
	private static byte getMoveBuffer(){
		return (byte) (KeyInput.isKeyDown(KeyCode.KEY_T) ? 5 : 20);
	}
	
	static GuiTexture getTileNameTexture(){
		return tileNameTextures[tileIndex];
	}
	
	static int getTargetX(){
		return targetX;
	}
	
	static int getTargetY(){
		return targetY;
	}
	
	static int getTargetZ(){
		return targetZ;
	}
	
	public static void setTime(){
		prevTime = System.nanoTime();
	}
	
	public static void measureTime(String name){
		totalRenderTime += ((System.nanoTime() - prevTime) / 1000);
		renderCount++;
	}
	
	public static TileHolder getSelectedTile() {
		return tiles[tileIndex];
	}
	
	private static class AreaWindowListener implements WindowListener {

		@Override
		public boolean preUpdate() {
			return false;
		}

		@Override
		public void postUpdate() {}

		@Override
		public boolean preRender() {
			return false;
		}

		@Override
		public void postRender() {}

		@Override
		public boolean preClick(float x, float y, int button) {
			return false;
		}

		@Override
		public void postClick(float x, float y, int button) {}

		@Override
		public float preScroll(float amount) {
			return amount;
		}

		@Override
		public void postScroll(float amount) {
			MouseInput.addScroll(amount);
		}

		@Override
		public boolean preKeyPressed(char character) {
			return false;
		}

		@Override
		public void postKeyPressed(char character) {
			KeyInput.addPress(character);
		}

		@Override
		public boolean preKeyPressed(int keyCode) {
			return false;
		}

		@Override
		public void postKeyPressed(int keyCode) {
			KeyInput.addPress(keyCode);
		}

		@Override
		public boolean preKeyReleased(int keyCode) {
			return false;
		}

		@Override
		public void postKeyReleased(int keyCode) {
			KeyInput.addRelease(keyCode);
		}

		@Override
		public boolean preRunLoop() {
			return false;
		}

		@Override
		public void postRunLoop() {}

		@Override
		public void preClose() {}

		@Override
		public void postClose() {}
	}
	
	static interface TileHolder {
		
		Tile getTile(Random random);
		
		String getTileName();
	}
	
	private static class SingleTileHolder implements TileHolder {
		
		private final Tile tile;
		private final String name;
		
		private SingleTileHolder(Tile tile, String name){
			this.tile = tile;
			this.name = name;
		}
		
		@Override
		public Tile getTile(Random random){
			return tile;
		}
		
		@Override
		public String getTileName(){
			return name;
		}
		
		@Override
		public String toString(){
			return "SingleTileHolder(" + name + ")";
		}
	}
	
	private static class ArrayTileHolder implements TileHolder {
		
		private final Tile[] tiles;
		private final String name;
		
		private ArrayTileHolder(Tile[] tiles, String name){
			this.tiles = tiles;
			this.name = name;
		}

		@Override
		public Tile getTile(Random random) {
			return tiles[random.nextInt(tiles.length)];
		}

		@Override
		public String getTileName() {
			return name;
		}
		
		@Override
		public String toString(){
			return "ArrayListHolder(" + name + ")";
		}
	}
}
