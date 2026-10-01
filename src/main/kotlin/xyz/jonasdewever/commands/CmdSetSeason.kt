package xyz.jonasdewever.commands

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.network.chat.Component
import xyz.jonasdewever.api.STime
import xyz.jonasdewever.api.Season
import xyz.jonasdewever.api.SeasonHandler
import java.util.function.Supplier

object CmdSetSeason {
    fun register(): LiteralArgumentBuilder<CommandSourceStack> = Commands
        .literal("set")
        .then(
            Commands
                .argument("season_arg", SeasonArgumentType())
                .executes(::setSeason)
        )


    private fun setSeason(ctx: CommandContext<CommandSourceStack>): Int {
        val desiredSeason: Season.SubSeason = ctx.getArgument("season_arg", Season.SubSeason::class.java)

        val level = ctx.source.level
        val data = SeasonHandler.getSeasonData(level)
        data.yearTicks = STime.EPOCH.subSeasonLength * desiredSeason.ordinal
        data.setDirty()

        val newTime = STime(data.yearTicks)

        SeasonHandler.updateSeason(level)

        val msg: Supplier<Component> = Supplier { Component.literal("Set season to: ${newTime.currentSubSeason}") }
        ctx.source.sendSuccess(msg, true)

        return 1
    }
}