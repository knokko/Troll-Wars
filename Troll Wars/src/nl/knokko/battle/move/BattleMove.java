package nl.knokko.battle.move;

import org.lwjgl.util.vector.Matrix4f;

public interface BattleMove {
	
	void update();
	
	void render(Matrix4f viewMatrix);
	
	boolean isDone();
}