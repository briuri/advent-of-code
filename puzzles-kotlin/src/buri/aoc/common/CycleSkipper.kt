package buri.aoc.common

/**
 * Utility class for locating cycles in a step-based process.
 *
 * The keyStrategy is used to generate a state key at each step.
 * The stepStrategy executes some arbitrary code at each step.
 *
 * Originally written for y23d14.
 *
 * @author Brian Uri!
 */
class CycleSkipper(private val keyStrategy: () -> String, private val stepStrategy: () -> Unit) {

    /**
     * Starts a process and calculates the first time a cycle occurs, its length, and (given a target end step)
     * the last time the cycle will occur before the process ends. Then jumps ahead to the last cycle
     * and finishes all iterations.
     *
     * The initial state is stored as step 0, so step 1 is the first iteration through the process.
     */
    fun run(targetEnd: Int) {
        val states = mutableMapOf<String, Int>()
        var step = 0
        while (step < targetEnd) {
            val stateKey = keyStrategy()
            val firstCycle = states[stateKey]
            if (firstCycle != null) {
                val cycleLength = step - firstCycle
                val remainingCycles = (targetEnd - step) / cycleLength + 1  // integer division
                // Skip ahead a whole number of cycles
                step = firstCycle + cycleLength * remainingCycles
            }
            states[stateKey] = step
            step++
            stepStrategy()
        }
    }
}