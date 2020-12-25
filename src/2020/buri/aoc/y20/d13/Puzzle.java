package buri.aoc.y20.d13;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 13: Shuttle Search
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Returns the input file unmodified.
	 */
	public static List<String> getInput(int fileIndex) {
		return (readFile(fileIndex));
	}

	/**
	 * Part 1:
	 * What is the ID of the earliest bus you can take to the airport multiplied by the number of minutes you'll need to
	 * wait for that bus?
	 *
	 * Part 2:
	 * What is the earliest timestamp such that all of the listed bus IDs depart at offsets matching their positions in
	 * the list?
	 */
	public static long getResult(Part part, List<String> input) {
		String[] tokens = input.get(1).split(",");
		Map<Integer, Integer> indexesToIds = new HashMap<>();
		for (int i = 0; i < tokens.length; i++) {
			if (!tokens[i].equals("x")) {
				indexesToIds.put(i, Integer.valueOf(tokens[i]));
			}
		}

		if (part == Part.ONE) {
			int earliest = Integer.valueOf(input.get(0));
			Map<Integer, Integer> earliestBusTimes = new HashMap<>();
			for (Integer bus : indexesToIds.values()) {
				int next = bus * (earliest / bus + 1);
				earliestBusTimes.put(bus, next);
			}
			Map.Entry<Integer, Integer> min = getMin(earliestBusTimes);
			return (min.getKey() * (min.getValue() - earliest));
		}

		// Naive approach
		/*
		long increment = 1L;
		while (true) {
			long t = indexesToIds.get(0) * increment;
			boolean found = true;
			for (Map.Entry<Integer, Integer> entry : indexesToIds.entrySet()) {
				if ((t + entry.getKey()) % entry.getValue() != 0) {
					increment++;
					found = false;
					break;
				}
			}
			if (found) {
				return (t);
			}
		}
		*/

		// Create pairs of (number, remainder) using (id, id - index)
		// Cast as Strings to support BigInteger math
		Map<String, String> idsToRemainders = new HashMap<>();
		for (Integer index : indexesToIds.keySet()) {
			idsToRemainders.put(String.valueOf(indexesToIds.get(index)), String.valueOf(indexesToIds.get(index) - index));
		}
		return (getFirstTime(idsToRemainders));
	}

	/**
	 * Uses Chinese Remainder Theorem to get the timestamp of the first bus, such that each remaining bus leaves [index]
	 * minutes later.
	 *
	 * The Chinese remainder theorem states that if one knows the remainders of the Euclidean division of an integer n
	 * by several integers, then one can determine uniquely the remainder of the division of n by the product of these
	 * integers, under the condition that the divisors are pairwise coprime.
	 *
	 * https://en.wikipedia.org/wiki/Chinese_remainder_theorem
	 * https://crypto.stanford.edu/pbc/notes/numbertheory/crt.html
	 */
	protected static long getFirstTime(Map<String, String> idsToRemainders) {
		// "the product of these integers"
		BigInteger product = new BigInteger("1");
		for (String id : idsToRemainders.keySet()) {
			product = product.multiply(new BigInteger(id));
		}

		// n is the timestamp we want
		BigInteger n = new BigInteger("0");

		// We know the remainders of Euclidean division of n for each bus (remainderBus)
		for (Map.Entry<String, String> bus : idsToRemainders.entrySet()) {
			BigInteger idBus = new BigInteger(bus.getKey());
			BigInteger remainderBus = new BigInteger(bus.getValue());

			// a = product / idBus
			BigInteger a = product.divide(idBus);

			// n += remainderBus * a * (a^-1 mod id)
			n = n.add(remainderBus.multiply(a.multiply(a.modInverse(idBus))));
		}

		// n mod id
		return (n.mod(product).longValue());
	}
}