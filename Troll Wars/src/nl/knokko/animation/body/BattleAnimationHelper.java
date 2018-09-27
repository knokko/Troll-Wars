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
package nl.knokko.animation.body;

public class BattleAnimationHelper {
	
	protected BodyAnimator[] animators;
	
	protected float stepSize;
	
	protected double walkedDistance;

	public BattleAnimationHelper(float stepSize, BodyAnimator... animators) {
		this.stepSize = stepSize;
		this.animators = animators;
	}
	
	public void reset(){
		walkedDistance = 0;
		for(BodyAnimator animator : animators)
			animator.update(0, true, true);
	}
	
	public void walk(double distance){
		walkedDistance += distance;
	}
	
	public void walk(float dx, float dy, float dz){
		walk(Math.sqrt(dx * dx + dy * dy + dz * dz));
		update();
	}
	
	public void update(){
		float progress = (float) ((walkedDistance - (int) (walkedDistance / stepSize) * stepSize) / stepSize);
		for(BodyAnimator animator : animators)
			animator.update(progress, true, true);
	}
}
