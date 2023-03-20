package buri.aoc.common

import buri.aoc.common.position.Point2D

/**
 * Search utility for finding paths between points.
 *
 * @author Brian Uri!
 */
class Pathfinder(val stepStrategy: (Point2D<Int>) -> List<Point2D<Int>>) {

    /**
     * Returns a "came from" map showing all the reachable spaces from a particular space. The entry for the
     * starting position start will have a null value.
     */
    fun exploreFrom(start: Point2D<Int>): Map<Point2D<Int>, Point2D<Int>?> {
        val frontier = ArrayDeque<Point2D<Int>>()
        frontier.add(start)

        val cameFrom = mutableMapOf<Point2D<Int>, Point2D<Int>?>()
        cameFrom[start] = null

        var current: Point2D<Int>?
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
 * Extension function to count the steps in a "came from" map. Returns -1 if there is no path between the points.
 */
fun <T> Map<T, T?>.countSteps(start: T, end: T): Int {
    if (start == end) {
        return 0
    }
    var steps = 0
    var current: T? = end
    while (current != null) {
        steps++
        current = this[current]
        if (current == start) {
            return steps
        }
    }
    return -1
}