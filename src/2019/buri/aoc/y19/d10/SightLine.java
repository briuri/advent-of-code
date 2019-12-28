package buri.aoc.y19.d10;

import buri.aoc.data.tuple.Pair;

/**
 * Model of a sight line from a monitoring station to an asteroid. Contains a pair representing the slope of the sight
 * line away from the station that can be ordered by its angle around the station (clockwise from up).
 * 
 * @author Brian Uri!
 */
public class SightLine implements Comparable<SightLine> {
	private Pair<Integer> _slope;
	private Double _angle;

	/**
	 * Calculates a sight line between two points.
	 */
	public SightLine(Pair<Integer> station, Pair<Integer> asteroid) {
		int dx = asteroid.getX() - station.getX();
		int dy = asteroid.getY() - station.getY();
		long gcd = Day10.getGCD(dx, dy);
		if (gcd < 0) {
			gcd = gcd * -1;
		}
		_slope = new Pair((int) (dx / gcd), (int) (dy / gcd));

		// Subtract PI/2 to start facing up rather than right.
		_angle = Math.atan2(getSlope().getX().doubleValue(), getSlope().getY().doubleValue()) - Math.PI / 2.0;
	}

	/**
	 * Sort on angle, starting facing up and going clockwise.
	 */
	@Override
	public int compareTo(SightLine o) {
		return (-1 * getAngle().compareTo(o.getAngle()));
	}

	/**
	 * Compares equality based on slope.
	 */
	@Override
	public boolean equals(Object obj) {
		return (getSlope().equals(((SightLine) obj).getSlope()));
	}

	/**
	 * Compares equality based on slope.
	 */
	@Override
	public int hashCode() {
		return (getSlope().hashCode());
	}

	/**
	 * Accessor for the slope
	 */
	public Pair getSlope() {
		return _slope;
	}

	/**
	 * Accessor for the angle
	 */
	private Double getAngle() {
		return _angle;
	}
}
