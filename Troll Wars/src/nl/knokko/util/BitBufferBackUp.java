/* 
 * The MIT License
 *
 * Copyright 2018 knokko.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package nl.knokko.util;

import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Arrays;

import nl.knokko.util.Maths;
import nl.knokko.util.color.Color;
import nl.knokko.util.color.ColorAlpha;

public class BitBufferBackUp {
	
	private static final byte[] BYTES = new byte[]{64,32,16,8,4,2,1};
	
	private static long get2Power(byte index){
		long l = 1;
		for(byte b = 0; b < index; b++){
			l *= 2;
			if(l < 0)
				l = Long.MAX_VALUE;
		}
		return l;
	}
	
	private static byte getRequiredBits(long number){
		if(number < 0)
			number = -(number + 1);
		long l = 1;
		byte b = 0;
		while(l < number){
			l *= 2;
			b++;
		}
		return b;
	}
	
	private static void checkBitCount(byte bits){
		if(bits < 0)
			throw new IllegalArgumentException("Number of bits ( + " + bits + ") can't be negative!");
		if(bits >= 64)
			throw new IllegalArgumentException("Number of bits ( + " + bits + ") can't be greater than 63!");
	}
	
	private static void checkOverflow(long number, byte bits){
		if(get2Power(bits) <= number || get2Power(bits) < -number)
			throw new IllegalArgumentException("You need more than " + bits + " bits to store the number " + number + "!");
	}
	
	private static boolean[] numberToBinair(long number, byte bits, boolean allowNegative){
		checkBitCount(bits);
		checkOverflow(number, bits);
		byte neg = (byte) (allowNegative ? 1 : 0);
		boolean[] bools = new boolean[bits + neg];
		if(allowNegative){
			if(number >= 0)
				bools[0] = true;
			else {
				//bools[0] will stay false
				number++;
				number = -number;
			}
		}
		for(byte b = 0; b < bits; b++){
			if(number >= get2Power((byte) (bits - b - 1))){
				number -= get2Power((byte) (bits - b - 1));
				bools[b + neg] = true;
			}
		}
		return bools;
	}
	
	private static boolean[] byteToBinary(byte b){
		boolean[] bools = new boolean[8];
		if(b >= 0)
			bools[7] = true;
		else {
			b++;
			b *= -1;
		}
		byte t = 0;
		while(t < 7){
			if(b >= BYTES[t]){
				b -= BYTES[t];
				bools[t] = true;
			}
			++t;
		}
		return bools;
	}
	
	private static long numberFromBinary(boolean[] bools, byte bits, boolean allowNegative){
		checkBitCount(bits);
		long number = 0;
		byte neg = (byte) (allowNegative ? 1 : 0);
		for(byte b = 0; b < bits; b++){
			if(bools[b + neg])
				number += get2Power((byte) (bits - b - 1));
		}
		if(allowNegative){
			if(!bools[0]){
				number = -number;
				number--;
			}
		}
		return number;
	}
	
	private static byte byteFromBinary(boolean[] bools){
		byte b = 0;
		int t = 0;
		while(t < 7){
			if(bools[t])
				b += BYTES[t];
			++t;
		}
		if(!bools[7]){
			b *= -1;
			b--;
		}
		return b;
	}
	
	//copied from java.nio.Bits
	static char makeChar(byte b0, byte b1) {
        return (char)((b1 << 8) | (b0 & 0xff));
    }
	
	private static short makeShort(byte b0, byte b1) {
        return (short)((b1 << 8) | (b0 & 0xff));
    }
	
	static private int makeInt(byte b0, byte b1, byte b2, byte b3) {
        return (((b3       ) << 24) |
                ((b2 & 0xff) << 16) |
                ((b1 & 0xff) <<  8) |
                ((b0 & 0xff)      ));
    }
	
	private static long makeLong(byte b0, byte b1, byte b2, byte b3,byte b4, byte b5, byte b6, byte b7){
		return ((((long)b7       ) << 56) |
				(((long)b6 & 0xff) << 48) |
				(((long)b5 & 0xff) << 40) |
				(((long)b4 & 0xff) << 32) |
				(((long)b3 & 0xff) << 24) |
				(((long)b2 & 0xff) << 16) |
			(((long)b1 & 0xff) <<  8) |
			(((long)b0 & 0xff)      ));
	}
	
	public static byte char1(char x) { return (byte)(x >> 8); }
    public static byte char0(char x) { return (byte)(x     ); }
    
    private static byte short1(short x) { return (byte)(x >> 8); }
    private static byte short0(short x) { return (byte)(x     ); }
    
    private static byte int3(int x) { return (byte)(x >> 24); }
    private static byte int2(int x) { return (byte)(x >> 16); }
    private static byte int1(int x) { return (byte)(x >>  8); }
    private static byte int0(int x) { return (byte)(x      ); }
    
    private static byte long7(long x) { return (byte)(x >> 56); }
    private static byte long6(long x) { return (byte)(x >> 48); }
    private static byte long5(long x) { return (byte)(x >> 40); }
    private static byte long4(long x) { return (byte)(x >> 32); }
    private static byte long3(long x) { return (byte)(x >> 24); }
    private static byte long2(long x) { return (byte)(x >> 16); }
    private static byte long1(long x) { return (byte)(x >>  8); }
    private static byte long0(long x) { return (byte)(x      ); }
    
    private static float fromInt(int i){
    	return Float.intBitsToFloat(i);
    }
    
    private static int fromFloat(float f){
    	return Float.floatToRawIntBits(f);
    }
    
    private static double fromLong(long l){
    	return Double.longBitsToDouble(l);
    }
    
    private static long fromDouble(double d){
    	return Double.doubleToRawLongBits(d);
    }
	
	boolean[] bits;
	
	private int writeIndex;
	int readIndex;

	public BitBufferBackUp(int expectedBits) {
		bits = new boolean[expectedBits];
	}
	
	public BitBufferBackUp(){
		this(800);
	}
	
	public BitBufferBackUp(boolean[] bits){
		this.bits = bits;
		writeIndex = bits.length;
	}
	
	public BitBufferBackUp(InputStream input) throws IOException{
		bits = new boolean[input.available() * 8];
		int b = input.read();
		while(b >= 0){
			addByte((byte)b);
			b = input.read();
		}
	}
	
	public BitBufferBackUp(byte[] bytes){
		bits = new boolean[bytes.length * 8];
		for(int i = 0; i < bytes.length; i++)
			addByte(bytes[i]);
	}
	
	public BitBufferBackUp(File file) throws IOException {
		byte[] bytes = Files.readAllBytes(file.toPath());
		bits = new boolean[bytes.length * 8];
		for(byte b : bytes)
			addByte(b);
	}
	
	public byte[] toBytes(){
		byte[] value;
		int test = writeIndex - ((writeIndex / 8) * 8);
		if(test == 0)
			value = new byte[writeIndex / 8];
		else
			value = new byte[writeIndex / 8 + 1];
		for(int index = 0; index < value.length; index++)
			value[index] = readByte(index * 8);
		return value;
	}
	
	public void save(OutputStream stream) throws IOException {
		for(int index = 0; index < writeIndex; index += 8)
			stream.write(readByte(index));
	}
	
	public void save(File file) throws FileNotFoundException, IOException {
		FileOutputStream output = new FileOutputStream(file);
		save(output);
		output.close();
	}
	
	public void addBoolean(boolean bool){
		ensureCapacity(writeIndex + 1);
		bits[writeIndex] = bool;
		writeIndex++;
	}
	
	public void addByte(byte b){
		ensureCapacity(writeIndex + 8);
		boolean[] bools = byteToBinary(b);
		for(boolean bool : bools)
			addBoolean(bool);
	}
	
	public void addChar(char c){
		ensureCapacity(writeIndex + 16);
		addByte(char0(c));
		addByte(char1(c));
	}
	
	public void addShort(short s){
		ensureCapacity(writeIndex + 16);
		addByte(short0(s));
		addByte(short1(s));
	}
	
	public void addInt(int i){
		ensureCapacity(writeIndex + 32);
		addByte(int0(i));
		addByte(int1(i));
		addByte(int2(i));
		addByte(int3(i));
	}
	
	public void addFloat(float f){
		addInt(fromFloat(f));
	}
	
	public void addLong(long l){
		ensureCapacity(writeIndex + 64);
		addByte(long0(l));
		addByte(long1(l));
		addByte(long2(l));
		addByte(long3(l));
		addByte(long4(l));
		addByte(long5(l));
		addByte(long6(l));
		addByte(long7(l));
	}
	
	public void addDouble(double d){
		addLong(fromDouble(d));
	}
	
	public void addNumber(long number, byte bitCount, boolean allowNegative){
		boolean[] bools = numberToBinair(number, bitCount, allowNegative);
		ensureCapacity(writeIndex + bools.length);
		System.arraycopy(bools, 0, bits, writeIndex, bools.length);
		writeIndex += bools.length;
	}
	
	public void addNumber(long number, boolean allowNegative){
		if(!allowNegative && number < 0)
			throw new IllegalArgumentException("Number (" + number + " can't be negative!");
		byte bitCount = getRequiredBits(number);
		if(allowNegative)
			bitCount++;
		addNumber(bitCount, (byte) 6, false);
		addNumber(number, bitCount, allowNegative);
	}
	
	public void addString(String string){
		if(string == null){
			addInt(-1);
			return;
		}
		char max = 1;
		for(int i = 0; i < string.length(); i++){
			char c = string.charAt(i);
			if(c > max)
				max = c;
		}
		byte bitCount = Maths.log2Up(max);
		//maximum is 2^16 - 1 --> maximum bitCount is 16
		ensureCapacity(writeIndex + 32 + 4 + bitCount * string.length());
		addInt(string.length());
		addNumber(bitCount - 1, (byte) 4, false);
		for(int i = 0; i < string.length(); i++)
			addNumber(string.charAt(i), bitCount, false);
	}
	
	public void addFont(Font font){
		addString(font.getName());
		addInt(font.getStyle());
		addInt(font.getSize());
	}
	
	public void addSimpleColor(Color color){
		if(color.getAlpha() == -1){
			ensureCapacity(writeIndex + 25);
			addBoolean(false);
			addByte(color.getRed());
			addByte(color.getGreen());
			addByte(color.getBlue());
		}
		else {
			ensureCapacity(writeIndex + 33);
			addBoolean(true);
			addByte(color.getRed());
			addByte(color.getGreen());
			addByte(color.getBlue());
			addByte(color.getAlpha());
		}
	}
	
	public void addBits(BitBufferBackUp buffer){
		int length = buffer.writeIndex - buffer.readIndex;
		ensureCapacity(writeIndex + length + 32);
		addInt(length);
		System.arraycopy(buffer.bits, buffer.readIndex, bits, writeIndex, length);
		writeIndex += length;
	}
	
	public boolean readBoolean(){
		boolean value = bits[readIndex];
		readIndex++;
		return value;
	}
	
	public boolean[] read8Booleans(){
		boolean[] value = new boolean[8];
		for(int i = 0; i < 8; i++)
			value[i] = bits[readIndex + i];
		readIndex += 8;
		return value;
	}
	
	private byte readByte(int index){
		boolean[] value = new boolean[8];
		for(int i = 0; i < 8 && index + i < bits.length; i++)
			value[i] = bits[index + i];
		return byteFromBinary(value);
	}
	
	public byte readByte(){
		return byteFromBinary(read8Booleans());
	}
	
	public char readChar(){
		return makeChar(readByte(), readByte());
	}
	
	public short readShort(){
		return makeShort(readByte(), readByte());
	}
	
	public int readInt(){
		return makeInt(readByte(), readByte(), readByte(), readByte());
	}
	
	public float readFloat(){
		return fromInt(readInt());
	}
	
	public long readLong(){
		return makeLong(readByte(), readByte(), readByte(), readByte(), readByte(), readByte(), readByte(), readByte());
	}
	
	public double readDouble(){
		return fromLong(readLong());
	}
	
	public long readNumber(byte bitCount, boolean allowNegative){
		byte size = bitCount;
		if(allowNegative)
			size++;
		long number = numberFromBinary(Arrays.copyOfRange(bits, readIndex, readIndex + size), bitCount, allowNegative);
		readIndex += size;
		return number;
	}
	
	public long readNumber(boolean allowNegative){
		byte bitCount = (byte) readNumber((byte) 6, false);
		return readNumber(bitCount, allowNegative);
	}
	
	public String readString(){
		int amount = readInt();
		if(amount == -1)
			return null;
		byte bitCount = (byte) (readNumber((byte) 4, false) + 1);
		char[] chars = new char[amount];
		for(int i = 0; i < chars.length; i++)
			chars[i] = (char) readNumber(bitCount, false);
		return new String(chars);
	}
	
	public Color readSimpleColor(){
		boolean alpha = readBoolean();
		if(!alpha)
			return new Color(readByte(), readByte(), readByte());
		return new ColorAlpha(readByte(), readByte(), readByte(), readByte());
	}
	
	public Font readFont(){
		return new Font(readString(), readInt(), readInt());
	}
	
	public BitBufferBackUp readBits(){
		int length = readInt();
		boolean[] subBits = new boolean[length];
		System.arraycopy(bits, readIndex, subBits, 0, length);
		readIndex += length;
		return new BitBufferBackUp(subBits);
	}
	
	private void ensureCapacity(int newCapacity){
		if(bits.length < newCapacity)
			bits = Arrays.copyOf(bits, newCapacity);
	}
	
	public static class NetworkBitBuffer extends BitBufferBackUp {
		
		private InputStream input;
		
		public NetworkBitBuffer(InputStream input){
			super(0);
			this.input = input;
		}
		
		@Override
		public void save(OutputStream output){
			throw new IllegalStateException("NetworkBitBuffer doesn't support writing or saving!");
		}
		
		@Override
		public void save(File file){
			throw new IllegalStateException("NetworkBitBuffer doesn't support writing or saving!");
		}
		
		@Override
		public void addBoolean(boolean b){
			throw new IllegalStateException("NetworkBitBuffer doesn't support writing or saving!");
		}
		
		@Override
		public void addByte(byte b){
			throw new IllegalStateException("NetworkBitBuffer doesn't support writing or saving!");
		}
		
		@Override
		public void addShort(short s){
			throw new IllegalStateException("NetworkBitBuffer doesn't support writing or saving!");
		}
		
		@Override
		public void addChar(char c){
			throw new IllegalStateException("NetworkBitBuffer doesn't support writing or saving!");
		}
		
		@Override
		public void addInt(int i){
			throw new IllegalStateException("NetworkBitBuffer doesn't support writing or saving!");
		}
		
		@Override
		public void addFloat(float f){
			throw new IllegalStateException("NetworkBitBuffer doesn't support writing or saving!");
		}
		
		@Override
		public void addLong(long l){
			throw new IllegalStateException("NetworkBitBuffer doesn't support writing or saving!");
		}
		
		@Override
		public void addDouble(double d){
			throw new IllegalStateException("NetworkBitBuffer doesn't support writing or saving!");
		}
		
		@Override
		public void addNumber(long n, byte b, boolean z){
			throw new IllegalStateException("NetworkBitBuffer doesn't support writing or saving!");
		}
		
		@Override
		public void addNumber(long number, boolean b){
			throw new IllegalStateException("NetworkBitBuffer doesn't support writing or saving!");
		}
		
		@Override
		public boolean readBoolean(){
			ensureIndex(readIndex + 1);
			return super.readBoolean();
		}
		
		@Override
		public boolean[] read8Booleans(){
			ensureIndex(readIndex + 8);
			return super.read8Booleans();
		}
		
		@Override
		public byte readByte(){
			return byteFromBinary(read8Booleans());
		}
		
		@Override
		public char readChar(){
			ensureIndex(readIndex + 16);
			return super.readChar();
		}
		
		@Override
		public short readShort(){
			ensureIndex(readIndex + 16);
			return super.readShort();
		}
		
		@Override
		public int readInt(){
			ensureIndex(readIndex + 32);
			return super.readInt();
		}
		
		@Override
		public long readLong(){
			ensureIndex(readIndex + 64);
			return super.readLong();
		}
		
		@Override
		public long readNumber(boolean allowNegative){
			byte bitCount = (byte) readNumber((byte) 6, false);
			return readNumber(bitCount, allowNegative);
		}
		
		@Override
		public long readNumber(byte bitCount, boolean allowNegative){
			ensureIndex(readIndex + bitCount + (allowNegative ? 1 : 0));
			return super.readNumber(bitCount, allowNegative);
		}
		
		/**
		 * Clears all bits that have already been read.
		 */
		public void clear(){
			bits = new boolean[0];
			readIndex = 0;
		}
		
		private void ensureIndex(int maxIndex){
			int lack = maxIndex - bits.length;
			if(lack > 0){
				int amount = lack / 8;
				if(amount * 8 != lack)
					amount++;
				byte[] data = new byte[amount];
				try {
					input.read(data);
				} catch (Exception ex) {
					throw new RuntimeException("Failed to load input data:", ex);
				}
				int length = bits.length;
				bits = Arrays.copyOf(bits, length + 8 * amount);
				for(int i = 0; i < data.length; i++){
					boolean[] bools = byteToBinary(data[i]);
					bits[length + 8 * i] = bools[0];
					bits[length + 8 * i + 1] = bools[1];
					bits[length + 8 * i + 2] = bools[2];
					bits[length + 8 * i + 3] = bools[3];
					bits[length + 8 * i + 4] = bools[4];
					bits[length + 8 * i + 5] = bools[5];
					bits[length + 8 * i + 6] = bools[6];
					bits[length + 8 * i + 7] = bools[7];
				}
			}
		}
	}
}
