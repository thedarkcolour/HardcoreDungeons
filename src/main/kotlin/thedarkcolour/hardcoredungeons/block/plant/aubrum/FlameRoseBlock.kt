package thedarkcolour.hardcoredungeons.block.plant.aubrum

import net.minecraft.world.level.block.state.BlockState
import net.minecraft.core.BlockPos
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.util.RandomSource
import net.minecraft.world.level.Level
import thedarkcolour.hardcoredungeons.block.plant.FlowerBlock
import thedarkcolour.hardcoredungeons.block.plant.PlantProperties

class FlameRoseBlock(properties: PlantProperties) : FlowerBlock(properties) {
    override fun animateTick(state: BlockState, worldIn: Level, pos: BlockPos, rand: RandomSource) {
        val d0 = state.getOffset(worldIn, pos).x + pos.x + 0.5
        val d1 = state.getOffset(worldIn, pos).y + pos.y + 0.96
        val d2 = state.getOffset(worldIn, pos).z + pos.z + 0.5
        worldIn.addParticle(ParticleTypes.FLAME, d0, d1, d2, 0.0, 0.0, 0.0)
    }
}