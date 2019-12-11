package buri.aoc.viz;

/**
 * Data class for a participant's median time.
 * 
 * @author Brian Uri!
 */
public class MedianRecord implements Comparable {
	
	private String _name;
	private String _medianTime;
	private int _first;
	private int _second;
	private int _third;
	
	/**
	 * Constructor
	 */
	public MedianRecord(String name, String medianTime) {
		_name = name;
		_medianTime = medianTime;
		_first = 0;
		_second = 0;
		_third = 0;
	}

	/**
	 * Acessor for the name of the participant
	 */
	public String getName() {
		return _name;
	}

	/**
	 * Accessor for the median time
	 */
	public String getMedianTime() {
		return _medianTime;
	}
	
	/**
	 * Returns true if this player has at least 1 medal.
	 */
	public boolean hasMedals() {
		return (getFirst() + getSecond() + getThird() > 0);
	}
	
	/**
	 * Accessor for the number of first place finishes
	 */
	public int getFirst() {
		return _first;
	}
	
	/**
	 * Accessor for the number of first place finishes
	 */
	public void addFirst() {
		_first += 1;
	}

	/**
	 * Accessor for the number of second place finishes
	 */
	public int getSecond() {
		return _second;
	}

	/**
	 * Accessor for the number of second place finishes
	 */
	public void addSecond() {
		_second += 1;
	}
	
	/**
	 * Accessor for the number of third place finishes
	 */
	public int getThird() {
		return _third;
	}

	/**
	 * Accessor for the number of third place finishes
	 */
	public void addThird() {
		_third += 1;
	}
	

	@Override
	public int compareTo(Object o) {
		return (getMedianTime().compareTo(((MedianRecord) o).getMedianTime()));
	}	
}