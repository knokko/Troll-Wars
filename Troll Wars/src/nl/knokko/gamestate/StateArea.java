/*******************************************************************************
 * The MIT License
 *
 * Copyright (c) 2019 knokko
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
package nl.knokko.gamestate;

import nl.knokko.area.Area;
import nl.knokko.area.AreaDoor.Location;
import nl.knokko.area.creature.AreaPlayer;
import nl.knokko.areas.AreaRargia;
import nl.knokko.gui.component.GuiComponent;
import nl.knokko.gui.keycode.KeyCode;
import nl.knokko.main.Game;
import nl.knokko.render.main.CreatureRenderer;
import nl.knokko.render.tile.TileRenderer;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;
import nl.knokko.util.position.SpawnPosition;
import nl.knokko.util.resources.Saver;
import nl.knokko.view.camera.Camera;
import nl.knokko.view.camera.CameraPlayer;
import nl.knokko.view.light.DefaultLight;
import nl.knokko.view.light.Light;

public class StateArea implements GameState {
	
	private TileRenderer tileRenderer;
	private CreatureRenderer creatureRenderer;
	private Light light;
	
	private Area[] areas;
	
	private Camera camera;
	private Area area;
	
	private byte steps;
	
	public StateArea(){
		areas = new Area[Area.amount()];
		for(int index = 0; index < areas.length; index++)
			areas[index] = Area.fromID((short) (index + Short.MIN_VALUE));
	}

	@Override
	public void update() {
		area.update();
		if(Game.getCurrentState() == this)
			camera.update();
	}

	@Override
	public void render() {
		area.render(creatureRenderer, tileRenderer, camera, light);
	}

	@Override
	public void open() {
		// TODO Grab mouse or improve controls
		//Mouse.setGrabbed(true);
		tileRenderer = new TileRenderer();
		creatureRenderer = new CreatureRenderer();
		light = new DefaultLight();
		
		Game.getWindow().setRenderContinuously(true);
	}

	@Override
	public void close() {
		//Mouse.setGrabbed(false);
		Game.getWindow().setRenderContinuously(false);
	}

	@Override
	public void enable() {
		Game.getWindow().setRenderContinuously(true);
		//Mouse.setGrabbed(true);
	}

	@Override
	public void disable() {
		Game.getWindow().setRenderContinuously(false);
		//Mouse.setGrabbed(false);
	}

	@Override
	public boolean renderTransparent() {
		return false;
	}

	@Override
	public boolean updateTransparent() {
		return false;
	}

	@Override
	public void setCurrentGui(GuiComponent gui) {}
	
	@Override
	public GuiComponent getCurrentGui(){
		return null;
	}
	
	public void transferPlayer(short areaID, Location destination, float yaw){
		area.disable();
		area = getArea(areaID);
		if(destination != null){
			AreaPlayer player = area.getPlayer(false);
			if(player == null){
				player = new AreaPlayer(new SpawnPosition(destination.getTileX(), destination.getTileY(), destination.getTileZ()));
				player.setArea(area);
				player.setYaw(yaw);
				area.getCreatures().spawnCreature(player);
			}
			player.getPosition().setPosition(destination.getTileX() * 32, destination.getTileY() * 8, destination.getTileZ() * 32);
			player.refreshPosition();
		}
		else
			area.getPlayer(true).getPosition().setPosition(area.getPlayerSpawn());
		area.getPlayer(true).setYaw(yaw);
		camera = new CameraPlayer(area.getPlayer(true).getPosition());
	}
	
	public void save(){
		saveGeneral();
		for(Area area : areas)
			area.save();
	}
	
	public void load(){
		loadGeneral();
		for(Area area : areas)
			area.load();
	}
	
	public void startNewGame(){
		area = getArea(AreaRargia.ID);
		camera = new CameraPlayer(area.getPlayer(true).getPosition());
	}
	
	public Area getArea(){
		return area;
	}
	
	public Camera getCamera(){
		return camera;
	}
	
	private void saveGeneral() {
		//BooleanArrayBitOutput buffer = new BooleanArrayBitOutput(136);
		BitOutput buffer = Saver.save("area.data", 136);
		//BitBuffer buffer = new BitBuffer(136);
		buffer.addShort(area.getID());
		buffer.addByte(steps());
		area.getPlayer(true).save(buffer);
		//Saver.save(buffer, "area.data");
	}
	
	private void loadGeneral() {
		BitInput buffer = Saver.load("area.data");
		area = getArea(buffer.readShort());
		setSteps(buffer.readByte());
		area.getPlayer(true).load(buffer);
		camera = new CameraPlayer(area.getCreatures().getPlayer(true).getPosition());
		//camera = new CameraFlying(area.getPlayerSpawn());
	}
	
	public void increaseSteps(){
		steps++;
	}
	
	public byte steps(){
		return steps;
	}
	
	public void setSteps(byte amount){
		steps = amount;
	}
	
	public void decreaseSteps(){
		steps -= 40;
	}
	
	private Area getArea(short id){
		areas[id - Short.MIN_VALUE].enable();
		return areas[id - Short.MIN_VALUE];
	}

	@Override
	public void keyPressed(int keyCode) {
		if(keyCode == KeyCode.KEY_ESCAPE)
			Game.addState(Game.getGameMenu());
	}
}
