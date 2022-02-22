package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.tags.BlockTags
import net.minecraft.util.RegistryKey
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import thedarkcolour.hardcoredungeons.block.base.BlockMaker
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import thedarkcolour.hardcoredungeons.data.modelgen.item.ItemModelType

class PortalCombo(id: ResourceLocation, key: () -> RegistryKey<World>, appearance: (() -> Block)? = null) : ICombo {
    val frame by BlockMaker.cubeAllWithItem(id.path + "_portal_frame", HProperties.copy(Blocks.OBSIDIAN), appearance = appearance)
    val portal by BlockMaker.withItem(id.path + "_portal", ItemModelType.SIMPLE_ITEM, BlockMaker.registerPortal(id, key) { frame.defaultBlockState() })

    override fun addTags(tags: DataTags) {
        tags.block(BlockTags.PORTALS, portal)
    }
}