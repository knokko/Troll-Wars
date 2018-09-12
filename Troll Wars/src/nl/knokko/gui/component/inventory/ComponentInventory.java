package nl.knokko.gui.component.inventory;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

import nl.knokko.gui.component.AbstractGuiComponent;
import nl.knokko.gui.component.menu.GuiMenu;
import nl.knokko.gui.mousecode.MouseCode;
import nl.knokko.gui.render.GuiRenderer;
import nl.knokko.gui.texture.GuiTexture;
import nl.knokko.gui.util.TextBuilder;
import nl.knokko.gui.util.TextBuilder.HorAlignment;
import nl.knokko.gui.util.TextBuilder.Properties;
import nl.knokko.gui.util.TextBuilder.VerAlignment;
import nl.knokko.items.Item;

public class ComponentInventory extends GuiMenu {
	
	public static final nl.knokko.util.color.Color BACKGROUND = new nl.knokko.util.color.Color(200, 200, 50);
	
	private final ClickAction action;
	private List<Item> items;

	public ComponentInventory(List<Item> itemsToShow, ClickAction clickAction) {
		action = clickAction;
		items = itemsToShow;
	}

	@Override
	protected void addComponents() {
		if(items != null){
			int index = 0;
			for(Item item : items){
				addComponent(new ItemComponent(item), 0, 1 - index * 0.1f, 1, 0.875f - index * 0.1f);
				index++;
			}
		}
	}
	
	@Override
	public nl.knokko.util.color.Color getBackgroundColor(){
		return BACKGROUND;
	}
	
	public void refresh(List<Item> newItemsToShow){
		components.clear();
		items = newItemsToShow;
		addComponents();
	}
	
	private class ItemComponent extends AbstractGuiComponent {
		
		private final Item item;
		
		private GuiTexture defaultTexture;
		private GuiTexture hoverTexture;
		
		private ItemComponent(Item item){
			this.item = item;
		}

		@Override
		public void init() {
			defaultTexture = state.getWindow().getTextureLoader().loadTexture(createItemImage(item, ITEM_NAME_PROPERTIES));
			hoverTexture = state.getWindow().getTextureLoader().loadTexture(createItemImage(item, HOVER_ITEM_NAME_PROPERTIES));
		}

		@Override
		public void update() {}

		@Override
		public void render(GuiRenderer renderer) {
			if(state.isMouseOver())
				renderer.renderTexture(hoverTexture, 0, 0, 1, 1);
			else
				renderer.renderTexture(defaultTexture, 0, 0, 1, 1);
		}

		@Override
		public void click(float x, float y, int button) {
			if(action != null && button == MouseCode.BUTTON_LEFT)
				action.onClick(item);
		}

		@Override
		public void clickOut(int button) {}

		@Override
		public boolean scroll(float amount) {
			return false;
		}

		@Override
		public void keyPressed(int keyCode) {}

		@Override
		public void keyPressed(char character) {}

		@Override
		public void keyReleased(int keyCode) {}
	}
	
	public static interface ClickAction {
		
		void onClick(Item item);
	}
	
	private static BufferedImage createItemImage(Item item, Properties properties){
		BufferedImage itemImage = item.getTexture().getImage();
		BufferedImage textImage = TextBuilder.createTexture(item.getName(), properties, itemImage.getWidth() * 3, itemImage.getHeight());
		BufferedImage image = new BufferedImage(itemImage.getWidth() * 4, itemImage.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		g.setColor(new Color(250, 250, 0));
		g.fillRect(0, 0, itemImage.getWidth(), itemImage.getHeight());
		g.drawImage(itemImage, 0, 0, null);
		g.drawImage(textImage, itemImage.getWidth(), 0, null);
		g.dispose();
		return image;
	}
	
	private static final Properties ITEM_NAME_PROPERTIES = new Properties(new Font("Italic", 0, 25), Color.BLACK, new Color(150, 150, 60), new Color(0, 200, 200), HorAlignment.LEFT, VerAlignment.MIDDLE, 0.025f, 0.05f, 0.05f, 0.1f);
	private static final Properties HOVER_ITEM_NAME_PROPERTIES = new Properties(new Font("Italic", 0, 25), Color.BLACK, new Color(200, 200, 90), new Color(0, 240, 240), HorAlignment.LEFT, VerAlignment.MIDDLE, 0.025f, 0.05f, 0.05f, 0.1f);
}