package buri.aoc.viz.common;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Metadata for each puzzle, read from puzzles.json.
 *
 * @author Brian Uri!
 */
public class Puzzle {
	private final String _title;
	private final List<String> _globalNames = new ArrayList<>();

	/**
	 * Constructor
	 */
	public Puzzle(ObjectNode puzzleNode) {
		_title = puzzleNode.get("title").asText();
		if (puzzleNode.get("globalNames") != null) {
			for (JsonNode jsonNode : puzzleNode.get("globalNames")) {
				_globalNames.add(jsonNode.asText());
			}
		}
	}

	/**
	 * Accessor for the title
	 */
	public String getTitle() {
		return _title;
	}

	/**
	 * Accessor for the names of players who made the global leaderboard
	 */
	public List<String> getGlobalNames() {
		return _globalNames;
	}
}