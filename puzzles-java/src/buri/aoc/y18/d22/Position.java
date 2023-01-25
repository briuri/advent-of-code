package buri.aoc.y18.d22;

import buri.aoc.common.data.tuple.Pair;

/**
 * A position in the maze, made unique by an equipped item.
 *
 * @author Brian Uri!
 */
public class Position extends Pair<Integer> {
	private char _item;

	/**
	 * Constructor
	 */
	public Position(int x, int y, char item) {
		super(x, y);
		_item = item;
	}

	@Override
	public int compareTo(Pair o) {
		int compare = super.compareTo(o);
		if (compare == 0 && o instanceof Position) {
			compare = getItem() - ((Position) o).getItem();
		}
		return compare;
	}

	@Override
	public boolean equals(Object obj) {
		return (super.equals(obj) && getItem() == ((Position) obj).getItem());
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result += 7 * getItem();
		return (result);
	}

	@Override
	public String toString() {
		return (super.toString() + "," + getItem());
	}

	/**
	 * Accessor for the item
	 */
	public char getItem() {
		return _item;
	}
}