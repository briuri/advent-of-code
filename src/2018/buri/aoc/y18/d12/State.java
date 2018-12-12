package buri.aoc.y18.d12;

/**
 * @author Brian Uri!
 */
public class State {
	private String _pattern;
	private boolean _result;
	
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