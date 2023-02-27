package buri.aoc.y18.d15

import buri.aoc.common.*
import buri.aoc.common.position.Grid
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(27730, 1)
        assertRun(36334, 2)
        assertRun(39514, 3)
        assertRun(27755, 4)
        assertRun(28944, 5)
        assertRun(18740, 6)
        assertRun(191216, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(4988, 1)
        assertRun(31284, 3)
        assertRun(3478, 4)
        assertRun(6474, 5)
        assertRun(1140, 6)
        assertRun(48050, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val mobs = mutableListOf<Mob>()
        val map = Grid(input[0].length, input.size, '#')
        for ((y, line) in input.withIndex()) {
            for ((x, value) in line.withIndex()) {
                if (value in "EG") {
                    mobs.add(Mob(Pair(x, y), value == 'E'))
                }
                map[x, y] = value
            }
        }

        if (part.isOne()) {
            val battle = Simulation(3)
            return battle.run(part, map, mobs)
        }

        var attack = 4
        var outcome: Int
        do {
            val battle = Simulation(attack)
            outcome = battle.run(part, map, mobs)
            attack++
        } while (battle.elvesDied)
        return outcome
    }

}

class Simulation(private val elfAttack: Int) {
    var elvesDied = false

    /**
     * Runs a simulation of a goblin/elf battle.
     */
    fun run(part: Part, rawMap: Grid<Char>, rawMobs: List<Mob>): Int {
        val map = rawMap.copy()
        val mobs = mutableListOf<Mob>()
        for (mob in rawMobs) {
            mobs.add(mob.copy())
        }

        // Use a pathfinder that avoids walls and other mobs.
        val pathfinder = Pathfinder { current ->
            current.getNeighbors(false).filter { map[it] !in "#EG" }
        }

        var round = 0
        while (true) {
            // Take turns in reading order.
            for (mob in mobs.sortedWith(compareBy({ it.position.second }, { it.position.first }))) {
                // Skip mobs that died this round (stale reference).
                if (mob.isDead()) {
                    continue
                }
                // Identify targets. Combat ends when no target remains.
                val allTargetMobs = mobs.filter { it.isElf != mob.isElf }
                if (allTargetMobs.isEmpty()) {
                    return round * mobs.sumOf { it.hp }
                }

                // MOVE

                // Only move if not already in range of a target.
                val mobNeighbors = mob.position.getNeighbors()
                if (mobNeighbors.none { map[it] == mob.enemySymbol }) {
                    // Pre-load steps from mob to every reachable open square.
                    val mobToTargetStepMap = pathfinder.exploreFrom(mob.position)

                    // Add any reachable, adjacent, open squares next to each target. End turn if there are none.
                    val targetSquares = mutableListOf<Pair<Int, Int>>()
                    for (squares in allTargetMobs.map { it.position.getNeighbors(false) }) {
                        targetSquares.addAll(squares.filter { it in mobToTargetStepMap.keys })
                    }
                    if (targetSquares.isEmpty()) {
                        continue
                    }

                    // Find shortest paths from mob to target's nearest squares. Break ties with reading order.
                    val mobToTargetPaths = countSteps(mobToTargetStepMap, mob.position, targetSquares)
                    val target = getBestInReadingOrder(mobToTargetPaths)

                    // Pre-load steps from target back to every reachable open square.
                    val targetToMobStepMap = pathfinder.exploreFrom(target)

                    // Find shortest paths from target back to mob's adjacent squares. Break ties with reading order.
                    val mobSquares = mobNeighbors.filter { map[it] !in "#EG" }
                    val targetToMobPaths = countSteps(targetToMobStepMap, target, mobSquares)
                    map[mob.position] = '.'
                    mob.position = getBestInReadingOrder(targetToMobPaths)
                    map[mob.position] = mob.symbol
                }

                // ATTACK

                // Find all targets in range. End turn if none are in range.
                val targets = mobs.filter { it.isElf != mob.isElf && it.position in mob.position.getNeighbors() }
                if (targets.isEmpty()) {
                    continue
                }

                // Pick the target with the fewest hit points (use reading order for ties).
                val target =
                    targets.sortedWith(compareBy({ it.hp }, { it.position.second }, { it.position.first })).first()
                target.hp -= if (mob.isElf) elfAttack else 3
                if (target.isDead()) {
                    map[target.position] = '.'
                    mobs.remove(target)     // Still need to check for this mob in this round in case of stale ref.
                    if (target.isElf) {
                        elvesDied = true
                        // Save time by not simulating the rest of the battle.
                        if (part.isTwo()) {
                            return -1
                        }
                    }
                }
            }
            round++
        }
    }

    /**
     * Stores the path lengths from a start to multiple ends using the "came from" map.
     */
    private fun countSteps(
        stepMap: Map<Pair<Int, Int>, Pair<Int, Int>?>,
        start: Pair<Int, Int>, ends: List<Pair<Int, Int>>
    ): MutableMap<Int, MutableList<Pair<Int, Int>>> {
        val paths = mutableMapOf<Int, MutableList<Pair<Int, Int>>>()
        for (end in ends) {
            val steps = stepMap.countSteps(start, end)
            if (steps != -1) {
                paths.putIfAbsent(steps, mutableListOf())
                paths[steps]!!.add(end)
            }
        }
        return paths
    }

    /**
     * Returns the point with the shortest distance, breaking ties with reading order.
     */
    private fun getBestInReadingOrder(map: Map<Int, MutableList<Pair<Int, Int>>>): Pair<Int, Int> {
        return map[map.keys.min()]!!.sortedWith(compareBy({ it.second }, { it.first })).first()
    }
}

data class Mob(val start: Pair<Int, Int>, val isElf: Boolean) {
    var position = start
    var hp = 200
    val symbol: Char
        get() = if (isElf) 'E' else 'G'
    val enemySymbol: Char
        get() = if (isElf) 'G' else 'E'

    /**
     * Returns true if the HP is 0 or less.
     */
    fun isDead(): Boolean = (hp <= 0)

    override fun toString(): String {
        return "$symbol($position,$hp)"
    }
}