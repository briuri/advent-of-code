package buri.aoc.viz;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Ancillary player and division data in a single year.
 *
 * @author Brian Uri!
 */
public class Novetta {
	private Map<String, String> _alternateNames;
	private Map<String, String> _divisions;
	private Map<String, Integer> _globalCounts;
	private Set<String> _ineligible;
	private List<String> _allDivisions;
	private int _places;
	private String _rules;

	/**
	 * Constructor
	 */
	public Novetta(List<String> allDivisions, int places, String rules) {
		_alternateNames = new HashMap<>();
		_divisions = new HashMap<>();
		_globalCounts = new HashMap<>();
		_ineligible = new HashSet<>();
		_allDivisions = allDivisions;
		_places = places;
		_rules = rules;
	}

	/**
	 * Adds a player record.
	 */
	public void addPlayer(ObjectNode player) {
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
		if (player.get("ineligible") != null && player.get("ineligible").asBoolean()) {
			getIneligible().add(name);
		}
	}

	/**
	 * Looks up an alternate name for a player.
	 */
	public String getAlternateNameFor(String name) {
		return (getAlternateNames().getOrDefault(name, name));
	}

	/**
	 * Looks up a division for a player.
	 */
	public String getDivisionFor(String name, boolean addParentheses) {
		String division = getDivisions().getOrDefault(name, "");
		if (addParentheses && division.length() > 0) {
			division = " (" + division + ")";
		}
		return (division);
	}

	/**
	 * Looks up the number of global ranks for a player.
	 */
	public int getGlobalCountFor(String name) {
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

	/**
	 * Accessor for the ineligible
	 */
	public Set<String> getIneligible() {
		return _ineligible;
	}

	/**
	 * Accessor for a list of all divisions at Novetta
	 */
	public List<String> getAllDivisions() {
		return _allDivisions;
	}

	/**
	 * Accessor for the number of places to show in the leaderboard.
	 */
	public int getPlaces() {
		return _places;
	}

	/**
	 * Accessor for this year's rules.
	 */
	public String getRules() {
		return _rules;
	}
}