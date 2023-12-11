package buri.aoc.viz.common

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Base functionality for loading data to build the Rankings Page. Rendering is in the inheriting class.
 *
 * @author Brian Uri!
 */
abstract class BaseRankingsPage protected constructor() {
    // Page-writing buffer
    protected val page = StringBuilder()

    // Metadata about puzzles, grouped by year
    protected val allPuzzles = mutableMapOf<String, List<Puzzle>>()

    // Metadata about players and divisions, grouped by year
    protected val companies = mutableMapOf<String, Company>()

    // Yearly solve times for all puzzles in the year
    protected val puzzleTimes = mutableMapOf<String, PuzzleTimes>()

    // Yearly solve times grouped by player rather than puzzle
    protected val playerTimes = mutableMapOf<String, List<PlayerTimes>>()

    init {
        readPuzzleMetadata()
        readCompanyMetadata()
        for (year in YEARS) {
            puzzleTimes[year] = getSolveTimes(year, readLeaderboards(year))
            playerTimes[year] = getPlayerTimes(year, puzzleTimes[year]!!)
        }
    }

    /**
     * Reads the puzzle metadata from the JSON file.
     */
    private fun readPuzzleMetadata() {
        val json = readJson("puzzles.json")!!
        for (year in YEARS) {
            val puzzles = mutableListOf<Puzzle>()
            val puzzleJson = json[year] as ArrayNode
            for (i in 0 until puzzleJson.size()) {
                puzzles.add(Puzzle((puzzleJson[i] as ObjectNode)))
            }
            allPuzzles[year] = puzzles
        }
    }

    /**
     * Reads ancillary player and division data from the JSON file (not included in version control).
     */
    private fun readCompanyMetadata() {
        val json = readJson("company.json")!!
        for (year in YEARS) {
            val divisionLabel = json["divisionLabel"][year].asText()
            val allDivisions = mutableListOf<String>()
            val divisionJson = json["divisions"][year] as ArrayNode
            for (i in 0 until divisionJson.size()) {
                allDivisions.add(divisionJson[i].asText())
            }

            val placesShown = json["places"][year].asInt()
            val exclusions = mutableListOf<String>()
            val exclusionsNode = json["exclusions"][year] as ArrayNode
            for (i in 0 until exclusionsNode.size()) {
                exclusions.add(exclusionsNode[i].asText())
            }
            val rules = json["rules"][year].asText()

            val company = Company(divisionLabel, allDivisions, placesShown, exclusions, rules)

            val playerJson = json["players"][year] as ArrayNode
            for (i in 0 until playerJson.size()) {
                company.addPlayer((playerJson[i] as ObjectNode))
            }
            companies[year] = company
        }
    }

    /**
     * Reads the raw leaderboard data from the AoC leaderboard JSON files.
     */
    private fun readLeaderboards(year: String): Map<String, Any> {
        val mapper = ObjectMapper()
        try {
            val typeRef = object : TypeReference<MutableMap<String, Any>>() {}
            val json = readJson("$year.json")!!
            val leaderboardJson = mapper.readValue(json["members"].toString(), typeRef)

            // Merge in overflow leaderboard, if available.
            val overflowJson = readJson("$year-1.json")
            if (overflowJson != null) {
                val overflow = mapper.readValue(overflowJson["members"].toString(), typeRef)
                for (key in overflow.keys) {
                    if (!leaderboardJson.containsKey(key)) {
                        leaderboardJson[key] = overflow[key]!!
                    }
                }
            }
            return leaderboardJson
        } catch (e: IOException) {
            throw IllegalArgumentException("Invalid leaderboard JSON.", e)
        }
    }

    /**
     * Loads puzzle completion times from the leaderboard JSON.
     */
    @Suppress("UNCHECKED_CAST")
    private fun getSolveTimes(year: String, leaderboardJson: Map<String, Any>): PuzzleTimes {
        val company = companies[year]!!
        val puzzleTimes = PuzzleTimes()
        for (key in leaderboardJson.keys) {
            val member = leaderboardJson[key] as Map<String, Any>
            var name = member["name"] as String?
            if ((name == null) || (name in company.exclusions)) {
                continue
            }
            // For some reason, "Manuel \"DZ\" Dominguez" doesn't work as a key in the alternateNames map.
            if (name.contains("\"DZ\"")) {
                name = "Manuel Dominguez"
            }
            val puzzleData = member["completion_day_level"] as Map<String, Any>
            for (day in puzzleData.keys) {
                val part1Time = getTime(TimeType.ONE, day, puzzleData)!!
                val totalTime = getTime(TimeType.TOTAL, day, puzzleData)
                puzzleTimes.add(day, SolveTime(year, day, company.getNicknameFor(name), part1Time, totalTime))
            }
        }
        puzzleTimes.sort()
        return puzzleTimes
    }

