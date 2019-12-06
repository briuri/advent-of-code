package buri.aoc.viz;

/**
 * Data class for a participant's median time.
 * 
 * @author Brian Uri!
 */
public class MedianRecord implements Comparable {
	
	private String _name;
	private String _medianTime;
	
	/**
	 * Constructor
	 */
	public MedianRecord(String name, String medianTime) {
		_name = name;
		_medianTime = medianTime;
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
	
	@Override
	public int compareTo(Object o) {
		return (getMedianTime().compareTo(((MedianRecord) o).getMedianTime()));
	}	
}