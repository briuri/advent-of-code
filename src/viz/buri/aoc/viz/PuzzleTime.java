package buri.aoc.viz;

import java.util.Calendar;

/**
 * Data class for 1 completion record in a daily puzzle.
 * 
 * @author Brian Uri!
 */
public class PuzzleTime implements Comparable<PuzzleTime> {
	private int _year;

	private String _name;
	private int _yearCompleted;
	private long _timeCompleted;	

	/**
	 * Constructor
	 */
	public PuzzleTime(int year, int day, String name, long unixTime) {
		_year = year;
		_name = name;
		
		Calendar unixTimeCompleted = Calendar.getInstance();
		unixTimeCompleted.setTimeInMillis(unixTime * 1000L);
		_yearCompleted = unixTimeCompleted.get(Calendar.YEAR);
		
		Calendar puzzleTime = Calendar.getInstance();
		puzzleTime.set(Calendar.YEAR, year);
		puzzleTime.set(Calendar.MONTH, Calendar.DECEMBER);
		puzzleTime.set(Calendar.DATE, day);
		puzzleTime.set(Calendar.HOUR_OF_DAY, 0);
		puzzleTime.set(Calendar.MINUTE, 0);
		puzzleTime.set(Calendar.SECOND, 0);
		puzzleTime.set(Calendar.MILLISECOND, 0);
		_timeCompleted = unixTimeCompleted.getTimeInMillis() - puzzleTime.getTimeInMillis();
	}
	
	/**
	 * Converts milliseconds into a string timestamp.
	 */
	public static String formatTime(long time) {
		StringBuffer buffer = new StringBuffer();
		
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
	
	/**
	 * Converts timestamp into "time after midnight on day of puzzle" (01:35:40).
	 * Adds 24 hours for completion times on later day (25:35:40).
	 * Returns text for puzzles completed outside of the competition (in 2019).
	 * Formats with HTML unless the time will be used for median calculations.
	 */
	public String getFormattedTime() {
		if (getYear() != getYearCompleted()) {
			return ("<i>(in " + getYearCompleted() + ")</i>");
		}
		return (formatTime(getTimeCompleted()));
	}

	@Override
	public int compareTo(PuzzleTime o) {
		return (Long.valueOf(getTimeCompleted()).compareTo(Long.valueOf(o.getTimeCompleted())));
	}

	/**
	 * Returns true if the puzzle was completed in the same year.
	 */
	public boolean completedInYear() {
		return (getYear() == getYearCompleted());
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
	 * Accessor for the year this puzzle was completed
	 */
	private int getYearCompleted() {
		return _yearCompleted;
	}
	
	/**
	 * Accessor for the time the puzzle was completed (after its release) in milliseconds
	 */
	public long getTimeCompleted() {
		return _timeCompleted;
	}
}