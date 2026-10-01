package xyz.jonasdewever

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.fabricmc.fabric.api.event.lifecycle.v1.CommonLifecycleEvents
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.resources.Identifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import xyz.jonasdewever.api.SeasonHandler
import xyz.jonasdewever.commands.SCommands
import xyz.jonasdewever.growth.CropGrowthRules
import xyz.jonasdewever.init.API
import xyz.jonasdewever.network.Payloads

object Seasons : ModInitializer {
    const val MOD_ID: String = "seasons"
    const val MOD_NAME: String = "Seasons"
    val LOGGER: Logger = LoggerFactory.getLogger(MOD_NAME)

    override fun onInitialize() {
        LOGGER.info("Hello Fabric world!")

        SCommands.registerArguments()
        registerEvents()
        Payloads.registerAll()
        API.init()
    }

    private fun registerEvents() {
        ServerTickEvents.END_LEVEL_TICK.register(SeasonHandler::onTick)
        CommonLifecycleEvents.TAGS_LOADED.register(CropGrowthRules::onTagsUpdated)
        CommandRegistrationCallback.EVENT.register(SCommands::onRegisterCommands)
    }

    @JvmStatic
    fun id(path: String): Identifier = Identifier.fromNamespaceAndPath(MOD_ID, path)
}
