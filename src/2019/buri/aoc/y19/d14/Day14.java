package buri.aoc.y19.d14;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import buri.aoc.Part;
import buri.aoc.Puzzle;

/**
 * Day 14: Space Stoichiometry
 * 
 * @author Brian Uri!
 */
public class Day14 extends Puzzle {

	/**
	 * Returns the input file as a list of reactions
	 */
	public static List<Reaction> getInput(int fileIndex) {
		List<Reaction> list = new ArrayList<>();
		for (String input : readFile("2019/14", fileIndex)) {
			list.add(new Reaction(input));
		}
		return (list);
	}

	/**
	 * Part 1:
	 * Given the list of reactions in your puzzle input, what is the minimum amount of ORE required to produce exactly 1
	 * FUEL?
	 * 
	 * Part 2:
	 * Given 1 trillion ORE, what is the maximum amount of FUEL you can produce?
	 */
	public static long getResult(Part part, List<Reaction> rawReactions) {
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