package buri.aoc.y22.d07

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
        assertRun(95437, 1)
        assertRun(1581595, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(24933642, 1)
        assertRun(1544176, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val nodes = mutableMapOf<String, Node>()
        val path = ArrayDeque<String>()

        var i = 0
        while (i in input.indices) {
            if (input[i].startsWith("$")) {
                val command = input[i].split(" ").drop(1)
                if (command[0] == "cd" && command[1] == "/") {
                    nodes.putIfAbsent(command[1], Node(command[1], true))
                    path.clear()
                    path.addFirst(command[1])
                } else if (command[0] == "cd" && command[1] == "..") {
                    path.removeFirst()
                } else if (command[0] == "cd") {
                    val parent = nodes[path.joinToString()]!!
                    path.addFirst(command[1])
                    val child = Node(command[1], true)
                    nodes.putIfAbsent(path.joinToString(), child)
                    parent.addChild(child)
                } else if (command[0] == "ls") {
                    val parent = nodes[path.joinToString()]!!
                    while (true) {
                        i++
                        if (i > input.lastIndex) {
                            break
                        }
                        if (input[i].startsWith("$")) {
                            i--
                            break
                        }
                        if (!input[i].startsWith("dir")) {
                            val tokens = input[i].split(" ")
                            val child = Node(path.joinToString() + tokens[1], false, tokens[0].toLong())
                            parent.addChild(child)
                        }
                    }
                }
                i++
            }
        }
        val directories = nodes.values.filter { it.isDirectory }
        if (part.isOne()) {
            return directories.filter { it.getSize() <= 100_000 }.sumOf { it.getSize() }
        }

        val spaceAvailable = 70_000_000L - nodes["/"]!!.getSize()
        val spaceNeeded = 30_000_000L - spaceAvailable
        return directories.filter { it.getSize() >= spaceNeeded }.minByOrNull { it.getSize() }!!.getSize()
    }
}

class Node(val name: String, val isDirectory: Boolean, private val fileSize: Long = 0) {
    private val children = mutableListOf<Node>()

    /**
     * Adds a child.
     */
    fun addChild(child: Node) {
        children.add(child)
    }

    /**
     * Recursively calculates the size of this directory or file.
     */
    fun getSize(): Long {
        return if (!isDirectory) {
            fileSize
        } else {
            children.sumOf { it.getSize() }
        }
    }
}