package buri.aoc.y18.d09;

import java.util.ArrayDeque;

/**
 * The marbles are numbered starting with 0 and increasing by 1 until every marble has a number.
 *
 * First, the marble numbered 0 is placed in the circle. At this point, while it contains only a single marble, it is
 * still a circle: the marble is both clockwise from itself and counter-clockwise from itself. This marble is designated
 * the current marble.
 *
 * Then, each Elf takes a turn placing the lowest-numbered remaining marble into the circle between the marbles that are
 * 1 and 2 marbles clockwise of the current marble. (When the circle is large enough, this means that there is one
 * marble between the marble that was just placed and the current marble.) The marble that was just placed then becomes
 * the current marble.
 *
 * However, if the marble that is about to be placed has a number which is a multiple of 23, something entirely
 * different happens. First, the current player keeps the marble they would have placed, adding it to their score. In
 * addition, the marble 7 marbles counter-clockwise from the current marble is removed from the circle and also added to
 * the current player's score. The marble located immediately clockwise of the marble that was removed becomes the new
 * current marble.
 *
 * @author Brian Uri!
 */
public class Circle {

	ArrayDeque<Integer> _circle;

	/**
	 * Constructor
	 *
	 * Part 2 Performance Notes:
	 * - ArrayList with initialized capacity and a pointer to the current position: 42 minutes.
	 * - LinkedList with a pointer to current position: Stopped after 6 hours with 2 million turns to go.
	 * - ArrayDeque with a rotate() method so only first/last elements are modified: 0.6 seconds.
	 */
	public Circle(int size) {
		_circle = new ArrayDeque<>(size);
		addMarble(0);
	}

	/**
	 * Shifts the list by delta using fast operations.
	 */
	private void rotate(int delta) {
		if (delta == 0) {
			return;
		}
		if (delta > 0) {
			for (int i = 0; i < delta; i++) {
				Integer last = getCircle().removeLast();
				getCircle().addFirst(last);
			}
		}
		else {
			for (int i = 0; i < Math.abs(delta) - 1; i++) {
				Integer first = getCircle().remove();
				getCircle().addLast(first);
			}
		}
	}

	/**
	 * Adds a marble according to the rules of the game. Returns the score of the move.
	 */
	public long addMarble(int value) {
		if (value < 2) {
			getCircle().add(value);
			return (0);
		}
		if (value % 23 == 0) {
			rotate(-7);
			return (getCircle().pop() + value);
		}
		rotate(2);
		getCircle().addLast(value);
		return (0);
	}

	/**
	 * Accessor for the circle
	 */
	private ArrayDeque<Integer> getCircle() {
		return _circle;
	}
}