package thedarkcolour.hardcoredungeons.block.decoration.castleton

import net.minecraft.block.BlockState
import net.minecraft.block.CampfireBlock
import net.minecraft.util.SoundCategory
import net.minecraft.util.SoundEvents
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import thedarkcolour.hardcoredungeons.block.properties.HProperties
import thedarkcolour.hardcoredungeons.registry.HBlocks
import thedarkcolour.hardcoredungeons.registry.HParticles
import java.util.*

class LumlightCampfireBlock(properties: HProperties) : CampfireBlock(true, 2, properties.build()) {
    override fun animateTick(stateIn: BlockState, worldIn: World, pos: BlockPos, rand: Random) {
        if (stateIn[LIT]) {
            if (rand.nextInt(10) == 0) {
                worldIn.playSound(
                    (pos.x + 0.5f).toDouble(),
                    (pos.y + 0.5f).toDouble(),
                    (pos.z + 0.5f).toDouble(),
                    SoundEvents.BLOCK_CAMPFIRE_CRACKLE,
                    SoundCategory.BLOCKS,
                    0.5f + rand.nextFloat(),
                    rand.nextFloat() * 0.7f + 0.6f,
                    false
                )
            }
            if (rand.nextInt(5) == 0) {
                for (i in 0 until rand.nextInt(1) + 1) {
                    worldIn.addParticle(
                        HParticles.CASTLETON_CAMPFIRE_POP,
                        (pos.x + 0.5f).toDouble(),
                        (pos.y + 0.5f).toDouble(),
                        (pos.z + 0.5f).toDouble(),
                        (rand.nextFloat() / 2.0f).toDouble(),
                        5.0E-5,
                        (rand.nextFloat() / 2.0f).toDouble()
                    )
                }
            }
        }
    }

    override fun isHayBlock(state: BlockState): Boolean {
        return state.block == HBlocks.CRACKED_CASTLETON_BRICKS
    }
}