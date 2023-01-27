package buri.aoc.y17.d12;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Day 12: Digital Plumber
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(6L, 1, false);
		assertRun(134L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(2L, 1, false);
		assertRun(193L, 0, true);
	}

	/**
	 * Part 1:
	 * How many programs are in the group that contains program ID 0?
	 *
	 * Part 2:
	 * How many groups are there in total?
	 */
	protected long runLong(Part part, List<String> input) {
		List<Report> reports = new ArrayList<>();
		for (String line : input) {
			reports.add(new Report(line));
		}
		Collections.sort(reports);

		Map<Integer, Set<Integer>> connections = new HashMap<>();
		for (Report report : reports) {
			connections.put(report.getId(), report.getConnections());
		}
		Set<Integer> zeroConnections = getConnections(connections, 0);
		if (part == Part.ONE) {
			return (zeroConnections.size());
		}

		// Part TWO
		List<Integer> unlinkedConnections = new ArrayList<>(connections.keySet());
		unlinkedConnections.removeAll(zeroConnections);
		int groupCount = 1;
		// Generate the connections for each id in the unlinkedConnections, removing them as we go.
		while (unlinkedConnections.size() > 0) {
			Set<Integer> group = getConnections(connections, unlinkedConnections.get(0));
			group.add(unlinkedConnections.get(0));
			unlinkedConnections.removeAll(group);
			groupCount++;
		}
		return (groupCount);
	}

	/**
	 * Returns all connections to the given id.
	 */
	private static Set<Integer> getConnections(Map<Integer, Set<Integer>> connections, int id) {
		Set<Integer> foundConnections = connections.get(id);
		while (true) {
			int totalConnections = foundConnections.size();
			Set<Integer> tempConnections = new HashSet<>();
			for (Integer tempId : foundConnections) {
				tempConnections.addAll(connections.get(tempId));
			}
			foundConnections.addAll(tempConnections);

			// Quit when nothing new has been added.
			if (foundConnections.size() == totalConnections) {
				break;
			}
		}
		return (foundConnections);
	}
}