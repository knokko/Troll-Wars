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
