package buri.aoc.y16.d17;

import java.util.Map;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;
import buri.aoc.data.path.Pathfinder;

/**
 * Day 17: Two Steps Forward
 * 
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

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
		Map<Position, Position> cameFrom = Pathfinder.breadthFirstSearch(start, Position.STEP_STRATEGY);
		
		if (part == Part.ONE) {
			String shortestPasscode = null;
			for (Position destination : cameFrom.keySet()) {
				if (destination.getX() == 3 && destination.getY() == 3) {
					if (shortestPasscode == null || destination.getPasscodeSoFar().length() < shortestPasscode.length()) {
						shortestPasscode = destination.getPasscodeSoFar();
					}
				}
			}
			return (shortestPasscode.substring(passcode.length()));
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