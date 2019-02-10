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
package nl.knokko.texture.factory;

import nl.knokko.texture.ModelTexture;
import nl.knokko.texture.Texture;
import nl.knokko.util.color.Color;
import nl.knokko.util.color.ColorAlpha;

public class SimpleTextureFactory {
	
	private static ModelTexture WHITE;
	
	private static Texture createWhiteTexture(){
		return createFilledTexture(Color.WHITE);
	}
	
	public static ModelTexture white(){
		if(WHITE == null)
			WHITE = new ModelTexture(createWhiteTexture(), 0.1f, 0.1f);
		return WHITE;
	}
	
	public static Texture createFilledTexture(Color color){
		TextureBuilder builder = new TextureBuilder(8, 8, color instanceof ColorAlpha);
		builder.fillRect(0, 0, 7, 7, color);
		return new Texture(builder.loadNormal());
	}
}