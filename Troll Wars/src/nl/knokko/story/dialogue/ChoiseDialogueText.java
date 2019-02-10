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

import java.awt.Font;

import nl.knokko.gui.util.Condition;
import nl.knokko.util.color.Color;

public class ChoiseDialogueText extends SimpleDialogueText {
	
	public static final Color DEFAULT_HOLD_COLOR = Color.YELLOW;
	
	protected final Color holdColor;
	protected Runnable action;
	protected Condition condition;

	public ChoiseDialogueText(String text, Color color, Color holdColor, Font font) {
		super(text, color, font);
		this.holdColor = holdColor;
	}

	public ChoiseDialogueText(String text, Font font) {
		this(text, DEFAULT_COLOR, DEFAULT_HOLD_COLOR, font);
	}

	public ChoiseDialogueText(String text, Color color) {
		this(text, color, DEFAULT_HOLD_COLOR, DEFAULT_FONT);
	}
	
	public ChoiseDialogueText(String text, Color color, Font font) {
		this(text, color, DEFAULT_HOLD_COLOR, font);
	}

	public ChoiseDialogueText(String text) {
		this(text, DEFAULT_COLOR, DEFAULT_HOLD_COLOR, DEFAULT_FONT);
	}
	
	@Override
	public void onClick(){
		if(action == null)
			throw new IllegalStateException("No action has been defined for the text: " + text);
		action.run();
	}
	
	public void setAction(Runnable action){
		this.action = action;
	}
	
	public void setCondition(Condition condition) {
		this.condition = condition;
	}
	
	public Color getHoldColor() {
		return holdColor;
	}
	
	public Condition getCondition() {
		return condition;
	}
}