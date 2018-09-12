package nl.knokko.area.creature;

import java.util.List;
import java.util.Random;

import nl.knokko.area.Block;
import nl.knokko.gui.keycode.KeyCode;
import nl.knokko.main.Game;
import nl.knokko.shaders.ShaderType;
import nl.knokko.util.Facing;
import nl.knokko.util.position.SpawnPosition;

public class AreaPlayer extends AreaTroll {
	
	private Random random;

	public AreaPlayer(SpawnPosition spawn) {
		super(spawn);
		random = new Random();
	}
	
	@Override
	public ShaderType getShaderType(){
		return ShaderType.NORMAL;
	}

	@Override
	protected void updateCreature() {
		float yaw = Game.getArea().getCamera().getDegYaw();
		if(Game.getWindow().getInput().isKeyDown(Game.getOptions().keyNorth))
			tryMove(yaw);
		if(Game.getWindow().getInput().isKeyDown(Game.getOptions().keyEast))
			tryMove(yaw + 90);
		if(Game.getWindow().getInput().isKeyDown(Game.getOptions().keySouth))
			tryMove(yaw + 180);
		if(Game.getWindow().getInput().isKeyDown(Game.getOptions().keyWest))
			tryMove(yaw + 270);
		if(Game.getWindow().getInput().isKeyDown(Game.getOptions().keyInteract)){
			if(moveX != 0 || moveZ != 0)
				return;
			Facing facing = Facing.fromYaw(rotationY);
			byte dx = facing.getDX();
			byte dz = facing.getDZ();
			Block[] blocks = area.getTiles().getBlocks(position.getTileX() + dx, position.getTileZ() + dz);
			for(Block block : blocks){
				if(block.getTileY() + 128 >= position.getTileY() && block.getTileY() + 128 < position.getTileY() + 8)
					block.getTile().onInteract(area, position.getTileX() + dx, block.getTileY() + 128, position.getTileZ() + dz, facing, false);
			}
			if(area != Game.getArea().getArea()) return;//prevent NullPointerException when the player has been transferred to another area.
			blocks = area.getTiles().getBlocks(position.getTileX(), position.getTileZ());
			for(Block block : blocks){
				if(block.getTileY() + 128 >= position.getTileY() && block.getTileY() + 128 <= position.getTileY() + 8)
					block.getTile().onInteract(area, position.getTileX(), block.getTileY() + 128, position.getTileZ(), facing, true);
			}
			if(area != Game.getArea().getArea()) return;//prevent NullPointerException when the player has been transferred to another area.
			List<AreaCreature> creatures = area.getCreatures().getList();
			for(AreaCreature ac : creatures){
				if((ac != this && ac.getPosition().getTileX() == position.getTileX() + dx || ac.getPosition().getTileX() == position.getTileX() + 2 * dx)
						&& (ac.getPosition().getTileY() == position.getTileY()) 
						&& (ac.getPosition().getTileZ() == position.getTileZ() + dz || ac.getPosition().getTileZ() == position.getTileZ() + 2 * dz))
					ac.interact();
			}
		}
		if(Game.getWindow().getInput().isKeyDown(KeyCode.KEY_P))
			System.out.println("Player position is (" + position.getTileX() + "," + position.getTileY() + "," + position.getTileZ() + ")");
	}
	
	private void tryMove(float yaw){
		Facing facing = Facing.fromYaw(yaw);
		if(facing == Facing.NORTH)
			tryMoveNorth();
		if(facing == Facing.EAST)
			tryMoveEast();
		if(facing == Facing.SOUTH)
			tryMoveSouth();
		if(facing == Facing.WEST)
			tryMoveWest();
	}

	@Override
	public void interact() {
		throw new RuntimeException("The player can't interact with himself!");
	}
	
	@Override
	public boolean tryMove(int targetX, int targetZ){
		boolean wasntMoving = moveX == 0 && moveZ == 0;
		boolean result = super.tryMove(targetX, targetZ);
		if(moveX == 0 && moveZ == 0){
			Facing facing = Facing.fromYaw(rotationY);
			byte dx = facing.getDX();
			byte dz = facing.getDZ();
			Block[] blocks = area.getTiles().getBlocks(position.getTileX() + dx, position.getTileZ() + dz);
			for(Block block : blocks)
				if(block.getTileY() + 128 >= position.getTileY() && block.getTileY() + 128 < position.getTileY() + 8)
					block.getTile().onWeakInteract(area, position.getTileX() + dx, block.getTileY() + 128, position.getTileZ() + dz, facing, false);
			
			if(area == Game.getArea().getArea()){//prevent NullPointerException when the player has been transferred to another area.
				blocks = area.getTiles().getBlocks(position.getTileX(), position.getTileZ());
				for(Block block : blocks)
					if(block.getTileY() + 128 >= position.getTileY() && block.getTileY() + 128 <= position.getTileY() + 8)
						block.getTile().onWeakInteract(area, position.getTileX(), block.getTileY() + 128, position.getTileZ(), facing, true);
			}
		}
		if(wasntMoving && (moveX != 0 || moveZ != 0)){
			if(Game.getEventHandler().onPlayerMove(position.getTileX(), position.getTileY(), position.getTileZ(), targetX, destinationBlock.getTileY() + 128, targetZ, area.getClass())){
				moveX = 0;
				moveZ = 0;
				return true;
			}
		}
		if(area.hasRandomBattles() && wasntMoving && (moveX != 0 || moveZ != 0)){
			if(random.nextInt(127) < Game.getArea().steps())
				area.startRandomBattle();
			else
				Game.getArea().increaseSteps();
		}
		return result;
	}
}
