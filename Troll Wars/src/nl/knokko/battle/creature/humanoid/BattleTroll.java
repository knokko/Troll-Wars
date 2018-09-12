package nl.knokko.battle.creature.humanoid;

import nl.knokko.animation.body.AnimatorHumanoid;
import nl.knokko.animation.body.BattleAnimationHelper;
import nl.knokko.battle.Battle;
import nl.knokko.battle.creature.ArmCreature;
import nl.knokko.battle.creature.BattleCreature;
import nl.knokko.battle.creature.MovingBattleCreature;
import nl.knokko.battle.creature.SimpleBattleCreature;
import nl.knokko.battle.element.ElementalStatistics;
import nl.knokko.battle.element.SimpleElementStats;
import nl.knokko.battle.move.BattleMove;
import nl.knokko.battle.move.FightMoveOption;
import nl.knokko.battle.move.ItemMoveOption;
import nl.knokko.battle.move.ItemMoveOption.Category;
import nl.knokko.battle.move.physical.MovePunch;
import nl.knokko.battle.render.properties.BattleRenderProperties;
import nl.knokko.battle.render.properties.TrollRenderProperties;
import nl.knokko.equipment.EquipmentFull;
import nl.knokko.inventory.Inventory;
import nl.knokko.items.ItemWeapon;
import nl.knokko.model.ModelPart;
import nl.knokko.model.body.BodyTroll;
import nl.knokko.model.body.hand.HumanoidHandProperties;
import nl.knokko.texture.painter.ModelPainter;
import nl.knokko.texture.painter.TrollPainter;
import nl.knokko.util.Maths;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;
import nl.knokko.util.resources.Resources;
import static nl.knokko.model.body.BodyTroll.Helper.*;

public class BattleTroll extends SimpleBattleCreature implements MovingBattleCreature, ArmCreature {
	
	protected BodyTroll body;
	protected TrollPainter painter;
	
	protected float radiusXZ;
	protected float height;
	
	protected boolean leftArm;

	public BattleTroll(String name, int level, EquipmentFull equipment, BodyTroll body, TrollPainter painter) {
		super(name, 500 + 200 * level, 100 + 50 * level, 30 + 10 * level, 20 + 5 * level, 10 + level, body, painter);
		this.equipment = equipment;
	}
	
	public BattleTroll(BitInput bits){
		super(bits);
		leftArm = bits.readBoolean();
	}
	
	@Override
	public void saveInBattle(Battle battle, BitOutput bits){
		super.saveInBattle(battle, bits);
		bits.addBoolean(leftArm);
	}

	@Override
	public short getID() {
		return BattleCreature.Registry.ID_TROLL;
	}
	
	@Override
	public EquipmentFull createEquipment(){
		return new EquipmentFull();
	}

	@Override
	public long getMaxHealth() {
		return maxHealth;
	}

	@Override
	public ElementalStatistics getElementStats() {
		return SimpleElementStats.TROLL;
	}

	@Override
	public int getArmor() {
		return equipment.getArmor();
	}

	@Override
	public int getResistance() {
		return equipment.getResistance();
	}

	@Override
	public boolean isPlayerControlled() {
		return false;
	}

	@Override
	public BattleMove chooseMove(Battle battle, BattleCreature[] ownTeam, BattleCreature[] opposingTeam) {
		return new MovePunch(this, this, Battle.Selector.selectRandom(opposingTeam));
	}

	@Override
	public ItemMoveOption[] getItems() {
		return null;
	}

	@Override
	public ItemMoveOption[] getItems(Category category) {
		return null;
	}

	@Override
	public FightMoveOption[] getMoves() {
		return null;
	}

	@Override
	public FightMoveOption[] getMoves(nl.knokko.battle.move.FightMoveOption.Category category) {
		return null;
	}

	@Override
	public Inventory getInventory() {
		return null;
	}

	@Override
	public ModelPart getUpperArm() {
		return leftArm ? models.get(0).getChildren()[1] : models.get(0).getChildren()[2];
	}

	@Override
	public ModelPart getUnderArm() {
		return getUpperArm().getChildren()[0];
	}

	@Override
	public float getSpeed() {
		//speed = stepLength * 4 / duration
		//stepLength = sin(maxRotation) * legLength
		//duration is defined by creature
		return getStepSize() / BattleCreature.StepDuration.TROLL;
	}

	@Override
	protected BattleAnimationHelper createAnimator() {
		ModelPart m = models.get(0);
		return new BattleAnimationHelper(getStepSize(), new AnimatorHumanoid(getLeftArm(m), getRightArm(m), getLeftLeg(m), getRightLeg(m), getLeftUnderLeg(m), getRightUnderLeg(m)));
	}

	@Override
	protected void addBody(Object body, ModelPainter painter) {
		this.models.add(Resources.createModelTroll((BodyTroll) body, (TrollPainter) painter));
		this.body = (BodyTroll) body;
		this.painter = (TrollPainter) painter;
		prepareHitBox();
	}
	
	@Override
	protected void addBody(BitInput bits){
		body = BodyTroll.Models.load(bits);
		painter = new TrollPainter(bits);
		models.add(Resources.createModelTroll(body, painter));
		prepareHitBox();
	}
	
	private void prepareHitBox(){
		radiusXZ = Math.max(body.bellyWidth(), body.bellyDepth()) / 2 + (body.upperArmLength() + body.underArmLength()) * Maths.sin(15) + body.shoulderRadius();
		height = body.footMidHeight() + body.underLegLength() + body.upperLegLength() + body.bellyHeight() + body.headHeight();
	}

	@Override
	protected void saveBody(BitOutput bits) {
		body.save(bits);
		painter.save(bits);
	}
	
	@Override
	protected int getYOffset(){
		return (int) (height / 2);
	}
	
	protected float getStepSize(){
		return Maths.sin(AnimatorHumanoid.LEG_ANGLE) * (body.upperLegLength() + body.underLegLength()) *  4;
	}

	@Override
	public float getUpperArmLength() {
		return body.upperArmLength();
	}

	@Override
	public float getUnderArmLength() {
		return body.underArmLength();
	}

	@Override
	public ItemWeapon getWeapon() {
		return leftArm ? equipment.getLeftWeapon() : equipment.getRightWeapon();
	}

	@Override
	public HumanoidHandProperties getHand() {
		return body;
	}

	@Override
	public String getActiveArm() {
		return leftArm ? "Left Arm" : "Right Arm";
	}

	@Override
	public void swapArm() {
		leftArm = !leftArm;
	}

	@Override
	protected BattleRenderProperties createRenderProperties(){
		return new TrollRenderProperties(this, body);
	}
}
