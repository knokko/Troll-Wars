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
import nl.knokko.gui.button.ButtonText;
import nl.knokko.gui.trading.GuiTrading;
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
import nl.knokko.render.main.GuiRenderer;
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
	
	private static GuiRenderer guiRenderer;
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
		nl.knokko.util.resources.Natives.prepare(gameFolder);
	}
	
	private static void open(){
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
		guiRenderer = new GuiRenderer();
		tileRenderer = new TileRenderer();
		creatureRenderer = new CreatureRenderer();
		effectRenderer = new EffectRenderer();
		battle2dRenderer = new Battle2dRenderer();
	}
	
	private static boolean shouldContinue(){
		return !isStopping && !Display.isCloseRequested();
	}
	
	private static void update(){
		for(int i = 0; i < updatingStates.size(); i++)
			updatingStates.get(i).update();
		MouseInput.update();
		KeyInput.update();
	}
	
	private static void render(){
		long startTime = System.nanoTime();
		for(int i = renderingStates.size() - 1; i >= 0; i--)
			renderingStates.get(i).render();
		long endTime = System.nanoTime();
		renderTime += (endTime - startTime);
		renderTicks++;
		GameScreen.updateScreen();
	}
	
	private static void finish(){
		TileRenderer.onFinish();
		if(needsSaving)
			save();
		for(GameState state : currentStates)
			state.close();
		guiRenderer.cleanUp();
		Resources.cleanUp();
		System.out.println("average button init time is " + (ButtonText.totalTime / ButtonText.instances) / 1000 + " microseconds");
		System.out.println("average render time is " + (renderTime / renderTicks) / 1000 + " microseconds");
	}
	
	private static void close(){
		GameScreen.closeScreen();
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
	
	public static GuiRenderer getGuiRenderer(){
		return guiRenderer;
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
}
