package xyz.jonasdewever.commands

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import net.minecraft.commands.CommandBuildContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands

object SCommands {
    fun onRegisterCommands(
        dispatcher: CommandDispatcher<CommandSourceStack>,
        context: CommandBuildContext,
        selection: Commands.CommandSelection
    ) {
        dispatcher.register(
            LiteralArgumentBuilder.literal<CommandSourceStack>("season")
                .requires(Commands.hasPermission(Commands.LEVEL_GAMEMASTERS))
                .then(CmdGetSeason.register())
        )
    }

    fun registerArguments() {

    }
}