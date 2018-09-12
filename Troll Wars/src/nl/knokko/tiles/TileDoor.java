package nl.knokko.tiles;

import nl.knokko.area.Area;
import nl.knokko.area.AreaDoor.Location;
import nl.knokko.main.Game;
import nl.knokko.texture.ModelTexture;
import nl.knokko.util.Facing;

public class TileDoor extends TileWall {

	public TileDoor(ModelTexture texture, Facing facing) {
		super(texture, facing);
	}
	
	@Override
	public void onInteract(Area area, int tileX, int tileY, int tileZ, Facing facing, boolean inside){
		if((!inside && facing.getOpposite() == this.facing) || (inside && facing == this.facing))
			if(Game.getArea().getArea() == area)
				area.getDoorAt(new Location(tileX, tileY, tileZ)).transferPlayer();
	}
	
	@Override
	public void onWeakInteract(Area area, int tileX, int tileY, int tileZ, Facing facing, boolean inside){
		onInteract(area, tileX, tileY, tileZ, facing, inside);
	}
}