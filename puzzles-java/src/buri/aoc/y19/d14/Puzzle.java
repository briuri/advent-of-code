package buri.aoc.y19.d14;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 14: Space Stoichiometry
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(31, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
		assertEquals(165, Puzzle.getResult(Part.ONE, Puzzle.getInput(2)));
		assertEquals(13312, Puzzle.getResult(Part.ONE, Puzzle.getInput(3)));
		assertEquals(180697, Puzzle.getResult(Part.ONE, Puzzle.getInput(4)));
		assertEquals(2210736, Puzzle.getResult(Part.ONE, Puzzle.getInput(5)));
	}

	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(399063, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(82892753, Puzzle.getResult(Part.TWO, Puzzle.getInput(3)));
		assertEquals(5586022, Puzzle.getResult(Part.TWO, Puzzle.getInput(4)));
		assertEquals(460664, Puzzle.getResult(Part.TWO, Puzzle.getInput(5)));
	}

	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(4215654, result);
	}

	/**
	 * Part 1:
	 * Given the list of reactions in your puzzle input, what is the minimum amount of ORE required to produce exactly 1
	 * FUEL?
	 *
	 * Part 2:
	 * Given 1 trillion ORE, what is the maximum amount of FUEL you can produce?
	 */
	public static long getResult(Part part, List<String> input) {
		List<Reaction> rawReactions = new ArrayList<>();
		for (String line : input) {
			rawReactions.add(new Reaction(line));
		}

		Map<String, Reaction> reactions = new HashMap<>();
		for (Reaction reaction : rawReactions) {
			reactions.put(reaction.getOutput().getName(), reaction);
		}
		if (part == Part.ONE) {
			return (reduceToOre(reactions, null, "FUEL", 1L));
		}

		// Part TWO
		long targetOre = 1000000000000L;
		long lowerFuelLimit = 0L;
		long upperFuelLimit = 100000000L; // Just picked a reasonably high value.
		long midpoint = (lowerFuelLimit + upperFuelLimit) / 2;
		while (lowerFuelLimit + 1 < upperFuelLimit) {
			long ore = reduceToOre(reactions, null, "FUEL", midpoint);
			if (ore > targetOre) {
				upperFuelLimit = midpoint - 1;
			}
			else {
				lowerFuelLimit = midpoint;
			}
			midpoint = (lowerFuelLimit + upperFuelLimit) / 2;
		}
		return (midpoint);
	}

	/**
	 * Recursively break down a reaction until it can be solved with just ORE.
	 */
	protected static long reduceToOre(Map<String, Reaction> reactions, Map<String, Long> leftovers, String name,
		long amountNeeded) {
		if (leftovers == null) {
			leftovers = new HashMap<>();
		}

		// Base case
		if (name.equals("ORE")) {
			return (amountNeeded);
		}
		// Start by using any leftover from other reactions.
		amountNeeded = amountNeeded - leftovers.getOrDefault(name, 0L);

		// Calculate amount based on subreactions.
		Reaction reaction = reactions.get(name);
		long amountProduced = (long) Math.ceil(amountNeeded / (reaction.getOutput().getAmount() * 1.0));
		long leftover = (amountProduced * reaction.getOutput().getAmount()) - amountNeeded;
		leftovers.put(name, leftover);

		long subAmounts = 0;
		for (Chemical subInput : reaction.getInputs()) {
			subAmounts += reduceToOre(reactions, leftovers, subInput.getName(), subInput.getAmount() * amountProduced);
		}
		return (subAmounts);
	}
}