package buri.aoc.viz;

import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Metadata for each puzzle, read from puzzles.json.
 *
 * @author Brian Uri!
 */
public class Puzzle {
	private final String _title;
	private int _globalPart1Count = 0;
	private int _globalPart2Count = 0;

	/**
	 * Constructor
	 */
	public Puzzle(ObjectNode puzzleNode) {
		_title = puzzleNode.get("title").asText();
		if (puzzleNode.get("globalCount") != null) {
			String[] tokens = puzzleNode.get("globalCount").asText().split(",");
			_globalPart1Count = Integer.parseInt(tokens[0]);
			_globalPart2Count = Integer.parseInt(tokens[1]);
		}
	}

	/**
	 * Accessor for the title
	 */
	public String getTitle() {
		return _title;
	}

	/**
	 * Accessor for the number of Part 1 spots on the global leaderboard
	 */
	public int getGlobalPart1Count() {
		return _globalPart1Count;
	}

	/**
	 * Accessor for the number of Part 2 spots on the global leaderboard
	 */
	public int getGlobalPart2Count() {
		return _globalPart2Count;
	}
}