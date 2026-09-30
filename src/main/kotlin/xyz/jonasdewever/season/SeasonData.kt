package xyz.jonasdewever.season

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.world.level.saveddata.SavedData
import xyz.jonasdewever.Seasons
import xyz.jonasdewever.api.STime
import xyz.jonasdewever.api.Season

class SeasonData(var yearTicks: Int) : SavedData() {
    constructor() : this(calcDefaultTicks())

    companion object {

        val CODEC: Codec<SeasonData> = RecordCodecBuilder.create { it ->
            it.group(
                Codec.INT.fieldOf("YearTicks").orElse(0).forGetter { it.yearTicks }
            ).apply(it, ::SeasonData)
        }

        val DATA_IDENTIFIER = Seasons.id("seasons")

        fun calcDefaultTicks(): Int {
            val STARTING_SEASON = Season.SPRING.ordinal // TODO get from config?

            return if (STARTING_SEASON > 0) {
                ((STARTING_SEASON - 1) * STime.EPOCH.subSeasonLength)
            } else 0
        }
    }
}