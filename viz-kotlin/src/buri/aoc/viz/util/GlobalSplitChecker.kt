package buri.aoc.viz.util

import buri.aoc.viz.common.BaseRankingsPage
import buri.aoc.viz.common.BaseRankingsPage.Companion.SPACE
import buri.aoc.viz.common.SolveTime
import java.io.File

/**
 * Simple utility to evaluate a global leaderboard and gather stats about times.
 *
 * @author Brian Uri!
 */
fun main() {
    // aoc-get-global.bat script grabs the desired leaderboard from the web.
    val year = "23"
    val day = "01"

    val firstHundredToken = "First hundred users"
    val pathPrefix = "C:\\workspace\\advent-of-code\\viz-kotlin\\data\\global"
    val lines = File("$pathPrefix\\y${year}d$day.htm").readLines()

    val part1Start = lines.indexOfLast { it.contains(firstHundredToken) }
    val part1End = lines.indexOfLast { it.contains("</main>") }
    val totalStart = lines.indexOfFirst { it.contains(firstHundredToken) }

    val part1Lines = lines.subList(part1Start, part1End).toMutableList()
    // Drop the extra "both parts" data at the beginning of this line.
    part1Lines[0] = part1Lines[0].drop(part1Lines[0].indexOf(firstHundredToken))
    val totalLines = lines.subList(totalStart, part1Start + 1)

    val part1Times = loadTimes(part1Lines)
    val totalTimes = loadTimes(totalLines)

    // Part 2 Split times can only be calculated for people who placed in both Part 1 and Total sets.
    val part2Keys = part1Times.keys.intersect(totalTimes.keys)
    val part2Times = mutableMapOf<String, Long>()
    for (key in part2Keys) {
        part2Times[key] = totalTimes[key]!! - part1Times[key]!!
    }

    val divider = "+-------------------------+--------------------------------+"
    println("Year 20$year Day $day Global Leadboard")
    println(divider)
    println("|  Type          Players  |  Fastest   Average   Slowest   |")
    println(divider)
    printTimes("Part 1 Split  ", part1Times)
    printTimes("Part 2 Split  ", part2Times)
    printTimes("Part 1+2 Total", totalTimes)
    println(divider)

}

/**
 * Extracts the ID and time (as milliseconds) from the leaderboard lines.
 */
private fun loadTimes(lines: List<String>): Map<String, Long> {
    val map = mutableMapOf<String, Long>()
    for (line in lines) {
        val id = line.split("data-user-id=\"")[1].split("\"")[0]
        val fullTime = line.split("leaderboard-time\">")[1].split("</span>")[0]
        val stringTime = fullTime.split(" ").last().split(":")
        var seconds = stringTime[2].toLong()
        seconds += (stringTime[1].toLong() * 60)
        seconds += (stringTime[0].toLong() * 60 * 60)
        map[id] = seconds * 1000
    }
    return map
}

/**
 * Outputs the times in a map.
 */
private fun printTimes(title: String, times: Map<String, Long>) {
    print("|  $title    ${leftPad(times.size.toString(), 3)}  | ")
    print("${formatTime(times.values.min())} ")
    print("${formatTime(times.values.average().toLong())} ")
    println("${formatTime(times.values.max())}  |")
}

/**
 * Formats a millisecond time as a display string.
 */
private fun formatTime(time: Long) = SolveTime.Companion.formatTime(time, true, false)

/**
 * Left pads a value.
 */
private fun leftPad(value: String, width: Int): String {
    val padSize = width - value.length
    return (" ".repeat(padSize) + value)
}