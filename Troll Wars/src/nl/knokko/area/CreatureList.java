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
package nl.knokko.area;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

import nl.knokko.area.creature.AreaCreature;
import nl.knokko.area.creature.AreaPlayer;
import nl.knokko.model.ModelPart;
import nl.knokko.render.main.CreatureRenderer;
import nl.knokko.shaders.ShaderType;
import nl.knokko.view.camera.Camera;
import nl.knokko.view.light.Light;

public class CreatureList {
	
	private List<AreaCreature> list;
	
	private AreaPlayer player;

	public CreatureList() {
		list = new ArrayList<AreaCreature>();
	}
	
	public AreaPlayer getPlayer(boolean raiseException){
		if(player == null && raiseException)
			throw new RuntimeException("This CreatureList does not contain a player!");
		return player;
	}
	
	public void spawnCreature(AreaCreature creature){
		list.add(creature);
		if(creature instanceof AreaPlayer)
			player = (AreaPlayer) creature;
	}
	
	public void update(){
		try {
			for(AreaCreature creature : list)
				creature.update();
		} catch(ConcurrentModificationException cme){}//no problem, this means that the players transfers to another area
	}
	
	public void render(CreatureRenderer renderer, Camera camera, Light light){
		renderer.prepare(false);
		for(AreaCreature creature : list){
			renderer.prepareShader(camera, light, creature.getShaderType());
			for(ModelPart part : creature.getModels()){
				renderModelPart(renderer, part, creature);
			}
		}
	}
	
	private void renderModelPart(CreatureRenderer renderer, ModelPart part, AreaCreature creature){
		ShaderType st = creature.getShaderType();
		renderer.prepareModel(part.getModel(), st);
		renderer.prepareTexture(part.getTexture(), st);
		renderer.renderInstance(part.getMatrix(creature), st, part.getModel().getVertexCount());
		for(ModelPart child : part.getChildren())
			if(child != null)
				renderModelPart(renderer, child, creature);
	}
	
	public List<AreaCreature> getList(){
		return list;
	}

	public void clearNonPlayers() {
		Iterator<AreaCreature> iterator = list.iterator();
		while(iterator.hasNext()){
			if(iterator.next() != player)
				iterator.remove();
		}
	}
}
