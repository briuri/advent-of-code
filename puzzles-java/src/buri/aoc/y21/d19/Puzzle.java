package buri.aoc.y21.d19;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.tuple.Triple;

/**
 * Day 19: Beacon Scanner
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * How many beacons are there?
	 *
	 * Part 2:
	 * What is the largest Manhattan distance between any two scanners?
	 */
	public static long getResult(Part part, List<String> input) {
		List<Scanner> scanners = new ArrayList<>();
		for (int i = 0; i < input.size(); i++) {
			if (input.get(i).startsWith("---")) {
				String id = input.get(i).split("scanner ")[1].split(" ")[0];
				List<Triple<Integer>> beacons = new ArrayList<>();
				i++;
				while (i < input.size() && input.get(i).length() > 0) {
					String beacon = input.get(i);
					String[] tokens = beacon.split(",");
					int x = Integer.valueOf(tokens[0]);
					int y = Integer.valueOf(tokens[1]);
					int z = Integer.valueOf(tokens[2]);
					beacons.add(new Triple<Integer>(x, y, z));
					i++;
				}
				scanners.add(new Scanner(id, beacons));
			}
		}

		// Use Scanner 0 as the baseline.
		scanners.get(0).setLocation(new Triple<Integer>(0, 0, 0));
		List<Scanner> frontier = new ArrayList<>();
		frontier.add(scanners.get(0));
		while (!frontier.isEmpty()) {
			Scanner scanner = frontier.remove(0);
			for (Scanner testScanner : scanners) {
				if (scanner.getId().equals(testScanner.getId()) || testScanner.getLocation() != null) {
					continue;
				}
				// When an overlap is found (and that testScanner is updated to the right reference frame,
				// add that testScanner to the frontier so other scanners can be compared to it next.
				// Eventually, every scanner will be in Scanner 0's reference frame.
				if (scanner.findFirstOverlap(testScanner)) {
					frontier.add(testScanner);
				}
			}
		}
		// With all scanners shifted, count the unique beacons.
		Set<Triple<Integer>> beacons = new HashSet<>();
		for (Scanner scanner : scanners) {
			beacons.addAll(scanner.getBeacons());
		}
		if (part == Part.ONE) {
			return (beacons.size());
		}

		long maxDistance = Long.MIN_VALUE;
		for (Scanner scanner : scanners) {
			for (Scanner testScanner : scanners) {
				maxDistance = Math.max(maxDistance, scanner.getLocation().getManhattanDistance(testScanner.getLocation()));
			}
		}
		return (maxDistance);
	}
}