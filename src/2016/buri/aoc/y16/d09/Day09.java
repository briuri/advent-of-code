package buri.aoc.y16.d09;

import buri.aoc.Part;
import buri.aoc.Puzzle;

/**
 * Day 9: Explosives in Cyberspace
 * 
 * @author Brian Uri!
 */
public class Day09 extends Puzzle {

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
	 * QUESTION
	 */
	public static int getResult(Part part, String input) {
		if (part == Part.ONE) {
			return (decompress(input));
		}
		return (0);
	}
	
	/**
	 * The format compresses a sequence of characters. Whitespace is ignored. To indicate that some sequence should be
	 * repeated, a marker is added to the file, like (10x2). To decompress this marker, take the subsequent 10
	 * characters and repeat them 2 times. Then, continue reading the file after the repeated data. The marker itself is
	 * not included in the decompressed output.
	 */
	public static int decompress(String input) {
		StringBuffer buffer = new StringBuffer();
		int current = 0;
		while (true) {
			int openMarker = input.indexOf('(', current);
			if (openMarker != -1) {
				buffer.append(input.substring(current, openMarker));
				int closeMarker = input.indexOf(")", openMarker);
				String[] marker = input.substring(openMarker + 1, closeMarker).split("x");
				int length = Integer.valueOf(marker[0]);
				int times = Integer.valueOf(marker[1]);
				String repeat = input.substring(closeMarker + 1, closeMarker + 1 + length);
				for (int i = 0; i < times; i++) {
					buffer.append(repeat);
				}
				current = closeMarker + 1 + length;
			}
			else {
				buffer.append(input.substring(current, input.length()));
				break;
			}
		}
		return (buffer.length());
	}
}