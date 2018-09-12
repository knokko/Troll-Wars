package nl.knokko.battle.render.properties;

import nl.knokko.battle.creature.BattleCreature;

public abstract class SimpleRenderProperties implements BattleRenderProperties {
	
	protected final BattleCreature creature;
	
	protected final float offsetX;
	protected final float offsetY;
	protected final float offsetZ;
	
	protected final float cilinderRadius;
	protected final float height;

	public SimpleRenderProperties(BattleCreature creature, float offsetX, float offsetY, float offsetZ, float cilinderRadius, float height) {
		this.creature = creature;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetZ = offsetZ;
		this.cilinderRadius = cilinderRadius;
		this.height = height;
	}
	
	@Override
	public float getMinX(){
		return creature.getX() + offsetX - cilinderRadius;
	}

	@Override
	public float getMinY() {
		return creature.getY() + offsetY;
	}
	
	@Override
	public float getMinZ(){
		return creature.getZ() + offsetZ - cilinderRadius;
	}
	
	@Override
	public float getMaxX(){
		return creature.getX() + offsetX + cilinderRadius;
	}

	@Override
	public float getMaxY() {
		return creature.getY() + offsetY + height;
	}
	
	@Override
	public float getMaxZ(){
		return creature.getZ() + offsetZ + cilinderRadius;
	}
	
	@Override
	public float getWidth(){
		return 2 * cilinderRadius;
	}

	@Override
	public float getHeight() {
		return height;
	}
	
	@Override
	public float getDepth(){
		return 2 * cilinderRadius;
	}

	@Override
	public float getCentreX() {
		return creature.getX() + offsetX;
	}
	
	@Override
	public float getCentreY(){
		return creature.getY() + offsetY + height / 2;
	}

	@Override
	public float getCentreZ() {
		return creature.getZ() + offsetZ;
	}

	@Override
	public float getCilinderRadius() {
		return cilinderRadius;
	}
	
	@Override
	public float getHealthX(){
		return creature.getX() + offsetX;
	}
	
	@Override
	public float getHealthY(){
		return creature.getY() + offsetY + 1.4f * height;
	}
	
	@Override
	public float getHealthZ(){
		return creature.getZ() + offsetZ;
	}
	
	@Override
	public float getHealthWidth(){
		return 4 * cilinderRadius;
	}
	
	@Override
	public float getHealthHeight(){
		return height * 0.3f;
	}
	
	@Override
	public float getManaX(){
		return creature.getX() + offsetX;
	}
	
	@Override
	public float getManaY(){
		return creature.getY() + offsetY + 1.15f * height;
	}
	
	@Override
	public float getManaZ(){
		return creature.getZ() + offsetZ;
	}
	
	@Override
	public float getManaWidth(){
		return 4 * cilinderRadius;
	}
	
	@Override
	public float getManaHeight(){
		return height * 0.2f;
	}
	
	@Override
	public boolean isInside(float x, float y, float z){
		if(y >= getMinY() && y <= getMaxY()){
			float dx = x - getCentreX();
			float dz = z - getCentreZ();
			float radius = getCilinderRadius();
			return dx * dx + dz * dz <= radius * radius;
		}
		return false;
	}
}