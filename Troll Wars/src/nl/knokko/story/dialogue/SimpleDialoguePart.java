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
package nl.knokko.story.dialogue;

import nl.knokko.texture.ImageTexture;
import nl.knokko.util.color.Color;

public class SimpleDialoguePart extends DialoguePart {
	
	public static final Color DEFAULT_BACKGROUND_COLOR = new Color(62, 0, 89);
	
	protected final Color backGround;
	
	protected final ImageTexture portrait;
	
	protected final DialogueText[] text;
	
	protected final SimpleDialogueText title;
	
	protected Runnable next;

	public SimpleDialoguePart(Dialogue dialogue, Color backGround, ImageTexture portrait, SimpleDialogueText title, DialogueText text) {
		super(dialogue);
		this.backGround = backGround;
		this.portrait = portrait;
		this.text = new DialogueText[]{text};
		this.title = title;
	}
	
	/*
	public SimpleDialoguePart(Dialogue dialogue, Color backGround, ImageTexture portrait, SimpleDialogueText title, String text, Color textColor){
		this(dialogue, backGround, portrait, title, new SimpleDialogueText(text, textColor));
	}
	
	public SimpleDialoguePart(Dialogue dialogue, ImageTexture portrait, SimpleDialogueText title, DialogueText text){
		this(dialogue, DEFAULT_BACKGROUND_COLOR, portrait, title, text);
	}
	
	public SimpleDialoguePart(Dialogue dialogue, ImageTexture portrait, SimpleDialogueText title, String text, Color textColor){
		this(dialogue, DEFAULT_BACKGROUND_COLOR, portrait, text, textColor);
	}
	
	public SimpleDialoguePart(Dialogue dialogue, ImageTexture portrait, String text){
		this(dialogue, portrait, new SimpleDialogueText(text));
	}
	*/

	@Override
	public Color getBackGround() {
		return backGround;
	}
	
	@Override
	public ImageTexture getPortrait(){
		return portrait;
	}

	@Override
	public DialogueText[] getText() {
		return text;
	}

	@Override
	public void next() {
		next.run();
	}
	
	public void setNext(Runnable next){
		this.next = next;
	}

	@Override
	public SimpleDialogueText getTitle() {
		return title;
	}
}
