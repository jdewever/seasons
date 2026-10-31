package xyz.jonasdewever.growth

import net.minecraft.core.BlockPos
import net.minecraft.core.RegistryAccess
import net.minecraft.core.registries.Registries
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.CropBlock
import net.minecraft.world.level.block.state.BlockState
import xyz.jonasdewever.api.Crop
import xyz.jonasdewever.api.CropOffSeasonBehaviour
import xyz.jonasdewever.api.ServerClientHelper
import xyz.jonasdewever.config.Config

object CropGrowthRules {

    val useStrict = Config.strictSeasons.get()
    val ossWhenStrict = Config.allowOSSWhenStrict.get()
    val slowGrowthLimit = Config.slowGrowthLowerLimit.get()
    val ossBehaviour = Config.offSeasonBehaviour.get()

    fun onTagsUpdated(access: RegistryAccess, bool: Boolean) {
        Fertility.populate()
    }

    @JvmStatic
    fun shouldAllowRandomTick(
        block: CropBlock, state: BlockState, level: ServerLevel, pos: BlockPos, random: RandomSource
    ): Boolean {
        val bl = block as Block
        val blockReg = level.registryAccess().lookupOrThrow(Registries.BLOCK)
        val blName = blockReg.getKey(bl).toString()

        if (useStrict) {
            // When strict mode, cut off at season end instead of tapering
            if (!Fertility.isInSeason(blName, level)) {
                // When allowing OSS in strict mode, resolve, else block all growth
                return ossWhenStrict && resolveOSSBehaviour(random, level, pos, block)
            }
        }

        val time = ServerClientHelper.getSeasonState(level)
        val modifier = Crop.fromName(blName).getModifier(time.currentDayInCycleRound)

        // Reached minimum growth, OSS behaviour kicks in
        if (modifier <= slowGrowthLimit) {
            return resolveOSSBehaviour(random, level, pos, block)
        }

        return random.nextFloat() <= modifier
    }

    private fun resolveOSSBehaviour(random: RandomSource, level: ServerLevel, pos: BlockPos, block: CropBlock): Boolean {
        when (ossBehaviour) {
            CropOffSeasonBehaviour.GROW_SLOW -> {
                // adds a chance of not allowing the random tick
                return random.nextFloat() <= slowGrowthLimit
            }

            CropOffSeasonBehaviour.NO_GROW -> {
                // deny random ticks
                // almost the same as strict mode, except this allows for growth a BIT longer
                return false
            }

            CropOffSeasonBehaviour.DIE_INSTANTLY -> {
                // kill the plant, dropping the seed
                level.destroyBlock(pos, true)
                return false
            }

            CropOffSeasonBehaviour.DIE_ON_GROWTH -> {
                TODO("Die when the plant would've grown; adds a random grace period")
            }

            CropOffSeasonBehaviour.RETURN_TO_FIRST_STAGE -> {
                // return to 'just planted'
                val changedState = block.getStateForAge(0)
                level.setBlockAndUpdate(pos, changedState)
                return false
            }
        }
    }

}