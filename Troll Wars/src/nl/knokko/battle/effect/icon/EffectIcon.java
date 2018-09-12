package nl.knokko.battle.effect.icon;

import java.awt.Graphics2D;

public interface EffectIcon {
	
	void draw(Graphics2D graphics, int x, int y, int width, int height);
	
	int getTextureID();
}