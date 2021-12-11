package buri.aoc.y19.d22;

import static org.junit.Assert.*;

import org.junit.Test;

import buri.aoc.BaseTest;
import buri.aoc.Part;

/**
 * @author Brian Uri!
 */
public class PuzzleTest extends BaseTest {

	@Test
	public void testDealIntoNewStack() {
		Deck deck = new Deck(10);
		deck.dealIntoNewStack();
		assertEquals("[9, 8, 7, 6, 5, 4, 3, 2, 1, 0]", deck.toString());
	}

	@Test
	public void testCut() {
		Deck deck = new Deck(10);
		deck.cut(3);
		assertEquals("[3, 4, 5, 6, 7, 8, 9, 0, 1, 2]", deck.toString());

		deck = new Deck(10);
		deck.cut(-4);
		assertEquals("[6, 7, 8, 9, 0, 1, 2, 3, 4, 5]", deck.toString());
	}

	@Test
	public void testDealWithIncrement() {
		Deck deck = new Deck(10);
		deck.dealWithIncrement(3);
		assertEquals("[0, 7, 4, 1, 8, 5, 2, 9, 6, 3]", deck.toString());
	}

	/**
	 * Solves the Part 1 puzzle.
	 */
	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(3749L, result);
	}

	/**
	 * Solves the Part 2 puzzle.
	 */
	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(77225522112241L, result);
	}
}
