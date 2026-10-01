package xyz.jonasdewever.client

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import xyz.jonasdewever.client.api.SeasonHandlerClient
import xyz.jonasdewever.client.network.ClientPayloads

object SeasonsClient : ClientModInitializer {
    override fun onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        ClientPayloads.registerAll()
        registerClientEvents()
    }

    private fun registerClientEvents() {
        ClientTickEvents.END_LEVEL_TICK.register(SeasonHandlerClient::onClientTick)
    }
}