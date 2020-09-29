package thedarkcolour.hardcoredungeons.block.plant.aubrum

import net.minecraft.block.BlockState
import net.minecraft.particles.ParticleTypes
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import thedarkcolour.hardcoredungeons.block.plant.FlowerBlock
import thedarkcolour.hardcoredungeons.block.properties.PlantProperties
import java.util.*

class FlameRoseBlock(properties: PlantProperties) : FlowerBlock(properties) {
    override fun animateTick(state: BlockState, worldIn: World, pos: BlockPos, rand: Random?) {
        val d0 = state.getOffset(worldIn, pos).x + pos.x + 0.5
        val d1 = state.getOffset(worldIn, pos).y + pos.y + 0.96
        val d2 = state.getOffset(worldIn, pos).z + pos.z + 0.5
        worldIn.addParticle(ParticleTypes.FLAME, d0, d1, d2, 0.0, 0.0, 0.0)
    }
}