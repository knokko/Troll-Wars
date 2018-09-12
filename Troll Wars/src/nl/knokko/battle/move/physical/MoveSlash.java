package nl.knokko.battle.move.physical;

import nl.knokko.battle.creature.ArmCreature;
import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.battle.creature.MovingBattleCreature;
import nl.knokko.battle.damage.AttackType;
import nl.knokko.battle.damage.DamageCalculator;
import nl.knokko.items.ItemWeapon;
import nl.knokko.model.ModelPart;

import static nl.knokko.util.Maths.AND;

public class MoveSlash extends MovePhysical {

	private byte slashState;
	
	private final ModelPart upperArm;
	private final ModelPart underArm;
	
	private final ItemWeapon weapon;
	private final int requiredDistance;
	
	public MoveSlash(MovingBattleCreature performer, ArmCreature arm, BattleCreature target){
		this(performer, target, arm.getUpperArm(), arm.getUnderArm(), arm.getUpperArmLength(), arm.getUnderArmLength(), arm.getWeapon().getRange(performer, arm.getHand()), arm.getWeapon());
	}

	private MoveSlash(MovingBattleCreature performer, BattleCreature target, ModelPart upperArm, ModelPart underArm, float upperArmLength, float underArmLength, float weaponRange, ItemWeapon weapon) {
		super(performer, target);
		this.upperArm = upperArm;
		this.underArm = underArm;
		requiredDistance = (int) Math.sqrt((underArmLength + upperArmLength) * (underArmLength + upperArmLength) + weaponRange * weaponRange);
		this.weapon = weapon;
	}

	@Override
	protected boolean performingDone() {
		return slashState == 3;
	}

	@Override
	protected void keepPerforming() {
		if(slashState == 0){
			if(AND(underArm.movePitchTowards(45f, 3f), upperArm.movePitchTowards(45f, 3f), upperArm.moveRollTowards(45f, 3f)))
				slashState = 1;
		}
		else if(slashState == 1){
			if(AND(underArm.movePitchTowards(0f, 10f), upperArm.movePitchTowards(250f, 10f))){
				slashState = 2;
				target.attack(DamageCalculator.getPhysicalDamageCause(performer, weapon, AttackType.SLASH), DamageCalculator.calculatePhysicalDamageOutput(performer, weapon, AttackType.SLASH));
			}
		}
		else if(slashState == 2){
			if(AND(upperArm.moveRollTowards(0f, 3f), upperArm.movePitchTowards(270f, 3f), underArm.moveRollTowards(0f, 3f)))
				slashState = 3;
		}
	}

	@Override
	protected int requiredDistance() {
		return requiredDistance;
	}
}
