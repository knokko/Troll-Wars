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
package nl.knokko.gui.dialogue;

import nl.knokko.gui.color.GuiColor;
import nl.knokko.gui.component.GuiComponent;
import nl.knokko.gui.component.dialogue.ChoiseDialogueComponent;
import nl.knokko.gui.component.dialogue.SimpleDialogueComponent;
import nl.knokko.gui.component.menu.GuiMenu;
import nl.knokko.main.Game;
import nl.knokko.story.dialogue.ChoiseDialoguePart;
import nl.knokko.story.dialogue.Dialogue;
import nl.knokko.story.dialogue.DialoguePart;
import nl.knokko.story.dialogue.SimpleDialoguePart;

public class GuiDialogue extends GuiMenu {
	
	private final Dialogue dialogue;
	
	private SubComponent partComponent;

	public GuiDialogue(Dialogue dialogue) {
		this.dialogue = dialogue;
	}
	
	public Dialogue getDialogue() {
		return dialogue;
	}

	@Override
	protected void addComponents() {
		partComponent = new SubComponent(createPartComponent(), 0, 0, 1, 0.3f);
		addComponent(partComponent);
	}
	
	@Override
	public GuiColor getBackgroundColor() {
		return null;
	}
	
	public void changePart() {
		partComponent.setComponent(createPartComponent());
		Game.getWindow().markChange();
	}
	
	private GuiComponent createPartComponent() {
		DialoguePart current = dialogue.current();
		if (current instanceof SimpleDialoguePart) {
			return new SimpleDialogueComponent((SimpleDialoguePart) current, this);
		} else {
			return new ChoiseDialogueComponent((ChoiseDialoguePart) current, this);
		}
	}
}