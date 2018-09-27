package nl.knokko.gui.battle;

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
import nl.knokko.gui.util.TextBuilder.Properties;
import nl.knokko.gui.util.Condition;
import nl.knokko.input.MouseInput;
import nl.knokko.main.Game;
import nl.knokko.util.Maths;
import nl.knokko.util.color.Color;
import nl.knokko.util.color.ColorAlpha;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class GuiBattle extends GuiMenu {
	
	protected static final java.awt.Color BUTTON_COLOR = new java.awt.Color(80, 0, 220);
	protected static final java.awt.Color BORDER_COLOR = new java.awt.Color(30, 0, 100);
	protected static final java.awt.Color BUTTON_HOVER_COLOR = new java.awt.Color(100, 0, 255);
	protected static final java.awt.Color BORDER_HOVER_COLOR = new java.awt.Color(40, 0, 120);
	
	protected static final Properties BUTTON_PROPERTIES = Properties.createButton(BUTTON_COLOR, BORDER_COLOR);
	protected static final Properties HOVER_PROPERTIES = Properties.createButton(BUTTON_HOVER_COLOR, BORDER_HOVER_COLOR);
	
	protected final Battle battle;
	
	protected State selectState;
        
    protected final ArmComponent armComponent = new ArmComponent();
    protected final FightMovesComponent fightMovesComponent = new FightMovesComponent();
    protected final ItemMovesComponent itemMovesComponent = new ItemMovesComponent();
    protected final MoveTargetsComponent moveTargetsComponent = new MoveTargetsComponent();
    protected final ItemTargetsComponent itemTargetsComponent = new ItemTargetsComponent();
	
	protected FightMoveOption selectedMove;
	protected ItemMoveOption selectedItem;
	
	protected boolean hasFightMoves;
	protected boolean hasItemMoves;
	protected boolean[] hasCategories;

	public GuiBattle(Battle battle) {
		this.battle = battle;
		this.selectState = State.WAITING;
	}
	
	@Override
	protected void addComponents(){
		addComponent(new ConditionalColorComponent(Color.ORANGE, () -> selectState == State.SELECT_MOVE_CATEGORY || selectState == State.SELECT_MOVE), 0.675f, 0f, 0.925f, 0.4f);
		addComponent(new ConditionalColorComponent(Color.BLUE, () -> selectState == State.SELECT_MOVE), 0.425f, 0.125f, 0.675f, 0.775f);
		addComponent(new ConditionalTextButton("Stop", BUTTON_PROPERTIES, HOVER_PROPERTIES, () -> {
                    Game.stop(true);
                }, () -> selectState != State.WAITING), 0.6f, 0.875f, 0.9f, 0.975f);
		addComponent(new ConditionalTextButton("Run", BUTTON_PROPERTIES, HOVER_PROPERTIES, () -> {
                    Game.getBattle().run();
                }, () -> battle.canRun()), 0.025f, 0.875f, 0.175f, 0.975f);
		addComponent(new ConditionalTextButton("Fight", BUTTON_PROPERTIES, HOVER_PROPERTIES, () -> {
                    toSelectMoveCatState();
                }, () -> hasFightMoves && (selectState == State.SELECT_ACTION || selectState == State.SELECT_ITEM_CATEGORY || selectState == State.SELECT_ITEM)), 0.675f, 0.025f, 0.925f, 0.125f);
		addComponent(new ConditionalTextButton("Cancel", BUTTON_PROPERTIES, HOVER_PROPERTIES, () -> {
                    toSelectActionState();
                    hasCategories = null;
                }, () -> selectState == State.SELECT_MOVE_CATEGORY || selectState == State.SELECT_MOVE), 0.675f, 0.025f, 0.925f, 0.125f);
		addComponent(new ConditionalTextButton("Items", BUTTON_PROPERTIES, HOVER_PROPERTIES, () -> {
                    toSelectItemCatState();
                }, () -> hasItemMoves && (selectState == State.SELECT_ACTION || selectState == State.SELECT_MOVE_CATEGORY || selectState == State.SELECT_MOVE)), 0.375f, 0.025f, 0.625f, 0.125f);
		addComponent(new ConditionalTextButton("Cancel", BUTTON_PROPERTIES, HOVER_PROPERTIES, () -> {
                    toSelectActionState();
                    hasCategories = null;
                }, () -> selectState == State.SELECT_ITEM_CATEGORY || selectState == State.SELECT_ITEM), 0.375f, 0.025f, 0.625f, 0.125f);
		addComponent(new ConditionalTextButton("Wait", BUTTON_PROPERTIES, HOVER_PROPERTIES, () -> {
                    battle.selectPlayerMove(new MoveSkipTurn());
                    toWaitingState();
                }, () -> selectState == State.SELECT_ACTION || selectState == State.SELECT_MOVE_CATEGORY || selectState == State.SELECT_MOVE || selectState == State.SELECT_ITEM_CATEGORY || selectState == State.SELECT_ITEM), 0.05f, 0.025f, 0.25f, 0.125f);
		FightMoveOption.Category[] fightCategories = FightMoveOption.Category.values();
		for(int index = 0; index < fightCategories.length; index++){
			addComponent(new FightCategoryButton(fightCategories[index]), 0.675f, 0.125f + (fightCategories.length - 1 - index) * 0.05f, 0.925f, 0.225f + (fightCategories.length - 1 - index) * 0.05f);
		}
		ItemMoveOption.Category[] itemCategories = ItemMoveOption.Category.values();
		for(int index = 0; index < itemCategories.length; index++){
			addComponent(new ItemCategoryButton(itemCategories[index]), 0.375f, 0.125f + (itemCategories.length - 1 - index) * 0.05f, 0.625f, 0.225f + (itemCategories.length - 1 - index) * 0.05f);
		}
        addComponent(armComponent, 0.3f, 0.875f, 0.5f, 0.975f);
        addComponent(fightMovesComponent, 0.425f, 0.125f, 0.575f, 0.8f);
        addComponent(itemMovesComponent, 0.225f, 0.125f, 0.425f, 0.8f);
        addComponent(moveTargetsComponent, 0f, 0.15f, 1f, 0.85f);
	}
	
	@Override
	public void update(){
        super.update();
		if(selectState == State.WAITING && battle.getChoosingPlayer() != null)
			toSelectActionState();
	}
	
    @Override
	public void click(float x, float y, int button){
        super.click(x, y, button);
        //TODO prevent the part below from being executed if a button was clicked on
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
	
    @Override
	public Color getBackgroundColor(){
		return null;
	}
	
	protected void selectMove(BattleMove move){
		battle.selectPlayerMove(move);
		toWaitingState();
	}
	
	protected void toWaitingState(){
		selectState = State.WAITING;
	}
	
	protected void toSelectActionState(){
		selectState = State.SELECT_ACTION;
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
		selectState = State.SELECT_MOVE_CATEGORY;
		hasCategories = new boolean[FightMoveOption.Category.values().length];
		for(int i = 0; i < hasCategories.length; i++)
			hasCategories[i] = battle.getChoosingPlayer().getMoves(FightMoveOption.Category.values()[i]).length > 0;
	}
	
	protected void toSelectItemCatState(){
		selectState = State.SELECT_ITEM_CATEGORY;
		hasCategories = new boolean[ItemMoveOption.Category.values().length];
		for(int i = 0; i < hasCategories.length; i++)
			hasCategories[i] = battle.getChoosingPlayer().getItems(ItemMoveOption.Category.values()[i]).length > 0;
	}
	
	protected void toSelectMoveState(FightMoveOption.Category category){
		selectState = State.SELECT_MOVE;
		FightMoveOption[] moves = battle.getChoosingPlayer().getMoves(category);
		fightMovesComponent.setMoves(moves);
	}
	
	protected void toSelectItemState(ItemMoveOption.Category category){
		selectState = State.SELECT_ITEM;
		ItemMoveOption[] items = battle.getChoosingPlayer().getItems(category);
		itemMovesComponent.setItems(items);
	}
	
	protected void toSelectTargetState(FightMoveOption move){
		selectState = State.SELECT_FIGHT_TARGET;
		selectedMove = move;
        moveTargetsComponent.setTargets();
	}
	
	protected void toSelectTargetState(ItemMoveOption item){
		selectState = State.SELECT_ITEM_TARGET;
		selectedItem = item;
		itemTargetsComponent.setTargets();
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
                return (button.getBattle().selectState == State.SELECT_MOVE_CATEGORY || button.getBattle().selectState == State.SELECT_MOVE) && button.getBattle().hasCategories[button.category.ordinal()];
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
            return (button.getBattle().selectState == State.SELECT_ITEM_CATEGORY || button.getBattle().selectState == State.SELECT_ITEM) && button.getBattle().hasCategories[button.category.ordinal()];
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
	
	protected static final Color ITEM_BUTTON_COLOR = new Color(0, 0, 200);
	protected static final Color ITEM_BORDER_COLOR = new Color(0, 0, 100);
	protected static final Color ITEM_TEXT_COLOR = new Color(0, 0, 50);
	
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
        
    protected class ArmComponent extends WrapperComponent<TextButton> {
            
        public ArmComponent() {
            super(null);
        }
        
        @Override
        public boolean isActive(){
            return GuiBattle.this.selectState == State.SELECT_ACTION;
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
        
        public void setMoves(FightMoveOption[] moves){
        	if(component == null){
        		component = new FightMovesMenu();
        		component.setState(state);
        	}
        	component.setMoves(moves);
        }
    }
    
    protected static final Properties FIGHT_MOVE_DEFAULT = Properties.createButton(new java.awt.Color(0, 0, 150), new java.awt.Color(0, 0, 50));
    protected static final Properties FIGHT_MOVE_HOVER = Properties.createButton(new java.awt.Color(0, 0, 200), new java.awt.Color(0, 0, 70));
    protected static final Properties FIGHT_MOVE_INACTIVE = Properties.createButton(new java.awt.Color(0, 0, 150, 100), new java.awt.Color(0, 0, 150, 100));
    
    protected class FightMovesMenu extends GuiMenu {

        @Override
        protected void addComponents() {}
        
        public void setMoves(FightMoveOption[] moves){
        	components.clear();
        	int index = 0;
            for(FightMoveOption move : moves){
                addComponent(new ActivatableTextButton(move.getName(), FIGHT_MOVE_DEFAULT, FIGHT_MOVE_HOVER, FIGHT_MOVE_INACTIVE, new FightMoveClickAction(move), () -> move.canCast(battle.getChoosingPlayer(), battle)), 0, 1 - (1 + index) * 0.2f, 1, 1 - index * 0.2f);
                index++;
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
                toSelectTargetState(move);
            }
        }
    }
    
    protected class ItemMovesComponent extends WrapperComponent<ItemMovesMenu> {
        
        public ItemMovesComponent() {
            super(new ItemMovesMenu());
        }
        
        public void setItems(ItemMoveOption[] moves){
        	if(component == null){
        		component = new ItemMovesMenu();
        		component.setState(state);
        	}
        	component.setItems(moves);
        }
    }
    
    protected static final Properties ITEM_MOVE_DEFAULT = Properties.createButton(new java.awt.Color(0, 0, 150), new java.awt.Color(0, 0, 50));
    protected static final Properties ITEM_MOVE_HOVER = Properties.createButton(new java.awt.Color(0, 0, 200), new java.awt.Color(0, 0, 70));
    protected static final Properties ITEM_MOVE_INACTIVE = Properties.createButton(new java.awt.Color(0, 0, 150, 100), new java.awt.Color(0, 0, 150, 100));
    
    protected class ItemMovesMenu extends GuiMenu {

        @Override
        protected void addComponents() {}
        
        public void setItems(ItemMoveOption[] moves){
        	components.clear();
        	int index = 0;
            for(ItemMoveOption move : moves){
                addComponent(new ActivatableTextButton(move.getName(), ITEM_MOVE_DEFAULT, ITEM_MOVE_HOVER, ITEM_MOVE_INACTIVE, new ItemMoveClickAction(move), () -> move.canUse(battle.getChoosingPlayer().getInventory(), battle.getChoosingPlayer(), battle)), 0, 1 - (1 + index) * 0.2f, 1, 1 - index * 0.2f);
                index++;
            }
        }
    }
    
    protected class ItemMoveClickAction implements Runnable {
        
        protected ItemMoveOption move;
        
        protected ItemMoveClickAction(ItemMoveOption move){
            this.move = move;
        }

        @Override
        public void run(){
            if(move.canUse(battle.getChoosingPlayer().getInventory(), battle.getChoosingPlayer(), battle)){
                toSelectTargetState(move);
            }
        }
    }
    
    protected class MoveTargetsComponent extends WrapperComponent<MoveTargetsMenu> {

		public MoveTargetsComponent() {
			super(null);
		}
        
        @Override
        public boolean isActive(){
            return GuiBattle.this.selectState == State.SELECT_FIGHT_TARGET;
        }
    	
		public void setTargets(){
			if(component == null){
				component = new MoveTargetsMenu();
				component.setState(state);
			}
			component.setTargets();
		}
    }
    
    protected static final Properties TARGET_ENEMY_DEFAULT = Properties.createButton(new java.awt.Color(200, 100, 0), new java.awt.Color(100, 50, 0));
    protected static final Properties TARGET_ENEMY_HOVER = Properties.createButton(new java.awt.Color(250, 125, 0), new java.awt.Color(125, 60, 0));
    protected static final Properties TARGET_ENEMY_DISABLED = Properties.createButton(new java.awt.Color(200, 100, 0, 100), new java.awt.Color(100, 50, 0, 100));
    
    protected static final Properties TARGET_PLAYER_DEFAULT = Properties.createButton(new java.awt.Color(0, 100, 200), new java.awt.Color(0, 50, 100));
    protected static final Properties TARGET_PLAYER_HOVER = Properties.createButton(new java.awt.Color(0, 125, 250), new java.awt.Color(0, 60, 125));
    protected static final Properties TARGET_PLAYER_DISABLED = Properties.createButton(new java.awt.Color(0, 100, 200, 100), new java.awt.Color(0, 50, 100, 100));
    
    protected class MoveTargetsMenu extends GuiMenu {
        
        protected TextButton returnButton;
        
        protected MoveTargetsMenu(){
            returnButton = new TextButton("Back", BUTTON_PROPERTIES, HOVER_PROPERTIES, new Runnable(){
                @Override
                public void run() {
                    toSelectMoveState(selectedMove.getCategory());
                }
            });
        }
    	
    	@Override
    	protected void addComponents(){}
    	
    	public void setTargets(){
    		components.clear();
            addComponent(returnButton, 0.1f, 0.45f, 0.25f, 0.55f);
    		BattleCreature[] players = battle.getPlayers();
    		BattleCreature[] opponents = battle.getOpponents();
            for(int index = 0; index < players.length; index++){
                addComponent(new PlayerButton(players[index]), 0.1f + 0.2f * index, 0.7f, 0.25f + 0.2f * index, 0.8f);
            }
            for(int index = 0; index < opponents.length; index++){
                addComponent(new EnemyButton(opponents[index]), 0.1f + 0.2f * index, 0.2f, 0.25f + 0.2f * index, 0.3f);
            }
    	}
    	
    	protected class PlayerButton extends ActivatableTextButton {

			public PlayerButton(BattleCreature player) {
				super(player.getName(), TARGET_PLAYER_DEFAULT, TARGET_PLAYER_HOVER, TARGET_PLAYER_DISABLED, new TargetClickAction(player), () -> selectedMove.canCast(battle.getChoosingPlayer(), player, battle));
			}
    	}
        
        protected class EnemyButton extends ActivatableTextButton {

			public EnemyButton(BattleCreature enemy) {
				super(enemy.getName(), TARGET_ENEMY_DEFAULT, TARGET_ENEMY_HOVER, TARGET_ENEMY_DISABLED, new TargetClickAction(enemy), () -> selectedMove.canCast(battle.getChoosingPlayer(), enemy, battle));
			}
    	}
    	
    	protected class TargetClickAction implements Runnable {
    		
    		protected BattleCreature target;
    		
    		protected TargetClickAction(BattleCreature target){
    			this.target = target;
    		}
    		
    		@Override
    		public void run(){
    			if(selectedMove.canCast(battle.getChoosingPlayer(), target, battle)){
    				battle.selectPlayerMove(selectedMove.createMove(battle.getChoosingPlayer(), target, battle));
    				toWaitingState();
    			}
    		}
    	}
    }
    
    protected class ItemTargetsComponent extends WrapperComponent<ItemTargetsMenu> {

		public ItemTargetsComponent() {
			super(null);
		}
        
        @Override
        public boolean isActive(){
            return GuiBattle.this.selectState == State.SELECT_ITEM_TARGET;
        }
    	
		public void setTargets(){
			if(component == null){
				component = new ItemTargetsMenu();
				component.setState(state);
			}
			component.setTargets();
		}
    }
    
    protected class ItemTargetsMenu extends GuiMenu {
        
        protected TextButton returnButton;
        
        protected ItemTargetsMenu(){
            returnButton = new TextButton("Back", BUTTON_PROPERTIES, HOVER_PROPERTIES, new Runnable(){
                @Override
                public void run() {
                    toSelectItemState(selectedItem.getCategory());
                }
            });
        }
    	
    	@Override
    	protected void addComponents(){}
    	
    	public void setTargets(){
    		components.clear();
            addComponent(returnButton, 0.1f, 0.45f, 0.25f, 0.55f);
    		BattleCreature[] players = battle.getPlayers();
    		BattleCreature[] opponents = battle.getOpponents();
            for(int index = 0; index < players.length; index++){
                addComponent(new PlayerButton(players[index]), 0.1f + 0.2f * index, 0.7f, 0.25f + 0.2f * index, 0.8f);
            }
            for(int index = 0; index < opponents.length; index++){
                addComponent(new EnemyButton(opponents[index]), 0.1f + 0.2f * index, 0.2f, 0.25f + 0.2f * index, 0.3f);
            }
    	}
    	
    	protected class PlayerButton extends ActivatableTextButton {

			public PlayerButton(BattleCreature player) {
				super(player.getName(), TARGET_PLAYER_DEFAULT, TARGET_PLAYER_HOVER, TARGET_PLAYER_DISABLED, new TargetClickAction(player), () -> selectedItem.canUse(battle.getChoosingPlayer().getInventory(), player, battle));
			}
    	}
        
        protected class EnemyButton extends ActivatableTextButton {

			public EnemyButton(BattleCreature enemy) {
				super(enemy.getName(), TARGET_ENEMY_DEFAULT, TARGET_ENEMY_HOVER, TARGET_ENEMY_DISABLED, new TargetClickAction(enemy), () -> selectedItem.canUse(battle.getChoosingPlayer().getInventory(), enemy, battle));
			}
    	}
    	
    	protected class TargetClickAction implements Runnable {
    		
    		protected BattleCreature target;
    		
    		protected TargetClickAction(BattleCreature target){
    			this.target = target;
    		}
    		
    		@Override
    		public void run(){
    			if(selectedMove.canCast(battle.getChoosingPlayer(), target, battle)){
    				battle.selectPlayerMove(selectedItem.createMove(battle.getChoosingPlayer().getInventory(), battle.getChoosingPlayer(), target, battle));
    				toWaitingState();
    			}
    		}
    	}
    }
}