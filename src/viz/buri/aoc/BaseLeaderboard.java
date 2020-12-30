package buri.aoc;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import buri.aoc.viz.OverallTimes;
import buri.aoc.viz.Novetta;
import buri.aoc.viz.Puzzle;
import buri.aoc.viz.PuzzleTime;

/**
 * Base functionality for loading data to build the leaderboard. Rendering is in the inheriting class.
 *
 * @author Brian Uri!
 */
public abstract class BaseLeaderboard {
	private StringBuffer _page;
	private Map<String, List<Puzzle>> _puzzles;
	private Map<String, Novetta> _novettas;

	// Total number of puzzles each year.
	protected static final int TOTAL_PUZZLES = 25;

	// Invisible text used to reduce search engine discoverability.
	protected static final String ANTI_INDEX = "<span class=\"antiIndex\">AoC</span>";

	// Folder containing JSON data files.
	private static final String JSON_FOLDER = "data/viz/json/";

	// Folder where pages are saved.
	private static final String OUTPUT_FOLDER = "data/viz/site/";

	// Known puzzle years
	protected static final String[] YEARS = new String[] { "2020", "2019", "2018", "2017", "2016" };

	// Date format for the last update dates.
	private static final SimpleDateFormat MODIFIED_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	// Date format for 2016 - 2017 leaderboards (before Unix timestamps).
	private static final SimpleDateFormat LEGACY_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

	/**
	 * Constructor
	 */
	protected BaseLeaderboard() {
		_page = new StringBuffer();
		_puzzles = new HashMap<>();
		_novettas = new HashMap<>();

		readPuzzleMetadata();
		readNovettaMetadata();
	}

	/**
	 * Reads the puzzle metadata from the JSON file.
	 */
	private void readPuzzleMetadata() {
		JsonNode json = readJson("puzzles.json");
		for (String year : YEARS) {
			List<Puzzle> puzzles = new ArrayList<>();
			ArrayNode puzzleJson = (ArrayNode) json.get(year);
			for (int i = 0; i < puzzleJson.size(); i++) {
				puzzles.add(new Puzzle((ObjectNode) puzzleJson.get(i)));
			}
			getPuzzles().put(year, puzzles);
		}
	}

	/**
	 * Reads ancillary player and division data from the JSON file (not included in version control).
	 */
	private void readNovettaMetadata() {
		JsonNode json = readJson("novetta.json");
		for (String year : YEARS) {
			String rules = json.get("rules").get(year).asText();
			int places = json.get("places").get(year).asInt();
			List<String> allDivisions = new ArrayList<>();
			ArrayNode divisionJson = (ArrayNode) json.get("divisions").get(year);
			for (int i = 0; i < divisionJson.size(); i++) {
				allDivisions.add(divisionJson.get(i).asText());
			}
			Novetta novetta = new Novetta(allDivisions, places, rules);
			ArrayNode playerJson = (ArrayNode) json.get("players").get(year);
			for (int i = 0; i < playerJson.size(); i++) {
				novetta.addPlayer((ObjectNode) playerJson.get(i));
			}
			getNovettas().put(year, novetta);
		}
	}

