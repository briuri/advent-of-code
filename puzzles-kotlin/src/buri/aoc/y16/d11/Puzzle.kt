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
        // Set up the start and final states.
        val start = State(0, input[0])
        val builder = StringBuilder("4")
        for (i in 0 until start.totalPairs) {
            builder.append("|44")
        }
        val end = State(0, builder.toString())

        val frontier = mutableListOf<State>()
        frontier.add(start)
        val visited = mutableSetOf<State>()
        var current: State?
        while (frontier.isNotEmpty()) {
            current = frontier.removeFirst()
            visited.add(current)
            if (current == end) {
                return current.steps
            }
            for (next in current.getNextStates().filter { it !in visited }) {
                frontier.add(next)
            }
            frontier.sortByDescending { it.getSortOrder() }
        }
        return -1
    }
}

class State(val steps: Int, unsortedState: String) {
    val totalPairs = (unsortedState.length - 1) / 3
    val state: String
    private val elevator = Character.getNumericValue(unsortedState[0])

    init {
        val pairs = mutableListOf<String>()
        for (i in 0 until totalPairs) {
            pairs.add(unsortedState.substring(toIndex(i, true), toIndex(i, false) + 1))
        }
        // Sort pairs to reduce the number of unique states reached.
        pairs.sort()
        val builder = StringBuilder()
        builder.append(unsortedState[0])
        for (pair in pairs) {
            builder.append('|').append(pair)
        }
        state = builder.toString()
    }

    /**
     * Determines what other states we can reach from where we are.
     */
    fun getNextStates(): List<State> {
        val gensHere = getItemsOn(elevator, true)
        val chipsHere = getItemsOn(elevator, false)
        val nextStates = mutableListOf<State>()

        // Elevator can go up or down 1 floor.
        val nextFloors = mutableListOf<Int>()
        if (elevator <= 3) {
            nextFloors.add(elevator + 1)
        }
        if (elevator >= 2) {
            nextFloors.add(elevator - 1)
        }
        for (nextFloor in nextFloors) {
            val nextFloorChar = nextFloor.digitToChar()
            val nextGens = getItemsOn(nextFloor, true)
            val nextChips = getItemsOn(nextFloor, false)
            val nextSteps = steps + 1

            // Elevator can move matching pair (gen+chip) at any time.
            for (genId in gensHere.filter { it in chipsHere }) {
                val builder = toBuilder(nextFloorChar)
                builder.setCharAt(toIndex(genId, true), nextFloorChar)
                builder.setCharAt(toIndex(genId, false), nextFloorChar)
                nextStates.add(State(nextSteps, builder.toString()))
            }

            // Elevator can move 2 gens if they won't fry things on next floor.
            for (genIds in getPairPermutations(gensHere).filter { isGenAllowedNear(it, nextGens, nextChips) }) {
                val builder = toBuilder(nextFloorChar)
                builder.setCharAt(toIndex(genIds[0], true), nextFloorChar)
                builder.setCharAt(toIndex(genIds[1], true), nextFloorChar)
                nextStates.add(State(nextSteps, builder.toString()))
            }

            // Elevator can move 2 chips if they won't fry on next floor.
            for (chipIds in getPairPermutations(chipsHere).filter { isChipAllowedNear(it, nextGens) }) {
                val builder = toBuilder(nextFloorChar)
                builder.setCharAt(toIndex(chipIds[0], false), nextFloorChar)
                builder.setCharAt(toIndex(chipIds[1], false), nextFloorChar)
                nextStates.add(State(nextSteps, builder.toString()))
            }

            // Elevator can move 1 gen if it won't fry things on next floor.
            for (genId in gensHere.filter { isGenAllowedNear(mutableListOf(it), nextGens, nextChips) }) {
                val builder = toBuilder(nextFloorChar)
                builder.setCharAt(toIndex(genId, true), nextFloorChar)
                nextStates.add(State(nextSteps, builder.toString()))
            }

            // Elevator can move 1 chip if it won't fry on next floor.
            for (chipId in chipsHere.filter { isChipAllowedNear(mutableListOf(it), nextGens) }) {
                val builder = toBuilder(nextFloorChar)
                builder.setCharAt(toIndex(chipId, false), nextFloorChar)
                nextStates.add(State(nextSteps, builder.toString()))
            }
        }
        return nextStates
    }

    /**
     * Returns a score favoring items on higher floors.
     */
    fun getSortOrder(): Int {
        var score = 0
        for (i in 1..state.lastIndex) {
            if (state[i] in "1234") {
                score += state[i].digitToInt()
            }
        }
        return score
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
     * Creates a string builder for modification
     */
    private fun toBuilder(nextFloorChar: Char): StringBuilder {
        val builder = StringBuilder(state)
        builder.setCharAt(0, nextFloorChar)
        return builder
    }

    /**
     * Returns the IDs of all generators on some floor.
     */
    private fun getItemsOn(floor: Int, isGen: Boolean): List<Int> {
        val items = mutableListOf<Int>()
        for (i in 0 until totalPairs) {
            if (state[toIndex(i, isGen)].digitToInt() == floor) {
                items.add(i)
            }
        }
        return items
    }

    /**
     * Returns true if generators are allowed near chips.
     */
    private fun isGenAllowedNear(genIds: List<Int>, nextGens: List<Int>, nextChips: List<Int>): Boolean {
        var allowed = true
        for (chipId in nextChips) {
            allowed = allowed && (chipId in genIds || chipId in nextGens)
        }
        return allowed
    }

    /**
     * Returns true if chips are allowed near generators.
     */
    private fun isChipAllowedNear(chipIds: List<Int>, nextGens: List<Int>): Boolean {
        var allowed = true
        for (id in chipIds) {
            allowed = allowed && (id in nextGens)
        }
        allowed = allowed || nextGens.isEmpty()
        return allowed
    }

    /**
     * Converts a pair ID into its location in the state string.
     */
    private fun toIndex(id: Int, isGen: Boolean): Int {
        val offset = if (isGen) 2 else 3
        return offset + id * 3
    }

    override fun toString(): String = state
    override fun equals(other: Any?): Boolean = (state == (other as State).state)
    override fun hashCode(): Int = state.hashCode()
}