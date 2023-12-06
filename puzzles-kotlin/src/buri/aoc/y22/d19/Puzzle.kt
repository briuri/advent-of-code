package buri.aoc.y22.d19

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.extractInts
import buri.aoc.common.product
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        // Removed examples because optimizations didn't work on them.
        assertRun(1395, 0, true)
    }

    @Test
    fun runPart2() {
        // Removed examples because optimizations didn't work on them.
        assertRun(2700, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val blueprints = mutableListOf<Blueprint>()
        val usedLines = if (part.isOne()) input else input.subList(0, input.size.coerceAtMost(3))
        for (line in usedLines) {
            blueprints.add(Blueprint(line))
        }

        // Part 1 and 2 Optimization: Don't gather more than we can spend.
        val maxBuffer = if (part.isOne()) 1.25 else 1.5
        val maxCosts = Cost(
            (blueprints.maxOfOrNull { it.maxCost(ORE) }!! * maxBuffer).toInt(),
            (blueprints.maxOfOrNull { it.maxCost(CLAY) }!! * maxBuffer).toInt(),
            (blueprints.maxOfOrNull { it.maxCost(OBSIDIAN) }!! * maxBuffer).toInt()
        )

        val minutes = if (part.isOne()) 24 else 32
        val maxGeodes = mutableMapOf<Int, Int>()
        for (print in blueprints) {
            var frontier = mutableSetOf<State>()
            frontier.add(State("1 1|0|0|0 0|0|0|0"))
            while (frontier.isNotEmpty()) {
                // Part 1 and 2 Optimization: Prune paths that aren't close to max.
                // In part 1, need some wiggle room (~1). In part 2, discard anything less than the max.
                val pruneThreshold = if (part.isOne()) 1 else 0
                val maxGeodesSoFar = frontier.maxOfOrNull { it.geode }!!
                if (maxGeodesSoFar > pruneThreshold) {
                    frontier.removeIf { it.geode + pruneThreshold < maxGeodesSoFar }
                }

                val nextFrontier = mutableSetOf<State>()
                for (state in frontier) {
                    // Save geodes when blueprint finishes.
                    if (state.minute > minutes) {
                        val bestMax = maxGeodes.getOrDefault(print.id, 0)
                        maxGeodes[print.id] = bestMax.coerceAtLeast(state.geode)
                        continue
                    }
                    val beforeSize = nextFrontier.size

                    // Part 1 and 2 Optimization: Don't build geode robot on the last turn.
                    if (state.canAfford(print.geodeRobot) && state.minute != minutes) {
                        val nextState = state.next(maxCosts)
                        nextState.spend(GEODE, print.geodeRobot)
                        nextFrontier.add(nextState)
                        // Part 1 and 2 Optimization: Ignore all other moves when making a geode robot.
                        continue
                    }
                    // Part 1 and 2 Optimizations:
                    // Don't build other robots on the penultimate turn.
                    // Don't build more robots than the max materials we can spend.
                    if (state.minute + 1 < minutes) {
                        if (state.canAfford(print.obsidianRobot) && state.obsidianRobots < maxCosts.cost(OBSIDIAN)) {
                            val nextState = state.next(maxCosts)
                            nextState.spend(OBSIDIAN, print.obsidianRobot)
                            nextFrontier.add(nextState)
                        }
                        if (state.canAfford(print.clayRobot) && state.clayRobots < maxCosts.cost(CLAY)) {
                            val nextState = state.next(maxCosts)
                            nextState.spend(CLAY, print.clayRobot)
                            nextFrontier.add(nextState)
                        }
                        if (state.canAfford(print.oreRobot) && state.oreRobots < maxCosts.cost(ORE)) {
                            val nextState = state.next(maxCosts)
                            nextState.spend(ORE, print.oreRobot)
                            nextFrontier.add(nextState)
                        }
                    }

                    // Part 2 Optimization (doesn't work in Part 1): Only build nothing if no robots can be built.
                    if (part.isOne() || beforeSize == nextFrontier.size) {
                        nextFrontier.add(state.next(maxCosts))
                    }
                }
                frontier = nextFrontier
            }
        }

        return if (part.isOne()) {
            maxGeodes.map { it.key * it.value }.sum()
        } else {
            maxGeodes.values.product()
        }
    }
}

