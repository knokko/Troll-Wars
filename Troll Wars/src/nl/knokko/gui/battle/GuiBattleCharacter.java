package nl.knokko.gui.battle;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;

import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.battle.element.BattleElement;
import nl.knokko.battle.element.ElementalStatistics;
import nl.knokko.equipment.Equipment;
import nl.knokko.gui.Gui;
import nl.knokko.gui.button.ButtonText;
import nl.knokko.gui.button.IButton;
import nl.knokko.gui.texture.GuiTexture;
import nl.knokko.input.MouseClickEvent;
import nl.knokko.input.MouseInput;
import nl.knokko.items.Item;
import nl.knokko.main.Game;
import nl.knokko.main.GameScreen;
import nl.knokko.render.main.GuiRenderer;
import nl.knokko.texture.Texture;
import nl.knokko.util.color.Color;
import nl.knokko.util.resources.Resources;

public class GuiBattleCharacter implements Gui {
	
	protected static final Vector2f MAIN_SCALE = new Vector2f(0.3f, 0.1f);
	protected static final Color MAIN_COLOR = new Color(120, 80, 0);
	protected static final java.awt.Color BACKGROUND_COLOR = new java.awt.Color(150, 75, 0);
	protected static final java.awt.Color BACKGROUND_EDGE = new java.awt.Color(100, 50, 0);
	protected static final Font STATS_FONT = new Font("TimesRoman", Font.BOLD, 25);
	protected static final Font STATUS_FONT = new Font("TimesRoman", Font.BOLD, 30);
	protected static final Font ELEMENT_FONT = new Font("TimesRoman", 0, 20);
	
	protected final BattleCreature character;
	protected final GuiBattle returnGui;
	
	protected GuiTexture backGround;
	protected GuiTexture baseBackGround;
	protected GuiTexture equipmentBackGround;
	protected GuiTexture elementsBackGround;
	
	protected ButtonText returnButton;
	protected ButtonText baseButton;
	protected ButtonText equipmentButton;
	protected ButtonText elementsButton;
	
	protected State state;

	public GuiBattleCharacter(GuiBattle returnGui, BattleCreature character) {
		this.character = character;
		this.returnGui = returnGui;
		this.state = State.BASIC;
	}

	@Override
	public void render(GuiRenderer r) {
		r.start(this);
		render(r, backGround);
		if(state == State.BASIC)
			render(r, baseBackGround);
		if(state == State.EQUIPMENT)
			render(r, equipmentBackGround);
		if(state == State.ELEMENTS)
			render(r, elementsBackGround);
		render(r, returnButton);
		if(state != State.BASIC)
			render(r, baseButton);
		if(state != State.EQUIPMENT)
			render(r, equipmentButton);
		if(state != State.ELEMENTS)
			render(r, elementsButton);
		r.stop();
	}

	@Override
	public void update() {
		ArrayList<MouseClickEvent> clicks = MouseInput.getMouseClicks();
		for(MouseClickEvent click : clicks)
			if(click.getButton() == 0)
				leftClick(click.getX(), click.getY());
	}
	
	protected void render(GuiRenderer renderer, GuiTexture texture){
		renderer.renderGuiTexture(texture, this);
	}
	
	protected void render(GuiRenderer renderer, IButton button){
		renderer.renderButtonTexture(button, button.getTextures(), this);
	}

