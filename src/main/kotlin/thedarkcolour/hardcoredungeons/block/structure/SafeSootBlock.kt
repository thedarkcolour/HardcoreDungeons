package thedarkcolour.hardcoredungeons.block.structure

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.tags.BlockTags
import net.minecraft.util.Direction
import net.minecraft.util.SoundCategory
import net.minecraft.util.SoundEvents
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockReader
import net.minecraft.world.World
import net.minecraft.world.server.ServerWorld
import thedarkcolour.hardcoredungeons.registry.HBlocks
import thedarkcolour.hardcoredungeons.block.base.HBlock
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import java.util.*

class SafeSootBlock(properties: HProperties) : HBlock(properties) {
    override fun neighborChanged(
        state: BlockState,
        level: World,
        pos: BlockPos,
        block: Block,
        fromPos: BlockPos,
        p_220069_6_: Boolean
    ) {
        if (level.getBlockState(fromPos).`is`(BlockTags.FIRE)) {
            if (pos.above() == fromPos) {
                level.blockTicks.scheduleTick(pos, HBlocks.SOOT, 40)
            }
        }
    }

    override fun tick(p_225534_1_: BlockState, level: ServerWorld, pos: BlockPos, p_225534_4_: Random) {
        val above = pos.above()

        if (level.getBlockState(above).`is`(BlockTags.FIRE)) {
            level.removeBlock(above, false)
            level.playSound(null, pos.x + 0.5, pos.y.toDouble(), pos.z + 0.5, SoundEvents.FIRE_EXTINGUISH, SoundCategory.BLOCKS,
                0.5f,
                2.6f + (p_225534_4_.nextFloat() - p_225534_4_.nextFloat()) * 0.8f)
        } else {
            level.setBlockAndUpdate(pos.above(), Blocks.FIRE.defaultBlockState())
            level.playSound(null, pos.x + 0.5, pos.y.toDouble(), pos.z + 0.5, SoundEvents.FIRE_EXTINGUISH, SoundCategory.BLOCKS,
                0.5f,
                2.6f + (p_225534_4_.nextFloat() - p_225534_4_.nextFloat()) * 0.8f)
        }
    }

    override fun isFlammable(state: BlockState?, world: IBlockReader?, pos: BlockPos?, face: Direction?): Boolean {
        return face == Direction.UP
    }
}