package buri.aoc.y18.d08

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.Part.ONE
import buri.aoc.common.extractInts
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(138, 1)
        assertRun(40036, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(66, 1)
        assertRun(21677, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val numbers = input[0].extractInts()
        val root = getNode(numbers)
        return if (part == ONE) root.getMetadataSum() else root.getValue()
    }

    /**
     * Recursively builds the tree of nodes.
     */
    private fun getNode(numbers: List<Int>): Node {
        val node = Node(numbers[0], numbers[1])
        var nodeBody = numbers.subList(2, numbers.size)
        for (i in 0 until node.numChildren) {
            val child = getNode(nodeBody)
            node.addChild(child)
            nodeBody = nodeBody.subList(child.getLength(), nodeBody.size)
        }
        node.addMetadata(nodeBody.subList(0, node.numMetadata))
        return node
    }
}

data class Node(val numChildren: Int, val numMetadata: Int) {
    private val children = mutableListOf<Node>()
    private val metadata = mutableListOf<Int>()

    /**
     * Returns the length of this node
     */
    fun getLength(): Int {
        return 2 + children.sumOf { it.getLength() } + metadata.size
    }

    /**
     * Returns the sum of all metadata entries.
     */
    fun getMetadataSum(): Int {
        return metadata.sum() + children.sumOf { it.getMetadataSum() }
    }

    /**
     * Returns the value of a node.
     */
    fun getValue(): Int {
        return if (numChildren == 0) {
            getMetadataSum()
        } else {
            // Convert metadata entries to 0-based then pick the ones that are valid children.
            val indices = metadata.map { it - 1 }.filter { children.size > it }
            indices.map { children[it] }.sumOf { it.getValue() }
        }
    }

    /**
     * Adds a child to this node.
     */
    fun addChild(child: Node) {
        children.add(child)
    }

    /**
     * Adds metadata entries to this node.
     */
    fun addMetadata(entries: List<Int>) {
        metadata.addAll(entries)
    }
}