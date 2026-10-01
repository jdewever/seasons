package xyz.jonasdewever.client.api

import net.minecraft.client.Minecraft
import net.minecraft.resources.ResourceKey
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import xyz.jonasdewever.api.ISeasonTime
import xyz.jonasdewever.api.STime
import xyz.jonasdewever.api.Season

object SeasonHandlerClient {
    var lastSeason: Season.SubSeason? = null

    val clientSeasonCycleTicks: HashMap<ResourceKey<Level>, Int> = HashMap()

    fun onClientTick(level: Level) {
        val player: Player = Minecraft.getInstance().player as? Player ?: return
        val dim = player.level().dimension()

        // todo check dimension whitelist?

        clientSeasonCycleTicks.compute(dim) { k, v ->
            if (v == null) 0 else (v + 1) % STime.EPOCH.yearLength
        }

        val time = STime(clientSeasonCycleTicks[dim]!!)
        if (time.currentSubSeason != lastSeason) {
            // change season things i guess
            // if we have diff colouring etc
            lastSeason = time.currentSubSeason
        }
    }

    fun getClientSeasonState(level: Level): ISeasonTime {
        // TODO sync client and server
        val time = clientSeasonCycleTicks.getOrDefault(level.dimension(), 0)
        return STime(time)
    }
}