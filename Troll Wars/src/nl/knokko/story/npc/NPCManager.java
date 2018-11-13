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
package nl.knokko.story.npc;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import nl.knokko.area.Area;
import nl.knokko.area.creature.AreaCreature;
import nl.knokko.areas.AreaRargia;
import nl.knokko.inventory.trading.ItemStack;
import nl.knokko.inventory.trading.TradeOffer;
import nl.knokko.inventory.trading.TradeOffers;
import nl.knokko.items.Items;
import nl.knokko.main.Game;
import nl.knokko.model.body.BodyHuman;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;
import nl.knokko.util.color.Color;
import nl.knokko.util.position.SpawnPosition;
import nl.knokko.util.resources.Saver;

public class NPCManager {
	
	private final LazyTrollTraderNPC rargiaBoneSmith = new LazyTrollTraderNPC(AreaRargia.BoneSmith.class, 5, 4, 6, new TradeOffers(
			new TradeOffer(new ItemStack[]{new ItemStack(Items.CRYT_PIECE, 5), new ItemStack(Items.POG_SKIN, 10)}, new ItemStack(Items.POG_SHOE)),
			new TradeOffer(new ItemStack[]{new ItemStack(Items.CRYT_PIECE, 5), new ItemStack(Items.POG_SKIN, 20)}, new ItemStack(Items.POG_PANTS)),
			new TradeOffer(new ItemStack[]{new ItemStack(Items.CRYT_PIECE, 5), new ItemStack(Items.POG_SKIN, 30)}, new ItemStack(Items.POG_SHIRT)),
			new TradeOffer(new ItemStack[]{new ItemStack(Items.CRYT_PIECE, 5), new ItemStack(Items.POG_SKIN, 15)}, new ItemStack(Items.POG_GLOBE)),
			new TradeOffer(new ItemStack[]{new ItemStack(Items.CRYT_PIECE, 5), new ItemStack(Items.POG_SKIN, 10)}, new ItemStack(Items.POG_CAP))
	));
	
	private final LazyTrollTraderNPC rargiaFarmer = new LazyTrollTraderNPC(AreaRargia.Farmer.class, 5, 4, 6, new TradeOffers(
					new TradeOffer(new ItemStack[]{new ItemStack(Items.CRYT_PIECE, 10)}, new ItemStack(Items.POG_BONE)),
					new TradeOffer(new ItemStack[]{new ItemStack(Items.CRYT_PIECE, 2)}, new ItemStack(Items.POG_SKIN)),
					new TradeOffer(new ItemStack[]{new ItemStack(Items.CRYT_PIECE, 25)}, new ItemStack(Items.POG_SHOE)),
					new TradeOffer(new ItemStack[]{new ItemStack(Items.CRYT_PIECE, 45)}, new ItemStack(Items.POG_PANTS)),
					new TradeOffer(new ItemStack[]{new ItemStack(Items.CRYT_PIECE, 65)}, new ItemStack(Items.POG_SHIRT)),
					new TradeOffer(new ItemStack[]{new ItemStack(Items.CRYT_PIECE, 25)}, new ItemStack(Items.POG_GLOBE)),
					new TradeOffer(new ItemStack[]{new ItemStack(Items.CRYT_PIECE, 25)}, new ItemStack(Items.POG_CAP))
	));
	
	private final LazyTrollTraderNPC rargiaBlackSmith = new LazyTrollTraderNPC(AreaRargia.BlackSmith.class, 6, 0, 6, new TradeOffers(
					new TradeOffer(new ItemStack[]{new ItemStack(Items.CRYT_PIECE, 200)}, new ItemStack(Items.CRYT_HELMET)),
					new TradeOffer(new ItemStack[]{new ItemStack(Items.CRYT_PIECE, 600)}, new ItemStack(Items.CRYT_CHESTPLATE)),
					new TradeOffer(new ItemStack[]{new ItemStack(Items.CRYT_PIECE, 400)}, new ItemStack(Items.CRYT_LEGGINGS)),
					new TradeOffer(new ItemStack[]{new ItemStack(Items.CRYT_PIECE, 200)}, new ItemStack(Items.CRYT_SHOE)),
					new TradeOffer(new ItemStack[]{new ItemStack(Items.CRYT_PIECE, 20)}, new ItemStack(Items.SORG_SPEAR)),
					new TradeOffer(new ItemStack[]{new ItemStack(Items.CRYT_PIECE, 60)}, new ItemStack(Items.CRYT_SPEAR))
	));
	
	private final LazyTrollTraderNPC rargiaFarmacy = new LazyTrollTraderNPC(AreaRargia.Farmacy.class, 6, 0, 6, new TradeOffers());
	
