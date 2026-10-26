package xyz.jonasdewever.api

import com.mojang.serialization.Codec
import net.minecraft.util.StringRepresentable

enum class Season {
    SPRING, SUMMER, AUTUMN, WINTER;

    val subseasons: List<SubSeason>
        get() = SubSeason.entries.filter { it.season == this };

    val early: SubSeason
        get() = this.subseasons[0]
    val peak: SubSeason
        get() = this.subseasons[1]
    val late: SubSeason
        get() = this.subseasons[2]

    fun next(): Season = Season.entries.elementAt((this.ordinal + 1) % entries.size)
    fun prev(): Season =
        Season.entries.elementAt((this.ordinal - 1 + Season.entries.size) % Season.entries.size)

    fun isAdjacent(other: Season): Boolean {
        return this.next() == other || this.prev() == other
    }

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

        fun next(): SubSeason = SubSeason.entries.elementAt((this.ordinal + 1) % SubSeason.entries.size)
        fun prev(): SubSeason =
            SubSeason.entries.elementAt((this.ordinal - 1 + SubSeason.entries.size) % SubSeason.entries.size)

        companion object {
            var CODEC: Codec<SubSeason> = StringRepresentable.fromEnum(::values)
        }
    }
}