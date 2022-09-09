package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.tags.BlockTags
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.Item
import net.minecraft.world.item.SignItem
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.properties.WoodType
import net.minecraft.world.level.material.Material
import net.minecraft.world.level.material.MaterialColor
import thedarkcolour.hardcoredungeons.block.base.BlockMaker
import thedarkcolour.hardcoredungeons.block.base.ItemMaker
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import thedarkcolour.hardcoredungeons.data.modelgen.item.ItemModelType
import thedarkcolour.hardcoredungeons.item.Group
import thedarkcolour.hardcoredungeons.registry.HBlocks
import thedarkcolour.hardcoredungeons.registry.HBlockUtil

class SignCombo(wood: String, type: WoodType, topCol: MaterialColor, applyProperties: (HProperties) -> Unit) : ICombo, ItemLike {
    val sign by BlockMaker.registerStandingSign(wood + "_sign", type, BlockMaker.props(Material.WOOD, topCol, applyProperties))
    val wallSign by BlockMaker.registerWallSign(wood + "_wall_sign", type, BlockMaker.props(Material.WOOD, topCol, applyProperties))
    val item by ItemMaker.registerModelled(wood + "_sign", ItemModelType.SIMPLE_ITEM) { SignItem(Item.Properties().stacksTo(16).tab(Group), sign, wallSign) }

    init {
        HBlocks.onceRegistered {
            HBlockUtil.addValidBlocks(BlockEntityType.SIGN, sign, wallSign)
        }
    }

    override fun addTags(tags: DataTags) {
        tags.block(BlockTags.STANDING_SIGNS, sign)
        tags.block(BlockTags.WALL_SIGNS, wallSign)
        tags.item(ItemTags.SIGNS, item)
    }

    override fun asItem() = item
}