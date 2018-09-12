package nl.knokko.util.color;

import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;
import nl.knokko.util.color.special.ColorMyrmora;

public class ColorSpecial extends Color {
	
	public static final ColorMyrmora MYRMORA = new ColorMyrmora();
	
	private static final Color[] COLORS = {
		MYRMORA
	};
	
	public static Color loadSpecialColor(BitInput bits){
		char index = bits.readChar();
		if(index >= COLORS.length)
			throw new IllegalArgumentException("Invalid special color: " + index);
		return COLORS[index];
	}
	
	public static Color get(int index){
		if(index >= COLORS.length || index < 0)
			throw new IllegalArgumentException("Invalid index (" + index + "), it must be between 0 and " + (COLORS.length - 1));
		return COLORS[index];
	}
	
	public static int amount(){
		return COLORS.length;
	}
	
	protected final char id;
	
	public ColorSpecial(char id){
		super(145, 92, 84);
		this.id = id;
	}
	
	@Override
	public void toBits(BitOutput output){
		output.addBoolean(true);
		output.addChar(id);
	}
	
	@Override
	public boolean isSpecial(){
		return true;
	}
	
	@Override
	public String toString(){
		return getClass().getSimpleName();
	}
	
	@Override
	public boolean equals(Object other){
		return other instanceof ColorSpecial && ((ColorSpecial)other).id == id;
	}
	
	@Override
	public int hashCode(){
		return id;
	}
}