package buri.aoc.y22.d17;

import buri.aoc.data.tuple.Pair;

import java.util.HashSet;
import java.util.Set;

/**
 * Factory for generating new rocks in different shapes.
 */
public class RockFactory {

	private int _index;

	/**
	 * Constructor
	 */
	public RockFactory() {
		_index = 0;
	}

	/**
	 * Builds the next shape.
	 */
	public Set<Pair<Long>> getNewRock(long height) {
		Set<Pair<Long>> shape = new HashSet<>();
		switch (getIndex()) {
			case (0): // Dash
				shape.add(new Pair<>(2L, 0L));
				shape.add(new Pair<>(3L, 0L));
				shape.add(new Pair<>(4L, 0L));
				shape.add(new Pair<>(5L, 0L));
				break;
			case (1): // Cross
				shape.add(new Pair<>(3L, 0L));
				shape.add(new Pair<>(2L, 1L));
				shape.add(new Pair<>(3L, 1L));
				shape.add(new Pair<>(4L, 1L));
				shape.add(new Pair<>(3L, 2L));
				break;
			case (2): // Backwards L
				shape.add(new Pair<>(2L, 0L));
				shape.add(new Pair<>(3L, 0L));
				shape.add(new Pair<>(4L, 0L));
				shape.add(new Pair<>(4L, 1L));
				shape.add(new Pair<>(4L, 2L));
				break;
			case (3): // I
				shape.add(new Pair<>(2L, 0L));
				shape.add(new Pair<>(2L, 1L));
				shape.add(new Pair<>(2L, 2L));
				shape.add(new Pair<>(2L, 3L));
				break;
			case (4): // Square
				shape.add(new Pair<>(2L, 0L));
				shape.add(new Pair<>(3L, 0L));
				shape.add(new Pair<>(2L, 1L));
				shape.add(new Pair<>(3L, 1L));
		}
		for (Pair<Long> point : shape) {
			point.setY(point.getY() + height);
		}
		setIndex(getIndex() + 1);
		if (getIndex() == 5) {
			setIndex(0);
		}
		return shape;
	}

	/**
	 * Accessor for the index
	 */
	public int getIndex() {
		return _index;
	}

	/**
	 * Accessor for the index
	 */
	private void setIndex(int index) {
		_index = index;
	}
}