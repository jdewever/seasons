package xyz.jonasdewever.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.jonasdewever.bees.BeeBehaviour;

import java.util.List;

@Mixin(BeehiveBlockEntity.class)
public abstract class BeehiveBlockEntityMixin {

    @Inject(method = "releaseOccupant", at = @At("HEAD"), cancellable = true)
    private static void onReleaseOccupant(Level level, BlockPos blockPos, BlockState state, BeehiveBlockEntity.Occupant beeData, @Nullable List<Entity> spawned, BeehiveBlockEntity.BeeReleaseStatus releaseStatus, @Nullable BlockPos savedFlowerPos, CallbackInfoReturnable<Boolean> cir) {

        if (level.isClientSide()) return;

        if (!BeeBehaviour.shouldGoOut((ServerLevel) level, blockPos, state, beeData, spawned, releaseStatus, savedFlowerPos)) {
            cir.setReturnValue(false);
        }
    }
}
