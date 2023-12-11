package buri.aoc.y23.d08

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
        assertRun(2, 1)
        assertRun(20221, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(6, 2)
        assertRun(14616363770447, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val turns = input[0].toList()
        val nodes = mutableMapOf<String, Node>()
        for (line in input.drop(2)) {
            val node = Node(line)
            nodes[node.name] = node
        }
        for (node in nodes.values) {
            node.loadChildren(nodes)
        }

        if (part.isOne()) {
            return countSteps(nodes, turns, "AAA", "ZZZ")
        }
        // Use least common multiple to sync up each traversal.
        var lcm = 1L
        for (startName in nodes.keys.filter { it.endsWith("A") }) {
            lcm = getLCM(lcm, countSteps(nodes, turns, startName, "Z"))
        }
        return lcm
    }

    /**
     * Counts the steps needed to reach a final state from a node.
     */
    private fun countSteps(nodes: Map<String, Node>, turns: List<Char>, startName: String, endPattern: String): Long {
        var currentTurn = 0
        var totalSteps = 0L
        var currentNode = nodes[startName]!!
        while (!currentNode.name.endsWith(endPattern)) {
            val nextTurn = turns[currentTurn]
            currentNode = if (nextTurn == 'L') currentNode.left else currentNode.right
            totalSteps++
            currentTurn = (currentTurn + 1) % turns.size
        }
        return totalSteps
    }
}

/**
 * Data class for the traversable node.
 */
data class Node(val line: String) {
    val name = line.split(" = ")[0]
    private val leftName = line.split("(")[1].split(",")[0]
    private val rightName = line.split(", ")[1].dropLast(1)
    lateinit var left: Node
    lateinit var right: Node

    /**
     * Adds nodes objects as children based on their names, to simplify lookup later.
     */
    fun loadChildren(nodes: Map<String, Node>) {
        left = nodes[leftName]!!
        right = nodes[rightName]!!
    }
}