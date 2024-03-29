package buri.aoc.y20.d12;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.tuple.Pair;
import org.junit.Test;

import java.util.List;

/**
 * Day 12: Rain Risk
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(25L, 1, false);
		assertRun(2057L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(286L, 1, false);
		assertRun(71504L, 0, true);
	}

	private static final Pair<Integer> ORIGIN = new Pair<>(0, 0);

	/**
	 * Part 1:
	 * What is the Manhattan distance between that location and the ship's starting position?
	 *
	 * Part 2:
	 * What is the Manhattan distance between that location and the ship's starting position?
	 */
	protected long runLong(Part part, List<String> input) {
		Pair<Integer> ship = ORIGIN.copy();
		if (part == Part.ONE) {
			// LR: Turn ship in by that many degrees.
			// F: Move ship in current facing by the given value.
			// NSEW: Move in that direction by the given value.

			int facing = 0; // 0 = E, 90 = N, 180 = W, 270 = S
			for (String line : input) {
				char command = line.charAt(0);
				int distance = Integer.parseInt(line.substring(1));
				if (command == 'L' || command == 'R') {
					int orientation = (command == 'R' ? -1 : 1);
					facing = Math.floorMod(facing + (distance * orientation), 360);
				}
				if (command == 'F') {
					if (facing == 0) {
						command = 'E';
					}
					else if (facing == 90) {
						command = 'N';
					}
					else if (facing == 180) {
						command = 'W';
					}
					else if (facing == 270) {
						command = 'S';
					}
				}
				if (command == 'N' || command == 'S') {
					int orientation = (command == 'N' ? -1 : 1);
					ship.setY(ship.getY() + (distance * orientation));
				}
				if (command == 'E' || command == 'W') {
					int orientation = (command == 'W' ? -1 : 1);
					ship.setX(ship.getX() + (distance * orientation));
				}
			}
		}
		else {
			// LR: Rotate waypoint around ship in by that many degrees.
			// F: Move ship towards waypoint by the given value.
			// NSEW: Move waypoint in that direction by the given value.

			Pair<Integer> waypoint = new Pair<>(10, -1);
			for (String line : input) {
				char command = line.charAt(0);
				int distance = Integer.parseInt(line.substring(1));
				if (command == 'L' || command == 'R') {
					int xOrientation = (command == 'R' ? -1 : 1);
					int yOrientation = (command == 'L' ? -1 : 1);
					for (int i = 0; i < distance / 90; i++) {
						int newX = waypoint.getY() * xOrientation;
						int newY = waypoint.getX() * yOrientation;
						waypoint.setX(newX);
						waypoint.setY(newY);
					}
				}
				if (command == 'F') {
					ship.setX(ship.getX() + (waypoint.getX() * distance));
					ship.setY(ship.getY() + (waypoint.getY() * distance));
				}
				if (command == 'N' || command == 'S') {
					int orientation = (command == 'N' ? -1 : 1);
					waypoint.setY(waypoint.getY() + (distance * orientation));
				}
				if (command == 'E' || command == 'W') {
					int orientation = (command == 'W' ? -1 : 1);
					waypoint.setX(waypoint.getX() + (distance * orientation));
				}
			}
		}
		return (ship.getManhattanDistance(ORIGIN));
	}
}