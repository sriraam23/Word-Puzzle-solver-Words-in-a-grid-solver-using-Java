//public class MyHashTable<AnyType> implements the Quadratic Probing Hash Table.
public class MyHashTable<AnyType> {

	// Constructor - setting current size to 0 and initializing array elements
	// to null
	@SuppressWarnings("unchecked")
	public MyHashTable(int size) {
		this.arraysize = size;
		array = new HashArray[arraysize];
		currentsize = 0;
		for (int i = 0; i < array.length; i++)
			array[i] = null;
	}

	// insert function - finds the hash location and inserts the value at that
	// location.
	public void insert(AnyType x) {
		int position = findposition(x);
		if (array[position] == null) {
			currentsize++;
			array[position] = new HashArray<AnyType>(x);
		}
	}

	// contains function - to check if a certain value is present in the hash
	// table or not.
	// If the value could not be found, then the index that gets returned will
	// contain null.
	public boolean contains(AnyType x) {
		int position = findposition(x);
		if (array[position] != null)
			return true;
		else
			return false;
	}

	// size function - to return current size of the hash table
	public long size() {
		return currentsize;
	}

	// findposition function - finds the desired position on the array by
	// calling the hash function.
	// If the location is already occupied, it finds another location by using
	// quadratic probing.
	private int findposition(AnyType x) {
		int offset = 1;

		// Finding the hash position.
		int position = x.hashCode();
		position %= array.length;
		if (position < 0)
			position += array.length;

		// We keep incrementing the offset by 2.
		// This is because 4 = 1+(1+2), 9 = 1+3+(3+2), 16 = 1+3+5+(5+2).
		while (array[position] != null && !array[position].value.equals(x)) {
			position += offset;
			offset += 2;
			if (position >= array.length)
				position -= array.length;
		}
		return position;
	}

	// private class HashArray contains AnyType value and the constructor
	// initializes the parameter v to value.
	private static class HashArray<AnyType> {
		public AnyType value;

		public HashArray(AnyType v) {
			value = v;
		}
	}

	// private integers currentsize and arraysize and private array of type
	// HashArray.
	private int currentsize;
	private int arraysize;
	private HashArray<AnyType> array[];
}
