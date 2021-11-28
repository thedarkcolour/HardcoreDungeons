package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.tags.BlockTags
import net.minecraft.util.RegistryKey
import net.minecraft.resources.ResourceLocation
import thedarkcolour.hardcoredungeons.block.base.HBlockMaker
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import thedarkcolour.hardcoredungeons.data.BlockTagGenerator
import thedarkcolour.hardcoredungeons.data.modelgen.item.ItemModelType

class PortalCombo(id: ResourceLocation, key: () -> RegistryKey<World>, appearance: (() -> Block)? = null) : ICombo {
    val frame by HBlockMaker.cubeAllWithItem(id.path + "_portal_frame", HProperties.copy(Blocks.OBSIDIAN), appearance = appearance)
    val portal by HBlockMaker.withItem(id.path + "_portal", ItemModelType.SIMPLE_ITEM, HBlockMaker.registerPortal(id, key) { frame.defaultBlockState() })

    override fun addTags(gen: BlockTagGenerator) {
        gen.tag(BlockTags.PORTALS).add(portal)
    }
}