package nl.knokko.battle.move.physical.bird;

import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.battle.creature.MovingBattleCreature;
import nl.knokko.battle.damage.AttackType;
import nl.knokko.battle.damage.DamageCalculator;
import nl.knokko.battle.damage.DamageCause;
import nl.knokko.model.ModelPart;
import static nl.knokko.util.Maths.AND;

public class MoveBirdClawAttack extends MoveBirdAttack {
	
	protected final int range;
	
	protected final ModelPart leftLeg;
	protected final ModelPart rightLeg;
	
	protected byte attackState;

	public MoveBirdClawAttack(MovingBattleCreature bird, BattleCreature target, ModelPart leftLeg, ModelPart rightLeg, float bellyRadiusHeight, float legLength) {
		super(bird, target);
		range = (int) (bellyRadiusHeight + legLength);
		this.leftLeg = leftLeg;
		this.rightLeg = rightLeg;
	}

	@Override
	protected int requiredRange() {
		return range;
	}
	
	private static final float speed = 10f;

	@Override
	protected boolean performing() {
		if(attackState == 0){
			if(leftLeg.movePitchTowards(120f, speed))
				attackState = 1;
			return false;
		}
		if(attackState == 1){
			if(AND(leftLeg.movePitchTowards(0f, speed), rightLeg.movePitchTowards(120f, speed)))
				attackState = 2;
			return false;
		}
		if(attackState == 2){
			if(AND(leftLeg.movePitchTowards(120f, speed), rightLeg.movePitchTowards(0f, speed)))
				attackState = 3;
			return false;
		}
		if(attackState == 3){
			if(AND(leftLeg.movePitchTowards(0f, speed), rightLeg.movePitchTowards(120f, speed)))
				attackState = 4;
			return false;
		}
		if(attackState == 4){
			if(AND(leftLeg.movePitchTowards(120f, speed), rightLeg.movePitchTowards(0f, speed)))
				attackState = 5;
			return false;
		}
		if(attackState == 5){
			if(AND(leftLeg.movePitchTowards(0f, speed), rightLeg.movePitchTowards(120f, speed))){
				attackState = 6;
				target.attack(DamageCause.createPhysicalCause(bird.getAttackElement(), AttackType.SLASH), DamageCalculator.calculatePhysicalMonsterDamage(bird, AttackType.SLASH, 7));
			}
			return false;
		}
		if(attackState == 6){
			if(rightLeg.movePitchTowards(0f, speed))
				attackState = 7;
			return false;
		}
		return true;
	}

}
