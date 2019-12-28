package buri.aoc.y16.d17;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 17: Two Steps Forward
 * 
 * @author Brian Uri!
 */
public class Day17 extends BasePuzzle {

	/**
	 * Part 1:
	 * Given your vault's passcode, what is the shortest path (the actual path, not just the length) to reach the vault?
	 * 
	 * Part 2:
	 * What is the length of the longest path that reaches the vault?
	 */
	public static String getResult(Part part, String passcode) {
		Position start = new Position(0, 0, passcode);

		// Generate breadth first mapping to find shortest paths to all points.
		Queue<Position> frontier = new ArrayDeque<>();
		frontier.add(start);
		Map<Position, Position> cameFrom = new HashMap<>();
		cameFrom.put(start, null);
		Position current = null;
		while (!frontier.isEmpty()) {
			current = frontier.remove();
			for (Position next : current.getTraversableNeighbors()) {
				if (cameFrom.get(next) == null) {
					frontier.add(next);
					cameFrom.put(next, current);
				}
				// Part ONE: Quit once we reach the final square the first time.
				if (part == Part.ONE && next.getX() == 3 && next.getY() == 3) {
					return (next.getPasscodeSoFar().substring(passcode.length()));
				}
			}
		}
		if (part == Part.ONE) {
			throw new RuntimeException("Never arrived at vault.");
		}

		// Part TWO: Find the longest path of every (3,3) arrival.
		String longestPasscodeSoFar = "";
		for (Position destination : cameFrom.keySet()) {
			if (destination.getX() == 3 && destination.getY() == 3
				&& destination.getPasscodeSoFar().length() > longestPasscodeSoFar.length()) {
				longestPasscodeSoFar = destination.getPasscodeSoFar();
			}
		}
		return (String.valueOf(longestPasscodeSoFar.substring(passcode.length()).length()));
	}

}