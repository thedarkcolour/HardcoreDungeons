package thedarkcolour.hardcoredungeons.feature

import com.mojang.serialization.Codec
import net.minecraft.state.properties.BlockStateProperties.AXIS
import net.minecraft.state.properties.BlockStateProperties.FACING
import net.minecraft.util.Direction
import net.minecraft.util.math.BlockPos
import net.minecraft.world.ISeedReader
import net.minecraft.world.gen.ChunkGenerator
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.NoFeatureConfig
import thedarkcolour.hardcoredungeons.registry.HBlocks
import java.util.*

class CandyCaneFeature(codec: Codec<NoFeatureConfig>) : Feature<NoFeatureConfig>(codec) {
    override fun generate(
        worldIn: ISeedReader,
        generator: ChunkGenerator,
        rand: Random,
        pos: BlockPos,
        config: NoFeatureConfig,
    ): Boolean {
        // don't spawn in the water
        if (!worldIn.getBlockState(pos.down()).isSolid) return false

        // maximum height is 6 blocks
        val height = 3 + rand.nextInt(4)
        // start at ground block for ground checking
        val mutablePos = pos.toMutable().move(0, -1, 0)

        for (i in 0 .. height) {
            setBlockState(worldIn, mutablePos.move(0, 1, 0), CANDY_CANE_STATE)
        }

        // first corner rotation
        val direction = DIRECTIONS[rand.nextInt(4)]
        // last corner rotation
        val opposite = direction.opposite
        // the direction to build out the hook
        val extendDirection = direction.rotateY()

        // begin handle with last upright cane block
        setBlockState(worldIn, mutablePos.move(0, 1, 0), CANDY_CANE_STATE)
        // set first corner
        setBlockState(worldIn, mutablePos.move(0, 1, 0), BENT_CANDY_CANE_STATE.with(FACING, direction))
        // extend with horizontal candy cane block
        setBlockState(worldIn, mutablePos.move(extendDirection, 1), CANDY_CANE_STATE.with(AXIS, extendDirection.axis))
        // set last corner
        setBlockState(worldIn, mutablePos.move(extendDirection, 1), BENT_CANDY_CANE_STATE.with(FACING, opposite))
        // droop down and have the hook complete with last vertical candy cane block placement
        setBlockState(worldIn, mutablePos.move(0, -1, 0), CANDY_CANE_STATE)

        // feature gen completed
        return true
    }

    companion object {
        private val CANDY_CANE_STATE = HBlocks.CANDY_CANE_BLOCK.defaultState
        private val BENT_CANDY_CANE_STATE = HBlocks.BENT_CANDY_CANE_BLOCK.defaultState
        private val DIRECTIONS = arrayOf(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST)
    }
}