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

	private int _steps;
	ArrayDeque<Integer> _spinlock;

	/**
	 * Constructor
	 */
	public Spinlock(int size, int steps) {
		_spinlock = new ArrayDeque<>(size);
		_steps = steps;
		getSpinlock().add(0);
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
	 * Returns the value after zero in the list (spins the lock to find this).
	 */
	public int getValueAfterZero() {
		while (true) {
			if (getSpinlock().getFirst() == 0) {
				rotate(1);
				return (getSpinlock().getFirst());
			}
			rotate(1);
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
