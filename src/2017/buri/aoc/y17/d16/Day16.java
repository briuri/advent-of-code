package buri.aoc.y17.d16;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 16: Permutation Promenade
 * 
 * @author Brian Uri!
 */
public class Day16 extends BasePuzzle {

	/**
	 * Returns input file as a list of commands
	 */
	public static List<String> getInput(int fileIndex) {
		return (Arrays.asList(readFile(fileIndex).get(0).split(",")));
	}

	/**
	 * Part 1:
	 * In what order are the programs standing after their dance?
	 * 
	 * Part 2:
	 * In what order are the programs standing after their billion dances?
	 */
	public static String getResult(Part part, int numDancers, List<String> input) {
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