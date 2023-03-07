package buri.aoc.y18.d24

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
        assertRun(5216, 1)
        assertRun(16678, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(51, 1)
        assertRun(3758, 0, true)
    }

    private val targetSelectOrder = Comparator { g1: Group, g2: Group ->
        var compare = g2.effectivePower.compareTo(g1.effectivePower)
        if (compare == 0) {
            compare = g2.initiative.compareTo(g1.initiative)
        }
        compare
    }

    private val attackOrder = Comparator { g1: Group, g2: Group ->
        -1 * g1.initiative.compareTo(g2.initiative)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val mobs = mutableListOf<Group>()
        var isInfection = false
        for (line in input.filter { "Immune" !in it && it.isNotEmpty() }) {
            if (line.contains("Infection")) {
                isInfection = true
            } else {
                mobs.add(Group(isInfection, line))
            }
        }

        if (part.isOne()) {
            return battle(mobs, 0)!!.second
        }

        // Raised starting value after finding the correct answer.
        var boost = 40
        while (true) {
            val outcome = battle(mobs, boost)
            // Stalemate or infection won
            if (outcome == null || outcome.first) {
                boost++
            } else {
                return outcome.second
            }
        }
    }

    /**
     * Executes a battle simulation and returns a pair: whether infection won and the remaining units.
     */
    private fun battle(referenceMobs: List<Group>, boost: Int): Pair<Boolean, Int>? {
        val mobs = mutableListOf<Group>()
        mobs.addAll(referenceMobs.map { it.copy() })
        for (mob in mobs.filter { !it.isInfection }) {
            mob.attack += boost
        }

        // End when either side has no groups left.
        while (mobs.count { it.isInfection } != 0 && mobs.count { !it.isInfection } != 0) {
            // Target Selection
            val selections = mutableMapOf<Group, Group>()
            for (mob in mobs.sortedWith(targetSelectOrder)) {
                // Possible targets are any enemy not already targeted
                val targets = mobs.filter { it.isEnemy(mob) && it !in selections.values }
                // Best target is one taking most damage, with ties by effectivePower then initiative.
                val bestTarget = targets.sortedWith(targetSelectOrder).maxByOrNull { it.damageFrom(mob) }
                // Only attack if damage can be done.
                if (bestTarget != null && bestTarget.damageFrom(mob) > 0) {
                    selections[mob] = bestTarget
                }
            }

            // Attack
            var attackOccurred = false
            for (mob in selections.keys.sortedWith(attackOrder)) {
                if (!mob.isDead) {
                    val target = selections[mob]!!
                    target.units -= target.damageFrom(mob) / target.hp
                    attackOccurred = true
                    if (target.isDead) {
                        mobs.remove(target)
                    }
                }
            }

            // Stalemate
            if (!attackOccurred) {
                return null
            }
        }
        return Pair(mobs.any { it.isInfection }, mobs.sumOf { it.units })
    }
}

data class Group(val isInfection: Boolean, val data: String) {
    var units: Int
    val hp: Int
    private val immunities = mutableListOf<String>()
    private val weaknesses = mutableListOf<String>()
    var attack: Int
    private val attackType: String
    val initiative: Int
    val effectivePower: Int
        get() = attack * units
    val isDead: Boolean
        get() = units <= 0

    init {
        val numbers = data.extractInts()
        units = numbers[0]
        hp = numbers[1]

        if (data.contains("(")) {
            val caveats = data.substring(data.indexOf("(") + 1, data.indexOf(")"))
            if (caveats.indexOf("; ") != -1) {
                val left = caveats.split("; ")[0]
                val right = caveats.split("; ")[1]
                if (caveats.startsWith("immune")) {
                    immunities.addAll(toList(left, "immune to "))
                    weaknesses.addAll(toList(right, "weak to "))
                } else {
                    weaknesses.addAll(toList(left, "weak to "))
                    immunities.addAll(toList(right, "immune to "))
                }
            } else {
                if (caveats.contains("weak to ")) {
                    weaknesses.addAll(toList(caveats, "weak to "))
                }
                if (caveats.contains("immune to ")) {
                    immunities.addAll(toList(caveats, "immune to "))
                }
            }
        }
        attack = numbers[2]
        val tokens = data.split(" damage at")[0].split(" ")
        attackType = tokens[tokens.lastIndex]
        initiative = numbers[3]
    }

    /**
     * Helper function to get string data into a list format.
     */
    private fun toList(data: String, prefix: String): List<String> {
        return data.split(prefix)[1].split(", ")
    }

    /**
     * Returns true if the specified group is an enemy of this one.
     */
    fun isEnemy(group: Group): Boolean = group.isInfection != isInfection

    /**
     * Returns the amount of damage that would be received from the attacker (does not actually attack).
     */
    fun damageFrom(attacker: Group): Int {
        val multiplier = when (attacker.attackType) {
            in immunities -> 0
            in weaknesses -> 2
            else -> 1
        }
        return attacker.effectivePower * multiplier
    }

    override fun toString(): String {
        val type = if (isInfection) "Inf" else "Imm"
        return "$type[units=$units ep=${effectivePower} init=$initiative]"
    }
}