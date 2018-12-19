package buri.aoc.y18.d12;

import java.util.ArrayList;
import java.util.List;

/**
 * Data class for the row of pots. Uses an offset index for storage (0 to 2x for puzzle representation and -x to x for
 * array mapping)
 * 
 * @author Brian Uri!
 */
public class Pots {
	private int _generation;
	private boolean[] _pots;
	private List<State> _states;

	/**
	 * Constructor
	 */
	public Pots(List<String> input) {
		_generation = 0;
		_pots = new boolean[input.get(0).length() * 1000];
		_states = new ArrayList<>();
		for (String state : input.subList(1, input.size())) {
			_states.add(new State(state));
		}
		for (int i = 0; i < input.get(0).length(); i++) {
			char value = input.get(0).charAt(i);
			_pots[toTrueIndex(i)] = (String.valueOf(value).equals("#"));
		}
	}

	/**
	 * Grows the plants over 1 generation.
	 */
	public void grow() {
		boolean[] newPots = new boolean[getPots().length];
		System.arraycopy(getPots(), 0, newPots, 0, getPots().length);
		for (int i = 2; i < getPots().length - 2; i++) {
			String pattern = getPattern(i);
			for (State state : getStates()) {
				if (state.getPattern().equals(pattern)) {
					newPots[i] = state.getResult();
					// Don't check any other patterns if this one matches.
					break;
				}
				else {
					newPots[i] = false;
				}
			}
		}
		System.arraycopy(newPots, 0, _pots, 0, newPots.length);
		setGeneration(getGeneration() + 1);
	}

	/**
	 * Returns the sum of the puzzle indices for each slot with a plant.
	 */
	public long getGrowthSum() {
		long sum = 0;
		for (int i = 0; i < getPots().length; i++) {
			if (getPots()[i]) {
				sum += toPuzzleIndex(i);
			}
		}
		return (sum);
	}

	/**
	 * Shows the 0th puzzle index with parentheses.
	 */
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(getGeneration()).append(": ");
		for (int i = 0; i < getPots().length; i++) {
			String value = toOutput(getPots()[i]);
			if (toPuzzleIndex(i) == 0) {
				buffer.append("(").append(value).append(")");
			}
			else {
				buffer.append(" ").append(value).append(" ");
			}
		}
		return (buffer.toString());
	}

	/**
	 * Creates a matchable pattern for the plants around some index, used to compare to the different growth patterns.
	 */
	private String getPattern(int trueIndex) {
		StringBuffer buffer = new StringBuffer();
		for (int i = trueIndex - 2; i < trueIndex + 3; i++) {
			buffer.append(toOutput(getPots()[i]));
		}
		return (buffer.toString());
	}

	/**
	 * Shifts index calls from a puzzle index (-x to x) to a true 0-based index (0 to 2x)
	 */
	private int toTrueIndex(int i) {
		return (i + (getPots().length / 2));
	}

	/**
	 * Shifts index calls from a true index to a puzzle index
	 */
	private int toPuzzleIndex(int i) {
		return (i - (getPots().length / 2));
	}

	/**
	 * Converts true/false to #/.
	 */
	private String toOutput(boolean value) {
		return (value ? "#" : ".");
	}

	/**
	 * Accessor for the generation
	 */
	public int getGeneration() {
		return _generation;
	}

	/**
	 * Accessor for the generation
	 */
	private void setGeneration(int generation) {
		_generation = generation;
	}

	/**
	 * Accessor for the pots
	 */
	private boolean[] getPots() {
		return _pots;
	}

	/**
	 * Accessor for the states
	 */
	private List<State> getStates() {
		return _states;
	}
}
