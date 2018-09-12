package nl.knokko.model.equipment.weapon;

public interface ModelBone {
	
	float boneLength();
	
	float boneRadiusX();
	
	float boneRadiusZ();
	
	float boneRadiusTopX();
	
	float boneRadiusTopY();
	
	float boneRadiusTopZ();
	
	float maxRandomFactor();
	
	long seed();
	
	public static class Factory {
		
		public static ModelBone createInstance(final float boneLength, final float boneRadiusX, final float boneRadiusZ, final float boneRadiusTopX, final float boneRadiusTopY, final float boneRadiusTopZ, final float maxRandomFactor, final long seed){
			return new ModelBone(){

				@Override
				public float boneLength() {
					return boneLength;
				}

				@Override
				public float boneRadiusX() {
					return boneRadiusX;
				}

				@Override
				public float boneRadiusZ() {
					return boneRadiusZ;
				}
				
				@Override
				public float boneRadiusTopX(){
					return boneRadiusTopX;
				}
				
				@Override
				public float boneRadiusTopY(){
					return boneRadiusTopY;
				}
				
				@Override
				public float boneRadiusTopZ(){
					return boneRadiusTopZ;
				}

				@Override
				public float maxRandomFactor() {
					return maxRandomFactor;
				}

				@Override
				public long seed() {
					return seed;
				}
				
			};
		}
	}
}
