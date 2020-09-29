package thedarkcolour.hardcoredungeons.block.plant

import net.minecraft.block.BlockState
import net.minecraft.block.SaplingBlock
import net.minecraft.block.trees.Tree
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockReader
import net.minecraft.world.server.ServerWorld
import net.minecraftforge.common.Tags
import java.util.*

class SaplingBlock(tree: Tree, properties: Properties) : SaplingBlock(tree, properties) {
    override fun isValidGround(state: BlockState, worldIn: IBlockReader, pos: BlockPos): Boolean {
        return super.isValidGround(state, worldIn, pos) || state.isIn(Tags.Blocks.DIRT)
    }

    override fun tick(state: BlockState, worldIn: ServerWorld, pos: BlockPos, rand: Random) {
        if (worldIn.isAreaLoaded(pos, 1)) {
            if (worldIn.getLight(pos.up()) < 9 && rand.nextInt(7) == 0) {
                placeTree(worldIn, pos, state, rand)
            }
        }
    }
}