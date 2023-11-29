package buri.aoc.viz.common

import buri.aoc.viz.common.TimeType.*
import java.util.*

/**
 * Classes related to solve times.
 *
 * @author Brian Uri!
 */

/**
 * Enumeration of solve times -- Part 1, Part 2 split, and Total.
 */
enum class TimeType { ONE, TWO, TOTAL }

/**
 * Data class for a single completion record in a daily puzzle. Part One is assumed to always be solved at a minimum.
 * If Part Two is not solved, the Part 2 split time and Total time will be null.
 */
class SolveTime(
    private val year: String, day: String,
    val name: String, rawPart1Time: Long, rawTotalTime: Long?
) : Comparable<SolveTime> {

    // Time for Part 1 since midnight
    private val part1Time: Long

    // Year that Part 1 was finished
    private val part1Year: String

    // Time for both parts since midnight (or null if unfinished)
    private val totalTime: Long?

    // Year that Part 2 was finished (or null if unfinished)
    private var part2Year: String?

    init {
        val midnight = Calendar.getInstance()
        midnight[Calendar.YEAR] = year.toInt()
        midnight[Calendar.MONTH] = Calendar.DECEMBER
        midnight[Calendar.DATE] = day.toInt()
        midnight[Calendar.HOUR_OF_DAY] = 0
        midnight[Calendar.MINUTE] = 0
        midnight[Calendar.SECOND] = 0
        midnight[Calendar.MILLISECOND] = 0

        val unixPart1Time = toUnixTime(rawPart1Time)
        part1Year = unixPart1Time[Calendar.YEAR].toString()
        part1Time = unixPart1Time.timeInMillis - midnight.timeInMillis
        if (rawTotalTime == null) {
            part2Year = null
            totalTime = null
        } else {
            val unixTotalTime = toUnixTime(rawTotalTime)
            part2Year = unixTotalTime[Calendar.YEAR].toString()
            totalTime = unixTotalTime.timeInMillis - midnight.timeInMillis
        }
    }

    /**
     * Returns true if the most recent part solved was completed in the same year it was released.
     */
    fun completedInYear(): Boolean {
        return if (getTime(TOTAL) != null) {
            year == part2Year
        } else {
            year == part1Year
        }
    }

    /**
     * Accessor for one of the timestamps on a puzzle solve: Part One split, Part Two split, or Total.
     */
    fun getTime(type: TimeType): Long? {
        return when (type) {
            ONE -> part1Time
            TOTAL -> totalTime
            else -> {
                if (totalTime == null) null else (totalTime - part1Time)
            }
        }
    }

    /**
     * Base on total solve time, then Part 1 orphans. Use last name for ties.
     */
    override fun compareTo(other: SolveTime): Int {
        var compare = if (getTime(TOTAL) != null) {
            val otherTime = other.getTime(TOTAL) ?: Long.MAX_VALUE
            getTime(TOTAL)!!.compareTo(otherTime)
        } else {
            getTime(ONE)!!.compareTo(other.getTime(ONE)!!)
        }
        // For ties, alphabetize on last name.
        if (compare == 0) {
            compare = compareName(name, other.name)
        }
        return compare
    }

    companion object {
        /**
         * Convert a timestamp in seconds to a Unix timestamp in milliseconds.
         */
        private fun toUnixTime(rawTime: Long): Calendar {
            val unixTime = Calendar.getInstance()
            unixTime.timeInMillis = rawTime * 1000L
            return unixTime
        }

        /**
         * Converts milliseconds into a string timestamp.
         * Standard timestamps allow 3 digits for the hour. 2016 sometimes had 4-digit hours.
         */
        fun formatTime(time: Long?, isStandardWidth: Boolean): String {
            val width = if (isStandardWidth) 3 else 4
            return formatTime(time, width)
        }

        /**
         * Converts milliseconds into a string timestamp.
         */
        private fun formatTime(rawTime: Long?, hourWidth: Int): String {
            val output = StringBuilder()
            if (rawTime != null) {
                var time = rawTime
                // Median timestamps may have half-second from average calculation. Round up.
                if (time % 1000L != 0L) {
                    time += 500L
                }

                time /= 1000L
                val hours = (time / (60 * 60)).toString()
                if (hours.length == 1) {
                    output.append("0")
                }
                output.append(hours).append(":")

                time %= (60 * 60)
                val minutes = (time / 60).toString()
                if (minutes.length == 1) {
                    output.append("0")
                }
                output.append(minutes).append(":")

                time %= 60
                val seconds = time.toString()
                if (seconds.length == 1) {
                    output.append("0")
                }
                output.append(seconds)
            }
            // Left-pad time. 2016 had 4-digit hours in All Players report.
            val padSize = hourWidth + 6 - output.length
            for (i in 0 until padSize) {
                output.insert(0, BaseLeaderboard.SPACE)
            }
            return output.toString()
        }

        /**
         * Sorts on a name field (might have first/last or a single word).
         */
        fun compareName(name1: String, name2: String): Int {
            val last1 = if (name1.indexOf(" ") != -1) name1.split(" ")[1] else name1
            val last2 = if (name2.indexOf(" ") != -1) name2.split(" ")[1] else name2
            return last1.compareTo(last2)
        }
    }
}

