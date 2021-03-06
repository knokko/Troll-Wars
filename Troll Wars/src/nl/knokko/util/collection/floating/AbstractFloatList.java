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

public abstract class AbstractFloatList implements FloatList {

	@Override
	public void removeAll(float value) {
		removeAll(0, size(), value);
	}

	@Override
	public void removeFirst(float value) {
		removeFirst(0, size(), value);
	}

	@Override
	public void removeLast(float value) {
		removeLast(0, size(), value);
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public int firstIndex(float value) {
		return firstIndex(0, size(), value);
	}

	@Override
	public int lastIndex(float value) {
		return lastIndex(0, size(), value);
	}

	@Override
	public int[] indexes(float value) {
		return indexes(0, size(), value);
	}

	@Override
	public void replaceFirst(float original, float replacement) {
		replaceFirst(0, size(), original, replacement);
	}

	@Override
	public void replaceLast(float original, float replacement) {
		replaceLast(0, size(), original, replacement);
	}

	@Override
	public void replaceAll(float original, float replacement) {
		replaceAll(0, size(), original, replacement);
	}
}
