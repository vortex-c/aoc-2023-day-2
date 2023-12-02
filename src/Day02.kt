fun main() {

    val singleGameSetRegexString = """(\d+)\s+([^,\d]*)"""
    val gameSetRegex = Regex(singleGameSetRegexString)

    fun getRgbCount(setString: String, rgb: Rgb) {
        val gameSets = gameSetRegex.findAll(setString)
        gameSets.forEach {
            val countAndColor = it.value.split(" ")
            val count = countAndColor.first().toInt()
            val color = countAndColor.last()
            when (color) {
                "red" -> {
                    rgb.red = count
                }

                "green" -> {
                    rgb.green = count
                }

                "blue" -> {
                    rgb.blue = count
                }

                else -> {}
            }
        }
    }

    fun getRgbCountForAllSets(allSets: String): Rgb {
        val rgb = Rgb()
        val sets = allSets.split(";")
        sets.forEach {
            getRgbCount(it.trim(), rgb)
        }
        return rgb
    }

    fun getListOfRgb(information: List<String>): List<Rgb> {
        val rgb = mutableListOf<Rgb>()
        for (game in information) {
            val sets = game.split(":").last().trim()
            rgb.add(getRgbCountForAllSets(sets))
        }
        return rgb
    }

    fun part1(
        information: List<String>,
        setToCheck: String
    ): Int {
        var sum = 0
        val setToCheckRGB = getRgbCountForAllSets(setToCheck)
        val gameRGBCount = getListOfRgb(information)
        println(setToCheckRGB)
        gameRGBCount.forEachIndexed { index, rgb ->
            if (rgb.isThisSetPossible(setToCheckRGB)) {
                sum += index + 1
            }
        }
        return sum
    }

    fun part2(
        information: List<String>,
    ): Int {
        var powerSum = 0
        val gameRGBCount = getListOfRgb(information)
        gameRGBCount.forEach { rgb ->
            powerSum += rgb.power()
        }
        return powerSum
    }

    val readInput = readInput("Day02_test")
    val setToCheck = "12 red, 13 green, 14 blue"

    val ans1 = part1(readInput, setToCheck)
    println(ans1)
    val ans2 = part2(readInput)
    println(ans2)
}

data class Rgb(
    private var rCount: Int = 0,
    private var gCount: Int = 0,
    private var bCount: Int = 0
) {
    var red: Int
        get() = ::rCount.get()
        set(value) {
            if (value > ::rCount.get()) {
                ::rCount.set(value)
            }
        }
    var green: Int
        get() = ::gCount.get()
        set(value) {
            if (value > ::gCount.get()) {
                ::gCount.set(value)
            }
        }
    var blue: Int
        get() = ::bCount.get()
        set(value) {
            if (value > ::bCount.get()) {
                ::bCount.set(value)
            }
        }

    fun isThisSetPossible(rgb: Rgb): Boolean {
        return blue <= rgb.blue && red <= rgb.red && green <= rgb.green
    }

    fun power():Int = rCount * bCount * gCount

}
