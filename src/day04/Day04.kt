package day04

import println
import readInput

data class ScratchCard(
        val index: Int,
        val count: Int = 1,
)

fun main() {
    fun part1(input: List<String>): Int {
        var total = 0
        input.forEach { inputString ->
            val shortString = inputString.substringAfter(":")
            val winListNumbers = shortString.substringBefore("|").split(" ").filter { it != "" }
            val myListNumber = shortString.substringAfter("|").split(" ").filter { it != "" }
            val myWinListNumbers = myListNumber.filter { winListNumbers.contains(it) }
            total += when {
                myWinListNumbers.isEmpty() -> 0
                else -> {
                    var newValue = 1
                    repeat(myWinListNumbers.size - 1) {
                        newValue *= 2
                    }
                    newValue
                }
            }
        }
        return total
    }

    fun part2(input: List<String>): Int {
        var total = 0

        // create all parts and make it mutable
        val parts = MutableList(input.size) { index ->
            ScratchCard(index = index)
        }

        // Iterate over all string and update parts
        input.forEachIndexed { index, inputString ->
            // find numbers of winning values
            val shortString = inputString.substringAfter(":")
            val winListNumbers = shortString.substringBefore("|").split(" ").filter { it != "" }
            val myListNumber = shortString.substringAfter("|").split(" ").filter { it != "" }
            val myWinListNumbers = myListNumber.filter { winListNumbers.contains(it) }

            // update parts
            repeat(parts.getOrNull(index)?.count ?: 0) {
                repeat(myWinListNumbers.size + 1) {
                    if (it > 0) {
                        val scratchCard = parts.getOrNull(index + it)
                        if (scratchCard != null) {
                            parts[index + it] = scratchCard.copy(count = scratchCard.count + 1)
                        }
                    }
                }
            }
        }

        // count sum all instances
        parts.forEach {
            total += it.count
        }

        return total
    }


    val testInput = readInput("day04/Day04_test")
    "Test part1 ${part1(testInput).toString().println()}"
    "Test part2 ${part2(testInput).toString().println()}"

    val input = readInput("day04/Day04")
    "Final part1 ${part1(input).toString().println()}"
    "Final part2 ${part2(input).toString().println()}"
}