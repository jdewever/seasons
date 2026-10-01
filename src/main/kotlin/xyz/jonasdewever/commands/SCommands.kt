package xyz.jonasdewever.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry
import net.minecraft.commands.CommandBuildContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.synchronization.SingletonArgumentInfo
import xyz.jonasdewever.Seasons

object SCommands {
    fun onRegisterCommands(
        dispatcher: CommandDispatcher<CommandSourceStack>,
        context: CommandBuildContext,
        selection: Commands.CommandSelection
    ) {
        dispatcher.register(
            LiteralArgumentBuilder.literal<CommandSourceStack>("season")
                .requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
                .then(CmdSetSeason.register())
                .then(CmdGetSeason.register())
        )
    }

    fun registerArguments() {
        ArgumentTypeRegistry.registerArgumentType(
            Seasons.id("season_arg"),
            SeasonArgumentType::class.java,
            SingletonArgumentInfo.contextFree(::SeasonArgumentType)
        )
    }
}