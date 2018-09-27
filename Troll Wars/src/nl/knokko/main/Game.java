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
package nl.knokko.main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.filechooser.FileSystemView;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;

import nl.knokko.battle.Battle;
import nl.knokko.battle.BattleDefault;
import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.gamestate.*;
import nl.knokko.gui.component.AbstractGuiComponent;
import nl.knokko.gui.component.GuiComponent;
import nl.knokko.gui.component.state.GuiComponentState;
import nl.knokko.gui.component.state.RelativeComponentState;
import nl.knokko.gui.trading.GuiTrading;
import nl.knokko.gui.window.GLGuiWindow;
import nl.knokko.gui.window.WindowListener;
import nl.knokko.input.KeyInput;
import nl.knokko.input.MouseInput;
import nl.knokko.inventory.ArrayInventory;
import nl.knokko.inventory.Inventory;
import nl.knokko.inventory.trading.TradeOffers;
import nl.knokko.items.Items;
import nl.knokko.players.Players;
import nl.knokko.render.battle.Battle2dRenderer;
import nl.knokko.render.battle.EffectRenderer;
import nl.knokko.render.main.CreatureRenderer;
import nl.knokko.render.tile.TileModels;
import nl.knokko.render.tile.TileRenderer;
import nl.knokko.story.battle.StoryBattleManager;
import nl.knokko.story.dialogue.Dialogue;
import nl.knokko.story.dialogue.action.DialogueFunctions;
import nl.knokko.story.event.EventManager;
import nl.knokko.story.event.handler.EventHandler;
import nl.knokko.story.npc.NPCManager;
import nl.knokko.tiles.Tiles;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.resources.Resources;
import nl.knokko.util.resources.Saver;

public class Game {
	
	public static final float FOV = 70;
	public static final float NEAR_PLANE = 0.1f;
	public static final float FAR_PLANE = 10000;
	
	private static ArrayList<GameState> currentStates;
	private static ArrayList<GameState> updatingStates;
	private static ArrayList<GameState> renderingStates;
	
	private static File gameFolder;
	
	private static GameState[] states;
	
	private static TileRenderer tileRenderer;
	private static CreatureRenderer creatureRenderer;
	private static EffectRenderer effectRenderer;
	private static Battle2dRenderer battle2dRenderer;
	
	private static Inventory playerInventory;
	private static Options options;
	private static Players players;
	private static DialogueFunctions dialogueFunctions;
	
	private static NPCManager npcManager;
	private static EventManager eventManager;
	private static EventHandler eventHandler;
	private static StoryBattleManager storyBattleManager;
	
	private static GLGuiWindow window;
	private static CurrentGuiComponent guiComponent;
	
	private static Matrix4f projectionMatrix;
	
	private static String saveName;
	
	private static boolean isStopping;
	private static boolean needsSaving;
	
	private static int renderTicks;
	private static long renderTime;

	public static void main(String[] args) {
		System.out.println("Start memory is " + Runtime.getRuntime().totalMemory() + " bytes.");
		prepare();
		open();
		init();
		while(shouldContinue()){
			update();
			render();
		}
		finish();
		close();
	}
	
	private static void prepare(){
		gameFolder = new File(FileSystemView.getFileSystemView().getDefaultDirectory() + File.separator + "TrollWars");
		gameFolder.mkdirs();
	}
	
	private static void open(){
		window = new GLGuiWindow();
		guiComponent = new CurrentGuiComponent();
		window.setMainComponent(guiComponent);
		window.setWindowListener(new GameWindowListener());
		GameScreen.openScreen();
	}
	
	private static void init(){
		createProjectionMatrix();
		Saver.setSavesFolder(new File(gameFolder + File.separator + "saves"));
		Tiles.init();
		TileModels.init();
		npcManager = new NPCManager();
		eventManager = new EventManager();
		eventHandler = new EventHandler();
		storyBattleManager = new StoryBattleManager();
		options = new Options();
		players = new Players();
		dialogueFunctions = new DialogueFunctions();
		playerInventory = new ArrayInventory();
		states = new GameState[]{new StateMainMenu(), new StateArea(), new StateGameMenu(), new StateInteracting(), new StateBattle(), new StateDialogue()};
		currentStates = new ArrayList<GameState>();
		updatingStates = new ArrayList<GameState>();
		renderingStates = new ArrayList<GameState>();
		addState(getMainMenu());
		tileRenderer = new TileRenderer();
		creatureRenderer = new CreatureRenderer();
		effectRenderer = new EffectRenderer();
		battle2dRenderer = new Battle2dRenderer();
	}
	
	private static boolean shouldContinue(){
		return !isStopping && !Display.isCloseRequested();
	}
	
