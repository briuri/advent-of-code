package buri.aoc.y18.d04;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Data class for single observation about guard duty.
 * 
 * Timestamps are written using year-month-day hour:minute format. The guard falling asleep or waking up is always the
 * one whose shift most recently started. Because all asleep/awake times are during the midnight hour (00:00 - 00:59),
 * only the minute portion (00 - 59) is relevant for those events.
 * 
 * @author Brian Uri!
 */
public class LogEntry implements Comparable {

	private Date _date;
	private Integer _minute;
	private String _observation;

	private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	/**
	 * Constructor
	 * 
	 * [1518-04-27 23:50] Guard #661 begins shift
	 * [1518-08-29 00:58] wakes up
	 * [1518-09-26 00:48] falls asleep
	 */
	public LogEntry(String input) {
		String[] tokens = input.split("] ");
		String rawDate = tokens[0].substring(1, tokens[0].length());
		try {
			_date = FORMAT.parse(rawDate);
		}
		catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}

		_minute = Integer.valueOf(rawDate.split(":")[1]);
		_observation = tokens[1];
	}

	/**
	 * Returns the id of the guard in this input, or -1 if none is listed.
	 */
	public Integer getId() {
		Integer index = getObservation().indexOf("#");
		if (index != -1) {
			Integer nextIndex = getObservation().indexOf(" ", index);
			return (Integer.valueOf(getObservation().substring(index + 1, nextIndex)));
		}
		return (-1);
	}

	@Override
	public int compareTo(Object o) {
		Date date = ((LogEntry) o).getDate();
		return (getDate().compareTo(date));
	}

	/**
	 * Returns true if the observation is about going on duty.
	 */
	public boolean isOnDuty() {
		return (getObservation().startsWith("Guard"));
	}
	
	/**
	 * Returns true if the observation is about falling asleep.
	 */
	public boolean isSleeping() {
		return (getObservation().startsWith("falls"));
	}

	/**
	 * Returns true if the observation is about waking up.
	 */
	public boolean isWaking() {
		return (getObservation().startsWith("wakes"));
	}

	/**
	 * Accessor for the date
	 */
	public Date getDate() {
		return _date;
	}

	/**
	 * Accessor for the minute from the date
	 */
	public Integer getMinute() {
		return (_minute);
	}

	/**
	 * Accessor for the observation
	 */
	public String getObservation() {
		return _observation;
	}
}