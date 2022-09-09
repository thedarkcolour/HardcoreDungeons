package thedarkcolour.hardcoredungeons.block.structure

import net.minecraft.world.level.block.state.BlockState
import net.minecraft.tags.BlockTags
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.util.RandomSource
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.pathfinder.PathComputationType
import thedarkcolour.hardcoredungeons.block.base.HBlock
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import thedarkcolour.hardcoredungeons.registry.HBlocks
import java.util.*

class SafeSootBlock(properties: HProperties) : HBlock(properties) {
    override fun neighborChanged(state: BlockState, level: Level, pos: BlockPos, block: Block, fromPos: BlockPos, p_220069_6_: Boolean) {
        if (level.getBlockState(fromPos).`is`(BlockTags.FIRE)) {
            if (pos.above() == fromPos) {
                level.scheduleTick(pos, HBlocks.SOOT, 40)
            }
        }
    }

    override fun tick(state: BlockState, level: ServerLevel, pos: BlockPos, rand: RandomSource) {
        val above = pos.above()

        if (level.getBlockState(above).`is`(BlockTags.FIRE)) {
            level.removeBlock(above, false)
            level.playSound(null, pos.x + 0.5, pos.y.toDouble(), pos.z + 0.5, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS,
                0.5f,
                2.6f + (rand.nextFloat() - rand.nextFloat()) * 0.8f)
        } else {
            level.setBlockAndUpdate(pos.above(), Blocks.FIRE.defaultBlockState())
            level.playSound(null, pos.x + 0.5, pos.y.toDouble(), pos.z + 0.5, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS,
                0.5f,
                2.6f + (rand.nextFloat() - rand.nextFloat()) * 0.8f)
        }
    }

    override fun isFlammable(state: BlockState?, world: LevelReader?, pos: BlockPos?, face: Direction?): Boolean {
        return face == Direction.UP
    }

    override fun useShapeForLightOcclusion(state: BlockState): Boolean {
        return true
    }

    override fun isPathfindable(p_196266_1_: BlockState?, p_196266_2_: BlockGetter?, p_196266_3_: BlockPos?, p_196266_4_: PathComputationType?): Boolean {
        return false
    }
}