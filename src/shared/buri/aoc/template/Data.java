package buri.aoc.template;

/**
 * Data model FOR WHAT
 * 
 * @author Brian Uri!
 */
public class Data {

	private String _rawFormat = null;
	
	/**
	 * Constructor
	 * 
	 * HOW
	 */
	public Data(String input) {
		_rawFormat = input;
		String[] tokens = input.split(" ");		
	}
	
	@Override
	public String toString() {
		return (getRawFormat());
	}
	
	/**
	 * Accessor for the original format of this data.
	 */
	public String getRawFormat() {
		return (_rawFormat);
	}
	
}
