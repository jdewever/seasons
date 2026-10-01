package xyz.jonasdewever.client

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.TooltipFlag
import xyz.jonasdewever.client.api.SeasonHandlerClient
import xyz.jonasdewever.client.network.ClientPayloads
import xyz.jonasdewever.growth.Fertility

object SeasonsClient : ClientModInitializer {
    override fun onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        ClientPayloads.registerAll()
        registerClientEvents()
    }

    private fun registerClientEvents() {
        ClientTickEvents.END_LEVEL_TICK.register(SeasonHandlerClient::onClientTick)

        ItemTooltipCallback.EVENT.register(::onItemTooltip)
    }

    private fun onItemTooltip(
        stack: ItemStack,
        context: Item.TooltipContext,
        type: TooltipFlag,
        lines: MutableList<Component>
    ) {
        val player: Player = Minecraft.getInstance().player as Player
        val name = stack.item.toString()

        if (Fertility.isAffectedPlant(name)) {
            val current = SeasonHandlerClient.getClientSeasonState(player.level()).currentSeason
            val peakSeasons = Fertility.seasonsForCrop(name)
            if (peakSeasons.contains(current)) {
                lines.add(Component.literal("In PEAK season"))
            }
            lines.add(Component.literal("Peak season(s): ${peakSeasons.joinToString(", ") { it.name }}"))
        }
    }
}
