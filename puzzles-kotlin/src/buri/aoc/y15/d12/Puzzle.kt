package buri.aoc.y15.d12

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.Part.ONE
import buri.aoc.common.Part.TWO
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(3, 1)
        assertRun(156366, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(4, 2)
        assertRun(96852, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val node = ObjectMapper().readTree(input[0])
        return getSum(part, node)
    }

    /**
     * Recursively gets the sum of all numbers in a JsonNode
     */
    private fun getSum(part: Part, node: JsonNode): Int {
        var sum = 0
        if (node.isArray) {
            for (item in node as ArrayNode) {
                sum += getSum(part, item)
            }
        } else if (node.isObject) {
            val objectNode = node as ObjectNode
            var hasRed = false
            for (element in objectNode.elements()) {
                if (element.asText() == "red") {
                    hasRed = true
                    break
                }
            }
            for (item in objectNode) {
                if (part == ONE || (part == TWO && !hasRed)) {
                    sum += getSum(part, item)
                }
            }
        } else if (node.isNumber) {
            sum += node.asInt()
        }
        return sum
    }
}