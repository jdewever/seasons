package xyz.jonasdewever.api

import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.Level

object ServerClientHelper {
    lateinit var dataProvider: ISeasonDataProvider

    fun getSeasonState(level: Level): ISeasonTime {

        val data: ISeasonTime = if (!level.isClientSide) {
            dataProvider.getServerSeasonState(level as ServerLevel)
        } else {
            dataProvider.getClientSeasonState(level)
        }

        return data
    }
}

interface ISeasonDataProvider {
    fun getServerSeasonState(level: ServerLevel): ISeasonTime
    fun getClientSeasonState(level: Level): ISeasonTime
}