package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.block.WoodType
import net.minecraft.block.material.Material
import net.minecraft.block.material.MaterialColor
import net.minecraft.item.Item
import net.minecraft.item.SignItem
import net.minecraft.tags.BlockTags
import net.minecraft.tags.ItemTags
import net.minecraft.tileentity.TileEntityType
import net.minecraft.util.IItemProvider
import thedarkcolour.hardcoredungeons.block.base.BlockMaker
import thedarkcolour.hardcoredungeons.block.base.ItemMaker
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import thedarkcolour.hardcoredungeons.data.modelgen.item.ItemModelType
import thedarkcolour.hardcoredungeons.item.Group
import thedarkcolour.hardcoredungeons.registry.HBlocks
import thedarkcolour.hardcoredungeons.registry.HBlocksOld

class SignCombo(wood: String, type: WoodType, topCol: MaterialColor, applyProperties: (HProperties) -> Unit) : ICombo, IItemProvider {
    val sign by BlockMaker.registerStandingSign(wood + "_sign", type, BlockMaker.props(Material.WOOD, topCol, applyProperties))
    val wallSign by BlockMaker.registerWallSign(wood + "_wall_sign", type, BlockMaker.props(Material.WOOD, topCol, applyProperties))
    val item by ItemMaker.registerModelled(wood + "_sign", ItemModelType.SIMPLE_ITEM) { SignItem(Item.Properties().stacksTo(16).tab(Group), sign, wallSign) }

    init {
        HBlocks.onceRegistered {
            HBlocksOld.addValidBlocks(TileEntityType.SIGN, sign, wallSign)
        }
    }

    override fun addTags(tags: DataTags) {
        tags.block(BlockTags.STANDING_SIGNS, sign)
        tags.block(BlockTags.WALL_SIGNS, wallSign)
        tags.item(ItemTags.SIGNS, item)
    }

    override fun asItem() = item
}