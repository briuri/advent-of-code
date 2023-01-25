package buri.aoc.y17.d10;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Data class for a list that loops around.
 *
 * @author Brian Uri!
 */
public class CircleList {

	private List<Integer> _list;

	/**
	 * Constructor
	 *
	 * Initializes the value of each slot in the list to be its original index.
	 */
	public CircleList(int size) {
		_list = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			_list.add(i);
		}
	}

	/**
	 * Reverse the order of that length of elements in the list, starting with the element at the current position.
	 */
	public void reverse(int length, int currentPosition) {
		currentPosition = currentPosition % getSize();
		int interimEnd = Math.min(getSize(), currentPosition + length);

		// Create a sublist that contains all elements and reverse it.
		List<Integer> subList = new ArrayList<>(getList().subList(currentPosition, interimEnd));
		int remainder = length - (subList.size());
		subList.addAll(new ArrayList<>(getList().subList(0, remainder)));
		Collections.reverse(subList);

		// Overwrite real list with reversed elements.
		int overwritePosition = currentPosition;
		for (Integer value : subList) {
			getList().set(overwritePosition, value);
			overwritePosition++;
			if (overwritePosition >= getList().size()) {
				overwritePosition = 0;
			}
		}
	}

	/**
	 * Converts 16 numbers to its XOR.
	 */
	private static int toDenseHash(List<Integer> subList) {
		int xor = subList.get(0);
		for (int i = 1; i < subList.size(); i++) {
			xor = xor ^ subList.get(i);
		}
		return (xor);
	}

	/**
	 * Converts a sparse hash to a dense hash, then to hex.
	 */
	public String toHex() {
		List<Integer> denseHash = new ArrayList<>();
		for (int i = 0; i < getSize() / 16; i++) {
			List<Integer> subList = getList().subList(i * 16, i * 16 + 16);
			denseHash.add(toDenseHash(subList));
		}
		String hex = "";
		for (int i = 0; i < denseHash.size(); i++) {
			String subHex = Integer.toHexString(denseHash.get(i));
			if (subHex.length() == 1) {
				subHex = "0" + subHex;
			}
			hex += subHex;
		}
		return (hex);
	}

	/**
	 * Multiplies the first two values together.
	 */
	public int getResult() {
		return (getList().get(0) * getList().get(1));
	}

	/**
	 * Output for debugging
	 */
	public String toString(int currentPosition) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < getList().size(); i++) {
			if (i == currentPosition) {
				buffer.append(String.format("[%s]", getList().get(i)));
			}
			else {
				buffer.append(getList().get(i));
			}
			buffer.append(" ");
		}
		return (buffer.toString());
	}

	@Override
	public String toString() {
		return (toString(0));
	}

	/**
	 * Returns the size of the list
	 */
	private int getSize() {
		return (getList().size());
	}

	/**
	 * Accessor for the internal list
	 */
	private List<Integer> getList() {
		return _list;
	}
}