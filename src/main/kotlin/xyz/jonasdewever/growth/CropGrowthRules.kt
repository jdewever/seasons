package xyz.jonasdewever.growth

import net.minecraft.core.BlockPos
import net.minecraft.core.RegistryAccess
import net.minecraft.core.registries.Registries
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.CropBlock
import net.minecraft.world.level.block.state.BlockState

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

        return Fertility.isInSeason(blName, level)
    }

}