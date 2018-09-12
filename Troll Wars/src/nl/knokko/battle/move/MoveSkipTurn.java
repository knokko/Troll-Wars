package nl.knokko.battle.move;

import org.lwjgl.util.vector.Matrix4f;

public class MoveSkipTurn implements BattleMove {

	public MoveSkipTurn() {}

	@Override
	public void update() {}
	
	@Override
	public void render(Matrix4f viewMatrix){}

	@Override
	public boolean isDone() {
		return true;
	}
}
