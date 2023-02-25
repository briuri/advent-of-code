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
                    map[x, y] = '.'
                } else {
                    map[x, y] = value
                }
            }
        }

        if (part.isOne()) {
            val battle = Simulation(map, 3)
            return battle.run(part, mobs)
        }

        var attack = 4
        var outcome: Int
        do {
            val battle = Simulation(map, attack)
            outcome = battle.run(part, mobs)
            attack++
        } while (battle.elvesDied)
        return outcome
    }

}

class Simulation(private val map: Grid<Char>, private val elfAttack: Int) {
    var elvesDied = false

    /**
     * Runs a simulation of a goblin/elf battle.
     */
    fun run(part: Part, rawMobs: List<Mob>): Int {
        val mobs = mutableListOf<Mob>()
        for (mob in rawMobs) {
            mobs.add(mob.copy())
        }

        // Use a pathfinder that avoids walls and other mobs.
        val pathfinder = Pathfinder { current ->
            current.getNeighbors(false).filter { isTraversable(map, mobs, it) }
        }

        var round = 0
        while (true) {
            // Take turns in reading order.
            for (mob in mobs.sortedWith(compareBy({ it.position.second }, { it.position.first }))) {
                // Skip mobs that died this round (stale reference).
                if (mob.isDead()) {
                    continue
                }
                // Identify targets.
                val allTargetMobs = mobs.filter { it.isElf != mob.isElf }
                // Combat ends when no target remains.
                if (allTargetMobs.isEmpty()) {
                    return round * mobs.sumOf { it.hp }
                }

                // MOVE

                // Only move if not already in range of a target.
                val allTargetMobPositions = allTargetMobs.map { it.position }
                if (mob.position.getNeighbors().none { it in allTargetMobPositions }) {
                    // Pre-load steps from mob to every reachable open square.
                    val mobToTargetStepMap = pathfinder.exploreFrom(mob.position)

                    // Add any reachable, adjacent, open squares next to each target.
                    val targetSquares = mutableListOf<Pair<Int, Int>>()
                    for (squares in allTargetMobs.map { it.position.getNeighbors(false) }) {
                        targetSquares.addAll(squares.filter { it in mobToTargetStepMap.keys })
                    }
                    // If there are no reachable open squares, end turn.
                    if (targetSquares.isEmpty()) {
                        continue
                    }

                    // Find shortest paths from mob to target's nearest squares.
                    val mobToTargetPaths = countSteps(mobToTargetStepMap, mob.position, targetSquares)
                    // Pick the target destination that is nearest in reading order.
                    val target = getBestInReadingOrder(mobToTargetPaths)

                    // Pre-load steps from target back to every reachable open square.
                    val targetToMobStepMap = pathfinder.exploreFrom(target)

                    // Find shortest path from target square back to mob's adjacent squares.
                    val mobSquares = mob.position.getNeighbors().filter { isTraversable(map, mobs, it) }
                    val targetToMobPaths = countSteps(targetToMobStepMap, target, mobSquares)
                    // Pick the next step with the shortest path, breaking ties with reading order.
                    mob.position = getBestInReadingOrder(targetToMobPaths)
                }

                // ATTACK

                // Find all targets in range.
                val targets = mobs.filter { it.isElf != mob.isElf && it.position in mob.position.getNeighbors() }
                // If there are no targets, end turn.
                if (targets.isEmpty()) {
                    continue
                }

                // Pick the target with the fewest hit points (use reading order for ties).
                val target =
                    targets.sortedWith(compareBy({ it.hp }, { it.position.second }, { it.position.first })).first()
                target.hp -= if (mob.isElf) elfAttack else 3
                if (target.isElf && target.isDead()) {
                    elvesDied = true
                    // Save time by not simulating the rest of the battle.
                    if (part.isTwo()) {
                        return -1
                    }
                }
                // Drop this mob from future rounds (still need to check this round!)
                mobs.removeIf { it.isDead() }
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

    /**
     * Returns true if a square is open space with no mobs in it.
     */
    private fun isTraversable(map: Grid<Char>, mobs: MutableList<Mob>, point: Pair<Int, Int>): Boolean {
        return (map[point] != '#' && point !in mobs.map { it.position })
    }
}

data class Mob(val start: Pair<Int, Int>, val isElf: Boolean) {
    var position = start
    var hp = 200

    /**
     * Returns true if the HP is 0 or less.
     */
    fun isDead(): Boolean = (hp <= 0)
}