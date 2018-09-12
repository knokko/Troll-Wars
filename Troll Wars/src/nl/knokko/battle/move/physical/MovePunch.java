package nl.knokko.battle.move.physical;

import nl.knokko.battle.creature.ArmCreature;
import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.battle.creature.MovingBattleCreature;
import nl.knokko.battle.damage.AttackType;
import nl.knokko.battle.damage.DamageCalculator;
import nl.knokko.items.ItemWeapon;
import nl.knokko.model.ModelPart;

import static nl.knokko.util.Maths.AND;

public class MovePunch extends MovePhysical {
	
	public static final int BASE_DAMAGE = 5;
	
	protected final ModelPart upperArm;
	protected final ModelPart underArm;
	
	private final ItemWeapon weapon;
	
	protected final int armLength;
	
	private byte punchState = 0;
	
	public MovePunch(MovingBattleCreature performer, ArmCreature arm, BattleCreature target){
		this(performer, target, arm.getUpperArm(), arm.getUnderArm(), arm.getWeapon(), arm.getUnderArmLength() + arm.getUpperArmLength());
	}

	public MovePunch(MovingBattleCreature performer, BattleCreature target, ModelPart upperArm, ModelPart underArm, ItemWeapon weapon, float armLength) {
		super(performer, target);
		this.upperArm = upperArm;
		this.underArm = underArm;
		this.armLength = (int) armLength;
		this.weapon = weapon;
	}

	@Override
	protected boolean performingDone() {
		return punchState == 3;
	}

	@Override
	protected void keepPerforming() {
		if(punchState == 0){
			if(AND(underArm.movePitchTowards(90f, 10f), upperArm.movePitchTowards(270f, 10f)))
				punchState = 1;
		}
		else if(punchState == 1){
			if(AND(underArm.movePitchTowards(0f, 10f), upperArm.movePitchTowards(0f, 10f))){
				punchState = 2;
				target.attack(DamageCalculator.getPhysicalDamageCause(performer, weapon, AttackType.PUNCH), DamageCalculator.calculatePhysicalDamageOutput(performer, weapon, AttackType.PUNCH));
			}
		}
		else if(punchState == 2){
			if(upperArm.movePitchTowards(270f, 10f))
				punchState = 3;
		}
	}

	@Override
	protected int requiredDistance() {
		return armLength;
	}
}
