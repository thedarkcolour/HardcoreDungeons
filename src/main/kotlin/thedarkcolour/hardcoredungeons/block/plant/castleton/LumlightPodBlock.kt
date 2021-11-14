package thedarkcolour.hardcoredungeons.block.plant.castleton

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.state.StateContainer
import net.minecraft.state.properties.BlockStateProperties
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties

class LumlightPodBlock(properties: HProperties) : Block(properties.build()) {
    init {
        registerDefaultState(stateDefinition.any().setValue(BlockStateProperties.AGE_2, 0))
    }

    override fun createBlockStateDefinition(builder: StateContainer.Builder<Block, BlockState>) {
        builder.add(BlockStateProperties.AGE_2)
    }
}