/**
 * Data class for all puzzle times in a competition year. Handles calculations for stars and orphan Part One records.
 */
class PuzzleTimes {

    // Part 1 solve times for each puzzle (list index + 1 is the puzzle's Day)
    private val part1Times = mutableListOf<MutableList<SolveTime>>()

    // Total solve times for each puzzle (list index + 1 is the puzzle's Day)
    private val totalTimes = mutableListOf<MutableList<SolveTime>>()

    // Total number of stars earned across all puzzles.
    var stars = 0
        private set

    /**
     * Constructor
     */
    init {
        for (i in 0 until Puzzle.TOTAL_PUZZLES) {
            getTimes(ONE).add(mutableListOf())
            getTimes(TOTAL).add(mutableListOf())
        }
    }

    /**
     * Adds a puzzle record to the appropriate list (ignores any outside of our company's competition window).
     */
    fun add(day: String, record: SolveTime) {
        val index = day.toInt() - 1
        if (record.getTime(TOTAL) != null && record.completedInYear()) {
            getTimes(TOTAL)[index].add(record)
            stars += 2
        } else if (record.completedInYear()) {
            getTimes(ONE)[index].add(record)
            stars += 1
        }
    }

    /**
     * Sorts each list of puzzle records.
     */
    fun sort() {
        for (day in 0 until Puzzle.TOTAL_PUZZLES) {
            getTimes(ONE)[day].sort()
            getTimes(TOTAL)[day].sort()
        }
    }

    /**
     * Counts the stars earned by a specific person during the competition.
     */
    fun getStars(name: String): Int {
        var count = 0
        for (times in getTimes(ONE)) {
            count += times.count { it.name == name && it.completedInYear() }
        }
        for (times in getTimes(TOTAL)) {
            count += times.count { it.name == name && it.completedInYear() } * 2
        }
        return count
    }

    /**
     * Accessor for all records of a type: those that only have Part One solved, and those with both parts solved.
     */
    fun getTimes(type: TimeType): MutableList<MutableList<SolveTime>> {
        require(type != TWO) { "Only Part One or Total times can be retrieved." }
        return if (type == ONE) part1Times else totalTimes
    }

    /**
     * Accessor for how many records of a type exist on a particular day.
     */
    fun getCount(type: TimeType, index: Int): Int {
        return getTimes(type)[index].size
    }
}

/**
 * Data class for all of a player's times.
 */
class PlayerTimes(puzzleTimes: PuzzleTimes, val name: String, val times: List<TiebreakerTime>, useMedian: Boolean) :
    Comparable<PlayerTimes> {

    // Total stars earned by this player.
    val stars = puzzleTimes.getStars(name)

    // Time to use in the event of star ties.
    val tiebreakerTime: Long

    // How many first-place medals this player has.
    val first: Int

    // How many second-place medals this player has.
    val second: Int

    // How many third-place medals this player has.
    val third: Int

    init {
        tiebreakerTime = if (useMedian) calculateMedianTime(times) else calculateTotalTime(times)
        first = puzzleTimes.getTimes(TOTAL).count { it.size >= 1 && it[0].name == name }
        second = puzzleTimes.getTimes(TOTAL).count { it.size >= 2 && it[1].name == name }
        third = puzzleTimes.getTimes(TOTAL).count { it.size >= 3 && it[2].name == name }
    }

    /**
     * Sort on number of stars, then tiebreaker time.
     */
    override fun compareTo(other: PlayerTimes): Int {
        var compare = -1 * stars.compareTo(other.stars)
        if (compare == 0) {
            compare = tiebreakerTime.compareTo(other.tiebreakerTime)
        }
        // For ties, alphabetize on last name.
        if (compare == 0) {
            compare = SolveTime.compareName(name, other.name)
        }
        return compare
    }

    /**
     * Returns true if at least 1 medal is found.
     */
    fun hasMedals(): Boolean = (first + second + third > 0)

    companion object {
        /**
         * Calculates the sum of the given times.
         */
        private fun calculateTotalTime(times: List<TiebreakerTime>): Long = times.sumOf { it.time }

        /**
         * Calculates the median of the given times.
         */
        private fun calculateMedianTime(times: List<TiebreakerTime>): Long {
            // Odd number of times, so median is middle time.
            if (times.size % 2 == 1) {
                return times[times.size / 2].time
            }

            // Otherwise, take the average of 2 middle times.
            val low = times[times.size / 2 - 1].time
            val high = times[times.size / 2].time
            var median = (high + low) / 2

            // Round up 0.5 seconds in average.
            if ((high + low) % 2 != 0L) {
                median++
            }
            return median
        }
    }
}

/**
 * Data class for a single tiebreaker time on one day.
 *
 * In 2016, the total time was used as a tiebreaker.
 * In 2017 and beyond, the median time is a tiebreaker.
 */
class TiebreakerTime(val day: Int, val time: Long) : Comparable<TiebreakerTime> {

    /**
     * Sort on tiebreaker time.
     */
    override fun compareTo(other: TiebreakerTime): Int = time.compareTo(other.time)
}