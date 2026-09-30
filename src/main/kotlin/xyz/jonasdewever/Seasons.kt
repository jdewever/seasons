package xyz.jonasdewever

import net.fabricmc.api.ModInitializer
import net.minecraft.resources.Identifier
import org.slf4j.LoggerFactory

object Seasons : ModInitializer {
	const val MOD_ID: String = "seasons"

	private val LOGGER = LoggerFactory.getLogger(MOD_ID)

	override fun onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!")
	}

    @JvmStatic
    fun id(path: String): Identifier = Identifier.fromNamespaceAndPath(MOD_ID, path)
}