	/**
	 * Reads the raw leaderboard data from the AoC leaderboard JSON files.
	 */
	protected Map<String, Object> readLeaderboards(String year) {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> leaderboardJson = null;
		try {
			JsonNode json = readJson(year + ".json");
			leaderboardJson = mapper.readValue(json.get("members").toString(),
				new TypeReference<Map<String, Object>>() {});

			// Merge in overflow leaderboard, if available.
			json = readJson(year + "-1.json");
			if (json != null) {
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
			throw new IllegalArgumentException("Invalid leaderboard JSON.", e);
		}
		return (leaderboardJson);
	}

	/**
	 * Loads puzzle completion times from the leaderboard JSON.
	 */
	protected List<List<PuzzleTime>> getPuzzleTimes(String year, Map<String, Object> leaderboardJson) {
		List<List<PuzzleTime>> puzzleTimes = new ArrayList<>();
		for (int day = 0; day < TOTAL_PUZZLES; day++) {
			puzzleTimes.add(new ArrayList<>());
		}
		for (String key : leaderboardJson.keySet()) {
			Map<String, Object> member = (Map) leaderboardJson.get(key);
			String name = (String) member.get("name");
			Map<String, Object> puzzleData = (Map) member.get("completion_day_level");
			for (String dayKey : puzzleData.keySet()) {
				long part1Time = getTime(Part.ONE, dayKey, puzzleData);
				long part2Time = getTime(Part.TWO, dayKey, puzzleData);
				if (part2Time > 0) {
					PuzzleTime record = new PuzzleTime(year, Integer.valueOf(dayKey), name, part2Time);
					if (record.completedInYear()) {
						puzzleTimes.get(Integer.valueOf(dayKey) - 1).add(record);
					}
				}
			}
		}
		for (int day = 0; day < TOTAL_PUZZLES; day++) {
			Collections.sort(puzzleTimes.get(day));
		}
		return (puzzleTimes);
	}

	/**
	 * Reads number of stars each player has from the leaderboard. Based on raw puzzle records because the cumulative
	 * "stars" field in the leaderboard JSON includes stars earned outside of Novetta's competition dates.
	 */
	protected Map<String, Integer> getStars(String year, Map<String, Object> leaderboardJson) {
		Map<String, Integer> stars = new HashMap<>();
		for (String key : leaderboardJson.keySet()) {
			Map<String, Object> member = (Map) leaderboardJson.get(key);
			String name = (String) member.get("name");
			Map<String, Object> puzzleData = (Map) member.get("completion_day_level");

			int count = 0;
			for (String dayKey : puzzleData.keySet()) {
				long part1Time = getTime(Part.ONE, dayKey, puzzleData);
				if (part1Time > 0) {
					PuzzleTime part1Record = new PuzzleTime(year, Integer.valueOf(dayKey), name, part1Time);
					if (part1Record.completedInYear()) {
						count++;
					}
				}
				long part2Time = getTime(Part.TWO, dayKey, puzzleData);
				if (part2Time > 0) {
					PuzzleTime part2Record = new PuzzleTime(year, Integer.valueOf(dayKey), name, part2Time);
					if (part2Record.completedInYear()) {
						count++;
					}
				}
			}
			stars.put(name, count);
		}
		return (stars);
	}

	/**
	 * Groups puzzle completion times by name for median/total calculations.
	 */
	protected List<OverallTimes> getOverallTimes(String year, List<List<PuzzleTime>> puzzleTimes,
		Map<String, Integer> stars) {
		// Create an interim map of players to all of their puzzle times.
		Map<String, List<Long>> rawPuzzleTimes = new HashMap<>();
		for (int i = 0; i < puzzleTimes.size(); i++) {
			// Skip y18d06, since it was not included in AoC or Novetta calculations.
			if (!(year.equals("2018") && i == 5)) {
				List<PuzzleTime> singleDay = puzzleTimes.get(i);
				for (PuzzleTime time : singleDay) {
					if (rawPuzzleTimes.get(time.getName()) == null) {
						rawPuzzleTimes.put(time.getName(), new ArrayList<>());
					}
					rawPuzzleTimes.get(time.getName()).add(time.getTimeCompleted());
				}
			}
		}
		for (List<Long> times : rawPuzzleTimes.values()) {
			Collections.sort(times);
		}

		boolean useMedian = !year.equals("2016");
		List<OverallTimes> overallTimes = new ArrayList<>();
		for (String name : rawPuzzleTimes.keySet()) {
			overallTimes.add(new OverallTimes(puzzleTimes, name, stars.get(name), rawPuzzleTimes.get(name), useMedian));
		}
		Collections.sort(overallTimes);
		return (overallTimes);
	}

	/**
	 * Clears the string buffer
	 */
	protected void resetPage() {
		getPage().setLength(0);
	}

	/**
	 * Saves the page to the filesystem.
	 */
	protected void writePage(String year, boolean isArchive) {
		try {
			String outputFilename = (isArchive ? "index-" + year + ".html" : "index.html");
			Files.write(Paths.get(OUTPUT_FOLDER + outputFilename), getPage().toString().getBytes());
		}
		catch (IOException e) {
			throw new IllegalArgumentException("Invalid output file.", e);
		}
	}

	/**
	 * Reads the last modified date on the (first) leaderboard file.
	 */
	protected String readLastModified(String year, String currentYear) {
		StringBuffer buffer = new StringBuffer();
		if (year.equals(currentYear)) {
			File file = new File(JSON_FOLDER + year + ".json");
			buffer.append("\t<p class=\"tiny\">(as of ").append(MODIFIED_DATE_FORMAT.format(new Date(file.lastModified()))).append(")</p>\n");
		}
		return (buffer.toString());
	}

	/**
	 * Reads arbitrary JSON from a file.
	 */
	private JsonNode readJson(String filename) {
		try {
			File file = new File(JSON_FOLDER + filename);
			return (file.exists() ? new ObjectMapper().readTree(file) : null);
		}
		catch (IOException e) {
			throw new IllegalArgumentException("Invalid file: " + filename, e);
		}
	}

	/**
	 * Gets a part 1 or part 2 time in unix time. Returns -1 if a value cannot be found.
	 */
	private static long getTime(Part part, String dayKey, Map<String, Object> puzzleData) {
		String partKey = (part == Part.ONE ? "1" : "2");
		Map<String, Object> partData = (Map) ((Map) puzzleData.get(dayKey)).get(partKey);

		long unixTime = -1;
		if (partData != null) {
			String rawTime = (String) partData.get("get_star_ts");
			if (rawTime.contains("T")) {
				try {
					unixTime = LEGACY_DATE_FORMAT.parse(rawTime).getTime() / 1000;
				}
				catch (ParseException e) {
					throw new IllegalArgumentException("Invalid date format: " + rawTime, e);
				}
			}
			else {
				unixTime = Long.valueOf(rawTime);
			}
		}
		return (unixTime);
	}

	/**
	 * Accessor for the page buffer
	 */
	protected StringBuffer getPage() {
		return _page;
	}

	/**
	 * Accessor for the puzzles metadata
	 */
	protected Map<String, List<Puzzle>> getPuzzles() {
		return _puzzles;
	}

	/**
	 * Accessor for the player / division data.
	 */
	protected Map<String, Novetta> getNovettas() {
		return _novettas;
	}
}
