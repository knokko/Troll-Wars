package nl.knokko.model.equipment.chestplate;

public interface ModelArmorBelly {
	
	float distanceToBellyFactor();
	
	public static class Factory {
		
		public static ModelArmorBelly createInstance(final float distanceToBellyFactor){
			return new ModelArmorBelly(){

				@Override
				public float distanceToBellyFactor() {
					return distanceToBellyFactor;
				}
				
			};
		}
	}
}
