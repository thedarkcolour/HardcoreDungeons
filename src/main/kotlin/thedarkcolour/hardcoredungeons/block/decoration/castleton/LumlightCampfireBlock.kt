package thedarkcolour.hardcoredungeons.block.decoration.castleton

import net.minecraft.world.level.block.state.BlockState
import net.minecraft.core.BlockPos
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.util.RandomSource
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.CampfireBlock
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import thedarkcolour.hardcoredungeons.registry.block.HBlocks
import thedarkcolour.hardcoredungeons.registry.HParticles

class LumlightCampfireBlock(properties: HProperties) : CampfireBlock(true, 2, properties.build()) {
    override fun animateTick(state: BlockState, level: Level, pos: BlockPos, rand: RandomSource) {
        if (state.getValue(LIT)) {
            if (rand.nextInt(10) == 0) {
                level.playLocalSound((pos.x + 0.5f).toDouble(), (pos.y + 0.5f).toDouble(), (pos.z + 0.5f).toDouble(), SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS, 0.5f + rand.nextFloat(), rand.nextFloat() * 0.7f + 0.6f, false)
            }
            if (rand.nextInt(5) == 0) {
                for (i in 0 until rand.nextInt(1) + 1) {
                    level.addParticle(HParticles.CASTLETON_CAMPFIRE_POP, (pos.x + 0.5f).toDouble(), (pos.y + 0.5f).toDouble(), (pos.z + 0.5f).toDouble(), (rand.nextFloat() / 2.0f).toDouble(), 5.0E-5, (rand.nextFloat() / 2.0f).toDouble())
                }
            }
        }
    }

    override fun isSmokeSource(state: BlockState): Boolean {
        return state.block == HBlocks.CRACKED_CASTLETON_BRICKS
    }
}