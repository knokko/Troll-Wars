package nl.knokko.battle.creature.player;

import static nl.knokko.model.body.BodyTroll.Helper.getLeftArm;
import static nl.knokko.model.body.BodyTroll.Helper.getLeftLeg;
import static nl.knokko.model.body.BodyTroll.Helper.getLeftUnderLeg;
import static nl.knokko.model.body.BodyTroll.Helper.getRightArm;
import static nl.knokko.model.body.BodyTroll.Helper.getRightLeg;
import static nl.knokko.model.body.BodyTroll.Helper.getRightUnderLeg;
import static nl.knokko.model.body.BodyTroll.Models.BATTLE_GOTHROK;
import nl.knokko.animation.body.AnimatorHumanoid;
import nl.knokko.animation.body.BattleAnimationHelper;
import nl.knokko.battle.creature.ArmCreature;
import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.battle.creature.MovingBattleCreature;
import nl.knokko.battle.element.BattleElement;
import nl.knokko.battle.render.properties.BattleRenderProperties;
import nl.knokko.battle.render.properties.TrollRenderProperties;
import nl.knokko.items.ItemWeapon;
import nl.knokko.main.Game;
import nl.knokko.model.ModelPart;
import nl.knokko.model.body.BodyTroll;
import nl.knokko.model.body.hand.HumanoidHandProperties;
import nl.knokko.util.Maths;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.resources.Resources;

public class BattleGothrok extends BattlePlayer implements ArmCreature, MovingBattleCreature {
	
	private float height;

	public BattleGothrok(BitInput buffer) {
		super(buffer, Game.getPlayers().gothrok);
		prepareHitBox();
	}

	public BattleGothrok() {
		super(Game.getPlayers().gothrok);
		prepareHitBox();
	}
	
	private void prepareHitBox(){
		height = BATTLE_GOTHROK.footMidHeight() + BATTLE_GOTHROK.underLegLength() + BATTLE_GOTHROK.upperLegLength() + BATTLE_GOTHROK.bellyHeight() + BATTLE_GOTHROK.headHeight();
	}
	
	@Override
	public BattleElement getAttackElement(){
		return BattleElement.PHYSICAL;
	}

	@Override
	public short getID() {
		return BattleCreature.Registry.ID_PLAYER_GOTHROK;
	}

	@Override
	public float getSpeed() {
		return getStepSize() / BattleCreature.StepDuration.TROLL;
	}

	@Override
	public ModelPart getUpperArm() {
		return Game.getPlayers().gothrok.useLeftHand() ? models.get(0).getChildren()[1] : models.get(0).getChildren()[2];
	}

	@Override
	public ModelPart getUnderArm() {
		return getUpperArm().getChildren()[0];
	}
	
	@Override
	protected void addBody(){
		models.add(Resources.createModelTroll(BATTLE_GOTHROK, BodyTroll.Textures.createRargia()));
	}

	@Override
	protected BattleAnimationHelper createAnimator() {
		ModelPart m = models.get(0);
		return new BattleAnimationHelper(getStepSize(), new AnimatorHumanoid(getLeftArm(m), getRightArm(m), getLeftLeg(m), getRightLeg(m), getLeftUnderLeg(m), getRightUnderLeg(m)));
	}
	
	@Override
	protected int getYOffset(){
		return (int) (height / 2);
	}
	
	protected float getStepSize(){
		return Maths.sin(AnimatorHumanoid.LEG_ANGLE) * (BATTLE_GOTHROK.upperLegLength() + BATTLE_GOTHROK.underLegLength()) * 4;
	}

	@Override
	public float getUpperArmLength() {
		return BATTLE_GOTHROK.upperArmLength();
	}

	@Override
	public float getUnderArmLength() {
		return BATTLE_GOTHROK.underArmLength();
	}

	@Override
	public ItemWeapon getWeapon() {
		return Game.getPlayers().gothrok.useLeftHand() ? player.getEquipment().getLeftWeapon() : player.getEquipment().getRightWeapon();
	}

	@Override
	public HumanoidHandProperties getHand() {
		return BATTLE_GOTHROK;
	}

	@Override
	public String getActiveArm() {
		return Game.getPlayers().gothrok.useLeftHand() ? "Left Arm" : "Right Arm";
	}

	@Override
	public void swapArm() {
		Game.getPlayers().gothrok.swapHands();
	}

	@Override
	protected BattleRenderProperties createRenderProperties(){
		return new TrollRenderProperties(this, BATTLE_GOTHROK);
	}
}
