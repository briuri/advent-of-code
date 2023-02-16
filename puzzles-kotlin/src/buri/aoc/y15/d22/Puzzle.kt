package buri.aoc.y15.d22

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.Part.TWO
import org.junit.Test

/**
 * Entry point for a daily puzzle
 *
 * @author Brian Uri!
 */
class Puzzle : BasePuzzle() {
    @Test
    fun runPart1() {
        assertRun(953, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(1289, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val bossMaxHp = input[0].split(" ")[2].toInt()
        val bossDamage = input[1].split(" ")[1].toInt()
        var minMana = Int.MAX_VALUE
        // Experimented with upper bound until I got a stable answer
        for (i in 0..70_000) {
            minMana = minMana.coerceAtMost(runBattle(part, bossMaxHp, bossDamage, minMana))
        }
        return minMana
    }

    private val spells = mutableMapOf(
        "M" to Spell(53, 0, 0, 4),
        "D" to Spell(73, 0, 2, 2),
        "S" to Spell(113, 6, 7, 0),
        "P" to Spell(173, 6, 0, 3),
        "R" to Spell(229, 5, 101, 0)
    )

    /**
     * Simulates a battle with randomly selected spells.
     */
    private fun runBattle(part: Part, bossMaxHp: Int, bossDamage: Int, minMana: Int): Int {
        var bossHp = bossMaxHp
        var myHp = 50
        var myArmor = 0
        var myMana = 500

        var manaSpent = 0
        var isMyTurn = true
        val activeEffects = mutableMapOf<String, Int>()
        while (true) {
            if (part == TWO && isMyTurn) {
                myHp -= 1
            }

            // Apply Effects
            for ((effect, duration) in activeEffects) {
                val spell = spells[effect]!!
                when (effect) {
                    "S" -> {
                        myArmor = if (duration > 1) spell.meEffect else 0
                    }
                    "P" -> {
                        bossHp -= spell.bossEffect
                    }
                    "R" -> {
                        myMana += spell.meEffect
                    }
                }
                val tick = activeEffects[effect]!! - 1
                activeEffects[effect] = tick
            }
            activeEffects.entries.removeIf { it.value == 0 }

            // Win Condition
            if (bossHp <= 0) {
                return manaSpent
            }
            if (myHp <= 0 || manaSpent >= minMana) {
                return Int.MAX_VALUE
            }

            // Boss/Player Actions
            if (!isMyTurn) {
                myHp -= (bossDamage - myArmor).coerceAtLeast(1)
            } else {
                val allowedSpells = mutableListOf<String>()
                for ((name, spell) in spells) {
                    if (!activeEffects.containsKey(name) && spell.cost <= myMana) {
                        allowedSpells.add(name)
                    }
                }
                // Lose when we can't afford any spells.
                if (allowedSpells.isEmpty()) {
                    myHp = 0
                } else {
                    val spellName = allowedSpells.random()
                    val spell = spells[spellName]!!
                    myMana -= spell.cost
                    manaSpent += spell.cost
                    when (spellName) {
                        "M", "D" -> {
                            bossHp -= spell.bossEffect
                            myHp += spell.meEffect
                        }
                        "S", "P", "R" -> {
                            activeEffects[spellName] = spell.duration
                        }
                    }
                }
            }
            isMyTurn = !isMyTurn

            // Win Condition
            if (bossHp <= 0) {
                return manaSpent
            }
            if (myHp <= 0 || manaSpent >= minMana) {
                return Int.MAX_VALUE
            }
        }
    }
}
data class Spell(val cost: Int, val duration: Int, val meEffect: Int, val bossEffect: Int)