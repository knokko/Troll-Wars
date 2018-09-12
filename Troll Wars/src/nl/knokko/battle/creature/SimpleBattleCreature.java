package nl.knokko.battle.creature;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import nl.knokko.animation.body.BattleAnimationHelper;
import nl.knokko.battle.Battle;
import nl.knokko.battle.damage.DamageCalculator;
import nl.knokko.battle.damage.DamageCause;
import nl.knokko.battle.effect.management.SimpleStatusEffects;
import nl.knokko.battle.effect.management.StatusEffects;
import nl.knokko.battle.element.BattleElement;
import nl.knokko.battle.render.properties.BattleRenderProperties;
import nl.knokko.equipment.Equipment;
import nl.knokko.equipment.EquipmentNone;
import nl.knokko.main.Game;
import nl.knokko.model.ModelPart;
import nl.knokko.shaders.ShaderType;
import nl.knokko.texture.painter.ModelPainter;
import nl.knokko.util.Maths;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;

public abstract class SimpleBattleCreature implements BattleCreature {
	
	protected long maxHealth;
	protected long health;
	
	protected long maxMana;
	protected long mana;
	
	protected int strength;
	protected int spirit;
	protected int turnSpeed;
	
	protected List<ModelPart> models;
	protected final StatusEffects statusEffects;
	protected final BattleRenderProperties renderProperties;
	protected Equipment equipment;
	protected BattleAnimationHelper animator;
	protected String name;
	
	protected int x;
	protected int y;
	protected int z;
	
	protected float yaw;

	public SimpleBattleCreature(String name, long maxHealth, long maxMana, int strength, int spirit, int turnSpeed, Object body, ModelPainter painter) {
		this.name = name;
		this.maxHealth = Game.getOptions().getDifficulty().determineHealth(maxHealth);
		this.health = this.maxHealth;
		this.maxMana = Game.getOptions().getDifficulty().determineMana(maxMana);
		this.mana = this.maxMana;
		this.models = new ArrayList<ModelPart>(2);
		this.statusEffects = new SimpleStatusEffects();
		this.equipment = createEquipment();
		this.addBody(body, painter);
		this.animator = createAnimator();
		this.strength = Game.getOptions().getDifficulty().determineStrength(strength);
		this.spirit = Game.getOptions().getDifficulty().determineSpirit(spirit);
		this.turnSpeed = Game.getOptions().getDifficulty().determineTurnSpeed(turnSpeed);
		this.renderProperties = createRenderProperties();
	}
	
	public SimpleBattleCreature(BitInput buffer){
		
		name = buffer.readJavaString();
		maxHealth = buffer.readLong();
		health = buffer.readLong();
		maxMana = buffer.readLong();
		mana = buffer.readLong();
		strength = buffer.readInt();
		spirit = buffer.readInt();
		turnSpeed = buffer.readInt();
		x = buffer.readInt();
		y = buffer.readInt();
		z = buffer.readInt();
		yaw = buffer.readFloat();
		
		statusEffects = new SimpleStatusEffects(buffer);
		
		equipment = createEquipment();
		equipment.load(buffer);
		
		models = new ArrayList<ModelPart>(1);
		addBody(buffer);
		
		animator = createAnimator();
		renderProperties = createRenderProperties();
	}

	@Override
	public void saveInBattle(Battle battle, BitOutput buffer) {
		buffer.addJavaString(name);
		buffer.addLong(maxHealth);
		buffer.addLong(health);
		buffer.addLong(maxMana);
		buffer.addLong(mana);
		buffer.addInt(strength);
		buffer.addInt(spirit);
		buffer.addInt(turnSpeed);
		buffer.addInt(x);
		buffer.addInt(y);
		buffer.addInt(z);
		buffer.addFloat(yaw);
		statusEffects.save(battle, buffer);
		equipment.save(buffer);
		saveBody(buffer);
	}
	
	@Override
	public Equipment getEquipment(){
		return equipment;
	}
	
	@Override
	public BattleElement getAttackElement(){
		return BattleElement.PHYSICAL;
	}
	
	@Override
	public StatusEffects getStatusEffects(){
		return statusEffects;
	}

	@Override
	public long getHealth() {
		return health;
	}
	
	@Override
	public long getMaxHealth(){
		return maxHealth;
	}

	@Override
	public long getMana() {
		return mana;
	}

	@Override
	public long getMaxMana() {
		return maxMana;
	}

	@Override
	public int getFocus() {
		return 0;
	}

	@Override
	public int getMaxFocus() {
		return 0;
	}

	@Override
	public int getEnergy() {
		return 0;
	}

	@Override
	public int getMaxEnergy() {
		return 0;
	}

	@Override
	public int getRage() {
		return 0;
	}

	@Override
	public int getMaxRage() {
		return 0;
	}
	
	@Override
	public int getStrength(){
		return strength;
	}
	
	@Override
	public int getSpirit(){
		return spirit;
	}
	
	@Override
	public int getTurnSpeed(){
		return turnSpeed;
	}

	@Override
	public void restoreHealth(long amount) {
		health += amount;
		if(health > getMaxHealth())
			health = getMaxHealth();
	}

	@Override
	public void restoreMana(long amount) {
		mana += amount;
		if(mana > getMaxMana())
			mana = getMaxMana();
	}
	
	@Override
	public void restoreFocus(int amount){}

	@Override
	public void attack(DamageCause cause, long damage) {
		health -= DamageCalculator.calculateFinalDamage(this, cause, damage);
		if(health < 0)
			health = 0;
		if(health > getMaxHealth())
			health = getMaxHealth();
	}
	
	@Override
	public void inflictTrueDamage(long amount){
		health -= amount;
		if(health < 0)
			health = 0;
	}
	
	@Override
	public boolean isFainted(){
		return health <= 0;
	}
	
	@Override
	public List<ModelPart> getModels(){
		return models;
	}
	
	@Override
	public ShaderType getShaderType(){
		return ShaderType.NORMAL;
	}
	
	@Override
	public void move(float dx, float dy, float dz){
		animator.walk(dx, dy, dz);
		x += Math.round(dx);
		y += Math.round(dy);
		z += Math.round(dz);
		if(dx != 0 || dz != 0)
			yaw = (float) -Maths.getYaw(new Vector3f(dx, dy, dz));
	}
	
	@Override
	public void setPosition(int x, int y, int z){
		this.x = x;
		this.y = y - getYOffset();
		this.z = z;
		animator.reset();
	}
	
	@Override
	public void setRotation(float pitch, float yaw, float roll){
		this.yaw = yaw;
	}
	
	@Override
	public int getX(){
		return x;
	}
	
	@Override
	public int getY(){
		return y + getYOffset();
	}
	
	@Override
	public int getZ(){
		return z;
	}
	
	@Override
	public Matrix4f getMatrix(){
		return Maths.createTransformationMatrix(new Vector3f(renderProperties.getCentreX(), renderProperties.getMinY(), renderProperties.getCentreZ()), 0, -yaw, 0, 1);
	}
	
	@Override
	public String getName(){
		return name;
	}
	
	@Override
	public BattleRenderProperties getRenderProperties(){
		return renderProperties;
	}
	
	protected Equipment createEquipment(){
		return new EquipmentNone();
	}
	
	protected abstract BattleAnimationHelper createAnimator();
	
	protected abstract void addBody(Object body, ModelPainter painter);
	
	protected abstract void addBody(BitInput bits);
	
	protected abstract void saveBody(BitOutput bits);
	
	protected abstract int getYOffset();
	
	protected abstract BattleRenderProperties createRenderProperties();
}
