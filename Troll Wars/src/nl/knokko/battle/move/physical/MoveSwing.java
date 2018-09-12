package nl.knokko.battle.move.physical;

import nl.knokko.battle.creature.ArmCreature;
import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.battle.creature.MovingBattleCreature;
import nl.knokko.battle.damage.AttackType;
import nl.knokko.battle.damage.DamageCalculator;
import nl.knokko.items.ItemWeapon;
import nl.knokko.model.ModelPart;

import static nl.knokko.util.Maths.AND;

public class MoveSwing extends MovePhysical {

	private byte swingState;
	
	private final ModelPart upperArm;
	private final ModelPart underArm;
	
	private final ItemWeapon weapon;
	private final int requiredDistance;
	
	public MoveSwing(MovingBattleCreature performer, ArmCreature arm, BattleCreature target){
		this(performer, target, arm.getUpperArm(), arm.getUnderArm(), arm.getUpperArmLength(), arm.getUnderArmLength(), arm.getWeapon().getRange(performer, arm.getHand()), arm.getWeapon());
	}

	private MoveSwing(MovingBattleCreature performer, BattleCreature target, ModelPart upperArm, ModelPart underArm, float upperArmLength, float underArmLength, float weaponRange, ItemWeapon weapon) {
		super(performer, target);
		this.upperArm = upperArm;
		this.underArm = underArm;
		requiredDistance = (int) Math.sqrt((underArmLength + upperArmLength) * (underArmLength + upperArmLength) + weaponRange * weaponRange);
		this.weapon = weapon;
	}

	@Override
	protected boolean performingDone() {
		return swingState == 3;
	}

	@Override
	protected void keepPerforming() {
		if(swingState == 0){
			if(AND(underArm.movePitchTowards(90f, 3f), underArm.moveRollTowards(90f, 3f), upperArm.movePitchTowards(0f, 3f)))
				swingState = 1;
		}
		else if(swingState == 1){
			if(AND(underArm.movePitchTowards(0f, 10f), upperArm.moveYawTowards(270f, 10f))){
				swingState = 2;
				target.attack(DamageCalculator.getPhysicalDamageCause(performer, weapon, AttackType.SWING), DamageCalculator.calculatePhysicalDamageOutput(performer, weapon, AttackType.SWING));
			}
		}
		else if(swingState == 2){
			if(AND(upperArm.moveYawTowards(0f, 3f), upperArm.movePitchTowards(270f, 3f), underArm.moveRollTowards(0f, 3f)))
				swingState = 3;
		}
	}

	@Override
	protected int requiredDistance() {
		return requiredDistance;
	}
}
