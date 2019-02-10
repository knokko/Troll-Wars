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
package nl.knokko.items;

import static nl.knokko.inventory.InventoryType.MONEY;
import static nl.knokko.inventory.InventoryType.ORGANIC;
import nl.knokko.battle.element.ArmorElementStats;
import nl.knokko.battle.element.BattleElement;
import nl.knokko.model.equipment.weapon.ModelBone;
import nl.knokko.texture.equipment.ArmorTexture;
import nl.knokko.texture.equipment.SimpleArmorTexture;
import nl.knokko.texture.equipment.SimpleWeaponTextures;
import nl.knokko.texture.equipment.WeaponTextures;
import nl.knokko.texture.pattern.PatternAverage;
import nl.knokko.texture.pattern.PatternMetal;
import nl.knokko.util.color.Color;

public final class Items {
	
	private static final ArmorTexture CRYT_ARMOR = new SimpleArmorTexture(new PatternMetal(Color.CRYT, 92347834), 1f, 0.2f);
	private static final ArmorTexture POG_ARMOR = new SimpleArmorTexture(new PatternAverage(new Color(216, 83, 0), 0.15f, -2349834983948l), 0.3f, 0.1f);
	private static final ArmorTexture IRON_ARMOR = new SimpleArmorTexture(new PatternMetal(Color.IRON, -128293), 1f, 0.2f);
	private static final ArmorTexture BLESSED_IRON_ARMOR = new SimpleArmorTexture(new PatternMetal(Color.BLESSED_IRON, 109384), 1f, 0.4f);
	
	private static final WeaponTextures SORG_WEAPONS = SimpleWeaponTextures.createRockSet(Color.AYUE_PLANK, Color.SORG_BASE);
	private static final WeaponTextures CRYT_WEAPONS = SimpleWeaponTextures.createMetalSet(Color.AYUE_PLANK, Color.CRYT);
	private static final WeaponTextures IRON_WEAPONS = SimpleWeaponTextures.createMetalSet(Color.OAK_PLANK, Color.IRON);
	private static final WeaponTextures BLESSED_IRON_WEAPONS = SimpleWeaponTextures.createMetalSet(Color.OAK_PLANK, Color.BLESSED_IRON);
	
	static final Item[] ITEMS = new Item[27];
	
	public static final Item CRYT_PIECE = new Item("Cryt Piece", MONEY);
	public static final ItemShoe CRYT_SHOE = new ItemShoe("Cryt Shoe", 2, 0, CRYT_ARMOR);
	public static final ItemPants CRYT_LEGGINGS = new ItemPants("Cryt Leggings", 4, 0, CRYT_ARMOR);
	public static final ItemChestplate CRYT_CHESTPLATE = new ItemChestplate("Cryt Chestplate", 6, 0, CRYT_ARMOR);
	public static final ItemHelmet CRYT_HELMET = new ItemHelmet("Cryt Helmet", 2, 0, CRYT_ARMOR);
	public static final Item POG_SKIN = new Item("Pog Skin", ORGANIC);
	public static final ItemBone POG_BONE = new ItemBone("Pog Bone", ModelBone.Factory.createInstance(0.5f, 0.05f, 0.05f, 0.07f, 0.07f, 0.07f, 0.3f, 636), new Color(180, 110, 105), 9, 10, 10, 12, 8, 4);
	public static final ItemSpear SORG_SPEAR = new ItemSpear("Sorg Spear", SORG_WEAPONS, BattleElement.ROCK, 15, 11);
	public static final ItemShoe POG_SHOE = new ItemShoe("Pog Shoe", 1, 0, POG_ARMOR);
	public static final ItemPants POG_PANTS = new ItemPants("Pog Pants", 2, 0, POG_ARMOR);
	public static final ItemChestplate POG_SHIRT = new ItemChestplate("Pog Shirt", 3, 1, POG_ARMOR);
	public static final ItemHelmet POG_CAP = new ItemHelmet("Pog Cap", 1, 0, POG_ARMOR);
	public static final ItemSpear CRYT_SPEAR = new ItemSpear("Cryt Spear", CRYT_WEAPONS, BattleElement.METAL, 25, 21);
	public static final ItemShoe IRON_SHOE = new ItemShoe("Iron Shoe", 80, 20, IRON_ARMOR);
	public static final ItemPants IRON_LEGGINGS = new ItemPants("Iron Leggings", 160, 30, IRON_ARMOR);
	public static final ItemChestplate IRON_CHESTPLATE = new ItemChestplate("Iron Chestplate", 200, 40, IRON_ARMOR);
	public static final ItemHelmet IRON_HELMET = new ItemHelmet("Iron Helmet", 80, 20, IRON_ARMOR);
	public static final ItemGlobe POG_GLOBE = new ItemGlobe("Pog Globe", 1, 0, POG_ARMOR);
	public static final ItemSpear IRON_SPEAR = new ItemSpear("Iron Spear", IRON_WEAPONS, BattleElement.METAL, 900, 700);
	public static final ItemSword IRON_SWORD = new ItemSword("Iron Sword", IRON_WEAPONS, BattleElement.METAL, 800, 400);
	public static final ItemSword CRYT_SWORD = new ItemSword("Cryt Sword", CRYT_WEAPONS, BattleElement.METAL, 20, 15);
	public static final ItemSword SORG_SWORD = new ItemSword("Sorg Sword", SORG_WEAPONS, BattleElement.ROCK, 12, 4);
	public static final ItemShoe BLESSED_IRON_SHOE = new ItemShoe("Blessed Iron Shoe", ArmorElementStats.BLESSED_IRON, 80, 40, BLESSED_IRON_ARMOR);
	public static final ItemPants BLESSED_IRON_LEGGINGS = new ItemPants("Blessed Iron Leggings", ArmorElementStats.BLESSED_IRON, 160, 60, BLESSED_IRON_ARMOR);
	public static final ItemChestplate BLESSED_IRON_CHESTPLATE = new ItemChestplate("Blessed Iron Chestplate", ArmorElementStats.BLESSED_IRON, 200, 80, BLESSED_IRON_ARMOR);
	public static final ItemHelmet BLESSED_IRON_HELMET = new ItemHelmet("Blessed Iron Helmet", ArmorElementStats.BLESSED_IRON, 80, 40, BLESSED_IRON_ARMOR);
	public static final ItemSword BLESSED_IRON_SWORD = new ItemSword("Blessed Iron Sword", BLESSED_IRON_WEAPONS, BattleElement.LIGHT, 900, 900);
	
	public static final Item fromID(short id){
		if(id == Short.MAX_VALUE)
			return null;
		return ITEMS[id - Short.MIN_VALUE];
	}
	
	public static final short getID(Item item){
		return item != null ? item.getID() : Short.MAX_VALUE;
	}
	
	public static final int amount(){
		return ITEMS.length;
	}
}