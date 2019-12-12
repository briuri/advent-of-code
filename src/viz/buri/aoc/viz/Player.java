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

	/**
	 * Constructor
	 */
	public Player(ObjectNode player) {
		_name = player.get("name").asText();
		_alternateName = player.get("alt").asText("");
		_division = player.get("division").asText("");
	}

	/**
	 * Accessor for the player's name
	 */
	public String getName() {
		return _name;
	}

	/**
	 * Accessor for the alternate name, if available
	 */
	public String getAlternateName() {
		return _alternateName;
	}

	/**
	 * Accessor for the division, if available
	 */
	public String getDivision() {
		return _division;
	}
}
