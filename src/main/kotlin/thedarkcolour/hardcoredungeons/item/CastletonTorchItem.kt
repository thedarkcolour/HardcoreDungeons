package thedarkcolour.hardcoredungeons.item

import net.minecraft.core.Direction
import net.minecraft.core.NonNullList
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.block.Block
import net.minecraft.world.phys.shapes.CollisionContext

class CastletonTorchItem(blockIn: Block, private val wallBlock: Block, private val isLit: Boolean, builder: Properties) : BlockItem(blockIn, builder) {
    override fun getPlacementState(context: BlockPlaceContext): BlockState? {
        val state = wallBlock.getStateForPlacement(context)
        var state1: BlockState? = null
        val worldIn = context.level
        val pos = context.clickedPos

        for (direction in context.nearestLookingDirections) {
            if (direction != Direction.UP) {
                val state2 = if (direction == Direction.DOWN) block.getStateForPlacement(context) else state
                if (state2?.canSurvive(worldIn, pos) == true) {
                    state1 = state2
                    break
                }
            }
        }

        return if (state1 != null && worldIn.isUnobstructed(state1, pos, CollisionContext.empty())) state1 else null
    }

    override fun registerBlocks(blockToItemMap: MutableMap<Block, Item>, itemIn: Item) {
        if (isLit) {
            super.registerBlocks(blockToItemMap, itemIn)
            blockToItemMap[wallBlock] = itemIn
        }
    }

    override fun getDescriptionId(): String {
        return if (isLit) {
            super.getDescriptionId()
        } else {
            orCreateDescriptionId
        }
    }

    override fun removeFromBlockToItemMap(map: MutableMap<Block, Item>, itemIn: Item) {
        if (isLit) {
            super.removeFromBlockToItemMap(map, itemIn)
            map.remove(wallBlock)
        }
    }

    override fun fillItemCategory(group: CreativeModeTab, items: NonNullList<ItemStack>) {
        if (isLit) {
            super.fillItemCategory(group, items)
        } else {
            if (allowedIn(group)) {
                items.add(ItemStack(this))
            }
        }
    }
}