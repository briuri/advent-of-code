package buri.aoc.y20.d08;

/**
 * Mutable index representing the most recently modified instruction.
 *
 * @author Brian Uri!
 */
public class ModIndex {
	private int _index;

	/**
	 * Constructor
	 */
	public ModIndex() {
		setIndex(0);
	}

	/**
	 * Accessor for the index
	 */
	public int getIndex() {
		return _index;
	}

	/**
	 * Accessor for the index
	 */
	public void setIndex(int index) {
		_index = index;
	}
}
