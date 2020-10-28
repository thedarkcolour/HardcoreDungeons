package thedarkcolour.hardcoredungeons.data

import net.minecraft.data.BlockTagsProvider
import net.minecraft.data.DataGenerator
import net.minecraft.data.ItemTagsProvider
import net.minecraft.tags.ITag
import net.minecraft.tags.ItemTags
import net.minecraft.util.ResourceLocation
import net.minecraftforge.common.Tags
import net.minecraftforge.common.data.ExistingFileHelper
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.registry.HItems
import thedarkcolour.hardcoredungeons.tags.HItemTags
import java.nio.file.Path

class ItemTagGenerator(gen: DataGenerator, blockTags: BlockTagsProvider, helper: ExistingFileHelper) : ItemTagsProvider(gen, blockTags, HardcoreDungeons.ID, helper) {
    /**
     * Register tags for each block.
     */
    override fun registerTags() {
        // hcd tags
        getOrCreateBuilder(HItemTags.LUMLIGHT_LOGS).add(HItems.LUMLIGHT_LOG, HItems.LUMLIGHT_WOOD, HItems.STRIPPED_LUMLIGHT_LOG, HItems.STRIPPED_LUMLIGHT_WOOD)
        //getBuilder(HBlockTags.GLASS_RAINBOW).add(HBlocks.RAINBOW_GLASS)
        //getBuilder(HBlockTags.GLASS_PANES_RAINBOW).add(HBlocks.RAINBOW_GLASS_PANE)
        //getBuilder(HBlockTags.FENCES_BRICK).add(HBlocks.CASTLETON_BRICK_FENCE, HBlocks.CHARGED_CASTLETON_BRICK_FENCE, HBlocks.RAINBOW_BRICK_FENCE, HBlocks.RAINBOW_FACTORY_BRICK_FENCE)

        // vanilla tags
        getOrCreateBuilder(ItemTags.PLANKS).add(HItems.LUMLIGHT_PLANKS)
        getOrCreateBuilder(ItemTags.WOODEN_DOORS).add(HItems.LUMLIGHT_DOOR)
        getOrCreateBuilder(ItemTags.WOODEN_STAIRS).add(HItems.AURI_STAIRS, HItems.LUMLIGHT_STAIRS)
        getOrCreateBuilder(ItemTags.WOODEN_SLABS).add(HItems.AURI_SLAB, HItems.LUMLIGHT_SLAB)
        getOrCreateBuilder(ItemTags.WOODEN_FENCES).add(HItems.LUMLIGHT_FENCE)
        getOrCreateBuilder(ItemTags.WOODEN_PRESSURE_PLATES).add(HItems.LUMLIGHT_PRESSURE_PLATE)
        getOrCreateBuilder(ItemTags.SAPLINGS).add(HItems.LUMLIGHT_SAPLING)
        getOrCreateBuilder(ItemTags.LOGS).addTag(HItemTags.LUMLIGHT_LOGS)

        // forge tags
        getOrCreateBuilder(Tags.Items.FENCES).add(HItems.LUMLIGHT_FENCE, HItems.CHARGED_CASTLETON_BRICK_FENCE)
        getOrCreateBuilder(Tags.Items.FENCE_GATES_WOODEN).add(HItems.LUMLIGHT_FENCE_GATE)
        //getOrCreateBuilder(Tags.Items.GLASS).add(HItemTags.GLASS_RAINBOW)
        //getOrCreateBuilder(Tags.Items.GLASS_PANES).add(HItemTags.GLASS_PANES_RAINBOW)
        getOrCreateBuilder(Tags.Items.MUSHROOMS).add(HItems.BLUE_LUMSHROOM, HItems.PURPLE_LUMSHROOM)
    }

    /**
     * Resolves a Path for the location to save the given tag.
     */
    override fun makePath(id: ResourceLocation): Path? = super.makePath(id)

    /**
     * Gets a name for this provider, to use in logging.
     */
    override fun getName() = "Hardcore Dungeons Block Tags"
}
