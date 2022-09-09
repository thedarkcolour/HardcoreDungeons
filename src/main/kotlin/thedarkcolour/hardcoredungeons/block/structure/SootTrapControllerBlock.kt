package thedarkcolour.hardcoredungeons.block.structure

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.item.BlockItemUseContext
import net.minecraft.state.BooleanProperty
import net.minecraft.state.StateContainer
import net.minecraft.state.properties.BlockStateProperties
import net.minecraft.tileentity.TileEntity
import net.minecraft.core.BlockPos
import net.minecraft.world.IBlockReader
import net.minecraft.world.level.Level
import thedarkcolour.hardcoredungeons.block.base.HBlock
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import thedarkcolour.hardcoredungeons.tileentity.SootTrapControllerTileEntity

class SootTrapControllerBlock(properties: HProperties) : HBlock(properties) {
    init {
        registerDefaultState(stateDefinition.any().setValue(POWERED, false))
    }

    override fun getStateForPlacement(ctx: BlockItemUseContext): BlockState? {
        return defaultBlockState().setValue(POWERED, ctx.level.hasNeighborSignal(ctx.clickedPos))
    }

    override fun createBlockStateDefinition(builder: StateContainer.Builder<Block, BlockState>) {
        builder.add(POWERED)
    }

    override fun neighborChanged(state: BlockState, level: World, pos: BlockPos, p_220069_4_: Block?, fromPos: BlockPos?, isMoving: Boolean) {
        if (!level.isClientSide) {
            val hasPower = level.hasNeighborSignal(pos)

            if (state.getValue(POWERED) != hasPower) {
                if (hasPower) { // if we now have power
                    val tile = level.getBlockEntity(pos)

                    if (tile is SootTrapControllerTileEntity) {
                        tile.activate()
                    }
                }
            }

            // update state
            level.setBlock(pos, state.setValue(POWERED, hasPower), 2)
        }
    }

    override fun hasTileEntity(state: BlockState?): Boolean {
        return true
    }

    override fun createTileEntity(state: BlockState?, world: IBlockReader?): TileEntity {
        return SootTrapControllerTileEntity()
    }

    companion object {
        val POWERED: BooleanProperty = BlockStateProperties.POWERED
    }
}