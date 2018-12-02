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
package nl.knokko.gamestate;

import nl.knokko.gui.component.GuiComponent;
import nl.knokko.gui.dialogue.GuiDialogue;
import nl.knokko.main.Game;
import nl.knokko.story.dialogue.Dialogue;
import nl.knokko.story.dialogues.Dialogues;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;
import nl.knokko.util.resources.Saver;

public class StateDialogue implements GameState {
	
	private Dialogue current;
	private GuiDialogue currentGui;

	public StateDialogue() {}

	@Override
	public void update() {
		if(current == null)
			Game.removeState();
	}

	@Override
	public void render() {}

	@Override
	public void open() {
		if(current == null)
			throw new IllegalStateException("There is no current dialogue");
	}

	@Override
	public void close() {}

	@Override
	public void enable() {}

	@Override
	public void disable() {}

	@Override
	public void save() {
		if(current != null) {
			//BitBuffer data = new BitBuffer();
			BitOutput data = Saver.save("dialogue.data", 100);
			data.addInt(current.getID());
			current.save(data);
			//Saver.save(data, "dialogue.data");
		} 
	}
	
	public void load(BitInput data){
		current = Dialogues.fromID(data.readInt());
		current.load(data);
	}

	@Override
	public boolean renderTransparent() {
		return true;
	}

	@Override
	public boolean updateTransparent() {
		return false;
	}

	@Override
	public void setCurrentGui(GuiComponent gui) {
		throw new UnsupportedOperationException("Use setCurrentDialogue");
	}
	
	@Override
	public GuiComponent getCurrentGui(){
		return currentGui;
	}
	
	public void setCurrentDialogue(Dialogue dialogue){
		current = dialogue;
		if (dialogue != null) {
			currentGui = new GuiDialogue(dialogue);
			currentGui.setState(Game.getGuiState());
			currentGui.init();
		} else {
			currentGui = null;
		}
	}

	@Override
	public void keyPressed(int keyCode) {}
}
