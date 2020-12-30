package buri.aoc.viz;

import java.util.Calendar;

import buri.aoc.Part;

/**
 * Data class for 1 completion record in a daily puzzle.
 *
 * @author Brian Uri!
 */
public class PuzzleTime implements Comparable<PuzzleTime> {
	private int _year;

	private String _name;
	private int _yearPart1Completed;
	private int _yearPart2Completed;
	private long _part1Time;
	private long _part2Time;

	/**
	 * Constructor
	 */
	public PuzzleTime(String year, int day, String name, long part1Time, long part2Time) {
		_year = Integer.valueOf(year);
		_name = name;

		Calendar unixPart1Time = Calendar.getInstance();
		unixPart1Time.setTimeInMillis(part1Time * 1000L);
		_yearPart1Completed = unixPart1Time.get(Calendar.YEAR);

		Calendar unixPart2Time = Calendar.getInstance();
		unixPart2Time.setTimeInMillis(part2Time * 1000L);
		_yearPart2Completed = unixPart2Time.get(Calendar.YEAR);

		Calendar puzzleTime = Calendar.getInstance();
		puzzleTime.set(Calendar.YEAR, getYear());
		puzzleTime.set(Calendar.MONTH, Calendar.DECEMBER);
		puzzleTime.set(Calendar.DATE, day);
		puzzleTime.set(Calendar.HOUR_OF_DAY, 0);
		puzzleTime.set(Calendar.MINUTE, 0);
		puzzleTime.set(Calendar.SECOND, 0);
		puzzleTime.set(Calendar.MILLISECOND, 0);

		_part1Time = unixPart1Time.getTimeInMillis() - puzzleTime.getTimeInMillis();
		_part2Time = unixPart2Time.getTimeInMillis() - puzzleTime.getTimeInMillis();
	}

	/**
	 * Converts milliseconds into a string timestamp.
	 */
	public static String formatTime(long time) {
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

		return (buffer.toString());
	}

	@Override
	public int compareTo(PuzzleTime o) {
		int compare = Long.valueOf(getPart2Time()).compareTo(Long.valueOf(o.getPart2Time()));
		// Exact timestamp ties
		if (compare == 0) {
			compare = getName().split(" ")[1].compareTo(o.getName().split(" ")[1]);
		}
		return (compare);
	}

	/**
	 * Returns true if the puzzle part was completed in the same year.
	 */
	public boolean completedInYear(Part part) {
		return (part == Part.ONE ? getYear() == getYearPart1Completed() : getYear() == getYearPart2Completed());
	}

	/**
	 * Accessor for the year of this puzzle
	 */
	private int getYear() {
		return _year;
	}

	/**
	 * Accessor for the name of the player who solved the puzzle
	 */
	public String getName() {
		return _name;
	}


	/**
	 * Accessor for the year part 1 was completed
	 */
	public int getYearPart1Completed() {
		return _yearPart1Completed;
	}

	/**
	 * Accessor for the year part 2 was completed
	 */
	private int getYearPart2Completed() {
		return _yearPart2Completed;
	}

	/**
	 * Accessor for the time part 1 was completed (after its release) in milliseconds
	 */
	public long getPart1Time() {
		return _part1Time;
	}

	/**
	 * Accessor for the time the whole puzzle was completed (after its release) in milliseconds
	 */
	public long getPart2Time() {
		return _part2Time;
	}
}