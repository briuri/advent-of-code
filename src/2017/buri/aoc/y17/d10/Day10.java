package buri.aoc.y17.d10;

import java.util.ArrayList;
import java.util.List;

import buri.aoc.Part;
import buri.aoc.Puzzle;

/**
 * Day 10: Knot Hash
 * 
 * @author Brian Uri!
 */
public class Day10 extends Puzzle {

	/**
	 * Part 1:
	 * Returns input file as a list of integers.
	 * 
	 * Part 2:
	 * First, from now on, your input should be taken not as a list of numbers, but as a string of bytes instead. Unless
	 * otherwise specified, convert characters to bytes using their ASCII codes. This will allow you to handle arbitrary
	 * ASCII strings, and it also ensures that your input lengths are never larger than 255.
	 */
	public static List<Integer> getInput(Part part, int fileIndex) {
		List<Integer> data = new ArrayList<>();
		String rawData = readFile("2017/10", fileIndex).get(0);
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
		return (data);
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
	public static String getResult(Part part, int size, List<Integer> input) {
		if (part == Part.TWO) {
			input.add(17);
			input.add(31);
			input.add(73);
			input.add(47);
			input.add(23);
		}
		int numRounds = (part == Part.ONE ? 1 : 64);
		CircleList list = new CircleList(size);
		int currentPosition = 0;
		int skipSize = 0;
		for (int i = 0; i < numRounds; i++) {
			for (Integer length : input) {
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