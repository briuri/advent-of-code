package buri.aoc.viz;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
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

/**
 * Alternate visualization of Novetta's private leaderboard showing the Top X Solve Times for each puzzle. Generated
 * from the API JSON.
 * 
 * @author Brian Uri!
 */
public class Leaderboard {
	private static final String CURRENT_EVENT = "2019";

	private static final int TOP_NUM = 10;
	private static final int TOTAL_PUZZLES = 25;

	private static final String JSON_FOLDER = "data/viz/";
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final String ANTI_INDEX = "<span class=\"antiIndex\">Advent of Code</span>";

	/**
	 * Generate the Top X Solve Times pages via a JUnit test in Eclipse.
	 */
	@Test
	public void generatePages() {
		visualizeEvent("2019");

		/*
		 * NOTE: Inactive accounts were purged from Novetta's leaderboard in 2019 to avoid the 200-player cap.
		 * Redownloading JSON from older years and regenerating their pages will result in missing scores.
		 */
//		visualizeEvent("2018");
//		visualizeEvent("2017");
//		visualizeEvent("2016");
//		visualizeEvent("2015");
	}

	/**
	 * Generates the Top X page for a specific event (4-digit year)
	 */
	private static void visualizeEvent(String event) {
		final Players players = readPlayers();
		final List<Puzzle> puzzles = readPuzzles(event);
		final Map<String, Object> leaderboardJson = readLeaderboard(event);
		final List<List<PuzzleTime>> puzzleTimes = getPuzzleTimes(event, leaderboardJson);
		final Map<String, Integer> stars = getStars(event, leaderboardJson);
		final List<MedianTime> medianTimes = getMedianTimes(event, puzzleTimes, stars);

		StringBuffer page = new StringBuffer();
		insertHeader(page, event);
		insertMedianTimes(page, event, players, medianTimes);
		insertPuzzleTimes(page, event, players, puzzleTimes, puzzles);
		insertFooter(page, event);

		try {
			String outputFilename = (event.equals(CURRENT_EVENT) ? "index.html" : "index-" + event + ".html");
			Files.write(Paths.get("output/" + outputFilename), page.toString().getBytes());
		}
		catch (IOException e) {
			throw new IllegalArgumentException("Invalid output file.", e);
		}
	}

	/**
	 * Reads the puzzle metadata from the JSON file.
	 */
	private static List<Puzzle> readPuzzles(String event) {
		List<Puzzle> puzzles = new ArrayList<>();
		try {
			File file = new File(JSON_FOLDER + "puzzles.json");
			JsonNode json = new ObjectMapper().readTree(file);
			ArrayNode puzzleJson = (ArrayNode) json.get(event);
			for (int i = 0; i < puzzleJson.size(); i++) {
				puzzles.add(new Puzzle((ObjectNode) puzzleJson.get(i)));
			}
			return (puzzles);
		}
		catch (IOException e) {
			throw new IllegalArgumentException("Invalid puzzle file.", e);
		}
	}

	/**
	 * Reads ancillary player data from the file (not included in version control).
	 */
	private static Players readPlayers() {
		Players players = new Players();
		try {
			File file = new File(JSON_FOLDER + "players.json");
			JsonNode json = new ObjectMapper().readTree(file);
			ArrayNode playerJson = (ArrayNode) json.get("players");
			for (int i = 0; i < playerJson.size(); i++) {
				players.add((ObjectNode) playerJson.get(i));
			}
			return (players);
		}
		catch (IOException e) {
			throw new IllegalArgumentException("Invalid players file.", e);
		}
	}

