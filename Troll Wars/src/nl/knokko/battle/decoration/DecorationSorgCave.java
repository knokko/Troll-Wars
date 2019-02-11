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
package nl.knokko.battle.decoration;

import nl.knokko.model.ModelPart;
import nl.knokko.model.factory.ModelBuilder;
import nl.knokko.model.type.AbstractModel;
import nl.knokko.texture.ModelTexture;
import nl.knokko.texture.Texture;
import nl.knokko.texture.factory.SimpleTextureFactory;
import nl.knokko.texture.factory.TextureBuilder;

import static nl.knokko.battle.decoration.BattleDecorations.*;

import java.util.Random;

import org.lwjgl.util.vector.Vector3f;

public class DecorationSorgCave extends SimpleBattleDecoration {

	public DecorationSorgCave() {
		super(ID_SORG_CAVE, BACKGROUND_INSIDE, LIGHT_OUTSIDE);
	}

	@Override
	protected ModelPart[] createModel() {
		ModelPart[] result = new ModelPart[1];
		
		Random random = new Random();
		ModelBuilder builder = new ModelBuilder();
		builder.addPlane(0, 0, 1, 0, 0, -300, 0, 0, 100, 0, -300, 1, 0, 100, 100, -300, 1, 1, 0, 100, -300, 0, 1);
		
		// Build the cave wall
		WallBuilder wall = new WallBuilder(random);
		
		TextureBuilder textureBuilder = new TextureBuilder((int) wall.caveWallLength, (int) wall.caveWallHeight, false);
		for (int progressXZ = 0; progressXZ < 1000; progressXZ++) {
			float currentProgress = progressXZ * 0.001f;
			float nextProgress = (progressXZ + 1) * 0.001f;
			float currentX = wall.getX(currentProgress);
			float currentZ = wall.getZ(currentProgress);
			float nextX = wall.getX(nextProgress);
			float nextZ = wall.getZ(nextProgress);
		}
		
		// Finally create the wall model part
		result[0] = new ModelPart(builder.load(), new ModelTexture(new Texture(textureBuilder.loadNormal()), 0f, 0f), new Vector3f());
		return result;
	}
	
	private static class WallBuilder {
		
		/**
		 * The horizontal distance between the middle of the cave path and the wall
		 */
		private final float caveRadius;
		
		/**
		 * The length of the cave model in the positive x-direction
		 */
		private final float posCaveLength;
		
		/**
		 * The length of the cave model in the negative x-direction
		 */
		private final float negCaveLength;
		
		/**
		 * The total length of the cave wall
		 */
		private final float caveWallLength;
		
		/**
		 * The point where the cave wall should start forming a sphere rather than continuing its path
		 */
		private final float progressBorder1;
		
		/**
		 * The point where the cave wall has made a half sphere and needs to move in the opposite direction
		 */
		private final float progressBorder2;
		
		/**
		 * The height of the cave wall
		 */
		private final float caveWallHeight;
		
		private WallBuilder(Random random) {
			
			// Independent fields
			caveRadius = 400 + random.nextFloat() * 50;
			posCaveLength = 10000 + random.nextFloat() * 400;
			negCaveLength = 10000 + random.nextFloat() * 400;
			caveWallHeight = 2000 + random.nextFloat() * 200;;
			
			// Dependent fields
			// The total length of the cave wall
			caveWallLength = 2 * caveRadius + 2 * posCaveLength + 2 * negCaveLength;
			
			// The point where the cave wall should start forming a sphere rather than continuing its path
			progressBorder1 = posCaveLength / caveWallLength;
			
			// The point where the cave wall has made a half sphere and needs to move in the opposite direction
			progressBorder2 = (posCaveLength + 2 * caveRadius) / caveWallLength;
		}
		
		private float getX(float progress) {
			
		}
		
		private float getZ(float progress) {
			
		}
	}
}