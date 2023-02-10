package buri.aoc.common

/**
 * Search utility for finding paths between points.
 *
 * @author Brian Uri!
 */
class Pathfinder(val stepStrategy: (Pair<Int, Int>) -> List<Pair<Int, Int>>) {
    val stepsTo = mutableMapOf<Pair<Int, Int>, Int>()

    /**
     * Returns the smallest number of steps between two points using a breadth-first search.
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
        return stepsTo[end]!!
    }
}