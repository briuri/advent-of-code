package buri.aoc.common.data.tuple;

/**
 * Data class for a triple tuple, intended for Integers or Longs.
 *
 * @author Brian Uri!
 */
public class Triple<T extends Number> extends BaseTuple implements Comparable<Triple> {

	/**
	 * Constructor
	 */
	public Triple(T x, T y, T z) {
		getValues().add(x);
		getValues().add(y);
		getValues().add(z);
	}

	/**
	 * Returns a copy of this triple.
	 */
	public Triple copy() {
		return (new Triple(getX(), getY(), getZ()));
	}

	/**
	 * Sort order: by Y, then X, then Z (top-left first)
	 */
	@Override
	public int compareTo(Triple triple) {
		int compare = compare(getY(), triple.getY());
		if (compare == 0) {
			compare = compare(getX(), triple.getX());
		}
		if (compare == 0) {
			compare = compare(getZ(), triple.getZ());
		}
		return (compare);
	}

	/**
	 * Accessor for the x
	 */
	public T getX() {
		return ((T) getValues().get(0));
	}

	/**
	 * Accessor for the x
	 */
	public void setX(T x) {
		getValues().set(0, x);
	}

	/**
	 * Accessor for the y
	 */
	public T getY() {
		return ((T) getValues().get(1));
	}

	/**
	 * Accessor for the y
	 */
	public void setY(T y) {
		getValues().set(1, y);
	}

	/**
	 * Accessor for the z
	 */
	public T getZ() {
		return ((T) getValues().get(2));
	}

	/**
	 * Accessor for the z
	 */
	public void setZ(T z) {
		getValues().set(2, z);
	}
}