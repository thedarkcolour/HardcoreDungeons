package thedarkcolour.hardcoredungeons.block.plant

import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.MushroomBlock
import net.minecraft.util.Direction
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockReader
import net.minecraft.world.IWorldReader
import thedarkcolour.hardcoredungeons.block.properties.PlantProperties
import thedarkcolour.hardcoredungeons.util.copyOf

class MushroomBlock(properties: PlantProperties) : MushroomBlock(properties.build()) {
    private val blocks = properties.blocks.copyOf() // see Util.kt
    private val strict = properties.strict

    override fun isValidGround(state: BlockState, worldIn: IBlockReader?, pos: BlockPos?): Boolean {
        val block = state.block

        return if (strict) {
            blocks.contains(block)
        } else {
            blocks.contains(block) || block == Blocks.MYCELIUM || block == Blocks.PODZOL
        }
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