package thedarkcolour.hardcoredungeons.data

import net.minecraft.data.DataGenerator
import net.minecraft.data.tags.BlockTagsProvider
import net.minecraft.data.tags.ItemTagsProvider
import net.minecraft.tags.TagKey
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraftforge.common.Tags
import net.minecraftforge.common.data.ExistingFileHelper
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.block.combo.DataTags
import thedarkcolour.hardcoredungeons.registry.block.HBlocks
import thedarkcolour.hardcoredungeons.registry.items.BULLET_ITEM
import thedarkcolour.hardcoredungeons.registry.items.INCENDIARY_BULLET_ITEM
import thedarkcolour.hardcoredungeons.registry.items.RAINBOWSTONE_GEM_ITEM
import thedarkcolour.hardcoredungeons.registry.items.SHELL_ITEM
import thedarkcolour.hardcoredungeons.tags.HItemTags

class ItemTagGenerator(gen: DataGenerator, blockTags: BlockTagsProvider, helper: ExistingFileHelper) : ItemTagsProvider(gen, blockTags, HardcoreDungeons.ID, helper) {
    override fun addTags() {
        BlockTagGenerator.tags(DataTags.Items(this))

        tag(HItemTags.LUMSHROOM).add(HBlocks.PURPLE_LUMSHROOM.plant.asItem(), HBlocks.BLUE_LUMSHROOM.plant.asItem())

        tag(HItemTags.AMMUNITION_GENERIC).add(BULLET_ITEM, SHELL_ITEM, INCENDIARY_BULLET_ITEM)
        tag(HItemTags.AMMUNITION_SMALL).add(BULLET_ITEM, INCENDIARY_BULLET_ITEM)
        tag(HItemTags.AMMUNITION_INCENDIARY).add(INCENDIARY_BULLET_ITEM)
        tag(HItemTags.GEMS_MALACHITE).add(HBlocks.MALACHITE_CRYSTAL.item)
        tag(HItemTags.GEMS_RAINBOWSTONE).add(RAINBOWSTONE_GEM_ITEM)
        //getBuilder(HBlockTags.GLASS_RAINBOW).add(HBlocks.RAINBOW_GLASS)
        //getBuilder(HBlockTags.GLASS_PANES_RAINBOW).add(HBlocks.RAINBOW_GLASS_PANE)
        //getBuilder(HBlockTags.FENCES_BRICK).add(HBlocks.CASTLETON_BRICK_FENCE, HBlocks.CHARGED_CASTLETON_BRICK_FENCE, HBlocks.RAINBOW_BRICK_FENCE, HBlocks.RAINBOW_FACTORY_BRICK_FENCE)

        // forge tags
        //tag(Tags.Items.FENCES).add(LUMLIGHT_FENCE, CHARGED_CASTLETON_BRICK_FENCE)
        //tag(Tags.Items.FENCE_GATES_WOODEN).add(LUMLIGHT_FENCE_GATE)
        //tag(Tags.Items.GLASS).add(HItemTags.GLASS_RAINBOW)
        //tag(Tags.Items.GLASS_PANES).add(HItemTags.GLASS_PANES_RAINBOW)
        //tag(Tags.Items.MUSHROOMS).add(BLUE_LUMSHROOM, PURPLE_LUMSHROOM)
        //tag(Tags.Items.GEMS_DIAMOND).add(PRISTINE_DIAMOND_ITEM)
        tag(Tags.Items.GEMS).add(HBlocks.MALACHITE_CRYSTAL.item)
    }

    // Override to make public
    public override fun copy(from: TagKey<Block>, to: TagKey<Item>) = super.copy(from, to)
    public override fun tag(tag: TagKey<Item>): TagAppender<Item> = super.tag(tag)

    override fun getName() = "Hardcore Dungeons Item Tags"
}