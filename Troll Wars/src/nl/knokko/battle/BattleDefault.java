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
package nl.knokko.battle;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.battle.decoration.BattleDecoration;
import nl.knokko.battle.decoration.BattleDecorations;
import nl.knokko.battle.move.BattleMove;
import nl.knokko.main.Game;
import nl.knokko.model.ModelPart;
import nl.knokko.render.main.CreatureRenderer;
import nl.knokko.shaders.ShaderType;
import nl.knokko.util.Maths;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;
import nl.knokko.util.color.Color;
import nl.knokko.view.camera.*;
import nl.knokko.view.light.DefaultLight;
import nl.knokko.view.light.Light;

public class BattleDefault implements Battle {
	
	public static final byte CLASS_ID = -128;
	
	protected static final Light LIGHT = new DefaultLight();
	protected static final byte TEAM_SIZE_BITCOUNT = 4;
	
	protected BattleDecoration decoration;
	
	protected BattleState state;
	protected Camera camera;
	
	protected BattleMove currentMove;
	protected BattleCreature choosingPlayer;
	
	protected BattleCreature[] playerTeam;
	protected BattleCreature[] opposingTeam;
	/*
	 * long onturn = 0;
	 * while(onturn < Long.MAX_VALUE){
	 * 		List<BattleCreature> thisTurnCreatures = new ArrayList<BattleCreature>();
	 * 		for(BattleCreature creature : creatureList){
	 * 			if(canDivide(onturn,creature.getTurnSpeed()){
	 * 				thisTurnCreatures.add(creature);
	 * 			}
	 * 		}
	 *      onturn++;
	 * }
	 * int speed1 = 10;
	 * int speed2 = 15;
	 */
	protected long onTurn;
	
	private static final byte CURRENT_CREATURES_BIT_COUNT = 5;
	private static final byte CURRENT_CREATURE_BIT_COUNT = 4;
	private List<Byte> currentCreatures;
	private byte currentPlayerIndex;
	
	private byte timer;
	
	public BattleDefault(){
		camera = createCamera();
	}

	public BattleDefault(BattleDecoration decoration, BattleCreature[] players, BattleCreature[] opponents){
		this.decoration = decoration;
		decoration.refreshModel();
		state = BattleState.STARTING;
		playerTeam = players;
		opposingTeam = opponents;
		currentCreatures = new ArrayList<Byte>();
		timer = -128;
		camera = createCamera();
		for(int i = 0; i < players.length; i++){
			players[i].setPosition(-200, 0, -120 + 80 * i);
			players[i].setRotation(0, 90, 0);
		}
		for(int i = 0; i < opponents.length; i++){
			opponents[i].setPosition(200, 0, -120 + 80 * i);
			opponents[i].setRotation(0, 270, 0);
		}
	}

	@Override
	public void save(BitOutput buffer) {
		buffer.addByte(decoration.getID());
		buffer.addNumber(state.getID(), BattleState.BIT_COUNT, false);
		buffer.addLong(onTurn);
		buffer.addNumber(currentCreatures.size(), CURRENT_CREATURES_BIT_COUNT, false);
		for(Byte b : currentCreatures)
			buffer.addNumber(b, CURRENT_CREATURE_BIT_COUNT, true);
		buffer.addNumber(currentPlayerIndex, CURRENT_CREATURE_BIT_COUNT, true);
		buffer.addNumber(playerTeam.length, TEAM_SIZE_BITCOUNT, false);
		for(BattleCreature player : playerTeam)
			BattleCreature.Registry.saveCreature(this, player, buffer);
		buffer.addNumber(opposingTeam.length, TEAM_SIZE_BITCOUNT, false);
		for(BattleCreature opponent : opposingTeam)
			BattleCreature.Registry.saveCreature(this, opponent, buffer);
	}

