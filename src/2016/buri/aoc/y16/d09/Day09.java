package buri.aoc.y16.d09;

import buri.aoc.Part;
import buri.aoc.BasePuzzle;

/**
 * Day 9: Explosives in Cyberspace
 * 
 * @author Brian Uri!
 */
public class Day09 extends BasePuzzle {

	/**
	 * Returns the input file unmodified.
	 */
	public static String getInput(int fileIndex) {
		return (readFile("2016/09", fileIndex).get(0));
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