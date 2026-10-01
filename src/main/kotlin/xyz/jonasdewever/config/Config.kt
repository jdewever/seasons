package xyz.jonasdewever.config

import com.supermartijn642.configlib.api.ConfigBuilders
import xyz.jonasdewever.api.CropOffSeasonBehaviour
import java.util.function.Supplier

object Config {
    val startSeason: Supplier<Int>
    val subSeasonDays: Supplier<Int>
    val offSeasonBehaviour: Supplier<CropOffSeasonBehaviour>

    init {
        val builder = ConfigBuilders.newTomlConfig("season", null, false)

        startSeason =
            builder.comment("Which season does the game start in on day 1? From 0 (early spring) to 11 (late winter).")
                .define("startSeason", 0, 0, 11)
        subSeasonDays =
            builder.comment("How many days should a subseason last? (There's three subseasons in a whole season.)")
                .define("subSeasonDays", 3, 1, Int.MAX_VALUE)
        offSeasonBehaviour =
            builder.comment("Crop behaviour when in an off season. \n0 -> Grow slower \n1 -> Don't grow \n2 -> Die instantly \n3 -> Die on growth \n4 -> Return to first stage")
                .define("offSeasonBehaviour", CropOffSeasonBehaviour.GROW_SLOW)


        builder.build()
    }
}