package buri.aoc.y21.d21;

/**
 * Data model for a die that rolls to 100.
 *
 * @author Brian Uri!
 */
public class Die {
	private int _rollCount = 0;

	/**
	 * Constructor
	 */
	public Die() {}

	/**
	 * Returns the current roll.
	 */
	public int roll() {
		incrementRollCount();
		return ((getRollCount() -1) % 100 + 1);
	}

	public int getRollCount() {
		return (_rollCount);
	}

	public void incrementRollCount() {
		_rollCount += 1;
	}
}