	private static void update(){
		MouseInput.update();
		KeyInput.update();
		window.update();
		for(int i = 0; i < updatingStates.size(); i++)
			updatingStates.get(i).update();
	}
	
	private static void render(){
		/*
		long startTime = System.nanoTime();
		for(int i = renderingStates.size() - 1; i >= 0; i--)
			renderingStates.get(i).render();
		long endTime = System.nanoTime();
		renderTime += (endTime - startTime);
		renderTicks++;
		GameScreen.updateScreen();
		*/
		GameScreen.updateScreen();
	}
	
	private static void finish(){
		TileRenderer.onFinish();
		if(needsSaving)
			save();
		for(GameState state : currentStates)
			state.close();
		Resources.cleanUp();
		System.out.println("average render time is " + (renderTime / renderTicks) / 1000 + " microseconds");
	}
	
	private static void close(){
		//GameScreen.closeScreen();
		window.close();
	}
	
	public static void save(){
		Saver.startSaving(saveName);
		playerInventory.save(Saver.save("player.inv", Items.amount() * 6));
		//Saver.save(playerInventory.save(new BitBuffer(Items.amount() * 6)), "player.inv");
		players.save();
		npcManager.save();
		eventManager.save();
		for(GameState state : states)
			state.save();
		try {
			Saver.stopSaving();
		} catch (IOException e) {
			System.out.println("Failed to save game data: " + e.getLocalizedMessage());
		}
	}
	
	public static void addState(GameState state){
		if(currentStates.size() > 0)
			currentStates.get(currentStates.size() - 1).disable();
		currentStates.add(state);
		state.open();
		refreshStates();
	}
	
	public static void removeState(){
		currentStates.remove(currentStates.size() - 1).close();
		if(!currentStates.isEmpty())
			currentStates.get(currentStates.size() - 1).enable();
		refreshStates();
	}
	
	private static void clearStates(){
		for(GameState state : currentStates)
			state.close();
		currentStates.clear();
	}
	
	private static void refreshStates(){
		updatingStates.clear();
		for(int i = currentStates.size() - 1; i >= 0; i--){
			updatingStates.add(currentStates.get(i));
			if(!currentStates.get(i).updateTransparent())
				break;
		}
		renderingStates.clear();
		for(int i = currentStates.size() - 1; i >= 0; i--){
			renderingStates.add(currentStates.get(i));
			if(!currentStates.get(i).renderTransparent())
				break;
		}
	}
	
	public static StateMainMenu getMainMenu(){
		return (StateMainMenu) states[0];
	}
	
	public static StateArea getArea(){
		return (StateArea) states[1];
	}
	
	public static StateGameMenu getGameMenu(){
		return (StateGameMenu) states[2];
	}
	
	public static StateInteracting getInteracting(){
		return (StateInteracting) states[3];
	}
	
	public static StateBattle getBattle(){
		return (StateBattle) states[4];
	}
	
	public static StateDialogue getDialogue(){
		return (StateDialogue) states[5];
	}
	
	public static TileRenderer getTileRenderer(){
		return tileRenderer;
	}
	
	public static CreatureRenderer getCreatureRenderer(){
		return creatureRenderer;
	}
	
	public static EffectRenderer getEffectRenderer(){
		return effectRenderer;
	}
	
	public static Battle2dRenderer get2DBattleRenderer(){
		return battle2dRenderer;
	}
	
	public static void stop(boolean save){
		isStopping = true;
		needsSaving = save;
	}
	
	public static Options getOptions(){
		return options != null ? options : Options.getInstance();
	}
	
	public static Players getPlayers(){
		return players;
	}
	
	public static Inventory getPlayerInventory(){
		return playerInventory;
	}
	
	public static NPCManager getNPCManager(){
		return npcManager;
	}
	
	public static EventManager getEventManager(){
		return eventManager;
	}
	
	public static StoryBattleManager getStoryBattleManager(){
		return storyBattleManager;
	}
	
	public static EventHandler getEventHandler(){
		return eventHandler;
	}
	
	public static GLGuiWindow getWindow(){
		return window;
	}
	
	public static GuiComponentState getGuiState(){
		return new RelativeComponentState.Static(guiComponent.getState(), 0, 0, 1, 1);
	}
	
	public static DialogueFunctions getDialogueFunctions(){
		return dialogueFunctions;
	}
	
	public static void startNewGame(String saveName){
		Game.saveName = saveName;
		npcManager.initFirstGame();
		removeState();
		addState(getArea());
		getArea().startNewGame();
		getPlayers().initFirstGame();
		playerInventory.addItems(Items.CRYT_PIECE, 10000);//TODO reduce this to 100 after testing...
	}
	
