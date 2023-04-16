package buri.aoc.y22.d20

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.position.Point2D
import org.junit.Test
import kotlin.math.absoluteValue

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(3, 1)
        assertRun(1087, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(1623178306, 1)
        assertRun(13084440324666, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val key = if (part.isOne()) 1 else 811589153
        val mixes = if (part.isOne()) 1 else 10

        val originalList = input.map { it.toLong() * key }
        val list = ArrayDeque<Point2D<Long>>()
        var zero: Point2D<Long>? = null
        for ((index, value) in originalList.withIndex()) {
            val pair = Point2D(index.toLong(), value)
            list.addLast(pair)
            if (pair.y == 0L) {
                zero = pair
            }
        }

        for (mix in 0 until mixes) {
            for ((index, value) in originalList.withIndex()) {
                mix(list, Point2D(index.toLong(), value))
            }
        }

        locate(list, zero!!)
        var sum = 0L
        repeat(3) {
            rotate(list, 1000)
            sum += list.first().y
        }
        return sum
    }

    /**
     * Moves a value in the list to that many spaces away.
     */
    private fun mix(list: ArrayDeque<Point2D<Long>>, pair: Point2D<Long>) {
        locate(list, pair)
        list.removeFirst()
        rotate(list, pair.y)
        list.addFirst(pair)
    }

    /**
     * Rotates the list by some amount.
     */
    private fun rotate(list: ArrayDeque<Point2D<Long>>, rawTimes: Long) {
        val times = rawTimes % list.size
        if (times >= 0) {
            for (time in 0 until times) {
                list.addLast(list.removeFirst())
            }
        } else {
            for (time in 0 until times.absoluteValue) {
                list.addFirst(list.removeLast())
            }
        }
    }

    /**
     * Rotates the list until some pair is at the front.
     */
    private fun locate(list: ArrayDeque<Point2D<Long>>, pair: Point2D<Long>) {
        while (list.first() != pair) {
            list.addLast(list.removeFirst())
        }
    }
}