package nl.knokko.battle.move.physical.bird;

import org.lwjgl.util.vector.Matrix4f;

import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.battle.creature.MovingBattleCreature;
import nl.knokko.battle.move.BattleMove;

public abstract class MoveBirdAttack implements BattleMove {
	
	protected final MovingBattleCreature bird;
	protected final BattleCreature target;
	
	protected byte state;
	
	protected int startX;
	protected int startY;
	protected int startZ;

	public MoveBirdAttack(MovingBattleCreature bird, BattleCreature target) {
		this.bird = bird;
		this.target = target;
		startX = bird.getX();
		startY = bird.getY();
		startZ = bird.getZ();
	}

	@Override
	public void update() {
		if(state == 0){
			float dx = target.getRenderProperties().getCentreX() - bird.getRenderProperties().getCentreX();
			float dy = target.getRenderProperties().getCentreY() - bird.getRenderProperties().getCentreY();
			float dz = target.getRenderProperties().getCentreZ() - bird.getRenderProperties().getCentreZ();
			float distance = (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
			if(distance < requiredRange()){
				state = 1;
				return;
			}
			float speed = bird.getSpeed();
			bird.move(dx / distance * speed, dy / distance * speed, dz / distance * speed);
		}
		if(state == 1){
			if(performing()){
				state = 2;
				return;
			}
		}
		if(state == 2){
			int dx = startX - bird.getX();
			int dy = startY - bird.getY();
			int dz = startZ - bird.getZ();
			float distance = (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
			float speed = bird.getSpeed();
			if(distance <= speed){
				bird.setPosition(startX, startY, startZ);
				if(startX > 0)
					bird.setRotation(0, 270, 0);
				else
					bird.setRotation(0, 90, 0);
				state = 3;
			}
			else
				bird.move(dx / distance * speed, dy / distance * speed, dz / distance * speed);
		}
	}
	
	@Override
	public void render(Matrix4f matrix){}

	@Override
	public boolean isDone() {
		return state == 3;
	}
	
	protected abstract int requiredRange();
	
	/**
	 * This method will be called every tick until the job is finished.
	 * @return true if the performing has been finished, false if not
	 */
	protected abstract boolean performing();
}
