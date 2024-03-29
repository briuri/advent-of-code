package buri.aoc.y19.d14;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Day 14: Space Stoichiometry
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(31L, 1, false);
		assertRun(165L, 2, false);
		assertRun(13312L, 3, false);
		assertRun(180697L, 4, false);
		assertRun(2210736L, 5, false);
		assertRun(399063L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(82892753L, 3, false);
		assertRun(5586022L, 4, false);
		assertRun(460664L, 5, false);
		assertRun(4215654L, 0, true);
	}

	/**
	 * Part 1:
	 * Given the list of reactions in your puzzle input, what is the minimum amount of ORE required to produce exactly 1
	 * FUEL?
	 *
	 * Part 2:
	 * Given 1 trillion ORE, what is the maximum amount of FUEL you can produce?
	 */
	protected long runLong(Part part, List<String> input) {
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