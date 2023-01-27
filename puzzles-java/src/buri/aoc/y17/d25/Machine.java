package buri.aoc.y17.d25;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Representation of the Turing machine.
 *
 * @author Brian Uri!
 */
public class Machine {

	private char _currentState;
	private final int _checksumSteps;
	private final Map<Character, State> _states = new HashMap<>();

	private final int[] _tape;
	private int _cursor;

	/**
	 * Builds the machine from the input.
	 */
	public Machine(List<String> input) {
		setCurrentState(input.get(0).charAt(15));
		_checksumSteps = Integer.parseInt(input.get(1).split(" ")[5]);
		input = input.subList(2, input.size());

		final int definitionSize = 9;
		while (!input.isEmpty()) {
			List<String> definition = input.subList(0, definitionSize);
			State state = new State(definition);
			getStates().put(state.getName(), state);
			input = input.subList(definitionSize, input.size());
		}

		// Initialize tape and start in the middle.
		_tape = new int[100000];
		setCursor(getTape().length / 2);
	}

	/**
	 * Starts the machine.
	 */
	public int run() {
		for (int i = 0; i < getChecksumSteps(); i++) {
			State state = getStates().get(getCurrentState());
			int value = getTape()[getCursor()];
			getTape()[getCursor()] = state.getWrite(value);
			setCursor(getCursor() + state.getMove(value));
			setCurrentState(state.getNextState(value));
		}
		int sum = 0;
		for (int i = 0; i < getTape().length; i++) {
			sum += getTape()[i];
		}
		return (sum);
	}

	/**
	 * Accessor for the current state
	 */
	private char getCurrentState() {
		return _currentState;
	}

	/**
	 * Accessor for the current state
	 */
	private void setCurrentState(char currentState) {
		_currentState = currentState;
	}

	/**
	 * Accessor for the checksumSteps
	 */
	private int getChecksumSteps() {
		return _checksumSteps;
	}

	/**
	 * Accessor for the states
	 */
	private Map<Character, State> getStates() {
		return _states;
	}

	/**
	 * Accessor for the tape
	 */
	private int[] getTape() {
		return _tape;
	}

	/**
	 * Accessor for the cursor
	 */
	private int getCursor() {
		return _cursor;
	}

	/**
	 * Accessor for the cursor
	 */
	private void setCursor(int cursor) {
		_cursor = cursor;
	}
}