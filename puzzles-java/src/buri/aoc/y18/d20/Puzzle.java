package buri.aoc.y18.d20;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.List;

/**
 * Day 20: A Regular Map
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(3L, 1, false);
		assertRun(10L, 2, false);
		assertRun(18L, 3, false);
		assertRun(23L, 4, false);
		assertRun(31L, 5, false);
		assertRun(3465L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(7956L, 0, true);
	}

	/**
	 * Part 1:
	 * What is the largest number of doors you would be required to pass through to reach a room?
	 *
	 * Part 2:
	 * How many rooms have a shortest path from your current location that pass through at least 1000 doors?
	 */
	protected long runLong(Part part, List<String> input) {
		RoomMap map = new RoomMap();
		map.explore(input.get(0));
		if (part == Part.ONE) {
			return (map.getMostDoors());
		}

		// Part TWO
		return (map.getRoomsWithDoors(1000));
	}
}