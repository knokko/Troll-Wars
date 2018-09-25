package nl.knokko.gui.battle;

import java.util.ArrayList;
import java.util.List;

import nl.knokko.battle.Battle;
import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.battle.move.BattleMove;
import nl.knokko.battle.move.FightMoveOption;
import nl.knokko.battle.move.ItemMoveOption;
import nl.knokko.battle.move.MoveSkipTurn;
import nl.knokko.gui.component.WrapperComponent;
import nl.knokko.gui.component.menu.GuiMenu;
import nl.knokko.gui.component.simple.ConditionalColorComponent;
import nl.knokko.gui.component.text.ActivatableTextButton;
import nl.knokko.gui.component.text.ConditionalTextButton;
import nl.knokko.gui.component.text.TextButton;
import nl.knokko.gui.render.GuiRenderer;
import nl.knokko.gui.texture.OldGuiTexture;
import nl.knokko.gui.util.TextBuilder.Properties;
import nl.knokko.gui.util.Condition;
import nl.knokko.input.MouseClickEvent;
import nl.knokko.input.MouseInput;
import nl.knokko.input.MouseScrollEvent;
import nl.knokko.main.Game;
import nl.knokko.util.Maths;
import nl.knokko.util.color.Color;
import nl.knokko.util.color.ColorAlpha;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class GuiBattle extends GuiMenu {
	
	protected static final java.awt.Color BUTTON_COLOR = new java.awt.Color(80, 0, 220);
	protected static final java.awt.Color BORDER_COLOR = new java.awt.Color(30, 0, 100);
	protected static final java.awt.Color BUTTON_HOVER_COLOR = new java.awt.Color(100, 0, 255);
	protected static final java.awt.Color BORDER_HOVER_COLOR = new java.awt.Color(40, 0, 120);
	
	protected static final Properties BUTTON_PROPERTIES = Properties.createButton(BUTTON_COLOR, BORDER_COLOR);
	protected static final Properties HOVER_PROPERTIES = Properties.createButton(BUTTON_HOVER_COLOR, BORDER_HOVER_COLOR);
	
	protected final Battle battle;
	
	protected State state;
        
        protected ArmComponent armComponent = new ArmComponent();
	protected FightMoveButton[] fightMoveButtons;
	protected ItemMoveButton[] itemMoveButtons;
	
	protected FightMoveOption selectedMove;
	protected ItemMoveOption selectedItem;
	
	protected SelectTargetButton[] playerButtons;
	protected SelectTargetButton[] opponentButtons;
	protected ButtonText targetReturnButton;
	
	protected int scroll;
	
	protected boolean hasFightMoves;
	protected boolean hasItemMoves;
	protected boolean[] hasCategories;

	public GuiBattle(Battle battle) {
		this.battle = battle;
		this.state = State.WAITING;
	}
	
	@Override
	protected void addComponents(){
		addComponent(new ConditionalColorComponent(Color.ORANGE, () -> state == State.SELECT_MOVE_CATEGORY || state == State.SELECT_MOVE), 0.675f, 0f, 0.925f, 0.4f);
		addComponent(new ConditionalColorComponent(Color.BLUE, () -> state == State.SELECT_MOVE), 0.425f, 0.125f, 0.675f, 0.775f);
		addComponent(new ConditionalTextButton("Stop", BUTTON_PROPERTIES, HOVER_PROPERTIES, () -> {
                    Game.stop(true);
                }, () -> state != State.WAITING), 0.6f, 0.875f, 0.9f, 0.975f);
		addComponent(new ConditionalTextButton("Run", BUTTON_PROPERTIES, HOVER_PROPERTIES, () -> {
                    Game.getBattle().run();
                }, () -> battle.canRun()), 0.025f, 0.875f, 0.175f, 0.975f);
		addComponent(new ConditionalTextButton("Fight", BUTTON_PROPERTIES, HOVER_PROPERTIES, () -> {
                    toSelectMoveCatState();
                }, () -> hasFightMoves && (state == State.SELECT_ACTION || state == State.SELECT_ITEM_CATEGORY || state == State.SELECT_ITEM)), 0.675f, 0.025f, 0.925f, 0.125f);
		addComponent(new ConditionalTextButton("Cancel", BUTTON_PROPERTIES, HOVER_PROPERTIES, () -> {
                    toSelectActionState();
                    hasCategories = null;
                }, () -> state == State.SELECT_MOVE_CATEGORY || state == State.SELECT_MOVE), 0.675f, 0.025f, 0.925f, 0.125f);
		addComponent(new ConditionalTextButton("Items", BUTTON_PROPERTIES, HOVER_PROPERTIES, () -> {
                    toSelectItemCatState();
                }, () -> hasItemMoves && (state == State.SELECT_ACTION || state == State.SELECT_MOVE_CATEGORY || state == State.SELECT_MOVE)), 0.375f, 0.025f, 0.625f, 0.125f);
		addComponent(new ConditionalTextButton("Cancel", BUTTON_PROPERTIES, HOVER_PROPERTIES, () -> {
                    toSelectActionState();
                    hasCategories = null;
                }, () -> state == State.SELECT_ITEM_CATEGORY || state == State.SELECT_ITEM), 0.375f, 0.025f, 0.625f, 0.125f);
		addComponent(new ConditionalTextButton("Wait", BUTTON_PROPERTIES, HOVER_PROPERTIES, () -> {
                    battle.selectPlayerMove(new MoveSkipTurn());
                    toWaitingState();
                }, () -> state == State.SELECT_ACTION || state == State.SELECT_MOVE_CATEGORY || state == State.SELECT_MOVE || state == State.SELECT_ITEM_CATEGORY || state == State.SELECT_ITEM), 0.05f, 0.025f, 0.25f, 0.125f);
		FightMoveOption.Category[] fightCategories = FightMoveOption.Category.values();
		for(int index = 0; index < fightCategories.length; index++){
			addComponent(new FightCategoryButton(fightCategories[index]), 0.675f, 0.125f + (fightCategories.length - 1 - index) * 0.05f, 0.925f, 0.225f + (fightCategories.length - 1 - index) * 0.05f);
		}
		ItemMoveOption.Category[] itemCategories = ItemMoveOption.Category.values();
		for(int index = 0; index < itemCategories.length; index++){
			addComponent(new ItemCategoryButton(itemCategories[index]), 0.375f, 0.125f + (itemCategories.length - 1 - index) * 0.05f, 0.625f, 0.225f + (itemCategories.length - 1 - index) * 0.05f);
		}
                addComponent(armComponent, 0.3f, 0.875f, 0.5f, 0.975f);
	}
	
	@Override
	public void render(GuiRenderer renderer){
		super.render(renderer);
		if(state == State.SELECT_MOVE){
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
                    armComponent.setText(arm);
		}
                else {
                    armComponent.setNoArms();
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
        
        protected static final Properties FIGHT_PROPERTIES = Properties.createButton(new java.awt.Color(150, 100, 0), new java.awt.Color(50, 50, 0), new java.awt.Color(50, 50, 0));
	protected static final Properties FIGHT_HOVER_PROPERTIES = Properties.createButton(new java.awt.Color(200, 140, 0), new java.awt.Color(80, 80, 0), new java.awt.Color(80, 80, 0));
	
	protected class FightCategoryButton extends ConditionalTextButton {
		
		protected final FightMoveOption.Category category;
		
		protected FightCategoryButton(FightMoveOption.Category category){
			super(category.getDisplayName(), FIGHT_PROPERTIES, FIGHT_HOVER_PROPERTIES, new FightCategoryClickAction(), new FightCategoryCondition());
			this.category = category;
                        ((FightCategoryClickAction)this.clickAction).button = this;
                        ((FightCategoryCondition)this.condition).button = this;
		}
                
                protected GuiBattle getBattle(){
                    return GuiBattle.this;
                }
	}
        
        protected static class FightCategoryClickAction implements Runnable {
            
            protected FightCategoryButton button;
            
            @Override
            public void run(){
                button.getBattle().toSelectMoveState(button.category);
            }
        }
        
        protected static class FightCategoryCondition implements Condition {
            
            protected FightCategoryButton button;
            
            @Override
            public boolean isTrue(){
                return (button.getBattle().state == State.SELECT_MOVE_CATEGORY || button.getBattle().state == State.SELECT_MOVE) && button.getBattle().hasCategories[button.category.ordinal()];
            }
        }
	
	protected static final Color ITEM_CATEGORY_BUTTON_COLOR = new Color(0, 150, 150);
	protected static final Color ITEM_CATEGORY_BORDER_COLOR = new Color(0, 50, 50);
	protected static final Color ITEM_CATEGORY_TEXT_COLOR = new Color(0, 50, 50);  
        
        protected static final Properties ITEM_PROPERTIES = Properties.createButton(new java.awt.Color(0, 150, 150), new java.awt.Color(0, 50, 50), new java.awt.Color(0, 50, 50));
	protected static final Properties ITEM_HOVER_PROPERTIES = Properties.createButton(new java.awt.Color(0, 200, 200), new java.awt.Color(0,  80, 80), new java.awt.Color(0, 80, 80));
	
	protected class ItemCategoryButton extends ConditionalTextButton {
		
		protected final ItemMoveOption.Category category;
		
		protected ItemCategoryButton(ItemMoveOption.Category category){
			super(category.getDisplayName(), ITEM_PROPERTIES, ITEM_HOVER_PROPERTIES, new ItemCategoryClickAction(), new ItemCategoryCondition());
			this.category = category;
                        ((ItemCategoryClickAction)this.clickAction).button = this;
                        ((ItemCategoryCondition)this.condition).button = this;
		}

                protected GuiBattle getBattle(){
                    return GuiBattle.this;
                }
	}
        
        protected static class ItemCategoryClickAction implements Runnable {
            
            protected ItemCategoryButton button;
                    
            @Override
            public void run() {
		button.getBattle().toSelectItemState(button.category);
            }
        }
        
        protected static class ItemCategoryCondition implements Condition {
            
            protected ItemCategoryButton button;
            
            @Override
            public boolean isTrue(){
                return (button.getBattle().state == State.SELECT_ITEM_CATEGORY || button.getBattle().state == State.SELECT_ITEM) && button.getBattle().hasCategories[button.category.ordinal()];
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
        
    protected class ArmComponent extends WrapperComponent<TextButton> {
            
        public ArmComponent() {
            super(null);
        }
        
        @Override
        public boolean isActive(){
            return GuiBattle.this.state == State.SELECT_ACTION;
        }
        
        public void setNoArms(){
            setComponent(null);
        }
        
        public void setText(String text){
            if(component == null){
                setComponent(new TextButton(text, BUTTON_PROPERTIES, HOVER_PROPERTIES, () -> {
                    battle.getChoosingPlayer().swapArm();
                    toSelectActionState();
                }));
            }
            else {
                component.setText(text);
            }
        }
    }
    
    protected class FightMovesComponent extends WrapperComponent<FightMovesMenu> {
        
        public FightMovesComponent() {
            super(new FightMovesMenu());
        }
        
    }
    
    protected static final Properties FIGHT_MOVE_DEFAULT = Properties.createButton(new java.awt.Color(), new java.awt.Color());
    protected static final Properties FIGHT_MOVE_HOVER = Properties.createButton(new java.awt.Color(), new java.awt.Color());
    protected static final Properties FIGHT_MOVE_INACTIVE = Properties.createButton(new java.awt.Color(), new java.awt.Color());
    
    protected class FightMovesMenu extends GuiMenu {

        @Override
        protected void addComponents() {}
        
        public void setMoves(FightMoveOption[] moves){
            for(FightMoveOption move : moves){
                addComponent(new ActivatableTextButton(move.getName(), FIGHT_MOVE_DEFAULT, FIGHT_MOVE_HOVER, FIGHT_MOVE_INACTIVE, new FightMoveClickAction(move), () -> move.canCast(battle.getChoosingPlayer(), battle)), 0, , 1, );
            }
        }
    }
    
    protected class FightMoveClickAction implements Runnable {
        
        protected FightMoveOption move;
        
        protected FightMoveClickAction(FightMoveOption move){
            this.move = move;
        }

        @Override
        public void run(){
            if(move.canCast(battle.getChoosingPlayer(), battle)){
                selectMove(move);
            }
        }
    }
}