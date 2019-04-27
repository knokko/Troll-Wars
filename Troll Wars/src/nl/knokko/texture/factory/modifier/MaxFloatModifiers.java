package nl.knokko.texture.factory.modifier;

public class MaxFloatModifiers extends CombineFloatModifiers {
	
	public MaxFloatModifiers(int textureWidth, int textureHeight, FloatModifier...modifiers) {
		super(textureWidth, textureHeight, modifiers);
	}

	@Override
	protected float combine(float f1, float f2) {
		return f1 > f2 ? f1 : f2;
	}
}