	public static void startRandomBattle(BattleCreature... opponents){
		BattleCreature[] playerTeam = players.getBattlePlayers();
		Battle battle = new BattleDefault(playerTeam, opponents);
		startBattle(battle);
		getArea().decreaseSteps();
	}
	
	public static void startBattle(Battle battle){
		getBattle().setBattle(battle);
		addState(getBattle());
	}
	
	public static void finishBattle(){
		removeState();
	}
	
	public static void finishDialogue(){
		removeState();
	}
	
	public static void gameOver(){
		stop(false);//TODO a proper game over...
	}
	
	public static void loadGame(String saveName, long saveTime){
		Game.saveName = saveName;
		try {
			Saver.startLoading(saveName, saveTime);
			playerInventory.load(Saver.load("player.inv"));
			removeState();
			addState(getArea());
			getArea().load();
			players.load();
			npcManager.load();
			eventManager.load();
			BitInput buffer = Saver.load("battle.data");
			if(buffer != null){
				getBattle().load(buffer);
				addState(getBattle());
			}
			buffer = Saver.load("dialogue.data");
			if(buffer != null){
				getDialogue().load(buffer);
				addState(getDialogue());
			}
			Saver.stopLoading();
		} catch(IOException ex){
			if(Saver.isLoading())
				Saver.stopLoading();
			throw new RuntimeException(ex);
		}
	}
	
	public static void toMainMenu(){
		clearStates();
		addState(getMainMenu());
	}
	
	public static GameState getCurrentState(){
		return currentStates.get(currentStates.size() - 1);
	}
	
	public static void setTrading(TradeOffers offers){
		addState(getInteracting());
		getInteracting().setCurrentGui(new GuiTrading(offers));
	}
	
	public static void closeDialogue(){
		getDialogue().setCurrentDialogue(null);
	}
	
	public static void startDialogue(Dialogue dialogue){
		getDialogue().setCurrentDialogue(dialogue);
		addState(getDialogue());
	}
	
	public static Matrix4f getProjectionMatrix(){
		return projectionMatrix;
	}
	
	public static void createProjectionMatrix(){
        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;
        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
        projectionMatrix.m33 = 0;
    }
	
	private static class CurrentGuiComponent extends AbstractGuiComponent {
		
		private GuiComponent get(){
			for(GameState state : renderingStates){
				GuiComponent gui = state.getCurrentGui();
				if(gui != null)
					return gui;
			}
			return null;
		}

		@Override
		public void init() {}

		@Override
		public void update() {
			GuiComponent component = get();
			if(component != null)
				component.update();
		}

		@Override
		public void render(nl.knokko.gui.render.GuiRenderer renderer) {
			GuiComponent component = get();
			if(component != null)
				component.render(renderer);
		}

		@Override
		public void click(float x, float y, int button) {
			GuiComponent component = get();
			if(component != null)
				component.click(x, y, button);
		}

		@Override
		public void clickOut(int button) {
			GuiComponent component = get();
			if(component != null)
				component.clickOut(button);
		}

		@Override
		public boolean scroll(float amount) {
			MouseInput.addScroll(amount);
			GuiComponent component = get();
			if(component != null)
				return component.scroll(amount);
			return false;
		}

		@Override
		public void keyPressed(int keyCode) {
			GuiComponent component = get();
			if(component != null)
				component.keyPressed(keyCode);
			KeyInput.addPress(keyCode);
		}

		@Override
		public void keyPressed(char character) {
			GuiComponent component = get();
			if(component != null)
				component.keyPressed(character);
			KeyInput.addPress(character);
		}

		@Override
		public void keyReleased(int keyCode) {
			GuiComponent component = get();
			if(component != null)
				component.keyReleased(keyCode);
			KeyInput.addRelease(keyCode);
		}
	}
	
	private static class GameWindowListener implements WindowListener {

		@Override
		public boolean preUpdate() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void postUpdate() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean preRender() {
			for(int i = renderingStates.size() - 1; i >= 0; i--)
				renderingStates.get(i).render();
			return false;
		}

		@Override
		public void postRender() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean preClick(float x, float y, int button) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void postClick(float x, float y, int button) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public float preScroll(float amount) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void postScroll(float amount) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean preKeyPressed(char character) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void postKeyPressed(char character) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean preKeyPressed(int keyCode) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void postKeyPressed(int keyCode) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean preKeyReleased(int keyCode) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void postKeyReleased(int keyCode) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void preClose() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void postClose() {
			// TODO Auto-generated method stub
			
		}

                @Override
                public boolean preRunLoop() {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void postRunLoop() {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
		
	}
}