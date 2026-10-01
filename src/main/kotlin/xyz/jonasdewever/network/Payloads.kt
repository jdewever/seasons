package xyz.jonasdewever.network

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry
import net.fabricmc.fabric.api.networking.v1.PlayerLookup
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.level.Level
import xyz.jonasdewever.network.clientbound.SeasonSyncPacket

object Payloads {
    fun registerAll() {
        PayloadTypeRegistry.clientboundPlay().register(SeasonSyncPacket.TYPE, SeasonSyncPacket.CODEC)
    }

    fun sendToPlayers(level: Level, payload: CustomPacketPayload) {
        if (level.isClientSide) return

        for (player in PlayerLookup.level(level as ServerLevel)) {
            ServerPlayNetworking.send(player, payload)
        }
    }
}