package day05

import kotlinx.coroutines.runBlocking
import println
import readInput

data class Plant(
        val seed: Long,
        val soil: Long = 0,
        val fertilizer: Long = 0,
        val water: Long = 0,
        val light: Long = 0,
        val temperature: Long = 0,
        val humidity: Long = 0,
        val location: Long = 0,
)

data class Category(
        val destination: Long,
        val source: Long,
        val length: Long
)

fun String.takeNumbers() = this.split(" ").filter { it != "" }

fun main(): Unit = runBlocking {
    fun part1(input: List<String>): Long {
        val seedToSoil: MutableList<Category> = mutableListOf()
        val soilToFertilizer: MutableList<Category> = mutableListOf()
        val fertilizerToWater: MutableList<Category> = mutableListOf()
        val waterToLight: MutableList<Category> = mutableListOf()
        val lightToTemperature: MutableList<Category> = mutableListOf()
        val temperatureToHumidity: MutableList<Category> = mutableListOf()
        val humidityToLocation: MutableList<Category> = mutableListOf()

        var currentCategory = 1

        var smallestLocation = Long.MAX_VALUE
        val plants = input
                .first()
                .substringAfter(":")
                .trim()
                .split(" ")
                .map { Plant(it.toLong()) }
                .toMutableList()

        input.drop(2).forEach { inputString ->
            when {
                inputString.isBlank() -> currentCategory += 1
                inputString.none { it.isDigit() } -> {}
                else -> {
                    when (currentCategory) {
                        1 -> {
                            val value = inputString.takeNumbers()
                            seedToSoil.add(Category(value.first().toLong(), value[1].toLong(), value.last().toLong()))
                        }

                        2 -> {
                            val value = inputString.takeNumbers()
                            soilToFertilizer.add(Category(value.first().toLong(), value[1].toLong(), value.last().toLong()))
                        }

                        3 -> {
                            val value = inputString.takeNumbers()
                            fertilizerToWater.add(Category(value.first().toLong(), value[1].toLong(), value.last().toLong()))
                        }

                        4 -> {
                            val value = inputString.takeNumbers()
                            waterToLight.add(Category(value.first().toLong(), value[1].toLong(), value.last().toLong()))
                        }

                        5 -> {
                            val value = inputString.takeNumbers()
                            lightToTemperature.add(Category(value.first().toLong(), value[1].toLong(), value.last().toLong()))
                        }

                        6 -> {
                            val value = inputString.takeNumbers()
                            temperatureToHumidity.add(Category(value.first().toLong(), value[1].toLong(), value.last().toLong()))
                        }

                        7 -> {
                            val value = inputString.takeNumbers()
                            humidityToLocation.add(Category(value.first().toLong(), value[1].toLong(), value.last().toLong()))
                        }

                        else -> {}
                    }
                }
            }
        }

        val seedSoil = mutableListOf<Pair<Long, Long>>()
        val soilFertilizer = mutableListOf<Pair<Long, Long>>()
        val fertilizerWater = mutableListOf<Pair<Long, Long>>()
        val waterLight = mutableListOf<Pair<Long, Long>>()
        val lightTemperature = mutableListOf<Pair<Long, Long>>()
        val temperatureHumidity = mutableListOf<Pair<Long, Long>>()
        val humidityLocation = mutableListOf<Pair<Long, Long>>()

        seedToSoil.forEach { category ->
            (0L until category.length).forEach {
                seedSoil.add((category.source + it) to category.destination + it)
            }
        }

        plants.forEachIndexed { index, plant ->
            plants[index] = plants[index].copy(soil = seedSoil.firstOrNull { it.first == plant.seed }?.second
                    ?: plant.seed)
        }

        soilToFertilizer.forEach { category ->
            (0L until category.length).forEach {
                soilFertilizer.add((category.source + it) to category.destination + it)
            }
        }

        plants.forEachIndexed { index, plant ->
            plants[index] = plants[index].copy(fertilizer = soilFertilizer.firstOrNull { it.first == plant.soil }?.second
                    ?: plant.soil)
        }

        fertilizerToWater.forEach { category ->
            (0L until category.length).forEach {
                fertilizerWater.add((category.source + it) to category.destination + it)
            }
        }

        plants.forEachIndexed { index, plant ->
            plants[index] = plants[index].copy(water = fertilizerWater.firstOrNull { it.first == plant.fertilizer }?.second
                    ?: plant.fertilizer)
        }

        waterToLight.forEach { category ->
            (0L until category.length).forEach {
                waterLight.add((category.source + it) to (category.destination + it))
            }
        }

        plants.forEachIndexed { index, plant ->
            plants[index] = plants[index].copy(light = waterLight.firstOrNull { it.first == plant.water }?.second
                    ?: plant.water)
        }

        lightToTemperature.forEach { category ->
            (0L until category.length).forEach {
                lightTemperature.add((category.source + it) to category.destination + it)
            }
        }

        plants.forEachIndexed { index, plant ->
            plants[index] = plants[index].copy(temperature = lightTemperature.firstOrNull { it.first == plant.light }?.second
                    ?: plant.light)
        }

        temperatureToHumidity.forEach { category ->
            (0L until category.length).forEach {
                temperatureHumidity.add((category.source + it) to category.destination + it)
            }
        }

        plants.forEachIndexed { index, plant ->
            plants[index] = plants[index].copy(humidity = temperatureHumidity.firstOrNull { it.first == plant.temperature }?.second
                    ?: plant.temperature)
        }

        humidityToLocation.forEach { category ->
            (0L until category.length).forEach {
                humidityLocation.add((category.source + it) to category.destination + it)
            }
        }

        plants.forEachIndexed { index, plant ->
            plants[index] = plants[index].copy(location = humidityLocation.firstOrNull { it.first == plant.humidity }?.second
                    ?: plant.humidity)
        }

        plants.forEach {
            if (smallestLocation > it.location) {
                smallestLocation = it.location
            }
        }

        return smallestLocation
    }

    fun part2(input: List<String>): Int {
        var total = 0
        input.forEach { inputString ->

        }

        return total
    }


    val testInput = readInput("day05/Day05_test")
    "Test part1 ${part1(testInput).toString().println()}"
//    "Test part2 ${part2(testInput).toString().println()}"

    val input = readInput("day05/Day05")
    "Final part1 ${part1(input).toString().println()}"
//    "Final part2 ${part2(input).toString().println()}"
}

