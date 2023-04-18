package buri.aoc.viz.common

import com.fasterxml.jackson.databind.node.ObjectNode

/**
 * Classes containing background data loaded from files.
 *
 * @author Brian Uri!
 */

/**
 * Ancillary player and division data in a single year.
 */
class Company(
    val divisionLabel: String, val allDivisions: List<String>,
    val maxPlaces: Int, val exclusions: List<String>, val rules: String
) {

    // Player names (which might be a username) mapped to their nicknames.
    private val nicknames = mutableMapOf<String, String>()

    // Nicknames mapped to their divisions.
    private val divisions = mutableMapOf<String, String>()

    // Nicknames mapped to their global leaderboard counts.
    private val globalCounts = mutableMapOf<String, Int>()

    // Nicknames that are ineligible for prizes.
    val ineligible = mutableSetOf<String>()

    /**
     * Adds a player record.
     */
    fun addPlayer(player: ObjectNode) {
        val username = player["username"].asText()
        if (player["nickname"] != null) {
            nicknames[username] = player["nickname"].asText()
        }
        val nickname = nicknames[username] ?: username
        if (player["division"] != null) {
            divisions[nickname] = player["division"].asText()
        }
        if (player["globalCount"] != null) {
            globalCounts[nickname] = player["globalCount"].asInt()
        }
        if (player["ineligible"] != null && player["ineligible"].asBoolean()) {
            ineligible.add(nickname)
        }
    }

    /**
     * Looks up a nickname for a player by username.
     */
    fun getNicknameFor(username: String): String {
        return nicknames[username] ?: username
    }

    /**
     * Looks up a division for a player by nickname.
     */
    fun getDivisionFor(nickname: String, addParentheses: Boolean): String {
        var division = divisions.getOrDefault(nickname, "")
        if (addParentheses && division.isNotEmpty()) {
            division = " ($division)"
        }
        return division
    }

    /**
     * Looks up the number of global ranks for a player by nickname.
     */
    fun getGlobalCountFor(nickname: String): Int {
        return globalCounts.getOrDefault(nickname, 0)
    }
}

/**
 * Metadata for each puzzle, read from puzzles.json.
 *
 * @author Brian Uri!
 */
class Puzzle(puzzleNode: ObjectNode) {
    // Puzzle title
    val title = puzzleNode["title"].asText()!!

    // List of people who made the global leaderboard.
    val globalNames = mutableListOf<String>()

    init {
        if (puzzleNode["globalNames"] != null) {
            globalNames.addAll(puzzleNode["globalNames"].map { it.asText() })
        }
    }

    companion object {
        // Total number of puzzles each year.
        const val TOTAL_PUZZLES = 25
    }
}