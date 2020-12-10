package buri.aoc.viz;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Creates a table of part 1 / 2 times for comparison.
 *
 * @author Brian Uri!
 */
public class TimeAnalyzer {
	private static final String JSON_FOLDER = "data/viz/";

	@Test
	public void analyzeTimes() {
		analyzeTimes(2020);
	}

	/**
	 * Generates the table for a specific year
	 */
	private static void analyzeTimes(int year) {
		final Map<String, Object> leaderboardJson = readLeaderboard(year);
		analyzePuzzleTimes(year, leaderboardJson);
	}

	/**
	 * Reads the raw leaderboard.
	 */
	private static Map<String, Object> readLeaderboard(int year) {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> leaderboardJson = null;
		try {
			File file = new File(JSON_FOLDER + year + ".json");
			JsonNode json = mapper.readTree(file);
			leaderboardJson = mapper.readValue(json.get("members").toString(),
				new TypeReference<Map<String, Object>>() {});

			// Merge in overflow leaderboard, if available.
			File overflowFile = new File(JSON_FOLDER + year + "-1.json");
			if (overflowFile.exists()) {
				json = mapper.readTree(overflowFile);
				Map<String, Object> overflow = mapper.readValue(json.get("members").toString(),
					new TypeReference<Map<String, Object>>() {});
				for (String key : overflow.keySet()) {
					if (!leaderboardJson.containsKey(key)) {
						leaderboardJson.put(key, overflow.get(key));
					}
				}
			}
		}
		catch (IOException e) {
			throw new IllegalArgumentException("Invalid file.", e);
		}
		return (leaderboardJson);
	}

	/**
	 * Analyzes puzzle completion times from the leaderboard.
	 */
	private static void analyzePuzzleTimes(int year, Map<String, Object> leaderboardJson) {
		System.out.println("Name\tDay\tTotal\tPart 1\tPart 2\tPart 2 %");
		for (String key : leaderboardJson.keySet()) {
			Map<String, Object> member = (Map) leaderboardJson.get(key);
			String name = (String) member.get("name");
			Map<String, Object> puzzleData = (Map) member.get("completion_day_level");
			for (String dayKey : puzzleData.keySet()) {
				Map<String, Object> part1Data = (Map) ((Map) puzzleData.get(dayKey)).get("1");
				PuzzleTime record1 = null;
				if (part1Data != null) {
					long unixTime = Long.valueOf((String) part1Data.get("get_star_ts"));
					record1 = new PuzzleTime(year, Integer.valueOf(dayKey), name, unixTime);
				}
				Map<String, Object> part2Data = (Map) ((Map) puzzleData.get(dayKey)).get("2");
				PuzzleTime record2 = null;
				if (part2Data != null) {
					long unixTime = Long.valueOf((String) part2Data.get("get_star_ts"));
					record2 = new PuzzleTime(year, Integer.valueOf(dayKey), name, unixTime);
				}
				// Ignore records where part 1 took longer than 1 hour, or part 2 was never finished
				if (record1.getTimeCompleted() > 3600000L || part2Data == null) {
					continue;
				}
				System.out.print(name + "\t" + dayKey + "\t");
				System.out.print(record2.getFormattedTime() + "\t");
				System.out.print(record1.getFormattedTime() + "\t");
				long diff = record2.getTimeCompleted() - record1.getTimeCompleted();
				System.out.print(PuzzleTime.formatTime(diff) + "\t");
				long percentage = (long) (100.0 * diff / record2.getTimeCompleted());
				System.out.print("\t" + percentage + "%");
				System.out.println();
			}
		}
	}
}
