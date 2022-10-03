package buri.aoc.y20.d22;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 22: Crab Combat
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * What is the winning player's score?
	 *
	 * Part 2:
	 * What is the winning player's score?
	 */
	public static long getResult(Part part, List<String> input) {
		List<ArrayDeque<Integer>> decks = new ArrayList<>();
		for (String line : input) {
			ArrayDeque<Integer> deck = new ArrayDeque<>();
			for (String stringInt : line.split(",")) {
				deck.add(Integer.valueOf(stringInt));
			}
			decks.add(deck);
		}

		ArrayDeque<Integer> deck1 = decks.get(0);
		ArrayDeque<Integer> deck2 = decks.get(1);
		int totalCards = deck1.size() + deck2.size();

		boolean playerOneWins = playGame(part, deck1, deck2);
		ArrayDeque<Integer> winningDeck = playerOneWins ? deck1 : deck2;
		long score = 0;
		for (int i = 0; i < totalCards; i++) {
			score += winningDeck.pop() * (totalCards - i);
		}
		return (score);
	}

	/**
	 * Plays a game of Combat, using different rules in each Part.
	 *
	 * In Part One, follow standard rules.
	 * In Part Two, introduces recursion.
	 */
	protected static boolean playGame(Part part, ArrayDeque<Integer> deck1, ArrayDeque<Integer> deck2) {
		Set<String> snapshots = new HashSet<>();
		boolean playerOneWins = false;
		while (deck1.size() > 0 && deck2.size() > 0) {
			// Keep track of previous states in part two.
			if (part == Part.TWO) {
				String snapshotKey = deck1.toString() + deck2.toString();
				if (snapshots.contains(snapshotKey)) {
					return (true);
				}
				snapshots.add(snapshotKey);
			}

			int card1 = deck1.pop();
			int card2 = deck2.pop();

			// Add recursive rules in part two.
			if (part == Part.TWO && deck1.size() >= card1 && deck2.size() >= card2) {
				playerOneWins = playGame(part, copyDeck(deck1, card1), copyDeck(deck2, card2));
			}
			// Otherwise, high card wins.
			else {
				playerOneWins = (card1 > card2);
			}
			if (playerOneWins) {
				addCards(deck1, card1, card2);
			}
			else {
				addCards(deck2, card2, card1);
			}
		}
		return (playerOneWins);
	}

	/**
	 * Clones a deck and discards any cards at the bottom greater than "size".
	 */
	protected static ArrayDeque<Integer> copyDeck(ArrayDeque<Integer> deck, int size) {
		ArrayDeque<Integer> clone = deck.clone();
		while (clone.size() != size) {
			clone.removeLast();
		}
		return (clone);
	}

	/**
	 * Adds two cards to the bottom of the deck, high card first.
	 */
	protected static void addCards(ArrayDeque<Integer> deck, int highCard, int lowCard) {
		deck.addLast(highCard);
		deck.addLast(lowCard);
	}
}