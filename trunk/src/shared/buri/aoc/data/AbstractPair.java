package buri.aoc.data;

/**
 * Base class for an X-Y coordinate pair.
 *  
 * @author Brian Uri!
 */
public abstract class AbstractPair {
	private int _x;
	private int _y;
	
	/**
	 * String-based Constructor
	 */
	public AbstractPair(String data) {
		String tokens[] = data.split(", ");
		_x = Integer.valueOf(tokens[0]);
		_y = Integer.valueOf(tokens[1]);
	}
	
	/**
	 * Integer-based Constructor
	 */
	public AbstractPair(int x, int y) {
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
	 * Accessor for X
	 */
	public void setX(int x) {
		_x = x;
	}
	
	/**
	 * Accessor for Y
	 */
	public int getY() {
		return _y;
	}

	/**
	 * Accessor for Y
	 */
	public void setY(int y) {
		_y = y;
	}	
}