package xyz.jonasdewever.util

object SMath {
    // Cubic Hermite
    fun smoothstep(from: Double, to: Double, t: Double): Double {
        val clampedT = t.coerceIn(0.0, 1.0)
        val smoothT = clampedT * clampedT * (3 - 2 * clampedT)
        return from + (to - from) * smoothT
    }
}