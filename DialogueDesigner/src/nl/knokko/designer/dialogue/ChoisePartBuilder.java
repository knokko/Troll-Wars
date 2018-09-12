package nl.knokko.designer.dialogue;

import java.util.ArrayList;
import java.util.List;

import nl.knokko.story.dialogue.ChoiseDialogueText;
import nl.knokko.story.dialogue.DialogueText;
import nl.knokko.story.dialogue.PortraitMap;
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
			output.addJavaString(choise.function);
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
