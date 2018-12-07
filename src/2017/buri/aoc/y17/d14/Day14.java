package buri.aoc.y17.d14;

import buri.aoc.Part;
import buri.aoc.Puzzle;

/**
 * The disk in question consists of a 128x128 grid; each square of the grid is either free or used. On this disk, the
 * state of the grid is tracked by the bits in a sequence of knot hashes.
 * 
 * A total of 128 knot hashes are calculated, each corresponding to a single row in the grid; each hash contains 128
 * bits which correspond to individual grid squares. Each bit of a hash indicates whether that square is free (0) or
 * used (1).
 * 
 * The hash inputs are a key string (your puzzle input), a dash, and a number from 0 to 127 corresponding to the row.
 * For example, if your key string were flqrgnkx, then the first row would be given by the bits of the knot hash of
 * flqrgnkx-0, the second row from the bits of the knot hash of flqrgnkx-1, and so on until the last row, flqrgnkx-127.
 * 
 * The output of a knot hash is traditionally represented by 32 hexadecimal digits; each of these digits correspond to 4
 * bits, for a total of 4 * 32 = 128 bits. To convert to bits, turn each hexadecimal digit to its equivalent binary
 * value, high-bit first: 0 becomes 0000, 1 becomes 0001, e becomes 1110, f becomes 1111, and so on; a hash that begins
 * with a0c2017... in hexadecimal would begin with 10100000110000100000000101110000... in binary.
 * 
 * @author Brian Uri!
 */
public class Day14 extends Puzzle {
	
	/**
	 * Part 1:
	 * Given your actual key string, how many squares are used?
	 */
	public static int getResult(Part part, String input) {
		// TODO
		if (part == Part.ONE) {
			return (0);
		}
		return (0);
	}
}