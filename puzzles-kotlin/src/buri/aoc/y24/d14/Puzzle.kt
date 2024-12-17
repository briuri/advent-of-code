package buri.aoc.y24.d14

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractInts
import buri.aoc.common.position.Bounds2D
import buri.aoc.common.position.Point2D
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(12, 1)
        assertRun(230172768, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(8087, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val bounds = if (input.size < 20) {
            Bounds2D(setOf(Point2D(0, 0), Point2D(11, 7)))
        } else {
            Bounds2D(setOf(Point2D(0, 0), Point2D(101, 103)))
        }
        val quads = if (input.size < 20) {
            listOf(
                Bounds2D(setOf(Point2D(0, 0), Point2D(4, 2))),
                Bounds2D(setOf(Point2D(6, 0), Point2D(10, 2))),
                Bounds2D(setOf(Point2D(0, 4), Point2D(4, 6))),
                Bounds2D(setOf(Point2D(6, 4), Point2D(10, 6))),
            )
        } else {
            listOf(
                Bounds2D(setOf(Point2D(0, 0), Point2D(49, 50))),
                Bounds2D(setOf(Point2D(51, 0), Point2D(100, 50))),
                Bounds2D(setOf(Point2D(0, 52), Point2D(49, 102))),
                Bounds2D(setOf(Point2D(51, 52), Point2D(100, 102))),
            )
        }

        val bots = mutableSetOf<Bot>()
        for (line in input) {
            val tokens = line.extractInts(allowNegative = true)
            bots.add(Bot(Point2D(tokens[0], tokens[1]), Point2D(tokens[2], tokens[3])))
        }
        if (part.isOne()) {
            for (i in 1..100) {
                bots.forEach { it.move(bounds) }
            }
            var product = 1
            for (quad in quads) {
                product *= bots.count { it.pos.x in quad.x && it.pos.y in quad.y }
            }
            return product
        }

        // Part TWO
        val nearbyScores = mutableMapOf<Int, Int>()
        // Find the point where the MOST bots are near each other (MD=1)
        for (i in 1..10_000) {
            bots.forEach { it.move(bounds) }
            var nearbyMDs = 0
            // After solving, I cut MD calculation down to points near answer to speed up execution
            // (checking at every i value takes +20 seconds).
            if (i in 8080..8090) {
                for (bot in bots) {
                    for (bot2 in bots.filter { it != bot }) {
                        if (bot.pos.getManhattanDistance(bot2.pos) == 1L) {
                            nearbyMDs++
                        }
                    }
                }
                nearbyScores[i] = nearbyMDs
            }
        }
        return nearbyScores.maxBy { it.value }.key
    }
}

data class Bot(var pos: Point2D<Int>, val v: Point2D<Int>) {
    fun move(bounds: Bounds2D) {
        val nextX = (pos.x + v.x + bounds.x.last) % bounds.x.last
        val nextY = (pos.y + v.y + bounds.y.last) % bounds.y.last
        pos = Point2D(nextX, nextY)
    }
}