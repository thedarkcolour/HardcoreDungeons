package thedarkcolour.hardcoredungeons.block.structure

import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.ActionResultType
import net.minecraft.util.Hand
import net.minecraft.core.BlockPos
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.world.IBlockReader
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.phys.BlockHitResult
import thedarkcolour.hardcoredungeons.block.base.HBlock
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties
import thedarkcolour.hardcoredungeons.registry.HBlocks
import thedarkcolour.hardcoredungeons.registry.HItems
import thedarkcolour.hardcoredungeons.tileentity.DungeonSpawnerTileEntity

class DungeonSpawnerBlock(properties: HProperties) : HBlock(properties) {
    override fun hasTileEntity(state: BlockState?): Boolean {
        return true
    }

    override fun createTileEntity(state: BlockState?, world: BlockGetter?): TileEntity {
        return DungeonSpawnerTileEntity()
    }

    override fun use(state: BlockState, worldIn: Level, pos: BlockPos, player: Player, handIn: InteractionHand, hit: BlockHitResult): InteractionResult {
        if (player.getItemInHand(handIn).item == HItems.GEAR_WAND) {
            if (!worldIn.isClientSide) {
                val tile = worldIn.getBlockEntity(pos)

                if (tile is DungeonSpawnerTileEntity) {

                }
                return InteractionResult.CONSUME
            } else {
                return ActionResultType.SUCCESS
            }
        }
        return super.use(state, worldIn, pos, player, handIn, hit)
    }
}
