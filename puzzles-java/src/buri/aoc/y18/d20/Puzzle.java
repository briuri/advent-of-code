package buri.aoc.y18.d20;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 20: A Regular Map
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(3, Puzzle.getResult(Part.ONE, Puzzle.getInput(1).get(0)));
		assertEquals(10, Puzzle.getResult(Part.ONE, Puzzle.getInput(2).get(0)));
		assertEquals(18, Puzzle.getResult(Part.ONE, Puzzle.getInput(3).get(0)));
		assertEquals(23, Puzzle.getResult(Part.ONE, Puzzle.getInput(4).get(0)));
		assertEquals(31, Puzzle.getResult(Part.ONE, Puzzle.getInput(5).get(0)));
	}

	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0).get(0));
		toConsole(result);
		assertEquals(3465, result);
	}

	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0).get(0));
		toConsole(result);
		assertEquals(7956, result);
	}

	/**
	 * Part 1:
	 * What is the largest number of doors you would be required to pass through to reach a room?
	 *
	 * Part 2:
	 * How many rooms have a shortest path from your current location that pass through at least 1000 doors?
	 */
	public static int getResult(Part part, String input) {
		RoomMap map = new RoomMap();
		map.explore(input);
		if (part == Part.ONE) {
			return (map.getMostDoors());
		}

		// Part TWO
		return (map.getRoomsWithDoors(1000));
	}
}