package xyz.jonasdewever.config

import com.supermartijn642.configlib.api.ConfigBuilders
import xyz.jonasdewever.api.CropOffSeasonBehaviour
import java.util.function.Supplier

object Config {
    val startSeason: Supplier<Int>
    val subSeasonDays: Supplier<Int>
    val strictSeasons: Supplier<Boolean>
    val offSeasonBehaviour: Supplier<CropOffSeasonBehaviour>
    val slowGrowthLowerLimit: Supplier<Double>
    val allowOSSWhenStrict: Supplier<Boolean>
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
            builder.comment("Out Of Season (OSS) behaviour. What happens to the crop when it reaches minimum growth rate.")
                .define("offSeasonBehaviour", CropOffSeasonBehaviour.GROW_SLOW)

        slowGrowthLowerLimit =
            builder.comment("The minimum growth rate, used for the Out of Season behaviour. (percentage in decimal)")
                .define("slowGrowthLowerLimit", .2, 0.0, 1.0)

        strictSeasons =
            builder.comment("Strictly enforce season limits? If enabled, cuts off growth at the end of the peak season instantly. If disabled, the growth rate will gradually taper down (OOS behaviour kicks in only at minimum growth).")
                .define("strictSeasons", false)

        allowOSSWhenStrict =
            builder.comment("Allow following the defined OSS behaviour when enforcing strict seasonal limits? This still cuts off the gradual tapering after a peak season ends, but instead of blocking growth completely, follows OSS behaviour.")
                .define("allowOSSWhenStrict", true)

        builder.push("Mobs").categoryComment("Pertaining mobs")

        beesStayIndoorInWinter =
            builder.comment("Whether bees stay in their hives in the winter season.")
                .define("beesStayIndoorInWinter", true)

        builder.pop()


        builder.build()
    }
}