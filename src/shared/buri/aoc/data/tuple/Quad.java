package buri.aoc.data.tuple;

/**
 * Data class for a quad tuple, intended for Integers or Longs.
 * 
 * @author Brian Uri!
 */
public class Quad<T extends Number> extends BaseTuple implements Comparable<Quad> {

	/**
	 * Constructor
	 */
	public Quad(T x, T y, T z, T t) {
		getValues().add(x);
		getValues().add(y);
		getValues().add(z);
		getValues().add(t);
	}

	/**
	 * Returns a copy of this quad.
	 */
	public Quad copy() {
		return (new Quad(getX(), getY(), getZ(), getT()));
	}

	/**
	 * Sort order: by Y, then X, then Z, then T (top-left first)
	 */
	@Override
	public int compareTo(Quad quad) {
		int compare = compare(getY(), quad.getY());
		if (compare == 0) {
			compare = compare(getX(), quad.getX());
		}
		if (compare == 0) {
			compare = compare(getZ(), quad.getZ());
		}
		if (compare == 0) {
			compare = compare(getT(), quad.getT());
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

	/**
	 * Accessor for the t
	 */
	public T getT() {
		return ((T) getValues().get(3));
	}

	/**
	 * Accessor for the t
	 */
	public void setT(T t) {
		getValues().set(4, t);
	}
}
