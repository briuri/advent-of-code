package buri.aoc.common

/**
 * Search utility for finding paths between points.
 *
 * @author Brian Uri!
 */
class Pathfinder(val stepStrategy: (Pair<Int, Int>) -> List<Pair<Int, Int>>) {
    val stepsTo = mutableMapOf<Pair<Int, Int>, Int>()

    /**
     * Returns the smallest number of steps between two points using a breadth-first search, or
     * -1 if there is no path.
     */
    fun countSteps(start: Pair<Int, Int>, end: Pair<Int, Int>): Int {
        val frontier = ArrayDeque<Pair<Int, Int>>()
        frontier.add(start)

        stepsTo.clear()
        stepsTo[start] = 0

        var current: Pair<Int, Int>?
        while (frontier.isNotEmpty()) {
            current = frontier.removeFirst()
            if (current == end) {
                break
            }
            for (next in stepStrategy(current).filter { !stepsTo.containsKey(it) }) {
                frontier.add(next)
                stepsTo[next] = stepsTo[current]!! + 1
            }
        }
        return stepsTo[end] ?: -1
    }

    /**
     * Returns a "came from" map showing all the reachable spaces from a particular space.
     */
    fun exploreFrom(start: Pair<Int, Int>): Map<Pair<Int, Int>, Pair<Int, Int>?> {
        val frontier = ArrayDeque<Pair<Int, Int>>()
        frontier.add(start)

        val cameFrom = mutableMapOf<Pair<Int, Int>, Pair<Int, Int>?>()
        cameFrom[start] = null

        var current: Pair<Int, Int>?
        while (frontier.isNotEmpty()) {
            current = frontier.removeFirst()
            for (next in stepStrategy(current).filter { !cameFrom.containsKey(it) }) {
                frontier.add(next)
                cameFrom[next] = current
            }
        }
        return cameFrom
    }
}

/**
 * Extension function to count the steps in a "came from" map.
 */
fun Map<Pair<Int, Int>, Pair<Int, Int>?>.countSteps(start: Pair<Int, Int>, end: Pair<Int, Int>): Int {
    if (start == end) {
        return 0
    }
    var steps = 0
    var current: Pair<Int, Int>? = end
    while (current != null) {
        steps++
        current = this[current]
        if (current == start) {
            return steps
        }
    }
    return -1
}