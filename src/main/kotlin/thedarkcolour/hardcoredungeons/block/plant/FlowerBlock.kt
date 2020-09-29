package thedarkcolour.hardcoredungeons.block.plant

import net.minecraft.block.BlockState
import net.minecraft.block.FlowerBlock
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockReader
import thedarkcolour.hardcoredungeons.block.properties.PlantProperties
import thedarkcolour.hardcoredungeons.util.matchesAny

open class FlowerBlock(properties: PlantProperties) : FlowerBlock(properties.effect, properties.duration, properties.build()) {
    private val strict = properties.strict
    private val blocks = properties.blocks

    override fun isValidGround(state: BlockState, worldIn: IBlockReader, pos: BlockPos): Boolean {
        return if (strict) {
            state.block.matchesAny(blocks)
        } else {
            super.isValidGround(state, worldIn, pos) && state.block.matchesAny(blocks)
        }
    }
}