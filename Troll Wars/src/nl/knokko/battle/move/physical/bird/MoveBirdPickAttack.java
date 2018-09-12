package nl.knokko.battle.move.physical.bird;

import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.battle.creature.MovingBattleCreature;
import nl.knokko.battle.damage.AttackType;
import nl.knokko.battle.damage.DamageCalculator;
import nl.knokko.battle.damage.DamageCause;
import nl.knokko.model.ModelPart;
import static nl.knokko.util.Maths.AND;

public class MoveBirdPickAttack extends MoveBirdAttack {
	
	protected final ModelPart body;
	protected final ModelPart head;
	
	protected final int range;
	
	protected final float startBodyRotation;
	protected final float startHeadRotation;
	
	protected byte pickState;

	public MoveBirdPickAttack(MovingBattleCreature bird, BattleCreature target, ModelPart body, ModelPart head, float snailLength) {
		super(bird, target);
		range = (int) snailLength;
		startBodyRotation = body.getPitch();
		startHeadRotation = head.getPitch();
		this.body = body;
		this.head = head;
	}

	@Override
	protected int requiredRange() {
		return range;
	}

	@Override
	protected boolean performing() {
		if(pickState == 0){
			if(AND(body.movePitchTowards(50f, 3f), head.movePitchTowards(310f, 3f)))
				pickState = 1;
			return false;
		}
		if(pickState == 1){
			if(AND(body.movePitchTowards(290f, 10f), head.movePitchTowards(290f, 10f))){
				pickState = 2;
				target.attack(DamageCause.createPhysicalCause(bird.getAttackElement(), AttackType.STAB), DamageCalculator.calculatePhysicalMonsterDamage(bird, AttackType.PRICK, 8));
			}
			return false;
		}
		if(pickState == 2){
			if(AND(body.movePitchTowards(startBodyRotation, 3f), head.movePitchTowards(startHeadRotation, 3f)))
				pickState = 3;
			return false;
		}
		return true;
	}

}
