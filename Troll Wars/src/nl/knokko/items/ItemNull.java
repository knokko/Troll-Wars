package nl.knokko.items;

import nl.knokko.texture.ImageTexture;

public class ItemNull extends Item {

	ItemNull() {
		super("Null Item", null);
	}
	
	@Override
	protected ImageTexture loadTexture(){
		return null;
	}
}
