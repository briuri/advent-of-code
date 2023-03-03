package buri.aoc.common

/**
 * Search utility for finding paths between points.
 *
 * @author Brian Uri!
 */
class Pathfinder(val stepStrategy: (Pair<Int, Int>) -> List<Pair<Int, Int>>) {

    /**
     * Returns a "came from" map showing all the reachable spaces from a particular space. The entry for the
     * starting position start will have a null value.
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
    fun exploreFrom(startX: Int, startY: Int): Map<Pair<Int, Int>, Pair<Int, Int>?> = exploreFrom(Pair(startX, startY))

}

/**
 * Extension function to count the steps in a "came from" map. Returns -1 if there is no path between the points.
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