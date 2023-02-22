package buri.aoc.y17.d07

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
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
        assertRun("tknk", 1)
        assertRun("uownj", 0, true)
    }

    @Test
    fun runPart2() {
        assertRun("60", 1)
        assertRun("596", 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): String {
        val discs = mutableMapOf<String, Disc>()
        for (line in input) {
            val name = line.split(" ")[0]
            discs[name] = Disc(name, line.extractInts(false)[0])
        }
        for (line in input.filter { it.contains(" -> ") }) {
            val parent = line.split(" ")[0]
            val children = line.split(" -> ")[1].split(", ")
            for (child in children) {
                discs[parent]!!.add(discs[child]!!)
            }
        }
        val root = discs.values.first { it.parent == null }
        if (part.isOne()) {
            return root.name
        }

        // Find the disc that contains all balanced children.
        var current = root
        while (current.imbalancedChild != null) {
            current = current.imbalancedChild!!
        }

        // Check any sibling of this disc to see what the weight should be.
        val targetWeight = current.parent!!.children.filter { it != current }[0].totalWeight
        val delta = targetWeight - current.totalWeight
        return ((current.weight + delta).toString())
    }
}

data class Disc(val name: String, val weight: Int) {
    val children = mutableListOf<Disc>()
    var parent: Disc? = null
    val totalWeight: Int
        get() = weight + children.sumOf { it.totalWeight }
    val imbalancedChild: Disc?
        get() {
            val weightToDisc = mutableMapOf<Int, MutableList<Disc>>()
            for (child in children) {
                val childTotalWeight = child.totalWeight
                weightToDisc.putIfAbsent(childTotalWeight, mutableListOf())
                weightToDisc[childTotalWeight]!!.add(child)
            }
            if (weightToDisc.size > 1) {
                for ((_, value) in weightToDisc) {
                    if (value.size == 1) {
                        return value[0]
                    }
                }
            }
            return null
        }

    /**
     * Adds a child disc
     */
    fun add(disc: Disc) {
        disc.parent = this
        children.add(disc)
    }
}