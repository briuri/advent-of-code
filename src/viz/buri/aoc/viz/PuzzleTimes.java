package buri.aoc.viz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import buri.aoc.BaseLeaderboard;

/**
 * Data class for all puzzle times in a competition year. Handles calculations for stars and orphan Part One records.
 *
 * @author Brian Uri!
 */
public class PuzzleTimes {
	private List<List<PuzzleTime>> _part1Times;
	private List<List<PuzzleTime>> _part2Times;

	/**
	 * Constructor
	 */
	public PuzzleTimes() {
		_part1Times = new ArrayList<>();
		_part2Times = new ArrayList<>();
		for (int i = 0; i < BaseLeaderboard.TOTAL_PUZZLES; i++) {
			_part1Times.add(new ArrayList<>());
			_part2Times.add(new ArrayList<>());
		}
	}

	/**
	 * Adds a puzzle record to the appropriate list (ignores any outside of the Novetta competition window).
	 */
	public void add(int index, PuzzleTime record) {
		if (record.getPart2Time() != null && record.completedInYear()) {
			getPart2Times().get(index).add(record);
		}
		else if (record.completedInYear()) {
			getPart1Times().get(index).add(record);
		}
	}

	/**
	 * Sorts each list of puzzle records.
	 */
	public void sort() {
		for (int day = 0; day < BaseLeaderboard.TOTAL_PUZZLES; day++) {
			Collections.sort(getPart1Times().get(day));
			Collections.sort(getPart2Times().get(day));
		}
	}

	/**
	 * Counts the stars earned by a specific person during the competition.
	 */
	public int getStars(String name) {
		int count = 0;
		for (List<PuzzleTime> times : getPart1Times()) {
			for (PuzzleTime time : times) {
				if (time.getName().equals(name) && time.completedInYear()) {
					count += 1;
					break;
				}
			}
		}
		for (List<PuzzleTime> times : getPart2Times()) {
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
	 * Accessor for all records that only have Part One solved.
	 */
	public List<List<PuzzleTime>> getPart1Times() {
		return (_part1Times);
	}

	/**
	 * Accessor for all records that have both parts solved.
	 */
	public List<List<PuzzleTime>> getPart2Times() {
		return (_part2Times);
	}
}