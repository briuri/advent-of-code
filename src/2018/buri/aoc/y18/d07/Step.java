package buri.aoc.y18.d07;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A graph-based model of a step, with prereqs (previous) and next steps (next).
 * 
 * @author Brian Uri!
 */
public class Step implements Comparable<Step> {

	private String _name;
	private List<String> _previous;
	private List<String> _next;
	private int _baseTime;
	private int _startedAt;

	/**
	 * Constructor
	 */
	public Step(String name, int baseTime) {
		_name = name;
		_previous = new ArrayList<>();
		_next = new ArrayList<>();
		_baseTime = baseTime;
		_startedAt = -1;
	}

	@Override
	public String toString() {
		return (getPrevious() + " -> " + getName() + " -> " + getNext());
	}

	@Override
	public int compareTo(Step o) {
		return (getName().compareTo(o.getName()));
	}

	/**
	 * Calculates the time to complete this step. Assumes 1 capital letter for a name.
	 */
	public int getTime() {
		return (getBaseTime() + ((int) getName().charAt(0)) - 64);
	}

	/**
	 * Checks if this step finishes at the specific time, based on when it started and the time required.
	 */
	public boolean finishesAt(int currentTime) {
		if (getStartedAt() == -1) {
			return (false);
		}
		return (currentTime - getStartedAt() >= getTime());
	}

	/**
	 * Adds a step to the list of dependencies.
	 */
	public void addPrevious(String name) {
		if (!getPrevious().contains(name)) {
			getPrevious().add(name);
			Collections.sort(getPrevious());
		}
	}

	/**
	 * Adds a step to the list of downstream steps.
	 */
	public void addNext(String name) {
		if (!getNext().contains(name)) {
			getNext().add(name);
			Collections.sort(getNext());
		}
	}

	/**
	 * Accessor for the name
	 */
	public String getName() {
		return _name;
	}

	/**
	 * Accessor for the previous
	 */
	public List<String> getPrevious() {
		return _previous;
	}

	/**
	 * Accessor for the next
	 */
	public List<String> getNext() {
		return _next;
	}

	/**
	 * Accessor for the base time (not including the time based on name)
	 */
	public int getBaseTime() {
		return _baseTime;
	}

	/**
	 * Accessor for the startedAt
	 */
	public int getStartedAt() {
		return _startedAt;
	}

	/**
	 * Accessor for the startedAt
	 */
	public void setStartedAt(int startedAt) {
		_startedAt = startedAt;
	}
}