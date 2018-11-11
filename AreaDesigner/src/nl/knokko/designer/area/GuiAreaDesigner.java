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