package buri.aoc.y22.d16;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Day 16: Proboscidea Volcanium
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {

	/**
	 * Part 1:
	 * What is the most pressure you can release?
	 *
	 * Part 2:
	 * With you and an elephant working together for 26 minutes, what is the most pressure you could release?
	 */
	public static long getResult(Part part, List<String> input) {
		String start = "AA";
		// Valve names mapped to flow rates.
		Map<String, Long> valves = new HashMap<>();
		// Valve names mapped to index-based longs so we can represent opened valves as 1 number.
		Map<String, Long> valvesAsLongs = new HashMap<>();
		// Valve names mapped to connected tunnels.
		Map<String, List<String>> tunnels = new HashMap<>();
		for (String line : input) {
			String valve = line.substring(6, 8);
			long flow = Long.parseLong(line.split("rate=")[1].split(";")[0]);
			if (flow > 0) {
				valves.put(valve, flow);
				valvesAsLongs.put(valve, (long) Math.pow(2, valves.size() - 1));
			}

			tunnels.put(valve, new ArrayList<>());
			String[] tokens;
			if (line.contains("tunnels")) {
				tokens = line.split(" to valves ")[1].split(", ");
			}
			else {
				tokens = line.split(" to valve ")[1].split(", ");
			}
			for (String token : tokens) {
				tunnels.get(valve).add(token);
			}
		}

		final int MINUTES = (part == Part.ONE ? 30 : 26);
		Set<State> frontier = new HashSet<>();
		frontier.add(new State(start, 0, 0L));
		Map<String, Long> bestPressures = new HashMap<>();
		for (int minute = 1; minute <= MINUTES; minute++) {
			Set<State> newFrontier = new HashSet<>();
			for (State state : frontier) {
				// Optimization: Track previous best pressure and abandon this path if not good enough.
				String bestKey = state.getValve() + state.getOpened();
				if (state.getPressure() >= bestPressures.getOrDefault(bestKey, 0L)) {
					bestPressures.put(bestKey, state.getPressure());
				}
				else {
					continue;
				}

				if (valves.get(state.getValve()) != null) {
					// Explore possibility of opening valve.
					long flow = valves.get(state.getValve());
					long currentValveOpen = valvesAsLongs.get(state.getValve()) & state.getOpened();
					if (currentValveOpen == 0) {
						long remainingFlow = flow * (MINUTES - minute);
						long opened = valvesAsLongs.get(state.getValve()) | state.getOpened();
						newFrontier.add(new State(state.getValve(), opened, state.getPressure() + remainingFlow));
					}
				}
				// Explore possibility of moving through tunnels.
				for (String tunnel : tunnels.get(state.getValve())) {
					newFrontier.add(new State(tunnel, state.getOpened(), state.getPressure()));
				}
			}
			frontier = newFrontier;
		}

		// In part one, there will be one optimal path.
		if (part == Part.ONE) {
			List<State> sortedFrontier = new ArrayList<>(frontier);
			Collections.sort(sortedFrontier);
			Collections.reverse(sortedFrontier);
			return (sortedFrontier.get(0).getPressure());
		}
		// In part two, the max will come from two paths that each have distinct sets of opened valves.
		// Instead of comparing all states, group by sets of opened valves first.
		Map<Long, Long> groupedPressures = new TreeMap<>();
		for (State state : frontier) {
			long bestPressure = groupedPressures.getOrDefault(state.getOpened(), 0L);
			if (state.getPressure() > bestPressure) {
				groupedPressures.put(state.getOpened(), state.getPressure());
			}
		}
		// Now use the sum of any 2 opened values that do not overlap.
		long max = Long.MIN_VALUE;
		for (long iOpened : groupedPressures.keySet()) {
			for (long elephantOpened : groupedPressures.keySet()) {
				if ((iOpened & elephantOpened) == 0) {
					long combinedPressure = groupedPressures.get(iOpened) + groupedPressures.get(elephantOpened);
					max = Math.max(max, combinedPressure);
				}
			}
		}
		return (max);
	}
}