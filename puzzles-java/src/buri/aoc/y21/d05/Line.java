package buri.aoc.y21.d05;

import buri.aoc.common.data.tuple.Pair;

import java.util.HashSet;
import java.util.Set;

/**
 * Data model for a line segment.
 *
 * @author Brian Uri!
 */
public class Line {
	private Pair<Integer> _start;
	private Pair<Integer> _end;
	private Set<Pair<Integer>> _points;

	/**
	 * Constructor
	 *
	 * 0,9 -> 5,9
	 *
	 * All lines will be horizontal, vertical or 45 degree angled.
	 */
	public Line(String line) {
		String[] tokens = line.split(" -> ");
		_start = new Pair<>(tokens[0], Integer.class);
		_end = new Pair<>(tokens[1], Integer.class);
		_points = new HashSet<>();

		int incrementX = (getStart().getX() > getEnd().getX() ? -1 : 1);
		int incrementY = (getStart().getY() > getEnd().getY() ? -1 : 1);
		if (isHorizontal()) {
			incrementY = 0;
		}
		if (isVertical()) {
			incrementX = 0;
		}
		Pair<Integer> point = getStart().copy();
		getPoints().add(point.copy());
		while (!point.equals(getEnd())) {
			point.setX(point.getX() + incrementX);
			point.setY(point.getY() + incrementY);
			getPoints().add(point.copy());
		}
	}

	/**
	 * Returns true if startY == endY
	 */
	public boolean isHorizontal() {
		return (getStart().getY().equals(getEnd().getY()));
	}

	/**
	 * Returns true if startX == endX
	 */
	public boolean isVertical() {
		return (getStart().getX().equals(getEnd().getX()));
	}

	/**
	 * Returns true if the line is diagonal (only 3 types of line)
	 */
	public boolean isDiagonal() {
		return (!isHorizontal() && !isVertical());
	}

	/**
	 * Accessor for the start
	 */
	private Pair<Integer> getStart() {
		return _start;
	}

	/**
	 * Accessor for the end
	 */
	private Pair<Integer> getEnd() {
		return _end;
	}

	/**
	 * Accessor for the line as points
	 */
	public Set<Pair<Integer>> getPoints() {
		return _points;
	}
}