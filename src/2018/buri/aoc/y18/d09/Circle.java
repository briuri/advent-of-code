package buri.aoc.y18.d09;

import java.util.ArrayList;
import java.util.List;

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

	private int _current;
	List<Integer> _circle;
	
	/**
	 * Constructor
	 * 
	 * Strangely, the ArrayList with a set initial capacity runs Part 2 in 42 minutes, while the LinkedList
	 * implementation had only processed 5 million turns after 6 hours!
	 */
	public Circle(int size) {
		_circle = new ArrayList<>(size);
		addMarble(0);
	}

	/**
	 * Adds a marble according to the rules of the game. Returns the score of the move.
	 */
	public long addMarble(int value) {
		if (value < 2) {
			getCircle().add(value);
			setCurrent(value);
			return (0);
		}
		if (value % 23 == 0) {
			int index = getCurrent() - 7;
			if (index < 0) {
				index += getCircle().size();
			}
			int score = getCircle().remove(index);
			setCurrent(index);
			return (score + value);
		}
		int index = getCurrent() + 2;
		if (index >= getCircle().size()) {
			index -= getCircle().size();
		}
		getCircle().add(index, value);
		setCurrent(index);
		return (0);		
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < getCircle().size(); i++) {
			int value = getCircle().get(i);
			if (i == getCurrent()) {
				buffer.append(String.format("(%1$-1s)", value));
			}
			else {
				buffer.append(value);
			}
			buffer.append(" ");
		}
		return (buffer.toString());
	}

	/**
	 * Accessor for the current
	 */
	public int getCurrent() {
		return _current;
	}

	/**
	 * Accessor for the current
	 */
	public void setCurrent(int current) {
		_current = current;
	}

	/**
	 * Accessor for the circle
	 */
	private List<Integer> getCircle() {
		return _circle;
	}
}
