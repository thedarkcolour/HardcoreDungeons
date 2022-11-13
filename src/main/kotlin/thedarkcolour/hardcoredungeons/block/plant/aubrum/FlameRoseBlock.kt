package thedarkcolour.hardcoredungeons.block.plant.aubrum

import net.minecraft.core.BlockPos
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.util.RandomSource
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.FlowerPotBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.Material
import thedarkcolour.hardcoredungeons.block.combo.CustomFlowerPot
import thedarkcolour.hardcoredungeons.block.plant.FlowerBlock
import thedarkcolour.hardcoredungeons.block.plant.PlantProperties
import java.util.function.Supplier

class FlameRoseBlock(properties: PlantProperties) : FlowerBlock(properties) {
    override fun animateTick(state: BlockState, level: Level, pos: BlockPos, rand: RandomSource) = playParticles(state, level, pos)

    companion object : CustomFlowerPot {
        override fun makeFlowerPot(plant: Supplier<Block>): FlowerPotBlock {
            return Potted(plant)
        }

        private fun playParticles(state: BlockState, level: Level, pos: BlockPos) {
            val d0 = state.getOffset(level, pos).x + pos.x + 0.5
            val d1 = state.getOffset(level, pos).y + pos.y + 0.96
            val d2 = state.getOffset(level, pos).z + pos.z + 0.5
            level.addParticle(ParticleTypes.FLAME, d0, d1, d2, 0.0, 0.0, 0.0)
        }
    }

    private class Potted(plant: Supplier<Block>) : FlowerPotBlock({ Blocks.FLOWER_POT as FlowerPotBlock}, plant, Properties.of(Material.DECORATION).instabreak().noOcclusion().lightLevel { 13 }) {
        override fun animateTick(state: BlockState, level: Level, pos: BlockPos, rand: RandomSource) = playParticles(state, level, pos)
    }
}