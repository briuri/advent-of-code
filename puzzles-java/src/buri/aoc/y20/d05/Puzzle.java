package buri.aoc.y20.d05;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 05: Binary Boarding
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(820, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(838, result);
	}

	@Test
	public void testPart2Puzzle() {
		// 715
		int result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(714, result);
	}

	/**
	 * Part 1:
	 * What is the highest seat ID on a boarding pass?
	 *
	 * Part 2:
	 * What is the ID of your seat?
	 */
	public static int getResult(Part part, List<String> input) {
		List<Pass> list = new ArrayList<>();
		for (String line : input) {
			list.add(new Pass(line));
		}

		List<Integer> seats = new ArrayList<>();
		for (Pass pass : list) {
			seats.add(pass.getId());
		}
		Collections.sort(seats);
		if (part == Part.ONE) {
			return (seats.get(seats.size() - 1));
		}

		for (int i = 1; i < seats.size(); i++) {
			if (seats.get(i) != seats.get(i - 1) + 1) {
				return (seats.get(i) - 1);
			}
		}
		throw new RuntimeException("Plane is full.");
	}
}