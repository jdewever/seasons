package xyz.jonasdewever.client.dataGen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.references.BlockItemIds;
import org.jspecify.annotations.NonNull;
import xyz.jonasdewever.common.TagManager;

import java.util.concurrent.CompletableFuture;

public class SeasonsBlockTagProvider extends FabricTagsProvider.BlockTagsProvider {

    public SeasonsBlockTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, registryLookupFuture);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider registries) {
        getOrCreateRawBuilder(TagManager.Blocks.INSTANCE.getSPRING_CROPS())
                .addElement(BlockItemIds.POTATO_CROP.block().identifier())
                .addElement(BlockItemIds.CARROT_CROP.block().identifier());

        getOrCreateRawBuilder(TagManager.Blocks.INSTANCE.getSUMMER_CROPS())
                .addElement(BlockItemIds.WHEAT_CROP.block().identifier())
                .addElement(BlockItemIds.MELON_CROP.block().identifier());

        getOrCreateRawBuilder(TagManager.Blocks.INSTANCE.getAUTUMN_CROPS())
                .addElement(BlockItemIds.WHEAT_CROP.block().identifier())
                .addElement(BlockItemIds.BEETROOT_CROP.block().identifier())
                .addElement(BlockItemIds.CARROT_CROP.block().identifier())
                .addElement(BlockItemIds.PUMPKIN_CROP.block().identifier());

        /*getOrCreateRawBuilder(Tags.Blocks.INSTANCE.getWINTER_CROPS())
                .addElement(BlockItemIds.BEETROOT_CROP.block().identifier())
                .addElement(BlockItemIds.CARROT_CROP.block().identifier())
                .addElement(BlockItemIds.POTATO_CROP.block().identifier())
                .addElement(BlockItemIds.POTATO_CROP.block().identifier());*/
    }
}
