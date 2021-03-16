package thedarkcolour.hardcoredungeons.data

import net.minecraft.data.BlockTagsProvider
import net.minecraft.data.DataGenerator
import net.minecraft.data.ItemTagsProvider
import net.minecraft.tags.ItemTags
import net.minecraftforge.common.Tags
import net.minecraftforge.common.data.ExistingFileHelper
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.registry.HItems
import thedarkcolour.hardcoredungeons.tags.HItemTags

class ItemTagGenerator(gen: DataGenerator, blockTags: BlockTagsProvider, helper: ExistingFileHelper) : ItemTagsProvider(gen, blockTags, HardcoreDungeons.ID, helper) {
    override fun registerTags() {
        // hcd tags
        getOrCreateBuilder(HItemTags.LUMLIGHT_LOGS).add(HItems.LUMLIGHT_LOG, HItems.LUMLIGHT_WOOD, HItems.STRIPPED_LUMLIGHT_LOG, HItems.STRIPPED_LUMLIGHT_WOOD)
        getOrCreateBuilder(HItemTags.AMMUNITION_GENERIC).add(HItems.BULLET, HItems.SHELL, HItems.INCENDIARY_BULLET)
        getOrCreateBuilder(HItemTags.AMMUNITION_SMALL).add(HItems.BULLET, HItems.INCENDIARY_BULLET)
        getOrCreateBuilder(HItemTags.AMMUNITION_INCENDIARY).add(HItems.INCENDIARY_BULLET)
        getOrCreateBuilder(HItemTags.GEMS_MALACHITE).add(HItems.MALACHITE_CRYSTAL)
        getOrCreateBuilder(HItemTags.GEMS_RAINBOWSTONE).add(HItems.RAINBOWSTONE_GEM)
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
        getOrCreateBuilder(Tags.Items.GEMS_DIAMOND).add(HItems.PRISTINE_DIAMOND)
        getOrCreateBuilder(Tags.Items.GEMS).add(HItems.MALACHITE_CRYSTAL)
    }

    override fun getName() = "HCD Block Tags"
}