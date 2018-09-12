package nl.knokko.model.equipment.boots;

public interface ModelArmorFoot {
	
	float distanceToFootFactor();
	
	public static class Factory {
		
		public static ModelArmorFoot createInstance(final float distanceToFootFactor){
			return new ModelArmorFoot(){

				@Override
				public float distanceToFootFactor() {
					return distanceToFootFactor;
				}
			};
		}
	}
}