package buri.aoc.y18.d25;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.data.tuple.Quad;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 25: Four-Dimensional Adventure
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(2, Puzzle.getResult(Puzzle.getInput(1)));
		assertEquals(1, Puzzle.getResult(Puzzle.getInput(2)));
		assertEquals(4, Puzzle.getResult(Puzzle.getInput(3)));
		assertEquals(3, Puzzle.getResult(Puzzle.getInput(4)));
		assertEquals(8, Puzzle.getResult(Puzzle.getInput(5)));
	}

	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Puzzle.getInput(0));
		toConsole(result);
		assertEquals(388, result);
	}

	/**
	 * Part 1:
	 * How many constellations are formed by the fixed points in spacetime?
	 */
	public static int getResult(List<String> input) {
		List<Quad> stars = new ArrayList<>();
		for (String line : input) {
			String[] tokens = line.trim().split(",");
			stars.add(new Quad(Integer.valueOf(tokens[0]), Integer.valueOf(tokens[1]), Integer.valueOf(tokens[2]),
				Integer.valueOf(tokens[3])));
		}

		List<Set<Quad>> constellations = new ArrayList<>();
		while (!stars.isEmpty()) {
			int remainingStars = stars.size();
			// Compare each star in each existing constellation to unadded stars.
			for (Set<Quad> constellation : constellations) {
				// Use a local set to avoid a concurrent modification exception when adding to constellation.
				Set<Quad> quadsToAdd = new HashSet<>();
				for (Quad star : constellation) {
					for (Iterator<Quad> iterator = stars.iterator(); iterator.hasNext();) {
						Quad unadded = iterator.next();
						if (star.getManhattanDistance(unadded) <= 3) {
							quadsToAdd.add(unadded);
							iterator.remove();
						}
					}
				}
				constellation.addAll(quadsToAdd);
			}

			// If no constellation found any new stars to add, create a new constellation with the first unadded star.
			if (remainingStars == stars.size()) {
				Set<Quad> constellation = new HashSet<>();
				constellation.add(stars.remove(0));
				constellations.add(constellation);
			}
		}

		// A constellation has 1 or more stars. I misread instructions initially, and thought 2 were required.
		return (constellations.size());
	}
}