	/**
	 * Reads the raw leaderboard.
	 */
	private static Map<String, Object> readLeaderboard(String event) {
		Map<String, Object> leaderboardJson = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			File file = new File(JSON_FOLDER + event + ".json");
			JsonNode json = mapper.readTree(file);
			leaderboardJson = mapper.readValue(json.get("members").toString(),
				new TypeReference<Map<String, Object>>() {});
			return (leaderboardJson);
		}
		catch (IOException e) {
			throw new IllegalArgumentException("Invalid file.", e);
		}
	}

	/**
	 * Reads the last modified date on the leaderboard file.
	 */
	private static String readLastModified(String event) {
		File file = new File(JSON_FOLDER + event + ".json");
		return DATE_FORMAT.format(new Date(file.lastModified()));
	}

	/**
	 * Reads puzzle completion times from the leaderboard.
	 */
	private static List<List<PuzzleTime>> getPuzzleTimes(String event, Map<String, Object> leaderboardJson) {
		List<List<PuzzleTime>> puzzleTimes = new ArrayList<>();
		for (int day = 0; day < TOTAL_PUZZLES; day++) {
			puzzleTimes.add(new ArrayList<>());
		}
		for (String key : leaderboardJson.keySet()) {
			Map<String, Object> member = (Map) leaderboardJson.get(key);
			String name = (String) member.get("name");
			Map<String, Object> puzzleData = (Map) member.get("completion_day_level");
			for (String dayKey : puzzleData.keySet()) {
				Map<String, Object> part2Data = (Map) ((Map) puzzleData.get(dayKey)).get("2");
				if (part2Data != null) {
					long timestamp = Long.valueOf((String) part2Data.get("get_star_ts"));
					PuzzleTime record = new PuzzleTime(event, Integer.valueOf(dayKey), name, timestamp);
					puzzleTimes.get(Integer.valueOf(dayKey) - 1).add(record);
				}
			}
		}
		for (int day = 0; day < TOTAL_PUZZLES; day++) {
			Collections.sort(puzzleTimes.get(day));
		}
		return (puzzleTimes);
	}

	/**
	 * Reads number of stars from the leaderboard.
	 */
	private static Map<String, Integer> getStars(String event, Map<String, Object> leaderboardJson) {
		Map<String, Integer> stars = new HashMap<>();
		for (String key : leaderboardJson.keySet()) {
			Map<String, Object> member = (Map) leaderboardJson.get(key);
			stars.put((String) member.get("name"), (int) member.get("stars"));
		}
		return (stars);
	}

	/**
	 * Groups puzzle completion times by name for median calculations.
	 */
	private static List<MedianTime> getMedianTimes(String event, List<List<PuzzleTime>> puzzleTimes,
		Map<String, Integer> stars) {
		if (!event.equals(CURRENT_EVENT)) {
			return (new ArrayList<>());
		}
		// Create an interim map of players to all of their puzzle times.
		Map<String, List<String>> rawPuzzleTimes = new HashMap<>();
		for (List<PuzzleTime> singleDay : puzzleTimes) {
			for (PuzzleTime time : singleDay) {
				if (rawPuzzleTimes.get(time.getName()) == null) {
					rawPuzzleTimes.put(time.getName(), new ArrayList<>());
				}
				rawPuzzleTimes.get(time.getName()).add(time.getPrettyTime(true));
			}
		}
		List<MedianTime> medianTimes = new ArrayList<>();
		for (String name : rawPuzzleTimes.keySet()) {
			medianTimes.add(new MedianTime(puzzleTimes, name, stars.get(name), rawPuzzleTimes.get(name)));
		}
		Collections.sort(medianTimes);
		return (medianTimes);
	}

	/**
	 * Looks up the alternate name of the player, if available, and also obfuscates name to deter robots.
	 */
	private static String maskName(Players players, String name) {
		StringBuffer buffer = new StringBuffer(players.getAlternateName(name));
		buffer.insert(buffer.indexOf(" ") + 2, ANTI_INDEX);
		buffer.insert(1, ANTI_INDEX);
		return (buffer.toString());
	}

	/**
	 * Looks up a player's division, if available.
	 */
	private static String getDivision(Players players, String name) {
		String division = players.getDivision(name);
		if (division.length() > 0) {
			division = " (" + division + ")";
		}
		return (division);
	}

	/**
	 * Adds the HTML page header
	 */
	private static void insertHeader(StringBuffer page, String event) {
		page.append("<html>\n<head>\n");
		page.append("<meta charset=\"UTF-8\">");
		page.append("<meta name=\"viewport\" content=\"width=device-width,initial-scale=1\">\n");
		page.append("<title>Novetta Advent of Code - Top ").append(TOP_NUM).append(" Solve Times");
		page.append(" (").append(event).append(")").append("</title>\n");
		page.append("<style>\n");
		page.append("\tbody { background-color: #0f0f23; color: #cccccc; font-family: monospace; font-size: 10pt; }\n");
		page.append("\ta { color: #009900; }\n");
		page.append("\ta:hover { color: #99ff99; }\n");
		page.append("\ta:link { text-decoration: none; }\n");
		page.append("\th1 { font-size: 12pt; }\n");
		page.append("\th3 { font-size: 11pt; margin-bottom: 0px; }\n");
		page.append("\tli.median { margin-bottom: 5px; }\n");
		page.append("\tspan.median { color: #ffffff; text-shadow: 0 0 5px #ffffff; }\n");
		page.append("\tspan.medianLink:hover { color: #99ff99; cursor: pointer; *cursor: hand; }\n");
		page.append("\t.antiIndex { display: none; }\n");
		page.append("\t.details { display: none; margin-bottom: 10px; }\n");
		page.append("\t.emoji { font-size: 8pt; }\n");
		page.append("\t.empty { font-size: 11pt; }\n");
		page.append("\t.global { color: #ffff00; }\n");
		page.append("\t.navBar { background-color: #1f1f43; font-size: 11pt; padding: 5px; }\n");
		page.append("\t.tiny { font-size: 9pt; }\n");
		page.append("</style>\n</head>\n\n<body>\n");
		page.append("<h1>Novetta Advent of Code - Top ").append(TOP_NUM).append(" Solve Times");
		page.append(" (").append(event).append(")").append("</h1>\n\n");

		page.append("<div class=\"navBar\">");
		page.append(event.equals("2019") ? event : "<a href=\"index.html\">2019</a>");
		page.append(" | ");
		page.append(event.equals("2018") ? event : "<a href=\"index-2018.html\">2018</a>");
		page.append(" | ");
		page.append(event.equals("2017") ? event : "<a href=\"index-2017.html\">2017</a>");
		page.append(" | ");
		page.append(event.equals("2016") ? event : "<a href=\"index-2016.html\">2016</a>");
		page.append(" | ");
		page.append(event.equals("2015") ? event : "<a href=\"index-2015.html\">2015</a>");
		page.append(" | ");
		page.append("<a href=\"https://adventofcode.com/").append(event).append("/leaderboard/private/view/105906\">");
		page.append("Leaderboard &rArr;</a>");
		page.append(" | ");
		page.append("<a href=\"https://novetta.slack.com/archives/advent-of-code\">Slack &rArr;</a>");
		page.append("</div>\n\n");
	}

	/**
	 * Adds the Top X Median Times during the current event.
	 */
	private static void insertMedianTimes(StringBuffer page, String event, Players players,
		List<MedianTime> medianTimes) {
		if (!event.equals(CURRENT_EVENT)) {
			return;
		}
		int numMedians = Math.min(12, medianTimes.size());
		page.append("<script type=\"text/javascript\">\n");
		page.append("function expand(place) {\n");
		page.append("\toldDisplay = document.getElementById('details' + place).style.display;\n");
		page.append("\tdocument.getElementById('details' + place).style.display =\n");
		page.append("\t\t(oldDisplay == 'block' ? 'none' : 'block');\n");
		page.append("\tdocument.getElementById('median' + place).style.color =\n");
		page.append("\t\t(oldDisplay == 'block' ? '#ffffff' : '#888800');\n");
		page.append("}\n");
		page.append("</script>\n");
		page.append("\n<h3>Top ").append(numMedians).append(" Overall");
		page.append(" (").append(readLastModified(event)).append(")</h3>\n");
		page.append("<p class=\"tiny\">Click median time to show/hide all times.</p>\n");
		page.append("<ol>\n");
		for (int i = 0; i < numMedians; i++) {
			MedianTime player = medianTimes.get(i);
			page.append("\t<li class=\"median\">");
			if (player.getMedianTime().length() == 8) {
				page.append("&nbsp;");
			}
			page.append("<span class=\"median medianLink\" id=\"median").append(i).append("\" title=\"Show/Hide All Times\"");
			page.append(" onClick=\"expand(").append(i).append(")\">");
			page.append(player.getMedianTime());
			page.append("</span>&nbsp;&nbsp;").append(maskName(players, player.getName()));
			page.append(getDivision(players, player.getName())).append("<br />\n");
			for (int j = 0; j < 12; j++) {
				page.append("&nbsp;");
			}
			page.append(player.getStars()).append("<span class=\"emoji\" title=\"Stars\">&#x2B50;</span> ");
			if (player.hasMedals()) {
				page.append(player.getFirst()).append("<span class=\"emoji\" title=\"1st Place\">&#x1F947;</span> ");
				page.append(player.getSecond()).append("<span class=\"emoji\" title=\"2nd Place\">&#x1F948;</span> ");
				page.append(player.getThird()).append("<span class=\"emoji\" title=\"3rd Place\">&#x1F949;</span> ");
			}
			int globalCount = players.getGlobalCount(player.getName());
			if (globalCount > 0) {
				page.append(globalCount).append("<span class=\"emoji\" title=\"Global Leaderboard\">&#x1F30E;</span> ");
			}
			page.append("\n<div class=\"details\" id=\"details").append(i).append("\">\n");
			int totalTimes = player.getTimes().size();
			for (int j = 0; j < totalTimes; j++) {
				String time = player.getTimes().get(j);
				page.append("\t");
				if (time.length() == 8) {
					page.append("&nbsp;");
				}
				
				// Averaged median
				if (totalTimes % 2 == 0 && (j == totalTimes / 2 - 1)) {
					page.append("<span class=\"median\">").append(time).append("</span>");
					page.append("&nbsp;&nbsp;average is");
				}
				// Single median
				else if (j == totalTimes / 2)  {
					page.append("<span class=\"median\">").append(time).append("</span>");
					page.append("&nbsp;&nbsp;current median");
				}
				// 13th median (superceded by current median once event is complete).
				else if (j == 12) {
					page.append("<span class=\"median\">").append(time).append("</span>");
					page.append("&nbsp;&nbsp;13th fastest median");
				}
				else {
					page.append(time);
				}
				page.append("<br />\n");
			}
			page.append("</div>\n");
			page.append("</li>\n");
		}
		page.append("</ol>\n");
	}

	/**
	 * Adds the Top X times for each puzzle.
	 */
	private static void insertPuzzleTimes(StringBuffer page, String event, Players players,
		List<List<PuzzleTime>> puzzleTimes, List<Puzzle> puzzles) {
		boolean allEmpty = true;
		for (int i = TOTAL_PUZZLES - 1; i >= 0; i--) {
			List<PuzzleTime> places = puzzleTimes.get(i);
			if (!places.isEmpty()) {
				allEmpty = false;
				int day = i + 1;
				page.append("<a name=\"day").append(day).append("\"></a>");
				page.append("<h3><a href=\"https://adventofcode.com/").append(event).append("/day/").append(day);
				page.append("\">").append(puzzles.get(i).getTitle()).append("</a></h3>\n");
				page.append("<ol>\n");
				for (int place = 0; place < Math.min(TOP_NUM, places.size()); place++) {
					PuzzleTime record = places.get(place);
					String time = record.getPrettyTime(false);
					page.append("\t<li>");					
					if (place + 1 <= puzzles.get(i).getGlobalCount()) {
						page.append("<a href=\"https://adventofcode.com/").append(event);
						page.append("/leaderboard/day/").append(day).append("\"><sup class=\"global\">*</sup></a>");
					}
					else if (time.length() == 8) {
						page.append("&nbsp;");
					}
					page.append(time);
					page.append("&nbsp;&nbsp;").append(maskName(players, record.getName())).append("</li>\n");
				}
				page.append("</ol>\n");
			}
		}
		if (allEmpty) {
			page.append("<p class=\"empty\">No times recorded yet.</p>");
		}
	}

	/**
	 * Adds the HTML page footer
	 */
	private static void insertFooter(StringBuffer page, String event) {
		page.append("<div class=\"navBar\"><a href=\"#\">Jump to Top</a></div>");
		page.append("<p class=\"tiny\"><sup class=\"global\">*</sup> Top 100 on the daily Global Leaderboard<br />");
		page.append("&nbsp;&nbsp;last update on ").append(readLastModified(event)).append("</p>\n");
		page.append("</body>\n</html>");
	}
}