    /**
     * Gets a part 1 or total time in unix time from the raw leaderboard. Note that total time is listed as "2" in the
     * JSON. Returns null if a value does not exist.
     */
    @Suppress("UNCHECKED_CAST")
    private fun getTime(type: TimeType, day: String, puzzleData: Map<String, Any>): Long? {
        require(type != TimeType.TWO) { "Part 2 split time not included in JSON." }
        val timeKey = if (type == TimeType.ONE) "1" else "2"
        val outerMap = puzzleData[day] as Map<String, Any>
        val timeData = outerMap[timeKey] as Map<String, Any>?
        var unixTime: Long? = null
        if (timeData != null) {
            val rawTime = timeData["get_star_ts"].toString()
            unixTime = if (rawTime.contains("T")) {
                try {
                    LEGACY_DATE_FORMAT.parse(rawTime).time / 1000
                } catch (e: ParseException) {
                    throw IllegalArgumentException("Invalid date format: $rawTime", e)
                }
            } else {
                rawTime.toLong()
            }
        }
        return unixTime
    }

    /**
     * Groups puzzle completion times by name for median/total calculations.
     *
     * NOTE: This does not handle any player who has not yet completed Part 2 of any puzzle.
     */
    private fun getPlayerTimes(year: String, puzzleTimes: PuzzleTimes): List<PlayerTimes> {
        val company = companies[year]!!
        // Create an interim map of players to all of their puzzle times.
        val rawPuzzleTimes = mutableMapOf<String, MutableList<TiebreakerTime>>()
        for (i in puzzleTimes.getTimes(TimeType.TOTAL).indices) {
            val day = i + 1
            // Skip y18d06, since it was not included in AoC or our calculations.
            if (!(year == "2018" && day == 6)) {
                val singleDay = puzzleTimes.getTimes(TimeType.TOTAL)[i]
                for (time in singleDay) {
                    rawPuzzleTimes.putIfAbsent(time.name, mutableListOf())
                    val totalTime = time.getTime(TimeType.TOTAL)
                    if (totalTime != null) {
                        rawPuzzleTimes[time.name]!!.add(TiebreakerTime(day, totalTime))
                    }
                }
            }
        }
        for (times in rawPuzzleTimes.values) {
            times.sort()
        }

        val useMedian = (year != "2016")
        val playerTimes = mutableListOf<PlayerTimes>()
        for (name in rawPuzzleTimes.keys) {
            playerTimes.add(PlayerTimes(puzzleTimes, company.getNicknameFor(name), rawPuzzleTimes[name]!!, useMedian))
        }
        playerTimes.sort()
        return playerTimes
    }

    /**
     * Clears the page buffer
     */
    protected fun resetPage() {
        page.setLength(0)
    }

    /**
     * Saves the page to the filesystem.
     */
    protected fun writePage(filename: String) {
        try {
            Files.write(Paths.get(OUTPUT_FOLDER + filename), page.toString().toByteArray())
        } catch (e: IOException) {
            throw IllegalArgumentException("Invalid output file.", e)
        }
    }

    /**
     * Reads the last modified date on the (first) leaderboard file.
     */
    protected fun readLastModified(year: String): String {
        val output = StringBuilder()
        if (year == CURRENT_YEAR) {
            val file = File("$JSON_FOLDER$year.json")
            val date = MODIFIED_DATE_FORMAT.format(Date(file.lastModified()))
            output.append("\t<p class=\"tiny\">(as of $date)</p>\n")
        }
        return output.toString()
    }

    /**
     * Reads arbitrary JSON from a file.
     */
    private fun readJson(filename: String): JsonNode? {
        try {
            val file = File(JSON_FOLDER + filename)
            return if (file.exists()) ObjectMapper().readTree(file) else null
        } catch (e: IOException) {
            throw IllegalArgumentException("Invalid file: $filename", e)
        }
    }

    /**
     * Returns the fastest split time for either part 1 or part 2 in a day's puzzle. Returns 0 if no times are available.
     */
    protected fun getFastestSplitTime(places: List<SolveTime>, maxPlaces: Int, type: TimeType): Long {
        require(type != TimeType.TOTAL) { "Total time is not a split time." }
        val times = mutableListOf<Long>()
        for (record in places.subList(0, maxPlaces)) {
            val time = record.getTime(type)
            if (time != null) {
                times.add(time)
            }
        }
        times.sort()
        return if (times.isEmpty()) 0L else times[0]
    }

    companion object {
        // Current year
        const val CURRENT_YEAR = "2023"

        // Known puzzle years
        val YEARS = arrayOf("2023", "2022", "2021", "2020", "2019", "2018", "2017", "2016")

        // Date format for the last update dates.
        val MODIFIED_DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        // Invisible text used to reduce search engine discoverability.
        const val ANTI_INDEX = "<span class=\"ai\">AoC</span>"

        // Folder containing JSON data files.
        private const val JSON_FOLDER = "data/json/"

        // Folder where pages are saved.
        private const val OUTPUT_FOLDER = "data/site/"

        // Date format for 2016 - 2017 leaderboards (before Unix timestamps).
        private val LEGACY_DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")

        // HTML Space
        const val SPACE = "&nbsp;"
    }
}