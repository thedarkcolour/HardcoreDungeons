package thedarkcolour.hardcoredungeons.block.plant

import net.minecraft.block.BlockState
import net.minecraft.block.SaplingBlock
import net.minecraft.block.trees.Tree
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockReader
import net.minecraft.world.server.ServerWorld
import net.minecraftforge.common.Tags
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import java.util.*

class HSaplingBlock(tree: Tree, properties: HProperties) : SaplingBlock(tree, properties.build()) {
    override fun mayPlaceOn(state: BlockState, worldIn: IBlockReader, pos: BlockPos): Boolean {
        return super.mayPlaceOn(state, worldIn, pos) || state.`is`(Tags.Blocks.DIRT)
    }

    override fun tick(state: BlockState, worldIn: ServerWorld, pos: BlockPos, rand: Random) {
        if (worldIn.isAreaLoaded(pos, 1)) {
            if (worldIn.getLightEmission(pos.above()) < 9 && rand.nextInt(7) == 0) {
                advanceTree(worldIn, pos, state, rand)
            }
        }
    }
}