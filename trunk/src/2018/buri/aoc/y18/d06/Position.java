package buri.aoc.y18.d06;

/**
 * Immutable Integer-based ordered pair class.
 * 
 * @author Brian Uri!
 */
public class Position {
	private int _x;
	private int _y;

	/**
	 * Constructor
	 */
	public Position(String data) {
		String tokens[] = data.split(", ");
		_x = Integer.valueOf(tokens[0]);
		_y = Integer.valueOf(tokens[1]);
	}
	
	/**
	 * Constructor
	 */
	public Position(int x, int y) {
		_x = x;
		_y = y;
	}

	/**
	 * Accessor for X
	 */
	public int getX() {
		return _x;
	}

	/**
	 * Accessor for Y
	 */
	public int getY() {
		return _y;
	}
}
