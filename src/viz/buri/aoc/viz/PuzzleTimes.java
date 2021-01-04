package buri.aoc.viz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import buri.aoc.BaseLeaderboard;
import buri.aoc.TimeType;

/**
 * Data class for all puzzle times in a competition year. Handles calculations for stars and orphan Part One records.
 *
 * @author Brian Uri!
 */
public class PuzzleTimes {
	private List<List<PuzzleTime>> _part1Times;
	private List<List<PuzzleTime>> _totalTimes;
	private int _stars;

	/**
	 * Constructor
	 */
	public PuzzleTimes() {
		_part1Times = new ArrayList<>();
		_totalTimes = new ArrayList<>();
		_stars = 0;

		for (int i = 0; i < BaseLeaderboard.TOTAL_PUZZLES; i++) {
			getTimes(TimeType.ONE).add(new ArrayList<>());
			getTimes(TimeType.TOTAL).add(new ArrayList<>());
		}
	}

	/**
	 * Adds a puzzle record to the appropriate list (ignores any outside of the Novetta competition window).
	 */
	public void add(String day, PuzzleTime record) {
		int index = Integer.valueOf(day) - 1;
		if (record.getTime(TimeType.TOTAL) != null && record.completedInYear()) {
			getTimes(TimeType.TOTAL).get(index).add(record);
			addStar(2);
		}
		else if (record.completedInYear()) {
			getTimes(TimeType.ONE).get(index).add(record);
			addStar(1);
		}
	}

	/**
	 * Sorts each list of puzzle records.
	 */
	public void sort() {
		for (int day = 0; day < BaseLeaderboard.TOTAL_PUZZLES; day++) {
			Collections.sort(getTimes(TimeType.ONE).get(day));
			Collections.sort(getTimes(TimeType.TOTAL).get(day));
		}
	}

	/**
	 * Counts the stars earned by a specific person during the competition.
	 */
	public Integer getStars(String name) {
		Integer count = 0;
		for (List<PuzzleTime> times : getTimes(TimeType.ONE)) {
			for (PuzzleTime time : times) {
				if (time.getName().equals(name) && time.completedInYear()) {
					count += 1;
					break;
				}
			}
		}
		for (List<PuzzleTime> times : getTimes(TimeType.TOTAL)) {
			for (PuzzleTime time : times) {
				if (time.getName().equals(name) && time.completedInYear()) {
					count += 2;
					break;
				}
			}
		}
		return (count);
	}

	/**
	 * Accessor for all records of a type: those that only have Part One solved, and those with both parts solved.
	 */
	public List<List<PuzzleTime>> getTimes(TimeType type) {
		if (type == TimeType.TWO) {
			throw new IllegalArgumentException("Only Part One or Total times can be retrieved.");
		}
		return (type == TimeType.ONE ? _part1Times : _totalTimes);
	}

	/**
	 * Accessor for how many records of a type exist on a particular day.
	 */
	public int getCount(TimeType type, int index) {
		List<List<PuzzleTime>> list = getTimes(type);
		return (list.get(index).size());
	}

	/**
	 * Accessor for the total number of stars earned.
	 */
	public int getStars() {
		return (_stars);
	}

	/**
	 * Increments the star count.
	 */
	private void addStar(int count) {
		_stars = _stars + count;
	}
}