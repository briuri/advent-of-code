package buri.aoc.data;

/**
 * Base class for an X-Y coordinate pair.
 *  
 * Based on Java's array indexing:
 * (0,0) is the upper left corner of a grid.
 * (x,0) is lower left corner of a grid.
 * (0,y) is upper right corner of grid.
 * (x,y) is lower right corner of grid.
 * 
 * @author Brian Uri!
 */
public class Pair implements Comparable<Pair> {
	private int _x;
	private int _y;
	
	/**
	 * Base constructor
	 */
	protected Pair() {}
	
	/**
	 * String-based Constructor with format "x, y"
	 */
	public Pair(String data) {
		String tokens[] = data.split(",");
		_x = Integer.valueOf(tokens[0].trim());
		_y = Integer.valueOf(tokens[1].trim());
	}
	
	/**
	 * Integer-based Constructor
	 */
	public Pair(int x, int y) {
		_x = x;
		_y = y;
	}

	/**
	 * Returns a copy of this pair.
	 */
	public Pair copy() {
		return (new Pair(getX(), getY()));
	}
	
	/**
	 * Moves the position 1 step in a direction. (0,0 is top-left).
	 */
	public void move(Direction direction) {
		switch (direction) {
			case UP:
				setY(getY() - 1);
				break;
			case RIGHT:
				setX(getX() + 1);
				break;
			case DOWN:
				setY(getY() + 1);
				break;
			case LEFT:
				setX(getX() - 1);
				break;
		}
	}
	
	/**
	 * Pairs are compared by y then x (top-left first).
	 */
	@Override
	public int compareTo(Pair o) {
		int compare = getY() - o.getY();
		if (compare == 0) {
			compare = getX() - o.getX();
		}
		return compare;
	}
	
	@Override
	public boolean equals(Object obj) {
		Pair p = (Pair) obj;
		return (getX() == p.getX() && getY() == p.getY());
	}
	
	@Override
	public int hashCode() {
		int result = getX();
		result = 7 * getY();
		return (result);
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(getX()).append(",").append(getY());
		return (buffer.toString());
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