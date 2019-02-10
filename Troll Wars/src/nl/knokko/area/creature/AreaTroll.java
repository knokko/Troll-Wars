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
package nl.knokko.area.creature;

import nl.knokko.animation.body.AnimatorHumanoid;
import nl.knokko.animation.body.BodyAnimator;
import nl.knokko.model.ModelPart;
import nl.knokko.model.body.BodyTroll;
import nl.knokko.model.factory.creature.TrollFactory;
import nl.knokko.texture.painter.TrollPainter;
import nl.knokko.util.position.SpawnPosition;

public abstract class AreaTroll extends AreaCreature {
	
	public AreaTroll(SpawnPosition spawn) {
		super(spawn);
	}

	@Override
	protected void updateCreature() {}

	@Override
	protected void createBody() {
		addModel(TrollFactory.createModelTroll(getBody(), getTexture()));
	}

	@Override
	protected BodyAnimator createAnimator() {
		ModelPart[] childs = models.get(0).getChildren();
		return new AnimatorHumanoid(childs[1], childs[2], childs[3], childs[4], childs[3].getChildren()[0], childs[4].getChildren()[0]);
	}
	
	public BodyTroll getBody(){
		return BodyTroll.Models.AREA;
	}
	
	public TrollPainter getTexture(){
		return BodyTroll.Textures.createRargia();
	}
}
