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

	@Test
	public void visualizeLeaderboard() {
		Map<String, Object> map = null;
		// Read JSON
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode json = mapper.readTree(new File("data/viz/105906.json"));
			map = mapper.readValue(json.get("members").toString(),
				new TypeReference<Map<String, Object>>() {});
		}
		catch (IOException e) {
			throw new IllegalArgumentException("Invalid file.", e);
		}
		
		// Create lists of daily records for people who completed each day.
		List<List<Record>> dailyRecords = new ArrayList<>();
		for (int i = 0; i < 25; i++) {
			dailyRecords.add(new ArrayList<>());
		}
		for (String key : map.keySet()) {
			Map<String, Object> member = (Map) map.get(key);
			String name = (String) member.get("name");
			Map<String, Object> completionData = (Map) member.get("completion_day_level");
			for (String dayKey : completionData.keySet()) {
				int day = Integer.valueOf(dayKey);
				Map<String, Object> dayData = (Map) completionData.get(dayKey);
				Map<String, Object> part2Data = (Map) dayData.get("2");
				if (part2Data != null) {
					long timestamp = Long.valueOf((String) part2Data.get("get_star_ts"));
					Record record = new Record(name, timestamp);
					dailyRecords.get(day).add(record);					
				}
			}
		}
		
		// Now show the top 3 finishes on each day.
		for (int i = 0; i < dailyRecords.size(); i++) {
			List<Record> day = dailyRecords.get(i);
			if (!day.isEmpty()) {
				System.out.println("Day " + i);
				Collections.sort(day);
				for (int j = 0; j < 10; j++) {
					System.out.print("\t" + (j + 1) + ": ");
					System.out.println(day.get(j).getPrettyTime() + "\t" + day.get(j).getName());
				}
			}
		}
	}
}
