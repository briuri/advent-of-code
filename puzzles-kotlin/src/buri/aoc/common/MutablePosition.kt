package buri.aoc.common

import buri.aoc.common.Direction.*

/**
 * Helper class for a 2D point and directional facing.
 *
 * @author Brian Uri!
 */
data class MutablePosition(var coords: Pair<Int, Int>, var facing: Direction) {

    /**
     * Move one square in the current direction.
     */
    fun move() {
        coords = when (facing) {
            NORTH -> coords.copy(second = coords.second - 1)
            EAST -> coords.copy(first = coords.first + 1)
            SOUTH -> coords.copy(second = coords.second + 1)
            WEST -> coords.copy(first = coords.first - 1)
        }
    }

    /**
     * Change the facing on this position
     */
    fun turn(turn: Char) {
        if (turn == 'R') {
            facing = when (facing) {
                NORTH -> EAST
                EAST -> SOUTH
                SOUTH -> WEST
                WEST -> NORTH
            }
        }
        // L
        else {
            facing = when (facing) {
                NORTH -> WEST
                EAST -> NORTH
                SOUTH -> EAST
                WEST -> SOUTH
            }
        }
    }
}
enum class Direction { NORTH, EAST, SOUTH, WEST }