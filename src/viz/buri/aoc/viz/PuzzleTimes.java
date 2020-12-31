package buri.aoc.viz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import buri.aoc.BaseLeaderboard;
import buri.aoc.Part;

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
		if (record.getTime(Part.TWO) != null && record.completedInYear()) {
			getTimes(Part.TWO).get(index).add(record);
		}
		else if (record.completedInYear()) {
			getTimes(Part.ONE).get(index).add(record);
		}
	}

	/**
	 * Sorts each list of puzzle records.
	 */
	public void sort() {
		for (int day = 0; day < BaseLeaderboard.TOTAL_PUZZLES; day++) {
			Collections.sort(getTimes(Part.ONE).get(day));
			Collections.sort(getTimes(Part.TWO).get(day));
		}
	}

	/**
	 * Counts the stars earned by a specific person during the competition.
	 */
	public int getStars(String name) {
		int count = 0;
		for (List<PuzzleTime> times : getTimes(Part.ONE)) {
			for (PuzzleTime time : times) {
				if (time.getName().equals(name) && time.completedInYear()) {
					count += 1;
					break;
				}
			}
		}
		for (List<PuzzleTime> times : getTimes(Part.TWO)) {
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
	public List<List<PuzzleTime>> getTimes(Part part) {
		return (part == Part.ONE ? _part1Times : _part2Times);
	}
}