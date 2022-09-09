package thedarkcolour.hardcoredungeons.block.decoration.castleton

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.block.state.properties.BlockStateProperties.LIT
import net.minecraft.core.BlockPos
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.util.RandomSource
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.TorchBlock
import net.minecraft.world.level.block.WallTorchBlock
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.phys.HitResult
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import thedarkcolour.hardcoredungeons.registry.HBlocks
import thedarkcolour.hardcoredungeons.registry.HParticles
import java.util.*

class CastletonTorchBlock(properties: HProperties) : TorchBlock(properties.build(), null) { // Particle is not registered yet
    init {
        registerDefaultState(stateDefinition.any().setValue(LIT, false))
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(LIT)
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
        return withLitState(super.getStateForPlacement(context), context)
    }

    override fun animateTick(state: BlockState, level: Level, pos: BlockPos, rand: RandomSource) {
        if (state.getValue(LIT)) {
            val d0 = pos.x.toDouble() + 0.5
            val d1 = pos.y.toDouble() + 0.7
            val d2 = pos.z.toDouble() + 0.5
            level.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0, 0.0, 0.0)
            level.addParticle(HParticles.CASTLETON_TORCH_FLAME, d0, d1, d2, 0.0, 0.0, 0.0)
        }
    }

    override fun getCloneItemStack(state: BlockState, result: HitResult?, level: BlockGetter?, pos: BlockPos?, player: Player?): ItemStack {
        return pickBlock(state)
    }

    class Wall(properties: HProperties) : WallTorchBlock(properties.build(), null) {
        override fun getStateForPlacement(context: BlockPlaceContext): BlockState? {
            return withLitState(super.getStateForPlacement(context), context)
        }

        override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
            builder.add(FACING, LIT)
        }

        override fun animateTick(state: BlockState, level: Level, pos: BlockPos, rand: RandomSource) {
            if (state.getValue(LIT)) {
                val direction1 = state.getValue(FACING).opposite
                val d0 = pos.x + 0.5 + 0.27 * direction1.stepX
                val d1 = pos.y + 0.92
                val d2 = pos.z + 0.5 + 0.27 * direction1.stepZ
                level.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0, 0.0, 0.0)
                level.addParticle(HParticles.CASTLETON_TORCH_FLAME, d0, d1, d2, 0.0, 0.0, 0.0)
            }
        }

        override fun getCloneItemStack(state: BlockState, result: HitResult?, level: BlockGetter?, pos: BlockPos?, player: Player?): ItemStack {
            return pickBlock(state)
        }
    }

    companion object {
        private fun pickBlock(state: BlockState): ItemStack {
            return if (state.getValue(LIT)) {
                ItemStack(HBlocks.CASTLETON_TORCH.litItem)
            } else {
                ItemStack(HBlocks.CASTLETON_TORCH.burntItem)
            }
        }

        private fun withLitState(state: BlockState?, context: BlockPlaceContext): BlockState? {
            return state?.setValue(LIT, context.itemInHand.item == HBlocks.CASTLETON_TORCH.litItem)
        }
    }
}