package thedarkcolour.hardcoredungeons.block.structure

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.ActionResultType
import net.minecraft.util.Hand
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.world.IBlockReader
import net.minecraft.world.World
import thedarkcolour.hardcoredungeons.registry.HItems
import thedarkcolour.hardcoredungeons.tileentity.DungeonSpawnerTileEntity

class DungeonSpawnerBlock(properties: Properties) : Block(properties) {
    override fun hasTileEntity(state: BlockState?): Boolean {
        return true
    }

    override fun createTileEntity(state: BlockState?, world: IBlockReader?): TileEntity {
        return DungeonSpawnerTileEntity()
    }

    override fun use(state: BlockState, worldIn: World, pos: BlockPos, player: PlayerEntity, handIn: Hand, hit: BlockRayTraceResult): ActionResultType {
        if (player.getItemInHand(handIn).item == HItems.GEAR_WAND) {
            if (!worldIn.isClientSide) {
                val tile = worldIn.getBlockEntity(pos)

                if (tile is DungeonSpawnerTileEntity) {

                }
                return ActionResultType.CONSUME
            } else {
                return ActionResultType.SUCCESS
            }
        }
        return super.use(state, worldIn, pos, player, handIn, hit)
    }
}
