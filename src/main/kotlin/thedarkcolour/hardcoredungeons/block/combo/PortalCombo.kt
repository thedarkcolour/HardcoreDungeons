package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.block.Blocks
import net.minecraft.tags.BlockTags
import net.minecraft.util.RegistryKey
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import thedarkcolour.hardcoredungeons.block.base.BlockMaker
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties

class PortalCombo(id: ResourceLocation, key: () -> RegistryKey<World>, properties: HProperties = HProperties.copy(Blocks.OBSIDIAN)) : ICombo {
    val frame by BlockMaker.portalFrameWithItem(id, properties, this)
    val portal by BlockMaker.registerPortal(id, key, this)

    override fun addTags(tags: DataTags) {
        tags.block(BlockTags.PORTALS, portal)
    }
}