	@Override
	public void addButtons() {
		returnButton = new ButtonText(new Vector2f(-0.6f, 0.7f), MAIN_SCALE, MAIN_COLOR, Color.BLACK, Color.BLACK, "Close"){
			
			@Override
			public void leftClick(float x, float y){
				Game.getBattle().setCurrentGui(returnGui);
			}
		};
		baseButton = new ButtonText(new Vector2f(-0.6f, 0.0f), MAIN_SCALE, MAIN_COLOR, Color.BLACK, Color.BLACK, "Basic Info"){

			@Override
			public void leftClick(float x, float y) {
				state = State.BASIC;
			}
		};
		equipmentButton = new ButtonText(new Vector2f(-0.6f, -0.3f), MAIN_SCALE, MAIN_COLOR, Color.BLACK, Color.BLACK, "Equipment"){

			@Override
			public void leftClick(float x, float y) {
				state = State.EQUIPMENT;
			}
		};
		elementsButton = new ButtonText(new Vector2f(-0.6f, -0.6f), MAIN_SCALE, MAIN_COLOR, Color.BLACK, Color.BLACK, "Elements"){

			@Override
			public void leftClick(float x, float y) {
				state = State.ELEMENTS;
			}
		};
		int width = (int) (1.0f * GameScreen.getWidth());
		int height = (int) (1.0f * GameScreen.getHeight());
		BufferedImage backGroundImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		int dx = GameScreen.getWidth() / 20;
		int dy = GameScreen.getHeight() / 20;
		Graphics2D g = backGroundImage.createGraphics();
		g.setColor(BACKGROUND_EDGE);
		g.fillRect(0, 0, dx, height);
		g.fillRect(0, 0, width, dy);
		g.fillRect(width - dx, 0, dx, height);
		g.fillRect(0, height - dy, width, dy);
		g.setColor(BACKGROUND_COLOR);
		g.fillRect(dx, dy, width - 2 * dx, height - 2 * dy);
		g.dispose();
		backGround = new GuiTexture(new Vector2f(), new Vector2f(1.0f, 1.0f), new Texture(Resources.loadTexture(backGroundImage, false)));
		BufferedImage baseImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g = baseImage.createGraphics();
		g.setFont(STATUS_FONT);
		g.setColor(Color.HEALTH_FULL.toAWTColor());
		g.drawString("Health", width * 0.5f, height * 0.1f);
		drawBar(g, width, height, (float) character.getHealth() / character.getMaxHealth(), 0.1f);
		g.setColor(Color.BLACK.toAWTColor());
		g.setFont(STATS_FONT);
		g.drawString(character.getHealth() + " / " + character.getMaxHealth(), 0.71f * width, 0.105f * height);
		if(character.getMaxMana() > 0){
			g.setColor(Color.MANA_FULL.toAWTColor());
			g.drawString("Mana", width * 0.5f, height * 0.2f);
			drawBar(g, width, height, (float) character.getMana() / character.getMaxMana(), 0.2f);
			g.setColor(Color.BLACK.toAWTColor());
			g.setFont(STATS_FONT);
			g.drawString(character.getMana() + " / " + character.getMaxMana(), 0.71f * width, 0.205f * height);
		}
		if(character.getMaxFocus() > 0){
			g.setColor(Color.FOCUS_FULL.toAWTColor());
			g.drawString("Focus", width * 0.5f, height * 0.3f);
			drawBar(g, width, height, (float) character.getFocus() / character.getMaxFocus(), 0.3f);
			g.setColor(Color.BLACK.toAWTColor());
			g.setFont(STATS_FONT);
			g.drawString(character.getFocus() + " / " + character.getMaxFocus(), 0.71f * width, 0.305f * height);
		}
		if(character.getMaxEnergy() > 0){
			g.setColor(Color.ENERGY_FULL.toAWTColor());
			g.drawString("Energy", width * 0.5f, height * 0.4f);
			drawBar(g, width, height, (float) character.getEnergy() / character.getMaxEnergy(), 0.4f);
			g.setColor(Color.BLACK.toAWTColor());
			g.setFont(STATS_FONT);
			g.drawString(character.getEnergy() + " / " + character.getMaxEnergy(), 0.71f * width, 0.405f * height);
		}
		if(character.getMaxRage() > 0){
			g.setColor(Color.RAGE_FULL.toAWTColor());
			g.drawString("Rage", width * 0.5f, height * 0.5f);
			drawBar(g, width, height, (float) character.getRage() / character.getMaxRage(), 0.5f);
			g.setColor(Color.BLACK.toAWTColor());
			g.setFont(STATS_FONT);
			g.drawString(character.getRage() + " / " + character.getMaxRage(), 0.71f * width, 0.505f * height);
		}
		g.setFont(STATS_FONT);
		g.setColor(Color.STRENGTH.toAWTColor());
		g.drawString("Strength: " + character.getStrength(), width * 0.6f, height * 0.7f);
		g.setColor(Color.SPIRIT.toAWTColor());
		g.drawString("Spirit: " + character.getSpirit(), width * 0.6f, height * 0.75f);
		g.setColor(Color.TURN_SPEED.toAWTColor());
		g.drawString("Speed: " + character.getTurnSpeed(), width * 0.6f, height * 0.8f);
		g.dispose();
		baseBackGround = new GuiTexture(new Vector2f(), new Vector2f(1.0f, 1.0f), new Texture(Resources.loadTexture(baseImage, true)));
		BufferedImage equipmentImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g = equipmentImage.createGraphics();
		Equipment equipment = character.getEquipment();
		drawItemTexture(g, equipment.getHelmet(), width * 0.6f, height * 0.2f);
		drawItemTexture(g, equipment.getChestplate(), width * 0.6f, height * 0.4f);
		drawItemTexture(g, equipment.getLeftWeapon(), width * 0.4f, height * 0.4f);
		drawItemTexture(g, equipment.getRightWeapon(), width * 0.8f, height * 0.4f);
		drawItemTexture(g, equipment.getLeftGlobe(), width * 0.4f, height * 0.5f);
		drawItemTexture(g, equipment.getRightGlobe(), width * 0.8f, height * 0.5f);
		drawItemTexture(g, equipment.getPants(), width * 0.6f, height * 0.6f);
		drawItemTexture(g, equipment.getLeftShoe(), width * 0.4f, height * 0.7f);
		drawItemTexture(g, equipment.getRightShoe(), width * 0.8f, height * 0.7f);
		g.dispose();
		equipmentBackGround = new GuiTexture(new Vector2f(), new Vector2f(1.0f, 1.0f), new Texture(Resources.loadTexture(equipmentImage, true)));
		BufferedImage elementImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g = elementImage.createGraphics();
		g.setColor(Color.BLACK.toAWTColor());
		g.setFont(ELEMENT_FONT);
		g.drawString("Element", width * 0.4f, height * 0.2f);
		g.drawString("Power", width * 0.6f, height * 0.2f);
		g.drawString("Resistance", width * 0.8f, height * 0.2f);
		ElementalStatistics es = character.getElementStats();
		drawElement(g, BattleElement.PHYSICAL, es, width, height * 0.3f);
		drawElement(g, BattleElement.ROCK, es, width, height * 0.35f);
		drawElement(g, BattleElement.METAL, es, width, height * 0.4f);
		drawElement(g, BattleElement.WIND, es, width, height * 0.45f);
		drawElement(g, BattleElement.WATER, es, width, height * 0.5f);
		drawElement(g, BattleElement.LIGHT, es, width, height * 0.55f);
		drawElement(g, BattleElement.FIRE, es, width, height * 0.6f);
		drawElement(g, BattleElement.POISON, es, width, height * 0.65f);
		drawElement(g, BattleElement.ELECTRIC, es, width, height * 0.7f);
		drawElement(g, BattleElement.SPIRITUAL, es, width, height * 0.75f);
		drawElement(g, BattleElement.PSYCHIC, es, width, height * 0.8f);
		g.dispose();
		elementsBackGround = new GuiTexture(new Vector2f(), new Vector2f(1.0f, 1.0f), new Texture(Resources.loadTexture(elementImage, true)));
	}
	
