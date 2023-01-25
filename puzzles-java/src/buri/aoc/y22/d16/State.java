package buri.aoc.y22.d16;

import java.util.Objects;

/**
 * Capture a snapshot of a potential valve/tunnel state.
 *
 * @author Brian Uri!
 */
public class State implements Comparable<State> {

	private final String _valve;
	private final long _opened;
	private final long _pressure;

	/**
	 * Constructor
	 */
	public State(String valve, long opened, long pressure) {
		_valve = valve;
		_opened = opened;
		_pressure = pressure;
	}

	@Override
	public String toString() {
		return ("[" + getValve() + ", " + getOpened() + ", " + getPressure() + "]");
	}

	/**
	 * Special comparator just for Part 2. Does not match equals/hashcode.
	 */
	@Override
	public int compareTo(State o) {
		return Long.compare(getPressure(), o.getPressure());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		State state = (State) o;
		return (getOpened() == state.getOpened()
				&& getPressure() == state.getPressure()
				&& Objects.equals(getValve(), state.getValve()));
	}

	@Override
	public int hashCode() {
		return Objects.hash(getValve(), getOpened(), getPressure());
	}

	/**
	 * Accessor for the valve name
	 */
	public String getValve() {
		return _valve;
	}

	/**
	 * Accessor for all of the valves currently opened.
	 */
	public long getOpened() {
		return _opened;
	}

	/**
	 * Accessor for the total pressure so far.
	 */
	public long getPressure() {
		return _pressure;
	}
}