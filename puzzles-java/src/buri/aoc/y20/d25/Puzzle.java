package buri.aoc.y20.d25;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.math.BigInteger;
import java.util.List;

/**
 * Day 25: Combo Breaker
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(14897079L, 1, false);
		assertRun(10548634L, 0, true);
	}

	private static final BigInteger STARTING_VALUE = toBigInt(7);
	private static final BigInteger MODULUS = toBigInt(20201227);

	/**
	 * Part 1:
	 * What encryption key is the handshake trying to establish?
	 */
	protected long runLong(Part part, List<String> input) {
		long cardPki = Long.valueOf(input.get(0));
		long doorPki = Long.valueOf(input.get(1));
		// Set a bounds based on the input data to speed up execution.
		long upperBound = (cardPki == 5764801L ? 15L : 11900000L);

		// Naive method
//		long cardStart = STARTING_VALUE.longValue();
//		int cardLoop = 0;
//		while (true) {
//			cardLoop++;
//			if (transform(cardStart, cardLoop) == cardPki) {
//				break;
//			}
//		}
//		return (transform(doorPki, cardLoop));

		// cardPki = 7^cardLoop mod modulus
		// doorPki = 7^doorLoop mod modulus
		// key = doorPki^cardLoop mod modulus = cardPki^doorLoop mod modulus

		// Search in reverse from upper bound to save time (added post-solve to reduce test suite run time).
		for (long loopSize = upperBound; loopSize > 0; loopSize--) {
			BigInteger exponent = toBigInt(loopSize);
			if (STARTING_VALUE.modPow(exponent, MODULUS).longValue() == doorPki) {
				BigInteger base = toBigInt(cardPki);
				return (base.modPow(exponent, MODULUS).longValue());
			}
		}
		throw new RuntimeException("Could not find exponent in provided bounds.");
	}

	/**
	 * Runs the transformation algorithm over a subjectNumber in a loop.
	 *
	 * value = subjectNumber^loopSize mod modulus
	 */
	protected static long transform(long subjectNumber, long loopSize) {
		long value = 1;
		for (long i = 0; i < loopSize; i++) {
			value = value * subjectNumber;
			value = value % MODULUS.longValue();
		}
		return (value);
	}
}