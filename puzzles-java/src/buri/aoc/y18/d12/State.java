package buri.aoc.y18.d12;

/**
 * A pattern state that shows the input pattern and its result.
 *
 * @author Brian Uri!
 */
public class State {
	private final String _pattern;
	private final boolean _result;

	/**
	 * Constructor
	 */
	public State(String input) {
		String[] tokens = input.split(" => ");
		_pattern = tokens[0];
		_result = (tokens[1].equals("#"));
	}

	/**
	 * Accessor for the pattern
	 */
	public String getPattern() {
		return _pattern;
	}

	/**
	 * Accessor for the result
	 */
	public boolean getResult() {
		return _result;
	}
}