package buri.aoc.y21.d16;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class PuzzleTest extends BaseTest {

	@Test
	public void testPart1Examples() {
		assertEquals(6L, Puzzle.getResult(Part.ONE, "D2FE28"));
		assertEquals(9L, Puzzle.getResult(Part.ONE, "38006F45291200"));
		assertEquals(14L, Puzzle.getResult(Part.ONE, "EE00D40C823060"));
		assertEquals(16L, Puzzle.getResult(Part.ONE, "8A004A801A8002F478"));
		assertEquals(12L, Puzzle.getResult(Part.ONE, "620080001611562C8802118E34"));
		assertEquals(23L, Puzzle.getResult(Part.ONE, "C0015000016115A2E0802F182340"));
		assertEquals(31L, Puzzle.getResult(Part.ONE, "A0016C880162017C3686B18A3D4780"));
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0).get(0));
		toConsole(result);
		assertEquals(854L, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(3L, Puzzle.getResult(Part.TWO, "C200B40A82"));
		assertEquals(54L, Puzzle.getResult(Part.TWO, "04005AC33890"));
		assertEquals(7L, Puzzle.getResult(Part.TWO, "880086C3E88112"));
		assertEquals(9L, Puzzle.getResult(Part.TWO, "CE00C43D881120"));
		assertEquals(1L, Puzzle.getResult(Part.TWO, "D8005AC2A8F0"));
		assertEquals(0L, Puzzle.getResult(Part.TWO, "F600BC2D8F"));
		assertEquals(0L, Puzzle.getResult(Part.TWO, "9C005AC2F8F0"));
		assertEquals(1L, Puzzle.getResult(Part.TWO, "9C0141080250320F1802104A08"));
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0).get(0));
		toConsole(result);
		assertEquals(186189840660L, result);
	}
}
