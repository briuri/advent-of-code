package buri.aoc.y21.d12;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Day 12: Passage Pathing
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(10L, 1, false);
		assertRun(19L, 2, false);
		assertRun(226L, 3, false);
		assertRun(3000L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(36L, 1, false);
		assertRun(103L, 2, false);
		assertRun(3509L, 3, false);
		assertRun(74222L, 0, true);
	}

	/**
	 * Part 1:
	 * How many paths through this cave system are there that visit small caves at most once?
	 *
	 * Part 2:
	 * Given these new rules, how many paths through this cave system are there?
	 */
	protected long runLong(Part part, List<String> input) {
		Map<String, Cave> caves = new HashMap<>();
		for (String line : input) {
			String[] tokens = line.split("-");
			addCave(caves, tokens[0]);
			addCave(caves, tokens[1]);
			caves.get(tokens[0]).addNeighbor(caves.get(tokens[1]));
			caves.get(tokens[1]).addNeighbor(caves.get(tokens[0]));
		}

		return (explore(part, caves.get("start"), new ArrayList<>()));
	}

	/**
	 * Recursively counts the number of valid paths from a position to the end.
	 */
	private static int explore(Part part, Cave start, List<Cave> visitedSmallCaves) {
		if (start.getName().equals("end")) {
			return (1);
		}

		if (start.isSmall()) {
			visitedSmallCaves.add(start);
		}
		int totalPaths = 0;
		for (Cave neighbor : start.getNeighbors()) {
			// In part one, no small cave can be visited more than one time.
			if (part == Part.ONE) {
				if (!visitedSmallCaves.contains(neighbor)) {
					totalPaths += explore(part, neighbor, new ArrayList<>(visitedSmallCaves));
				}
			}
			// In part two, 1 small cave can be visited twice.
			else {
				// If any cave has already been visited twice, the set will be smaller than the list.
				Set<Cave> noDuplicates = new HashSet<>(visitedSmallCaves);
				if (!visitedSmallCaves.contains(neighbor) || (noDuplicates.size() == visitedSmallCaves.size())) {
					totalPaths += explore(part, neighbor, new ArrayList<>(visitedSmallCaves));
				}
			}
		}
		return (totalPaths);
	}

	/**
	 * Adds a new cave if it has not already been created.
	 */
	private static void addCave(Map<String, Cave> caves, String name) {
		if (caves.get(name) == null) {
			caves.put(name, new Cave(name));
		}
	}
}