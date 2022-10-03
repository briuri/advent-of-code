package buri.aoc.y21.d16;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 16: Packet Decoder
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * What do you get if you add up the version numbers in all packets?
	 *
	 * Part 2:
	 * What do you get if you evaluate the expression represented by your hexadecimal-encoded BITS transmission?
	 */
	public static long getResult(Part part, String input) {
		StringBuffer buffer = new StringBuffer();
		for (char hex : input.toCharArray()) {
			buffer.append(Packet.hexToBin(hex));
		}
		Packet packet = new Packet(buffer.toString(), 0);
		return (part == Part.ONE ? packet.getVersionSum() : packet.getResult());
	}
}