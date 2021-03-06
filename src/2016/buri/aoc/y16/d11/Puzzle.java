package buri.aoc.y16.d11;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import buri.aoc.BasePuzzle;
import buri.aoc.Part;

/**
 * Day 11: Radioisotope Thermoelectric Generators
 * 
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Returns the input file as a snapshot of facility state (from line 1).
	 */
	public static String getInput(int fileIndex) {
		return (readFile(fileIndex).get(0));
	}

	/**
	 * Part 1:
	 * In your situation, what is the minimum number of steps required to bring all of the objects to the fourth floor?
	 * 
	 * Part 2:
	 * What is the minimum number of steps required to bring all of the objects, including these four new ones, to the
	 * fourth floor?
	 */
	public static int getResult(Part part, String start) {
		// Set up the start and final states.
		final State START_STATE = new State(start);
		StringBuffer buffer = new StringBuffer("4");
		for (int i = 0; i < START_STATE.getPairs(); i++) {
			buffer.append("|44");
		}
		final State FINAL_STATE = new State(buffer.toString());

		// Do a BFS to get to the final state.
		Queue<State> frontier = new ArrayDeque<>();
		frontier.add(START_STATE);
		Map<State, State> cameFrom = new HashMap<>();
		cameFrom.put(START_STATE, null);
		State current = null;
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