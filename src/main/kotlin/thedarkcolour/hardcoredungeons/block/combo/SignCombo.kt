package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.block.WoodType
import net.minecraft.block.material.Material
import net.minecraft.block.material.MaterialColor
import net.minecraft.item.Item
import net.minecraft.item.SignItem
import net.minecraft.tags.BlockTags
import net.minecraft.tileentity.TileEntityType
import thedarkcolour.hardcoredungeons.block.HBlocks
import thedarkcolour.hardcoredungeons.block.base.HBlockMaker
import thedarkcolour.hardcoredungeons.block.base.ItemMaker
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import thedarkcolour.hardcoredungeons.data.BlockTagGenerator
import thedarkcolour.hardcoredungeons.data.modelgen.item.ItemModelType
import thedarkcolour.hardcoredungeons.item.Group
import thedarkcolour.hardcoredungeons.registry.HBlocksOld

class SignCombo(wood: String, type: WoodType, topCol: MaterialColor, applyProperties: (HProperties) -> Unit) : ICombo {
    val sign by HBlockMaker.registerStandingSign(wood + "_sign", type, HBlockMaker.props(Material.WOOD, topCol, applyProperties))
    val wallSign by HBlockMaker.registerWallSign(wood + "_wall_sign", type, HBlockMaker.props(Material.WOOD, topCol, applyProperties))
    val item by ItemMaker.registerModelled(wood + "_sign", ItemModelType.SIMPLE_ITEM) { SignItem(Item.Properties().stacksTo(16).tab(Group), sign, wallSign) }

    init {
        HBlocks.onceRegistered {
            HBlocksOld.addValidBlocks(TileEntityType.SIGN, sign, wallSign)
        }
    }

    override fun addTags(gen: BlockTagGenerator) {
        gen.tag(BlockTags.STANDING_SIGNS).add(sign)
        gen.tag(BlockTags.WALL_SIGNS).add(wallSign)
    }
}