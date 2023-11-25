package thedarkcolour.hardcoredungeons.data

import net.minecraft.world.item.Item
import net.minecraftforge.common.Tags
import thedarkcolour.hardcoredungeons.block.combo.DataTags
import thedarkcolour.hardcoredungeons.registry.block.HBlocks
import thedarkcolour.hardcoredungeons.registry.items.BULLET_ITEM
import thedarkcolour.hardcoredungeons.registry.items.INCENDIARY_BULLET_ITEM
import thedarkcolour.hardcoredungeons.registry.items.RAINBOWSTONE_GEM_ITEM
import thedarkcolour.hardcoredungeons.registry.items.SHELL_ITEM
import thedarkcolour.hardcoredungeons.tags.HItemTags
import thedarkcolour.modkit.data.MKTagsProvider

fun MKTagsProvider<Item>.addItemTags() {
    comboTags(DataTags.Items(this))

    tag(HItemTags.LUMSHROOM).add(HBlocks.PURPLE_LUMSHROOM.plant.asItem(), HBlocks.BLUE_LUMSHROOM.plant.asItem())

    tag(HItemTags.AMMUNITION_GENERIC).add(BULLET_ITEM, SHELL_ITEM, INCENDIARY_BULLET_ITEM)
    tag(HItemTags.AMMUNITION_SMALL).add(BULLET_ITEM, INCENDIARY_BULLET_ITEM)
    tag(HItemTags.AMMUNITION_INCENDIARY).add(INCENDIARY_BULLET_ITEM)
    tag(HItemTags.GEMS_MALACHITE).add(HBlocks.MALACHITE_CRYSTAL.shard)
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
    tag(Tags.Items.GEMS).add(HBlocks.MALACHITE_CRYSTAL.shard)
}