	private void drawBar(Graphics2D g, int width, int height, float part, float y){
		g.fillRect((int) (width * 0.7f), (int) ((y - 0.04f) * height), (int) (width * 0.2f * part), (int) (0.06f * height));
		g.setColor(Color.BLACK.toAWTColor());
		g.drawRect((int) (width * 0.7f), (int) ((y - 0.04f) * height), (int) (width * 0.2f), (int) (0.06f * height));
	}
	
	private void drawElement(Graphics2D g, BattleElement element, ElementalStatistics es, float width, float y){
		g.setColor(element.getColor().toAWTColor());
		g.drawString(element.getName(), width * 0.4f, y);
		g.drawString(Math.round(es.getPower(element) * 100) + "%", width * 0.6f, y);
		g.drawString(Math.round(es.getResistance(element) * 100) + "%", width * 0.8f, y);
	}
	
	private void drawItemTexture(Graphics2D g, Item item, float x, float y){
		if(item != null)
			g.drawImage(item.getTexture().getImage(), (int) x, (int) y, (int) x + 64, (int) y + 64, 0, 0, 32, 32, null);
	}

	@Override
	public void open() {
		state = State.BASIC;
	}

	@Override
	public Color getBackGroundColor() {
		return null;
	}
	
	protected void leftClick(float x, float y){
		if(click(returnButton, x, y)) return;
		if(click(baseButton, x, y)) return;
		if(click(equipmentButton, x, y)) return;
		if(click(elementsButton, x, y)) return;
	}
	
	protected boolean click(IButton button, float x, float y){
		if(button.isHit(x, y)){
			button.leftClick(x, y);
			return true;
		}
		return false;
	}
	
	protected static enum State {
		
		BASIC,
		EQUIPMENT,
		ELEMENTS;
	}
}
