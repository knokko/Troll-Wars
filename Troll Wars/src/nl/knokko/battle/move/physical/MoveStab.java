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

public class MoveStab extends MovePhysical {
	
	private byte stabState;
	
	private final ModelPart upperArm;
	private final ModelPart underArm;
	
	private final ItemWeapon weapon;
	private final int requiredDistance;
	
	public MoveStab(MovingBattleCreature performer, ArmCreature arm, BattleCreature target){
		this(performer, target, arm.getUpperArm(), arm.getUnderArm(), arm.getUpperArmLength(), arm.getUnderArmLength(), arm.getWeapon().getRange(performer, arm.getHand()), arm.getWeapon());
	}

	private MoveStab(MovingBattleCreature performer, BattleCreature target, ModelPart upperArm, ModelPart underArm, float upperArmLength, float underArmLength, float weaponRange, ItemWeapon weapon) {
		super(performer, target);
		this.upperArm = upperArm;
		this.underArm = underArm;
		requiredDistance = (int) (Maths.cos(-60) * upperArmLength + Maths.cos(-30) * underArmLength + Maths.cos(60) * weaponRange);
		this.weapon = weapon;
	}

	@Override
	protected boolean performingDone() {
		return stabState == 3;
	}

	@Override
	protected void keepPerforming() {
		if(stabState == 0){
			if(AND(underArm.movePitchTowards(0f, 3f), upperArm.movePitchTowards(250f, 3f)))
				stabState = 1;
		}
		else if(stabState == 1){
			if(AND(underArm.movePitchTowards(30f, 10f), upperArm.movePitchTowards(300f, 10f))){
				stabState = 2;
				target.attack(DamageCalculator.getPhysicalDamageCause(performer, weapon, AttackType.STAB), DamageCalculator.calculatePhysicalDamageOutput(performer, weapon, AttackType.STAB));
			}
		}
		else if(stabState == 2){
			if(AND(underArm.movePitchTowards(0f, 3f), upperArm.movePitchTowards(270f, 3f)))
				stabState = 3;
		}
	}

	@Override
	protected int requiredDistance() {
		return requiredDistance;
	}
}
