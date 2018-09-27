/* 
 * The MIT License
 *
 * Copyright 2018 knokko.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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