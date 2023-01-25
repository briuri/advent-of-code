package buri.aoc.y17.d24;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;

/**
 * Day 24: Electromagnetic Moat
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * What is the strength of the strongest bridge you can make with the components you have available?
	 *
	 * Part 2:
	 * What is the strength of the longest bridge you can make?
	 */
	public static int getResult(Part part, List<String> input) {
		PieceBag bag = new PieceBag(input);
		List<Piece> bridge = new ArrayList<>();
		Map<Integer, List<Integer>> lengthsToScores = new HashMap<>();

		buildBridge(bridge, bag, lengthsToScores, 0);

		if (part == Part.ONE) {
			int maxScore = 0;
			for (List<Integer> scores : lengthsToScores.values()) {
				maxScore = Math.max(maxScore, Collections.max(scores));
			}
			return (maxScore);
		}

		// Part TWO
		int maxLength = Collections.max(lengthsToScores.keySet());
		return (Collections.max(lengthsToScores.get(maxLength)));
	}

	/**
	 * Recursive method to build a bridge, starting at the given port.
	 */
	private static void buildBridge(List<Piece> bridge, PieceBag bag, Map<Integer, List<Integer>> lengthsToScores,
		int port) {
		if (!bag.getAvailablePieces(port).isEmpty()) {
			int startingBridgeSize = bridge.size();
			for (Piece piece : bag.getAvailablePieces(port)) {
				// Add the current piece to the bridge.
				bridge.add(piece);
				bag.remove(piece);

				// Recursively consider next piece depth-first.
				buildBridge(bridge, bag, lengthsToScores, piece.getOtherPort(port));

				// Clean up for next attempt.
				List<Piece> trailingPieces = bridge.subList(startingBridgeSize, bridge.size());
				bag.add(trailingPieces);
				bridge.removeAll(trailingPieces);
			}
		}
		else {
			if (lengthsToScores.get(bridge.size()) == null) {
				lengthsToScores.put(bridge.size(), new ArrayList<>());
			}
			lengthsToScores.get(bridge.size()).add(getScore(bridge));
		}
	}

	/**
	 * Calculates the total score of the bridge.
	 */
	private static int getScore(List<Piece> bridge) {
		int score = 0;
		for (Piece piece : bridge) {
			score += piece.getScore();
		}
		return (score);
	}
}