/* 
 * The MIT License
 *
 * Copyright 2018 knokko.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
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
 */
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
