package buri.aoc.y19.d22;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Day 22: Slam Shuffle
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(3749L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(77225522112241L, 0, true);
	}
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
	 * Part 1:
	 * After shuffling your factory order deck of 10007 cards, what is the position of card 2019?
	 *
	 * Part 2:
	 * After shuffling your new, giant, factory order deck that many times, what number is on the card that ends up in
	 * position 2020?
	 */
	protected long runLong(Part part, List<String> input) {
		Deck deck = new Deck(part == Part.ONE ? 10007L : 119315717514047L);
		for (String line : input) {
			String[] tokens = line.split(" ");
			if (tokens[0].equals("cut")) {
				deck.cut(Integer.valueOf(tokens[1]));
			}
			else if (tokens[0].equals("deal") && tokens[1].equals("into")) {
				deck.dealIntoNewStack();
			}
			// deal with increment
			else {
				deck.dealWithIncrement(Integer.valueOf(tokens[3]));
			}
		}
		if (part == Part.ONE) {
			return (deck.getPositionOfCard(2019));
		}

		// Part TWO
		return (deck.getCardAtPosition(2020L, 101741582076661L));
	}
}