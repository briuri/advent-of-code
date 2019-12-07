package buri.aoc.viz;

/**
 * Ancillary player data.
 * 
 * @author Brian Uri!
 */
public class Player {

	private String _name;
	private String _alternateName;
	private String _division;
	
	public Player(String name, String alt, String division) {
		_name = name;
		_alternateName = alt;
		_division = division;
	}
	
	/**
	 * Accessor for the name
	 */
	public String getName() {
		return _name;
	}

	/**
	 * Accessor for the alternateName
	 */
	public String getAlternateName() {
		return _alternateName;
	}

	/**
	 * Accessor for the division
	 */
	public String getDivision() {
		return _division;
	}
}
