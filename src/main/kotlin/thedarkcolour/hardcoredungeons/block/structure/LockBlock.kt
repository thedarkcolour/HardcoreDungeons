package thedarkcolour.hardcoredungeons.block.structure

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.ActionResultType
import net.minecraft.util.Direction
import net.minecraft.util.Hand
import net.minecraft.util.IItemProvider
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.BlockRayTraceResult
import net.minecraft.world.World
import thedarkcolour.hardcoredungeons.block.base.HBlock
import thedarkcolour.hardcoredungeons.block.base.properties.HProperties

/**
 * @param key The item used to unlock this door
 * @param keyspace The block that is "unlocked" by this key
 * @param properties Block properties
 */
class LockBlock(private val key: IItemProvider, private val keyspace: () -> Block, properties: HProperties) : HBlock(properties) {
    override fun use(
        state: BlockState,
        worldIn: World,
        pos: BlockPos,
        player: PlayerEntity,
        handIn: Hand,
        hit: BlockRayTraceResult,
    ): ActionResultType {
        val key = key.asItem()
        if (key == player.getItemInHand(Hand.MAIN_HAND).item || key == player.getItemInHand(Hand.OFF_HAND).item) {
            if (worldIn.isClientSide) {
                return ActionResultType.SUCCESS
            } else {
                val positions = arrayListOf(pos)
                val keyspace = keyspace()

                try {
                    collectAdjacentBlocks(worldIn, pos, keyspace, positions, 144)
                } catch (e: Exception) {
                    e.printStackTrace()
                    return ActionResultType.FAIL
                }

                for (position in positions) {
                    worldIn.destroyBlock(position, false)
                }
            }
        }

        return ActionResultType.PASS
    }

    private fun collectAdjacentBlocks(worldIn: World, from: BlockPos, target: Block, list: MutableList<BlockPos>, limit: Int) {
        val toCheck = arrayListOf(from)

        for (pos in toCheck) {
            for (direction in Direction.values()) {
                val p = pos.relative(direction)

                if (list.size >= limit) {
                    return
                }

                if (worldIn.getBlockState(p).block == target && !list.contains(p)) {
                    list.add(p)
                    toCheck.add(p)
                    //collectAdjacentBlocks(worldIn, p, target, list, limit)
                }
            }
        }
    }
}