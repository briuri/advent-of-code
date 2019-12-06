package buri.aoc.viz;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
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
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Leaderboard {
	private static final int TOTAL_PUZZLES = 25;
	
	@Test
	public void visualizeLeaderboard() {
		visualizeEvent("2019");
		// NOTE: Accounts were removed in 2019 to avoid the 200-player cap.
		// Redownloading JSON from older years may result in leaderboards with missing scores.
//		visualizeEvent("2018");
//		visualizeEvent("2017");
//		visualizeEvent("2016");
//		visualizeEvent("2015");
	}
	
	private static void visualizeEvent(String event) {
		final String currentEvent = "2019";
		final String pageTitle = "Novetta Advent of Code - Top 10 Solve Times";
		final List<Metadata> metadata = readMetadata(event);
		
		// Read leaderboard.
		Map<String, Object> members = null;
		String lastModified = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			File file = new File("data/viz/" + event + ".json");
			lastModified = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date.from(Instant.ofEpochMilli(file.lastModified())));
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
					puzzleRecords.get(Integer.valueOf(dayKey) - 1).add(record);
				}
			}
		}

		// Generate the HTML page.		
		StringBuffer buffer = new StringBuffer();
		
		// Head
		buffer.append("<html>\n<head>\n");
		buffer.append("<meta name=\"viewport\" content=\"width=device-width,initial-scale=1\">\n");
		buffer.append("<title>").append(pageTitle).append(" (").append(event).append(")").append("</title>\n");
		buffer.append("<style>\n");
		buffer.append("\tbody { background-color: #0f0f23; color: #cccccc; font-family: monospace; font-size: 10pt; }\n");
		buffer.append("\th1 { font-size: 12pt; }\n");
		buffer.append("\th3 { font-size: 11pt; margin-bottom: 0px; }\n");
		buffer.append("\ta { color: #009900; }\n");
		buffer.append("\ta:hover { color: #99ff99; }\n");
		buffer.append("\t.navBar { background-color: #1f1f43; font-size: 11pt; padding: 5px; }\n");
		buffer.append("\t.empty { font-size: 11pt; }\n");
		buffer.append("\t.tiny { font-size: 9pt; }\n");
		buffer.append("\t.global { color: #ffff00; }\n");
		buffer.append("</style>\n</head>\n\n<body>\n");
		buffer.append("<h1>").append(pageTitle).append(" (").append(event).append(")").append("</h1>\n\n");

		// Nav Bar
		buffer.append("<div class=\"navBar\">");
		buffer.append(event.equals("2019") ? event : "<a href=\"index.html\">2019</a>");
		buffer.append(" | ");
		buffer.append(event.equals("2018") ? event : "<a href=\"index-2018.html\">2018</a>");
		buffer.append(" | ");
		buffer.append(event.equals("2017") ? event : "<a href=\"index-2017.html\">2017</a>");
		buffer.append(" | ");
		buffer.append(event.equals("2016") ? event : "<a href=\"index-2016.html\">2016</a>");
		buffer.append(" | ");
		buffer.append(event.equals("2015") ? event : "<a href=\"index-2015.html\">2015</a>");
		buffer.append(" | ");
		buffer.append("<a href=\"https://adventofcode.com/").append(event).append("/leaderboard/private/view/105906\">");
		buffer.append("Leaderboard &rArr;</a>");
		buffer.append(" | ");
		buffer.append("<a href=\"https://novetta.slack.com/archives/advent-of-code\">Slack &rArr;</a>");
		buffer.append("</div>\n\n");
				
		boolean allEmpty = true;
		for (int i = TOTAL_PUZZLES - 1; i >= 0; i--) {
			List<Record> places = puzzleRecords.get(i);
			if (!places.isEmpty()) {
				allEmpty = false;
				int day = i + 1;
				Collections.sort(places);
				buffer.append("\n<a name=\"day").append(day).append("\"></a>");
				buffer.append("<h3><a href=\"https://adventofcode.com/").append(event).append("/day/").append(day);
				buffer.append("\">").append(metadata.get(i).getTitle()).append("</a></h3>\n");
				buffer.append("<ol>\n");
				int numPlaces = Math.min(10, places.size());
				for (int place = 0; place < numPlaces; place++) {
					Record record = places.get(place);
					buffer.append("\t<li>").append(record.getPrettyTime(day, event));
					if (place + 1 <= metadata.get(i).getGlobalCount()) {
						buffer.append("<sup class=\"global\">*</sup>");
					}
					else {
						buffer.append("&nbsp;");
					}
					buffer.append(" - ").append(record.getName()).append("</li>\n");
				}
				buffer.append("</ol>\n");
			}
		}
		if (allEmpty) {
			buffer.append("<p class=\"empty\">No times recorded yet.</p>");
		}
		else {
			buffer.append("<div class=\"navBar\">");
			buffer.append("<a href=\"#\">Jump to Top</a>\n");
			buffer.append("</div>");
		}
		buffer.append("<p class=\"tiny\"><sup class=\"global\">*</sup> Top 100 on the daily Global Leaderboard<br />");			
		buffer.append("&nbsp;&nbsp;last update on ").append(lastModified).append("</p>\n");
		buffer.append("</body>\n</html>");

		// Save to file.
		try {
			String outputFilename = (event.equals(currentEvent) ? "index.html" : "index-" + event + ".html");
			Files.write(Paths.get("output/" + outputFilename), buffer.toString().getBytes());
		}
		catch (IOException e) {
			throw new IllegalArgumentException("Invalid output file", e);
		}
	}
	
	
	/**
	 * Reads the metadata from the file.
	 */
	private static List<Metadata> readMetadata(String event) {
		List<Metadata> metadata = new ArrayList<>();
		try {
			ObjectMapper mapper = new ObjectMapper();
			File file = new File("data/viz/metadata.json");
			JsonNode json = mapper.readTree(file);
			ArrayNode metadataJson = (ArrayNode) json.get(event);
			for (int i = 0; i < metadataJson.size(); i++) {
				ObjectNode puzzleMetadata = (ObjectNode) metadataJson.get(i);
				metadata.add(new Metadata(puzzleMetadata.get("title").asText(), puzzleMetadata.get(
					"globalCount").asInt()));
			}
			return (metadata);
		}
		catch (IOException e) {
			throw new IllegalArgumentException("Invalid metadata file.", e);
		}
	}
}
