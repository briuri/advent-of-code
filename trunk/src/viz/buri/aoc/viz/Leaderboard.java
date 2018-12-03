package buri.aoc.viz;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Leaderboard {

	private static final int TOTAL_PUZZLES = 25;

	@Test
	public void visualizeLeaderboard() {
		Map<String, Object> members = null;
		String lastModified = null;
		// Read JSON
		try {
			ObjectMapper mapper = new ObjectMapper();
			File file = new File("data/viz/105906.json");
			lastModified = Date.from(Instant.ofEpochMilli(file.lastModified())).toString();
			JsonNode json = mapper.readTree(file);
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
		String title = "Novetta Advent of Code 2018 - Top 10 Solve Times";
		StringBuffer buffer = new StringBuffer();
		buffer.append("<html>\n<head><title>").append(title).append("</title>\n");
		buffer.append("<style>\n");
		buffer.append("\tbody { background-color: #0f0f23; color: #cccccc; font-family: monospace; font-size: 12pt; }\n");
		buffer.append("\t.tiny { font-size: 10pt; }\n");
		buffer.append("\ta { color: #009900; }\n");
		buffer.append("\ta:hover { color: #99ff99; }\n");
		buffer.append("</style>\n</head>\n\n<body>\n");
		buffer.append("<h2>").append(title).append("</h2>\n\n");
		buffer.append("<p class=\"tiny\">");
		buffer.append("<a href=\"https://adventofcode.com/2018/leaderboard/private/view/105906\">Novetta Leaderboard</a>");
		buffer.append(" JSON downloaded on <b>").append(lastModified).append("</b></p>\n");
		for (int day = 0; day < TOTAL_PUZZLES; day++) {
			List<Record> places = puzzleRecords.get(day);
			if (!places.isEmpty()) {
				Collections.sort(places);
				buffer.append("<h3>Day ").append(day).append("</h3><ol>");
				for (int place = 0; place < 10; place++) {
					Record record = places.get(place);
					buffer.append("<li>").append(record.getPrettyTime()).append(" - ");
					buffer.append(record.getName()).append("</li>");
				}
				buffer.append("</ol>\n");
			}
		}
		buffer.append("</body>\n</html>");
		
		// Save to file.
		try {
			Files.write(Paths.get("output/index.html"), buffer.toString().getBytes());
		}
		catch (IOException e) {
			throw new IllegalArgumentException("Invalid output file", e);
		}
	}
}
