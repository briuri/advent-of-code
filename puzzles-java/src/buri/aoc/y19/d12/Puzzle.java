package buri.aoc.y19.d12;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 12: The N-Body Problem
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(179, Puzzle.getResult(Part.ONE, Puzzle.getInput(1), 10));
		assertEquals(1940, Puzzle.getResult(Part.ONE, Puzzle.getInput(2), 100));
	}

	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0), 1000);
		toConsole(result);
		assertEquals(8362, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(2772, Puzzle.getResult(Part.TWO, Puzzle.getInput(1), 0));
		assertEquals(4686774924L, Puzzle.getResult(Part.TWO, Puzzle.getInput(2), 0));
	}

	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0), 0);
		toConsole(result);
		assertEquals(478373365921244L, result);
	}

	/**
	 * Part 1:
	 * What is the total energy in the system after simulating the moons given in your scan for 1000 steps?
	 *
	 * Part 2:
	 * How many steps does it take to reach the first state that exactly matches a previous state?
	 */
	public static long getResult(Part part, List<String> input, int maxSteps) {
		if (part == Part.ONE) {
			List<Moon> moons = loadMoons(input);
			for (int i = 0; i < maxSteps; i++) {
				adjustMoons(moons);
			}
			long totalEnergy = 0;
			for (Moon moon : moons) {
				totalEnergy += moon.getEnergy();
			}
			return (totalEnergy);
		}

		// Part TWO
		Set<String> statesX = new HashSet<>();
		long repeatX = 0;
		Set<String> statesY = new HashSet<>();
		long repeatY = 0;
		Set<String> statesZ = new HashSet<>();
		long repeatZ = 0;

		List<Moon> moons = loadMoons(input);
		int steps = 0;
		StringBuffer state = new StringBuffer();
		while (true) {
			if (repeatX == 0) {
				state.delete(0, state.length());
				for (Moon moon : moons) {
					state.append(moon.getXState());
				}
				if (statesX.contains(state.toString())) {
					repeatX = steps;
				}
				statesX.add(state.toString());
			}
			if (repeatY == 0) {
				state.delete(0, state.length());
				for (Moon moon : moons) {
					state.append(moon.getYState());
				}
				if (statesY.contains(state.toString())) {
					repeatY = steps;
				}
				statesY.add(state.toString());
			}
			if (repeatZ == 0) {
				state.delete(0, state.length());
				for (Moon moon : moons) {
					state.append(moon.getZState());
				}
				if (statesZ.contains(state.toString())) {
					repeatZ = steps;
				}
				statesZ.add(state.toString());
			}
			if (repeatX > 0 && repeatY > 0 && repeatZ > 0) {
				break;
			}
			adjustMoons(moons);
			steps++;
		}
		return (getLCM(getLCM(repeatX, repeatY), repeatZ));
	}

	/**
	 * Converts the input into moons.
	 */
	private static List<Moon> loadMoons(List<String> input) {
		List<Moon> moons = new ArrayList<>();
		for (String line : input) {
			moons.add(new Moon(line));
		}
		return (moons);
	}

	/**
	 * Applies gravity across all moons then moves them.
	 */
	private static void adjustMoons(List<Moon> moons) {
		for (Moon moon1 : moons) {
			for (Moon moon2 : moons) {
				if (moon1 == moon2) {
					continue;
				}
				moon1.applyGravity(moon2);
			}
		}
		for (Moon moon : moons) {
			moon.move();
		}
	}
}