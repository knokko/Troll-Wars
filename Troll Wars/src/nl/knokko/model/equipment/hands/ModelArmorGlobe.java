package nl.knokko.model.equipment.hands;

public interface ModelArmorGlobe {
	
	float distanceToHandFactor();
	
	public static class Factory {
		
		public static ModelArmorGlobe createInstance(final float distanceToHandFactor){
			return new ModelArmorGlobe(){

				@Override
				public float distanceToHandFactor() {
					return distanceToHandFactor;
				}
			};
		}
	}
}