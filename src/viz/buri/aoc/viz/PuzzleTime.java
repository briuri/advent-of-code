package buri.aoc.viz;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

/**
 * Data class for 1 completion record in a daily puzzle.
 * 
 * @author Brian Uri!
 */
public class PuzzleTime implements Comparable {
	private String _event;
	private int _day;

	private String _name;
	private Date _timestamp;

	/**
	 * Constructor
	 */
	public PuzzleTime(String event, int day, String name, long timestamp) {
		_event = event;
		_day = day;

		_name = name;
		_timestamp = Date.from(Instant.ofEpochSecond(timestamp));
	}

	/**
	 * Converts timestamp into "time after midnight on day of puzzle" (01:35:40).
	 * Adds 24 hours for completion times on later day (25:35:40).
	 * Returns text for puzzles completed outside of the competition (in 2019).
	 * Formats with HTML unless the time will be used for median calculations.
	 */
	public String getPrettyTime(boolean forMedianCalculation) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getTimestamp());
		if (Integer.valueOf(getEvent()) != calendar.get(Calendar.YEAR)) {
			return (forMedianCalculation ? "" : "<i>(in " + calendar.get(Calendar.YEAR) + ")</i>");
		}

		StringBuffer buffer = new StringBuffer(getTimestamp().toString().substring(11, 19));
		int overflow = calendar.get(Calendar.DAY_OF_MONTH) - getDay();
		if (overflow > 0) {
			int hours = Integer.valueOf(buffer.substring(0, 2)) + (24 * overflow);
			buffer.replace(0, 2, String.valueOf(hours));
		}
		if (buffer.length() == 8 && !forMedianCalculation) {
			buffer.insert(0, "&nbsp;");
		}
		return (buffer.toString());
	}

	@Override
	public int compareTo(Object o) {
		return (getTimestamp().compareTo(((PuzzleTime) o).getTimestamp()));
	}

	/**
	 * Accessor for the event containing this puzzle (a 4-digit year)
	 */
	private String getEvent() {
		return _event;
	}

	/**
	 * Accessor for the day of the puzzle (1 - 25)
	 */
	private int getDay() {
		return _day;
	}

	/**
	 * Accessor for the name of the player who solved the puzzle
	 */
	public String getName() {
		return _name;
	}

	/**
	 * Accessor for the time the puzzle was completed
	 */
	private Date getTimestamp() {
		return _timestamp;
	}
}