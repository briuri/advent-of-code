package buri.aoc.y16.d17;

import java.util.Map;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.path.Pathfinder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 17: Two Steps Forward
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals("DDRRRD", Puzzle.getResult(Part.ONE, "ihgpwlah"));
		assertEquals("DDUDRLRRUDRD", Puzzle.getResult(Part.ONE, "kglvqrro"));
		assertEquals("DRURDRUDDLLDLUURRDULRLDUUDDDRR", Puzzle.getResult(Part.ONE, "ulqzkmiv"));
	}

	@Test
	public void testPart1Puzzle() {
		String result = Puzzle.getResult(Part.ONE, "rrrbmfta");
		toConsole(result);
		assertEquals("RLRDRDUDDR", result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals("370", Puzzle.getResult(Part.TWO, "ihgpwlah"));
		assertEquals("492", Puzzle.getResult(Part.TWO, "kglvqrro"));
		assertEquals("830", Puzzle.getResult(Part.TWO, "ulqzkmiv"));
	}

	@Test
	public void testPart2Puzzle() {
		String result = Puzzle.getResult(Part.TWO, "rrrbmfta");
		toConsole(result);
		assertEquals("420", result);
	}

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