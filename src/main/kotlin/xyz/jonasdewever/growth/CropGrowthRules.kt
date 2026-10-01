package xyz.jonasdewever.growth

import net.minecraft.core.BlockPos
import net.minecraft.core.RegistryAccess
import net.minecraft.core.registries.Registries
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.CropBlock
import net.minecraft.world.level.block.state.BlockState
import xyz.jonasdewever.api.CropOffSeasonBehaviour
import xyz.jonasdewever.config.Config

object CropGrowthRules {

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

        if (Fertility.isInSeason(blName, level)) {
            return true
        }

        // Not in season
        when (Config.offSeasonBehaviour.get()) {
            CropOffSeasonBehaviour.GROW_SLOW -> {
                // adds a chance of not allowing the random tick
                return random.nextFloat() <= .5 // TODO
            }

            CropOffSeasonBehaviour.NO_GROW -> {
                // deny random ticks
                return false
            }

            CropOffSeasonBehaviour.DIE_INSTANTLY -> {
                // kill the plant, dropping the seed
                level.destroyBlock(pos, true)
                return false
            }

            CropOffSeasonBehaviour.DIE_ON_GROWTH -> {
                // Die when the plant would've grown; adds a random grace period
                TODO()
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