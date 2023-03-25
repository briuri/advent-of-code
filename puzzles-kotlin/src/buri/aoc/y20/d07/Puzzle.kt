package buri.aoc.y20.d07

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
        assertRun(4, 1)
        assertRun(233, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(126, 2)
        assertRun(421550, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val bags = mutableMapOf<String, Bag>()
        for (line in input) {
            val parentName = line.split(" bags contain")[0]
            if (parentName !in bags.keys) {
                bags[parentName] = Bag(parentName)
            }

            for (child in line.split("contain ")[1].dropLast(1).split(", ")) {
                if (!child.startsWith("no")) {
                    val childName = child.split(" bag")[0].drop(2)
                    val childAmount = child.take(1).toInt()
                    if (childName !in bags.keys) {
                        bags[childName] = Bag(childName)
                    }
                    bags[parentName]!!.addBag(bags[childName]!!, childAmount)
                }
            }
        }

        return if (part.isOne()) {
            bags.values.count { it.canCarry("shiny gold") }
        } else {
            bags["shiny gold"]!!.countChildren()
        }
    }
}

class Bag(val name: String) {
    private val parents = mutableSetOf<Bag>()
    private val children = mutableMapOf<Bag, Int>()

    /**
     * Returns true of this bag can contain some other bag at any depth.
     */
    fun canCarry(name: String): Boolean {
        return (name in children.map { it.key.name }) || children.keys.any { it.canCarry(name) }
    }

    /**
     * Counts the number of children in this bag.
     */
    fun countChildren(): Int {
        var count = 0
        for ((child, amount) in children) {
            count += amount + (amount * child.countChildren())
        }
        return count
    }

    /**
     * Adds a bag to this bag.
     */
    fun addBag(bag: Bag, amount: Int) {
        children[bag] = amount
        bag.addParent(this)
    }

    /**
     * Assigns a parent to this bag.
     */
    private fun addParent(parent: Bag) {
        parents.add(parent)
    }
}