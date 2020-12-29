package buri.aoc;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import buri.aoc.viz.Puzzle;

/**
 * Base functionality for loading data to build the leaderboard. Calculation and rendering are in the inheriting class.
 *
 * @author Brian Uri!
 */
public abstract class BaseLeaderboard {
	private StringBuffer _page;
	private Map<String, List<Puzzle>> _puzzles;
	private Map<String, Novetta> _novettas;

	// Invisible text used to reduce search engine discoverability.
	protected static final String ANTI_INDEX = "<span class=\"antiIndex\">AoC</span>";

	// Folder containing JSON data files.
	private static final String JSON_FOLDER = "data/viz/json/";

	// Known puzzle years
	private static final String[] YEARS = new String[] { "2020", "2019", "2018", "2017", "2016", "2015" };

	// Date format for the last update dates.
	private static final SimpleDateFormat MODIFIED_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
			List<String> allDivisions = new ArrayList<>();
			ArrayNode divisionJson = (ArrayNode) json.get("divisions").get(year);
			for (int i = 0; i < divisionJson.size(); i++) {
				allDivisions.add(divisionJson.get(i).asText());
			}
			Novetta novetta = new Novetta(allDivisions);
			ArrayNode playerJson = (ArrayNode) json.get("players").get(year);
			for (int i = 0; i < playerJson.size(); i++) {
				novetta.addPlayer((ObjectNode) playerJson.get(i));
			}
			getNovettas().put(year, novetta);
		}
	}

	/**
	 * Reads the raw leaderboards.
	 */
	protected static Map<String, Object> readLeaderboard(String year) {
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
	 * Reads arbitrary JSON from a file.
	 */
	private static JsonNode readJson(String filename) {
		try {
			File file = new File(JSON_FOLDER + filename);
			return (file.exists() ? new ObjectMapper().readTree(file) : null);
		}
		catch (IOException e) {
			throw new IllegalArgumentException("Invalid file: " + filename, e);
		}
	}


	/**
	 * Reads the last modified date on the (first) leaderboard file.
	 */
	protected static String readLastModified(String year) {
		File file = new File(JSON_FOLDER + year + ".json");
		return MODIFIED_DATE_FORMAT.format(new Date(file.lastModified()));
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
