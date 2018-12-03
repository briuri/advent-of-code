package buri.aoc.viz;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Leaderboard {

	private static final int TOTAL_PUZZLES = 25;
	private static final int TOP_PLACES = 10;

	@Test
	public void visualizeLeaderboard() {
		Map<String, Object> members = null;
		// Read JSON
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode json = mapper.readTree(new File("data/viz/105906.json"));
			members = mapper.readValue(json.get("members").toString(), new TypeReference<Map<String, Object>>() {});
		}
		catch (IOException e) {
			throw new IllegalArgumentException("Invalid file.", e);
		}

		// Create lists of daily records for people who completed each day.
		List<List<Record>> puzzleRecords = new ArrayList<>();
		for (int day = 0; day < TOTAL_PUZZLES; day++) {
			puzzleRecords.add(new ArrayList<>());
		}

		// Load the Part 2 completion timestamps for each member of the leaderboard.
		for (String key : members.keySet()) {
			Map<String, Object> member = (Map) members.get(key);
			String name = (String) member.get("name");
			Map<String, Object> puzzleData = (Map) member.get("completion_day_level");
			for (String dayKey : puzzleData.keySet()) {
				Map<String, Object> part2Data = (Map) ((Map) puzzleData.get(dayKey)).get("2");
				if (part2Data != null) {
					long timestamp = Long.valueOf((String) part2Data.get("get_star_ts"));
					puzzleRecords.get(Integer.valueOf(dayKey)).add(new Record(name, timestamp));
				}
			}
		}

		// Show the top finishes on each day.
		for (int day = 0; day < TOTAL_PUZZLES; day++) {
			List<Record> places = puzzleRecords.get(day);
			if (!places.isEmpty()) {
				Collections.sort(places);
				System.out.println(String.format("Day %d", day));
				for (int place = 0; place < TOP_PLACES; place++) {
					Record record = places.get(place);
					System.out.print(String.format("\t%1$-2s\t", place + 1));
					System.out.println(String.format("%s\t%s", record.getPrettyTime(), record.getName()));
				}
			}
		}
	}
}
