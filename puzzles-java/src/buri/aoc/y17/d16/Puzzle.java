package buri.aoc.y17.d16;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Day 16: Permutation Promenade
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun("baedc", 1, false);
		assertRun("jcobhadfnmpkglie", 0, true);
	}
	@Test
	public void testPart2() {
		assertRun("pclhmengojfdkaib", 0, true);
	}

	/**
	 * Part 1:
	 * In what order are the programs standing after their dance?
	 *
	 * Part 2:
	 * In what order are the programs standing after their billion dances?
	 */
	protected String runString(Part part, List<String> input) {
		final int numDancers = (input.get(0).length() < 20 ? 5 : 16);
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