package xyz.jonasdewever.api

enum class Season {
    SPRING, SUMMER, AUTUMN, WINTER;

    enum class SubSeason(val season: Season) {
        EARLY_SPRING(SPRING),
        MID_SPRING(SPRING),
        LATE_SPRING(SPRING),
        EARLY_SUMMER(SUMMER),
        MID_SUMMER(SUMMER),
        LATE_SUMMER(SUMMER),
        EARLY_AUTUMN(AUTUMN),
        MID_AUTUMN(AUTUMN),
        LATE_AUTUMN(AUTUMN),
        EARLY_WINTER(WINTER),
        MID_WINTER(WINTER),
        LATE_WINTER(WINTER);
    }
}