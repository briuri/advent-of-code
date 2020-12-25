package buri.aoc.y19.d22;

import java.util.List;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 22: Slam Shuffle
 * 
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Returns the input file unmodified.
	 */
	public static List<String> getInput(int fileIndex) {
		return (readFile(fileIndex));
	}

	/**
	 * Part 1:
	 * After shuffling your factory order deck of 10007 cards, what is the position of card 2019?
	 * 
	 * Part 2:
	 * After shuffling your new, giant, factory order deck that many times, what number is on the card that ends up in
	 * position 2020?
	 */
	public static long getResult(Part part, List<String> input) {
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