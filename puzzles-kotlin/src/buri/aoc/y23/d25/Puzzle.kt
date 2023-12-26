package buri.aoc.y23.d25

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import org.junit.Test
import java.lang.Exception

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(54, 1)
        assertRun(596376, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val diagram = mutableMapOf<String, MutableSet<String>>()
        for (line in input) {
            val tokens = line.split(": ")
            val name = tokens[0]
            val to = tokens[1].split(" ")
            diagram.putIfAbsent(name, mutableSetOf())
            for (component in to) {
                diagram[name]!!.add(component)
                diagram.putIfAbsent(component, mutableSetOf())
                diagram[component]!!.add(name)
            }
        }
        val edges = mutableSetOf<Set<String>>()
        for (a in diagram.keys) {
            for (b in diagram[a]!!) {
                edges.add(setOf(a, b))
            }
        }

        // Karger's algorithm: Pick random edge, merge two nodes, and continue until only 2 supernodes remain.
        // http://web.stanford.edu/class/archive/cs/cs161/cs161.1176/Lectures/CS161Lecture16.pdf

        // Some attempts may cut wrong. Keep trying.
        while (true) {
            // Initially, every node is a supernode.
            val supernodes = mutableSetOf<MutableSet<String>>()
            for (a in diagram.keys) {
                val set = mutableSetOf<String>()
                set.add(a)
                supernodes.add(set)
            }

            val shuffledEdges = edges.shuffled().toMutableList()
            // When 2 supernodes remain, any edges that span both supernodes are the ones that need to be cut.
            while (supernodes.size > 2) {
                val edge = shuffledEdges.removeFirst()
                val supernode1 = supernodes.first { edge.first() in it }
                val supernode2 = supernodes.first { edge.last() in it }
                // Already merged.
                if (supernode1 == supernode2) {
                    continue
                }
                supernode1.addAll(supernode2)
                supernodes.removeIf { it == supernode2 }
            }

            val cuts = mutableSetOf<Set<String>>()
            for (edge in edges) {
                val supernode1 = supernodes.first { edge.first() in it }
                val supernode2 = supernodes.first { edge.last() in it }
                if (supernode1 == supernode2) {
                    continue
                }
                cuts.add(edge)
            }
            if (cuts.size == 3) {
                val start = cuts.first().first()
                val size = diagram.exploreFrom(start, cuts)
                return size * (diagram.keys.size - size)
            }
        }
    }

    /**
     * Explores the edges from a starting point and returns all components visited.
     */
    private fun MutableMap<String, MutableSet<String>>.exploreFrom(start: String, cuts: Set<Set<String>>): Int {
        val visited = mutableSetOf<String>()
        val frontier = mutableListOf<String>()
        frontier.add(start)
        while (frontier.isNotEmpty()) {
            val current = frontier.removeFirst()
            if (current in visited) {
                continue
            }
            visited.add(current)
            for (next in this[current]!!) {
                val edge = setOf(current, next)
                if (edge !in cuts) {
                    frontier.add(next)
                }
            }
        }
        return visited.size
    }
}