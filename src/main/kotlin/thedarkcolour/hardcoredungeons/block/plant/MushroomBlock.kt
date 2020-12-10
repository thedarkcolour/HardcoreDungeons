package thedarkcolour.hardcoredungeons.block.plant

import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.MushroomBlock
import net.minecraft.util.Direction
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockReader
import net.minecraft.world.IWorldReader
import thedarkcolour.hardcoredungeons.block.properties.PlantProperties

class MushroomBlock(properties: PlantProperties) : MushroomBlock(properties.build()) {
    // if strict only use predicate else you can also use podzol and mycelium
    private val predicate = if (properties.strict) { properties.predicate } else { state -> properties.predicate(state) || state.block == Blocks.MYCELIUM || state.block == Blocks.PODZOL }

    override fun isValidGround(state: BlockState, worldIn: IBlockReader?, pos: BlockPos?): Boolean {
        return predicate(state)
    }

    override fun isValidPosition(state: BlockState?, worldIn: IWorldReader, pos: BlockPos): Boolean {
        val down = pos.down()
        val downState = worldIn.getBlockState(down)
        return if (!isValidGround(downState, worldIn, pos)) {
            worldIn.getNeighborAwareLightSubtracted(pos, 0) < 13 && downState.canSustainPlant(worldIn, down, Direction.UP, this)
        } else {
            true
        }
    }
}