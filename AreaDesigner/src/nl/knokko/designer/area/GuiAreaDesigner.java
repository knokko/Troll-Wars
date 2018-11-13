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
package nl.knokko.designer.area;

import nl.knokko.gui.component.menu.GuiMenu;
import nl.knokko.gui.component.simple.SimpleColorComponent;
import nl.knokko.gui.component.text.TextComponent;
import nl.knokko.gui.util.TextBuilder.Properties;
import nl.knokko.util.color.Color;

public class GuiAreaDesigner extends GuiMenu {
	
	private TextComponent tileNameComponent;
	private TextComponent positionComponent;
	
	@Override
	public Color getBackgroundColor(){
		return null;
	}

	@Override
	protected void addComponents() {
		tileNameComponent = new TextComponent(AreaDesigner.getSelectedTile().getTileName(), Properties.createLabel());
		positionComponent = new TextComponent(getPositionText(), Properties.createLabel());
		addComponent(new SimpleColorComponent(Color.BLUE_PURPLE), 0, 0.9f, 1, 1);
		addComponent(tileNameComponent, 0.65f, 0.91f, 0.85f, 0.99f);
		addComponent(positionComponent, 0.2f, 0.91f, 0.4f, 0.99f);
	}
	
	private String getPositionText() {
		return "position:[" + AreaDesigner.getTargetX() + "," + AreaDesigner.getTargetY() + "," + AreaDesigner.getTargetZ() + "]";
	}
	
	public void updateSelectedTile() {
		tileNameComponent.setText(AreaDesigner.getSelectedTile().getTileName());
	}
	
	public void updateSelectedPosition() {
		positionComponent.setText(getPositionText());
	}
}