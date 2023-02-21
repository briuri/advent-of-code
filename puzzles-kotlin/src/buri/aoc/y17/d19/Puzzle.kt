package buri.aoc.y17.d19

import buri.aoc.common.BasePuzzle
import buri.aoc.common.position.Direction.*
import buri.aoc.common.position.MutablePosition
import buri.aoc.common.Part
import buri.aoc.common.Part.ONE
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun("ABCDEF", 1)
        assertRun("VTWBPYAQFU", 0, true)
    }

    @Test
    fun runPart2() {
        assertRun("38", 1)
        assertRun("17358", 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): String {
        val width = input.maxOf { it.length }
        val height = input.size
        val map = input.map { it.padEnd(width, ' ') }

        val path = StringBuilder()
        var steps = 0
        val point = MutablePosition(Pair(map[0].indexOf('|'), 0), SOUTH)
        while (true) {
            point.move()
            steps++
            val x = point.coords.first
            val y = point.coords.second
            if (x !in 0 until width || y !in 0 until height || map[y][x] == ' ') {
                break
            }
            val value = map[y][x]
            if (value in 'A'..'Z') {
                path.append(value)
            } else if (value == '+') {
                if (point.facing == NORTH || point.facing == SOUTH) {
                    point.facing = if (x == 0 || map[y][x - 1] == ' ') EAST else WEST
                } else {
                    point.facing = if (y == 0 || map[y - 1][x] == ' ') SOUTH else NORTH
                }
            }
        }
        return if (part == ONE) path.toString() else steps.toString()
    }
}