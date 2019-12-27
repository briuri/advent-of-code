package buri.aoc.y17.d11;

import java.util.ArrayList;
import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 11: Hex Ed
 * 
 * @author Brian Uri!
 */
public class Day11 extends BasePuzzle {

	/**
	 * Returns input file as a list of directions.
	 */
	public static List<String> getInput(int fileIndex) {
		List<String> data = new ArrayList<>();
		for (String rawData : readFile("2017/11", fileIndex).get(0).split(",")) {
			data.add(rawData);
		}
		return (data);
	}

	/**
	 * Using cube coordinates as described at:
	 * https://www.redblobgames.com/grids/hexagons/
	 * 
	 * Part 1:
	 * Starting where he started, you need to determine the fewest number of steps required to reach him.
	 * 
	 * Part 2:
	 * How many steps away is the furthest he ever got from his starting position?
	 */
	public static int getResult(Part part, List<String> input) {
		int x = 0;
		int y = 0;
		int z = 0;
		int max = 0;
		for (String step : input) {
			if (step.equals("nw")) {
				x = x - 1;
				y = y + 1;
			}
			else if (step.equals("se")) {
				x = x + 1;
				y = y - 1;
			}
			else if (step.equals("n")) {
				y = y + 1;
				z = z - 1;
			}
			else if (step.equals("s")) {
				y = y - 1;
				z = z + 1;
			}
			else if (step.equals("ne")) {
				x = x + 1;
				z = z - 1;
			}
			else if (step.equals("sw")) {
				x = x - 1;
				z = z + 1;
			}
			max = Math.max(getDistance(x, y, z), max);
		}
		if (part == Part.ONE) {
			return (getDistance(x, y, z));
		}
		return (max);
	}

	/**
	 * Calculates the distance from the origin.
	 */
	private static int getDistance(int x, int y, int z) {
		return ((Math.abs(x) + Math.abs(y) + Math.abs(z)) / 2);
	}
}