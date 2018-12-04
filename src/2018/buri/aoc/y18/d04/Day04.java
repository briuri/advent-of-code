package buri.aoc.y18.d04;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import buri.aoc.Part;
import buri.aoc.Puzzle;

/**
 * @author Brian Uri!
 */
public class Day04 extends Puzzle {

	/**
	 * Input: Each line is an entry in a log about guard duty.
	 * Output: List of entries, sorted chronologically.
	 */
	public static List<LogEntry> getInput(int fileIndex) {
		List<LogEntry> data = new ArrayList<>();
		for (String rawData : readFile("2018/04", fileIndex)) {
			data.add(new LogEntry(rawData));
		}
		Collections.sort(data);
		return (data);
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
	public static String getResult(Part part, List<LogEntry> input) {
		Map<Integer, SleepSchedule> sleepSchedules = buildSleepSchedules(input);

		if (part == Part.ONE) {
			// Tally up the total sleep for each guard.
			Map<Integer, Integer> totalSleep = new HashMap<>();
			for (Integer id : sleepSchedules.keySet()) {
				totalSleep.put(id, sleepSchedules.get(id).getTotalSleep());
			}

			// Work backwards from the max sleep value to the id of the guard who slept that much.
			Map.Entry<Integer, Integer> maxEntry = null;
			for (Map.Entry<Integer, Integer> entry : totalSleep.entrySet()) {
				if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
					maxEntry = entry;
				}
			}
			int id = maxEntry.getKey();
			int minuteMostSpentAsleep = sleepSchedules.get(id).getMaxMinute();
			return (String.valueOf(id * minuteMostSpentAsleep));
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
				return (String.valueOf(id * maxMinute.get(id)));
			}
		}
		throw new RuntimeException("Could not get the minute based on the max amount of sleep.");
	}

	/**
	 * Calculates sleep schedules based on the log entries.
	 */
	private static Map<Integer, SleepSchedule> buildSleepSchedules(List<LogEntry> input) {
		Map<Integer, SleepSchedule> schedules = new HashMap<>();
		int currentGuard = 0;
		int sleepMinute = -1;
		int wakeMinute = -1;
		for (LogEntry logEntry : input) {
			if (logEntry.isOnDuty()) {
				// Guard has changed since previous id observation.
				currentGuard = logEntry.getId();
			}
			else if (logEntry.isSleeping()) {
				sleepMinute = logEntry.getMinute();
			}
			else if (logEntry.isWaking()) {
				wakeMinute = logEntry.getMinute();
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