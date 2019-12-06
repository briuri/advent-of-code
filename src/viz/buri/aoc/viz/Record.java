package buri.aoc.viz;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

/**
 * Data class for 1 completion record in a daily puzzle.
 * 
 * @author Brian Uri!
 */
public class Record implements Comparable {
	
	private String _name;
	private String _event;
	private int _day;
	private long _timestamp;
	private Date _date;
	
	/**
	 * Constructor
	 */
	public Record(String name, String event, int day, long timestamp) {
		_name = name;
		_event = event;
		_day = day;
		_timestamp = timestamp;
		_date = Date.from(Instant.ofEpochSecond(timestamp));
	}

	/**
	 * Converts UNIX timestamp into "time after midnight". Adds 24 hours for completion times on the next day.
	 * Returns a message for times in a different year (people who did the puzzles outside of the competition).
	 */
	public String getPrettyTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getDate());
		if (Integer.valueOf(getEvent()) != calendar.get(Calendar.YEAR)) {
			return ("<i>(in " + calendar.get(Calendar.YEAR) + ")</i>");
		}

		String prettyTime = getDate().toString().substring(11, 19);
		int overflow = calendar.get(Calendar.DAY_OF_MONTH) - getDay();
		if (overflow > 0) {
			int hours = Integer.valueOf(prettyTime.substring(0, 2)) + (24 * overflow);
			prettyTime = String.valueOf(hours) + prettyTime.substring(2, prettyTime.length());
		}
		if (prettyTime.length() == 8) {
			prettyTime = "&nbsp;" + prettyTime;
		}
		return (prettyTime);
	}
	
	/**
	 * Returns true if this completion happened during the event. False if in the next year.
	 */
	public boolean occurredInYear(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getDate());
		return (calendar.get(Calendar.YEAR) == year);
	}
		
	/**
	 * Acessor for the name of the participant
	 */
	public String getName() {
		return _name;
	}

	/**
	 * Accessor for the year
	 */
	public String getEvent() {
		return _event;
	}
	
	/**
	 * Accessor for the day of the puzzle
	 */
	public int getDay() {
		return _day;
	}
	
	/**
	 * Accessor for the solve time as a UNIX timestamp
	 */
	public long getTimestamp() {
		return _timestamp;
	}
	
	/**
	 * Accessor for the date
	 */
	public Date getDate() {
		return _date;
	}
	
	@Override
	public int compareTo(Object o) {
		if (getTimestamp() == ((Record) o).getTimestamp()) {
			return (0);
		}
		return (getTimestamp() > ((Record) o).getTimestamp() ? 1 : -1);
	}	
}