package buri.aoc.y21.d23

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.position.Point2D
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(12521, 1)
        assertRun(16244, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(44169, 2)
        assertRun(43226, 3, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val lowestCosts = mutableMapOf<String, Long>()
        var lowestEnd = Long.MAX_VALUE

        val firstState = State(input[0])
        val frontier = ArrayDeque<State>()
        frontier.addFirst(firstState)
        while (frontier.isNotEmpty()) {
            val current = frontier.removeFirst()
            for (next in current.getNextStates()) {
                if (next.cost < lowestCosts.getOrDefault(next.toString(), Long.MAX_VALUE)) {
                    if (next.isFinished()) {
                        lowestEnd = lowestEnd.coerceAtMost(next.cost)
                    } else if (next.cost < lowestEnd) {
                        frontier.addFirst(next)
                    }
                    lowestCosts[next.toString()] = next.cost
                }
            }
        }
        return lowestEnd
    }
}

const val EMPTY = '.'
const val INVALID = '_'
val COSTS = mapOf('A' to 1, 'B' to 10, 'C' to 100, 'D' to 1000)

class State(data: String, val cost: Long = 0L) {
    private val hall: Hall
    private val rooms = mutableMapOf<Char, Room>()

    init {
        val tokens = data.split(" ")
        hall = Hall(tokens[0])
        for (token in tokens.drop(1)) {
            rooms[token[0]] = Room(token)
        }
    }

    /**
     * Find all possible next moves from the current state.
     */
    fun getNextStates(): List<State> {
        val nextStates = mutableListOf<State>()
        // Moving a hall pod into its room is always the best option.
        val hallPods = hall.getPods()
        val occupiedHallSpaces = hallPods.map { it.first }
        for ((pod, value) in hallPods) {
            val roomSpace = rooms[value]!!.getDeepestEmpty()
            if (roomSpace != null) {
                var isHallClear = true
                if (roomSpace.x < pod.x) {
                    for (x in roomSpace.x + 1 until pod.x) {
                        isHallClear = isHallClear && (Point2D(x, 1) !in occupiedHallSpaces)
                    }
                } else {
                    for (x in pod.x + 1 until roomSpace.x) {
                        isHallClear = isHallClear && (Point2D(x, 1) !in occupiedHallSpaces)
                    }
                }
                if (isHallClear) {
                    nextStates.add(buildNextState(pod, roomSpace, value))
                    break
                }
            }
        }
        // Only consider moving pods into hall if no pods can move into rooms.
        if (nextStates.isEmpty()) {
            val pods = mutableListOf<Pair<Point2D<Int>, Char>?>()
            pods.addAll(rooms.values.map { it.getShallowestPod() })
            for ((pod, value) in pods.filterNotNull()) {
                for (hallSpace in hall.getSpaces(pod)) {
                    nextStates.add(buildNextState(pod, hallSpace, value))
                }
            }
        }
        return nextStates
    }

    /**
     * Builds a next state.
     */
    private fun buildNextState(pod: Point2D<Int>, space: Point2D<Int>, value: Char): State {
        val next = State(toString(), cost + pod.getManhattanDistance(space) * COSTS[value]!!)
        next[pod] = EMPTY
        next[space] = value
        return next
    }

    /**
     * Sets the value of some position in the hallway or a room.
     */
    operator fun set(space: Point2D<Int>, value: Char) {
        if (space.y == 1) {
            hall[space] = value
        } else {
            val room = rooms.values.first { it.xIndex == space.x }
            room[space] = value
        }
    }

    /**
     * Returns true if every room is finished.
     */
    fun isFinished(): Boolean {
        return rooms.values.all { it.isFinished() }
    }

    override fun toString(): String {
        val output = StringBuilder("$hall")
        for (room in rooms.values) {
            output.append(" $room")
        }
        return output.toString()
    }
}

/**
 * H|.._._._._..
 */
class Hall(data: String) {
    private val spaces = data.drop(2).toCharArray()

    /**
     * Sets the value of some position in the hallway.
     */
    operator fun set(space: Point2D<Int>, value: Char) {
        spaces[space.x - 1] = value
    }

    /**
     * Returns a list of any pods in the hallway (may be empty). Ds are first in the list.
     */
    fun getPods(): List<Pair<Point2D<Int>, Char>> {
        val pods = mutableListOf<Pair<Point2D<Int>, Char>>()
        for ((index, type) in spaces.withIndex()) {
            if (type != INVALID && type != EMPTY) {
                pods.add(Pair(Point2D(index + 1, 1), type))
            }
        }
        return pods.sortedByDescending { it.second }
    }

    /**
     * Returns a list of the reachable empty hallway spots, or an empty list if all near spots are full.
     */
    fun getSpaces(space: Point2D<Int>): List<Point2D<Int>> {
        val nearest = mutableListOf<Point2D<Int>>()
        var left = space.x - 1
        while (left > 0) {
            if (spaces[left - 1] == EMPTY) {
                nearest.add(Point2D(left, 1))
            } else if (spaces[left - 1] != INVALID) {
                break
            }
            left--
        }
        var right = space.x + 1
        while (right < 12) {
            if (spaces[right - 1] == EMPTY) {
                nearest.add(Point2D(right, 1))
            } else if (spaces[right - 1] != INVALID) {
                break
            }
            right++
        }
        return nearest
    }

    override fun toString(): String = "H|${spaces.joinToString("")}"
}

/**
 * A|.CDA
 */
class Room(data: String) {
    private val spaces = data.drop(2).toCharArray()
    private val type = data[0]
    val xIndex = when (type) {
        'A' -> 3
        'B' -> 5
        'C' -> 7
        else -> 9
    }

    /**
     * Sets the value of some position in the room.
     */
    operator fun set(space: Point2D<Int>, value: Char) {
        spaces[space.y - 2] = value
    }

    /**
     * Returns the first pod that can leave the room, or null if the room is finished.
     */
    fun getShallowestPod(): Pair<Point2D<Int>, Char>? {
        for (y in 0 until spaces.size - getProgress()) {
            if (spaces[y] != EMPTY) {
                return Pair(Point2D(xIndex, y + 2), spaces[y])
            }
        }
        return null
    }

    /**
     * Returns the deepest space reachable in the room, or null if the room is blocked or finished.
     */
    fun getDeepestEmpty(): Point2D<Int>? {
        val wrongPods = spaces.count { it != type && it != EMPTY }
        if (wrongPods == 0) {
            val y = spaces.indexOfLast { it == EMPTY }
            if (y != -1) {
                return Point2D(xIndex, y + 2)
            }
        }
        return null
    }

    /**
     * Returns a count of how many spaces are finished.
     */
    private fun getProgress(): Int {
        var count = 0
        for (i in spaces.lastIndex downTo 0) {
            if (spaces[i] == type) {
                count++
            } else {
                break
            }
        }
        return count
    }

    /**
     * Returns true if every space in the room has the right type of pod.
     */
    fun isFinished(): Boolean = getProgress() == spaces.size

    override fun toString(): String = "$type|${spaces.joinToString("")}"
}