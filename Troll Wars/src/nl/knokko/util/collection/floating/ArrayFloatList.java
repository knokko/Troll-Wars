/*******************************************************************************
 * The MIT License
 *
 * Copyright (c) 2019 knokko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
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
 *******************************************************************************/
package nl.knokko.util.collection.floating;

public class ArrayFloatList extends AbstractFloatList {
	
	private float[] data;
	
	private int size;

	public ArrayFloatList(int capacity) {
		data = new float[capacity];
		size = 0;
	}
	
	public ArrayFloatList(float... values){
		data = values;
		size = data.length;
	}

	@Override
	public void add(float value) {
		ensureCapacity(size + 1);
		data[size] = value;
		size++;
	}

	@Override
	public void addAll(float... values) {
		ensureCapacity(size + values.length);
		System.arraycopy(values, 0, data, size, values.length);
		size += values.length;
	}

	@Override
	public void remove(int index) {
		checkBound(index);
		if(index == size - 1){
			size--;
			return;
		}
		if(index == 0){
			System.arraycopy(data, 1, data, 0, size - 1);
			size--;
			return;
		}
		System.arraycopy(data, index + 1, data, index, size - index - 1);
		size--;
	}

	@Override
	public void removeAll(int indexFrom, int indexBound, float value) {
		checkBounds(indexFrom, indexBound);
		int[] indexes = indexes(indexFrom, indexBound, value);
		float[] newData = new float[size - indexes.length];
		int newIndex = 0;
		for(int i = 0; i < size; i++)
			if(!contains(indexes, i)){
				newData[newIndex] = data[i];
				newIndex++;
			}
		data = newData;
		size = data.length;
	}
	
	private boolean contains(int[] indexes, int index){
		for(int i = 0; i < indexes.length; i++)
			if(indexes[i] == index)
				return true;
		return false;
	}

	@Override
	public void removeFirst(int indexFrom, int indexBound, float value) {
		checkBounds(indexFrom, indexBound);
		for(int i = indexFrom; i < indexBound; i++)
			if(data[i] == value)
				remove(i);
	}

	@Override
	public void removeLast(int indexFrom, int indexBound, float value) {
		checkBounds(indexFrom, indexBound);
		for(int i = indexBound - 1; i >= indexFrom; i--)
			if(data[i] == value)
				remove(i);
	}

	@Override
	public void clear() {
		data = new float[0];
	}

	@Override
	public boolean contains(float value) {
		for(int i = 0; i < size; i++)
			if(data[i] == value)
				return true;
		return false;
	}

	@Override
	public boolean containsAll(float... values) {
		for(float value : values)
			if(!contains(value))
				return false;
		return true;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public int capacity() {
		return data.length;
	}

	@Override
	public void ensureCapacity(int capacity) {
		if(data.length < capacity){
			float[] newData = new float[capacity];
			System.arraycopy(data, 0, newData, 0, size);
			data = newData;
		}
	}

	@Override
	public void increaseCapacity(int amount) {
		ensureCapacity(data.length + amount);
	}

	@Override
	public void set(int index, float value) {
		checkBound(index);
		data[index] = value;
	}

	@Override
	public float get(int index) {
		checkBound(index);
		return data[index];
	}

	@Override
	public int firstIndex(int indexFrom, int indexBound, float value) {
		checkBounds(indexFrom, indexBound);
		for(int index = indexFrom; index < indexBound; index++)
			if(data[index] == value)
				return index;
		return -1;
	}

	@Override
	public int lastIndex(int indexFrom, int indexBound, float value) {
		checkBounds(indexFrom, indexBound);
		for(int index = indexBound - 1; index >= indexFrom; index--)
			if(data[index] == value)
				return index;
		return -1;
	}

	@Override
	public int[] indexes(int indexFrom, int indexBound, float value) {
		// TODO Use an IntList for this one
		return null;
	}

	@Override
	public void replaceFirst(int indexFrom, int indexBound, float original, float replacement) {
		checkBounds(indexFrom, indexBound);
		for(int i = indexFrom; i < indexBound; i++)
			if(data[i] == original){
				data[i] = replacement;
				return;
			}
	}

	@Override
	public void replaceLast(int indexFrom, int indexBound, float original, float replacement) {
		checkBounds(indexFrom, indexBound);
		for(int i = indexBound - 1; i >= indexFrom; i++)
			if(data[i] == original){
				data[i] = replacement;
				return;
			}
	}

	@Override
	public void replaceAll(int indexFrom, int indexBound, float original, float replacement) {
		checkBounds(indexFrom, indexBound);
		for(int i = indexFrom; i < indexBound; i++)
			if(data[i] == original)
				data[i] = replacement;
	}

	@Override
	public FloatList subList(int indexMin, int indexBound) {
		return new IndexedFloatList(data, indexMin, indexBound);
	}

	@Override
	public void fill(int startIndex, int length, float[] destination, int destinationIndex) {
		checkBounds(startIndex, startIndex + length);
		System.arraycopy(data, startIndex, destination, destinationIndex, length);
	}

	@Override
	public void fill(int startIndex, int amount, float value) {
		int bound = startIndex + amount;
		checkBounds(startIndex, bound);
		for(int i = startIndex; i < bound; i++)
			data[i] = value;
	}

	@Override
	public float[] createArray(int indexMin, int indexBound) {
		float[] array = new float[indexBound - indexMin];
		System.arraycopy(data, indexMin, array, 0, indexBound - indexMin);
		return array;
	}

	@Override
	public float[] getArray() {
		if(size == data.length)
			return data;
		float[] array = new float[size];
		System.arraycopy(data, 0, array, 0, size);
		return array;
	}
	
	private void checkBounds(int index, int bound){
		checkBound(index);
		checkBound(bound - 1);
	}
	
	private void checkBound(int index){
		if(index >= size)
			throw new IllegalArgumentException("index is " + index + " and size is " + size);
		if(index < 0)
			throw new IllegalArgumentException("index is " + index);
	}
}
