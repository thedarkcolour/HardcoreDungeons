package thedarkcolour.hardcoredungeons.item.castleton

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.item.*
import net.minecraft.util.Direction
import net.minecraft.util.NonNullList
import net.minecraft.util.math.shapes.ISelectionContext

class CastletonTorchItem(blockIn: Block, private val wallBlock: Block, private val isLit: Boolean, builder: Properties) : BlockItem(blockIn, builder) {
    override fun getStateForPlacement(context: BlockItemUseContext): BlockState? {
        val state = wallBlock.getStateForPlacement(context)
        var state1: BlockState? = null
        val worldIn = context.world
        val pos = context.pos

        for (direction in context.nearestLookingDirections) {
            if (direction != Direction.UP) {
                val state2 = if (direction == Direction.DOWN) block.getStateForPlacement(context) else state
                if (state2?.isValidPosition(worldIn, pos) == true) {
                    state1 = state2
                    break
                }
            }
        }

        return if (state1 != null && worldIn.func_226663_a_(state1, pos, ISelectionContext.dummy())) state1 else null
    }

    override fun addToBlockToItemMap(blockToItemMap: MutableMap<Block, Item>, itemIn: Item) {
        if (isLit) {
            super.addToBlockToItemMap(blockToItemMap, itemIn)
            blockToItemMap[wallBlock] = itemIn
        }
    }

    override fun getTranslationKey(): String {
        return if (isLit) {
            super.getTranslationKey()
        } else {
            defaultTranslationKey
        }
    }

    override fun removeFromBlockToItemMap(map: MutableMap<Block, Item>, itemIn: Item) {
        if (isLit) {
            super.removeFromBlockToItemMap(map, itemIn)
            map.remove(wallBlock)
        }
    }

    override fun fillItemGroup(group: ItemGroup, items: NonNullList<ItemStack>) {
        if (isLit) {
            super.fillItemGroup(group, items)
        } else {
            if (isInGroup(group)) {
                items.add(ItemStack(this))
            }
        }
    }
}