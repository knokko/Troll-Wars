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
package nl.knokko.designer.dialogue;

import java.util.ArrayList;
import java.util.List;

import nl.knokko.story.dialogue.ChoiseDialogueText;
import nl.knokko.story.dialogue.DialogueText;
import nl.knokko.story.dialogue.PortraitMap;
import nl.knokko.story.dialogue.action.DialogueConditions;
import nl.knokko.story.dialogue.action.DialogueFunctions;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;
import nl.knokko.util.color.Color;

public class ChoisePartBuilder extends PartBuilder {
	
	public List<Choise> choises;
	public SimplePartBuilder.Text title;
	public Color background;

	public ChoisePartBuilder(int index, int x, int y) {
		super(index, x, y);
		choises = new ArrayList<Choise>();
		choises.add(new Choise(new SimplePartBuilder.Text("Enter text...", ChoiseDialogueText.DEFAULT_COLOR, ChoiseDialogueText.DEFAULT_FONT), -1, null, null));
		title = new SimplePartBuilder.Text("Gothrok", ChoiseDialogueText.DEFAULT_COLOR, ChoiseDialogueText.DEFAULT_FONT);
		background = Color.BLUE;
	}
	
	public ChoisePartBuilder(BitInput input, PortraitMap portraits, int index, int x, int y){
		super(index, x, y);
		background = Color.fromBits(input);
		setPortrait(portraits.loadPortrait(input));
		int size = input.readInt();
		choises = new ArrayList<Choise>(size);
		for(int i = 0; i < size; i++)
			choises.add(new Choise(new SimplePartBuilder.Text(input), input.readInt(), input.readJavaString(), input.readJavaString()));
		title = new SimplePartBuilder.Text(input);
	}

	@Override
	public void save(BitOutput output, PortraitMap portraits) {
		output.addBoolean(true);
		background.toBits(output);
		portraits.savePortrait(output, portrait);
		output.addInt(choises.size());
		for(Choise choise : choises){
			choise.text.save(output);
			output.addInt(choise.nextIndex);
			if (choise.function != null) {
				try {
					DialogueFunctions.class.getMethod(choise.function);
				} catch (NoSuchMethodException ex) {
					System.out.println("There is no dialogue function with name " + choise.function);
				}
			} else if (choise.nextIndex == -1) {
				System.out.println("The choise part with text '" + choise.text.text + "' has no next part and no function!");
			}
			output.addJavaString(choise.function);
			if (choise.condition != null) {
				try {
					DialogueConditions.class.getMethod(choise.condition);
				} catch (NoSuchMethodException ex) {
					System.out.println("There is no dialogue condition with name " + choise.condition);
				}
			}
			output.addJavaString(choise.condition);
		}
		title.save(output);
	}
	
	public DialogueText[] toDialogueTexts(){
		DialogueText[] texts = new DialogueText[choises.size()];
		int i = 0;
		for(Choise choise : choises){
			texts[i] = new ChoiseDialogueText(choise.text.text, choise.text.color, choise.text.color, choise.text.font);
			i++;
		}
		return texts;
	}
	
	public static class Choise {
		
		public Choise(SimplePartBuilder.Text text, int nextIndex, String function, String condition){
			this.text = text;
			this.nextIndex = nextIndex;
			this.function = function;
			this.condition = condition;
		}
		
		public SimplePartBuilder.Text text;
		
		public int nextIndex;
		
		public String function;
		
		public String condition;
	}
}
