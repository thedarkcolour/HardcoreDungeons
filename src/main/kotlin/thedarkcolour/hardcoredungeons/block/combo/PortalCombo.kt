package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.resources.ResourceKey
import net.minecraft.tags.BlockTags
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Blocks
import thedarkcolour.hardcoredungeons.block.base.BlockMaker
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties

class PortalCombo(id: ResourceLocation, key: () -> ResourceKey<Level>, properties: HProperties = HProperties.copy(Blocks.OBSIDIAN)) : ICombo {
    val frame by BlockMaker.portalFrameWithItem(id, properties, this)
    val portal by BlockMaker.registerPortal(id, key, this)

    override fun addTags(tags: DataTags) {
        tags.block(BlockTags.PORTALS, portal)
    }
}