	@Override
	public void load(BitInput buffer) {
		decoration = BattleDecorations.fromID(buffer.readByte());
		decoration.refreshModel();
		state = BattleState.fromID(buffer.readNumber(BattleState.BIT_COUNT, false));
		onTurn = buffer.readLong();
		byte currentCreaturesSize = (byte) buffer.readNumber(CURRENT_CREATURES_BIT_COUNT, false);
		currentCreatures = new ArrayList<Byte>(currentCreaturesSize);
		for(byte b = 0; b < currentCreaturesSize; b++)
			currentCreatures.add((byte) buffer.readNumber(CURRENT_CREATURE_BIT_COUNT, true));
		currentPlayerIndex = (byte) buffer.readNumber(CURRENT_CREATURE_BIT_COUNT, true);
		playerTeam = new BattleCreature[(int) buffer.readNumber(TEAM_SIZE_BITCOUNT, false)];
		for(int i = 0; i < playerTeam.length; i++)
			playerTeam[i] = BattleCreature.Registry.loadCreature(buffer);
		opposingTeam = new BattleCreature[(int) buffer.readNumber(TEAM_SIZE_BITCOUNT, false)];
		for(int i = 0; i < opposingTeam.length; i++)
			opposingTeam[i] = BattleCreature.Registry.loadCreature(buffer);
		if(state == BattleState.WAITING_FOR_PLAYER)
			startCreatureTurn(getCreature(currentPlayerIndex), currentPlayerIndex);
	}

	@Override
	public byte getClassID() {
		return CLASS_ID;
	}

	@Override
	public void update() {
		camera.update();
		switch(state){
		case DEFEAT: {
			if(timer >= getDefeatDelay())
				onDefeat();
			else
				timer++;
			updateAnimations();
			break;
		}
		case STARTING: {
			if(timer >= getStartDelay())
				updateState();
			else
				timer++;
			updateAnimations();
			break;
		}
		case UPDATING_MOVE: {
			currentMove.update();
			if(currentMove.isDone())
				updateState();
			updateAnimations();
			break;
		}
		case VICTORY: {
			if(timer >= getVictoryDelay())
				onVictory();
			else
				timer++;
			updateAnimations();
			break;
		}
		case WAITING_FOR_PLAYER: {
			updateAnimations();
			break;
		}
		default:
			throw new IllegalStateException("Unknown battle state: " + state);
		}
	}

