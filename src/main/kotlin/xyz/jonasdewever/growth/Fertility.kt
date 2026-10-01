package xyz.jonasdewever.growth

import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.server.level.ServerLevel
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import xyz.jonasdewever.api.Season
import xyz.jonasdewever.api.ServerClientHelper
import xyz.jonasdewever.common.TagManager

object Fertility {

    private val springPlants: MutableSet<String> = HashSet()
    private val summerPlants: MutableSet<String> = HashSet()
    private val autumnPlants: MutableSet<String> = HashSet()
    private val winterPlants: MutableSet<String> = HashSet()
    private val allPlants: MutableSet<String> = HashSet()

    private val seedSeasons: MutableMap<String, Int> = HashMap()

    fun populate() {
        springPlants.clear()
        summerPlants.clear()
        autumnPlants.clear()
        winterPlants.clear()
        allPlants.clear()
        seedSeasons.clear()

        populateSeasonCrops(TagManager.Blocks.SPRING_CROPS, springPlants, 1)
        populateSeasonCrops(TagManager.Blocks.SUMMER_CROPS, summerPlants, 2)
        populateSeasonCrops(TagManager.Blocks.AUTUMN_CROPS, autumnPlants, 4)
//        populateSeasonCrops(Tags.Blocks.WINTER_CROPS, winterPlants, 8)


        populateSeasonSeeds(TagManager.Items.SPRING_CROPS, springPlants, 1)
        populateSeasonSeeds(TagManager.Items.SUMMER_CROPS, summerPlants, 2)
        populateSeasonSeeds(TagManager.Items.AUTUMN_CROPS, autumnPlants, 4)
//        populateSeasonSeeds(Tags.Items.WINTER_CROPS, winterPlants, 8)
    }

    fun isAffectedPlant(blockName: String): Boolean {
        return allPlants.contains(blockName)
    }

    fun isInSeason(blockName: String, level: ServerLevel): Boolean {
        // todo biome dependant season, underground, infertile biomes, tropical biomes, year round

        return when (ServerClientHelper.getSeasonState(level).currentSeason) {
            Season.SPRING -> {
                springPlants.contains(blockName)
            }

            Season.SUMMER -> {
                summerPlants.contains(blockName)
            }

            Season.AUTUMN -> {
                autumnPlants.contains(blockName)
            }

            Season.WINTER -> {
                winterPlants.contains(blockName)
            }
        }
    }

    fun seasonsForCrop(blockName: String): List<Season> {
        if (!isAffectedPlant(blockName)) return mutableListOf()

        val seasonList = mutableListOf<Season>()

        if (springPlants.contains(blockName)) seasonList.add(Season.SPRING)
        if (summerPlants.contains(blockName)) seasonList.add(Season.SUMMER)
        if (autumnPlants.contains(blockName)) seasonList.add(Season.AUTUMN)
        if (winterPlants.contains(blockName)) seasonList.add(Season.WINTER)

        return seasonList
    }

    private fun populateSeasonCrops(tag: TagKey<Block>, cropSet: MutableSet<String>, mask: Int) {
        BuiltInRegistries.BLOCK.get(tag).ifPresent { blocks ->
            blocks.forEach { block ->
                val blockKey = block.unwrapKey()
                if (blockKey.isEmpty) return@forEach

                val plantName = blockKey.get().identifier().toString()
                cropSet.add(plantName)

                if (mask != 0) {
                    allPlants.add(plantName)
                } else {
                    return@forEach
                }

                if (seedSeasons.containsKey(plantName)) {
                    val seasons = seedSeasons[plantName]!!
                    seedSeasons[plantName] = seasons or mask
                } else {
                    seedSeasons[plantName] = mask
                }
            }
        }
    }

    private fun populateSeasonSeeds(tag: TagKey<Item>, cropSet: MutableSet<String>, mask: Int) {
        BuiltInRegistries.ITEM.get(tag).ifPresent { items ->
            items.forEach { item ->
                val itemKey = item.unwrapKey()
                if (itemKey.isEmpty) return@forEach

                val plantName = itemKey.get().identifier().toString()
                cropSet.add(plantName)

                if (mask != 0) {
                    allPlants.add(plantName)
                } else {
                    return@forEach
                }

                if (seedSeasons.containsKey(plantName)) {
                    val seasons = seedSeasons[plantName]!!
                    seedSeasons[plantName] = seasons or mask
                } else {
                    seedSeasons[plantName] = mask
                }
            }
        }
    }

}