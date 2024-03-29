package buri.aoc.y19.d24;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.tuple.Triple;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Day 24: Planet of Discord
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(2129920L, 1, false);
		assertRun(18842609L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(2059L, 0, true);
	}

	private static final char BUG = '#';
	private static final int SIZE = 5;

	/**
	 * Part 1:
	 * What is the biodiversity rating for the first layout that appears twice?
	 *
	 * Part 2:
	 * Starting with your scan, how many bugs are present after 200 minutes?
	 */
	protected long runLong(Part part, List<String> input) {
		Set<Triple<Integer>> bugs = getBugs(input);
		if (part == Part.ONE) {
			Set<String> snapshots = new HashSet<>();
			while (true) {
				bugs = evolve(part, bugs);
				if (snapshots.contains(bugs.toString())) {
					break;
				}
				snapshots.add(bugs.toString());
			}
			return (getBiodiversity(bugs));
		}

		// Part TWO
		for (int i = 0; i < 200; i++) {
			bugs = evolve(part, bugs);
		}
		return (bugs.size());
	}

	/**
	 * Converts the starting input into a set of triples.
	 */
	private static Set<Triple<Integer>> getBugs(List<String> input) {
		Set<Triple<Integer>> bugs = new TreeSet<>();
		for (int y = 0; y < input.size(); y++) {
			String line = input.get(y);
			for (int x = 0; x < line.length(); x++) {
				if (line.charAt(x) == BUG) {
					bugs.add(new Triple<>(x, y, 0));
				}
			}
		}
		return (bugs);
	}

	/**
	 * Calculates the biodiversity rating.
	 */
	public static int getBiodiversity(Set<Triple<Integer>> bugs) {
		BigInteger total = BigInteger.valueOf(0);
		int power = 0;
		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				Triple possibleBug = new Triple<>(x, y, 0);
				if (bugs.contains(possibleBug)) {
					total = total.add(BigInteger.valueOf(2).pow(power));
				}
				power++;
			}
		}
		return (total.intValue());
	}

	/**
	 * Each minute, the bugs live and die based on the number of bugs in the adjacent tiles.
	 */
	public static Set<Triple<Integer>> evolve(Part part, Set<Triple<Integer>> bugs) {
		// Level will always be 0 in part one.
		int minLevel = Integer.MAX_VALUE;
		int maxLevel = Integer.MIN_VALUE;
		for (Triple<Integer> bug : bugs) {
			minLevel = Math.min(minLevel, bug.getZ());
			maxLevel = Math.max(maxLevel, bug.getZ());
		}

		Set<Triple<Integer>> nextBugs = new TreeSet<>();
		for (int z = minLevel - 1; z < maxLevel + 2; z++) {
			for (int y = 0; y < SIZE; y++) {
				for (int x = 0; x < SIZE; x++) {
					if (part == Part.TWO && x == 2 && y == 2) {
						continue;
					}
					Triple<Integer> tile = new Triple<>(x, y, z);
					if (becomesBug(part, bugs, tile)) {
						nextBugs.add(tile);
					}
				}
			}
		}
		return (nextBugs);
	}

	/**
	 * Returns true if a tile will evolve into a bug.
	 *
	 * A bug dies (becoming an empty space) unless there is exactly one bug adjacent to it.
	 * An empty space becomes infested with a bug if exactly one or two bugs are adjacent to it.
	 */
	private static boolean becomesBug(Part part, Set<Triple<Integer>> bugs, Triple<Integer> tile) {
		boolean isBug = bugs.contains(tile);

		List<Triple<Integer>> neighbors = new ArrayList<>();
		neighbors.add(new Triple<>(tile.getX(), tile.getY() - 1, tile.getZ()));
		neighbors.add(new Triple<>(tile.getX(), tile.getY() + 1, tile.getZ()));
		neighbors.add(new Triple<>(tile.getX() - 1, tile.getY(), tile.getZ()));
		neighbors.add(new Triple<>(tile.getX() + 1, tile.getY(), tile.getZ()));

		// Handle nested neighbors.
		if (part == Part.TWO) {
			List<Triple<Integer>> newNeighbors = new ArrayList<>();
			for (Triple<Integer> neighbor : neighbors) {
				boolean isInner = (neighbor.getX() == 2 && neighbor.getY() == 2);
				int outerZ = neighbor.getZ() - 1;
				int innerZ = neighbor.getZ() + 1;

				// Outer Edges
				if (neighbor.getY() < 0) {
					newNeighbors.add(new Triple<>(2, 1, outerZ));
				}
				else if (neighbor.getY() == SIZE) {
					newNeighbors.add(new Triple<>(2, 3, outerZ));
				}
				else if (neighbor.getX() < 0) {
					newNeighbors.add(new Triple<>(1, 2, outerZ));
				}
				else if (neighbor.getX() == SIZE) {
					newNeighbors.add(new Triple<>(3, 2, outerZ));
				}
				// Inner Edges
				else if (isInner && tile.getX() == 1) {
					newNeighbors.add(new Triple<>(0, 0, innerZ));
					newNeighbors.add(new Triple<>(0, 1, innerZ));
					newNeighbors.add(new Triple<>(0, 2, innerZ));
					newNeighbors.add(new Triple<>(0, 3, innerZ));
					newNeighbors.add(new Triple<>(0, 4, innerZ));
				}
				else if (isInner && tile.getX() == 3) {
					newNeighbors.add(new Triple<>(4, 0, innerZ));
					newNeighbors.add(new Triple<>(4, 1, innerZ));
					newNeighbors.add(new Triple<>(4, 2, innerZ));
					newNeighbors.add(new Triple<>(4, 3, innerZ));
					newNeighbors.add(new Triple<>(4, 4, innerZ));
				}
				else if (isInner && tile.getY() == 1) {
					newNeighbors.add(new Triple<>(0, 0, innerZ));
					newNeighbors.add(new Triple<>(1, 0, innerZ));
					newNeighbors.add(new Triple<>(2, 0, innerZ));
					newNeighbors.add(new Triple<>(3, 0, innerZ));
					newNeighbors.add(new Triple<>(4, 0, innerZ));
				}
				else if (isInner && tile.getY() == 3) {
					newNeighbors.add(new Triple<>(0, 4, innerZ));
					newNeighbors.add(new Triple<>(1, 4, innerZ));
					newNeighbors.add(new Triple<>(2, 4, innerZ));
					newNeighbors.add(new Triple<>(3, 4, innerZ));
					newNeighbors.add(new Triple<>(4, 4, innerZ));
				}
				// Regular Edges
				else {
					newNeighbors.add(neighbor);
				}
			}
			neighbors = newNeighbors;
		}

		int adjacentBugs = 0;
		for (Triple neighbor : neighbors) {
			if (bugs.contains(neighbor)) {
				adjacentBugs++;
			}
		}
		return (isBug && adjacentBugs == 1) || (!isBug && (adjacentBugs == 1 || adjacentBugs == 2));
	}
}