package buri.aoc.y17.d17

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(1306, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(20430489, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val steps = input[0].toInt()
        if (part.isOne()) {
            val spinlock = Spinlock(steps)
            repeat(2017) {
                spinlock.spin()
            }
            return spinlock.getValueAfter(2017)
        }

        // Instead of building a big spinlock, just keep track of when things are inserted after 0.
        var afterZero = Int.MIN_VALUE
        var current = 0
        for (i in 1..50_000_000) {
            current = (current + steps) % i
            if (current == 0) {
                afterZero = i
            }
            current++
        }
        return afterZero
    }
}

class Spinlock(private val steps: Int) {
    private val lock = ArrayDeque<Int>()

    init {
        lock.addFirst(0)
    }

    /**
     * Jumps ahead some amount and inserts the next value.
     */
    fun spin() {
        repeat(steps + 1) {
            lock.addLast(lock.removeFirst())
        }
        lock.addFirst(lock.size)
    }

    /**
     * Returns the value after some specified target.
     */
    fun getValueAfter(target: Int): Int {
        while (lock.last() != target) {
            lock.addLast(lock.removeFirst())
        }
        return lock.first()
    }
}