package nl.knokko.util.color;


public class ColorAlpha extends Color {
	
	public static final ColorAlpha TRANSPARENT = new ColorAlpha(0, 0, 0, 0);
	
	private final byte alpha;
	
	public ColorAlpha(byte red, byte green, byte blue, byte alpha){
		super(red, green, blue);
		this.alpha = alpha;
	}

	public ColorAlpha(int red, int green, int blue, int alpha) {
		super(red, green, blue);
		this.alpha = (byte) alpha;
	}
	
	@Override
	public byte getAlpha(){
		return alpha;
	}
	
	@Override
	public int getAlphaI(){
		return alpha & (0xff);
	}
	
	@Override
	public float getAlphaF(){
		return getAlphaI() / 255f;
	}
	
	@Override
	public boolean equals(Object other){
		if(other.getClass() == Color.class && alpha == -1){
			Color c = (Color) other;
			return c.getRed() == getRed() && c.getGreen() == getGreen() && c.getBlue() == getBlue();
		}
		if(other.getClass() == ColorAlpha.class){
			ColorAlpha c = (ColorAlpha) other;
			return c.getRed() == getRed() && c.getGreen() == getGreen() && c.getBlue() == getBlue() && c.alpha == alpha;
		}
		return false;
	}
}
