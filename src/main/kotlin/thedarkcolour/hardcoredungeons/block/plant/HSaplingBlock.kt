package thedarkcolour.hardcoredungeons.block.plant

import net.minecraft.world.level.block.state.BlockState
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.tags.BlockTags
import net.minecraft.util.RandomSource
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.SaplingBlock
import net.minecraft.world.level.block.grower.AbstractTreeGrower
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties

class HSaplingBlock(tree: AbstractTreeGrower, properties: HProperties) : SaplingBlock(tree, properties.build()) {
    override fun mayPlaceOn(state: BlockState, worldIn: BlockGetter, pos: BlockPos): Boolean {
        return super.mayPlaceOn(state, worldIn, pos) || state.`is`(BlockTags.DIRT)
    }

    override fun tick(state: BlockState, worldIn: ServerLevel, pos: BlockPos, rand: RandomSource) {
        if (worldIn.isAreaLoaded(pos, 1)) {
            if (worldIn.getLightEmission(pos.above()) < 9 && rand.nextInt(7) == 0) {
                advanceTree(worldIn, pos, state, rand)
            }
        }
    }
}