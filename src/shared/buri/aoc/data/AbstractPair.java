package buri.aoc.data;

/**
 * Base class for an X-Y coordinate pair.
 *  
 * @author Brian Uri!
 */
public abstract class AbstractPair implements Comparable<AbstractPair> {
	private int _x;
	private int _y;
	
	/**
	 * Base constructor
	 */
	protected AbstractPair() {}
	
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
	 * Pairs are compared by y then x (top-left first).
	 */
	@Override
	public int compareTo(AbstractPair o) {
		int compare = getY() - o.getY();
		if (compare == 0) {
			compare = getX() - o.getX();
		}
		return compare;
	}
	
	@Override
	public boolean equals(Object obj) {
		AbstractPair p = (AbstractPair) obj;
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