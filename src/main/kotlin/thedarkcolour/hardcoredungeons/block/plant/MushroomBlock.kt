package thedarkcolour.hardcoredungeons.block.plant

import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.Blocks
import net.minecraft.core.Direction
import net.minecraft.core.BlockPos
import net.minecraft.server.level.ServerLevel
import net.minecraft.util.RandomSource
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.LevelReader

class MushroomBlock(properties: PlantProperties) : net.minecraft.world.level.block.MushroomBlock(properties.build(), null) {
    // if strict only use predicate else you can also use podzol and mycelium
    private val predicate = if (properties.strict) { properties.predicate } else { state -> properties.predicate(state) || state.block == Blocks.MYCELIUM || state.block == Blocks.PODZOL }

    override fun mayPlaceOn(state: BlockState, worldIn: BlockGetter, pos: BlockPos): Boolean {
        return predicate(state)
    }

    override fun growMushroom(
        level: ServerLevel,
        pos: BlockPos,
        state: BlockState,
        random: RandomSource
    ): Boolean {
        return false
    }

    override fun canSurvive(state: BlockState, worldIn: LevelReader, pos: BlockPos): Boolean {
        val down = pos.below()
        val downState = worldIn.getBlockState(down)
        return if (!mayPlaceOn(downState, worldIn, pos)) {
            worldIn.getMaxLocalRawBrightness(pos, 0) < 13 && downState.canSustainPlant(worldIn, down, Direction.UP, this)
        } else {
            true
        }
    }
}