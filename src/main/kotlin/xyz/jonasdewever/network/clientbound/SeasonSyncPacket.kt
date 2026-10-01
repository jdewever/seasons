package xyz.jonasdewever.network.clientbound

import net.minecraft.core.registries.Registries
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.network.protocol.common.custom.CustomPacketPayload
import net.minecraft.resources.ResourceKey
import net.minecraft.world.level.Level
import xyz.jonasdewever.Seasons

@JvmRecord
data class SeasonSyncPacket(val level: ResourceKey<Level>, val yearTicks: Int) : CustomPacketPayload {

    override fun type(): CustomPacketPayload.Type<out CustomPacketPayload> {

        return TYPE
    }

    companion object {
        val SEASON_SYNC_PAYLOAD_ID = Seasons.id("season_sync")
        val TYPE: CustomPacketPayload.Type<SeasonSyncPacket> = CustomPacketPayload.Type(SEASON_SYNC_PAYLOAD_ID)

        val CODEC: StreamCodec<RegistryFriendlyByteBuf, SeasonSyncPacket> = StreamCodec.composite(
            ResourceKey.streamCodec(Registries.DIMENSION),
            SeasonSyncPacket::level,
            ByteBufCodecs.INT,
            SeasonSyncPacket::yearTicks
        ) { level: ResourceKey<Level>, yearTicks: Int -> SeasonSyncPacket(level, yearTicks) }
    }
}