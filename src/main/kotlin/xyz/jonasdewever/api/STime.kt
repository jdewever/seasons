package xyz.jonasdewever.api

class STime(val ticks: Int) : ISeasonTime {
    override val dayLength: Int
        get() = 24000
    override val subSeasonLength: Int
        get() = dayLength * 8
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

    companion object {
        val EPOCH = STime(0)
    }
}