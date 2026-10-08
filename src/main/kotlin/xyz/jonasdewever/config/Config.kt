package xyz.jonasdewever.config

import com.supermartijn642.configlib.api.ConfigBuilders
import xyz.jonasdewever.api.CropOffSeasonBehaviour
import java.util.function.Supplier

object Config {
    val startSeason: Supplier<Int>
    val subSeasonDays: Supplier<Int>
    val offSeasonBehaviour: Supplier<CropOffSeasonBehaviour>
    val beesStayIndoorInWinter: Supplier<Boolean>

    init {
        val builder = ConfigBuilders.newTomlConfig("season", null, false)

        builder.push("Seasons").categoryComment("Pertaining the base season settings")

        startSeason =
            builder.comment("Which season does the game start in on day 1? From 0 (early spring) to 11 (late winter).")
                .define("startSeason", 0, 0, 11)

        subSeasonDays =
            builder.comment("How many days should a subseason last? (There's three subseasons in a whole season.)")
                .define("subSeasonDays", 3, 1, Int.MAX_VALUE)

        builder.pop()

        builder.push("Crops").categoryComment("Pertaining crops")

        offSeasonBehaviour =
            builder.comment("Crop behaviour when in an off season.")
                .define("offSeasonBehaviour", CropOffSeasonBehaviour.GROW_SLOW)

        builder.push("Mobs").categoryComment("Pertaining mobs")

        beesStayIndoorInWinter =
            builder.comment("Whether bees stay in their hives in the winter season.")
                .define("beesStayIndoorInWinter", true)

        builder.pop()


        builder.build()
    }
}