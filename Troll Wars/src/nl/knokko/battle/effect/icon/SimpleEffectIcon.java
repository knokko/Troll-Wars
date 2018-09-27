/* 
 * The MIT License
 *
 * Copyright 2018 knokko.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
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
 */
package nl.knokko.battle.effect.icon;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import nl.knokko.util.resources.Resources;

public class SimpleEffectIcon implements EffectIcon {
	
	protected BufferedImage image;
	protected int textureID;

	public SimpleEffectIcon(String iconName) {
		image = Resources.loadImage("effects/" + iconName);
		textureID = Resources.loadTexture(image, true);
	}

	@Override
	public void draw(Graphics2D graphics, int x, int y, int width, int height) {
		graphics.drawImage(image, x, y, width, height, null);
	}

	@Override
	public int getTextureID() {
		return textureID;
	}
	
	public BufferedImage getImage(){
		return image;
	}
	
	public void setImage(BufferedImage newImage, int newTextureID){
		image = newImage;
		textureID = newTextureID;
	}
	
	public void setImage(BufferedImage newImage){
		setImage(newImage, Resources.loadTexture(newImage, true));
	}
}