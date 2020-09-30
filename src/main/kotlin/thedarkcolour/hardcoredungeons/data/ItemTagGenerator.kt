package thedarkcolour.hardcoredungeons.data

import net.minecraft.data.DataGenerator
import net.minecraft.data.ItemTagsProvider
import net.minecraft.loot.TagLootEntry.getBuilder
import net.minecraft.tags.ItemTags
import net.minecraft.util.ResourceLocation
import net.minecraftforge.common.Tags
import thedarkcolour.hardcoredungeons.registry.HItems
import thedarkcolour.hardcoredungeons.tags.HItemTags
import java.nio.file.Path

class ItemTagGenerator(gen: DataGenerator) : ItemTagsProvider(gen) {
    var filter: Set<ResourceLocation>? = null

    /**
     * Register tags for each block.
     */
    override fun registerTags() {
        super.registerTags()
        filter = tagToBuilder.entries.map { e -> e.key.id }.toHashSet()

        // hcd tags
        getBuilder(HItemTags.LUMLIGHT_LOGS).add(HItems.LUMLIGHT_LOG, HItems.LUMLIGHT_WOOD, HItems.STRIPPED_LUMLIGHT_LOG, HItems.STRIPPED_LUMLIGHT_WOOD)
        //getBuilder(HBlockTags.GLASS_RAINBOW).add(HBlocks.RAINBOW_GLASS)
        //getBuilder(HBlockTags.GLASS_PANES_RAINBOW).add(HBlocks.RAINBOW_GLASS_PANE)
        //getBuilder(HBlockTags.FENCES_BRICK).add(HBlocks.CASTLETON_BRICK_FENCE, HBlocks.CHARGED_CASTLETON_BRICK_FENCE, HBlocks.RAINBOW_BRICK_FENCE, HBlocks.RAINBOW_FACTORY_BRICK_FENCE)

        // vanilla tags
        getBuilder(ItemTags.PLANKS).add(HItems.LUMLIGHT_PLANKS)
        getBuilder(ItemTags.WOODEN_DOORS).add(HItems.LUMLIGHT_DOOR)
        getBuilder(ItemTags.WOODEN_STAIRS).add(HItems.AURI_STAIRS, HItems.LUMLIGHT_STAIRS)
        getBuilder(ItemTags.WOODEN_SLABS).add(HItems.AURI_SLAB, HItems.LUMLIGHT_SLAB)
        getBuilder(ItemTags.WOODEN_FENCES).add(HItems.LUMLIGHT_FENCE)
        getBuilder(ItemTags.WOODEN_PRESSURE_PLATES).add(HItems.LUMLIGHT_PRESSURE_PLATE)
        getBuilder(ItemTags.SAPLINGS).add(HItems.LUMLIGHT_SAPLING)
        getBuilder(ItemTags.LOGS).add(HItemTags.LUMLIGHT_LOGS)

        // forge tags
        getBuilder(Tags.Items.FENCES).add(HItems.LUMLIGHT_FENCE, HItems.CHARGED_CASTLETON_BRICK_FENCE)
        getBuilder(Tags.Items.FENCE_GATES_WOODEN).add(HItems.LUMLIGHT_FENCE_GATE)
        //getBuilder(Tags.Items.GLASS).add(HItemTags.GLASS_RAINBOW)
        //getBuilder(Tags.Items.GLASS_PANES).add(HItemTags.GLASS_PANES_RAINBOW)
        getBuilder(Tags.Items.MUSHROOMS).add(HItems.BLUE_LUMSHROOM, HItems.PURPLE_LUMSHROOM)
    }

    /**
     * Resolves a Path for the location to save the given tag.
     */
    override fun makePath(id: ResourceLocation): Path? {
        return if (filter?.contains(id) == true) null else super.makePath(id)
    }

    /**
     * Gets a name for this provider, to use in logging.
     */
    override fun getName(): String {
        return "Hardcore Dungeons Block Tags"
    }
}