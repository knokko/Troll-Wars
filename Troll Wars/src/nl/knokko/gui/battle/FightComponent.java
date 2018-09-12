package nl.knokko.gui.battle;

import nl.knokko.battle.move.FightMoveOption;
import nl.knokko.gui.color.GuiColor;
import nl.knokko.gui.color.SimpleGuiColor;
import nl.knokko.gui.component.menu.GuiMenu;
import nl.knokko.gui.component.text.TextButton;
import nl.knokko.gui.util.TextBuilder.Properties;

public class FightComponent extends GuiMenu {
	
	protected final GuiBattle guiBattle;
	
	protected FightButton fightButton;
	protected CancelButton cancelButton;
	protected FightCategoriesSubComponent fightCategories;

	public FightComponent(GuiBattle guiBattle) {
		this.guiBattle = guiBattle;
	}
	
	public void setMoves(FightMoveOption[] moves){
		
	}

	@Override
	protected void addComponents() {
		fightButton = new FightButton();
		cancelButton = new CancelButton();
		fightCategories = new FightCategoriesSubComponent();
		addComponent(fightButton);
		addComponent(cancelButton);
		addComponent(fightCategories);
	}
	
	public static final Properties FIGHT_BUTTON_PROPERTIES = Properties.createButton(GuiBattle.BUTTON_COLOR, GuiBattle.BORDER_COLOR);
	public static final Properties FIGHT_HOVER_PROPERTIES = Properties.createButton(GuiBattle.BUTTON_HOVER_COLOR, GuiBattle.BORDER_HOVER_COLOR);
	
	private class FightButton extends SubComponent {

		public FightButton() {
			super(new TextButton("Fight", FIGHT_BUTTON_PROPERTIES, FIGHT_HOVER_PROPERTIES, new Runnable(){

				@Override
				public void run() {
					guiBattle.toSelectMoveCatState();
				}
			}), 0.675f, 0.025f, 0.925f, 0.125f);
		}
		
		@Override
		protected boolean isActive(){
			return (guiBattle.state == GuiBattle.State.SELECT_ACTION || guiBattle.state == GuiBattle.State.SELECT_ITEM_CATEGORY || guiBattle.state == GuiBattle.State.SELECT_ITEM) && guiBattle.hasFightMoves;
		}
	}
	
	private class CancelButton extends SubComponent {

		public CancelButton() {
			super(new TextButton("Cancel", FIGHT_BUTTON_PROPERTIES, FIGHT_HOVER_PROPERTIES, new Runnable(){

				@Override
				public void run() {
					guiBattle.toSelectActionState();
					guiBattle.hasCategories = null;
				}
				
			}), 0.675f, 0.025f, 0.925f, 0.125f);
		}
		
		@Override
		protected boolean isActive(){
			return guiBattle.state == GuiBattle.State.SELECT_MOVE_CATEGORY || guiBattle.state == GuiBattle.State.SELECT_MOVE;
		}
	}
	
	private class FightCategoriesSubComponent extends SubComponent {

		public FightCategoriesSubComponent() {
			super(new FightCategoriesComponent(), 0.675f, 0.175f, 0.925f, 0.325f);
		}
		
		@Override
		protected boolean isActive(){
			return guiBattle.state == GuiBattle.State.SELECT_MOVE_CATEGORY || guiBattle.state == GuiBattle.State.SELECT_MOVE;
		}
	}
	
	public static final GuiColor FIGHT_CATEGORY_BACKGROUND = new SimpleGuiColor(250, 100, 0);
	
	private class FightCategoriesComponent extends GuiMenu {

		@Override
		protected void addComponents() {
			FightMoveOption.Category[] categories = FightMoveOption.Category.values();
			for(int index = 0; index < categories.length; index++)
				addComponent(new FightCategoryButton(categories[index]), 0, 0.8f - 0.25f * index, 1, 1 - 0.25f * index);
		}
		
		@Override
		public GuiColor getBackgroundColor(){
			return FIGHT_CATEGORY_BACKGROUND;
		}
	}
	
	public static final Properties FIGHT_CATEGORY_BUTTON_PROPERTIES = Properties.createButton(GuiBattle.FIGHT_CATEGORY_BUTTON_COLOR, GuiBattle.FIGHT_CATEGORY_BORDER_COLOR, GuiBattle.FIGHT_CATEGORY_TEXT_COLOR);
	public static final Properties FIGHT_CATEGORY_HOVER_PROPERTIES = Properties.createButton(GuiBattle.FIGHT_CATEGORY_HOVER_BUTTON_COLOR, GuiBattle.FIGHT_CATEGORY_HOVER_BORDER_COLOR, GuiBattle.FIGHT_CATEGORY_HOVER_TEXT_COLOR);
	
	private class FightCategoryButton extends TextButton {

		public FightCategoryButton(final FightMoveOption.Category category) {
			super(category.getDisplayName(), FIGHT_CATEGORY_BUTTON_PROPERTIES, FIGHT_CATEGORY_HOVER_PROPERTIES, new Runnable(){

				@Override
				public void run() {
					guiBattle.toSelectMoveState(category);
				}
			});
		}
	}
}