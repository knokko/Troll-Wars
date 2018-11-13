/*******************************************************************************
 * The MIT License
 *
 * Copyright (c) 2018 knokko
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
package nl.knokko.gui.battle;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.battle.element.BattleElement;
import nl.knokko.battle.element.ElementalStatistics;
import nl.knokko.equipment.Equipment;
import nl.knokko.gui.component.image.ConditionalImageComponent;
import nl.knokko.gui.component.image.SimpleImageComponent;
import nl.knokko.gui.component.menu.GuiMenu;
import nl.knokko.gui.component.text.ActivatableTextButton;
import nl.knokko.gui.component.text.TextButton;
import nl.knokko.gui.util.Condition;
import nl.knokko.gui.util.TextBuilder.Properties;
import nl.knokko.items.Item;
import nl.knokko.main.Game;
import nl.knokko.main.GameScreen;
import nl.knokko.util.color.Color;

public class GuiBattleCharacter extends GuiMenu {
        
        protected static final java.awt.Color BUTTON_COLOR = new java.awt.Color(120, 80, 0);
        protected static final java.awt.Color BUTTON_BORDER_COLOR = new java.awt.Color(40, 20, 0);
        protected static final java.awt.Color HOVER_COLOR = new java.awt.Color(180, 110, 20);
        protected static final java.awt.Color HOVER_BORDER_COLOR = new java.awt.Color(60, 30, 0);
        protected static final java.awt.Color ACTIVE_COLOR = new java.awt.Color(100, 65, 0);
        protected static final java.awt.Color ACTIVE_BORDER_COLOR = new java.awt.Color(50, 25, 0);
        
	protected static final java.awt.Color BACKGROUND_COLOR = new java.awt.Color(150, 75, 0);
	protected static final java.awt.Color BACKGROUND_EDGE = new java.awt.Color(100, 50, 0);
	protected static final Font STATS_FONT = new Font("TimesRoman", Font.BOLD, 25);
	protected static final Font STATUS_FONT = new Font("TimesRoman", Font.BOLD, 30);
	protected static final Font ELEMENT_FONT = new Font("TimesRoman", 0, 20);
	
	protected final BattleCreature character;
	protected final GuiBattle returnGui;
	
	protected State showState;

	public GuiBattleCharacter(GuiBattle returnGui, BattleCreature character) {
		this.character = character;
		this.returnGui = returnGui;
		this.showState = State.BASIC;
	}

	@Override
	protected void addComponents() {
            showState = State.BASIC;
            Properties buttonProperties = Properties.createButton(BUTTON_COLOR, BUTTON_BORDER_COLOR);
            Properties borderProperties = Properties.createButton(HOVER_COLOR, HOVER_BORDER_COLOR);
            Properties activeProperties = Properties.createButton(ACTIVE_COLOR, ACTIVE_BORDER_COLOR);
                addComponent(new TextButton("Close", Properties.createButton(BUTTON_COLOR, BUTTON_BORDER_COLOR), Properties.createButton(HOVER_COLOR, HOVER_BORDER_COLOR), new Runnable(){
                
                    @Override
                    public void run() {
                        Game.getBattle().setCurrentGui(returnGui);
                    }
                }), 0.05f, 0.8f, 0.35f, 0.9f);
                addComponent(new ActivatableTextButton("Basic Info", buttonProperties, borderProperties, activeProperties, new Runnable(){
                    
                    @Override
                    public void run() {
                        showState = State.BASIC;
                    }  
                }, new Condition(){
                    
                    @Override
                    public boolean isTrue() {
                        return showState == State.BASIC;
                    }
                }), 0.05f, 0.45f, 0.35f, 0.55f);
                addComponent(new ActivatableTextButton("Equipment", buttonProperties, borderProperties, activeProperties, new Runnable(){
                    
                    @Override
                    public void run() {
                        showState = State.EQUIPMENT;
                    }  
                }, new Condition(){
                    
                    @Override
                    public boolean isTrue() {
                        return showState == State.EQUIPMENT;
                    }
                }), 0.05f, 0.3f, 0.35f, 0.4f);
                addComponent(new ActivatableTextButton("Elements", buttonProperties, borderProperties, activeProperties, new Runnable(){
                    
                    @Override
                    public void run() {
                        showState = State.ELEMENTS;
                    }  
                }, new Condition(){
                    
                    @Override
                    public boolean isTrue() {
                        return showState == State.ELEMENTS;
                    }
                }), 0.05f, 0.15f, 0.35f, 0.25f);
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
                addComponent(new SimpleImageComponent(state.getWindow().getTextureLoader().loadTexture(backGroundImage)), 0, 0, 1, 1);
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
                addComponent(new ConditionalImageComponent(state.getWindow().getTextureLoader().loadTexture(baseImage), new Condition(){
                    @Override
                    public boolean isTrue() {
                        return showState == State.BASIC;
                    }
                }), 0, 0, 1, 1);
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
                addComponent(new ConditionalImageComponent(state.getWindow().getTextureLoader().loadTexture(equipmentImage), new Condition(){
                    @Override
                    public boolean isTrue() {
                        return showState == State.EQUIPMENT;
                    }
                }), 0, 0, 1, 1);
		BufferedImage elementsImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g = elementsImage.createGraphics();
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
                addComponent(new ConditionalImageComponent(state.getWindow().getTextureLoader().loadTexture(elementsImage), new Condition(){
                    @Override
                    public boolean isTrue() {
                        return showState == State.ELEMENTS;
                    }
                }), 0, 0, 1, 1);
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
	
	protected static enum State {
		
		BASIC,
		EQUIPMENT,
		ELEMENTS;
	}
}
