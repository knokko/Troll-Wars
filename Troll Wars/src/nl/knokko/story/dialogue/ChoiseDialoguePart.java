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

import nl.knokko.texture.ImageTexture;
import nl.knokko.util.color.Color;

public class ChoiseDialoguePart extends DialoguePart {
	
	public static final Color DEFAULT_BACKGROUND_COLOR = SimpleDialoguePart.DEFAULT_BACKGROUND_COLOR;
	public static final ImageTexture DEFAULT_PORTRAIT = Portraits.GOTHROK;
	
	protected static ChoiseDialogueText[] createOptions(String... options){
		ChoiseDialogueText[] choises = new ChoiseDialogueText[options.length];
		for(int i = 0; i < options.length; i++)
			choises[i] = new ChoiseDialogueText(options[i]);
		return choises;
	}
	
	protected final Color backGround;
	
	protected final ImageTexture portrait;
	
	protected final ChoiseDialogueText[] text;
	
	protected final SimpleDialogueText title;

	public ChoiseDialoguePart(Dialogue dialogue, Color backgroundColor, ImageTexture portrait, SimpleDialogueText title, ChoiseDialogueText... options) {
		super(dialogue);
		backGround = backgroundColor;
		this.portrait = portrait;
		text = options;
		this.title = title;
	}
	
	/*
	public ChoiseDialoguePart(Dialogue dialogue, SimpleDialogueText title, ChoiseDialogueText... options){
		this(dialogue, DEFAULT_BACKGROUND_COLOR, DEFAULT_PORTRAIT, title, options);
	}
	
	public ChoiseDialoguePart(Dialogue dialogue, SimpleDialogueText title, String... options){
		this(dialogue, title, createOptions(options));
	}
	
	public ChoiseDialoguePart(Dialogue dialogue, ImageTexture portrait, SimpleDialogueText title, String... options){
		this(dialogue, DEFAULT_BACKGROUND_COLOR, portrait, title, createOptions(options));
	}
	*/

	@Override
	public Color getBackGround() {
		return backGround;
	}

	@Override
	public ImageTexture getPortrait() {
		return portrait;
	}

	@Override
	public DialogueText[] getText() {
		return text;
	}

	@Override
	public void next() {}
	
	public void setActions(Runnable... actions){
		if(actions.length != text.length)
			throw new IllegalArgumentException("The length of the actions must be equal to the length of the choises!");
		for(int i = 0; i < text.length; i++)
			text[i].setAction(actions[i]);
	}

	@Override
	public SimpleDialogueText getTitle() {
		return title;
	}
}
