package buri.aoc.y18.d07

import buri.aoc.common.BasePuzzle
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
        assertRun("CABDFE", 1)
        assertRun("GRTAHKLQVYWXMUBCZPIJFEDNSO", 0, true)
    }

    @Test
    fun runPart2() {
        assertRun("1115", 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): String {
        val workers = if (part == ONE) 1 else 5
        val jobs = mutableSetOf<Job>()

        val graph = Graph(input)
        val visited = mutableListOf<Char>()
        var time = 0
        while (!graph.isComplete(visited)) {
            visited.addAll(jobs.filter { it.end <= time }.map { it.node })
            jobs.removeIf { it.end <= time }

            val next = graph.getNext(visited, jobs.map { it.node }).toMutableList()
            while (jobs.size < workers && next.isNotEmpty()) {
                val node = next.removeAt(0)
                val end = if (part == ONE) 0 else (time + node.code - 4) // 60 + alphabet position = ASCII - 4
                jobs.add(Job(node, end))
            }
            time++
        }
        return if (part == ONE) visited.joinToString("") else (time - 1).toString()
    }
}

data class Graph(val input: List<String>) {
    private val vertices = mutableListOf<Pair<Char, Char>>()
    private val nodes = mutableSetOf<Char>()
    private val starts = mutableListOf<Char>()

    init {
        val parents = mutableSetOf<Char>()
        val children = mutableSetOf<Char>()
        for (line in input) {
            val tokens = line.split(" ")
            vertices.add(Pair(tokens[1][0], tokens[7][0]))
            parents.add(tokens[1][0])
            children.add(tokens[7][0])
        }
        nodes.addAll(vertices.map { it.toList() }.reduce { acc, list -> acc + list })
        starts.addAll(parents.filter { it !in children }.sorted())
    }

    /**
     * Returns true if the graph is totally visited.
     */
    fun isComplete(visited: List<Char>): Boolean {
        return nodes.all { visited.contains(it) }
    }

    /**
     * Determines which nodes can be visited next, based on what has already been visited.
     */
    fun getNext(visited: List<Char>, inProgress: List<Char>): List<Char> {
        val next = mutableSetOf<Char>()
        next.addAll(starts.filter { it !in visited && it !in inProgress })
        for (node in nodes.filter { it !in visited && it !in inProgress  }) {
            if (vertices.filter { it.second == node }.all { it.first in visited }) {
                next.add(node)
            }
        }
        return next.toList().sorted()
    }
}

data class Job(val node: Char, val end: Int)