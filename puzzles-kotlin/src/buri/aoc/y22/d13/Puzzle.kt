package buri.aoc.y22.d13

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.y18.d24.Group
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(13, 1)
        assertRun(6395, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(140, 1)
        assertRun(24921, 0, true)
    }

    private val mapper = ObjectMapper()

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        if (part.isOne()) {
            var sum = 0
            for ((index, chunk) in input.chunked(3).withIndex()) {
                if (compare(mapper.readTree(chunk[0]), mapper.readTree(chunk[1])) == IN_ORDER) {
                    sum += (index + 1)
                }
            }
            return sum
        }

        val nodes = input.filter { it.isNotEmpty() }.map { mapper.readTree(it) }.toMutableList()
        val twoNode = mapper.readTree("[[2]]")
        val sixNode = mapper.readTree("[[6]]")
        nodes.add(twoNode)
        nodes.add(sixNode)
        nodes.sortWith { p1: JsonNode, p2: JsonNode -> compare(p1, p2) }

        var product = 1
        for ((index, node) in nodes.withIndex()) {
            if (node == twoNode || node == sixNode) {
                product *= (index + 1)
            }
        }
        return product
    }

    /**
     * Does a comparison on the two packets.
     */
    private fun compare(packet1: JsonNode, packet2: JsonNode): Int {
        var left = packet1
        var right = packet2
        // If one value is an integer, convert to a list containing that integer and retry.
        if (left.isArray && right.isNumber) {
            right = mapper.readTree("[${right.asInt()}]")
        }
        if (right.isArray && left.isNumber) {
            left = mapper.readTree("[${left.asInt()}]")
        }

        // If both values are integers, sort normally. No decision if both are equal.
        if (left.isNumber && right.isNumber) {
            return left.asInt().compareTo(right.asInt())
        }

        // If both values are lists, compare item by item.
        // If left list runs out first, inputs are in right order.
        // If right runs out first, inputs are not in right order.
        if (left.isArray && right.isArray) {
            for (i in 0 until left.size()) {
                if (i >= right.size()) {
                    return NOT_IN_ORDER
                }
                val compare = compare(left.get(i), right.get(i))
                if (compare != NO_IDEA) {
                    return compare
                }
            }
            if (left.size() < right.size()) {
                return IN_ORDER
            }
        }
        return NO_IDEA
    }
}

const val IN_ORDER = -1
const val NO_IDEA = 0
const val NOT_IN_ORDER = 1