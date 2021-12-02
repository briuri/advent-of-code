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

import buri.aoc.viz.Novetta;
import buri.aoc.viz.OverallTimes;
import buri.aoc.viz.Puzzle;
import buri.aoc.viz.PuzzleTime;
import buri.aoc.viz.PuzzleTimes;

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
	public static final int TOTAL_PUZZLES = 25;

	// Invisible text used to reduce search engine discoverability.
	protected static final String ANTI_INDEX = "<span class=\"ai\">AoC</span>";

	// Folder containing JSON data files.
	private static final String JSON_FOLDER = "data/viz/json/";

	// Folder where pages are saved.
	private static final String OUTPUT_FOLDER = "data/viz/site/";

	// Known puzzle years
	protected static final String[] YEARS = new String[] { "2021", "2020", "2019", "2018", "2017", "2016" };

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
			List<String> exclusions = new ArrayList<>();
			ArrayNode exclusionsNode = (ArrayNode) json.get("exclusions").get(year);
			for (int i = 0; i < exclusionsNode.size(); i++) {
				exclusions.add(exclusionsNode.get(i).asText());
			}
			Novetta novetta = new Novetta(allDivisions, places, exclusions, rules);
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
	protected PuzzleTimes getPuzzleTimes(String year, Map<String, Object> leaderboardJson) {
		Novetta novetta = getNovettas().get(year);
		PuzzleTimes puzzleTimes = new PuzzleTimes();
		for (String key : leaderboardJson.keySet()) {
			Map<String, Object> member = (Map) leaderboardJson.get(key);
			String name = (String) member.get("name");
			if (name == null || novetta.getExclusions().contains(name)) {
				continue;
			}
			Map<String, Object> puzzleData = (Map) member.get("completion_day_level");
			for (String day : puzzleData.keySet()) {
				Long part1Time = getTime(TimeType.ONE, day, puzzleData);
				Long totalTime = getTime(TimeType.TOTAL, day, puzzleData);
				puzzleTimes.add(day, new PuzzleTime(year, day, name, part1Time, totalTime));
			}
		}
		puzzleTimes.sort();
		return (puzzleTimes);
	}

	/**
	 * Groups puzzle completion times by name for median/total calculations.
	 */
	protected List<OverallTimes> getOverallTimes(String year, PuzzleTimes puzzleTimes) {
		// Create an interim map of players to all of their puzzle times.
		Map<String, List<Long>> rawPuzzleTimes = new HashMap<>();
		for (int i = 0; i < puzzleTimes.getTimes(TimeType.TOTAL).size(); i++) {
			// Skip y18d06, since it was not included in AoC or Novetta calculations.
			if (!(year.equals("2018") && (i + 1) == 6)) {
				List<PuzzleTime> singleDay = puzzleTimes.getTimes(TimeType.TOTAL).get(i);
				for (PuzzleTime time : singleDay) {
					if (rawPuzzleTimes.get(time.getName()) == null) {
						rawPuzzleTimes.put(time.getName(), new ArrayList<>());
					}
					rawPuzzleTimes.get(time.getName()).add(time.getTime(TimeType.TOTAL));
				}
			}
		}
		for (List<Long> times : rawPuzzleTimes.values()) {
			Collections.sort(times);
		}

		boolean useMedian = !year.equals("2016");
		List<OverallTimes> overallTimes = new ArrayList<>();
		for (String name : rawPuzzleTimes.keySet()) {
			overallTimes.add(new OverallTimes(puzzleTimes, name, rawPuzzleTimes.get(name), useMedian));
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
	protected void writePage(String filename) {
		try {
			Files.write(Paths.get(OUTPUT_FOLDER + filename), getPage().toString().getBytes());
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
			buffer.append("\t<p class=\"tiny\">");
			buffer.append("(as of ").append(MODIFIED_DATE_FORMAT.format(new Date(file.lastModified()))).append(")");
			buffer.append("</p>\n");
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
	 * Gets a part 1 or total time in unix time from the raw leaderboard. Note that total time is listed as "2" in the
	 * JSON. Returns null if a value does not exist.
	 */
	private static Long getTime(TimeType type, String day, Map<String, Object> puzzleData) {
		if (type == TimeType.TWO) {
			throw new IllegalArgumentException("Part 2 split time not included in JSON.");
		}
		String timeKey = (type == TimeType.ONE ? "1" : "2");
		Map<String, Object> timeData = (Map) ((Map) puzzleData.get(day)).get(timeKey);

		Long unixTime = null;
		if (timeData != null) {
			String rawTime = timeData.get("get_star_ts").toString();
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
	 * Returns the fastest split time for either part 1 or part 2 in a day's puzzle.
	 */
	protected Long getFastestSplitTime(List<PuzzleTime> places, int maxPlaces, TimeType type) {
		if (type == TimeType.TOTAL) {
			throw new IllegalArgumentException("Total time is not a split time.");
		}
		List<Long> times = new ArrayList<>();
		for (PuzzleTime record : places.subList(0, maxPlaces)) {
			Long time = record.getTime(type);
			if (time != null) {
				times.add(record.getTime(type));
			}
		}
		Collections.sort(times);
		return (times.get(0));
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
