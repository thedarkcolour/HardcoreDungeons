package thedarkcolour.hardcoredungeons.block.combo

import net.minecraft.tags.BlockTags
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.Item
import net.minecraft.world.item.SignItem
import net.minecraft.world.level.ItemLike
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.properties.WoodType
import net.minecraft.world.level.material.MapColor
import thedarkcolour.hardcoredungeons.block.base.BlockMaker
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import thedarkcolour.hardcoredungeons.data.modelgen.item.ItemModelType
import thedarkcolour.hardcoredungeons.registry.block.HBlockUtil
import thedarkcolour.hardcoredungeons.registry.block.HBlocks
import thedarkcolour.hardcoredungeons.registry.items.ItemMaker

class SignCombo(wood: String, type: WoodType, topCol: MapColor, applyProperties: (HProperties) -> Unit) : BlockCombo(), ItemLike {
    val sign by BlockMaker.registerStandingSign(wood + "_sign", type, BlockMaker.props(topCol, applyProperties))
    val wallSign by BlockMaker.registerWallSign(wood + "_wall_sign", type, BlockMaker.props(topCol, applyProperties))
    val item by ItemMaker.registerModelled(wood + "_sign", ItemModelType.SIMPLE_ITEM) { SignItem(Item.Properties().stacksTo(16)/*.tab(Group)*/, sign, wallSign) }

    init {
        HBlocks.onceRegistered {
            HBlockUtil.addValidBlocks(BlockEntityType.SIGN, sign, wallSign)
        }
    }

    override fun addTags(tags: DataTags) {
        tags.block(BlockTags.STANDING_SIGNS, sign)
        tags.block(BlockTags.WALL_SIGNS, wallSign)
        tags.item(ItemTags.SIGNS, item)

        tags.axe(sign)
        tags.axe(wallSign)
    }

    override fun asItem() = item
}