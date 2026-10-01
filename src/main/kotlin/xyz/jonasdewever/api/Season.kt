package xyz.jonasdewever.api

import com.mojang.serialization.Codec
import net.minecraft.util.StringRepresentable

enum class Season {
    SPRING, SUMMER, AUTUMN, WINTER;

    enum class SubSeason(val season: Season) : StringRepresentable {
        EARLY_SPRING(SPRING),
        MID_SPRING(SPRING),
        LATE_SPRING(SPRING),
        EARLY_SUMMER(SUMMER),
        MID_SUMMER(SUMMER),
        LATE_SUMMER(SUMMER),
        EARLY_AUTUMN(AUTUMN),
        MID_AUTUMN(AUTUMN),
        LATE_AUTUMN(AUTUMN),
        EARLY_WINTER(WINTER),
        MID_WINTER(WINTER),
        LATE_WINTER(WINTER);

        override fun getSerializedName(): String = this.name.lowercase()

        companion object {
            var CODEC: Codec<SubSeason> = StringRepresentable.fromEnum(::values)
        }
    }
}