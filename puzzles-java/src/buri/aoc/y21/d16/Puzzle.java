package buri.aoc.y21.d16;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.List;

/**
 * Day 16: Packet Decoder
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(854L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(186189840660L, 0, true);
	}

	/**
	 * Part 1:
	 * What do you get if you add up the version numbers in all packets?
	 *
	 * Part 2:
	 * What do you get if you evaluate the expression represented by your hexadecimal-encoded BITS transmission?
	 */
	protected long runLong(Part part, List<String> input) {
		StringBuilder builder = new StringBuilder();
		for (char hex : input.get(0).toCharArray()) {
			builder.append(Packet.hexToBin(hex));
		}
		Packet packet = new Packet(builder.toString(), 0);
		return (part == Part.ONE ? packet.getVersionSum() : packet.getResult());
	}
}