	@Override
	public void render() {
		Color background = decoration.getBackgroundColor();
		GL11.glClearColor(background.getRedF(), background.getGreenF(), background.getBlueF(), 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		decoration.render(camera);
		Matrix4f viewMatrix = Maths.createOriginViewMatrix(camera);
		Game.getCreatureRenderer().prepare(false);
		Game.getCreatureRenderer().prepareWorldShader(camera, LIGHT);
		renderTeam(camera, playerTeam, ShaderType.NORMAL);
		renderTeam(camera, opposingTeam, ShaderType.NORMAL);
		Game.getCreatureRenderer().prepareLiquidShader(camera, LIGHT);
		renderTeam(camera, playerTeam, ShaderType.LIQUID);
		renderTeam(camera, opposingTeam, ShaderType.LIQUID);
		Game.getCreatureRenderer().prepareSpiritShader(camera, LIGHT);
		renderTeam(camera, playerTeam, ShaderType.SPIRIT);
		renderTeam(camera, opposingTeam, ShaderType.SPIRIT);
		Game.get2DBattleRenderer().startResourceShader(viewMatrix);
		for(BattleCreature creature : playerTeam)
			if(creature != null && !creature.isFainted())
				Game.get2DBattleRenderer().renderResources(camera, creature);
		for(BattleCreature creature : opposingTeam)
			if(creature != null && !creature.isFainted())
				Game.get2DBattleRenderer().renderResources(camera, creature);
		Game.get2DBattleRenderer().stopResourceShader();
		if(currentMove != null)
			currentMove.render(viewMatrix, camera);
	}
	
	@Override
	public Camera getCamera(){
		return camera;
	}
	
	protected void renderTeam(Camera camera, BattleCreature[] team, ShaderType st){
		for(BattleCreature bc : team){
			if(!bc.isFainted() && bc.getShaderType() == st){
				for(ModelPart part : bc.getModels())
					renderModelPart(camera, Game.getCreatureRenderer(), st, part, bc);
			}
		}
	}
	
	protected void renderModelPart(Camera camera, CreatureRenderer renderer, ShaderType shaderType, ModelPart part, BattleCreature creature){
		renderer.prepareModel(part.getModel(), shaderType);
		renderer.prepareTexture(part.getTexture(), shaderType);
		renderer.renderInstance(part.getMatrix(creature.getMatrix(camera)), shaderType, part.getModel().getVertexCount());
		for(ModelPart child : part.getChildren())
			if(child != null)
				renderModelPart(camera, renderer, shaderType, child, creature);
	}

	@Override
	public void selectPlayerMove(BattleMove move) {
		if(state == BattleState.WAITING_FOR_PLAYER){
			if(move == null)
				throw new NullPointerException("move is null");
			this.currentMove = move;
			state = BattleState.UPDATING_MOVE;
		}
		else
			throw new IllegalStateException("Current state is " + state);
	}
	
	@Override
	public BattleCreature getChoosingPlayer(){
		if(state == BattleState.WAITING_FOR_PLAYER)
			return choosingPlayer;
		else
			return null;
	}
	
	@Override
	public boolean canRun(){
		return Game.getOptions().getDifficulty().canRunAlways() || (state == BattleState.WAITING_FOR_PLAYER && Game.getOptions().getDifficulty().canRunWhileOnTurn());
	}
	
	@Override
	public BattleCreature[] getPlayers(){
		return playerTeam;
	}
	
	@Override
	public BattleCreature[] getOpponents(){
		return opposingTeam;
	}
	
	protected Camera createCamera(){
		return new CameraBattle();
	}
	
	/**
	 * -128 is no delay at all, 0 is 2 seconds delay, 127 is 4 seconds delay
	 * @return
	 */
	protected byte getStartDelay(){
		return 0;
	}
	
	protected byte getVictoryDelay(){
		return 127;
	}
	
	protected byte getDefeatDelay(){
		return 127;
	}
	
	protected void updateAnimations(){
		
	}
	
	protected void updateState(){
		if(noPlayersLeft()){
			state = BattleState.DEFEAT;
			timer = -128;
			return;
		}
		if(noEnemiesLeft()){
			state = BattleState.VICTORY;
			timer = -128;
			return;
		}
		for(byte currentIndex = 0; currentIndex < currentCreatures.size(); currentIndex++){
			byte b = currentCreatures.get(currentIndex);
			BattleCreature current = getCreature(b);
			if(!current.isFainted()){
				currentCreatures.remove(currentIndex);
				startCreatureTurn(current, b);
				return;
			}
			else {
				currentCreatures.remove(currentIndex);
				currentIndex--;
			}
		}
		while(currentCreatures.isEmpty()){
			onTurn++;
			findOnTurnCreatures();
		}
	}
	
	protected void findOnTurnCreatures(){
		for(byte b = 0; b < playerTeam.length; b++){
			BattleCreature player = playerTeam[b];
			if(!player.isFainted() && canDivide(onTurn, getTurnDelay(player.getTurnSpeed())))
				currentCreatures.add(b);
		}
		for(byte b = 0; b < opposingTeam.length; b++){
			BattleCreature opponent = opposingTeam[b];
			if(!opponent.isFainted() && canDivide(onTurn, getTurnDelay(opponent.getTurnSpeed())))
				currentCreatures.add((byte)(-b - 1));
		}
	}
	
	protected void startCreatureTurn(BattleCreature creature, byte index){
		currentPlayerIndex = index;
		if(creature.isPlayerControlled()){
			state = BattleState.WAITING_FOR_PLAYER;
			choosingPlayer = creature;
		}
		else {
			if(index >= 0)
				setCreatureMove(creature.chooseMove(this, playerTeam, opposingTeam));
			else
				setCreatureMove(creature.chooseMove(this, opposingTeam, playerTeam));
		}
	}
	
	protected void setCreatureMove(BattleMove move){
		if(move == null)
			throw new NullPointerException("move is null");
		state = BattleState.UPDATING_MOVE;
		currentMove = move;
	}
	
	protected boolean noPlayersLeft(){
		for(BattleCreature creature : playerTeam){
			if(!creature.isFainted())
				return false;
		}
		return true;
	}
	
	protected boolean noEnemiesLeft(){
		for(BattleCreature creature : opposingTeam){
			if(!creature.isFainted())
				return false;
		}
		return true;
	}
	
	protected BattleCreature getCreature(byte index){
		if(index >= 0)
			return playerTeam[index];
		else
			return opposingTeam[-index - 1];
	}
	
	protected void onVictory(){
		Game.finishBattle();
	}
	
	protected void onDefeat(){
		Game.gameOver();
	}
	
	public static boolean canDivide(long first, long second){
		return first == first / second * second;
	}
	
	public static long getTurnDelay(int turnSpeed){
		return 10000 / turnSpeed;
	}
}
