package xyz.jonasdewever.api

interface ISeasonTime {
    val dayLength: Int
    val subSeasonLength: Int
    val seasonLength: Int
    val yearLength: Int

    val currentSeason: Season
    val currentSubSeason: Season.SubSeason
}