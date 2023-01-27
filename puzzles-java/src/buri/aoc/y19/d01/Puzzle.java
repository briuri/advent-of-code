package buri.aoc.y19.d01;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.PuzzleMath;
import org.junit.Test;

import java.util.List;

/**
 * Day 01: The Tyranny of the Rocket Equation
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(3087896L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(4628989L, 0, true);
	}

	/**
	 * Part 1:
	 * What is the sum of the fuel requirements for all of the modules on your spacecraft?
	 *
	 * Part 2:
	 * What is the sum of the fuel requirements for all of the modules on your spacecraft when also taking into account
	 * the mass of the added fuel?
	 */
	protected long runLong(Part part, List<String> input) {
		List<Integer> values = PuzzleMath.toInts(input);
		int sum = 0;
		for (Integer mass : values) {
			sum += getFuel(part, mass);
		}
		return (sum);
	}

	/**
	 * Calculates the amount of fuel needed for some mass. Fuel required to launch a given module is based on its mass.
	 * Specifically, to find the fuel required for a module, take its mass, divide by three, round down, and subtract 2.
	 *
	 * In Part Two, the fuel itself also requires fuel.
	 */
	public static int getFuel(Part part, int mass) {
		int fuel = Math.max(mass / 3 - 2, 0);
		if (part == Part.TWO && fuel > 0) {
			fuel += getFuel(part, fuel);
		}
		return (fuel);
	}
}