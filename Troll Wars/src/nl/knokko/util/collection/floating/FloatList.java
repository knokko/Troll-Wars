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
package nl.knokko.util.collection.floating;

public interface FloatList {
	
	void add(float value);
	
	void addAll(float... values);
	
	void remove(int index);
	
	void removeAll(float value);
	
	void removeFirst(float value);
	
	void removeLast(float value);
	
	void removeAll(int indexFrom, int indexBound, float value);
	
	void removeFirst(int indexFrom, int indexBound, float value);
	
	void removeLast(int indexFrom, int indexBound, float value);
	
	void clear();
	
	boolean contains(float value);
	
	boolean containsAll(float... values);
	
	int size();
	
	int capacity();
	
	void ensureCapacity(int capacity);
	
	void increaseCapacity(int amount);
	
	boolean isEmpty();
	
	void set(int index, float value);
	
	float get(int index);
	
	int firstIndex(float value);
	
	int firstIndex(int indexFrom, int indexBound, float value);
	
	int lastIndex(float value);
	
	int lastIndex(int indexFrom, int indexBound, float value);
	
	int[] indexes(float value);
	
	int[] indexes(int indexFrom, int indexBound, float value);
	
	void replaceFirst(float original, float replacement);
	
	void replaceFirst(int indexFrom, int indexBound, float original, float replacement);
	
	void replaceLast(float original, float replacement);
	
	void replaceLast(int indexFrom, int indexBound, float original, float replacement);
	
	void replaceAll(float original, float replacement);
	
	void replaceAll(int indexFrom, int indexBound, float original, float replacement);
	
	FloatList subList(int indexMin, int indexBound);
	
	void fill(int startIndex, int length, float[] destination, int destinationIndex);
	
	void fill(int startIndex, int amount, float value);
	
	float[] createArray(int indexMin, int indexBound);
	
	float[] getArray();
}