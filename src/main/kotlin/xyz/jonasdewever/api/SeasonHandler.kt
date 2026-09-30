package xyz.jonasdewever.api

import net.minecraft.server.level.ServerLevel
import net.minecraft.util.Mth
import net.minecraft.util.datafix.DataFixTypes
import net.minecraft.world.level.Level
import net.minecraft.world.level.saveddata.SavedDataType

object SeasonHandler: ISeasonDataProvider {

    val lastTimes = HashMap<Level, Long>()
    val updateTicks = HashMap<Level, Int>()

    // Gets called from a server event
    fun onTick(level: Level) {
        if (level.isClientSide) return  // TODO This prolly won't happen?

        val serverLevel = level as ServerLevel

        val currentTime = serverLevel.overworldClockTime
        val lastTime = lastTimes.getOrDefault(level, currentTime)
        lastTimes[level] = currentTime

        var timeDiff = currentTime - lastTime
        if (timeDiff == 0L) return

        if (timeDiff < 0) {
            // TODO normalize completely?
            // it should never be THAT much outta sync right
            timeDiff += 24000L
        }

        val savedData = getSeasonData(serverLevel)
        savedData.yearTicks = Mth.positiveModulo(savedData.yearTicks + timeDiff.toInt(), STime.EPOCH.yearLength)

        var ticks = updateTicks.getOrDefault(level, 0)
        if (ticks >= 20) {
            //todo sendUpdate to client
            ticks %= 20
        }
        updateTicks[level] = ticks + 1
        savedData.setDirty()
    }

    fun getSeasonData(level: ServerLevel): SeasonData {
        val dataManager = level.chunkSource.dataStorage
        val isInitialized = dataManager.get(DATA_TYPE) != null
        val savedData = dataManager.computeIfAbsent(DATA_TYPE)

        // todo here's the place to randomize the starting (sub)season with isInitialized

        return savedData
    }

    val DATA_TYPE: SavedDataType<SeasonData> = SavedDataType(
        SeasonData.DATA_IDENTIFIER, ::SeasonData, SeasonData.CODEC, DataFixTypes.LEVEL
    )

    override fun getServerSeasonState(level: ServerLevel): ISeasonTime {
        val savedData = getSeasonData(level)
        return STime(savedData.yearTicks)
    }

    override fun getClientSeasonState(level: Level): ISeasonTime {
        TODO()
    }
}