package nl.knokko.model.equipment.helmet;

public interface ModelArmorHead {
	
	float distanceToHeadFactor();
	
	public static class Factory {
		
		public static ModelArmorHead createInstance(final float distanceToHeadFactor){
			return new ModelArmorHead(){

				@Override
				public float distanceToHeadFactor() {
					return distanceToHeadFactor;
				}
				
			};
		}
	}
}