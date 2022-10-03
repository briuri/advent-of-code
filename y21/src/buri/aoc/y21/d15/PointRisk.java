package buri.aoc.y21.d15;

import buri.aoc.data.tuple.Pair;

/**
 * Data model for a point in the risk map and the risk accumulated so far to get there.
 *
 * @author Brian Uri!
 */
public class PointRisk implements Comparable<PointRisk> {
	private Pair<Integer> _point;
	private Integer _riskSoFar;

	/**
	 * Constructor
	 */
	public PointRisk(Pair<Integer> point, int riskSoFar) {
		_point = point;
		_riskSoFar = riskSoFar;
	}

	/**
	 * Sort by ascending risk and then ascending X/Y point.
	 */
	@Override
	public int compareTo(PointRisk o) {
		int compare = getRiskSoFar().compareTo(o.getRiskSoFar());
		if (compare == 0) {
			compare = getPoint().compareTo(o.getPoint());
		}
		return (compare);
	}

	/**
	 * Accessor for the point
	 */
	public Pair<Integer> getPoint() {
		return _point;
	}

	/**
	 * Accessor for the risk accumulated up to this point
	 */
	public Integer getRiskSoFar() {
		return _riskSoFar;
	}
}