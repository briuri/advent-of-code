package buri.aoc.y18.d20;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 20: A Regular Map
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

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