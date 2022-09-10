package thedarkcolour.hardcoredungeons.worldgen.feature

import net.minecraft.world.level.block.state.properties.BlockStateProperties.AXIS
import net.minecraft.world.level.block.state.properties.BlockStateProperties.FACING
import net.minecraft.core.Direction
import net.minecraft.core.BlockPos
import net.minecraft.util.RandomSource
import net.minecraft.world.level.WorldGenLevel
import net.minecraft.world.level.levelgen.feature.Feature
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration
import thedarkcolour.hardcoredungeons.registry.block.HBlocks

class CandyCaneFeature : Feature<NoneFeatureConfiguration>(NoneFeatureConfiguration.CODEC) {
    override fun place(ctx: FeaturePlaceContext<NoneFeatureConfiguration>): Boolean {
        return place(ctx.level(), ctx.random(), ctx.origin())
    }

    private fun place(
        worldIn: WorldGenLevel,
        rand: RandomSource,
        pos: BlockPos,
    ): Boolean {
        // don't spawn in the water
        if (!worldIn.getBlockState(pos.below()).canOcclude()) return false

        // maximum height is 6 blocks
        val height = 3 + rand.nextInt(4)
        // start at ground block for ground checking
        val mutablePos = pos.mutable().move(0, -1, 0)

        for (i in 0..height) {
            setBlock(worldIn, mutablePos.move(0, 1, 0), CANDY_CANE_STATE)
        }

        // first corner rotation
        val direction = DIRECTIONS[rand.nextInt(4)]
        // last corner rotation
        val opposite = direction.opposite
        // the direction to build out the hook
        val extendDirection = direction.clockWise

        // begin handle with last upright cane block
        setBlock(worldIn, mutablePos.move(0, 1, 0), CANDY_CANE_STATE)
        // set first corner
        setBlock(worldIn, mutablePos.move(0, 1, 0), BENT_CANDY_CANE_STATE.setValue(FACING, direction))
        // extend with horizontal candy cane block
        setBlock(worldIn, mutablePos.move(extendDirection, 1), CANDY_CANE_STATE.setValue(AXIS, extendDirection.axis))
        // set last corner
        setBlock(worldIn, mutablePos.move(extendDirection, 1), BENT_CANDY_CANE_STATE.setValue(FACING, opposite))
        // droop down and have the hook complete with last vertical candy cane block placement
        setBlock(worldIn, mutablePos.move(0, -1, 0), CANDY_CANE_STATE)

        // feature gen completed
        return true
    }

    companion object {
        private val CANDY_CANE_STATE by lazy { HBlocks.CANDY_CANE_BLOCK.defaultBlockState() }
        private val BENT_CANDY_CANE_STATE by lazy { HBlocks.BENT_CANDY_CANE_BLOCK.defaultBlockState() }
        private val DIRECTIONS = arrayOf(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST)
    }
}