	private final NPCMyrmora.Intro introMyrmora = new NPCMyrmora.Intro();
	private final Random introRandom = new Random(28372837L);
	private final IntroHumanNPC introWarrior1 = new IntroHumanNPC(new SpawnPosition(46,13,45), BodyHuman.Models.createSimpleInstance(introRandom, 0.05f), BodyHuman.Textures.createBlanc(new Color(30, 20, 10)), Items.IRON_SWORD);
	private final IntroHumanNPC introWarrior2 = new IntroHumanNPC(new SpawnPosition(47,13,46), BodyHuman.Models.createSimpleInstance(introRandom, 0.05f), BodyHuman.Textures.createBrown(new Color(10, 20, 10)), Items.IRON_SPEAR);
	private final IntroHumanNPC introWarrior3 = new IntroHumanNPC(new SpawnPosition(48,13,46), BodyHuman.Models.createSimpleInstance(introRandom, 0.05f), BodyHuman.Textures.createBlanc(new Color(250, 250, 75)), Items.IRON_SWORD);
	private final IntroHumanNPC introPaladin = new IntroHumanNPC(new SpawnPosition(49,13,44), BodyHuman.Models.createSimpleInstance(introRandom, 0f), BodyHuman.Textures.createBlanc(new Color(230, 250, 220)), Items.BLESSED_IRON_SWORD);
	private final IntroHumanNPC introWarrior4 = new IntroHumanNPC(new SpawnPosition(50,13,46), BodyHuman.Models.createSimpleInstance(introRandom, 0.05f), BodyHuman.Textures.createBrown(new Color(100, 70, 0)), Items.IRON_SWORD);
	private final IntroTheresa introTheresa = new IntroTheresa(BodyHuman.Models.createSimpleInstance(introRandom, 0.05f), BodyHuman.Textures.createBlanc(new Color(250, 250, 75)));
	private final IntroHumanNPC introWarrior6 = new IntroHumanNPC(new SpawnPosition(52,13,45), BodyHuman.Models.createSimpleInstance(introRandom, 0.05f), BodyHuman.Textures.createBlanc(new Color(30, 20, 10)), Items.IRON_SWORD);
	
	private final NPC[] npcs = {
			rargiaBoneSmith, rargiaFarmer, rargiaBlackSmith, rargiaFarmacy, 
			introMyrmora, introWarrior1, introWarrior2, introWarrior3, introPaladin, introWarrior4, introTheresa, introWarrior6
	};
	
	private final Map<Class<?>,List<NPC>> areaMap = new TreeMap<Class<?>,List<NPC>>(new Comparator<Class<?>>(){

		@Override
		public int compare(Class<?> o1, Class<?> o2) {
			return o1.getName().compareTo(o2.getName());
		}
	});
	
	public NPCManager(){
		for(NPC npc : npcs){
			Class<?>[] areas = npc.getPossibleAreas();
			for(Class<?> area : areas){
				List<NPC> list = areaMap.get(area);
				if(list == null){
					list = new ArrayList<NPC>();
					areaMap.put(area, list);
				}
				list.add(npc);
			}
		}
	}
	
	public void save(){
		BitOutput buffer = Saver.save("npcs.data", 1000);
		//BitBuffer buffer = new BitBuffer(1000);
		buffer.addInt(npcs.length);
		for(NPC npc : npcs)
			npc.save(buffer);
		//Saver.save(buffer, "npcs.data");
	}
	
	public void load(){
		BitInput buffer = Saver.load("npcs.data");
		int amount = buffer.readInt();
		if(amount != npcs.length)
			System.out.println("The loaded save file had less npc's.");
		int index;
		for(index = 0; index < amount; index++)
			npcs[index].load(buffer);
		for(; index < npcs.length; index++)
			npcs[index].initFirstGame();
	}
	
	public void initFirstGame(){
		for(NPC npc : npcs)
			npc.initFirstGame();
	}
	
	public void processArea(Area area){
		List<NPC> npcs = areaMap.get(area.getClass());
		if(npcs != null){
			for(NPC npc : npcs){
				AreaCreature creature = npc.createRepresentation(area);
				if(creature != null)
					area.spawnCreature(creature);
			}
		}
	}
	
	public void refresh(){
		processArea(Game.getArea().getArea());
	}
	
	public IntroHumanNPC getIntroWarrior1(){
		return introWarrior1;
	}
	
	public IntroHumanNPC getIntroWarrior2(){
		return introWarrior2;
	}
	
	public IntroHumanNPC getIntroWarrior3(){
		return introWarrior3;
	}
	
	public IntroHumanNPC getIntroWarrior4(){
		return introWarrior4;
	}
	
	public IntroTheresa getIntroTheresa(){
		return introTheresa;
	}
	
	public IntroHumanNPC getIntroWarrior6(){
		return introWarrior6;
	}
	
	public IntroHumanNPC getIntroPaladin(){
		return introPaladin;
	}
}
