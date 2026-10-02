package xyz.jonasdewever.bees

import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.Entity
import net.minecraft.world.level.block.entity.BeehiveBlockEntity
import net.minecraft.world.level.block.state.BlockState
import xyz.jonasdewever.api.STime
import xyz.jonasdewever.api.Season
import xyz.jonasdewever.api.SeasonHandler
import xyz.jonasdewever.config.Config

object BeeBehaviour {

    @JvmStatic
    fun shouldGoOut(
        level: ServerLevel,
        blockPos: BlockPos,
        state: BlockState,
        beeData: BeehiveBlockEntity.Occupant,
        spawned: List<Entity>?,
        releaseStatus: BeehiveBlockEntity.BeeReleaseStatus,
        savedFlowerPos: BlockPos?
    ): Boolean {
        if (level.isClientSide) return true
        if (!Config.beesStayIndoorInWinter.get()) return true

        val data = SeasonHandler.getSeasonData(level)
        val time = STime(data.yearTicks)

        return time.currentSeason != Season.WINTER
    }
}