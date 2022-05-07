package thedarkcolour.hardcoredungeons.block.structure

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.item.BlockItemUseContext
import net.minecraft.state.EnumProperty
import net.minecraft.state.StateContainer
import net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING
import net.minecraft.state.properties.BlockStateProperties.OPEN
import net.minecraft.util.Direction
import net.minecraft.util.IStringSerializable
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import thedarkcolour.hardcoredungeons.block.decoration.HorizontalBlock

/**
 * Hop through a warp painting and show up in a different area.
 * Tile entity with saved position, 2x2 block like a painting
 * One way transport
 */
class TeleportPaintingBlock(properties: HProperties) : HorizontalBlock(properties) {
    init {
        registerDefaultState(defaultBlockState().setValue(OPEN, false).setValue(HORIZONTAL_FACING, Direction.NORTH).setValue(CORNER, Corner.BOTTOM_LEFT))
    }

    override fun createBlockStateDefinition(builder: StateContainer.Builder<Block, BlockState>) {
        builder.add(HORIZONTAL_FACING, CORNER, OPEN)
    }

    override fun getStateForPlacement(context: BlockItemUseContext): BlockState? {
        val pos = context.clickedPos
        val level = context.level
        val facing = context.horizontalDirection
        val bottomRight = pos.relative(facing.counterClockWise)
        return if (pos.y < 255 && level.worldBorder.isWithinBounds(bottomRight) && level.getBlockState(bottomRight).canBeReplaced(context) && level.getBlockState(pos.above()).canBeReplaced(context) && level.getBlockState(bottomRight.above()).canBeReplaced(context)) {
            return defaultBlockState().setValue(CORNER, Corner.BOTTOM_LEFT)
        } else null
    }

    companion object {
        private val CORNER = EnumProperty.create("corner", Corner::class.java)
    }

    enum class Corner(private val serializedName: String) : IStringSerializable {
        TOP_LEFT("top_left"),
        TOP_RIGHT("top_right"),
        BOTTOM_LEFT("bottom_left"),
        BOTTOM_RIGHT("bottom_right");

        override fun getSerializedName(): String {
            return serializedName
        }
    }
}