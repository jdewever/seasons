package xyz.jonasdewever.client.dataGen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.references.BlockItemIds;
import org.jspecify.annotations.NonNull;
import xyz.jonasdewever.common.TagManager;

import java.util.concurrent.CompletableFuture;

public class SeasonsItemTagProvider extends FabricTagsProvider.ItemTagsProvider {

    public SeasonsItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, registryLookupFuture);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider registries) {
        getOrCreateRawBuilder(TagManager.Items.INSTANCE.getSPRING_CROPS())
                .addElement(BlockItemIds.POTATO_CROP.item().identifier())
                .addElement(BlockItemIds.CARROT_CROP.item().identifier());

        getOrCreateRawBuilder(TagManager.Items.INSTANCE.getSUMMER_CROPS())
                .addElement(BlockItemIds.WHEAT_CROP.item().identifier())
                .addElement(BlockItemIds.MELON_CROP.item().identifier());

        getOrCreateRawBuilder(TagManager.Items.INSTANCE.getAUTUMN_CROPS())
                .addElement(BlockItemIds.WHEAT_CROP.item().identifier())
                .addElement(BlockItemIds.BEETROOT_CROP.item().identifier())
                .addElement(BlockItemIds.CARROT_CROP.item().identifier())
                .addElement(BlockItemIds.PUMPKIN_CROP.item().identifier());

        /*getOrCreateRawBuilder(Tags.Items.INSTANCE.getWINTER_CROPS())
                .addElement(BlockItemIds.BEETROOT_CROP.item().identifier())
                .addElement(BlockItemIds.CARROT_CROP.item().identifier())
                .addElement(BlockItemIds.POTATO_CROP.item().identifier())
                .addElement(BlockItemIds.POTATO_CROP.item().identifier());*/
    }
}
