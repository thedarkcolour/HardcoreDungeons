package thedarkcolour.hardcoredungeons.block.plant

import net.minecraft.block.BlockState
import net.minecraft.block.FlowerBlock
import net.minecraft.util.math.BlockPos
import net.minecraft.world.IBlockReader

open class FlowerBlock(properties: PlantProperties) : FlowerBlock(properties.effect, properties.duration, properties.build()) {
    private val predicate = properties.predicate

    override fun mayPlaceOn(state: BlockState, worldIn: IBlockReader, pos: BlockPos): Boolean {
        return predicate(state)
    }
}