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
