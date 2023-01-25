package buri.aoc.y22.d15;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.tuple.Pair;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Day 15: Beacon Exclusion Zone
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(26L, Puzzle.getResult(Part.ONE, true, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, false, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(5335787L, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(56000011L, Puzzle.getResult(Part.TWO, true, Puzzle.getInput(1)));
	}

	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, false, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(13673971349056L, result);
	}

	/**
	 * Part 1:
	 * In the row where y=2000000, how many positions cannot contain a beacon?
	 *
	 * Part 2:
	 * What is its tuning frequency?
	 */
	public static long getResult(Part part, boolean isSample, List<String> input) {
		long answerY = isSample ? 10L : 2000000L;
		Set<Pair<Long>> sensorsBeacons = new HashSet<>();
		Map<Pair<Long>, Long> sensorDistances = new HashMap<>();
		for (String line : input) {
			String[] tokens = line.split("x=");
			long sX = Long.parseLong(tokens[1].split(", ")[0]);
			long bX = Long.parseLong(tokens[2].split(", ")[0]);
			tokens = line.split("y=");
			long sY = Long.parseLong(tokens[1].split(": ")[0]);
			long bY = Long.parseLong(tokens[2]);
			Pair<Long> sensor = new Pair<>(sX, sY);
			Pair<Long> beacon = new Pair<>(bX, bY);
			sensorsBeacons.add(sensor);
			sensorsBeacons.add(beacon);
			sensorDistances.put(sensor, sensor.getManhattanDistance(beacon));
		}

		if (part == Part.ONE) {
			long minX = Long.MAX_VALUE;
			long maxX = Long.MIN_VALUE;
			long largestDistance = Long.MIN_VALUE;
			for (Pair<Long> sensor : sensorDistances.keySet()) {
				minX = Math.min(minX, sensor.getX());
				maxX = Math.max(maxX, sensor.getX());
				largestDistance = Math.max(largestDistance, sensorDistances.get(sensor));
			}

			long noBeaconPositions = 0;
			for (long x = minX - largestDistance; x <= maxX + largestDistance; x++) {
				Pair<Long> test = new Pair<>(x, answerY);
				if (sensorsBeacons.contains(test)) {
					continue;
				}
				for (Pair<Long> sensor : sensorDistances.keySet()) {
					long distance = sensorDistances.get(sensor);
					if (sensor.getManhattanDistance(test) <= distance) {
						noBeaconPositions++;
						break;
					}
				}
			}
			return noBeaconPositions;
		}

		// After getting a working solution to Part 2, I saw that the answer has 4 sensors bounding it.
		// Sorting the sensors by lowest Manhattan distance to their respecctive beacons for the sake of reducing
		// Part 2 run time, but I'm not sure if there's something programmatically consistent that can be used as a
		// sort order.
		List<Pair<Long>> sortedSensors = new ArrayList<>(sensorDistances.keySet());
		sortedSensors.sort(new Comparator<Pair<Long>>() {
			@Override
			public int compare(Pair<Long> o1, Pair<Long> o2) {
				return (Long.compare(sensorDistances.get(o1), sensorDistances.get(o2)));
			}
		});
		for (Pair<Long> sensor : sortedSensors) {
			long distance = sensorDistances.get(sensor);
			Pair<Long> test = testRing(sensorsBeacons, sensorDistances, sensor, distance + 1);
			if (test != null) {
				return (test.getX() * 4000000 + test.getY());
			}
		}
		throw new RuntimeException("Could not find the hidden beacon.");
	}

	/**
	 * Constrains locations in Part 2.
	 */
	protected static boolean isInBounds(long x, long y) {
		return (x >= 0 && y >= 0 && x <= 4000000 && y <= 4000000);
	}

	/**
	 * Creates a ring just outside the beacon Manhattan distance around a sensor. The Part 2 answer will be along
	 * one of these rings.
	 */
	protected static Pair<Long> testRing(Set<Pair<Long>> sensorsBeacons, Map<Pair<Long>, Long> sensorDistances,
										 Pair<Long> ringSensor, long ringDistance) {

		// Avoid overhead of making Pair objects until we know point is in bounds.
		long x, y;
		Set<Pair<Long>> tests = new HashSet<>();
		for (long i = 0; i < ringDistance; i++) {
			// Upper left side
			x = ringSensor.getX() - ringDistance + i;
			y = ringSensor.getY() - i;
			if (isInBounds(x, y)) {
				tests.add(new Pair<>(x, y));
			}
			// Upper right side
			x = ringSensor.getX() + ringDistance - i;
			y = ringSensor.getY() - i;
			if (isInBounds(x, y)) {
				tests.add(new Pair<>(x, y));
			}
			// Lower right side
			x = ringSensor.getX() + ringDistance - i;
			y = ringSensor.getY() + i;
			if (isInBounds(x, y)) {
				tests.add(new Pair<>(x, y));
			}
			// Lower left side
			x = ringSensor.getX() - ringDistance - i;
			y = ringSensor.getY() + i;
			if (isInBounds(x, y)) {
				tests.add(new Pair<>(x, y));
			}
		}

		Pair<Long> outOfBoundsPoint = null;
		for (Pair<Long> test : tests) {
			if (sensorsBeacons.contains(test)) {
				continue;
			}
			boolean outOfRangeOfAllSensors = true;
			for (Pair<Long> sensor : sensorDistances.keySet()) {
				long distance = sensorDistances.get(sensor);
				outOfRangeOfAllSensors = outOfRangeOfAllSensors && (sensor.getManhattanDistance(test) > distance);
				if (!outOfRangeOfAllSensors) {
					break;
				}
			}
			if (outOfRangeOfAllSensors) {
				outOfBoundsPoint = test;
				break;
			}
		}
		return outOfBoundsPoint;
	}
}