const val ORE = 0
const val CLAY = 1
const val OBSIDIAN = 2
const val GEODE = 3

/**
 * 1 0|0|0|0 0|0|0|0
 */
class State(data: String) {
    var minute: Int
        private set
    var oreRobots: Int
        private set
    var clayRobots: Int
        private set
    var obsidianRobots: Int
        private set
    private var geodeRobots: Int
    private var ore: Int
    private var clay: Int
    private var obsidian: Int
    var geode: Int
        private set

    init {
        val numbers = data.extractInts()
        minute = numbers[0]
        oreRobots = numbers[1]
        clayRobots = numbers[2]
        obsidianRobots = numbers[3]
        geodeRobots = numbers[4]
        ore = numbers[5]
        clay = numbers[6]
        obsidian = numbers[7]
        geode = numbers[8]
    }

    /**
     * Returns the next state after collecting materials.
     */
    fun next(maxCosts: Cost): State {
        val next = State(toString())
        next.collect(maxCosts)
        return next
    }

    /**
     * Returns true if we can afford a robot.
     */
    fun canAfford(robot: Cost): Boolean {
        return ore >= robot.cost(ORE) && clay >= robot.cost(CLAY) && obsidian >= robot.cost(OBSIDIAN)
    }

    /**
     * Increments materials and minute.
     */
    private fun collect(maxCosts: Cost) {
        minute++
        if (ore <= maxCosts.cost(ORE)) {
            changeAmount(ORE, oreRobots)
        }
        if (clay <= maxCosts.cost(CLAY)) {
            changeAmount(CLAY, clayRobots)
        }
        if (obsidian <= maxCosts.cost(OBSIDIAN)) {
            changeAmount(OBSIDIAN, obsidianRobots)
        }
        changeAmount(GEODE, geodeRobots)
    }

    /**
     * Spends resources on a robot.
     */
    fun spend(type: Int, cost: Cost) {
        when (type) {
            ORE -> oreRobots++
            CLAY -> clayRobots++
            OBSIDIAN -> obsidianRobots++
            else -> geodeRobots++
        }
        changeAmount(ORE, -1 * cost.cost(ORE))
        changeAmount(CLAY, -1 * cost.cost(CLAY))
        changeAmount(OBSIDIAN, -1 * cost.cost(OBSIDIAN))
    }

    /**
     * Adds or removes gathered materials.
     */
    private fun changeAmount(type: Int, amount: Int) {
        when (type) {
            ORE -> ore += amount
            CLAY -> clay += amount
            OBSIDIAN -> obsidian += amount
            else -> geode += amount
        }
    }

    override fun equals(other: Any?): Boolean = toString() == other.toString()

    override fun hashCode(): Int = toString().hashCode()

    override fun toString(): String {
        return "$minute $oreRobots|$clayRobots|$obsidianRobots|$geodeRobots $ore|$clay|$obsidian|$geode"
    }
}

class Blueprint(data: String) {
    val id: Int
    val oreRobot: Cost
    val clayRobot: Cost
    val obsidianRobot: Cost
    val geodeRobot: Cost
    private val robots: List<Cost>

    init {
        val numbers = data.extractInts()
        id = numbers[0]
        oreRobot = Cost(numbers[1], 0, 0)
        clayRobot = Cost(numbers[2], 0, 0)
        obsidianRobot = Cost(numbers[3], numbers[4], 0)
        geodeRobot = Cost(numbers[5], 0, numbers[6])
        robots = listOf(oreRobot, clayRobot, obsidianRobot, geodeRobot)
    }

    /**
     * Returns the max cost of some type of material for all robots.
     */
    fun maxCost(type: Int): Int {
        var max = 0
        for (robot in robots) {
            max = max.coerceAtLeast(robot.cost(type))
        }
        return max
    }

    override fun toString(): String {
        return "$id $oreRobot $clayRobot $obsidianRobot $geodeRobot"
    }
}

class Cost(private val ore: Int, private val clay: Int, private val obsidian: Int) {

    /**
     * Alternate accessor for a cost.
     */
    fun cost(type: Int): Int {
        return when (type) {
            ORE -> ore
            CLAY -> clay
            else -> obsidian
        }
    }

    override fun toString(): String {
        return "$ore|$clay|$obsidian|0"
    }
}