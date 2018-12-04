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
package nl.knokko.area.creature;

import nl.knokko.animation.body.AnimatorMyrre;
import nl.knokko.animation.body.BodyAnimator;
import nl.knokko.model.body.BodyMyrre;
import nl.knokko.shaders.ShaderType;
import nl.knokko.util.position.SpawnPosition;
import nl.knokko.util.resources.Resources;

public class AreaMyrmora {

	public static class Intro extends AreaCreature {

		public Intro(SpawnPosition spawn) {
			super(spawn);
		}

		@Override
		protected void updateCreature() {}//just needs to stand, nothing more

		@Override
		protected void createBody() {
			addModel(Resources.createModelMyrre(BodyMyrre.Models.MYRMORA, BodyMyrre.Textures.MYRMORA));
		}

		@Override
		protected BodyAnimator createAnimator() {
			return new AnimatorMyrre(this);
		}
		
		@Override
		public ShaderType getShaderType() {
			return ShaderType.SPIRIT;
		}

		@Override
		public void interact() {}//the player can't interact
	}
}