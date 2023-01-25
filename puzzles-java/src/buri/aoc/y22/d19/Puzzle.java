package buri.aoc.y22.d19;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.data.tuple.Pair;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Day 19: Not Enough Minerals
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	@Test
	public void testPart1Examples() {
		assertEquals(33L, Puzzle.getResult(Part.ONE, Puzzle.getInput(1)));
	}

	@Test
	public void testPart1Puzzle() {
		long result = Puzzle.getResult(Part.ONE, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(1613L, result);
	}

	@Test
	public void testPart2Examples() {
		assertEquals(3472L, Puzzle.getResult(Part.TWO, Puzzle.getInput(1)));
	}

	@Test
	public void testPart2Puzzle() {
		long result = Puzzle.getResult(Part.TWO, Puzzle.getInput(0));
		toConsole(result);
		assertEquals(46816L, result);
	}

	/**
	 * Part 1:
	 * What do you get if you add up the quality level of all of the blueprints in your list?
	 *
	 * Part 2:
	 * What do you get if you multiply these numbers together?
	 */
	public static long getResult(Part part, List<String> input) {
		List<Blueprint> prints = new ArrayList<>();
		if (part == Part.TWO) {
			input = input.subList(0, Math.min(3, input.size()));
		}
		for (String line : input) {
			prints.add(new Blueprint(line));
		}

		// Optimize search by not having too many robots gathering more ore than we can spend.
		int maxOreCost = 0;
		int maxClayCost = 0;
		int maxObsidianCost = 0;
		for (Blueprint print : prints) {
			maxOreCost = Math.max(maxOreCost, print.getOreRobotCost(Blueprint.ORE));
			maxOreCost = Math.max(maxOreCost, print.getClayRobotCost(Blueprint.ORE));
			maxOreCost = Math.max(maxOreCost, print.getObsidianRobotCost(Blueprint.ORE));
			maxOreCost = Math.max(maxOreCost, print.getGeodeRobotCost(Blueprint.ORE));
			maxClayCost = Math.max(maxClayCost, print.getObsidianRobotCost(Blueprint.CLAY));
			maxObsidianCost = Math.max(maxObsidianCost, print.getGeodeRobotCost(Blueprint.OBSIDIAN));
		}

		final int MINUTES = (part == Part.ONE ? 24 : 32);
		Map<Integer, Integer> maxGeodes = new HashMap<>();
		for (Blueprint print : prints) {
			Set<State> frontier = new HashSet<>();
			frontier.add(new State(1, new Pair<>(1, 0), new Pair<>(0, 0),
					new Pair<>(0, 0), new Pair<>(0, 0)));
			while (!frontier.isEmpty()) {
				// Optimization: Prune any paths that aren't close to the max.
				// I had to tweak the meaning of "close" to find a value that worked in both parts.
				int threshold = 2;
				int maxGeodesSoFar = 0;
				int minute = 0;
				for (State state : frontier) {
					minute = state.getMinute();
					maxGeodesSoFar = Math.max(maxGeodesSoFar, state.getGeodesCollected());
				}
				if (maxGeodesSoFar > threshold) {
					for (Iterator<State> iter = frontier.iterator(); iter.hasNext(); ) {
						State state = iter.next();
						if (state.getGeodesCollected() + threshold < maxGeodesSoFar) {
							iter.remove();
						}
					}
				}

				Set<State> newFrontier = new HashSet<>();
				for (State state : frontier) {

					// When a blueprint times out, save the number of geodes.
					if (state.getMinute() > MINUTES) {
						int bestMax = maxGeodes.getOrDefault(print.getId(), 0);
						maxGeodes.put(print.getId(), Math.max(bestMax, state.getGeodesCollected()));
						continue;
					}
					State nextState;
					// Geode Robot: Always build if we can afford it.
					if (print.getGeodeRobotCost(Blueprint.ORE) <= state.getOreCollected()
							&& print.getGeodeRobotCost(Blueprint.OBSIDIAN) <= state.getObsidianCollected()) {
						nextState = state.copy();
						collect(nextState, maxOreCost, maxClayCost, maxObsidianCost);
						nextState.addOreCollected(-1 * print.getGeodeRobotCost(Blueprint.ORE));
						nextState.addObsidianCollected(-1 * print.getGeodeRobotCost(Blueprint.OBSIDIAN));
						nextState.addGeodeRobot();
						newFrontier.add(nextState);
						// This robot always has 1st priority.
						continue;
					}
					// Obsidian Robot: If we don't have enough of them and we can afford it.
					if (state.getObsidianRobots() < maxObsidianCost
							&& print.getObsidianRobotCost(Blueprint.ORE) <= state.getOreCollected()
							&& print.getObsidianRobotCost(Blueprint.CLAY) <= state.getClayCollected()) {
						nextState = state.copy();
						collect(nextState, maxOreCost, maxClayCost, maxObsidianCost);
						nextState.addOreCollected(-1 * print.getObsidianRobotCost(Blueprint.ORE));
						nextState.addClayCollected(-1 * print.getObsidianRobotCost(Blueprint.CLAY));
						nextState.addObsidianRobot();
						newFrontier.add(nextState);
						// This robot always has 2nd priority.
						// This only works in Part 1.
						if (part == Part.ONE) {
							continue;
						}
					}
					// Clay Robot: If we don't have enough of them and we can afford it.
					if (state.getClayRobots() < maxClayCost
							&& print.getClayRobotCost(Blueprint.ORE) <= state.getOreCollected()) {
						nextState = state.copy();
						collect(nextState, maxOreCost, maxClayCost, maxObsidianCost);
						nextState.addOreCollected(-1 * print.getClayRobotCost(Blueprint.ORE));
						nextState.addClayRobot();
						newFrontier.add(nextState);
					}
					// Ore Robot: If we don't have enough of them and we can afford it.
					if (state.getOreRobots() < maxOreCost
							&& print.getOreRobotCost(Blueprint.ORE) <= state.getOreCollected()) {
						nextState = state.copy();
						collect(nextState, maxOreCost, maxClayCost, maxObsidianCost);
						nextState.addOreCollected(-1 * print.getOreRobotCost(Blueprint.ORE));
						nextState.addOreRobot();
						newFrontier.add(nextState);
					}
					// Don't build anything.
					nextState = state.copy();
					collect(nextState, maxOreCost, maxClayCost, maxObsidianCost);
					newFrontier.add(nextState);
				}
				frontier = newFrontier;
			}
		}

		if (part == Part.ONE) {
			long qualitySum = 0;
			for (int id : maxGeodes.keySet()) {
				qualitySum += (id * maxGeodes.get(id));
			}
			return (qualitySum);
		}
		long qualityProduct = 1;
		for (int id : maxGeodes.keySet()) {
			qualityProduct *= maxGeodes.get(id);
		}
		return (qualityProduct);
	}

	/**
	 * Increments collection counters.
	 *
	 * Optimization: Cap the amount of ore collected at 2x max cost (so we can buy the same robot on 2 successive turns)
	 * We can never spend the excess and this keeps the number of possible states bounded.
	 */
	protected static void collect(State state, int maxOreCost, int maxClayCost, int maxObsidianCost) {
		state.addMinute();

		int oreIncrement = (state.getOreCollected() <= maxOreCost * 2 ? state.getOreRobots() : 0);
		state.addOreCollected(oreIncrement);

		int clayIncrement = (state.getClayCollected() <= maxClayCost * 2 ? state.getClayRobots() : 0);
		state.addClayCollected(clayIncrement);

		int obsidianIncrement = (state.getObsidianCollected() <= maxObsidianCost * 2 ? state.getObsidianRobots() : 0);
		state.addObsidianCollected(obsidianIncrement);

		state.addGeodesCollected(state.getGeodeRobots());
	}
}