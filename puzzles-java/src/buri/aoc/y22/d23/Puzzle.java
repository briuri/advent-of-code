package buri.aoc.y22.d23;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.tuple.Pair;
import buri.aoc.common.data.tuple.Quad;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Day 23: Unstable Diffusion
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * How many empty ground tiles does that rectangle contain?
	 *
	 * Part 2:
	 * What is the number of the first round where no Elf moves?
	 */
	public static long getResult(Part part, List<String> input) {
		Set<Pair<Integer>> elves = new HashSet<>();
		for (int y = 0; y < input.size(); y++) {
			for (int x = 0; x < input.get(0).length(); x++) {
				char value = input.get(y).charAt(x);
				if (value == '#') {
					elves.add(new Pair<>(x, y));
				}
			}
		}
		final int NUM_ROUNDS = (part == Part.ONE ? 10 : Integer.MAX_VALUE);
		for (int round = 1; round <= NUM_ROUNDS; round++) {
			boolean elfMoved = false;
			Set<Pair<Integer>> newElves = new HashSet<>();
			Map<Pair<Integer>, Pair<Integer>> elvesToProposed = new HashMap<>();
			Map<Pair<Integer>, Integer> proposedCounts = new HashMap<>();

			// First half of round -- propose a move.
			for (Pair<Integer> elf : elves) {
				List<Pair<Integer>> adjacent = elf.getAdjacent(true);
				adjacent.retainAll(elves);
				if (!adjacent.isEmpty()) {
					Pair<Integer> proposedMove = getProposedMove(round, elf, elves);
					if (proposedMove != null) {
						elvesToProposed.put(elf, proposedMove);
						int count = proposedCounts.getOrDefault(proposedMove, 0);
						proposedCounts.put(proposedMove, count + 1);
					}
				}
			}

			// Second half of round -- move if no one else has claimed the proposed move.
			for (Pair<Integer> elf : elves) {
				Pair<Integer> target = elvesToProposed.get(elf);
				if (target == null || proposedCounts.get(target) > 1) {
					newElves.add(elf);
				}
				else {
					elfMoved = true;
					newElves.add(elvesToProposed.get(elf));
				}
			}

			// Part 2: Check if no one moved.
			if (part == Part.TWO && !elfMoved) {
				return (round);
			}

			// Otherwise go to next round.
			elves = newElves;
		}
		if (part == Part.TWO) {
			throw new RuntimeException("Didn't reach a round where no elf moved.");
		}

		// Part 1: Find the rectangle of elves and count empty spaces.
		Quad<Integer> bounds = getBounds(elves);
		long emptySpaces = 0;
		for (int y = bounds.getZ(); y <= bounds.getT(); y++) {
			for (int x = bounds.getX(); x <= bounds.getY(); x++) {
				if (!elves.contains(new Pair<>(x, y))) {
					emptySpaces++;
				}
			}
		}
		return (emptySpaces);
	}

	protected static Pair<Integer> getProposedMove(int round, Pair<Integer> elf, Set<Pair<Integer>> elves) {
		Pair<Integer> target = null;
		int order = (round - 1) % 4;
		if (order == 0) {
			target = testNorth(elf, elves);
			if (target == null) {
				target = testSouth(elf, elves);
			}
			if (target == null) {
				target = testWest(elf, elves);
			}
			if (target == null) {
				target = testEast(elf, elves);
			}
		}
		else if (order == 1) {
			target = testSouth(elf, elves);
			if (target == null) {
				target = testWest(elf, elves);
			}
			if (target == null) {
				target = testEast(elf, elves);
			}
			if (target == null) {
				target = testNorth(elf, elves);
			}
		}
		else if (order == 2) {
			target = testWest(elf, elves);
			if (target == null) {
				target = testEast(elf, elves);
			}
			if (target == null) {
				target = testNorth(elf, elves);
			}
			if (target == null) {
				target = testSouth(elf, elves);
			}
		}
		else if (order == 3) {
			target = testEast(elf, elves);
			if (target == null) {
				target = testNorth(elf, elves);
			}
			if (target == null) {
				target = testSouth(elf, elves);
			}
			if (target == null) {
				target = testWest(elf, elves);
			}
		}
		return (target);
	}

	/**
	 * If there is no Elf in the N, NE, or NW adjacent positions, the Elf proposes moving north one step.
	 */
	protected static Pair<Integer> testNorth(Pair<Integer> elf, Set<Pair<Integer>> elves) {
		Pair<Integer> adj1 = new Pair<>(elf.getX() - 1, elf.getY() - 1);
		Pair<Integer> adj2 = new Pair<>(elf.getX(), elf.getY() - 1);
		Pair<Integer> adj3 = new Pair<>(elf.getX() + 1, elf.getY() - 1);
		if (elves.contains(adj1) || elves.contains(adj2) || elves.contains(adj3)) {
			return null;
		}
		return (new Pair<>(elf.getX(), elf.getY() - 1));
	}

	/**
	 * If there is no Elf in the S, SE, or SW adjacent positions, the Elf proposes moving south one step.
	 */
	protected static Pair<Integer> testSouth(Pair<Integer> elf, Set<Pair<Integer>> elves) {
		Pair<Integer> adj1 = new Pair<>(elf.getX() - 1, elf.getY() + 1);
		Pair<Integer> adj2 = new Pair<>(elf.getX(), elf.getY() + 1);
		Pair<Integer> adj3 = new Pair<>(elf.getX() + 1, elf.getY() + 1);
		if (elves.contains(adj1) || elves.contains(adj2) || elves.contains(adj3)) {
			return null;
		}
		return (new Pair<>(elf.getX(), elf.getY() + 1));
	}

	/**
	 * If there is no Elf in the E, NE, or SE adjacent positions, the Elf proposes moving east one step.
	 */
	protected static Pair<Integer> testEast(Pair<Integer> elf, Set<Pair<Integer>> elves) {
		Pair<Integer> adj1 = new Pair<>(elf.getX() + 1, elf.getY() - 1);
		Pair<Integer> adj2 = new Pair<>(elf.getX() + 1, elf.getY());
		Pair<Integer> adj3 = new Pair<>(elf.getX() + 1, elf.getY() + 1);
		if (elves.contains(adj1) || elves.contains(adj2) || elves.contains(adj3)) {
			return null;
		}
		return (new Pair<>(elf.getX() + 1, elf.getY()));
	}

	/**
	 * If there is no Elf in the W, NW, or SW adjacent positions, the Elf proposes moving west one step.
	 */
	protected static Pair<Integer> testWest(Pair<Integer> elf, Set<Pair<Integer>> elves) {
		Pair<Integer> adj1 = new Pair<>(elf.getX() - 1, elf.getY() - 1);
		Pair<Integer> adj2 = new Pair<>(elf.getX() - 1, elf.getY());
		Pair<Integer> adj3 = new Pair<>(elf.getX() - 1, elf.getY() + 1);
		if (elves.contains(adj1) || elves.contains(adj2) || elves.contains(adj3)) {
			return null;
		}
		return (new Pair<>(elf.getX() - 1, elf.getY()));
	}
}