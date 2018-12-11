package buri.aoc.viz;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Leaderboard {

	private static final String CURRENT_EVENT = "2018";
	private static final int TOTAL_PUZZLES = 25;

	private static final Map<Integer, String> DESCRIPTIONS = new HashMap<>();
	static {
		DESCRIPTIONS.put(1, "frequency adjustments");
		DESCRIPTIONS.put(2, "box IDs");
		DESCRIPTIONS.put(3, "fabric squares");
		DESCRIPTIONS.put(4, "sleeping guards");
		DESCRIPTIONS.put(5, "reducing polymers");
		DESCRIPTIONS.put(6, "Manhattan distances");
		DESCRIPTIONS.put(7, "step graph");
		DESCRIPTIONS.put(8, "license tree");
		DESCRIPTIONS.put(9, "marble game");
		DESCRIPTIONS.put(10, "sky writing");
		DESCRIPTIONS.put(11, "fuel cell charges");
		DESCRIPTIONS.put(12, "");
		DESCRIPTIONS.put(13, "");
		DESCRIPTIONS.put(14, "");
		DESCRIPTIONS.put(15, "");
		DESCRIPTIONS.put(16, "");
		DESCRIPTIONS.put(17, "");
		DESCRIPTIONS.put(18, "");
		DESCRIPTIONS.put(19, "");
		DESCRIPTIONS.put(20, "");
		DESCRIPTIONS.put(21, "");
		DESCRIPTIONS.put(22, "");
		DESCRIPTIONS.put(23, "");
		DESCRIPTIONS.put(24, "");
		DESCRIPTIONS.put(25, "");
	}
	@Test
	public void visualizeLeaderboard() {
		visualizeEvent("105906.json");
//		visualizeEvent("2017.json");
//		visualizeEvent("2016.json");
	}
	
	private static void visualizeEvent(String filename) {
		Map<String, Object> members = null;
		String event = null;
		String lastModified = null;
		// Read JSON
		try {
			ObjectMapper mapper = new ObjectMapper();
			File file = new File("data/viz/" + filename);
			lastModified = Date.from(Instant.ofEpochMilli(file.lastModified())).toString();
			JsonNode json = mapper.readTree(file);
			event = json.get("event").asText();
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
					Record record = new Record(name, timestamp);
					if (record.occurredInYear(Integer.valueOf(event))) {
						puzzleRecords.get(Integer.valueOf(dayKey) - 1).add(record);
					}
				}
			}
		}

		// Show the top finishes on each day.
		String title = "Novetta Advent of Code - Top 10 Solve Times";
		StringBuffer buffer = new StringBuffer();
		
		// Head
		buffer.append("<html>\n<head><title>").append(title).append(" (").append(event).append(")").append("</title>\n");
		buffer.append("<style>\n");
		buffer.append("\tbody { background-color: #0f0f23; color: #cccccc; font-family: monospace; font-size: 11pt; }\n");
		buffer.append("\t.tiny { font-size: 9pt; }\n");
		buffer.append("\ta { color: #009900; }\n");
		buffer.append("\ta:hover { color: #99ff99; }\n");
		buffer.append("</style>\n</head>\n\n<body>\n");
		buffer.append("<h2>").append(title).append(" (").append(event).append(")").append("</h2>\n\n");
		buffer.append("<p class=\"tiny\">JSON downloaded from ");
		buffer.append("<a href=\"https://adventofcode.com/").append(event).append("/leaderboard/private/view/105906\">Novetta Leaderboard</a>");
		buffer.append(" on <b>").append(lastModified).append("</b>.</p>\n\n");
		
		// Nav Bar
		buffer.append("<p>");
		buffer.append(event.equals("2018") ? event : "<a href=\"index.html\">2018</a>");
		buffer.append(" | ");
		buffer.append(event.equals("2017") ? event : "<a href=\"index-2017.html\">2017</a>");
		buffer.append(" | ");
		buffer.append(event.equals("2016") ? event : "<a href=\"index-2016.html\">2016</a>");
		buffer.append("<p>\n\n");
		
		for (int i = TOTAL_PUZZLES - 1; i >= 0; i--) {
			List<Record> places = puzzleRecords.get(i);
			if (!places.isEmpty()) {
				Collections.sort(places);
				buffer.append("\n<h3>Day ").append(i + 1);
				if (event.equals("2018") && DESCRIPTIONS.get(i + 1) != "") {
					buffer.append(" (").append(DESCRIPTIONS.get(i + 1)).append(")");
				}
				buffer.append("</h3>\n<ol>\n");
				int numPlaces = Math.min(10, places.size());
				for (int place = 0; place < numPlaces; place++) {
					Record record = places.get(place);
					buffer.append("\t<li>").append(record.getPrettyTime(i + 1)).append(" - ");
					buffer.append(record.getName()).append("</li>\n");
				}
				buffer.append("</ol>\n");
			}
		}
		buffer.append("\n<p><a href=\"#\">Jump to Top</a></p>");
		buffer.append("</body>\n</html>");
		
		// Save to file.
		try {
			String outputFilename = (event.equals(CURRENT_EVENT) ? "index.html" : "index-" + event + ".html");
			Files.write(Paths.get("output/" + outputFilename), buffer.toString().getBytes());
		}
		catch (IOException e) {
			throw new IllegalArgumentException("Invalid output file", e);
		}
	}
}
