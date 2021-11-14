package thedarkcolour.hardcoredungeons.block.decoration.castleton

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.state.StateContainer
import net.minecraft.state.properties.BlockStateProperties
import thedarkcolour.hardcoredungeons.block.base.HBlock
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties

class LampBlock(properties: HProperties) : HBlock(properties) {
    override fun createBlockStateDefinition(builder: StateContainer.Builder<Block, BlockState>) {
        builder.add(BlockStateProperties.LIT)
    }
}