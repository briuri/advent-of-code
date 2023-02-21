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
        // Part ONE is a special case of Part TWO, with 1 worker and no job durations.
        val workers = if (part == ONE) 1 else 5
        val jobs = mutableSetOf<Job>()

        val graph = Graph(input)
        val visited = mutableListOf<Char>()
        var time = 0
        while (!graph.isComplete(visited)) {
            // Mark all nodes in jobs that finish on this tick as visited.
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
    private val vertices = mutableSetOf<Pair<Char, Char>>()
    private val nodes = mutableSetOf<Char>()
    private val starts = mutableListOf<Char>()

    init {
        val parents = mutableSetOf<Char>()
        val children = mutableSetOf<Char>()
        for (line in input) {
            val tokens = line.split(" ").map { it[0] }
            vertices.add(Pair(tokens[1], tokens[7]))
            parents.add(tokens[1])
            children.add(tokens[7])
        }
        // Store all unique node names.
        nodes.addAll(vertices.map { it.toList() }.reduce { acc, list -> acc + list })
        // Store all nodes that have no prerequisites.
        starts.addAll(parents.filter { it !in children }.sorted())
    }

    /**
     * Returns true if the graph is totally visited.
     */
    fun isComplete(visited: List<Char>): Boolean {
        return nodes.all { it in visited }
    }

    /**
     * Determines which nodes can be visited next, based on what has already been visited.
     */
    fun getNext(visited: List<Char>, inProgress: List<Char>): List<Char> {
        val unavailable = visited + inProgress
        val next = mutableSetOf<Char>()
        // Start with unvisited nodes that have no prequisites.
        next.addAll(starts.filter { it !in unavailable })
        for (node in nodes.filter { it !in unavailable }) {
            // Add nodes whose prerequisites have all been met.
            if (vertices.filter { it.second == node }.all { it.first in visited }) {
                next.add(node)
            }
        }
        return next.toList().sorted()
    }
}

data class Job(val node: Char, val end: Int)