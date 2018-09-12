package nl.knokko.battle.render.properties;

import org.lwjgl.util.vector.Vector3f;

public interface BattleRenderProperties {
	
	float getMinX();
	
	float getMinY();
	
	float getMinZ();
	
	float getMaxX();
	
	float getMaxY();
	
	float getMaxZ();
	
	float getWidth();
	
	float getHeight();
	
	float getDepth();
	
	float getCentreX();
	
	float getCentreY();
	
	float getCentreZ();
	
	float getCilinderRadius();
	
	float getHealthX();
	
	float getHealthY();
	
	float getHealthZ();
	
	float getHealthWidth();
	
	float getHealthHeight();
	
	float getManaX();
	
	float getManaY();
	
	float getManaZ();
	
	float getManaWidth();
	
	float getManaHeight();
	
	boolean isInside(float x, float y, float z);
	
	Vector3f[] getCastHands();
}