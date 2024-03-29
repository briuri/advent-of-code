package buri.aoc.y17.d17;

import java.util.ArrayDeque;

/**
 * Data model of the spinlock. Uses a double-ended queue for fast insert/remove at edges.
 *
 * The current position of the spinlock is always the first entry in the dequeue.
 *
 * @author Brian Uri!
 */
public class Spinlock {

	private final int _steps;
	private final ArrayDeque<Integer> _spinlock;

	/**
	 * Constructor
	 */
	public Spinlock(int size, int steps) {
		_spinlock = new ArrayDeque<>(size);
		_steps = steps;
		getSpinlock().add(0);
	}

	/**
	 * Steps forward, inserts a new value after the point where it stopped, and uses the inserted location as the new
	 * current position.
	 *
	 * Returns the value AFTER the inserted position.
	 */
	public int spinsert(int value) {
		rotate(getSteps() + 1);
		int rightValue = getSpinlock().getFirst();
		getSpinlock().addFirst(value);
		return (rightValue);
	}

	/**
	 * Shifts the list by delta using fast operations.
	 */
	private void rotate(int delta) {
		for (int i = 0; i < delta; i++) {
			Integer first = getSpinlock().removeFirst();
			getSpinlock().addLast(first);
		}
	}

	@Override
	public String toString() {
		return (getSpinlock().toString());
	}

	/**
	 * Accessor for the steps
	 */
	private int getSteps() {
		return _steps;
	}

	/**
	 * Accessor for the spinlock
	 */
	private ArrayDeque<Integer> getSpinlock() {
		return _spinlock;
	}
}