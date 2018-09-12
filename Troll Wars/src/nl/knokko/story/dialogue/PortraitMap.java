package nl.knokko.story.dialogue;

import java.util.Arrays;

import nl.knokko.texture.ImageTexture;
import nl.knokko.util.Maths;
import nl.knokko.util.bits.BitInput;
import nl.knokko.util.bits.BitOutput;

public class PortraitMap {
	
	private ImageTexture[] portraits;
	private String[] names;
	
	private byte bitCount;

	public PortraitMap() {
		portraits = new ImageTexture[0];
		names = new String[0];
	}
	
	public PortraitMap(BitInput data){
		portraits = new ImageTexture[data.readInt()];
		names = new String[portraits.length];
		for(int i = 0; i < portraits.length; i++){
			names[i] = data.readJavaString();
			portraits[i] = fromString(names[i]);
		}
		if(portraits.length == 0)
			bitCount = 0;
		else
			bitCount = Maths.log2Up(portraits.length);
	}
	
	public void save(BitOutput data){
		data.addInt(names.length);
		for(String name : names)
			data.addJavaString(name);
	}
	
	public ImageTexture loadPortrait(BitInput data){
		return portraits[(int) data.readNumber(bitCount, false)];
	}
	
	public void savePortrait(BitOutput data, ImageTexture portrait){
		int index = -1;
		for(int i = 0; i < portraits.length; i++)
			if(portraits[i] == portrait)
				index = i;
		if(index == -1)
			throw new IllegalArgumentException("The portrait (" + portrait + ") is not in this map: " + Arrays.deepToString(names));
		data.addNumber(index, bitCount, false);
	}
	
	public void add(String portrait){
		ImageTexture[] newPortraits = new ImageTexture[portraits.length + 1];
		String[] newNames = new String[names.length + 1];
		System.arraycopy(portraits, 0, newPortraits, 0, portraits.length);
		System.arraycopy(names, 0, newNames, 0, names.length);
		newPortraits[portraits.length] = fromString(portrait);
		newNames[names.length] = portrait;
		portraits = newPortraits;
		names = newNames;
		bitCount = Maths.log2Up(portraits.length);
	}
	
	public boolean has(String portrait){
		for(String name : names)
			if(name.equals(portrait))
				return true;
		return false;
	}
	
	private static ImageTexture fromString(String string){
		try {
			return (ImageTexture) Portraits.class.getDeclaredField(string).get(null);
		} catch (Exception e) {
			throw new IllegalArgumentException("There is no portrait with name: " + string);
		} 
	}
}
