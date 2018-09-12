package nl.knokko.model.equipment.weapon;

public interface ModelSpear {
	
	float stickRadius();
	
	float stickLength();
	
	float pointMaxRadius();
	
	float pointLength();
	
	public static class Factory {
		
		public static ModelSpear createSimple(){
			return createInstance(0.1f, 1.5f, 0.2f, 1.0f);
		}
		
		public static ModelSpear createInstance(final float stickRadius, final float stickLength, final float pointMaxRadius, final float pointLength){
			return new ModelSpear(){

				@Override
				public float stickRadius() {
					return stickRadius;
				}

				@Override
				public float stickLength() {
					return stickLength;
				}

				@Override
				public float pointMaxRadius() {
					return pointMaxRadius;
				}

				@Override
				public float pointLength() {
					return pointLength;
				}
			};
		}
	}
}
