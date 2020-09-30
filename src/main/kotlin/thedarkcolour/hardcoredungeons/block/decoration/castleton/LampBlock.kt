package thedarkcolour.hardcoredungeons.block.decoration.castleton

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.state.StateContainer
import net.minecraft.state.properties.BlockStateProperties

class LampBlock(properties: Properties) : Block(properties) {
    init {
        defaultState = defaultState.with(BlockStateProperties.LIT, true)
    }

    override fun fillStateContainer(builder: StateContainer.Builder<Block, BlockState>) {
        builder.add(BlockStateProperties.LIT)
    }
}