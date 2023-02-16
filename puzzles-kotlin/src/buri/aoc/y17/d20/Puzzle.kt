package buri.aoc.y17.d20

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.Part.ONE
import buri.aoc.common.Part.TWO
import buri.aoc.common.getManhattanDistance
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(170, 0, true)
    }
    @Test
    fun runPart2() {
        assertRun(571, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val particles = mutableListOf<Particle>()
        for ((index, line) in input.withIndex()) {
            particles.add(Particle(index, line))
        }
        repeat(1000) {
            particles.forEach { it.tick() }
            val particlesAt = mutableMapOf<Triple<Long, Long, Long>, MutableList<Particle>>()
            for (particle in particles) {
                particlesAt.putIfAbsent(particle.position, mutableListOf())
                particlesAt[particle.position]!!.add(particle)
            }
            if (part == TWO) {
                for (collision in particlesAt.values. filter { it.size > 1 }) {
                    particles.removeAll(collision)
                }
            }
        }
        if (part == ONE) {
            return particles.sortedBy { it.position.getManhattanDistance() }[0].num
        }
        return particles.size
    }
}
data class Particle(val num: Int, val input: String) {
    var position: Triple<Long, Long, Long>
    private var velocity: Triple<Long, Long, Long>
    private var acceleration: Triple<Long, Long, Long>
    init {
        val pStart = input.split("p=<")[1].split(">")[0].split(",").map { it.toLong() }
        position = Triple(pStart[0], pStart[1], pStart[2])

        val vStart = input.split("v=<")[1].split(">")[0].split(",").map { it.toLong() }
        velocity = Triple(vStart[0], vStart[1], vStart[2])

        val aStart = input.split("a=<")[1].split(">")[0].split(",").map { it.toLong() }
        acceleration = Triple(aStart[0], aStart[1], aStart[2])
    }

    /**
     * Updates particle position and velocity.
     */
    fun tick() {
        velocity = velocity.copy(first = velocity.first + acceleration.first,
            second = velocity.second + acceleration.second,
            third = velocity.third + acceleration.third)
        position = position.copy(first = position.first + velocity.first,
            second = position.second + velocity.second,
            third = position.third + velocity.third)
    }
}