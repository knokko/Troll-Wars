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

import nl.knokko.story.dialogue.PortraitMap;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;

public class DialogueBuilder {
	
	private static PartBuilder loadPartBuilder(BitInput data, PortraitMap portraits, int index){
		int x = data.readInt();
		int y = data.readInt();
		if(data.readBoolean())
			return new ChoisePartBuilder(data, portraits, index, x, y);
		else
			return new SimplePartBuilder(data, portraits, index, x, y);
	}
	
	private static void savePartBuilder(BitOutput data, PartBuilder part, PortraitMap portraits){
		data.addInt(part.designerX);
		data.addInt(part.designerY);
		part.save(data, portraits);
	}
	
	public boolean canLeave;
	
	public List<PartBuilder> parts;
	
	public PortraitMap portraits;

	public DialogueBuilder() {
		canLeave = true;
		parts = new ArrayList<PartBuilder>();
		portraits = new PortraitMap();
		portraits.add("EMPTY");
	}
	
	public DialogueBuilder(BitInput data){
		canLeave = data.readBoolean();
		portraits = new PortraitMap(data);
		int size = data.readInt();
		parts = new ArrayList<PartBuilder>(size);
		for(int i = 0; i < size; i++)
			parts.add(loadPartBuilder(data, portraits, i));
	}

	public void save(BitOutput data) {
		data.addBoolean(canLeave);
		portraits.save(data);
		data.addInt(parts.size());
		for(PartBuilder part : parts)
			savePartBuilder(data, part, portraits);
	}
}