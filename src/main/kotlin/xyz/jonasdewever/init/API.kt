package xyz.jonasdewever.init

import xyz.jonasdewever.api.ServerClientHelper
import xyz.jonasdewever.api.SeasonHandler

object API {
    val SEASON_HANDLER = SeasonHandler

    fun init() {
        ServerClientHelper.dataProvider = SEASON_HANDLER
    }
}