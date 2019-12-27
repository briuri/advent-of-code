package buri.aoc.y19.d10;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;
import buri.aoc.data.tuple.Pair;

/**
 * Day 10: Monitoring Station
 * 
 * @author Brian Uri!
 */
public class Day10 extends BasePuzzle {

	/**
	 * Returns the input file unmodified.
	 */
	public static List<String> getInput(int fileIndex) {
		return (readFile(fileIndex));
	}

	/**
	 * Part 1:
	 * How many other asteroids can be detected from that location?
	 * 
	 * Part 2:
	 * The Elves are placing bets on which will be the 200th asteroid to be vaporized. Win the bet by determining which
	 * asteroid that will be; what do you get if you multiply its X coordinate by 100 and then add its Y coordinate?
	 */
	public static int getResult(Part part, List<String> input) {
		Set<Pair> asteroids = new HashSet<>();
		for (int y = 0; y < input.size(); y++) {
			String line = input.get(y);
			for (int x = 0; x < line.length(); x++) {
				char icon = line.charAt(x);
				if (icon == '#') {
					asteroids.add(new Pair(x, y));
				}
			}
		}

		// Find best asteroid for monitoring station.
		Pair station = null;
		Set<SightLine> sightLines = null;
		for (Pair asteroid : asteroids) {
			Set<SightLine> currentSightLines = getSightLines(asteroids, asteroid);
			if (sightLines == null || currentSightLines.size() > sightLines.size()) {
				station = asteroid;
				sightLines = currentSightLines;
			}
		}
		if (part == Part.ONE) {
			return (sightLines.size());
		}

		// Part TWO
		// Because we have 230 asteroids in line of sight, we don't need multiple laser revolutions to hit 200.
		List<SightLine> sortedSightLines = new ArrayList<>(sightLines);
		Collections.sort(sortedSightLines);

		int target = 200;
		SightLine sightLine = sortedSightLines.get(target - 1);

		// Trace sight line along slope until an asteroid is found.
		Pair slope = sightLine.getSlope();
		Pair asteroid = new Pair(station.getX() + slope.getX(), station.getY() + slope.getY());
		while (!asteroids.contains(asteroid)) {
			asteroid.setX(asteroid.getX() + slope.getX());
			asteroid.setY(asteroid.getY() + slope.getY());
		}
		return (asteroid.getX() * 100 + asteroid.getY());
	}

	/**
	 * Calculates number of asteroids seen using unique slopes to block asteroids farther away.
	 */
	protected static Set<SightLine> getSightLines(Set<Pair> asteroids, Pair station) {
		Set<SightLine> sightLines = new HashSet<>();
		for (Pair asteroid : asteroids) {
			if (!asteroid.equals(station)) {
				sightLines.add(new SightLine(station, asteroid));
			}
		}
		return (sightLines);
	}
}