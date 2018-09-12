package nl.knokko.model.equipment.weapon;

public interface ModelSword {
	
	float handleRadius();
	
	float handleLength();
	
	float middleLength();
	
	float middleWidth();
	
	float bladeLength();
	
	float bladeWidth();
	
	float bladeDepth();
	
	public static class Factory {
		
		public static ModelSword createSimple(){
			return createInstance(1.5f, 4.9f, 8f, 2.5f, 29.25f, 4f, 2f);
		}
		
		public static ModelSword createInstance(final float handleRadius, final float handleLength,
				final float middleLength, final float middleWidth, final float bladeLength,
				final float bladeWidth, final float bladeDepth){
			return new ModelSword(){

				@Override
				public float handleRadius() {
					return handleRadius;
				}

				@Override
				public float handleLength() {
					return handleLength;
				}

				@Override
				public float middleLength() {
					return middleLength;
				}

				@Override
				public float middleWidth() {
					return middleWidth;
				}

				@Override
				public float bladeLength() {
					return bladeLength;
				}

				@Override
				public float bladeWidth() {
					return bladeWidth;
				}

				@Override
				public float bladeDepth() {
					return bladeDepth;
				}
			};
		}
	}
}