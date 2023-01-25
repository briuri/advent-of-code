package buri.aoc.y22.d24;

import buri.aoc.common.data.Direction;
import buri.aoc.common.data.tuple.Triple;

import java.util.HashSet;
import java.util.Set;

/**
 * Data class for blizzard positions at different times.
 */
public class Blizzards {

	private final int _totalMinutes;
	private final int _upWall, _downWall, _leftWall, _rightWall;
	private final Set<Triple<Integer>> _blizzards = new HashSet<>();

	/**
	 * Constructor
	 */
	public Blizzards(int totalMinutes, int upWall, int downWall, int leftWall, int rightWall) {
		_totalMinutes = totalMinutes;
		_upWall = upWall;
		_downWall = downWall;
		_leftWall = leftWall;
		_rightWall = rightWall;
	}

	/**
	 * Adds a blizzard to the collection and simulates its movement over time.
	 */
	public void addBlizzard(int x, int y, Direction direction) {
		Triple<Integer> blizzard = new Triple<>(x, y, 0);
		getBlizzards().add(blizzard);
		for (int minute = 1; minute < getTotalMinutes(); minute++) {
			blizzard = blizzard.copy();
			if (direction == Direction.UP) {
				blizzard.setY(blizzard.getY() - 1);
				if (blizzard.getY() == getUpWall()) {
					blizzard.setY(getDownWall() - 1);
				}
			}
			if (direction == Direction.DOWN) {
				blizzard.setY(blizzard.getY() + 1);
				if (blizzard.getY() == getDownWall()) {
					blizzard.setY(getUpWall() + 1);
				}
			}
			if (direction == Direction.LEFT) {
				blizzard.setX(blizzard.getX() - 1);
				if (blizzard.getX() == getLeftWall()) {
					blizzard.setX(getRightWall() - 1);
				}
			}
			if (direction == Direction.RIGHT) {
				blizzard.setX(blizzard.getX() + 1);
				if (blizzard.getX() == getRightWall()) {
					blizzard.setX(getLeftWall() + 1);
				}
			}
			blizzard.setZ(blizzard.getZ() + 1);
			getBlizzards().add(blizzard);
		}
	}

	/**
	 * Accessor for the total number of snapshots to simulate.
	 */
	public int getTotalMinutes() {
		return _totalMinutes;
	}

	/**
	 * Accessor for the y position of the upper wall.
	 */
	public int getUpWall() {
		return _upWall;
	}

	/**
	 * Accessor for the y position of the lower wall.
	 */
	public int getDownWall() {
		return _downWall;
	}

	/**
	 * Accessor for the x position of the left wall.
	 */
	public int getLeftWall() {
		return _leftWall;
	}

	/**
	 * Accessor for the x position of the right wall.
	 */
	public int getRightWall() {
		return _rightWall;
	}

	/**
	 * Accessor for the blizzard snapshots.
	 */
	public Set<Triple<Integer>> getBlizzards() {
		return _blizzards;
	}
}