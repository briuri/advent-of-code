package buri.aoc.y18.d23

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractInts
import buri.aoc.common.position.Point3D
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(7, 1)
        assertRun(297, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(36, 2)
        assertRun(126233088, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val bots = mutableListOf<Bot>()
        for (line in input) {
            bots.add(Bot(line.extractInts().map { it.toLong() }))
        }
        val strongest = bots.maxByOrNull { it.range }!!
        if (part.isOne()) {
            return bots.count { strongest.hasInRange(it.position) }
        }

        var minX = bots.minOfOrNull { it.position.x }!!
        var maxX = bots.maxOfOrNull { it.position.x }!!
        var minY = bots.minOfOrNull { it.position.y }!!
        var maxY = bots.maxOfOrNull { it.position.y }!!
        var minZ = bots.minOfOrNull { it.position.z }!!
        var maxZ = bots.maxOfOrNull { it.position.z }!!

        // Controls the sampling chunks.
        val origin = Point3D(0L, 0L, 0L)
        val searchRatio = 3
        // Example is so small that it doesn't need different rules at full zoom.
        val fullZoomResizeRatio = if (bots.size < 20) 1 else 10000

        var bestOverallBotsInRange = 0
        var bestOverallMD = Int.MAX_VALUE
        var repeats = 0
        // Zoom in the sample area until the max converges.
        while (true) {
            // Set up the step size for sampling.
            val dx = ((maxX - minX) / searchRatio).coerceAtLeast(1)
            val dy = ((maxY - minY) / searchRatio).coerceAtLeast(1)
            val dz = ((maxZ - minZ) / searchRatio).coerceAtLeast(1)

            // Take samples.
            var bestPosition: Point3D<Long>? = null
            var bestBotsInRange = 0
            for (z in minZ..maxZ step dz) {
                for (y in minY..maxY step dy) {
                    for (x in minX..maxX step dx) {
                        val sample = Point3D(x, y, z)
                        val botsInRange = bots.count { it.hasInRange(sample) }
                        val isNewOrBetter = (bestPosition == null || botsInRange > bestBotsInRange)
                        val isEqualAndCloser = (bestPosition != null &&
                                botsInRange == bestBotsInRange &&
                                sample.getManhattanDistance(origin) < bestPosition.getManhattanDistance(origin))
                        // Save the best sample. If there is a tie, use the one with smaller MD.
                        if (isNewOrBetter || isEqualAndCloser) {
                            bestPosition = sample
                            bestBotsInRange = botsInRange
                        }
                    }
                }
            }

            // Keep track of repeating bests. Stop sampling when the best value is repeated twice.
            val currentMD = bestPosition!!.getManhattanDistance(origin)
            if (bestBotsInRange == bestOverallBotsInRange && currentMD == bestOverallMD) {
                repeats++
                if (repeats == 2) {
                    break
                }
            }
            // If the best position has >= bots in range as the overall best, overwrite the overall best.
            else if (bestBotsInRange >= bestOverallBotsInRange) {
                bestOverallBotsInRange = bestBotsInRange
                bestOverallMD = bestPosition.getManhattanDistance(origin)
                repeats = 0
            }

            // Adjust min / max to zoom into the best volume. Broaden a bit at end so we don't fixate on local maxes.
            val resizeRatio = if (dx == 1L && dy == 1L && dz == 1L) fullZoomResizeRatio else 1
            minX = bestPosition.x - (resizeRatio * dx)
            maxX = bestPosition.x + (resizeRatio * dx)
            minY = bestPosition.y - (resizeRatio * dy)
            maxY = bestPosition.y + (resizeRatio * dy)
            minZ = bestPosition.z - (resizeRatio * dz)
            maxZ = bestPosition.z + (resizeRatio * dz)
        }
        return bestOverallMD
    }
}

class Bot(numbers: List<Long>) {
    val position = Point3D(numbers[0], numbers[1], numbers[2])
    val range = numbers[3]

    /**
     * Returns true if some point is in range of this bot.
     */
    fun hasInRange(testPosition: Point3D<Long>): Boolean {
        return (position.getManhattanDistance(testPosition) <= range)
    }

    override fun toString(): String {
        return ("p=$position r=$range")
    }
}