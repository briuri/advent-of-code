package buri.aoc.y24.d02

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractInts
import org.junit.Test
import kotlin.math.abs

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(2, 1)
        assertRun(680, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(4, 1)
        assertRun(710, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        var safe = 0
        for (line in input) {
            val report = line.extractInts()
            if (part.isOne()) {
                if (report.isSafe()) {
                    safe++
                }
            } else {
                for (i in report.indices) {
                    val levelOmitted = report.toMutableList()
                    levelOmitted.removeAt(i)
                    if (levelOmitted.isSafe()) {
                        safe++
                        break
                    }
                }
            }
        }
        return safe
    }

    /**
     * Checks if a report meets the criteria.
     */
    private fun List<Int>.isSafe(): Boolean {
        val allIncreasing = this.sorted() in listOf(this, this.reversed())
        var levelsClose = true
        for (i in 0..<this.lastIndex) {
            val distance = abs(this[i] - this[i + 1])
            levelsClose = levelsClose && (distance in 1..3)
        }
        return (allIncreasing && levelsClose)
    }
}