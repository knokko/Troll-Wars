package nl.knokko.gui.battle;

import java.util.ArrayList;
import java.util.List;

import nl.knokko.battle.Battle;
import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.battle.move.BattleMove;
import nl.knokko.battle.move.FightMoveOption;
import nl.knokko.battle.move.ItemMoveOption;
import nl.knokko.battle.move.MoveSkipTurn;
import nl.knokko.gui.component.menu.GuiMenu;
import nl.knokko.gui.component.text.TextButton;
import nl.knokko.gui.texture.OldGuiTexture;
import nl.knokko.input.MouseClickEvent;
import nl.knokko.input.MouseInput;
import nl.knokko.input.MouseScrollEvent;
import nl.knokko.main.Game;
import nl.knokko.util.Maths;
import nl.knokko.util.color.Color;
import nl.knokko.util.color.ColorAlpha;
import nl.knokko.util.resources.Resources;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class GuiBattle extends GuiMenu {
	
	protected static final java.awt.Color BUTTON_COLOR = new java.awt.Color(80, 0, 220);
	protected static final java.awt.Color BORDER_COLOR = new java.awt.Color(30, 0, 100);
	protected static final java.awt.Color BUTTON_HOVER_COLOR = new java.awt.Color(100, 0, 255);
	protected static final java.awt.Color BORDER_HOVER_COLOR = new java.awt.Color(40, 0, 120);
	
	protected final Battle battle;
	
	protected State state;
	
	protected TextButton stopButton;
	protected TextButton runButton;
	protected ButtonText fightButton;
	protected ButtonText itemButton;
	protected TextButton waitButton;
	protected TextButton armButton;
	
	protected FightCategoryButton[] fightCategoryButtons;
	protected ButtonText fightCategoryReturnButton;
	protected FightMoveButton[] fightMoveButtons;
	
	protected ItemCategoryButton[] itemCategoryButtons;
	protected ButtonText itemCategoryReturnButton;
	protected ItemMoveButton[] itemMoveButtons;
	
	protected FightMoveOption selectedMove;
	protected ItemMoveOption selectedItem;
	
	protected SelectTargetButton[] playerButtons;
	protected SelectTargetButton[] opponentButtons;
	protected ButtonText targetReturnButton;
	
	protected OldGuiTexture fightCategoryBackground;
	protected OldGuiTexture fightMoveBackground;
	
	protected int scroll;
	
	protected boolean hasFightMoves;
	protected boolean hasItemMoves;
	protected boolean[] hasCategories;

	public GuiBattle(Battle battle) {
		this.battle = battle;
		this.state = State.WAITING;
	}
	
	@Override
	public void render(GuiRenderer renderer){
		//TEST
		renderer.start(this);
		if(state != State.WAITING)
			render(stopButton, renderer);
		if(battle.canRun())
			render(runButton, renderer);
		if(state == State.SELECT_ACTION || state == State.SELECT_MOVE_CATEGORY || state == State.SELECT_MOVE || state == State.SELECT_ITEM_CATEGORY || state == State.SELECT_ITEM){
			render(waitButton, renderer);
		}
		if(state == State.SELECT_ACTION){
			if(hasFightMoves)
				render(fightButton, renderer);
			if(hasItemMoves)
				render(itemButton, renderer);
			if(armButton != null)
				render(armButton, renderer);
		}
		if(state == State.SELECT_MOVE_CATEGORY || state == State.SELECT_MOVE){
			render(fightCategoryBackground, renderer);
			for(int i = 0; i < hasCategories.length; i++)
				if(hasCategories[i])
					render(fightCategoryButtons[i], renderer);
			render(fightCategoryReturnButton, renderer);
			if(hasItemMoves)
				render(itemButton, renderer);
		}
		if(state == State.SELECT_ITEM_CATEGORY || state == State.SELECT_ITEM){
			for(int i = 0; i < hasCategories.length; i++)
				if(hasCategories[i])
					render(itemCategoryButtons[i], renderer);
			render(itemCategoryReturnButton, renderer);
			if(hasFightMoves)
				render(fightButton, renderer);
		}
		if(state == State.SELECT_MOVE){
			render(fightMoveBackground, renderer);
			render(renderer, fightMoveButtons);
		}
		if(state == State.SELECT_ITEM){
			render(renderer, itemMoveButtons);
		}
		if(state == State.SELECT_FIGHT_TARGET){
			render(renderer, playerButtons);
			render(renderer, opponentButtons);
		}
		if(state == State.SELECT_ITEM_TARGET){
			render(renderer, playerButtons);
			render(renderer, opponentButtons);
		}
		renderer.stop();
	}
	
	protected void render(IButton button, GuiRenderer renderer){
		renderer.renderButtonTexture(button, button.getTextures(), this);
	}
	
	protected void render(OldGuiTexture texture, GuiRenderer renderer){
		renderer.renderGuiTexture(texture, this);
	}
	
	protected void render(GuiRenderer renderer, IButton... buttons){
		for(IButton button : buttons)
			render(button, renderer);
	}
	
	@Override
	public void update(){
		if(state == State.WAITING && battle.getChoosingPlayer() != null)
			toSelectActionState();
		ArrayList<MouseClickEvent> clicks = MouseInput.getMouseClicks();
		for(MouseClickEvent click : clicks)
			if(click.getButton() == 0)
				leftClick(click.getX(), click.getY());
		ArrayList<MouseScrollEvent> scrolls = MouseInput.getMouseScrolls();
		for(MouseScrollEvent event : scrolls)
			scroll += event.getDeltaScroll();
	}
	
	protected void leftClick(float x, float y){
		if(state != State.WAITING)
			if(click(stopButton, x, y)) return;
		if(battle.canRun())
			if(click(runButton, x, y)) return;
		if(state == State.SELECT_ACTION || state == State.SELECT_MOVE_CATEGORY || state == State.SELECT_MOVE || state == State.SELECT_ITEM_CATEGORY || state == State.SELECT_ITEM){
			if(click(waitButton, x, y)) return;
		}
		if(state == State.SELECT_ACTION){
			if(hasFightMoves)
				if(click(fightButton, x, y)) return;
			if(hasItemMoves)
				if(click(itemButton, x, y)) return;
			if(armButton != null && click(armButton, x, y)) return;
		}
		if(state == State.SELECT_MOVE_CATEGORY || state == State.SELECT_MOVE){
			for(int i = 0; i < fightCategoryButtons.length; i++)
				if(hasCategories[i])
					if(click(fightCategoryButtons[i], x, y)) return;
			if(click(fightCategoryReturnButton, x, y)) return;
			if(hasItemMoves)
				if(click(itemButton, x, y)) return;
		}
		if(state == State.SELECT_ITEM_CATEGORY || state == State.SELECT_ITEM){
			for(int i = 0; i < itemCategoryButtons.length; i++)
				if(hasCategories[i])
					if(click(itemCategoryButtons[i], x, y)) return;
			if(click(itemCategoryReturnButton, x, y)) return;
			if(hasFightMoves)
				if(click(fightButton, x, y)) return;
		}
		if(state == State.SELECT_MOVE){
			click(x, y, fightMoveButtons);
		}
		if(state == State.SELECT_ITEM){
			if(click(x, y, itemMoveButtons)) return;
		}
		if(state == State.SELECT_FIGHT_TARGET){
			if(click(x, y, playerButtons)) return;
			if(click(x, y, opponentButtons)) return;
		}
		if(state == State.SELECT_ITEM_TARGET){
			if(click(x, y, playerButtons)) return;
			if(click(x, y, opponentButtons)) return;
		}
		
		Matrix4f invertProjViewMatrix = Matrix4f.mul(Game.getProjectionMatrix(), Maths.createViewMatrix(battle.getCamera()), null);
		invertProjViewMatrix.invert();
		Vector3f cam = new Vector3f(battle.getCamera().getPosition());
		float relY = MouseInput.getRelativeY();
		float relX = MouseInput.getRelativeX();
			
			/*
			 * gl_Position = projViewMatrix * worldPosition;
			 * gl_Posiiton = (relX,relY,-1,1);
			 * 
			 * so worldPosition = invert * (relX,relY,-1,1);
			 */
		Vector3f direction = Maths.multiply(invertProjViewMatrix, new Vector3f(relX, relY, 1));
		direction.normalise();
		direction.scale(10f);
		while(cam.length() < 1000){
			cam.translate(direction.x, direction.y, direction.z);
			BattleCreature[] opponents = battle.getOpponents();
			for(BattleCreature opponent : opponents){
				if(opponent.getRenderProperties().isInside(cam.x, cam.y, cam.z)){
					Game.getBattle().setCurrentGui(new GuiBattleCharacter(this, opponent));
					return;
				}
			}
			BattleCreature[] players = battle.getPlayers();
			for(BattleCreature player : players){
				if(player.getRenderProperties().isInside(cam.x, cam.y, cam.z)){
					Game.getBattle().setCurrentGui(new GuiBattleCharacter(this, player));
					return;
				}
			}
		}
	}
	
	protected boolean click(IButton button, float x, float y){
		if(button.isHit(x, y)){
			button.leftClick(x, y);
			return true;
		}
		return false;
	}
	
	protected boolean click(float x, float y, IButton... buttons){
		for(IButton button : buttons)
			if(click(button, x, y)) return true;
		return false;
	}
	
	@Override
	public Color getBackGroundColor(){
		return null;
	}
	
	protected void selectMove(BattleMove move){
		battle.selectPlayerMove(move);
		toWaitingState();
	}
	
	protected void addTargetButtons(){
		targetReturnButton = new ButtonText(new Vector2f(0f, -0.85f), SCALE, Color.ORANGE, Color.DARK_ORANGE, Color.BLACK, "Back"){

			@Override
			public void leftClick(float x, float y) {
				if(state == State.SELECT_FIGHT_TARGET)
					toSelectMoveState(selectedMove.getCategory());
				else if(state == State.SELECT_ITEM_TARGET)
					toSelectItemState(selectedItem.getCategory());
				else
					throw new IllegalStateException("The target return button is only available when selecting a target!");
			}
		};
		boolean positive;
		if(state == State.SELECT_FIGHT_TARGET)
			positive = selectedMove.isPositive();
		else if(state == State.SELECT_ITEM_TARGET)
			positive = selectedItem.isPositive();
		else
			throw new IllegalStateException("Can't add target buttons in state " + state);
		BattleCreature[] players = battle.getPlayers();
		List<SelectTargetButton> playerButtonList = new ArrayList<SelectTargetButton>(players.length);
		for(int i = 0; i < players.length; i++)
			if(canSelectTarget(players[i]))
				playerButtonList.add(new SelectTargetButton(players[i], i, false, positive));
		playerButtons = playerButtonList.toArray(new SelectTargetButton[playerButtonList.size()]);
		BattleCreature[] opponents = battle.getOpponents();
		List<SelectTargetButton> enemyButtonList = new ArrayList<SelectTargetButton>(opponents.length);
		for(int i = 0; i < opponents.length; i++)
			if(canSelectTarget(opponents[i]))
				enemyButtonList.add(new SelectTargetButton(opponents[i], i, true, positive));
		opponentButtons = enemyButtonList.toArray(new SelectTargetButton[enemyButtonList.size()]);
	}
	
	private boolean canSelectTarget(BattleCreature target){
		if(selectedItem != null)
			return selectedItem.canUse(battle.getChoosingPlayer().getInventory(), battle.getChoosingPlayer(), target, battle);
		return selectedMove.canCast(battle.getChoosingPlayer(), target, battle);
	}
	
	protected void removeTargetButtons(){
		playerButtons = null;
		opponentButtons = null;
	}
	
	protected void toWaitingState(){
		state = State.WAITING;
	}
	
	protected void toSelectActionState(){
		state = State.SELECT_ACTION;
		String arm = battle.getChoosingPlayer().getActiveArm();
		if(arm != null){
			armButton = new ButtonText(new Vector2f(-0.2f, 0.85f), new Vector2f(0.2f, 0.1f), BUTTON_COLOR, Color.BLACK, Color.BLACK, arm){

				@Override
				public void leftClick(float x, float y) {
					battle.getChoosingPlayer().swapArm();
					toSelectActionState();
				}
			};
		}
		hasItemMoves = false;
		ItemMoveOption[] items = battle.getChoosingPlayer().getItems();
		for(ItemMoveOption item : items){
			if(item.canUse(battle.getChoosingPlayer().getInventory(), battle.getChoosingPlayer(), battle)){
				hasItemMoves = true;
				break;
			}
		}
		hasFightMoves = false;
		FightMoveOption[] moves = battle.getChoosingPlayer().getMoves();
		for(FightMoveOption move : moves){
			if(move.canCast(battle.getChoosingPlayer(), battle)){
				hasFightMoves = true;
				break;
			}
		}
	}
	
	protected void toSelectMoveCatState(){
		state = State.SELECT_MOVE_CATEGORY;
		hasCategories = new boolean[FightMoveOption.Category.values().length];
		for(int i = 0; i < hasCategories.length; i++)
			hasCategories[i] = battle.getChoosingPlayer().getMoves(FightMoveOption.Category.values()[i]).length > 0;
	}
	
	protected void toSelectItemCatState(){
		state = State.SELECT_ITEM_CATEGORY;
		hasCategories = new boolean[ItemMoveOption.Category.values().length];
		for(int i = 0; i < hasCategories.length; i++)
			hasCategories[i] = battle.getChoosingPlayer().getItems(ItemMoveOption.Category.values()[i]).length > 0;
	}
	
	protected void toSelectMoveState(FightMoveOption.Category category){
		state = State.SELECT_MOVE;
		FightMoveOption[] moves = battle.getChoosingPlayer().getMoves(category);
		fightMoveButtons = new FightMoveButton[moves.length];
		for(int i = 0; i < moves.length; i++)
			fightMoveButtons[i] = new FightMoveButton(moves[i], i, moves[i].canCast(battle.getChoosingPlayer(), battle));
	}
	
	protected void toSelectItemState(ItemMoveOption.Category category){
		state = State.SELECT_ITEM;
		ItemMoveOption[] items = battle.getChoosingPlayer().getItems(category);
		itemMoveButtons = new ItemMoveButton[items.length];
		for(int i = 0; i < items.length; i++)
			itemMoveButtons[i] = new ItemMoveButton(items[i], i);
	}
	
	protected void toSelectTargetState(FightMoveOption move){
		state = State.SELECT_FIGHT_TARGET;
		selectedMove = move;
		addTargetButtons();
	}
	
	protected void toSelectTargetState(ItemMoveOption item){
		state = State.SELECT_ITEM_TARGET;
		selectedItem = item;
		addTargetButtons();
	}

	@Override
	public void addButtons() {
		
	}

	@Override
	public void open() {
		toWaitingState();
	}
	
	protected static enum State {
		
		WAITING,
		SELECT_ACTION,
		SELECT_MOVE_CATEGORY,
		SELECT_MOVE,
		SELECT_FIGHT_TARGET,
		SELECT_ITEM_TARGET,
		SELECT_ITEM_CATEGORY,
		SELECT_ITEM;
	}
	
	protected static final Vector2f SCALE = new Vector2f(0.25f, 0.1f);
	
	protected static final float SCROLL_SCALE = 0.002f;
	
	protected static final java.awt.Color FIGHT_CATEGORY_BUTTON_COLOR = new java.awt.Color(150, 100, 0);
	protected static final java.awt.Color FIGHT_CATEGORY_BORDER_COLOR = new java.awt.Color(50, 50, 0);
	protected static final java.awt.Color FIGHT_CATEGORY_TEXT_COLOR = new java.awt.Color(50, 50, 0);
	protected static final java.awt.Color FIGHT_CATEGORY_HOVER_BUTTON_COLOR = new java.awt.Color(200, 140, 0);
	protected static final java.awt.Color FIGHT_CATEGORY_HOVER_BORDER_COLOR = new java.awt.Color(80, 80, 0);
	protected static final java.awt.Color FIGHT_CATEGORY_HOVER_TEXT_COLOR = new java.awt.Color(80, 80, 0);
	
	protected class FightCategoryButton extends ButtonText {
		
		protected final FightMoveOption.Category category;
		
		protected FightCategoryButton(FightMoveOption.Category category){
			super(new Vector2f(0.6f, -0.65f + (3 - category.ordinal()) * SCALE.y), SCALE, FIGHT_CATEGORY_BUTTON_COLOR, FIGHT_CATEGORY_BORDER_COLOR, FIGHT_CATEGORY_TEXT_COLOR, category.getDisplayName());
			this.category = category;
		}

		@Override
		public void leftClick(float x, float y) {
			toSelectMoveState(category);
		}
	}
	
	protected static final Color ITEM_CATEGORY_BUTTON_COLOR = new Color(0, 150, 150);
	protected static final Color ITEM_CATEGORY_BORDER_COLOR = new Color(0, 50, 50);
	protected static final Color ITEM_CATEGORY_TEXT_COLOR = new Color(0, 50, 50);
	
	protected class ItemCategoryButton extends ButtonText {
		
		protected final ItemMoveOption.Category category;
		
		protected ItemCategoryButton(ItemMoveOption.Category category) {
			super(new Vector2f(0f, -0.65f + (3 - category.ordinal()) * SCALE.y), SCALE, ITEM_CATEGORY_BUTTON_COLOR, ITEM_CATEGORY_BORDER_COLOR, ITEM_CATEGORY_TEXT_COLOR, category.getDisplayName());
			this.category = category;
		}

		@Override
		public void leftClick(float x, float y) {
			toSelectItemState(category);
		}
	}
	
	protected static final Color AVAILABLE_FIGHT_BUTTON_COLOR = new Color(0, 0, 150);
	protected static final Color UNAVAILABLE_FIGHT_BUTTON_COLOR = new ColorAlpha(0, 0, 150, 100);
	
	protected static final Color AVAILABLE_FIGHT_BORDER_COLOR = new Color(0, 0, 50);
	protected static final Color UNAVAILABLE_FIGHT_BORDER_COLOR = new ColorAlpha(0, 0, 50, 100);
	
	protected static final Color AVAILABLE_FIGHT_TEXT_COLOR = new Color(0, 0, 0);
	protected static final Color UNAVAILABLE_FIGHT_TEXT_COLOR = new ColorAlpha(0, 0, 0, 100);
	
	protected static Color getFightButtonColor(boolean available){
		return available ? AVAILABLE_FIGHT_BUTTON_COLOR : UNAVAILABLE_FIGHT_BUTTON_COLOR;
	}
	
	protected static Color getFightBorderColor(boolean available){
		return available ? AVAILABLE_FIGHT_BORDER_COLOR : UNAVAILABLE_FIGHT_BORDER_COLOR;
	}
	
	protected static Color getFightTextColor(boolean available){
		return available ? AVAILABLE_FIGHT_TEXT_COLOR : UNAVAILABLE_FIGHT_TEXT_COLOR;
	}
	
	protected class FightMoveButton extends ButtonText {
		
		protected FightMoveOption move;

		public FightMoveButton(FightMoveOption move, int index, boolean available){
			super(new Vector2f(0.1f, -0.65f + index * SCALE.y * 2.5f), SCALE, getFightButtonColor(available), getFightBorderColor(available), getFightTextColor(available), move.getName());
			this.move = move;
		}

		@Override
		public void leftClick(float x, float y) {
			if(move.canCast(battle.getChoosingPlayer(), battle))
				toSelectTargetState(move);
		}
		
		@Override
		public Vector2f getCentre(){
			float y = centre.y - scroll * SCROLL_SCALE;
			return new Vector2f(centre.x, y >= -0.75f && y <= 0.55f ? y : -10);
		}
	}
	
	protected static final Color ITEM_BUTTON_COLOR = new Color(0, 0, 200);
	protected static final Color ITEM_BORDER_COLOR = new Color(0, 0, 100);
	protected static final Color ITEM_TEXT_COLOR = new Color(0, 0, 50);
	
	protected class ItemMoveButton extends ButtonText {
		
		protected final ItemMoveOption move;

		public ItemMoveButton(ItemMoveOption move, int index) {
			super(new Vector2f(-0.3f, -0.65f + (index)), SCALE, ITEM_BUTTON_COLOR, ITEM_BORDER_COLOR, ITEM_TEXT_COLOR, move.getName());
			this.move = move;
		}

		@Override
		public void leftClick(float x, float y) {
			toSelectTargetState(move);
		}
		
		@Override
		public Vector2f getCentre(){
			return new Vector2f(centre.x, centre.y - scroll * SCROLL_SCALE);
		}
	}
	
	protected static final Color TARGET_ENEMY_BUTTON_COLOR = new Color(200, 100, 0);
	protected static final Color TARGET_ENEMY_BORDER_COLOR = new Color(100, 50, 0);
	protected static final Color TARGET_ENEMY_TEXT_COLOR = new Color(0, 0, 0);
	
	protected static final Color TARGET_ALLY_BUTTON_COLOR = new Color(0, 100, 200);
	protected static final Color TARGET_ALLY_BORDER_COLOR = new Color(0, 50, 100);
	protected static final Color TARGET_ALLY_TEXT_COLOR = new Color(0, 0, 0);
	
	protected static Color getButtonColor(boolean isEnemy, boolean isPositive){
		if((isEnemy && !isPositive) || (!isEnemy && isPositive))
			return TARGET_ENEMY_BUTTON_COLOR;
		return TARGET_ALLY_BUTTON_COLOR;
	}
	
	protected static Color getBorderColor(boolean isEnemy, boolean isPositive){
		if((isEnemy && !isPositive) || (!isEnemy && isPositive))
			return TARGET_ENEMY_BORDER_COLOR;
		return TARGET_ALLY_BORDER_COLOR;
	}
	
	protected static Color getTextColor(boolean isEnemy, boolean isPositive){
		if((isEnemy && !isPositive) || (!isEnemy && isPositive))
			return TARGET_ENEMY_TEXT_COLOR;
		return TARGET_ALLY_TEXT_COLOR;
	}
	
	protected class SelectTargetButton extends ButtonText {
		
		protected final BattleCreature target;
		
		protected SelectTargetButton(BattleCreature target, int index, boolean isEnemy, boolean isPositive){
			super(new Vector2f(-0.3f + index * SCALE.x * 2.5f, isEnemy ? 0.3f : -0.3f), SCALE, getButtonColor(isEnemy, isPositive), getBorderColor(isEnemy, isPositive), getTextColor(isEnemy, isPositive), target.getName());
			this.target = target;
		}

		@Override
		public void leftClick(float x, float y) {
			if(state == State.SELECT_FIGHT_TARGET){
				battle.selectPlayerMove(selectedMove.createMove(battle.getChoosingPlayer(), target, battle));
				toWaitingState();
				removeTargetButtons();
			}
			else if(state == State.SELECT_ITEM_TARGET){
				battle.selectPlayerMove(selectedItem.createMove(battle.getChoosingPlayer().getInventory(), battle.getChoosingPlayer(), target, battle));
				toWaitingState();
				removeTargetButtons();
			}
			else
				throw new IllegalStateException("Invalid state for target button: " + state);
		}
	}

	@Override
	protected void addComponents() {
		stopButton = new ButtonText(new Vector2f(0.5f, 0.85f), new Vector2f(0.3f, 0.1f), BUTTON_COLOR, Color.BLACK, Color.BLACK, "Save and stop"){

			@Override
			public void leftClick(float x, float y) {
				Game.stop(true);
			}
		};
		runButton = new ButtonText(new Vector2f(-0.8f, 0.85f), new Vector2f(0.15f, 0.1f), BUTTON_COLOR, Color.RED, Color.RED, "Run"){

			@Override
			public void leftClick(float x, float y) {
				Game.getBattle().run();
			}
		};
		fightButton = new ButtonText(new Vector2f(0.6f, -0.85f), new Vector2f(0.25f, 0.1f), BUTTON_COLOR, Color.BLACK, Color.BLACK, "Fight"){

			@Override
			public void leftClick(float x, float y) {
				toSelectMoveCatState();
			}
		};
		fightCategoryReturnButton = new ButtonText(new Vector2f(0.6f, -0.85f), new Vector2f(0.25f, 0.1f), BUTTON_COLOR, Color.BLACK, Color.BLACK, "Cancel"){

			@Override
			public void leftClick(float x, float y) {
				toSelectActionState();
				hasCategories = null;
			}
		};
		itemButton = new ButtonText(new Vector2f(0f, -0.85f), new Vector2f(0.25f, 0.1f), BUTTON_COLOR, Color.BLACK, Color.BLACK, "Consume Item"){

			@Override
			public void leftClick(float x, float y) {
				toSelectItemCatState();
			}
		};
		itemCategoryReturnButton = new ButtonText(new Vector2f(0f, -0.85f), new Vector2f(0.25f, 0.1f), BUTTON_COLOR, Color.BLACK, Color.BLACK, "Cancel"){

			@Override
			public void leftClick(float x, float y) {
				toSelectActionState();
				hasCategories = null;
			}
			
		};
		waitButton = new ButtonText(new Vector2f(-0.7f, -0.85f), new Vector2f(0.2f, 0.1f), BUTTON_COLOR, Color.BLACK, Color.BLACK, "Wait"){

			@Override
			public void leftClick(float x, float y) {
				battle.selectPlayerMove(new MoveSkipTurn());
				toWaitingState();
			}
		};
		fightCategoryButtons = new FightCategoryButton[FightMoveOption.Category.values().length];
		for(int i = 0; i < fightCategoryButtons.length; i++)
			fightCategoryButtons[i] = new FightCategoryButton(FightMoveOption.Category.values()[i]);
		itemCategoryButtons = new ItemCategoryButton[ItemMoveOption.Category.values().length];
		for(int i = 0; i < itemCategoryButtons.length; i++)
			itemCategoryButtons[i] = new ItemCategoryButton(ItemMoveOption.Category.values()[i]);
		fightCategoryBackground = new OldGuiTexture(new Vector2f(0.6f, -0.6f), new Vector2f(0.25f, 0.4f), Resources.createFilledTexture(Color.ORANGE));
		fightMoveBackground = new OldGuiTexture(new Vector2f(0.1f, -0.1f), new Vector2f(0.25f, 0.65f), Resources.createFilledTexture(Color.BLUE));
	}
}
