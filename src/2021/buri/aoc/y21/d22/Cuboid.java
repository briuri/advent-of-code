package buri.aoc.y21.d22;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Data model for a cuboid.
 *
 * @author Brian Uri!
 */
public class Cuboid {
	private int _minX;
	private int _maxX;
	private int _minY;
	private int _maxY;
	private int _minZ;
	private int _maxZ;
	private boolean _on;

	/**
	 * Constructor
	 */
	public Cuboid(String line) {
		String[] tokens = line.split(" ");
		String[] coords = tokens[1].split(",");
		String[] xCoords = coords[0].substring(2).split("\\.\\.");
		_minX = Integer.valueOf(xCoords[0]);
		_maxX = Integer.valueOf(xCoords[1]);
		String[] yCoords = coords[1].substring(2).split("\\.\\.");
		_minY = Integer.valueOf(yCoords[0]);
		_maxY = Integer.valueOf(yCoords[1]);
		String[] zCoords = coords[2].substring(2).split("\\.\\.");
		_minZ = Integer.valueOf(zCoords[0]);
		_maxZ = Integer.valueOf(zCoords[1]);
		_on = tokens[0].equals("on");
	}

	/**
	 * Constructor for a cuboid fragment in part 2.
	 */
	public Cuboid(int minX, int maxX, int minY, int maxY, int minZ, int maxZ, boolean isOn) {
		_minX = minX;
		_maxX = maxX;
		_minY = minY;
		_maxY = maxY;
		_minZ = minZ;
		_maxZ = maxZ;
		_on = isOn;
	}

	/**
	 * Calculates the volume of this cuboid and returns as a string.
	 */
	public BigInteger getVolume() {
		BigInteger x = new BigInteger(String.valueOf(getMaxX() - getMinX() + 1));
		BigInteger y = new BigInteger(String.valueOf(getMaxY() - getMinY() + 1));
		BigInteger z = new BigInteger(String.valueOf(getMaxZ() - getMinZ() + 1));
		return (x.multiply(y).multiply(z));
	}

	/**
	 * Creates a cuboid representing the intersection of two cuboids
	 */
	public Cuboid getIntersection(Cuboid other) {
		if (getMaxX() < other.getMinX() || other.getMaxX() < getMinX() || getMaxY() < other.getMinY() || other.getMaxY() < getMinY()
			|| getMaxZ() < other.getMinZ() || other.getMaxZ() < getMinZ()) {
			return null;
		}
		List<Integer> xs = new ArrayList<>();
		xs.add(getMinX());
		xs.add(getMaxX());
		xs.add(other.getMinX());
		xs.add(other.getMaxX());
		Collections.sort(xs);

		List<Integer> ys = new ArrayList<>();
		ys.add(getMinY());
		ys.add(getMaxY());
		ys.add(other.getMinY());
		ys.add(other.getMaxY());
		Collections.sort(ys);

		List<Integer> zs = new ArrayList<>();
		zs.add(getMinZ());
		zs.add(getMaxZ());
		zs.add(other.getMinZ());
		zs.add(other.getMaxZ());
		Collections.sort(zs);

		return (new Cuboid(xs.get(1), xs.get(2), ys.get(1), ys.get(2), zs.get(1), zs.get(2), isOn()));
	}

	/**
	 * Creates smaller cuboids for the unique part of this cuboid.
	 */
	public List<Cuboid> remove(Cuboid chunk) {
		List<Cuboid> fragments = new ArrayList<>();
		if (getMinX() < chunk.getMinX()) {
			fragments.add(new Cuboid(getMinX(), chunk.getMinX() - 1, getMinY(), getMaxY(), getMinZ(), getMaxZ(), isOn()));
		}
		if (chunk.getMaxX() < getMaxX()) {
			fragments.add(new Cuboid(chunk.getMaxX() + 1, getMaxX(), getMinY(), getMaxY(), getMinZ(), getMaxZ(), isOn()));
		}
		if (getMinY() < chunk.getMinY()) {
			fragments.add(new Cuboid(chunk.getMinX(), chunk.getMaxX(), getMinY(), chunk.getMinY() - 1, getMinZ(), getMaxZ(), isOn()));
		}
		if (chunk.getMaxY() < getMaxY()) {
			fragments.add(new Cuboid(chunk.getMinX(), chunk.getMaxX(), chunk.getMaxY() + 1, getMaxY(), getMinZ(), getMaxZ(), isOn()));
		}
		if (getMinZ() < chunk.getMinZ()) {
			fragments.add(new Cuboid(chunk.getMinX(), chunk.getMaxX(), chunk.getMinY(), chunk.getMaxY(), getMinZ(), chunk.getMinZ() - 1, isOn()));
		}
		if (chunk.getMaxZ() < getMaxZ()) {
			fragments.add(new Cuboid(chunk.getMinX(), chunk.getMaxX(), chunk.getMinY(), chunk.getMaxY(), chunk.getMaxZ() + 1, getMaxZ(), isOn()));
		}
		return (fragments);
	}

	/**
	 * Returns true if cuboid is in init area (for part 1).
	 */
	public boolean inInitArea() {
		return (getMinX() >= -50 && getMinY() >= -50 && getMinZ() >= -50 && getMaxX() <= 50 && getMaxY() <= 50 && getMaxZ() <= 50);
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		Cuboid test = (Cuboid) obj;
		return (getMinX() == test.getMinX() && getMaxX() == test.getMaxX() && getMinY() == test.getMinY() && getMaxY() == test.getMaxY()
			&& getMinZ() == test.getMinZ() && getMaxZ() == test.getMaxZ() && isOn() == test.isOn());
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ("(" + (isOn() ? "on" : "off") + " x=" + getMinX() + ".." + getMaxX() + ",y=" + getMinY() + ".." + getMaxY() + ",z=" + getMinZ() + ".."
			+ getMaxZ() + ",vol=" + getVolume() + ")");
	}

	/**
	 * Accessor for the on
	 */
	public boolean isOn() {
		return _on;
	}

	/**
	 * Accessor for the minX
	 */
	public int getMinX() {
		return _minX;
	}

	/**
	 * Accessor for the maxX
	 */
	public int getMaxX() {
		return _maxX;
	}

	/**
	 * Accessor for the minY
	 */
	public int getMinY() {
		return _minY;
	}

	/**
	 * Accessor for the maxY
	 */
	public int getMaxY() {
		return _maxY;
	}

	/**
	 * Accessor for the minZ
	 */
	public int getMinZ() {
		return _minZ;
	}

	/**
	 * Accessor for the maxZ
	 */
	public int getMaxZ() {
		return _maxZ;
	}
}