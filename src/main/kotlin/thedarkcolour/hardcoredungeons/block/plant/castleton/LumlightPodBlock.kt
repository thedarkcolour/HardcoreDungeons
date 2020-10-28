package thedarkcolour.hardcoredungeons.block.plant.castleton

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.state.StateContainer
import net.minecraft.state.properties.BlockStateProperties
import thedarkcolour.hardcoredungeons.block.properties.HProperties

class LumlightPodBlock(properties: HProperties) : Block(properties.build()) {
    init {
        defaultState = defaultState.with(BlockStateProperties.AGE_0_2, 0)
    }

    override fun fillStateContainer(builder: StateContainer.Builder<Block, BlockState>) {
        builder.add(BlockStateProperties.AGE_0_2)
    }
}