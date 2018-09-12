package nl.knokko.gui;

import nl.knokko.render.main.GuiRenderer;
import nl.knokko.util.color.Color;

public interface Gui {
	
	void render(GuiRenderer renderer);
	
	void update();
	
	void addButtons();
	
	void open();
	
	Color getBackGroundColor();
}
