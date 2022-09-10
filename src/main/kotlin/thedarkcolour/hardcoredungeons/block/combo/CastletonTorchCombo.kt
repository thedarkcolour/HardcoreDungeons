package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.client.renderer.ItemBlockRenderTypes
import net.minecraft.client.renderer.RenderType
import net.minecraft.tags.BlockTags
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.material.Material
import thedarkcolour.hardcoredungeons.registry.items.ItemMaker
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import thedarkcolour.hardcoredungeons.block.decoration.castleton.CastletonTorchBlock
import thedarkcolour.hardcoredungeons.data.modelgen.item.ItemModelType
import thedarkcolour.hardcoredungeons.item.CastletonTorchItem
import thedarkcolour.hardcoredungeons.item.Group
import thedarkcolour.hardcoredungeons.registry.block.HBlocks

/**
 * @param name The prefix ex. "soul_fire". "_torch" and "_wall_torch" are applied as name suffixes.
 */
class CastletonTorchCombo private constructor(properties: HProperties) : ICombo {
    // Use an alternative constructor instead of storing the properties in a field
    constructor() : this(HProperties.of(Material.DECORATION).lightLevel { state ->
        if (state.getValue(BlockStateProperties.LIT)) 7 else 0
    }.sound(SoundType.WOOD).noCollision())

    val standing by HBlocks.register("castleton_torch") { CastletonTorchBlock(properties) }
    val wall by HBlocks.register("castleton_wall_torch") { CastletonTorchBlock.Wall(properties) }

    val litItem by ItemMaker.registerModelled("castleton_torch", ItemModelType.SIMPLE_ITEM) {
        CastletonTorchItem(standing, wall, true, Item.Properties().tab(Group))
    }
    val burntItem by ItemMaker.registerModelled("burnt_castleton_torch", ItemModelType.SIMPLE_ITEM) {
        CastletonTorchItem(standing, wall, false, Item.Properties().tab(Group))
    }

    override fun setRenderLayers() {
        ItemBlockRenderTypes.setRenderLayer(standing, RenderType.cutout())
        ItemBlockRenderTypes.setRenderLayer(wall, RenderType.cutout())
    }

    override fun addTags(tags: DataTags) {
        tags.block(BlockTags.WALL_POST_OVERRIDE, standing)
    }
}