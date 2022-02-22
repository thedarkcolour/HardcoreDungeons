package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.RenderTypeLookup
import net.minecraft.item.Item
import net.minecraft.state.properties.BlockStateProperties
import net.minecraft.tags.BlockTags
import thedarkcolour.hardcoredungeons.block.base.ItemMaker
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import thedarkcolour.hardcoredungeons.block.decoration.castleton.CastletonTorchBlock
import thedarkcolour.hardcoredungeons.data.modelgen.item.ItemModelType
import thedarkcolour.hardcoredungeons.item.Group
import thedarkcolour.hardcoredungeons.item.castleton.CastletonTorchItem
import thedarkcolour.hardcoredungeons.registry.HBlocks

/**
 * @param name The prefix ex. "soul_fire". "_torch" and "_wall_torch" are applied as name suffixes.
 */
class CastletonTorchCombo : ICombo {
    private val properties = HProperties.of(Material.DECORATION).lightLevel { state -> if (state.getValue(BlockStateProperties.LIT)) 7 else 0 }.sound(SoundType.WOOD).noCollission()

    val standing by HBlocks.register("castleton_torch") { CastletonTorchBlock(properties) }
    val wall by HBlocks.register("castleton_wall_torch") { CastletonTorchBlock.Wall(properties) }

    val litItem by ItemMaker.registerModelled("castleton_torch", ItemModelType.SIMPLE_ITEM) {
        CastletonTorchItem(standing, wall, true, Item.Properties().tab(Group))
    }
    val burntItem by ItemMaker.registerModelled("burnt_castleton_torch", ItemModelType.SIMPLE_ITEM) {
        CastletonTorchItem(standing, wall, false, Item.Properties().tab(Group))
    }

    override fun setRenderLayers() {
        RenderTypeLookup.setRenderLayer(standing, RenderType.cutout())
        RenderTypeLookup.setRenderLayer(wall, RenderType.cutout())
    }

    override fun addTags(tags: DataTags) {
        tags.block(BlockTags.WALL_POST_OVERRIDE, standing)
    }
}