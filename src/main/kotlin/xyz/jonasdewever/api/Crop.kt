package xyz.jonasdewever.api

import xyz.jonasdewever.config.Config
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

class Crop private constructor(val keyframes: List<Keyframe>) {
    fun getModifier(day: Int): Double {
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

    companion object {

        fun fromName(name: String): Crop {
            // todo check season and apply keyframes

            // default
            return Crop(springOnlyKeyframes)
        }

        // TODO config values
        const val PEAK_GROWTH: Double = 1.2
        const val PEAK_SIDESEASON: Double = .8
        const val DROPOFF_SEASON: Double = .4

        val springOnlyKeyframes = listOf(
            Keyframe.fromSeason(Season.SubSeason.EARLY_SPRING, PEAK_SIDESEASON),
            Keyframe.fromSeason(Season.SubSeason.MID_SPRING, PEAK_GROWTH),
            Keyframe.fromSeason(Season.SubSeason.LATE_SPRING, DROPOFF_SEASON),
            Keyframe.fromSeason(Season.SubSeason.EARLY_SUMMER, .2)
        )
        val summerOnlyKeyframes = listOf(
            Keyframe.fromSeason(Season.SubSeason.EARLY_SUMMER, PEAK_SIDESEASON),
            Keyframe.fromSeason(Season.SubSeason.MID_SUMMER, PEAK_GROWTH),
            Keyframe.fromSeason(Season.SubSeason.LATE_SUMMER, DROPOFF_SEASON),
            Keyframe.fromSeason(Season.SubSeason.EARLY_AUTUMN, .2)
        )
        val autumnOnlyKeyframes = listOf(
            Keyframe.fromSeason(Season.SubSeason.EARLY_AUTUMN, PEAK_SIDESEASON),
            Keyframe.fromSeason(Season.SubSeason.MID_AUTUMN, PEAK_GROWTH),
            Keyframe.fromSeason(Season.SubSeason.LATE_AUTUMN, DROPOFF_SEASON),
            Keyframe.fromSeason(Season.SubSeason.EARLY_WINTER, .2)
        )
    }
}