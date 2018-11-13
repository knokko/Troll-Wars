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
package nl.knokko.battle.move.physical;

import org.lwjgl.util.vector.Matrix4f;

import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.battle.creature.MovingBattleCreature;
import nl.knokko.battle.move.BattleMove;

public abstract class MovePhysical implements BattleMove {
	
	protected final MovingBattleCreature performer;
	protected final BattleCreature target;
	
	private final int startX;
	private final int startZ;
	
	private byte state = -1;

	public MovePhysical(MovingBattleCreature performer, BattleCreature target) {
		this.performer = performer;
		this.target = target;
		startX = performer.getX();
		startZ = performer.getZ();
	}

	@Override
	public void update() {
		if(state == -1){
			float dx = target.getRenderProperties().getCentreX() - performer.getRenderProperties().getCentreX();
			float dz = target.getRenderProperties().getCentreZ() - performer.getRenderProperties().getCentreZ();
			float distance = (float) Math.sqrt(dx * dx + dz * dz);
			if(distance <= requiredDistance())
				state = 0;
			else {
				float speed = performer.getSpeed();
				performer.move(dx / distance * speed, 0, dz / distance * speed);
			}
		}
		else if(state == 0){
			keepPerforming();
			if(performingDone())
				state = 1;
		}
		else if(state == 1){
			int dx = startX - performer.getX();
			int dz = startZ - performer.getZ();
			float speed = performer.getSpeed();
			float distance = (float) Math.sqrt(dx * dx + dz * dz);
			if(distance <= speed)
				state = 2;
			else 
				performer.move(dx / distance * speed, 0, dz / distance * speed);
		}
		else if(state == 2){
			performer.setPosition(startX, 0, startZ);
			if(startX > 0)
				performer.setRotation(0, 270, 0);
			else
				performer.setRotation(0, 90, 0);
			state = 3;
		}
		else if(state != 3)
			throw new IllegalStateException("Invalid state: " + state);
	}
	
	@Override
	public void render(Matrix4f matrix){}

	@Override
	public boolean isDone() {
		return state == 3;
	}
	
	protected abstract boolean performingDone();
	
	protected abstract void keepPerforming();
	
	protected abstract int requiredDistance();
}
