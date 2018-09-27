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
package nl.knokko.story.dialogue;

import java.awt.Font;

import nl.knokko.util.color.Color;

public class SimpleDialogueText implements DialogueText {
	
	public static final Font DEFAULT_FONT = new Font("TimesRoman", 0, 30);
	public static final Color DEFAULT_COLOR = Color.BLACK;
	
	protected final String text;
	protected final Color color;
	protected final Font font;

	public SimpleDialogueText(String text, Color color, Font font) {
		this.text = text;
		this.color = color;
		this.font = font;
	}
	
	public SimpleDialogueText(String text, Font font){
		this(text, DEFAULT_COLOR, font);
	}
	
	public SimpleDialogueText(String text, Color color){
		this(text, color, DEFAULT_FONT);
	}
	
	public SimpleDialogueText(String text){
		this(text, DEFAULT_COLOR, DEFAULT_FONT);
	}

	@Override
	public Color getColor() {
		return color;
	}
	
	@Override
	public Font getFont(){
		return font;
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public void onClick() {}
}
