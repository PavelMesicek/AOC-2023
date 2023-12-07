package day06

import println
import readInput

fun String.findNumbersInText(): List<Long> =
        "\\d+".toRegex().findAll(this).map { it.value.toLong() }.toList()


data class Race(
        val time: Long,
        val distance: Long,
        val winPossibilities: Long,
)

fun main() {
    fun part1(input: List<String>): Long {
        var total = 1L
        val time = input.first().findNumbersInText()
        val distance = input.last().findNumbersInText()
        val races = MutableList(time.size) { index ->
            Race(time[index], distance[index], 0)
        }

        races.forEachIndexed { index, race ->
            var hold = 1
            while ((race.time - hold) > 0) {
                if (race.distance < (race.time - hold) * hold) {
                    races[index] = races[index].copy(winPossibilities = races[index].winPossibilities.inc())
                }
                hold++
            }
        }

        races.forEach { race ->
            total *= race.winPossibilities
        }
        return total
    }

    fun part2(input: List<String>): Long {
        val time = input.first().filter { it.isDigit() }.toLong()
        val distance = input.last().filter { it.isDigit() }.toLong()
        var race = Race(time, distance, 0)

        var hold = 1
        while ((race.time - hold) > 0) {
            if (race.distance < (race.time - hold) * hold) {
                race = race.copy(winPossibilities = race.winPossibilities.inc())
            }
            hold++
        }

        return race.winPossibilities
    }


    val testInput = readInput("day06/Day06_test")
    "Test part1 ${part1(testInput).toString().println()}"
    "Test part2 ${part2(testInput).toString().println()}"

    val input = readInput("day06/Day06")
    "Final part1 ${part1(input).toString().println()}"
    "Final part2 ${part2(input).toString().println()}"
}
