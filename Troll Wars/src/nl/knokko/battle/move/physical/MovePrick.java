package nl.knokko.battle.move.physical;

import nl.knokko.battle.creature.ArmCreature;
import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.battle.creature.MovingBattleCreature;
import nl.knokko.battle.damage.AttackType;
import nl.knokko.battle.damage.DamageCalculator;
import nl.knokko.items.ItemWeapon;
import nl.knokko.model.ModelPart;
import nl.knokko.util.Maths;

import static nl.knokko.util.Maths.AND;

public class MovePrick extends MovePhysical {

	private byte prickState;
	
	private final ModelPart upperArm;
	private final ModelPart underArm;
	
	private final ItemWeapon weapon;
	private final int requiredDistance;
	
	public MovePrick(MovingBattleCreature performer, ArmCreature arm, BattleCreature target){
		this(performer, target, arm.getUpperArm(), arm.getUnderArm(), arm.getUpperArmLength(), arm.getUnderArmLength(), arm.getWeapon().getRange(performer, arm.getHand()), arm.getWeapon());
	}

	private MovePrick(MovingBattleCreature performer, BattleCreature target, ModelPart upperArm, ModelPart underArm, float upperArmLength, float underArmLength, float weaponRange, ItemWeapon weapon) {
		super(performer, target);
		this.upperArm = upperArm;
		this.underArm = underArm;
		requiredDistance = (int) (Maths.cos(245) * upperArmLength + Maths.cos(290) * underArmLength + Maths.cos(20) * weaponRange);
		this.weapon = weapon;
	}

	@Override
	protected boolean performingDone() {
		return prickState == 3;
	}

	@Override
	protected void keepPerforming() {
		if(prickState == 0){
			if(AND(underArm.movePitchTowards(0f, 3f), upperArm.movePitchTowards(225f, 3f)))
				prickState = 1;
		}
		else if(prickState == 1){
			if(AND(underArm.movePitchTowards(45f, 5f), upperArm.movePitchTowards(245f, 5f))){
				prickState = 2;
				target.attack(DamageCalculator.getPhysicalDamageCause(performer, weapon, AttackType.PRICK), DamageCalculator.calculatePhysicalDamageOutput(performer, weapon, AttackType.PRICK));
			}
		}
		else if(prickState == 2){
			if(AND(underArm.movePitchTowards(0f, 3f), upperArm.movePitchTowards(270f, 3f)))
				prickState = 3;
		}
	}

	@Override
	protected int requiredDistance() {
		return requiredDistance;
	}
}
