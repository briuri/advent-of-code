package buri.aoc.y16.d09;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 9: Explosives in Cyberspace
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(6, Puzzle.getResult(Part.ONE, "ADVENT"));
		assertEquals(7, Puzzle.getResult(Part.ONE, "A(1x5)BC"));
		assertEquals(9, Puzzle.getResult(Part.ONE, "(3x3)XYZ"));
		assertEquals(11, Puzzle.getResult(Part.ONE, "A(2x2)BCD(2x2)EFG"));
		assertEquals(6, Puzzle.getResult(Part.ONE, "(6x1)(1x3)A"));
		assertEquals(18, Puzzle.getResult(Part.ONE, "X(8x2)(3x3)ABCY"));
	}

	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0).get(0));
		toConsole(result);
		assertEquals(120765, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(9, Puzzle.getResult(Part.TWO, "(3x3)XYZ"));
		assertEquals(20, Puzzle.getResult(Part.TWO, "X(8x2)(3x3)ABCY"));
		assertEquals(241920, Puzzle.getResult(Part.TWO, "(27x12)(20x12)(13x14)(7x10)(1x12)A"));
		assertEquals(445, Puzzle.getResult(Part.TWO, "(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN"));
	}

	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0).get(0));
		toConsole(result);
		assertEquals(11658395076L, result);
	}

	/**
	 * Part 1:
	 * What is the decompressed length of the file (your puzzle input)?
	 *
	 * Part 2:
	 * What is the decompressed length of the file using this improved format?
	 */
	public static long getResult(Part part, String input) {
		return (decompress(part, input));
	}

	/**
	 * The format compresses a sequence of characters. Whitespace is ignored. To indicate that some sequence should be
	 * repeated, a marker is added to the file, like (10x2). To decompress this marker, take the subsequent 10
	 * characters and repeat them 2 times. Then, continue reading the file after the repeated data. The marker itself is
	 * not included in the decompressed output.
	 *
	 * In version two, the only difference is that markers within decompressed data are decompressed.
	 */
	private static long decompress(Part part, String input) {
		long totalLength = 0;
		int current = 0;
		while (true) {
			int openMarker = input.indexOf('(', current);
			if (openMarker != -1) {
				// Parse the marker.
				int closeMarker = input.indexOf(")", openMarker);
				String[] marker = input.substring(openMarker + 1, closeMarker).split("x");
				int length = Integer.valueOf(marker[0]);
				int times = Integer.valueOf(marker[1]);

				// Calculate the length of the current segment.
				totalLength += openMarker - current;
				if (part == Part.ONE) {
					totalLength += (length * times);
				}
				else {
					String nestedInput = input.substring(closeMarker + 1, closeMarker + 1 + length);
					totalLength += decompress(part, nestedInput) * times;
				}

				// Increment to the next segment.
				current = closeMarker + 1 + length;
			}
			else {
				// No markers left -- add the rest of the string.
				totalLength += input.length() - current;
				break;
			}
		}
		return (totalLength);
	}
}