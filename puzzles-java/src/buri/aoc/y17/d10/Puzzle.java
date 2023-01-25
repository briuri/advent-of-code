package buri.aoc.y17.d10;

import java.util.ArrayList;
import java.util.List;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 10: Knot Hash
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals("12", Puzzle.getResult(Part.ONE, 5, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		String result = Puzzle.getResult(Part.ONE, 256, Puzzle.getInput(0));
		toConsole(result);
		assertEquals("6909", result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals("33efeb34ea91902bb2f59c9920caa6cd", Puzzle.getResult(Part.TWO, 256, Puzzle.getInput(2)));
		assertEquals("3efbe78a8d82f29979031a4aa0b16a9d", Puzzle.getResult(Part.TWO, 256, Puzzle.getInput(3)));
		assertEquals("63960835bcdc130f0b66d7ff4f6a5a8e", Puzzle.getResult(Part.TWO, 256, Puzzle.getInput(4)));
	}

	@Test
	public void testPart2Puzzle() {
		String result = Puzzle.getResult(Part.TWO, 256, Puzzle.getInput(0));
		toConsole(result);
		assertEquals("9d5f4561367d379cfbf04f8c471c0095", result);
	}

	/**
	 * Part 1:
	 * Begin with a list of numbers from 0 to 255, a current position which begins at 0 (the first element in the list),
	 * a skip size (which starts at 0), and a sequence of lengths (your puzzle input). Then, for each length:
	 *
	 * - Reverse the order of that length of elements in the list, starting with the element at the current position.
	 * - Move the current position forward by that length plus the skip size.
	 * - Increase the skip size by one.
	 *
	 * The list is circular; if the current position and the length try to reverse elements beyond the end of the list,
	 * the operation reverses using as many extra elements as it needs from the front of the list. If the current
	 * position moves past the end of the list, it wraps around to the front. Lengths larger than the size of the list
	 * are invalid.
	 *
	 * Part 2:
	 * Once you have determined the sequence of lengths to use, add the following lengths to the end of the sequence:
	 * 17, 31, 73, 47, 23.
	 *
	 * Run a total of 64 rounds. Once the rounds are complete, you will be left with the numbers from 0 to 255 in some
	 * order, called the sparse hash. Reduce these to a list of only 16 numbers called the dense hash. To do this, use
	 * numeric bitwise XOR to combine each consecutive block of 16 numbers in the sparse hash (there are 16 such blocks
	 * in a list of 256 numbers).
	 *
	 * Finally, the standard way to represent a Knot Hash is as a single hexadecimal string. What is the Knot Hash of
	 * your puzzle input?
	 */
	public static String getResult(Part part, int size, List<String> input) {
		List<Integer> data = new ArrayList<>();
		String rawData = input.get(0);
		if (part == Part.ONE) {
			for (String token : rawData.split(",")) {
				data.add(Integer.valueOf(token));
			}
		}
		else {
			for (int i = 0; i < rawData.length(); i++) {
				data.add((int) rawData.charAt(i));
			}
		}
		if (part == Part.TWO) {
			data.add(17);
			data.add(31);
			data.add(73);
			data.add(47);
			data.add(23);
		}

		int numRounds = (part == Part.ONE ? 1 : 64);
		CircleList list = new CircleList(size);
		int currentPosition = 0;
		int skipSize = 0;
		for (int i = 0; i < numRounds; i++) {
			for (Integer length : data) {
				list.reverse(length, currentPosition);
				currentPosition = currentPosition + length + skipSize;
				skipSize++;
			}
		}
		if (part == Part.ONE) {
			return (String.valueOf(list.getResult()));
		}
		return (list.toHex());
	}
}