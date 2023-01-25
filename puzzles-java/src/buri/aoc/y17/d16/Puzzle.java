package buri.aoc.y17.d16;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 16: Permutation Promenade
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals("baedc", Puzzle.getResult(Part.ONE, 5, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		String result = Puzzle.getResult(Part.ONE, 16, Puzzle.getInput(0));
		toConsole(result);
		assertEquals("jcobhadfnmpkglie", result);
	}

	@Test
	public void testPart2Puzzle() {
		String result = Puzzle.getResult(Part.TWO, 16, Puzzle.getInput(0));
		toConsole(result);
		assertEquals("pclhmengojfdkaib", result);
	}

	/**
	 * Part 1:
	 * In what order are the programs standing after their dance?
	 *
	 * Part 2:
	 * In what order are the programs standing after their billion dances?
	 */
	public static String getResult(Part part, int numDancers, List<String> input) {
		input = Arrays.asList(input.get(0).split(","));
		Dancers dancers = new Dancers(numDancers);
		int iterations = (part == Part.ONE ? 1 : 1000000000);
		List<String> dances = new ArrayList<>();
		for (int i = 0; i < iterations; i++) {
			for (String move : input) {
				dancers.perform(move);
			}
			String dance = dancers.toString();

			// Found a repetition, so we can extrapolate to a billion.
			if (dances.contains(dance)) {
				break;
			}
			dances.add(dancers.toString());
		}
		if (part == Part.ONE) {
			return (dancers.toString());
		}
		// Part TWO
		String billionthDance = dances.get((iterations % dances.size() - 1));
		return (billionthDance);
	}
}