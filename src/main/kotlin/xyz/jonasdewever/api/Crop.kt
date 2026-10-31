package xyz.jonasdewever.api

import xyz.jonasdewever.config.Config
import xyz.jonasdewever.growth.Fertility
import xyz.jonasdewever.util.SMath
import kotlin.math.floor

data class Keyframe(val day: Double, val value: Double) {
    companion object {
        fun fromSeason(season: Season.SubSeason, value: Double): Keyframe {
            val sStart = Config.subSeasonDays.get() * season.ordinal
            val sMid = floor(sStart + (Config.subSeasonDays.get() / 2.0))
            return Keyframe(sMid, value)
        }
    }
}

class Crop private constructor(val name: String, val keyframes: List<Keyframe>) {

    val fertileSeasons: List<Season> = Fertility.seasonsForCrop(name)
    val useStrict = Config.strictSeasons.get()
    fun getModifier(day: Int): Double {

        if (useStrict) {
            val season = STime.fromDay(day).currentSeason

            if (fertileSeasons.contains(season)) {
                // off season in strict mode
                return 0.0
            }
        }

        val yearLength = STime.EPOCH.yearLength
        val x = day % yearLength

        val sorted = keyframes.sortedBy { it.day }
        val last = sorted.last()
        val first = sorted.first()

        // cycle end/start
        if (x >= last.day || x < first.day) {
            val span = (yearLength - last.day) + first.day
            val progress = if (x >= last.day) (x - last.day) / span else (x + yearLength - last.day) / span

            return SMath.smoothstep(last.value, first.value, progress)
        }

        for (i in 0 until sorted.size - 1) {
            val k1 = sorted[i]
            val k2 = sorted[i + 1]

            if (x >= k1.day && x < k2.day) {
                val progress = (x - k1.day) / (k2.day - k1.day)
                return SMath.smoothstep(k1.value, k2.value, progress)
            }
        }

        return 1.0
    }

    // TODO Allow for parsing from configs
    companion object {

        // TODO play around with values for early and late
        const val PEAK_GROWTH: Double = 1.0
        const val EARLY_GROWTH: Double = .6
        const val LATE_GROWTH: Double = .8
        val slowGrowthRate = Config.slowGrowthLowerLimit.get()

        fun fromName(name: String): Crop {
            val seasons = Fertility.seasonsForCrop(name)

            if (seasons.isEmpty()) return Crop(name, generateKeyFrames(Season.SPRING))

            return Crop(name, generateKeyFrames(seasons))
        }

        fun generateKeyFrames(peakSeasons: List<Season>): List<Keyframe> {
            val list = mutableListOf<Keyframe>()

            val sorted = peakSeasons.sortedBy { it.ordinal }

            // TODO if seasons are adjacent, forego the late and early in the middles
            for (season in sorted) {
                list.addAll(peakKeyframes(season))
            }

            Season.entries.filter { !peakSeasons.contains(it) }.forEach { s ->
                // add the slow growth rates to other seasons
                list.add(Keyframe.fromSeason(s.early, slowGrowthRate))
                list.add(Keyframe.fromSeason(s.late, slowGrowthRate))
            }


            return list.sortedBy { it.day }
        }

        fun generateKeyFrames(peakSeason: Season): List<Keyframe> = generateKeyFrames(listOf(peakSeason))

        fun peakKeyframes(season: Season): List<Keyframe> {
            val list = mutableListOf<Keyframe>()
            list.add(Keyframe.fromSeason(season.early, EARLY_GROWTH))
            list.add(Keyframe.fromSeason(season.peak, PEAK_GROWTH))
            list.add(Keyframe.fromSeason(season.late, LATE_GROWTH))

            return list
        }
    }
}