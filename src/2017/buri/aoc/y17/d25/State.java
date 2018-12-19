package buri.aoc.y17.d25;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data model for a state definition 
 * @author Brian Uri!
 */
public class State {

	private char _name;
	private Map<Integer, Integer> _writes = new HashMap<>();
	private Map<Integer, Integer> _moves = new HashMap<>();
	private Map<Integer, Character> _nextStates = new HashMap<>();
	
	/**
	 * Constructor
	 */
	public State(List<String> definition) {
		_name = definition.get(0).charAt(9);
		getWrites().put(0, Character.getNumericValue(definition.get(2).charAt(22)));
		getMoves().put(0, definition.get(3).indexOf("right") == -1 ? -1 : 1);
		getNextStates().put(0, definition.get(4).charAt(26));
		
		getWrites().put(1, Character.getNumericValue(definition.get(6).charAt(22)));
		getMoves().put(1, definition.get(7).indexOf("right") == -1 ? -1 : 1);
		getNextStates().put(1, definition.get(8).charAt(26));
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(getName()).append("[");
		for (Integer key : getWrites().keySet()) {
			buffer.append(key).append("(").append(getWrites().get(key)).append(",");
			buffer.append(getMoves().get(key)).append(",");
			buffer.append(getNextStates().get(key)).append(") ");
		}
		buffer.append("]");
		return (buffer.toString());
	}
	
	/**
	 * Returns the value to write
	 */
	public int getWrite(int value) {
		return (getWrites().get(value));
	}
	
	/**
	 * Returns the distance to move the cursor
	 */
	public int getMove(int value) {
		return (getMoves().get(value));
	}
	
	/**
	 * Returns the next state
	 */
	public char getNextState(int value) {
		return (getNextStates().get(value));
	}
	
	/**
	 * Accessor for the name
	 */
	public char getName() {
		return _name;
	}

	/**
	 * Accessor for the writes
	 */
	private Map<Integer, Integer> getWrites() {
		return _writes;
	}

	/**
	 * Accessor for the moves
	 */
	private Map<Integer, Integer> getMoves() {
		return _moves;
	}

	/**
	 * Accessor for the next states
	 */
	private Map<Integer, Character> getNextStates() {
		return _nextStates;
	}	
}
