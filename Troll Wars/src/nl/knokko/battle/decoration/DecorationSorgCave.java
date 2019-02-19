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
import nl.knokko.texture.ModelTexture;
import nl.knokko.texture.Texture;
import nl.knokko.texture.factory.TextureBuilder;
import nl.knokko.util.Maths;
import nl.knokko.util.color.Color;

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
		
		TextureBuilder textureBuilder = new TextureBuilder(2048, 256, false);
		
		// TODO create a proper texture later
		textureBuilder.fillAverage(0, 0, textureBuilder.width() - 1, textureBuilder.height() - 1, Color.RED, 0.4f, random);
		for (int progressXZ = 0; progressXZ < 1000; progressXZ++) {
			float currentProgress = progressXZ * 0.001f;
			float nextProgress = (progressXZ + 1) * 0.001f;
			float currentX = wall.getX(currentProgress);
			float currentZ = wall.getZ(currentProgress);
			float nextX = wall.getX(nextProgress);
			float nextZ = wall.getZ(nextProgress);
			
			// TODO improve texture coords and normals later
			builder.addPlane(0, 0, 1, currentX, 0, currentZ, currentProgress, 0, nextX, 0, nextZ, nextProgress, 0, nextX, wall.caveWallHeight, nextZ, nextProgress, 1, currentX, wall.caveWallHeight, currentZ, currentProgress, 1);
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
		 * The point where the cave wall should start forming a sphere for the second time
		 */
		private final float progressBorder3;
		
		/**
		 * The point where the cave wall has made a half sphere for the second time
		 */
		private final float progressBorder4;
		
		/**
		 * The difference between progressBorder2 and progressBorder1
		 */
		private final float progressBorderLength12;
		
		/**
		 * The difference between progressBorder4 and progressBorder3
		 */
		private final float progressBorderLength34;
		
		/**
		 * The height of the cave wall
		 */
		private final float caveWallHeight;
		
		private WallBuilder(Random random) {
			
			// Independent fields
			caveRadius = 200 + random.nextFloat() * 50;
			posCaveLength = 5000 + random.nextFloat() * 400;
			negCaveLength = 5000 + random.nextFloat() * 400;
			caveWallHeight = 1000 + random.nextFloat() * 200;
			
			// Dependent fields
			// The total length of the cave wall
			caveWallLength = 4 * caveRadius + 2 * posCaveLength + 2 * negCaveLength;
			
			// The point where the cave wall should start forming a sphere rather than continuing its path
			progressBorder1 = posCaveLength / caveWallLength;
			
			// The point where the cave wall has made a half sphere and needs to move in the opposite direction
			progressBorder2 = (posCaveLength + 2 * caveRadius) / caveWallLength;
			
			// The point where the cave wall should start forming a sphere for the second time
			progressBorder3 = (2 * caveRadius + 2 * posCaveLength + negCaveLength) / caveWallLength;
			
			// The point where the cave wall has made a half sphere for the second time
			progressBorder4 = (4 * caveRadius + 2 * posCaveLength + negCaveLength) / caveWallLength;
			
			progressBorderLength12 = progressBorder2 - progressBorder1;
			
			progressBorderLength34 = progressBorder4 - progressBorder3;
		}
		
		private float getX(float progress) {
			if (progress <= progressBorder1) {
				
				// Progress is in range [0, progressBorder1]
				// This is the part until the first half cilinder
				return progress * posCaveLength / progressBorder1;
			} else if (progress <= progressBorder2) {
				
				// Progress is in range (progressBorder1, progressBorder2]
				// This is the first half cylinder
				return posCaveLength + caveRadius * Maths.sin(180f * (progress - progressBorder1) / progressBorderLength12);
			} else if (progress <= 0.5f) {
				
				// Progress is in range (progressBorder2, 0.5]
				// This is after the first half cylinder
				return posCaveLength - (progress - progressBorder2) / (0.5f - progressBorder2) * posCaveLength;
			} else if (progress <= progressBorder3) {
				
				// Progress is in range (0.5, progressBorder3]
				// This is the part until the second half cylinder
				return -(progress - 0.5f) * negCaveLength / (progressBorder3 - 0.5f);
			} else if (progress <= progressBorder4) {
				
				// Progress is in range (progressBorder3, progressBorder4]
				// This is the second half cylinder
				return -negCaveLength - caveRadius * Maths.sin(180f * (progress - progressBorder3) / progressBorderLength34);
			} else {
				
				// Progress is in range (progressBorder4, 1]
				// This is the part after the second half cylinder
				return -negCaveLength + (progress - progressBorder4) / (1f - progressBorder4) * negCaveLength;
			}
		}
		
		private float getZ(float progress) {
			// TODO smoothen this later
			if (progress <= progressBorder1) {
				
				// Progress is in range [0, progressBorder1]
				// This is the part until the first half sphere begins
				return caveRadius;
			} else if (progress <= progressBorder2) {
				
				// Progress is in range (progressBorder1, progressBorder2]
				// The first half sphere should be in this region
				return caveRadius * Maths.sin(180f * (progress - progressBorder1) / progressBorderLength12 + 90f);
			}  else if (progress <= progressBorder3) {
				
				// Progress is in range (progressBorder2, progressBorder3]
				// This is the part until the second half sphere begins
				return -caveRadius;
			} else if (progress <= progressBorder4) {
				
				// Progress is in range (progressBorder3, progressBorder4]
				// The second half sphere should be in this region
				return caveRadius * Maths.sin(180f * (progress - progressBorder3) / progressBorderLength34 - 90f);
			} else {
				
				// Progress is in range (0.75, 1]
				return caveRadius;
			}
		}
	}
}