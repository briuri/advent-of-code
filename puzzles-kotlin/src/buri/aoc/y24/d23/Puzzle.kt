package buri.aoc.y24.d23

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
        assertRun("7", 1)
        assertRun("1098", 0, true)
    }

    @Test
    fun runPart2() {
        assertRun("co,de,ka,ta", 1)
        assertRun("ar,ep,ih,ju,jx,le,ol,pk,pm,pp,xf,yu,zg", 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): String {
        val nodes = mutableMapOf<String, Node>()
        for (line in input) {
            val tokens = line.split("-")
            nodes.putIfAbsent(tokens[0], Node(tokens[0]))
            nodes.putIfAbsent(tokens[1], Node(tokens[1]))
            nodes[tokens[0]]!!.addNode(nodes[tokens[1]]!!)
            nodes[tokens[1]]!!.addNode(nodes[tokens[0]]!!)
        }

        if (part.isOne()) {
            val triples = mutableSetOf<List<String>>()
            for (first in nodes.values) {
                for (second in first.others) {
                    for (third in first.intersect(nodes[second]!!)) {
                        triples.add(listOf(first.key, second, third).sorted())
                    }
                }
            }
            return triples.count { it.any { it2 -> it2.startsWith("t") } }.toString()
        }

        // Part TWO

        // Base theoretical case is that biggest LAN party uses all connections (too easy).
        // More likely: Computers in the biggest LAN party be in the fewest other LAN parties.
        // They will have a really big shared list and then a smaller number of extra connections
        // (turns out to be 1 extra in my data).

        // Group the discovered LAN parties by how many other LAN parties that computer is in
        val totalPartiesToParties = mutableMapOf<Int, MutableSet<List<String>>>()
        for (first in nodes.values) {
            // Visit every connection and build unique lists of computers in common.
            val sharedConnections = mutableSetOf<List<String>>()
            for (second in first.others) {
                val intersection = first.intersect(nodes[second]!!).toMutableSet()
                intersection.add(first.key)
                intersection.add(second)
                sharedConnections.add(intersection.sorted())
            }
            totalPartiesToParties.putIfAbsent(sharedConnections.size, mutableSetOf())
            totalPartiesToParties[sharedConnections.size]!!.addAll(sharedConnections)
        }

        // The computers in the smallest number of total parties are also in the biggest LAN party.
        // (fewestParties was 2 for in my data)
        val fewestParties = totalPartiesToParties.keys.min()
        val possibleParties = totalPartiesToParties[fewestParties]!!

        // Get the biggest LAN party of all the LAN parties these computers are in.
        val biggestPartySize = possibleParties.maxOfOrNull { it.size }!!
        return possibleParties.first { it.size == biggestPartySize }.joinToString(",")
    }
}

data class Node(val key: String, val others: MutableList<String> = mutableListOf()) {
    /**
     * Adds a connected node during initialization.
     */
    fun addNode(node: Node) {
        others.add(node.key)
    }

    /**
     * Finds nodes in common.
     */
    fun intersect(node: Node): Set<String> {
        return others.intersect(node.others.toSet())
    }

    override fun toString(): String = "$key$others"
}