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
import thedarkcolour.hardcoredungeons.block.properties.HProperties
import thedarkcolour.hardcoredungeons.registry.HItems
import thedarkcolour.hardcoredungeons.registry.HParticles
import java.util.*

class CastletonTorchBlock(properties: HProperties) : TorchBlock(properties.build(), HParticles.CASTLETON_TORCH_FLAME) {
    init {
        defaultState = defaultState.with(LIT, false)
    }

    override fun fillStateContainer(builder: StateContainer.Builder<Block, BlockState>) {
        builder.add(LIT)
    }

    override fun getStateForPlacement(context: BlockItemUseContext): BlockState {
        return defaultState.with(LIT, context.item.item == HItems.CASTLETON_TORCH)
    }

    override fun animateTick(state: BlockState, worldIn: World, pos: BlockPos, rand: Random?) {
        if (state[LIT]) {
            val d0 = pos.x.toDouble() + 0.5
            val d1 = pos.y.toDouble() + 0.7
            val d2 = pos.z.toDouble() + 0.5
            worldIn.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0, 0.0, 0.0)
            worldIn.addParticle(HParticles.CASTLETON_TORCH_FLAME, d0, d1, d2, 0.0, 0.0, 0.0)
        }
    }

    override fun getLightValue(state: BlockState, world: IBlockReader?, pos: BlockPos?): Int {
        return if (state[LIT]) super.getLightValue(state, world, pos) else 0
    }

    override fun getPickBlock(
        state: BlockState, result: RayTraceResult?, world: IBlockReader?, pos: BlockPos?, player: PlayerEntity?
    ): ItemStack {
        return if (state[LIT]) ItemStack(HItems.CASTLETON_TORCH) else ItemStack(HItems.BURNT_CASTLETON_TORCH)
    }

    class Wall(properties: HProperties) : WallTorchBlock(properties.build(), HParticles.CASTLETON_TORCH_FLAME) {
        init {
            defaultState = defaultState.with(LIT, true)
        }

        override fun getStateForPlacement(context: BlockItemUseContext): BlockState? {
            return super.getStateForPlacement(context)?.with(LIT, context.item.item == HItems.CASTLETON_TORCH)
        }

        override fun fillStateContainer(builder: StateContainer.Builder<Block, BlockState>) {
            builder.add(HORIZONTAL_FACING, LIT)
        }

        override fun animateTick(state: BlockState, worldIn: World, pos: BlockPos, rand: Random?) {
            if (state[LIT]) {
                val direction1 = state.get(HORIZONTAL_FACING).opposite
                val d0 = pos.x + 0.5 + 0.27 * direction1.xOffset
                val d1 = pos.y + 0.92
                val d2 = pos.z + 0.5 + 0.27 * direction1.zOffset
                worldIn.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0, 0.0, 0.0)
                worldIn.addParticle(HParticles.CASTLETON_TORCH_FLAME, d0, d1, d2, 0.0, 0.0, 0.0)
            }
        }

        override fun getLightValue(state: BlockState, world: IBlockReader?, pos: BlockPos?): Int {
            return if (state[LIT]) super.getLightValue(state, world, pos) else 0
        }

        override fun getPickBlock(
            state: BlockState, result: RayTraceResult?, world: IBlockReader?, pos: BlockPos?, player: PlayerEntity?
        ): ItemStack {
            return if (state[LIT]) ItemStack(HItems.CASTLETON_TORCH) else ItemStack(HItems.BURNT_CASTLETON_TORCH)
        }
    }

    companion object {
        private val LIT = BooleanProperty.create("lit")
    }
}