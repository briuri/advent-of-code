package buri.aoc.viz;

import java.util.Calendar;

import buri.aoc.TimeType;

/**
 * Data class for a completion record in a daily puzzle. Part One is assumed to always be solved at a minimum. If Part
 * Two is not solved, the Part 2 split time and Total time will be null.
 *
 * @author Brian Uri!
 */
public class PuzzleTime implements Comparable<PuzzleTime> {
	private final String _year;
	private final String _name;

	private final Long _part1Time;
	private final String _yearPart1Completed;

	private final Long _totalTime;
	private final String _yearPart2Completed;

	/**
	 * Constructor
	 */
	public PuzzleTime(String year, String day, String name, Long part1Time, Long totalTime) {
		_year = year;
		_name = name;

		Calendar puzzleTime = Calendar.getInstance();
		puzzleTime.set(Calendar.YEAR, Integer.parseInt(getYear()));
		puzzleTime.set(Calendar.MONTH, Calendar.DECEMBER);
		puzzleTime.set(Calendar.DATE, Integer.parseInt(day));
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

		if (totalTime == null) {
			_yearPart2Completed = "";
			_totalTime = null;
		}
		else {
			Calendar unixTotalTime = Calendar.getInstance();
			unixTotalTime.setTimeInMillis(totalTime * 1000L);
			_yearPart2Completed = String.valueOf(unixTotalTime.get(Calendar.YEAR));
			_totalTime = unixTotalTime.getTimeInMillis() - puzzleTime.getTimeInMillis();
		}
	}

	/**
	 * Converts milliseconds into a string timestamp. Standard timestamps allow 3 digits for the hour. 2016 sometimes
	 * had 4-digit hours.
	 */
	public static String formatTime(Long time, boolean isStandardWidth) {
		return (isStandardWidth ? formatTime(time, 3) : formatTime(time, 4));
	}

	/**
	 * Converts milliseconds into a string timestamp.
	 */
	private static String formatTime(Long time, int hourWidth) {
		StringBuilder builder = new StringBuilder();
		if (time != null) {
			// Median timestamps may have half-second from average calculation. Round up.
			if (time % 1000 != 0) {
				time += 500L;
			}

			time = time / 1000L;
			String hours = String.valueOf(time / (60 * 60));
			if (hours.length() == 1) {
				builder.append("0");
			}
			builder.append(hours).append(":");

			time = time % (60 * 60);
			String minutes = String.valueOf(time / 60);
			if (minutes.length() == 1) {
				builder.append("0");
			}
			builder.append(minutes).append(":");

			time = time % 60;
			String seconds = String.valueOf(time);
			if (seconds.length() == 1) {
				builder.append("0");
			}
			builder.append(seconds);
		}
		// Left-pad time. 2016 had 4-digit hours in All Players report.
		int padSize = hourWidth + 6 - builder.length();
		for (int i = 0; i < padSize; i++) {
			builder.insert(0, "&nbsp;");
		}
		return (builder.toString());
	}

	@Override
	public int compareTo(PuzzleTime o) {
		// Base on total solve time.
		int compare;
		// Part One+Two solves
		if (getTime(TimeType.TOTAL) != null) {
			// Part One+Two solves are always ranked higher than orphan Part One solves.
			Long compareValue = (o.getTime(TimeType.TOTAL) == null ? Long.MAX_VALUE : o.getTime(TimeType.TOTAL));
			compare = getTime(TimeType.TOTAL).compareTo(compareValue);
		}
		// Orphan Part One solves
		else {
			compare = getTime(TimeType.ONE).compareTo(o.getTime(TimeType.ONE));
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
		if (getTime(TimeType.TOTAL) != null) {
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
	 * Accessor for one of the timestamps on a puzzle solve: Part One split, Part Two split, or Total.
	 */
	public Long getTime(TimeType type) {
		if (type == TimeType.ONE) {
			return (_part1Time);
		}
		if (type == TimeType.TOTAL) {
			return (_totalTime);
		}
		return (_totalTime == null ? null : _totalTime - _part1Time);
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