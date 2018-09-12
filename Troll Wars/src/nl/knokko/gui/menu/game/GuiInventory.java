package nl.knokko.gui.menu.game;

import org.lwjgl.util.vector.Vector2f;

import nl.knokko.gamestate.StateGameMenu;
import nl.knokko.gui.GuiBase;
import nl.knokko.gui.button.ButtonCloseMenu;
import nl.knokko.gui.button.ButtonLink;
import nl.knokko.inventory.InventoryTypeBase;
import nl.knokko.main.Game;
import nl.knokko.render.main.GuiRenderer;
import nl.knokko.texture.SizedTexture;
import nl.knokko.util.color.Color;
import nl.knokko.util.resources.Resources;

public class GuiInventory extends GuiBase {
	
	private static final Color BUTTON_COLOR = new Color(100, 150, 0);
	private static final Color BORDER_COLOR = new Color(40, 50, 0);
	private static final Color BACKGROUND_COLOR = new Color(150, 100, 0);
	
	private final StateGameMenu state;
	
	private SizedTexture materialsTexture;
	private SizedTexture equipmentTexture;
	private SizedTexture consumablesTexture;
	
	private boolean hasTextures;

	public GuiInventory(StateGameMenu menu) {
		state = menu;
	}
	
	@Override
	public void addButtons(){
		/*
		addButton(new InventoryTypeButton(new Vector2f(-0.65f, 0.7f), null));
		addButton(new InventoryTypeButton(new Vector2f(-0.65f, 0.45f), InventoryTypeBase.MATERIAL));
		addButton(new InventoryTypeButton(new Vector2f(-0.65f, 0.2f), InventoryTypeBase.EQUIPMENT));
		addButton(new InventoryTypeButton(new Vector2f(-0.65f, -0.05f), InventoryTypeBase.CONSUMABLE));
		*/
		addButton(new ButtonLink(new Vector2f(-0.65f, -0.6f), new Vector2f(0.3f, 0.1f), BUTTON_COLOR, BORDER_COLOR, Color.BLACK, "Back to menu", state.getGameMenu(), state));
		addButton(new ButtonCloseMenu(new Vector2f(-0.65f, -0.85f), new Vector2f(0.3f, 0.1f), BUTTON_COLOR, BORDER_COLOR));
	}
	
	@Override
	public void render(GuiRenderer renderer){
		renderer.start(this);
		renderer.render(textures, buttons, this, false);
		if(!hasTextures){
			hasTextures = true;
			materialsTexture = Resources.createInventoryTexture(Game.getPlayerInventory(), Game.getPlayerInventory().getItems(InventoryTypeBase.MATERIAL));
			equipmentTexture = Resources.createInventoryTexture(Game.getPlayerInventory(), Game.getPlayerInventory().getItems(InventoryTypeBase.EQUIPMENT));
			consumablesTexture = Resources.createInventoryTexture(Game.getPlayerInventory(), Game.getPlayerInventory().getItems(InventoryTypeBase.CONSUMABLE));
		}
		if(materialsTexture != null)
			renderer.renderTextures(new Vector2f(-0.1f, 1 - materialsTexture.getRelativeHeight()), new Vector2f(0.2f, materialsTexture.getRelativeHeight()), materialsTexture);
		if(equipmentTexture != null)
			renderer.renderTextures(new Vector2f(0.35f, 1 - equipmentTexture.getRelativeHeight()), new Vector2f(0.2f, equipmentTexture.getRelativeHeight()), equipmentTexture);
		if(consumablesTexture != null)
			renderer.renderTextures(new Vector2f(0.8f, 1 - consumablesTexture.getRelativeHeight()), new Vector2f(0.2f, consumablesTexture.getRelativeHeight()), consumablesTexture);
		renderer.stop();
	}
	
	@Override
	public Color getBackGroundColor(){
		return BACKGROUND_COLOR;
	}
	
	/*
	private class InventoryTypeButton extends ButtonText {
		
		private SizedTexture texture;
		private InventoryTypeBase type;

		public InventoryTypeButton(Vector2f centre, InventoryTypeBase type) {
			super(centre, new Vector2f(0.3f, 0.1f), BUTTON_COLOR, BORDER_COLOR, Color.BLACK, type != null ? type.name().toLowerCase() : "all items");
			this.type = type;
		}

		@Override
		public void leftClick(float x, float y) {
			if(texture == null)
				texture = Resources.createInventoryTexture(Game.getPlayerInventory(), type != null ? Game.getPlayerInventory().getItems(type) : Game.getPlayerInventory().getItems());
			currentTexture = texture;
		}
		
	}
	*/
}
