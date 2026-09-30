package xyz.jonasdewever.common

import net.minecraft.core.registries.Registries
import net.minecraft.resources.Identifier
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import xyz.jonasdewever.Seasons

object TagManager {

    object Blocks {

        val SPRING_CROPS: TagKey<Block> = create(Seasons.id("spring_crops"))
        val SUMMER_CROPS: TagKey<Block> = create(Seasons.id("summer_crops"))
        val AUTUMN_CROPS: TagKey<Block> = create(Seasons.id("autumn_crops"))
        val WINTER_CROPS: TagKey<Block> = create(Seasons.id("winter_crops"))
        fun create(name: Identifier) = TagKey.create(Registries.BLOCK, name)
    }

    object Items {
        val SPRING_CROPS: TagKey<Item> = create(Seasons.id("spring_crops"))
        val SUMMER_CROPS: TagKey<Item> = create(Seasons.id("summer_crops"))
        val AUTUMN_CROPS: TagKey<Item> = create(Seasons.id("autumn_crops"))
        val WINTER_CROPS: TagKey<Item> = create(Seasons.id("winter_crops"))
        fun create(name: Identifier) = TagKey.create(Registries.ITEM, name)
    }
}