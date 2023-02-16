package buri.aoc.y16.d10

import buri.aoc.common.BasePuzzle
import buri.aoc.common.Part
import buri.aoc.common.Part.ONE
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
        assertRun(56, 0, true)
    }

    @Test
    fun runPart2() {
        assertRun(7847, 0, true)
    }

    /**
     * Executes a part of the puzzle using the specified input file.
     */
    override fun run(part: Part, input: List<String>): Number {
        val bots = mutableMapOf<Int, Bot>()
        val outputs = mutableListOf<Int>()
        for (line in input.filter { it.startsWith("bot") }) {
            val tokens = line.split(" ")
            val isLowBot = (tokens[5] == "bot")
            val isHighBot = (tokens[10] == "bot")
            bots[tokens[1].toInt()] = Bot(mutableListOf(), isLowBot, tokens[6].toInt(), isHighBot, tokens[11].toInt())
        }
        for (line in input.filter { it.startsWith("value") }) {
            val tokens = line.split(" ")
            bots[tokens[5].toInt()]!!.chips.add(tokens[1].toInt())
        }

        while (true) {
            for ((id, bot) in bots) {
                if (bot.chips.size == 2) {
                    if (part == ONE && bot.chips.min() == 17 && bot.chips.max() == 61) {
                        return id
                    }

                    if (bot.isLowBot) {
                        bots[bot.lowTo]!!.chips.add(bot.chips.min())
                    } else if (bot.lowTo in 0..2) {
                        // Only save the 3 outputs we care about for part 2.
                        outputs.add(bot.chips.min())
                    }

                    if (bot.isHighBot) {
                        bots[bot.highTo]!!.chips.add(bot.chips.max())
                    } else if (bot.highTo in 0..2) {
                        outputs.add(bot.chips.max())
                    }

                    bot.chips.clear()
                }
                if (part == TWO && outputs.size == 3) {
                    return outputs.reduce { acc, i -> acc * i }
                }
            }
        }
    }
}

data class Bot(
    val chips: MutableList<Int>,
    val isLowBot: Boolean,
    val lowTo: Int,
    val isHighBot: Boolean,
    val highTo: Int
)