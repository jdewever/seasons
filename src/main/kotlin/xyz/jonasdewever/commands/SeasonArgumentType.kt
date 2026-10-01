package xyz.jonasdewever.commands

import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.exceptions.CommandSyntaxException
import xyz.jonasdewever.api.Season

class SeasonArgumentType : ArgumentType<Season.SubSeason> {
    override fun parse(reader: StringReader): Season.SubSeason {
        try {
            val str = reader.readString()
            str.trim()

            val season = Season.SubSeason.valueOf(str)
            return season
        } catch (e: Exception) {
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.dispatcherParseException().create("Invalid season")
        }
    }
}