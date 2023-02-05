package buri.aoc.y16.d11

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
        assertRun(11, 1)
        assertRun(31, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(55, 2, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        // Set up the start and end states.
        val start = State(0, input[0])
        val builder = StringBuilder("4")
        for (i in 0 until start.totalPairs) {
            builder.append("|44")
        }
        val end = State(0, builder.toString())

        val frontier = mutableListOf<State>()
        val visited = mutableSetOf<String>()
        frontier.add(start)
        var current: State?
        while (frontier.isNotEmpty()) {
            current = frontier.removeFirst()
            visited.add(current.toString())
            if (current == end) {
                return current.steps
            }
            for (next in current.getNextStates().filter { !visited.contains(it.toString()) }) {
                frontier.add(next)
            }
            frontier.sortByDescending { it.getScore() }
        }
        return -1
    }
}

class State(val steps: Int, private var state: String) {
    val totalPairs = (state.length - 1) / 3
    private val elevator = Character.getNumericValue(state[0])

    init {
        // Sort pairs to reduce the number of unique states reached.
        val pairs = mutableListOf<String>()
        for (i in 0 until totalPairs) {
            pairs.add(state.substring(toGeneratorIndex(i), toChipIndex(i) + 1))
        }
        pairs.sort()
        val builder = StringBuilder()
        builder.append(state[0])
        for (pair in pairs) {
            builder.append('|').append(pair)
        }
        state = builder.toString()
    }

    /**
     * Determines what other states we can reach from where we are.
     */
    fun getNextStates(): List<State> {
        val generatorsHere = getGeneratorsOnFloor(elevator)
        val chipsHere = getChipsOnFloor(elevator)
        val states = mutableListOf<State>()

        // Elevator can go up or down 1 floor.
        val nextElevatorFloors = mutableListOf<Int>()
        if (elevator <= 3) {
            nextElevatorFloors.add(elevator + 1)
        }
        if (elevator >= 2) {
            nextElevatorFloors.add(elevator - 1)
        }
        for (nextFloor in nextElevatorFloors) {
            val nextFloorChar = nextFloor.digitToChar()
            val generatorsNext = getGeneratorsOnFloor(nextFloor)
            val chipsNext = getChipsOnFloor(nextFloor)

            // Elevator can move matching pair (generator+chip) at any time.
            for (generatorId in generatorsHere) {
                if (chipsHere.contains(generatorId)) {
                    val builder = StringBuilder(state)
                    builder.setCharAt(0, nextFloorChar)
                    builder.setCharAt(toGeneratorIndex(generatorId), nextFloorChar)
                    builder.setCharAt(toChipIndex(generatorId), nextFloorChar)
                    states.add(State(steps + 1, builder.toString()))
                }
            }

            // Elevator can move 2 generators if they won't fry things on next floor.
            for (generatorIds in getPairPermutations(generatorsHere)) {
                if (areGeneratorsAllowedNear(generatorIds, generatorsNext, chipsNext)) {
                    val builder = StringBuilder(state)
                    builder.setCharAt(0, nextFloorChar)
                    builder.setCharAt(toGeneratorIndex(generatorIds[0]), nextFloorChar)
                    builder.setCharAt(toGeneratorIndex(generatorIds[1]), nextFloorChar)
                    states.add(State(steps + 1, builder.toString()))
                }
            }

            // Elevator can move 2 chips if they won't fry on next floor.
            for (chipIds in getPairPermutations(chipsHere)) {
                if (areChipsAllowedNear(chipIds, generatorsNext)) {
                    val builder = StringBuilder(state)
                    builder.setCharAt(0, nextFloorChar)
                    builder.setCharAt(toChipIndex(chipIds[0]), nextFloorChar)
                    builder.setCharAt(toChipIndex(chipIds[1]), nextFloorChar)
                    states.add(State(steps + 1, builder.toString()))
                }
            }

            // Elevator can move 1 generator if it won't fry things on next floor.
            for (generatorId in generatorsHere) {
                if (areGeneratorsAllowedNear(mutableListOf(generatorId), generatorsNext, chipsNext)) {
                    val builder = StringBuilder(state)
                    builder.setCharAt(0, nextFloorChar)
                    builder.setCharAt(toGeneratorIndex(generatorId), nextFloorChar)
                    states.add(State(steps + 1, builder.toString()))
                }
            }

            // Elevator can move 1 chip if it won't fry on next floor.
            for (chipId in chipsHere) {
                if (areChipsAllowedNear(mutableListOf(chipId), generatorsNext)) {
                    val builder = StringBuilder(state)
                    builder.setCharAt(0, nextFloorChar)
                    builder.setCharAt(toChipIndex(chipId), nextFloorChar)
                    states.add(State(steps + 1, builder.toString()))
                }
            }
        }
        return states
    }

    /**
     * Returns a score favoring items on higher floors.
     */
    fun getScore(): Int {
        var score = 0
        for (i in 1 .. state.lastIndex) {
            if (state[i] in "1234") {
                score += state[i].digitToInt()
            }
        }
        return score
    }

    override fun toString(): String {
        return state
    }

    override fun equals(other: Any?): Boolean {
        return (state == (other as State).state)
    }

    override fun hashCode(): Int {
        return state.hashCode()
    }

    /**
     * Get all permutations of the items
     */
    private fun getPairPermutations(items: List<Int>): MutableSet<List<Int>> {
        val permutations: MutableSet<MutableSet<Int>> = mutableSetOf()
        for (item1 in items) {
            for (item2 in items.filter { it != item1 }) {
                permutations.add(mutableSetOf(item1, item2))
            }
        }
        // Convert to list after using set for uniqueness.
        val list: MutableSet<List<Int>> = mutableSetOf()
        for (set in permutations) {
            list.add(set.toList())
        }
        return list
    }

    /**
     * Returns the IDs of all generators on some floor.
     */
    private fun getGeneratorsOnFloor(floor: Int): List<Int> {
        val generators = mutableListOf<Int>()
        for (i in 0 until totalPairs) {
            if (state[toGeneratorIndex(i)].digitToInt() == floor) {
                generators.add(i)
            }
        }
        return generators
    }

    /**
     * Returns the IDs of all microchips on some floor.
     */
    private fun getChipsOnFloor(floor: Int): List<Int> {
        val chips = mutableListOf<Int>()
        for (i in 0 until totalPairs) {
            if (state[toChipIndex(i)].digitToInt() == floor) {
                chips.add(i)
            }
        }
        return chips
    }

    /**
     * Returns true if generators are allowed near chips.
     */
    private fun areGeneratorsAllowedNear(generatorIds: List<Int>, generatorsNext: List<Int>, chipsNext: List<Int>): Boolean {
        var allowed = true
        for (chipId in chipsNext) {
            allowed = allowed && (generatorIds.contains(chipId) || generatorsNext.contains(chipId))
        }
        return allowed
    }

    /**
     * Returns true if chips are allowed near generators.
     */
    private fun areChipsAllowedNear(chipIds: List<Int>, generatorsNext: List<Int>): Boolean {
        var allowed = true
        for (id in chipIds) {
            allowed = allowed && generatorsNext.contains(id)
        }
        allowed = allowed || generatorsNext.isEmpty()
        return allowed
    }

    /**
     * Converts a pair ID into its location in the state string.
     */
    private fun toGeneratorIndex(id: Int): Int {
        return 2 + id * 3
    }

    /**
     * Converts a pair ID into its location in the state string.
     */
    private fun toChipIndex(id: Int): Int {
        return 3 + id * 3
    }
}