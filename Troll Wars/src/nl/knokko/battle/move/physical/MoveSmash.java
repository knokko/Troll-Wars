package nl.knokko.battle.move.physical;

import nl.knokko.battle.creature.ArmCreature;
import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.battle.creature.MovingBattleCreature;
import nl.knokko.battle.damage.AttackType;
import nl.knokko.battle.damage.DamageCalculator;
import nl.knokko.items.ItemWeapon;
import nl.knokko.model.ModelPart;

import static nl.knokko.util.Maths.AND;

public class MoveSmash extends MovePhysical {

	private byte smashState;
	
	private final ModelPart upperArm;
	private final ModelPart underArm;
	
	private final ItemWeapon weapon;
	private final int requiredDistance;
	
	public MoveSmash(MovingBattleCreature performer, ArmCreature arm, BattleCreature target){
		this(performer, target, arm.getUpperArm(), arm.getUnderArm(), arm.getUpperArmLength(), arm.getUnderArmLength(), arm.getWeapon().getRange(performer, arm.getHand()), arm.getWeapon());
	}

	private MoveSmash(MovingBattleCreature performer, BattleCreature target, ModelPart upperArm, ModelPart underArm, float upperArmLength, float underArmLength, float weaponRange, ItemWeapon weapon) {
		super(performer, target);
		this.upperArm = upperArm;
		this.underArm = underArm;
		requiredDistance = (int) Math.sqrt((underArmLength + upperArmLength) * (underArmLength + upperArmLength) + weaponRange * weaponRange);
		this.weapon = weapon;
	}

	@Override
	protected boolean performingDone() {
		return smashState == 3;
	}

	@Override
	protected void keepPerforming() {
		if(smashState == 0){
			if(AND(underArm.movePitchTowards(100f, 3f), upperArm.movePitchTowards(70f, 3f)))
				smashState = 1;
		}
		else if(smashState == 1){
			if(AND(underArm.movePitchTowards(0f, 10f * (1f + (100f - underArm.getPitch()) / 100f)), upperArm.movePitchTowards(315f, 10f * (1 + 2 * (upperArm.getPitch() < 180f ? (70f - upperArm.getPitch()) : (430f - upperArm.getPitch())) / 115f)))){
				smashState = 2;
				target.attack(DamageCalculator.getPhysicalDamageCause(performer, weapon, AttackType.SMASH), DamageCalculator.calculatePhysicalDamageOutput(performer, weapon, AttackType.SMASH));
			}
		}
		else if(smashState == 2){
			if(AND(underArm.movePitchTowards(0f, 3f), upperArm.movePitchTowards(270f, 3f)))
				smashState = 3;
		}
	}

	@Override
	protected int requiredDistance() {
		return requiredDistance;
	}
}
