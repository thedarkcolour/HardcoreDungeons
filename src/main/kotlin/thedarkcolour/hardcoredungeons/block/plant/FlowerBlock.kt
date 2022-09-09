package thedarkcolour.hardcoredungeons.block.plant

import net.minecraft.world.level.block.state.BlockState
import net.minecraft.core.BlockPos
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.FlowerBlock

open class FlowerBlock(properties: PlantProperties) : FlowerBlock(properties.effect, properties.duration, properties.build()) {
    private val predicate = properties.predicate

    override fun mayPlaceOn(state: BlockState, worldIn: BlockGetter, pos: BlockPos): Boolean {
        return predicate(state)
    }
}