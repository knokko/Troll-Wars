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

public class IndexedFloatList extends AbstractFloatList {
	
	private float[] data;
	
	private int minIndex;
	private int boundIndex;
	private int size;

	public IndexedFloatList(int capacity) {
		minIndex = 0;
		boundIndex = 0;
		size = 0;
		data = new float[capacity];
	}
	
	public IndexedFloatList(float[] boundData){
		data = boundData;
		size = data.length;
		minIndex = 0;
		boundIndex = size;
	}
	
	public IndexedFloatList(float[] boundData, int minIndex, int boundIndex){
		data = boundData;
		this.minIndex = minIndex;
		this.boundIndex = boundIndex;
		size = boundIndex - minIndex;
	}

	@Override
	public void add(float value) {
		ensureCapacity(size + 1);
		data[boundIndex] = value;
		boundIndex++;
		size++;
	}

	@Override
	public void addAll(float... values) {
		ensureCapacity(size + values.length);
		System.arraycopy(values, 0, data, boundIndex, values.length);
		boundIndex += values.length;
		size += values.length;
	}

	@Override
	public void remove(int index) {
		checkRelativeIndex(index);
		if(index == 0){
			minIndex++;
			size--;
			return;
		}
		int actualIndex = index + minIndex;
		if(actualIndex == boundIndex - 1){
			boundIndex--;
			size--;
			return;
		}
		System.arraycopy(data, actualIndex + 1, data, actualIndex, size - index - 1);
		size--;
		boundIndex--;
	}

	@Override
	public void removeAll(int indexFrom, int indexBound, float value) {
		checkRelativeBounds(indexFrom, indexBound);
		int[] indexes = indexes(indexFrom, indexBound, value);
		float[] newData = new float[size - indexes.length];
		int newIndex = 0;
		for(int i = minIndex; i < boundIndex; i++)
			if(!contains(indexes, i)){
				newData[newIndex] = data[i];
				newIndex++;
			}
		data = newData;
		size = data.length;
		minIndex = 0;
		boundIndex = size;
	}
	
	private boolean contains(int[] indexes, int index){
		for(int i = 0; i < indexes.length; i++)
			if(indexes[i] == index)
				return true;
		return false;
	}

	@Override
	public void removeFirst(int indexFrom, int indexBound, float value) {
		int bound = indexBound + boundIndex;
		for(int index = indexFrom + minIndex; index < bound; index++){
			if(data[index] == value){
				remove(index);
				return;
			}
		}
	}

	@Override
	public void removeLast(int indexFrom, int indexBound, float value) {
		int min = minIndex + indexFrom;
		for(int index = boundIndex + indexBound - 1; index >= min; index++){
			if(data[index] == value){
				remove(index);
				return;
			}
		}
	}

	@Override
	public void clear() {
		data = new float[0];
		minIndex = 0;
		size = 0;
		boundIndex = 0;
	}

	@Override
	public boolean contains(float value) {
		for(int index = minIndex; index < boundIndex; index++)
			if(data[index] == value)
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
		return data.length - minIndex;
	}

	@Override
	public void ensureCapacity(int capacity) {
		if(boundIndex < minIndex + capacity){
			float[] newData = new float[capacity];
			System.arraycopy(data, minIndex, newData, 0, size);
			data = newData;
			boundIndex -= minIndex;
			minIndex = 0;
		}
	}

	@Override
	public void increaseCapacity(int amount) {
		ensureCapacity(capacity() + amount);
	}

	@Override
	public void set(int index, float value) {
		checkRelativeIndex(index);
		data[minIndex + index] = value;
	}

	@Override
	public float get(int index) {
		checkRelativeIndex(index);
		return data[minIndex + index];
	}

	@Override
	public int firstIndex(int indexFrom, int indexBound, float value) {
		checkRelativeBounds(indexFrom, indexBound);
		int bound = indexBound + boundIndex;
		for(int index = indexFrom + minIndex; index < bound; index++)
			if(data[index] == value)
				return index;
		return -1;
	}

	@Override
	public int lastIndex(int indexFrom, int indexBound, float value) {
		checkRelativeBounds(indexFrom, indexBound);
		int min = indexFrom + minIndex;
		for(int index = indexBound + boundIndex - 1; index >= min; index++)
			if(data[index] == value)
				return index;
		return -1;
	}

	@Override
	public int[] indexes(int indexFrom, int indexBound, float value) {
		checkRelativeBounds(indexFrom, indexBound);
		//TODO create IntList for this
		return null;
	}

	@Override
	public void replaceFirst(int indexFrom, int indexBound, float original, float replacement) {
		checkRelativeBounds(indexFrom, indexBound);
		int bound = indexBound + boundIndex;
		for(int index = minIndex + indexFrom; index < bound; index++){
			if(data[index] == original){
				data[index] = replacement;
				return;
			}
		}
	}

	@Override
	public void replaceLast(int indexFrom, int indexBound, float original, float replacement) {
		checkRelativeBounds(indexFrom, indexBound);
		int min = indexFrom + minIndex;
		for(int index = indexBound + boundIndex - 1; index >= min; index++){
			if(data[index] == original){
				data[index] = replacement;
				return;
			}
		}
	}

	@Override
	public void replaceAll(int indexFrom, int indexBound, float original, float replacement) {
		checkRelativeBounds(indexFrom, indexBound);
		int bound = indexBound + boundIndex;
		for(int index = minIndex + indexFrom; index < bound; index++)
			if(data[index] == original)
				data[index] = replacement;
	}

	@Override
	public FloatList subList(int indexMin, int indexBound) {
		checkRelativeBounds(indexMin, indexBound);
		return new IndexedFloatList(data, minIndex + indexMin, minIndex + indexBound);
	}

	@Override
	public void fill(int startIndex, int length, float[] destination, int destinationIndex) {
		checkRelativeBounds(startIndex, startIndex + length);
		System.arraycopy(data, minIndex + startIndex, destination, destinationIndex, length);
	}

	@Override
	public void fill(int startIndex, int amount, float value) {
		checkRelativeBounds(startIndex, startIndex + amount);
		for(int i = 0; i < amount; i++)
			data[startIndex + minIndex + i] = value;
	}

	@Override
	public float[] createArray(int indexMin, int indexBound) {
		checkRelativeBounds(indexMin, indexBound);
		float[] array = new float[indexBound - indexMin];
		System.arraycopy(data, minIndex + indexMin, array, 0, array.length);
		return array;
	}

	@Override
	public float[] getArray() {
		if(minIndex == 0 && boundIndex == data.length)
			return data;
		return createArray(0, size);
	}
	
	private void checkRelativeIndex(int index){
		if(index < 0 || index >= size)
			throw new IndexOutOfBoundsException("index " + index + " is smaller than 0 or not smaller than " + size);
	}
	
	private void checkRelativeBounds(int index, int bound){
		checkRelativeIndex(index);
		checkRelativeIndex(bound - 1);
	}
}
