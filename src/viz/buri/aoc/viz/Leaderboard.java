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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Leaderboard {
	private static final int TOP_NUM = 10;
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
		final String pageTitle = "Novetta Advent of Code - Top " + TOP_NUM + " Solve Times";
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
		List<List<PuzzleRecord>> puzzleRecords = new ArrayList<>();
		for (int day = 0; day < TOTAL_PUZZLES; day++) {
			puzzleRecords.add(new ArrayList<>());
		}
		Map<String, List<String>> medianData = new HashMap<>(); 
		for (String key : members.keySet()) {
			Map<String, Object> member = (Map) members.get(key);
			String name = (String) member.get("name");
			Map<String, Object> puzzleData = (Map) member.get("completion_day_level");
			for (String dayKey : puzzleData.keySet()) {
				Map<String, Object> part2Data = (Map) ((Map) puzzleData.get(dayKey)).get("2");
				if (part2Data != null) {
					long timestamp = Long.valueOf((String) part2Data.get("get_star_ts"));
					PuzzleRecord record = new PuzzleRecord(name, event, Integer.valueOf(dayKey), timestamp);
					puzzleRecords.get(Integer.valueOf(dayKey) - 1).add(record);
					if (medianData.get(name) == null) {
						medianData.put(name, new ArrayList<>());
					}
					medianData.get(name).add(record.getPrettyTime(true));
				}
			}
		}

		// Generate the HTML page.		
		StringBuffer buffer = new StringBuffer();
		insertHeader(buffer, pageTitle, event);
		insertMeanTimes(buffer, medianData);
		
		// Insert Puzzle Times
		boolean allEmpty = true;
		for (int i = TOTAL_PUZZLES - 1; i >= 0; i--) {
			List<PuzzleRecord> places = puzzleRecords.get(i);
			if (!places.isEmpty()) {
				allEmpty = false;
				int day = i + 1;
				Collections.sort(places);
				buffer.append("\n<a name=\"day").append(day).append("\"></a>");
				buffer.append("<h3><a href=\"https://adventofcode.com/").append(event).append("/day/").append(day);
				buffer.append("\">").append(metadata.get(i).getTitle()).append("</a></h3>\n");
				buffer.append("<ol>\n");
				int numPlaces = Math.min(TOP_NUM, places.size());
				for (int place = 0; place < numPlaces; place++) {
					PuzzleRecord record = places.get(place);
					buffer.append("\t<li>").append(record.getPrettyTime(false));
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
		insertFooter(buffer, allEmpty, lastModified);

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
	
	/**
	 * Reads divisions from the file (not included in Git repository).
	 */
	private static Map<String, String> readDivisions() {
		Map<String, String> divisions = new HashMap<>();
		try {
			ObjectMapper mapper = new ObjectMapper();
			File file = new File("data/viz/divisions.json");
			JsonNode json = mapper.readTree(file);
			ArrayNode divJson = (ArrayNode) json.get("divisions");
			for (int i = 0; i < divJson.size(); i++) {
				ObjectNode participant = (ObjectNode) divJson.get(i);
				divisions.put(participant.get("name").asText(), participant.get("division").asText());
				System.out.println(participant.get("name").asText() + " " + participant.get("division").asText());
			}
		}
		catch (IOException e) {
			// Silently return an empty map.
		}
		return (divisions);
	}
	
	/**
	 * Adds the HTML page header
	 */
	private static void insertHeader(StringBuffer buffer, String pageTitle, String event) {
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
	}
	
	/**
	 * Generates a Top X list of mean times.
	 */
	private static void insertMeanTimes(StringBuffer buffer, Map<String, List<String>> times) {
		// Only do calculations for people with all puzzles completed so far.
		int puzzlesCompleted = 0;
		for (List<String> list : times.values()) {
			puzzlesCompleted = Math.max(list.size(), puzzlesCompleted);
		}		
		List<MedianRecord> records = new ArrayList<>();
		for (String name : times.keySet()) {
			if (times.get(name).size() == puzzlesCompleted) {
				Collections.sort(times.get(name));
				records.add(new MedianRecord(name, calculateMedianTime(times.get(name))));
			}
		}
		Collections.sort(records);
		Map<String, String> divisions = readDivisions();
		
		buffer.append("\n<h3>Top ").append(TOP_NUM).append(" Median Times (only participants with ");
		buffer.append(puzzlesCompleted * 2).append("*)</h3>\n");
		buffer.append("<ol>\n");
		int numPlaces = Math.min(TOP_NUM, records.size());
		for (int i = 0; i < numPlaces; i++) {
			MedianRecord record = records.get(i);
			buffer.append("\t<li>").append(record.getMedianTime());
			buffer.append("&nbsp; - ").append(insertDivision(record.getName(), divisions)).append("</li>\n");
		}
		buffer.append("</ol>\n");
	}
	
	/**
	 * Looks up the division of the participant, if available.
	 */
	private static String insertDivision(String name, Map<String, String> divisions) {
		String division = divisions.getOrDefault(name, "");
		if (division.length() > 0) {
			name = name + " (" + division + ")";
		}
		return (name);
	}
	
	/**
	 * Calculates the median of the given times.
	 */
	private static String calculateMedianTime(List<String> times) {
		if (times.size() % 2 == 1) {
			return (times.get(times.size() / 2));
		}
		int low = toSeconds(times.get(times.size() / 2 - 1));
		int high = toSeconds(times.get(times.size() / 2));
		int median = (high + low) / 2;
		// Round up 0.5 seconds.
		if ((high + low) % 2 != 0) {
			median++;
		}
		
		String hours = String.valueOf(median / (60 * 60));
		if (hours.length() == 1) {
			hours = "0" + hours;
		}
		if (hours.length() == 2) {
			hours = "&nbsp;" + hours;
		}
		
		median = median % (60 * 60);
		String minutes = String.valueOf(median / 60);
		if (minutes.length() == 1) {
			minutes = "0" + minutes;
		}
		median = median % 60;
		String seconds = String.valueOf(median);
		if (seconds.length() == 1) {
			seconds = "0" + seconds;
		}
		return (hours + ":" + minutes + ":" + seconds);
	}
	
	/**
	 * Converts a time in the format HH:MM:SS to seconds.
	 */
	private static int toSeconds(String time) {
		int seconds = Integer.valueOf(time.substring(6));
		seconds += 60 * Integer.valueOf(time.substring(3, 5));
		seconds += 60 * 60 * Integer.valueOf(time.substring(0, 2));
		return (seconds);
	}
	
	/**
	 * Adds the HTML page footer
	 */
	private static void insertFooter(StringBuffer buffer, boolean allEmpty, String lastModified) {
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
	}
}
