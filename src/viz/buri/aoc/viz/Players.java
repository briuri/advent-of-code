package buri.aoc.viz;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Ancillary player data, read from players.json.
 * 
 * @author Brian Uri!
 */
public class Players {
	private Map<String, String> _alternateNames;
	private Map<String, String> _divisions;
	private Map<String, Integer> _globalCounts;

	/**
	 * Constructor
	 */
	public Players() {
		_alternateNames = new HashMap<>();
		_divisions = new HashMap<>();
		_globalCounts = new HashMap<>();
	}

	/**
	 * Adds a player record.
	 */
	public void add(ObjectNode player) {
		String name = player.get("name").asText();
		if (player.get("alt") != null) {
			getAlternateNames().put(name, player.get("alt").asText());
		}
		if (player.get("division") != null) {
			getDivisions().put(name, player.get("division").asText());
		}
		if (player.get("globalCount") != null) {
			getGlobalCounts().put(name, player.get("globalCount").asInt());
		}
	}

	/**
	 * Looks up an alternate name for a player.
	 */
	public String getAlternateName(String name) {
		return (getAlternateNames().getOrDefault(name, name));
	}

	/**
	 * Looks up a division for a player.
	 */
	public String getDivision(String name) {
		return (getDivisions().getOrDefault(name, ""));
	}

	/**
	 * Looks up the number of global ranks for a player.
	 */
	public int getGlobalCount(String name) {
		return (getGlobalCounts().getOrDefault(name, 0));
	}

	/**
	 * Accessor for the alternateNames map
	 */
	private Map<String, String> getAlternateNames() {
		return _alternateNames;
	}

	/**
	 * Accessor for the divisions map
	 */
	private Map<String, String> getDivisions() {
		return _divisions;
	}

	/**
	 * Accessor for the globalCounts map
	 */
	private Map<String, Integer> getGlobalCounts() {
		return _globalCounts;
	}
}