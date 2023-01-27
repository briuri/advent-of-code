package buri.aoc.y18.d04;

import buri.aoc.common.BasePuzzle;
import buri.aoc.common.Part;
import buri.aoc.common.PuzzleMath;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Day 4: Repose Record
 *
 * @author Brian Uri!
 */
public class Puzzle extends BasePuzzle {
	@Test
	public void testPart1() {
		assertRun(240L, 1, false);
		assertRun(76357L, 0, true);
	}
	@Test
	public void testPart2() {
		assertRun(4455L, 1, false);
		assertRun(41668L, 0, true);
	}

	/**
	 * Part 1:
	 * Find the guard that has the most minutes asleep. What minute does that guard spend asleep the most? What is the
	 * ID of the guard you chose multiplied by the minute you chose?
	 *
	 * Part 2:
	 * Of all guards, which guard is most frequently asleep on the same minute? What is the ID of the guard you chose
	 * multiplied by the minute you chose?
	 */
	protected long runLong(Part part, List<String> input) {
		List<Observation> data = new ArrayList<>();
		for (String line : input) {
			data.add(new Observation(line));
		}
		Collections.sort(data);

		Map<Integer, SleepSchedule> sleepSchedules = buildSleepSchedules(data);

		if (part == Part.ONE) {
			// Tally up the total sleep for each guard.
			Map<Integer, Integer> totalSleep = new HashMap<>();
			for (Integer id : sleepSchedules.keySet()) {
				totalSleep.put(id, sleepSchedules.get(id).getTotalSleep());
			}

			// Work backwards from the max sleep value to the id of the guard who slept that much.
			Map.Entry<Integer, Integer> maxEntry = PuzzleMath.getMax(totalSleep);
			int id = maxEntry.getKey();
			int minuteMostSpentAsleep = sleepSchedules.get(id).getMaxMinute();
			return ((long) id * minuteMostSpentAsleep);
		}

		// Part TWO
		// Organize sleep data for each guard (which minute they slept most, and how much).
		Map<Integer, Integer> maxMinute = new HashMap<>();
		Map<Integer, Integer> maxSleep = new HashMap<>();
		for (Integer id : sleepSchedules.keySet()) {
			SleepSchedule schedule = sleepSchedules.get(id);
			maxMinute.put(id, schedule.getMaxMinute());
			maxSleep.put(id, schedule.getMaxSleep());
		}

		// Find the guard who slept the most of all guards in some minute.
		int max = Collections.max(maxSleep.values());

		// Look up which minute that was by id.
		for (Integer id : maxSleep.keySet()) {
			if (maxSleep.get(id) == max) {
				return ((long) id * maxMinute.get(id));
			}
		}
		throw new RuntimeException("Could not get the minute based on the max amount of sleep.");
	}

	/**
	 * Calculates sleep schedules based on the observations.
	 */
	private static Map<Integer, SleepSchedule> buildSleepSchedules(List<Observation> input) {
		Map<Integer, SleepSchedule> schedules = new HashMap<>();
		int currentGuard = 0;
		int sleepMinute = -1;
		int wakeMinute;
		for (Observation observation : input) {
			if (observation.isOnDuty()) {
				// Guard has changed since previous id observation.
				currentGuard = observation.getId();
			}
			else if (observation.isSleeping()) {
				sleepMinute = observation.getMinute();
			}
			else if (observation.isWaking()) {
				wakeMinute = observation.getMinute();
				// Once we hit a waking line, we can fill the sleep-to-wake duration with sleep.
				if (schedules.get(currentGuard) == null) {
					schedules.put(currentGuard, new SleepSchedule());
				}
				for (int i = sleepMinute; i < wakeMinute; i++) {
					schedules.get(currentGuard).markAsleep(i);
				}
			}
		}
		return (schedules);
	}
}