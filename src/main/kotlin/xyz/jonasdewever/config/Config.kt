package xyz.jonasdewever.config

import com.supermartijn642.configlib.api.ConfigBuilders
import java.util.function.Supplier

object Config {
    val startSeason: Supplier<Int>
    val subSeasonDays: Supplier<Int>

    init {
        val builder = ConfigBuilders.newTomlConfig("season", null, false)

        startSeason =
            builder.comment("Which season does the game start in on day 1? From 0 (early spring) to 11 (late winter).")
                .define("startSeason", 0, 0, 11)
        subSeasonDays =
            builder.comment("How many days should a subseason last? (There's three subseasons in a whole season.)")
                .define("subSeasonDays", 3, 1, Int.MAX_VALUE)

        builder.build()
    }
}