package xyz.jonasdewever.client.network

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
import xyz.jonasdewever.client.api.SeasonHandlerClient
import xyz.jonasdewever.network.clientbound.SeasonSyncPacket

object ClientPayloads {

    fun registerAll() {
        ClientPlayNetworking.registerGlobalReceiver(SeasonSyncPacket.TYPE, ::handleSeasonSyncPacket)
    }

    private fun handleSeasonSyncPacket(packet: SeasonSyncPacket, context: ClientPlayNetworking.Context) {
        val player = context.player()
        val localDim = player.level().dimension()

        if (localDim == packet.level) {
            SeasonHandlerClient.clientSeasonCycleTicks[localDim] = packet.yearTicks
        }
    }
}