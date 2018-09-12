package nl.knokko.designer.dialogue;

import java.awt.Font;

import nl.knokko.story.dialogue.PortraitMap;
import nl.knokko.story.dialogue.SimpleDialoguePart;
import nl.knokko.story.dialogue.SimpleDialogueText;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;
import nl.knokko.util.color.Color;

public class SimplePartBuilder extends PartBuilder {
	
	public Text text;
	public Text title;
	
	public Color background;
	
	public int nextIndex = -1;
	public String functionName;

	public SimplePartBuilder(int index, int x, int y) {
		super(index, x, y);
		text = new Text("Write the text...", SimpleDialogueText.DEFAULT_COLOR, SimpleDialogueText.DEFAULT_FONT);
		title = new Text("Write portrait name...", SimpleDialogueText.DEFAULT_COLOR, SimpleDialogueText.DEFAULT_FONT);
		background = SimpleDialoguePart.DEFAULT_BACKGROUND_COLOR;
	}
	
	public SimplePartBuilder(BitInput input, PortraitMap portraits, int index, int x, int y){
		super(index, x, y);
		background = Color.fromBits(input);
		setPortrait(portraits.loadPortrait(input));
		text = new Text(input);
		title = new Text(input);
		nextIndex = input.readInt();
		functionName = input.readJavaString();
	}

	@Override
	public void save(BitOutput output, PortraitMap portraits) {
		output.addBoolean(false);
		background.toBits(output);
		portraits.savePortrait(output, portrait);
		text.save(output);
		title.save(output);
		output.addInt(nextIndex);
		output.addJavaString(functionName);
	}
	
	public static class Text {
		
		public String text;
		public Color color;
		public Font font;
		
		public Text(String text, Color color, Font font){
			this.text = text;
			this.color = color;
			this.font = font;
		}
		
		public Text(BitInput input){
			this(input.readJavaString(), Color.fromBits(input), new Font(input.readJavaString(), input.readInt(), input.readInt()));
		}
		
		public void save(BitOutput output){
			output.addJavaString(text);
			color.toBits(output);
			output.addJavaString(font.getName());
			output.addInt(font.getStyle());
			output.addInt(font.getSize());
		}
		
		public SimpleDialogueText toSDT(){
			return new SimpleDialogueText(text, color, font);
		}
	}
}
