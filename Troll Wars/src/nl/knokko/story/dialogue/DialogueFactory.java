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
import java.lang.reflect.Method;
import java.util.Arrays;

import nl.knokko.main.Game;
import nl.knokko.story.dialogue.action.DialogueFunctions;
import nl.knokko.texture.ImageTexture;
import nl.knokko.util.Maths;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;
import nl.knokko.util.color.Color;

public final class DialogueFactory {
	
	public static final byte MAX_CHOISES = 32;
	public static final byte MIN_CHOISES = 1;
	public static final byte CHOISE_BITCOUNT = 5;
	
	public static Dialogue loadFromBits(BitInput data, int id){
		boolean canLeave = data.readBoolean();
		int amount = data.readInt();
		PortraitMap portraits = new PortraitMap(data);
		byte bitCount = Maths.log2Up(amount + 1);
		Part[] parts = new Part[amount];
		Instance dialogue = new Instance(canLeave, id);
		for(int i = 0; i < amount; i++)
			parts[i] = new Part(loadPart(data, dialogue, bitCount, portraits), i);
		dialogue.setParts(parts);
		return dialogue;
	}
	
	private static DialoguePart loadPart(BitInput data, Instance dialogue, byte bitCount, PortraitMap map){
		if(data.readBoolean()){
			Color background = Color.fromBits(data);
			ImageTexture portrait = map.loadPortrait(data);
			SimpleDialogueText title = loadSimple(data, dialogue);
			ChoiseDialogueText[] choises = new ChoiseDialogueText[(int) (data.readNumber(CHOISE_BITCOUNT, false) + MIN_CHOISES)];
			for(int i = 0; i < choises.length; i++)
				choises[i] = loadChoise(data, dialogue, bitCount);
			return new ChoiseDialoguePart(dialogue, background, portrait, title, choises);
		}
		else {
			Color background = Color.fromBits(data);
			ImageTexture portrait = map.loadPortrait(data);
			SimpleDialogueText title = loadSimple(data, dialogue);
			SimpleDialogueText text = loadSimple(data, dialogue);
			SimpleDialoguePart part = new SimpleDialoguePart(dialogue, background, portrait, title, text);
			part.setNext(loadAction(data, dialogue, bitCount));
			return part;
		}
	}
	
	private static Font loadFont(BitInput data){
		return new Font(data.readJavaString(), data.readInt(), data.readInt());
	}
	
	private static ChoiseDialogueText loadChoise(BitInput data, Instance instance, byte bitCount){
		ChoiseDialogueText choise = new ChoiseDialogueText(data.readJavaString(), Color.fromBits(data), Color.fromBits(data), loadFont(data));
		choise.setAction(loadAction(data, instance, bitCount));
		return choise;
	}
	
	private static SimpleDialogueText loadSimple(BitInput data, Instance instance){
		return new SimpleDialogueText(data.readJavaString(), Color.fromBits(data), loadFont(data));
	}
	
	private static Runnable loadAction(BitInput data, Instance dialogue, byte bitCount){
		String action = data.readJavaString();
		int next = (int) data.readNumber(bitCount, false);
		if(!action.isEmpty())
			return new ActionFunction(dialogue, next, action);
		return new ActionNextPart(dialogue, next);
	}
	
	private static class Instance implements Dialogue {
		
		private Part current;
		
		private Part[] parts;
		
		private final int id;
		private final boolean canLeave;
		
		private Instance(boolean canLeave, int id){
			this.canLeave = canLeave;
			this.id = id;
		}

		@Override
		public boolean canLeave() {
			return canLeave;
		}

		@Override
		public DialoguePart current() {
			return current.getPart();
		}

		@Override
		public void setCurrent(DialoguePart newPart) {
			for(Part part : parts){
				if(part.getPart() == newPart){
					current = part;
					return;
				}
			}
			throw new IllegalArgumentException("The following part doesn't belong to this dialogue: " + Arrays.deepToString(newPart.getText()));
		}
		
		private void setCurrent(int index){
			if(index == parts.length)
				Game.closeDialogue();
			else
				current = parts[index];
		}

		@Override
		public void save(BitOutput output) {
			output.addNumber(current.getID(), Maths.log2Up(parts.length), false);
		}

		@Override
		public void load(BitInput input) {
			current = parts[(int) input.readNumber(Maths.log2Up(parts.length), false)];
		}

		@Override
		public int getID() {
			return id;
		}
		
		private void setParts(Part[] parts){
			this.parts = parts;
			current = parts[0];
			for(Part part : parts)
				if(part == null)
					throw new NullPointerException();
		}
	}
	
	private static class ActionNextPart implements Runnable {
		
		private final Instance dialogue;
		
		private final int index;
		
		private ActionNextPart(Instance dialogue, int index){
			this.dialogue = dialogue;
			this.index = index;
		}

		@Override
		public void run() {
			dialogue.setCurrent(index);
		}
	}
	
	private static class ActionFunction extends ActionNextPart {
		
		private final Method method;
		
		private ActionFunction(Instance dialogue, int index, String name){
			super(dialogue, index);
			try {
				method = DialogueFunctions.class.getDeclaredMethod(name);
			} catch (Exception e) {
				throw new IllegalArgumentException("Unknown function: " + name, e);
			} 
		}
		
		@Override
		public void run() {
			super.run();
			try {
				method.invoke(null);
			} catch(Exception e){
				throw new IllegalArgumentException("Incorrect function: " + method.getName(), e);
			}
		}
	}
	
	private static class Part {
		
		private final DialoguePart part;
		private final int id;
		
		private Part(DialoguePart dialoguePart, int numericID){
			part = dialoguePart;
			id = numericID;
		}
		
		private DialoguePart getPart(){
			return part;
		}
		
		private int getID(){
			return id;
		}
	}
}
