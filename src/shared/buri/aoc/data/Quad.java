package buri.aoc.data;

/**
 * Data class for a quad
 * 
 * @author Brian Uri!
 */
public class Quad {

	private int _x;
	private int _y;
	private int _z;
	private int _t;

	/**
	 * Constructor
	 */
	public Quad(int x, int y, int z, int t) {
		_x = x;
		_y = y;
		_z = z;
		_t = t;
	}

	/**
	 * Returns the Manhattan distance to another quad.
	 */
	public int getManhattanDistance(Quad quad) {
		return (Math.abs(getX() - quad.getX()) + Math.abs(getY() - quad.getY()) + Math.abs(getZ() - quad.getZ())
			+ Math.abs(getT() - quad.getT()));
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(getX()).append(",");
		buffer.append(getY()).append(",");
		buffer.append(getZ()).append(",");
		buffer.append(getT());
		return (buffer.toString());
	}

	@Override
	public boolean equals(Object obj) {
		Quad q = (Quad) obj;
		return (getX() == q.getX() && getY() == q.getY() && getZ() == q.getZ() && getT() == q.getT());
	}

	@Override
	public int hashCode() {
		int result = getX();
		result = 7 * getY();
		result = 7 * getZ();
		result = 7 * getT();
		return (Long.valueOf(result).intValue());
	}

	/**
	 * Accessor for the x
	 */
	public int getX() {
		return _x;
	}

	/**
	 * Accessor for the x
	 */
	public void setX(int x) {
		_x = x;
	}

	/**
	 * Accessor for the y
	 */
	public int getY() {
		return _y;
	}

	/**
	 * Accessor for the y
	 */
	public void setY(int y) {
		_y = y;
	}

	/**
	 * Accessor for the z
	 */
	public int getZ() {
		return _z;
	}

	/**
	 * Accessor for the z
	 */
	public void setZ(int z) {
		_z = z;
	}

	/**
	 * Accessor for the t
	 */
	public int getT() {
		return _t;
	}

	/**
	 * Accessor for the t
	 */
	public void setT(int t) {
		_t = t;
	}
}
