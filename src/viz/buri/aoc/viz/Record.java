package buri.aoc.viz;

import java.time.Instant;
import java.util.Date;

/**
 * Data class for 1 completion record in a daily puzzle.
 * 
 * @author Brian Uri!
 */
public class Record implements Comparable {
	
	private String _name;
	private long _timestamp;
	
	/**
	 * Constructor
	 * @param name
	 * @param timestamp
	 */
	public Record(String name, long timestamp) {
		_name = name;
		_timestamp = timestamp;
	}

	/**
	 * Converts UNIX timestamp into "time after midnight"
	 */
	public String getPrettyTime() {
		return (Date.from(Instant.ofEpochSecond(getTimestamp())).toString().substring(11, 19));
	}
	
	/**
	 * Acessor for the name of the participant
	 */
	public String getName() {
		return _name;
	}

	/**
	 * Accessor for the solve time as a UNIX timestamp
	 */
	public long getTimestamp() {
		return _timestamp;
	}

	@Override
	public int compareTo(Object o) {
		if (getTimestamp() == ((Record) o).getTimestamp()) {
			return (0);
		}
		return (getTimestamp() > ((Record) o).getTimestamp() ? 1 : -1);
	}	
}