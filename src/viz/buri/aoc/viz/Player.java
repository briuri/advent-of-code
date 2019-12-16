package buri.aoc.viz;

import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Ancillary player data, read from players.json.
 * 
 * @author Brian Uri!
 */
public class Player {
	private String _name;
	private String _alternateName;
	private String _division;
	private int _globalCount;

	/**
	 * Constructor
	 */
	public Player(ObjectNode player) {
		_name = player.get("name").asText();
		_alternateName = player.get("alt").asText("");
		_division = player.get("division").asText("");
		_globalCount = player.get("globalCount").asInt(0);
	}

	/**
	 * Accessor for the player's name
	 */
	public String getName() {
		return _name;
	}

	/**
	 * Accessor for the alternate name
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

	/**
	 * Accessor for the globalCount
	 */
	public int getGlobalCount() {
		return _globalCount;
	}	
}
