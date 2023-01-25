package buri.aoc.y19.d01;

import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;

/**
 * Day 01: The Tyranny of the Rocket Equation
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * What is the sum of the fuel requirements for all of the modules on your spacecraft?
	 *
	 * Part 2:
	 * What is the sum of the fuel requirements for all of the modules on your spacecraft when also taking into account
	 * the mass of the added fuel?
	 */
	public static int getResult(Part part, List<String> input) {
		List<Integer> values = convertStringsToInts(input);
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