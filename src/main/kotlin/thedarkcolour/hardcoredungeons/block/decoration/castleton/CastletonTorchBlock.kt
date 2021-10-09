package thedarkcolour.hardcoredungeons.block.decoration.castleton

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.TorchBlock
import net.minecraft.block.WallTorchBlock
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.BlockItemUseContext
import net.minecraft.item.ItemStack
import net.minecraft.particles.ParticleTypes
import net.minecraft.state.BooleanProperty
import net.minecraft.state.StateContainer
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.RayTraceResult
import net.minecraft.world.IBlockReader
import net.minecraft.world.World
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import thedarkcolour.hardcoredungeons.registry.HItems
import thedarkcolour.hardcoredungeons.registry.HParticles
import java.util.*

class CastletonTorchBlock(properties: HProperties) : TorchBlock(properties.build(), HParticles.CASTLETON_TORCH_FLAME) {
    init {
        registerDefaultState(stateDefinition.any().setValue(LIT, false))
    }

    override fun createBlockStateDefinition(builder: StateContainer.Builder<Block, BlockState>) {
        builder.add(LIT)
    }

    override fun getStateForPlacement(context: BlockItemUseContext): BlockState {
        return defaultBlockState().setValue(LIT, context.itemInHand.item == HItems.CASTLETON_TORCH)
    }

    override fun animateTick(state: BlockState, level: World, pos: BlockPos, rand: Random?) {
        if (state.getValue(LIT)) {
            val d0 = pos.x.toDouble() + 0.5
            val d1 = pos.y.toDouble() + 0.7
            val d2 = pos.z.toDouble() + 0.5
            level.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0, 0.0, 0.0)
            level.addParticle(HParticles.CASTLETON_TORCH_FLAME, d0, d1, d2, 0.0, 0.0, 0.0)
        }
    }

    // todo remove
    override fun getLightValue(state: BlockState, level: IBlockReader?, pos: BlockPos?): Int {
        return if (state.getValue(LIT)) super.getLightValue(state, level, pos) else 0
    }

    override fun getPickBlock(
        state: BlockState, result: RayTraceResult?, level: IBlockReader?, pos: BlockPos?, player: PlayerEntity?
    ): ItemStack {
        return if (state.getValue(LIT)) ItemStack(HItems.CASTLETON_TORCH) else ItemStack(HItems.BURNT_CASTLETON_TORCH)
    }

    class Wall(properties: HProperties) : WallTorchBlock(properties.build(), HParticles.CASTLETON_TORCH_FLAME) {
        override fun getStateForPlacement(context: BlockItemUseContext): BlockState? {
            return super.getStateForPlacement(context)?.setValue(LIT, context.itemInHand.item == HItems.CASTLETON_TORCH)
        }

        override fun createBlockStateDefinition(builder: StateContainer.Builder<Block, BlockState>) {
            builder.add(FACING, LIT)
        }

        override fun animateTick(state: BlockState, level: World, pos: BlockPos, rand: Random?) {
            if (state.getValue(LIT)) {
                val direction1 = state.getValue(FACING).opposite
                val d0 = pos.x + 0.5 + 0.27 * direction1.stepX
                val d1 = pos.y + 0.92
                val d2 = pos.z + 0.5 + 0.27 * direction1.stepZ
                level.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0, 0.0, 0.0)
                level.addParticle(HParticles.CASTLETON_TORCH_FLAME, d0, d1, d2, 0.0, 0.0, 0.0)
            }
        }

        // todo remove
        override fun getLightValue(state: BlockState, level: IBlockReader?, pos: BlockPos?): Int {
            return if (state.getValue(LIT)) super.getLightValue(state, level, pos) else 0
        }

        override fun getPickBlock(state: BlockState, result: RayTraceResult?, level: IBlockReader?, pos: BlockPos?, player: PlayerEntity?): ItemStack {
            return if (state.getValue(LIT)) ItemStack(HItems.CASTLETON_TORCH) else ItemStack(HItems.BURNT_CASTLETON_TORCH)
        }
    }

    companion object {
        private val LIT = BooleanProperty.create("lit")
    }
}