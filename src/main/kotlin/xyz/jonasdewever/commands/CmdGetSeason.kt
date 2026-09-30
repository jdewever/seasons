package xyz.jonasdewever.commands

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.network.chat.Component
import xyz.jonasdewever.api.STime
import xyz.jonasdewever.api.SeasonHandler
import java.util.function.Supplier

object CmdGetSeason {
    fun register(): LiteralArgumentBuilder<CommandSourceStack?>? {
        return Commands.literal("get")
            .executes(::getSeason)
    }

    private fun getSeason(ctx: CommandContext<CommandSourceStack>): Int {
        val level = ctx.source.level
        val data = SeasonHandler.getSeasonData(level)
        val sTime = STime(data.yearTicks)

        val msg: Supplier<Component> = Supplier { Component.literal("Current season: ${sTime.currentSubSeason}") }
        ctx.source.sendSuccess(msg, true)

        return 1
    }
}