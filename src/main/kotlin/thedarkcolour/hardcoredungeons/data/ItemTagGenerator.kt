package thedarkcolour.hardcoredungeons.data

import net.minecraft.block.Block
import net.minecraft.data.BlockTagsProvider
import net.minecraft.data.DataGenerator
import net.minecraft.data.ItemTagsProvider
import net.minecraft.item.Item
import net.minecraft.tags.ITag
import net.minecraftforge.common.Tags
import net.minecraftforge.common.data.ExistingFileHelper
import thedarkcolour.hardcoredungeons.HardcoreDungeons
import thedarkcolour.hardcoredungeons.block.combo.DataTags
import thedarkcolour.hardcoredungeons.registry.HBlocks
import thedarkcolour.hardcoredungeons.registry.HItems
import thedarkcolour.hardcoredungeons.tags.HItemTags

class ItemTagGenerator(gen: DataGenerator, blockTags: BlockTagsProvider, helper: ExistingFileHelper) : ItemTagsProvider(gen, blockTags, HardcoreDungeons.ID, helper) {
    override fun addTags() {
        BlockTagGenerator.tags(DataTags.Items(this))

        tag(HItemTags.LUMSHROOM).add(HBlocks.PURPLE_LUMSHROOM.plant.asItem(), HBlocks.BLUE_LUMSHROOM.plant.asItem())

        tag(HItemTags.AMMUNITION_GENERIC).add(HItems.BULLET, HItems.SHELL, HItems.INCENDIARY_BULLET)
        tag(HItemTags.AMMUNITION_SMALL).add(HItems.BULLET, HItems.INCENDIARY_BULLET)
        tag(HItemTags.AMMUNITION_INCENDIARY).add(HItems.INCENDIARY_BULLET)
        tag(HItemTags.GEMS_MALACHITE).add(HBlocks.MALACHITE_CRYSTAL.item)
        tag(HItemTags.GEMS_RAINBOWSTONE).add(HItems.RAINBOWSTONE_GEM)
        //getBuilder(HBlockTags.GLASS_RAINBOW).add(HBlocks.RAINBOW_GLASS)
        //getBuilder(HBlockTags.GLASS_PANES_RAINBOW).add(HBlocks.RAINBOW_GLASS_PANE)
        //getBuilder(HBlockTags.FENCES_BRICK).add(HBlocks.CASTLETON_BRICK_FENCE, HBlocks.CHARGED_CASTLETON_BRICK_FENCE, HBlocks.RAINBOW_BRICK_FENCE, HBlocks.RAINBOW_FACTORY_BRICK_FENCE)

        // forge tags
        //tag(Tags.Items.FENCES).add(HItems.LUMLIGHT_FENCE, HItems.CHARGED_CASTLETON_BRICK_FENCE)
        //tag(Tags.Items.FENCE_GATES_WOODEN).add(HItems.LUMLIGHT_FENCE_GATE)
        //tag(Tags.Items.GLASS).add(HItemTags.GLASS_RAINBOW)
        //tag(Tags.Items.GLASS_PANES).add(HItemTags.GLASS_PANES_RAINBOW)
        //tag(Tags.Items.MUSHROOMS).add(HItems.BLUE_LUMSHROOM, HItems.PURPLE_LUMSHROOM)
        tag(Tags.Items.GEMS_DIAMOND).add(HItems.PRISTINE_DIAMOND)
        tag(Tags.Items.GEMS).add(HBlocks.MALACHITE_CRYSTAL.item)
    }

    // Override to make public
    public override fun copy(from: ITag.INamedTag<Block>, to: ITag.INamedTag<Item>) = super.copy(from, to)
    public override fun tag(tag: ITag.INamedTag<Item>): Builder<Item> = super.tag(tag)

    override fun getName() = "Hardcore Dungeons Item Tags"
}