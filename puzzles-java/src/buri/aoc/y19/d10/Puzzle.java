package buri.aoc.y19.d10;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.tuple.Pair;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Day 10: Monitoring Station
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(8, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
		assertEquals(33, Puzzle.getResult(Part.ONE, Puzzle.getInput(2)));
		assertEquals(35, Puzzle.getResult(Part.ONE, Puzzle.getInput(3)));
		assertEquals(41, Puzzle.getResult(Part.ONE, Puzzle.getInput(4)));
		assertEquals(210, Puzzle.getResult(Part.ONE, Puzzle.getInput(5)));
	}

	@Test
	public void testPart1Puzzle() {
		int result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(230, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(802, Puzzle.getResult(Part.TWO, Puzzle.getInput(5)));
	}

	@Test
	public void testPart2Puzzle() {
		int result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(1205, result);
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
		Pair<Integer> station = null;
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
		Pair<Integer> slope = sightLine.getSlope();
		Pair<Integer> asteroid = station.copy();
		while (asteroid.equals(station) || !asteroids.contains(asteroid)) {
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