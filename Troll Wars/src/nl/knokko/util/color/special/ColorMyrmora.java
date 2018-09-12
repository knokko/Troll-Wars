package nl.knokko.util.color.special;

import java.util.Random;

import nl.knokko.util.color.ColorSpecial;

public class ColorMyrmora extends ColorSpecial {
	
	private static Random random = new Random();

	public ColorMyrmora() {
		super((char) 0);
	}
	
	public byte getRed(){
		return (byte) (random.nextInt(50) - 120);
	}
	
	public byte getGreen(){
		return (byte) random.nextInt(30);
	}
	
	public byte getBlue(){
		return (byte) (random.nextInt(50) - 70);
	}
}