package xyz.jonasdewever.api

import xyz.jonasdewever.config.Config
import kotlin.math.roundToInt

class STime(val ticks: Int) : ISeasonTime {
    override val dayLength: Int
        get() = 24000

    override val subSeasonLength: Int
        get() = dayLength * Config.subSeasonDays.get()

    override val seasonLength: Int
        get() = subSeasonLength * 3
    
    override val yearLength: Int
        get() = Season.SubSeason.entries.size * subSeasonLength

    override val currentSeason: Season
        get() = currentSubSeason.season

    override val currentSubSeason: Season.SubSeason
        get() {
            val index = (ticks / subSeasonLength) % Season.SubSeason.entries.size
            return Season.SubSeason.entries[index]
        }

    override val currentDayInCycle: Double
        get() = ticks.toDouble().div(dayLength.toDouble())

    override val currentDayInCycleRound: Int
        get() = currentDayInCycle.roundToInt()

    override val currentYearTicks: Int
        get() = ticks

    companion object {
        val EPOCH = STime(0)
    }
}