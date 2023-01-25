package buri.aoc.viz.common;

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
public class Company {
	private final Map<String, String> _nicknames;
	private final String _divisionLabel;
	private final Map<String, String> _divisions;
	private final Map<String, Integer> _globalCounts;
	private final Set<String> _ineligible;
	private final List<String> _allDivisions;
	private final int _places;
	private final List<String> _exclusions;
	private final String _rules;

	/**
	 * Constructor
	 */
	public Company(String divisionLabel, List<String> allDivisions, int places, List<String> exclusions, String rules) {
		_nicknames = new HashMap<>();
		_divisionLabel = divisionLabel;
		_divisions = new HashMap<>();
		_globalCounts = new HashMap<>();
		_ineligible = new HashSet<>();
		_allDivisions = allDivisions;
		_exclusions = exclusions;
		_places = places;
		_rules = rules;
	}

	/**
	 * Adds a player record.
	 */
	public void addPlayer(ObjectNode player) {
		String username = player.get("username").asText();
		if (player.get("nickname") != null) {
			getNicknames().put(username, player.get("nickname").asText());
		}
		String nickname = getNicknames().getOrDefault(username, username);

		if (player.get("division") != null) {
			getDivisions().put(nickname, player.get("division").asText());
		}
		if (player.get("globalCount") != null) {
			getGlobalCounts().put(nickname, player.get("globalCount").asInt());
		}
		if (player.get("ineligible") != null && player.get("ineligible").asBoolean()) {
			getIneligible().add(nickname);
		}
	}

	/**
	 * Looks up a nickname for a player.
	 */
	public String getNicknameFor(String name) {
		return (getNicknames().getOrDefault(name, name));
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
	 * Accessor for the nicknames map
	 */
	private Map<String, String> getNicknames() {
		return _nicknames;
	}

	/**
	 * Accessor for the Portfolio/Division label
	 */
	public String getDivisionLabel() {
		return _divisionLabel;
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
	 * Accessor for a list of all divisions at the company
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
	 * Accessor for a list of names to exclude from the leaderboard
	 */
	public List<String> getExclusions() {
		return _exclusions;
	}

	/**
	 * Accessor for this year's rules.
	 */
	public String getRules() {
		return _rules;
	}
}