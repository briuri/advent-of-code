package buri.aoc.viz;

import java.util.Calendar;

import buri.aoc.Part;

/**
 * Data class for 1 completion record in a daily puzzle. Part One is assumed to always be solved at a minimum.
 *
 * @author Brian Uri!
 */
public class PuzzleTime implements Comparable<PuzzleTime> {
	private String _year;
	private String _name;

	private Long _part1Time;
	private String _yearPart1Completed;

	private Long _part2Time;
	private String _yearPart2Completed;

	/**
	 * Constructor
	 */
	public PuzzleTime(String year, String day, String name, Long part1Time, Long part2Time) {
		_year = year;
		_name = name;

		Calendar puzzleTime = Calendar.getInstance();
		puzzleTime.set(Calendar.YEAR, Integer.valueOf(getYear()));
		puzzleTime.set(Calendar.MONTH, Calendar.DECEMBER);
		puzzleTime.set(Calendar.DATE, Integer.valueOf(day));
		puzzleTime.set(Calendar.HOUR_OF_DAY, 0);
		puzzleTime.set(Calendar.MINUTE, 0);
		puzzleTime.set(Calendar.SECOND, 0);
		puzzleTime.set(Calendar.MILLISECOND, 0);

		if (part1Time == null) {
			throw new IllegalArgumentException("A puzzle record must have at least Part 1 completed.");
		}
		Calendar unixPart1Time = Calendar.getInstance();
		unixPart1Time.setTimeInMillis(part1Time * 1000L);
		_yearPart1Completed = String.valueOf(unixPart1Time.get(Calendar.YEAR));
		_part1Time = unixPart1Time.getTimeInMillis() - puzzleTime.getTimeInMillis();

		if (part2Time == null) {
			_yearPart2Completed = "";
			_part2Time = null;
		}
		else {
			Calendar unixPart2Time = Calendar.getInstance();
			unixPart2Time.setTimeInMillis(part2Time * 1000L);
			_yearPart2Completed = String.valueOf(unixPart2Time.get(Calendar.YEAR));
			_part2Time = unixPart2Time.getTimeInMillis() - puzzleTime.getTimeInMillis();
		}
	}

	/**
	 * Converts milliseconds into a string timestamp. Standard timestamps allow 3 digits for the hour. 2016 sometimes had 4-digit hours.
	 */
	public static String formatTime(long time, boolean isStandardWidth) {
		return (isStandardWidth ? formatTime(time, 3) : formatTime(time, 4));
	}

	/**
	 * Converts milliseconds into a string timestamp.
	 */
	private static String formatTime(long time, int hourWidth) {
		StringBuffer buffer = new StringBuffer();

		// Median timestamps may have half-second from average calculation. Round up.
		if (time % 1000 != 0) {
			time += 500L;
		}

		time = time / 1000L;
		String hours = String.valueOf(time / (60 * 60));
		if (hours.length() == 1) {
			buffer.append("0");
		}
		buffer.append(hours).append(":");

		time = time % (60 * 60);
		String minutes = String.valueOf(time / 60);
		if (minutes.length() == 1) {
			buffer.append("0");
		}
		buffer.append(minutes).append(":");

		time = time % 60;
		String seconds = String.valueOf(time);
		if (seconds.length() == 1) {
			buffer.append("0");
		}
		buffer.append(seconds);

		// Left-pad time. 2016 had 4-digit hours in the All Players report.
		int padSize = hourWidth + 6 - buffer.length();
		for (int i = 0; i < padSize; i++) {
			buffer.insert(0, "&nbsp;");
		}
		return (buffer.toString());
	}

	@Override
	public int compareTo(PuzzleTime o) {
		// Base on total solve time.
		int compare;
		// Part One+Two solves
		if (getTime(Part.TWO) != null) {
			// Part One+Two solves are always ranked higher than orphan Part One solves.
			Long compareValue = (o.getTime(Part.TWO) == null ? Long.MAX_VALUE : o.getTime(Part.TWO));
			compare = getTime(Part.TWO).compareTo(compareValue);
		}
		// Orphan Part One solves
		else {
			compare = getTime(Part.ONE).compareTo(o.getTime(Part.ONE));
		}
		// For ties, alphabetize on last name.
		if (compare == 0) {
			compare = getName().split(" ")[1].compareTo(o.getName().split(" ")[1]);
		}
		return (compare);
	}

	/**
	 * Returns true if the most recent part solved was completed in the same year it was released.
	 */
	public boolean completedInYear() {
		if (getTime(Part.TWO) != null) {
			return (getYear().equals(getYearPart2Completed()));
		}
		return (getYear().equals(getYearPart1Completed()));
	}

	/**
	 * Accessor for the year of this puzzle
	 */
	private String getYear() {
		return _year;
	}

	/**
	 * Accessor for the name of the player who solved the puzzle
	 */
	public String getName() {
		return _name;
	}

	/**
	 * Accessor for the time part of a puzzle was completed (after its release) in milliseconds
	 */
	public Long getTime(Part part) {
		return (part == Part.ONE ? _part1Time : _part2Time);
	}

	/**
	 * Accessor for the year part 1 was completed
	 */
	private String getYearPart1Completed() {
		return _yearPart1Completed;
	}

	/**
	 * Accessor for the year part 2 was completed
	 */
	private String getYearPart2Completed() {
		return _yearPart2Completed;
	}
}