package buri.aoc.y22.d17;

/**
 * Data class for the jet cycle pattern.
 *
 * @author Brian Uri!
 */
public class JetPattern {

	private final String _pattern;
	private int _index;

	/**
	 * Constructor
	 */
	public JetPattern(String pattern) {
		_pattern = pattern;
		_index = 0;
	}

	/**
	 * Returns the next character in the pattern. Starts over if needed.
	 */
	public char next() {
		char next = getPattern().charAt(getIndex());
		setIndex(getIndex() + 1);
		if (getIndex() >= getPattern().length()) {
			setIndex(0);
		}
		return next;
	}

	/**
	 * Accessor for the pattern
	 */
	private String getPattern() {
		return _pattern;
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
	private void setIndex(int index) {
		_index = index;
	}
}