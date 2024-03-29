package buri.aoc.y21.d03;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.CharFrequency;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Day 03: Binary Diagnostic
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(198L, 1, false);
		assertRun(2972336L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(230L, 1, false);
		assertRun(3368358L, 0, true);
	}

	/**
	 * Part 1:
	 * What is the power consumption of the submarine?
	 *
	 * Part 2:
	 * What is the life support rating of the submarine?
	 */
	protected long runLong(Part part, List<String> input) {
		final int LENGTH = input.get(0).length();
		if (part == Part.ONE) {
			CharFrequency[] freqs = getFrequencies(input);
			StringBuilder gammaBuilder = new StringBuilder();
			StringBuilder epsilonBuilder = new StringBuilder();
			for (int i = 0; i < LENGTH; i++) {
				if (freqs[i].getFrequencyFor('1') > freqs[i].getFrequencyFor('0')) {
					gammaBuilder.append('1');
					epsilonBuilder.append('0');
				}
				else {
					gammaBuilder.append('0');
					epsilonBuilder.append('1');
				}
			}
			int gamma = Integer.parseInt(gammaBuilder.toString(), 2);
			int epsilon = Integer.parseInt(epsilonBuilder.toString(), 2);
			return (gamma * epsilon);
		}

		// Part TWO
		int oxygen = Integer.parseInt(reduceList(input, true), 2);
		int scrubber = Integer.parseInt(reduceList(input, false), 2);
		return (oxygen * scrubber);
	}

	/**
	 * Builds a character frequency for each index in the strings.
	 */
	protected static CharFrequency[] getFrequencies(List<String> input) {
		final int LENGTH = input.get(0).length();
		CharFrequency[] freqs = new CharFrequency[LENGTH];
		for (int i = 0; i < LENGTH; i++) {
			freqs[i] = new CharFrequency();
			for (String line : input) {
				freqs[i].add(line.charAt(i));
			}
		}
		return (freqs);
	}

	/**
	 * Cycles through the list of numbers and removes those that don't meet the criteria. Returns the final remaining
	 * binary number.
	 *
	 * @param input the starting list (a copy is made)
	 * @param keepMostCommon true for the oxygen calculation, false for the scrubber calculation.
	 */
	protected static String reduceList(List<String> input, boolean keepMostCommon) {
		final int LENGTH = input.get(0).length();
		List<String> list = new ArrayList<>(input);
		for (int i = 0; i < LENGTH; i++) {
			CharFrequency[] freqs = getFrequencies(list);
			int freq0 = freqs[i].getFrequencyFor('0');
			int freq1 = freqs[i].getFrequencyFor('1');
			for (Iterator<String> iter = list.iterator(); iter.hasNext();) {
				String line = iter.next();
				if (keepMostCommon) {
					if ((freq0 > freq1 && line.charAt(i) == '1') || (freq0 <= freq1 && line.charAt(i) == '0')) {
						iter.remove();
					}
				}
				else {
					if ((freq0 > freq1 && line.charAt(i) == '0') || (freq0 <= freq1 && line.charAt(i) == '1')) {
						iter.remove();
					}
				}
			}
			if (list.size() == 1) {
				break;
			}
		}
		return (list.get(0));
	}

}