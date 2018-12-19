package buri.aoc.y17.d12;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import buri.aoc.Part;
import buri.aoc.Puzzle;

/**
 * Day 12: Digital Plumber
 * 
 * @author Brian Uri!
 */
public class Day12 extends Puzzle {

	/**
	 * Returns input file as a sorted list of reports.
	 */
	public static List<Report> getInput(int fileIndex) {
		List<Report> data = new ArrayList<>();
		for (String rawData : readFile("2017/12", fileIndex)) {
			data.add(new Report(rawData));
		}
		Collections.sort(data);
		return (data);
	}

	/**
	 * Part 1:
	 * How many programs are in the group that contains program ID 0?
	 * 
	 * Part 2:
	 * How many groups are there in total?
	 */
	public static int getResult(Part part, List<Report> input) {
		Map<Integer, Set<Integer>> connections = new HashMap<>();
		for (Report report : input) {
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
			if (group.size() > 0) {
				unlinkedConnections.removeAll(group);
				groupCount++;
			}
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