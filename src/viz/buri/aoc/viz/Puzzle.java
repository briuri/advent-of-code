package buri.aoc.viz;

import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Metadata for each puzzle, read from puzzles.json.
 * 
 * @author Brian Uri!
 */
public class Puzzle {
	private String _title;
	private int _globalCount;

	/**
	 * Constructor
	 */
	public Puzzle(ObjectNode puzzleNode) {
		_title = puzzleNode.get("title").asText();
		_globalCount = puzzleNode.get("globalCount").asInt();
	}

	/**
	 * Accessor for the title
	 */
	public String getTitle() {
		return _title;
	}

	/**
	 * Accessor for the number of spots on the global leaderboard
	 */
	public int getGlobalCount() {
		return _globalCount;
	}
}