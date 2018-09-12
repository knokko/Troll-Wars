package nl.knokko.model.equipment.chestplate;

public interface ModelArmorArm extends ModelArmorUnderArm, ModelArmorUpperArm {
	
	public static class Factory {
		
		public static ModelArmorArm createInstance(final float distanceToArmFactor, final float distanceToShoulderFactor){
			return new ModelArmorArm(){

				@Override
				public float distanceToArmFactor() {
					return distanceToArmFactor;
				}

				@Override
				public float distanceToShoulderFactor() {
					return distanceToShoulderFactor;
				}
			};
		}
	}
}
