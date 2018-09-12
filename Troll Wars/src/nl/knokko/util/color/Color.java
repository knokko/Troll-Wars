package nl.knokko.util.color;

import org.lwjgl.util.vector.Vector3f;

import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;

public class Color implements Comparable<Color> {
	
	public static final Color BLACK = new Color(0, 0, 0);
	public static final Color DARK_GRAY = new Color(63, 63, 63);
	public static final Color GRAY = new Color(127, 127, 127);
	public static final Color LIGHT_GRAY = new Color(190, 190, 190);
	public static final Color WHITE = new Color(255, 255, 255);
	
	public static final Color RED = new Color(255, 0, 0);
	public static final Color GREEN = new Color(0, 255, 0);
	public static final Color BLUE = new Color(0, 0, 255);
	
	public static final Color DARK_RED = new Color(200, 0, 0);
	public static final Color DARK_GREEN = new Color(0, 200, 0);
	public static final Color DARK_BLUE = new Color(0, 0, 200);
	
	public static final Color PURPLE = new Color(178, 0, 255);
	public static final Color DARK_PURPLE = new Color(89, 0, 127);
	public static final Color BLUE_PURPLE = new Color(33, 0, 127);
	
	public static final Color ORANGE = new Color(250, 100, 0);
	public static final Color DARK_ORANGE = new Color(180, 70, 0);
	
	public static final Color YELLOW = new Color(250, 200, 0);
	
	public static final Color SORG_BASE = new Color(35, 32, 43);
	public static final Color SORG_BRICK_EDGE = new Color(15, 18, 13);
	public static final Color[] SORG_TINTS = {new Color(97, 58, 72), new Color(88, 65, 150)};
	
	public static final Color AYUE_PLANK = new Color(38, 34, 63);
	public static final Color AYUE_EDGE = new Color(18, 15, 30);
	
	public static final Color OAK_PLANK = new Color(89, 32, 0);
	public static final Color OAK_EDGE = new Color(63, 24, 0);
	
	public static final Color CRYT = new Color(0, 0, 150);
	public static final Color IRON = new Color(112, 112, 112);
	public static final Color BLESSED_IRON = new Color(158, 158, 158);
	
	public static final Color STRENGTH = new Color(200, 0, 0);
	public static final Color SPIRIT = new Color(50, 0, 200);
	public static final Color TURN_SPEED = new Color(200, 150, 0);
	
	public static final Color HEALTH_FULL = new Color(30, 200, 30);
	public static final Color HEALTH_EMPTY = new Color(200, 30, 30);
	public static final Color MANA_FULL = new Color(0, 250, 250);
	public static final Color MANA_EMPTY = new Color(250, 0, 250);
	public static final Color FOCUS_FULL = new Color(250, 100, 0);
	public static final Color FOCUS_EMPTY = new Color(40, 40, 40);
	public static final Color ENERGY_FULL = new Color(250, 200, 0);
	public static final Color ENERGY_EMPTY = new Color(75, 50, 0);
	public static final Color RAGE_FULL = new Color(150, 0, 0);
	public static final Color RAGE_EMPTY = new Color(30, 0, 0);
	
	public static final ColorAlpha TRANSPARENT = new ColorAlpha(0, 0, 0, 0);
	
	public static Color fromBits(BitInput bits){
		if(bits.readBoolean())
			return ColorSpecial.loadSpecialColor(bits);
		if(bits.readBoolean())
			return new ColorAlpha(bits.readByte(), bits.readByte(), bits.readByte(), bits.readByte());
		return new Color(bits.readByte(), bits.readByte(), bits.readByte());
	}
	
	public static Color fromSimpleBits(BitInput bits){
		if(bits.readBoolean())
			return new ColorAlpha(bits.readByte(), bits.readByte(), bits.readByte(), bits.readByte());
		return new Color(bits.readByte(), bits.readByte(), bits.readByte());
	}

	private final byte red;
	private final byte green;
	private final byte blue;
	
	public Color(int red, int green, int blue){
		this.red = (byte) red;
		this.green = (byte) green;
		this.blue = (byte) blue;
	}
	
	public Color(byte red, byte green, byte blue){
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	public java.awt.Color toAWTColor(){
		return new java.awt.Color(getRedI(), getGreenI(), getBlueI(), getAlphaI());
	}
	
	public Vector3f toVector(){
		return new Vector3f(getRedF(), getGreenF(), getBlueF());
	}
	
	@Override
	public String toString(){
		return "GuiColor:[" + red + "," + green + "," + blue + "]";
	}
	
	@Override
	public boolean equals(Object other){
		if(other.getClass() == Color.class){
			Color c = (Color) other;
			return c.red == red && c.green == green && c.blue == blue;
		}
		if(other.getClass() == ColorAlpha.class){
			Color c = (Color) other;
			return c.red == red && c.green == green && c.blue == blue && c.getAlpha() == -1;
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		return 16777216 * getRedI() + 65536 * getGreenI() + 256 * getBlueI() + getAlphaI();
	}
	
	@Override
	public int compareTo(Color other){
		if(other instanceof ColorSpecial){
			if(this instanceof ColorSpecial){
				char own = ((ColorSpecial) this).id;
				char oth = ((ColorSpecial) other).id;
				if(own > oth)
					return 1;
				if(own < oth)
					return -1;
				return 0;
			}
			return -1;
		}
		if(this instanceof ColorSpecial)
			return 1;
		if(red > other.red)
			return 1;
		if(red < other.red)
			return -1;
		if(green > other.green)
			return 1;
		if(green < other.green)
			return -1;
		if(blue > other.blue)
			return 1;
		if(blue < other.blue)
			return -1;
		if(getAlpha() > other.getAlpha())
			return 1;
		if(getAlpha() < other.getAlpha())
			return -1;
		return 0;
	}
	
	public byte getRed(){
		return red;
	}
	
	public byte getGreen(){
		return green;
	}
	
	public byte getBlue(){
		return blue;
	}
	
	public byte getAlpha(){
		return -1;
	}
	
	public int getRedI(){
		return getRed() & (0xff);
	}
	
	public int getGreenI(){
		return getGreen() & (0xff);
	}
	
	public int getBlueI(){
		return getBlue() & (0xff);
	}
	
	public int getAlphaI(){
		return 255;
	}
	
	public float getRedF(){
		return getRedI() / 255f;
	}
	
	public float getGreenF(){
		return getGreenI() / 255f;
	}
	
	public float getBlueF(){
		return getBlueI() / 255f;
	}
	
	public float getAlphaF(){
		return 1f;
	}
	
	public void toSimpleBits(BitOutput bits){
		if(getAlpha() != -1){
			bits.addBoolean(true);
			bits.addByte(red);
			bits.addByte(green);
			bits.addByte(blue);
			bits.addByte(getAlpha());
		}
		else {
			bits.addBoolean(false);
			bits.addByte(red);
			bits.addByte(green);
			bits.addByte(blue);
		}
	}
	
	public void toBits(BitOutput bits){
		bits.addBoolean(false);
		toSimpleBits(bits);
	}
	
	public boolean isSpecial(){
		return false;
	}
}