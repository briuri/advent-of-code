package buri.aoc.y16.d11;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Day 11: Radioisotope Thermoelectric Generators
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(11L, 1, false);
		assertRun(31L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(55L, 2, true);
	}

	/**
	 * Part 1:
	 * In your situation, what is the minimum number of steps required to bring all of the objects to the fourth floor?
	 *
	 * Part 2:
	 * What is the minimum number of steps required to bring all of the objects, including these four new ones, to the
	 * fourth floor?
	 */
	protected long runLong(Part part, List<String> input) {
		// Set up the start and final states.
		final State START_STATE = new State(input.get(0));
		StringBuilder builder = new StringBuilder("4");
		for (int i = 0; i < START_STATE.getPairs(); i++) {
			builder.append("|44");
		}
		final State FINAL_STATE = new State(builder.toString());

		// Do a BFS to get to the final state.
		Queue<State> frontier = new ArrayDeque<>();
		frontier.add(START_STATE);
		Map<State, State> cameFrom = new HashMap<>();
		cameFrom.put(START_STATE, null);
		State current;
		while (!frontier.isEmpty()) {
			current = frontier.remove();
			if (current.equals(FINAL_STATE)) {
				int steps = 0;
				while (current != null) {
					steps++;
					current = cameFrom.get(current);
				}
				// Subtract the final start-to-null step.
				return (steps - 1);
			}
			for (State next : current.getNextStates()) {
				// Don't revisit the starting point.
				if (next.equals(START_STATE)) {
					continue;
				}
				if (cameFrom.get(next) == null) {
					frontier.add(next);
					cameFrom.put(next, current);
				}
			}
		}
		throw new RuntimeException("Could not reach final state.");
	}
}