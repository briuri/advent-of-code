package buri.aoc.y15.d21

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
        assertRun(91, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(158, 0, true)
    }

    private val weapons = mutableListOf<Item>()
    private val armors = mutableListOf<Item>()
    private val rings = mutableListOf<Item>()

    init {
        weapons.add(Item(8, 4, 0))
        weapons.add(Item(10, 5, 0))
        weapons.add(Item(25, 6, 0))
        weapons.add(Item(40, 7, 0))
        weapons.add(Item(74, 8, 0))
        armors.add(Item(0, 0, 0))
        armors.add(Item(13, 0, 1))
        armors.add(Item(31, 0, 2))
        armors.add(Item(53, 0, 3))
        armors.add(Item(75, 0, 4))
        armors.add(Item(102, 0, 5))
        rings.add(Item(0, 0, 0))
        rings.add(Item(0, 0, 0))
        rings.add(Item(25, 1, 0))
        rings.add(Item(50, 2, 0))
        rings.add(Item(100, 3, 0))
        rings.add(Item(20, 0, 1))
        rings.add(Item(40, 0, 2))
        rings.add(Item(80, 0, 3))
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val boss = Mob(
            input[0].split(" ")[2].toInt(),
            input[1].split(" ")[1].toInt(),
            input[2].split(" ")[1].toInt(),
        )
        var minCost = Int.MAX_VALUE
        var maxCost = Int.MIN_VALUE
        for (weapon in weapons) {
            for (armor in armors) {
                for (ring1 in rings) {
                    for (ring2 in rings) {
                        val cost = weapon.cost + armor.cost + ring1.cost + ring2.cost
                        val damageTotal = weapon.damage + armor.damage + ring1.damage + ring2.damage
                        val armorTotal = weapon.armor + armor.armor + ring1.armor + ring2.armor
                        val me = Mob(100, damageTotal, armorTotal)
                        if (isBossDefeated(me, boss)) {
                            minCost = minCost.coerceAtMost(cost)
                        } else {
                            maxCost = maxCost.coerceAtLeast(cost)
                        }
                    }
                }
            }
        }
        return if (part.isOne()) minCost else maxCost
    }

    /**
     * Simulates a battle between two mobs. Returns true if I win.
     */
    private fun isBossDefeated(me: Mob, boss: Mob): Boolean {
        var myHp = me.hp
        var bossHp = boss.hp
        val myAttack = (me.damage - boss.armor).coerceAtLeast(1)
        val bossAttack = (boss.damage - me.armor).coerceAtLeast(1)
        while (true) {
            bossHp -= myAttack
            if (bossHp <= 0) {
                return true
            }
            myHp -= bossAttack
            if (myHp <= 0) {
                return false
            }
        }
    }
}

data class Mob(val hp: Int, val damage: Int, val armor: Int)
data class Item(val cost: Int, val damage: Int, val armor: Int)