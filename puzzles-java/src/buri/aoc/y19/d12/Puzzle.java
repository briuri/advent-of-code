package buri.aoc.y19.d12;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.PuzzleMath;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Day 12: The N-Body Problem
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(1940L, 1, false);
		assertRun(8362L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(4686774924L, 1, false);
		assertRun(478373365921244L, 0, true);
	}

	/**
	 * Part 1:
	 * What is the total energy in the system after simulating the moons given in your scan for 1000 steps?
	 *
	 * Part 2:
	 * How many steps does it take to reach the first state that exactly matches a previous state?
	 */
	protected long runLong(Part part, List<String> input) {
		if (part == Part.ONE) {
			int maxSteps = (input.get(0).startsWith("<x=-8") ? 100 : 1000);
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
		StringBuilder state = new StringBuilder();
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
		return (PuzzleMath.getLCM(PuzzleMath.getLCM(repeatX, repeatY), repeatZ));
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