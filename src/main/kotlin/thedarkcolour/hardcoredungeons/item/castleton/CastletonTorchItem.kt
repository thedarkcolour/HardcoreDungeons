package thedarkcolour.hardcoredungeons.item.castleton

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.item.*
import net.minecraft.util.Direction
import net.minecraft.util.NonNullList
import net.minecraft.util.math.shapes.ISelectionContext

class CastletonTorchItem(blockIn: Block, private val wallBlock: Block, private val isLit: Boolean, builder: Properties) : BlockItem(blockIn, builder) {
    override fun getPlacementState(context: BlockItemUseContext): BlockState? {
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

        return if (state1 != null && worldIn.isUnobstructed(state1, pos, ISelectionContext.empty())) state1 else null
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

    override fun fillItemCategory(group: ItemGroup, items: NonNullList<ItemStack>) {
        if (isLit) {
            super.fillItemCategory(group, items)
        } else {
            if (allowdedIn(group)) {
                items.add(ItemStack(this))
            }
        }
    }
}