package nl.knokko.tiles;

import nl.knokko.area.creature.AreaCreature;
import nl.knokko.model.type.TileModel;
import nl.knokko.render.tile.TileModels;
import nl.knokko.shaders.ShaderType;
import nl.knokko.texture.ModelTexture;
import nl.knokko.tiles.Tiles.RenderForm;
import nl.knokko.util.Facing;

public class TileLiquid extends TileBottom {

	public TileLiquid(ModelTexture texture) {
		super(texture);
	}
	
	@Override
	public RenderForm getRenderForm(){
		return RenderForm.QUAD;
	}
	
	@Override
	public ShaderType getShaderType(){
		return ShaderType.LIQUID;
	}
	
	@Override
	public TileModel getModel(){
		return TileModels.BOTTOM_LIQUID;
	}
	
	@Override
	public boolean canMoveTo(AreaCreature creature, int ownTileY, int moverTileY, Facing facing) {
		return false;
	}
	
	@Override
	public int getExitTileY(int ownTileY, Facing facing) {
		throw new RuntimeException("Creatures can not move to another tile from this tile because they can not walk over this tile!");
	}
	
	@Override
	public boolean canExitTile(int ownTileY, Facing facing) {
		throw new RuntimeException("Creatures can not move to another tile from this tile because they can not walk over this tile!");
	}
}
