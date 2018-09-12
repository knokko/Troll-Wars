package nl.knokko.model.equipment.pants;

public interface ModelArmorLeg extends ModelArmorUnderLeg, ModelArmorUpperLeg {
	
	public static class Factory {
		
		public static ModelArmorLeg createInstance(final float distanceToLegFactor){
			return new ModelArmorLeg(){

				@Override
				public float distanceToLegFactor() {
					return distanceToLegFactor;
				}
				
			};
		}
	}
}
